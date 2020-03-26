package com.aas.kafkaproducerimpl;

import com.aas.entity.SampleData;
import com.aas.kafkaProducer.SampleDataProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This class will accept map type data which contains device Id as key and list of sample data as value.
 * In this method traversing through all sample data based on map key and converting it into json string.
 * producing json string into kafka with topic name sample-data.
 */
@Component
public class SampleDataProducerImpl implements SampleDataProducer {
    @Override
    public String dataProducer(Map<Integer, List<SampleData>> listData) {

        String result="";
        //Setting properties for kafka producer.
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");

        props.put("acks", "all");

        props.put("key.serializer", StringSerializer.class.getName());

        props.put("value.serializer", StringSerializer.class.getName());

        //Creating kafka producer object
        Producer<String, String> producer = new KafkaProducer<>(props);

        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Integer deviceId : listData.keySet()) {
                for (SampleData sampleData : listData.get(deviceId)) {
                    //Converting sample data object into json.
                    String data = mapper.writeValueAsString(sampleData);
                    //Producing sample data onto kafka.
                    producer.send(new ProducerRecord<>("sample-data", data));
                }
            }
            //closing kafka producer connection
            producer.close();
            result= "Successfully produces";
        } catch (JsonProcessingException e) {
            result ="Error with Json processing";
            e.printStackTrace();// TODO need to log instead of printing on console.
        }
    return result;
    }
}
