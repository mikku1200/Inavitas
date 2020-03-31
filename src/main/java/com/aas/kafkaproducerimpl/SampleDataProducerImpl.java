package com.aas.kafkaproducerimpl;

import com.aas.entity.SampleData;
import com.aas.kafkaproducer.SampleDataProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This class will accept map type data which contains device Id as key and list of sample data as value.
 * Here we will create producer object and after traversing through it we will send list of sampleData of same DeviceId to producer thread to produce all data to kafka.
 */
public class SampleDataProducerImpl implements SampleDataProducer {
    public void dataProducer(Map<Integer, List<SampleData>> listData) {

        //Setting properties for kafka producer.
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");

        props.put("acks", "all");

        props.put("key.serializer", StringSerializer.class.getName());

        props.put("value.serializer", StringSerializer.class.getName());

        //Creating kafka producer object
        Producer<String, String> producer = new KafkaProducer<>(props);

        for (Integer deviceId : listData.keySet()) {
            //Creating and starting producerThread to produce data to kafka
            ProducerThread p = new ProducerThread(listData.get(deviceId), producer);
            p.start();
            try {
                //here joining thread so that one thread start running when previous one is completed its execution.
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //closing kafka producer connection
        producer.close();
        System.out.println("Producer Closed");
    }
}
