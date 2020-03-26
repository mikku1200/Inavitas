package com.aas.controller;

import com.aas.Jsonutility.CSVToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This is SampleDataProducerController. This is entry point of any request
 * 1. Api "publish/sampledata"  Get is used to test API is working correctly
 * 2. Api "publish/sampledata"  Post, It accepts path of csv file and convert it into Json and push to kafka
 */
@RestController
public class SampleDataProducerController {

    @Autowired
    CSVToJson csvToJson;

    //Get mapping to test API is working just for test
    @GetMapping("publish/sampledata")
    public String check() {
        return "SUCCESS";
    }

    // It accepts csv path in request param
    @PostMapping("publish/sampledata")
    public String postDataByFilePath(@RequestParam String path) {
        return csvToJson.createJson(path);
    }
}
