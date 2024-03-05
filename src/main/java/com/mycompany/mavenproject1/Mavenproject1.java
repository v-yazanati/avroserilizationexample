/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;


import com.azure.core.util.serializer.*;
import io.cloudevents.*;
import com.azure.core.models.*;
import com.azure.core.util.serializer.TypeReference;
import com.azure.data.schemaregistry.*;
import com.azure.data.schemaregistry.apacheavro.SchemaRegistryApacheAvroSerializer;
import com.azure.data.schemaregistry.apacheavro.SchemaRegistryApacheAvroSerializerBuilder;
import com.azure.data.schemaregistry.avro.*;
import com.azure.identity.*;
import com.azure.messaging.eventhubs.*;
import java.net.URI;
import java.io.*;




/**
 *
 * @author v-yazanati
 */
public class Mavenproject1 {

    public static void main(String[] args) {
        

        String conStr = "Endpoint=sb://yazan-eh.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=CoAVkqVnwsFw4vhAPm8tp/HgfXhOZz+xG+AEhIWprts=";
        String eveHubName = "yazaninstance";
        String eventHubEndPoint = "yazan-eh.servicebus.windows.net";
        String schemaGroup = "yazangroup";
        AzureCliCredential azureCredential = new AzureCliCredentialBuilder().build();
        
                    var schemaRegistryAsyncClient = new SchemaRegistryClientBuilder()
                    .fullyQualifiedNamespace(eventHubEndPoint)
                    .credential(azureCredential)
                    .buildAsyncClient();

            var AVRO_SERIALIZER = new SchemaRegistryApacheAvroSerializerBuilder()
                    .schemaRegistryClient(schemaRegistryAsyncClient)
                    .schemaGroup(schemaGroup)
                    .avroSpecificReader(true)
                    .autoRegisterSchemas(true)
                    .buildSerializer();
                EventHubProducerClient producer = new EventHubClientBuilder()
            .connectionString(conStr, eveHubName)
            .buildProducerClient();
        
        io.cloudevents.CloudEvent data = io.cloudevents.CloudEvent.newBuilder().setId("1")
                .setDataschema("yazanschema")
                .setDatacontenttype("avro")
                .setSource("src")
                .setSpecversion("1")
                .setSubject("sub")
                .setType("avro")
                .setTime("12:00")
                .setData("{}")
                .build();

        

     EventData encodedMessage  = AVRO_SERIALIZER.serialize(data, TypeReference.createInstance(EventData.class));
     
        
        var batch = producer.createBatch();
        batch.tryAdd(encodedMessage);
        producer.send(batch);
        System.out.println("Message sent!!!!!!!!");
        
        
  
    }
}


