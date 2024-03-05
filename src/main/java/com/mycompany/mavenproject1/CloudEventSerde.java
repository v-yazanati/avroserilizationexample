package com.mycompany.mavenproject1;

import com.azure.core.credential.TokenCredential;
import com.azure.core.models.MessageContent;
import com.azure.core.util.BinaryData;
import com.azure.core.util.serializer.TypeReference;
import com.azure.data.schemaregistry.*;
import com.azure.data.schemaregistry.apacheavro.*;
import io.cloudevents.CloudEvent;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.ByteBuffer;


public class CloudEventSerde<T> implements Serde<T> {
    public static final String SPEC_VERSION = "1.0";
    private final static CloudEvent CLOUD_EVENT_SAMPLE = CloudEvent.newBuilder()
            .setSpecversion(SPEC_VERSION)
                .setDatacontenttype("avro/binary+*")
                .setData("{}")
                .build();
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudEventSerde.class);
    private static SchemaRegistryApacheAvroSerializer AVRO_SERIALIZER = null;
    private static String CLOUD_EVENT_CONTENT_TYPE = null;
    private final Serializer<T> serializer;
    private final Deserializer<T> deserializer;
    private final Class<T> type;
    private final URI sourceURI;

    public CloudEventSerde(String fullyQualifiedNamespace,
                           String schemaGroupName,
                           TokenCredential tokenCredential,
                           URI sourceURI,
                           Class<T> type){
            setAvroSerializerAndContentType(fullyQualifiedNamespace, schemaGroupName, tokenCredential);
            this.type = type;
            this.sourceURI = sourceURI;
            this.serializer = new CloudEventSerializer<>();
            this.deserializer = new CloudEventDeserializer<>();
            LOGGER.info("Instantiated serde for type {}, with CloudEvent serializer and deserializer, cloud event content type: {}", this.type.getSimpleName(), this.CLOUD_EVENT_CONTENT_TYPE);
    }


    public static void setAvroSerializerAndContentType(String fullyQualifiedNamespace,
                                                             String schemaGroupName,
                                                             TokenCredential tokenCredential) {
        if( AVRO_SERIALIZER == null ) {
            var schemaRegistryAsyncClient = new SchemaRegistryClientBuilder()
                    .fullyQualifiedNamespace(fullyQualifiedNamespace)
                    .credential(tokenCredential)
                    .buildAsyncClient();

            AVRO_SERIALIZER = new SchemaRegistryApacheAvroSerializerBuilder()
                    .schemaRegistryClient(schemaRegistryAsyncClient)
                    .schemaGroup(schemaGroupName)
                    .avroSpecificReader(true)
                    .buildSerializer();
        }

        if( CLOUD_EVENT_CONTENT_TYPE == null ){
            CLOUD_EVENT_CONTENT_TYPE = AVRO_SERIALIZER.serialize( CLOUD_EVENT_SAMPLE, TypeReference.createInstance(MessageContent.class)).getContentType();

        }
    }


    @Override
    public Serializer<T> serializer() {
        return this.serializer;
    }

    @Override
    public Deserializer<T> deserializer() {
        return this.deserializer;
    }

    public class CloudEventSerializer<T> implements Serializer<T> {

        @Override
        public byte[] serialize(String topic,  T message) {
            return serialize( topic, null, message);
        }

        @Override
        public byte[] serialize(String topic, Headers headers, T message) {
            LOGGER.info("Message for topic {} before serializing: {}", topic, message);
            var encodedMessage = AVRO_SERIALIZER.serialize(message, TypeReference.createInstance(MessageContent.class));
            // Create a cloud event, and pass it the serialized message
            var cloudEvent = CloudEvent.newBuilder()
                    .setData(encodedMessage.getBodyAsBinaryData().toByteBuffer())
                    .setDataschema("""
                                   {  "type" : "record",
                                     "name" : "InboundCancellationReceived",
                                     "namespace" : "dk.almbrand.ext.cancellation.inbound.contract",
                                     "doc" : "Records of this type should be published to Received evh",
                                     "fields" : [ {
                                       "name" : "state",
                                       "type" : {
                                         "type" : "enum",
                                         "name" : "State",
                                         "doc" : "This is the message that tells us about the state of the cancellation saga",
                                         "symbols" : [ "RECEIVED", "REJECTED", "DISPATCHED_TO_MANUAL_PROCESSING", "DISPATCHED_TO_AUTOMATED_PROCESSING", "PROCESSING_COMPLETED", "ERRORED" ],
                                         "default" : "ERRORED"
                                       }
                                     }, {
                                       "name" : "inboundCancellationProperties",
                                       "type" : {
                                         "type" : "record",
                                         "name" : "InboundCancellationProperties",
                                         "doc" : "This  record has a collection of properties, that we always want to be present, no matter the state of the inbound cancellation process",
                                         "fields" : [ {
                                           "name" : "externalReferenceNumber",
                                           "type" : "string",
                                           "doc" : "This is the reference id that is used to correlate the inbound cancellation inside AB Group with the process represented in Connector Plus with the field referenceNumber"
                                         }, {
                                           "name" : "customerNo",
                                           "type" : "string",
                                           "doc" : "This is the AB Group party id, representing the customer, that wants to cancel the insurance policy"
                                         }, {
                                           "name" : "policyNumber",
                                           "type" : "string"
                                         }, {
                                           "name" : "brand",
                                           "type" : {
                                             "type" : "record",
                                             "name" : "Brand",
                                             "doc" : "ABG has Brands (Codan, ALMB), and partner subagreements (Volvo), that can be processed in different source systems.",
                                             "fields" : [ {
                                               "name" : "brandName",
                                               "type" : "string",
                                               "doc" : "The ABG Brand (eg PSK)"
                                             }, {
                                               "name" : "partnerSubagreement",
                                               "type" : "string",
                                               "doc" : "The insurance label carried by the brand  - fx Volvo"
                                             }, {
                                               "name" : "sourceSystemId",
                                               "type" : {
                                                 "type" : "enum",
                                                 "name" : "SourceSystemId",
                                                 "doc" : "This is the id of the AB Group system, that is the source of the policy data",
                                                 "symbols" : [ "ABTIA", "CODANTIA", "VIPS" ]
                                               },
                                               "doc" : "The source system, that can process this brand"
                                             } ]
                                           },
                                           "doc" : "This field represents information about brand, ncesessary for procesing of the cancellation"
                                         }, {
                                           "name" : "cancellationDate",
                                           "type" : {
                                             "type" : "int",
                                             "logicalType" : "date"
                                           },
                                           "doc" : "This is the date that the cancellation is supposed to take effect"
                                         }, {
                                           "name" : "freeText",
                                           "type" : [ "null", "string" ],
                                           "default" : null
                                         }, {
                                           "name" : "industryProductCode",
                                           "type" : [ "null", "string" ],
                                           "default" : null
                                         }, {
                                           "name" : "cancellationCauseCode",
                                           "type" : {
                                             "type" : "enum",
                                             "name" : "CancellationCause",
                                             "symbols" : [ "_285_main_due_date", "_287_change_of_ownership_287", "_288_cancellation_with_free_text", "_292_dismissal", "_294_withdrawal", "_296_shortened_cancellation_only_for_private_customers", "_1000_unknown_due_to_error" ]
                                           }
                                         }, {
                                           "name" : "senderCompanyId",
                                           "type" : [ "null", "string" ],
                                           "default" : null
                                         }, {
                                           "name" : "receiverCompanyId",
                                           "type" : [ "null", "string" ],
                                           "default" : null
                                         } ]
                                       }
                                     }, {
                                       "name" : "stateReceivedProperties",
                                       "type" : {
                                         "type" : "record",
                                         "name" : "StateReceivedProperties",
                                         "fields" : [ {
                                           "name" : "cancelledPolicy",
                                           "type" : {
                                             "type" : "record",
                                             "name" : "CancelledPolicy",
                                             "fields" : [ {
                                               "name" : "policyNumber",
                                               "type" : "string"
                                             }, {
                                               "name" : "productId",
                                               "type" : [ "null", "string" ],
                                               "doc" : "An internal product id that is used for mapping",
                                               "default" : null
                                             }, {
                                               "name" : "objectId",
                                               "type" : [ "null", "string" ],
                                               "doc" : "A unique identifier of the object in internal systems"
                                             }, {
                                               "name" : "sourceSystem",
                                               "type" : "SourceSystemId"
                                             }, {
                                               "name" : "policyHolders",
                                               "type" : {
                                                 "type" : "array",
                                                 "items" : {
                                                   "type" : "record",
                                                   "name" : "PolicyHolder",
                                                   "fields" : [ {
                                                     "name" : "personalInformation",
                                                     "type" : [ "null", {
                                                       "type" : "record",
                                                       "name" : "PolicyHolderPersonalInformation",
                                                       "fields" : [ {
                                                         "name" : "name",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "lastName",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "birthDate",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "cpr",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "ptal",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "customerNo",
                                                         "type" : "string"
                                                       }, {
                                                         "name" : "cvr",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "vtal",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       } ]
                                                     } ],
                                                     "default" : null
                                                   }, {
                                                     "name" : "policyHolderAddress",
                                                     "type" : [ "null", {
                                                       "type" : "record",
                                                       "name" : "PolicyHolderAddress",
                                                       "fields" : [ {
                                                         "name" : "streetAddress",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "streetNumber",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "floor",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "side",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "door",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "city",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "postalCode",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "postalDistrict",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "countryCode",
                                                         "type" : [ "null", "string" ],
                                                         "default" : null
                                                       }, {
                                                         "name" : "kvhx",
                                                         "type" : [ "null", "string" ],
                                                         "doc" : "Unique address identity of the insured person or company.",
                                                         "default" : null
                                                       }, {
                                                         "name" : "akr",
                                                         "type" : [ "null", "string" ],
                                                         "doc" : "length 9",
                                                         "default" : null
                                                       } ]
                                                     } ],
                                                     "default" : null
                                                   } ]
                                                 }
                                               },
                                               "default" : [ ]
                                             }, {
                                               "name" : "cancellationDate",
                                               "type" : {
                                                 "type" : "int",
                                                 "logicalType" : "date"
                                               }
                                             }, {
                                               "name" : "policyCoverageIds",
                                               "type" : {
                                                 "type" : "array",
                                                 "items" : "string"
                                               },
                                               "default" : [ ]
                                             } ]
                                           }
                                         }, {
                                           "name" : "cancelledByMessage",
                                           "type" : {
                                             "type" : "record",
                                             "name" : "CancelledByMessage",
                                             "fields" : [ {
                                               "name" : "cancellationRequestPolicy",
                                               "type" : [ "null", {
                                                 "type" : "record",
                                                 "name" : "CancellationRequestPolicy",
                                                 "fields" : [ {
                                                   "name" : "policyNumber",
                                                   "type" : "string"
                                                 }, {
                                                   "name" : "policyHolders",
                                                   "type" : {
                                                     "type" : "array",
                                                     "items" : {
                                                       "type" : "record",
                                                       "name" : "CancellationRequestPolicyHolder",
                                                       "fields" : [ {
                                                         "name" : "policyHolder",
                                                         "type" : "PolicyHolder"
                                                       }, {
                                                         "name" : "consentForm",
                                                         "type" : "boolean",
                                                         "doc" : "true = Consent is aquired by Company",
                                                         "default" : false
                                                       } ]
                                                     }
                                                   },
                                                   "default" : [ ]
                                                 }, {
                                                   "name" : "product",
                                                   "type" : {
                                                     "type" : "record",
                                                     "name" : "CancellationRequestProduct",
                                                     "fields" : [ {
                                                       "name" : "industryProductCode",
                                                       "type" : "string"
                                                     }, {
                                                       "name" : "productId",
                                                       "type" : [ "null", "string" ],
                                                       "default" : null
                                                     }, {
                                                       "name" : "insuredObject",
                                                       "type" : {
                                                         "type" : "record",
                                                         "name" : "InsuredObject",
                                                         "fields" : [ {
                                                           "name" : "vehicle",
                                                           "type" : [ "null", {
                                                             "type" : "record",
                                                             "name" : "Vehicle",
                                                             "fields" : [ {
                                                               "name" : "description",
                                                               "type" : [ "null", "string" ],
                                                               "default" : null
                                                             }, {
                                                               "name" : "registrationNumber",
                                                               "type" : [ "null", "string" ],
                                                               "default" : null
                                                             }, {
                                                               "name" : "vinNumber",
                                                               "type" : [ "null", "string" ],
                                                               "default" : null
                                                             } ]
                                                           } ],
                                                           "default" : null
                                                         }, {
                                                           "name" : "animal",
                                                           "type" : [ "null", {
                                                             "type" : "record",
                                                             "name" : "Animal",
                                                             "fields" : [ {
                                                               "name" : "name",
                                                               "type" : [ "null", "string" ],
                                                               "default" : null
                                                             }, {
                                                               "name" : "chipNumber",
                                                               "type" : [ "null", "string" ],
                                                               "default" : null
                                                             } ]
                                                           } ],
                                                           "default" : null
                                                         }, {
                                                           "name" : "boat",
                                                           "type" : [ "null", {
                                                             "type" : "record",
                                                             "name" : "Boat",
                                                             "fields" : [ {
                                                               "name" : "hullNumber",
                                                               "type" : [ "null", "string" ],
                                                               "default" : null
                                                             }, {
                                                               "name" : "sailNumber",
                                                               "type" : [ "null", "string" ],
                                                               "default" : null
                                                             }, {
                                                               "name" : "buildNumber",
                                                               "type" : [ "null", "string" ],
                                                               "default" : null
                                                             } ]
                                                           } ],
                                                           "default" : null
                                                         }, {
                                                           "name" : "insured",
                                                           "type" : [ "null", "PolicyHolder" ],
                                                           "default" : null
                                                         }, {
                                                           "name" : "insuredPlace",
                                                           "type" : [ "null", {
                                                             "type" : "record",
                                                             "name" : "InsurancePlace",
                                                             "fields" : [ {
                                                               "name" : "cadastralNumber",
                                                               "type" : "string",
                                                               "doc" : "Cadastral Number (matrikelnummer)"
                                                             }, {
                                                               "name" : "insuredAddress",
                                                               "type" : "PolicyHolderAddress"
                                                             } ]
                                                           } ],
                                                           "default" : null
                                                         } ]
                                                       }
                                                     } ]
                                                   }
                                                 } ]
                                               } ],
                                               "default" : null
                                             }, {
                                               "name" : "buildingFire",
                                               "type" : "boolean",
                                               "default" : false
                                             }, {
                                               "name" : "cancellationCause",
                                               "type" : "CancellationCause"
                                             }, {
                                               "name" : "cancellationDate",
                                               "type" : {
                                                 "type" : "int",
                                                 "logicalType" : "date"
                                               }
                                             }, {
                                               "name" : "firstReferenceNumber",
                                               "type" : [ "null", "string" ]
                                             }, {
                                               "name" : "freeText",
                                               "type" : [ "null", "string" ]
                                             }, {
                                               "name" : "requestDate",
                                               "type" : {
                                                 "type" : "int",
                                                 "logicalType" : "date"
                                               }
                                             }, {
                                               "name" : "sender",
                                               "type" : {
                                                 "type" : "record",
                                                 "name" : "PartyIdentification",
                                                 "fields" : [ {
                                                   "name" : "virkNo",
                                                   "type" : "string",
                                                   "doc" : "6 digits"
                                                 }, {
                                                   "name" : "abGroupInsuranceCompanyIdentifier",
                                                   "type" : [ "null", "string" ],
                                                   "doc" : "needs a lookup in a caching service",
                                                   "default" : null
                                                 } ]
                                               }
                                             }, {
                                               "name" : "receiver",
                                               "type" : "PartyIdentification"
                                             }, {
                                               "name" : "companyMessageId",
                                               "type" : "string",
                                               "doc" : "An id set by the sending company, like Tryg, which is unique for this specific company"
                                             }, {
                                               "name" : "messageId",
                                               "type" : "string",
                                               "doc" : "A unique message id from F&P."
                                             }, {
                                               "name" : "referenceNumber",
                                               "type" : "string",
                                               "doc" : "A human readable identifier for a Correspondence between a request and a reply (can be anything as long as it is unique between two companies). If not set when sending a message, a reference number is created by ConnectorPlus."
                                             } ]
                                           }
                                         }, {
                                           "name" : "caseDataUrl",
                                           "type" : [ "null", "string" ]
                                         } ]
                                       }
                                     } ]
                                   }""")
                    .setDatacontenttype( encodedMessage.getContentType())
                    .setSource(sourceURI.toString())
                    .setSpecversion(SPEC_VERSION)
                    .build();
            LOGGER.info("Cloud event envelope with serialized message: {}", cloudEvent);

            // Serialize the cloud event (with the serialized message  inside)
            var encodedCloudEvent = AVRO_SERIALIZER.serialize(cloudEvent, TypeReference.createInstance(MessageContent.class));
            LOGGER.info("Serialized cloud event: {}", encodedCloudEvent.getBodyAsBinaryData().toBytes());
            return encodedCloudEvent.getBodyAsBinaryData().toBytes();
        }

        @Override
        public void close() {
            Serializer.super.close();
        }
    }

    public class CloudEventDeserializer<T> implements Deserializer<T> {

        @Override
        public T deserialize(String s, byte[] bytes) {
            return deserialize(s, null, bytes);
        }


        @Override
        public T deserialize(String topic, Headers headers, byte[] bytes) {
            try {
                //FIXME deserialize - could depend on headers (ie the content type) - discuss w Piotr
                var mc = new MessageContent();
                mc.setBodyAsBinaryData(BinaryData.fromBytes(bytes));
                mc.setContentType(CLOUD_EVENT_CONTENT_TYPE);
                var cloudEvent =  AVRO_SERIALIZER.deserialize( mc, TypeReference.createInstance(CloudEvent.class));

                //Message content
                var innerMc = new MessageContent();
                innerMc.setBodyAsBinaryData( BinaryData.fromByteBuffer( (ByteBuffer )cloudEvent.getData() ));
                innerMc.setContentType( String.valueOf( cloudEvent.getDatacontenttype()) );
                @SuppressWarnings("unchecked") //FIXME - can this typecast be avoided ???
                var deserializee = (T) AVRO_SERIALIZER.deserialize( innerMc, TypeReference.createInstance(type));
                return deserializee;
            } catch (Exception e) {
                LOGGER.error("Deserializer issues - couldn't deserialize message from topic {}",topic, e);
                return null;
            }
        }

    }
}
