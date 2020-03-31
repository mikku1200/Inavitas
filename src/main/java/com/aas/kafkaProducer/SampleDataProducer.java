package com.aas.kafkaproducer;

import com.aas.entity.SampleData;
import java.util.List;
import java.util.Map;

public interface SampleDataProducer {
     void dataProducer(Map<Integer, List<SampleData>> listData);
}
