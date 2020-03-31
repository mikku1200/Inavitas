package com.aas.entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entity to store sample data
 */
@Getter
@Setter
public class SampleData {
    private Integer deviceId;
    private String dateTime;
    private List<Data> dataList;
}
