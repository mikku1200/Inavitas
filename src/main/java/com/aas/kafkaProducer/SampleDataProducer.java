package com.aas.kafkaProducer;

import com.aas.entity.SampleData;
import java.util.List;
import java.util.Map;

public interface SampleDataProducer {
     String dataProducer(Map<Integer, List<SampleData>> listData);
}
