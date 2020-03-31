package com.aas.Jsonutility;

import com.aas.sampledataexception.FileFormatException;
import com.aas.sampledataexception.SampleDataFileNotFoundException;

/**
 * Interface to call method create json
 * create json will accept csv file path as a input and return a Output string
 */
public interface CSVToJson {
    void createJson(String path) throws FileFormatException, SampleDataFileNotFoundException;
}
