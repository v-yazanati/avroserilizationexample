/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

import com.azure.core.models.*;
import com.azure.core.util.serializer.TypeReference;
import com.azure.data.schemaregistry.*;
import com.azure.data.schemaregistry.apacheavro.SchemaRegistryApacheAvroSerializer;
import com.azure.data.schemaregistry.apacheavro.SchemaRegistryApacheAvroSerializerBuilder;
import com.azure.data.schemaregistry.avro.*;
import com.azure.identity.*;
import com.azure.messaging.eventhubs.*;
import java.time.LocalDate;
import java.io.*;



/**
 *
 * @author v-yazanati
 */
public class Mavenproject1 {

    public static void main(String[] args) {
        

        String conStr = "<connectionString>";
        String eveHubName = "<eventhubName>";
        String eventHubEndPoint = "<eventhubEndpoint>";
        String schemaGroup = "<schemagroup>";
        
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

        //EventData ev1 = new EventData();
        //ev1.setContentType("avro/binary+1");

AzureCliCredential azureCredential = new AzureCliCredentialBuilder().build();



var schemaRegistryAsyncClient = new SchemaRegistryClientBuilder()
    
    .fullyQualifiedNamespace(eventHubEndPoint)
        .credential(azureCredential)
        .buildAsyncClient();


      SchemaRegistryApacheAvroSerializer serializer = new SchemaRegistryApacheAvroSerializerBuilder()
     .schemaRegistryClient(schemaRegistryAsyncClient)
     .schemaGroup(schemaGroup)
     .autoRegisterSchemas(true)
     .avroSpecificReader(true)
     .buildSerializer();


     EventData eventData = serializer.serialize(data, TypeReference.createInstance(EventData.class));
        
        var batch = producer.createBatch();
        batch.tryAdd(eventData);
        producer.send(batch);
        System.out.println("Message sent!!!!!!!!");
        
  
    }
}


