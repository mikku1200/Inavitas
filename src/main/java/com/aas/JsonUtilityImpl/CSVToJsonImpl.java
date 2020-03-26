package com.aas.JsonUtilityImpl;
import com.aas.entity.Data;
import com.aas.entity.SampleData;
import com.aas.Jsonutility.CSVToJson;
import com.aas.kafkaProducer.SampleDataProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** This class is to convert a csv file into Object of type Sampledata.
 *  Inside this createJson() method will accept one argument 1. Path of CSV file
 */
@Component
public class CSVToJsonImpl implements CSVToJson {

    @Autowired
    SampleDataProducer sampleDataProducer;

    @Override
    public String createJson(String path) {
        String result="";
        String line="";
        List<String> headers=new ArrayList();
        int flag =0;
        // to store all data with same device Id into key value pair. device id will be key.
        Map<Integer,List<SampleData>> listData=new HashMap<Integer,List<SampleData>>();
        //TODO Need to check for file extension
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            //Loop for traversing line by line csv file.
            while((line=br.readLine())!=null)
            {

                SampleData sampleData=new SampleData();
                //Check for headers
                if(flag==0)
                {
                    headers= Arrays.asList(line.split(","));
                    flag=1;
                }
                else {
                    String[] values = line.split(",");

                    sampleData.setDeviceId(Integer.parseInt(values[0]));
                    sampleData.setDateTime(values[1]);
                    List<Data> dataList = new ArrayList<>();

                    // loop to create data list skipping 1st and 2nd as it is device id and datetime which is not in data list.
                    for (int i = 2; i < values.length; i++) {
                        dataList.add(new Data(headers.get(i), values[i]));
                    }
                    sampleData.setDataList(dataList);


                    //If device id already present add sampledata to value
                    if (listData.containsKey(sampleData.getDeviceId())) {
                        List<SampleData> sampleData1 = listData.get(sampleData.getDeviceId());
                        sampleData1.add(sampleData);
                        listData.put(sampleData.getDeviceId(), sampleData1);
                    } else {
                        List<SampleData> sampleData1 = new ArrayList<>();
                        sampleData1.add(sampleData);
                        listData.put(sampleData.getDeviceId(), sampleData1);
                    }
                }

            }

            result =sampleDataProducer.dataProducer(listData);


        } catch (IOException i) {
        i.printStackTrace();//TODO need to log instead of printing on console.
        result = "Error with file";

        }
        return result;
    }
}
