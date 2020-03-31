package com.aas.kafkaproducerimpl;

import com.aas.entity.SampleData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;

public class ProducerThread extends Thread {


    private List<SampleData> sampleDatas;
    private Producer producer;
    //parameterised constructor
    public ProducerThread(List<SampleData> sampleData,Producer producer)
    {
        this.sampleDatas=sampleData;
        this.producer=producer;
    }
    @Override
    public void run()
    {
        //Creating objectMapper object for creating json string from object
        ObjectMapper mapper = new ObjectMapper();
        // traversing through all sample data of same device ID.
        for (SampleData sampleData : sampleDatas) {
            //Converting sample data object into json.
            String data = null;
            try {
                data = mapper.writeValueAsString(sampleData);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //Producing sample data onto kafka.
            if(data!=null) {
                producer.send(new ProducerRecord<String, String>("sample-data", data));
            }
        }
    }
}
