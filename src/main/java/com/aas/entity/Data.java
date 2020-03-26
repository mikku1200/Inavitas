package com.aas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity to store data list
 */
@Setter
@Getter
@AllArgsConstructor
public class Data {
    private String dataName;
    private String dataValue;
}
