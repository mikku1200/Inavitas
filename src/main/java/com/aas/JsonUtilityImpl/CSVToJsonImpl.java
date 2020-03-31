package com.aas.jsonutilityimpl;

import com.aas.entity.Data;
import com.aas.entity.SampleData;
import com.aas.Jsonutility.CSVToJson;
import com.aas.kafkaproducer.SampleDataProducer;
import com.aas.kafkaproducerimpl.SampleDataProducerImpl;
import com.aas.sampledataexception.FileFormatException;
import com.aas.sampledataexception.SampleDataFileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class is to convert a csv file into Object of type Sampledata.
 * Inside this createJson() method will accept one argument 1. Path of CSV file
 */

public class CSVToJsonImpl implements CSVToJson {
    @Override
    public void createJson(String path) throws FileFormatException, SampleDataFileNotFoundException {

        SampleDataProducer sampleDataProducer = new SampleDataProducerImpl();
        String line;
        List<String> headers = new ArrayList();
        int flag = 0;

        // to store all data with same device Id into key value pair. device id will be key.
        Map<Integer, List<SampleData>> listData = new HashMap<Integer, List<SampleData>>();

        //here we are checking for file extension. If file extension is not correct throwing custom exception "FileFormatException"
        if(!path.endsWith(".csv"))
        {
            throw new FileFormatException("File path doesn't contain CSV file");
        }

        //Storing all file data into buffered reader
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            //Loop for traversing line by line csv file.
            while ((line = br.readLine()) != null) {

                SampleData sampleData = new SampleData();
                //Check for headers as first line is header in csv file.
                if (flag == 0) {
                    headers = Arrays.asList(line.split(","));
                    flag = 1;
                }
                //Traversing all the lines except headers
                else {
                    String[] values = line.split(",");
                    //Setting all data to sample data object
                    sampleData.setDeviceId(Integer.parseInt(values[0]));
                    sampleData.setDateTime(values[1]);
                    List<Data> dataList = new ArrayList<>();

                    // loop to create data list skipping 1st and 2nd as it is device id and datetime which is not in data list.
                    for (int i = 2; i < values.length; i++) {
                        if (values[i] != null && !values[i].equals("")) {
                            dataList.add(new Data(headers.get(i), values[i]));
                        }
                    }
                    sampleData.setDataList(dataList);

                    //Adding sample data to listData map object where all sample data is stored bases on deviceId.
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
            //calling method dataProducer here we will iterate through the list of object and push to kafka
            sampleDataProducer.dataProducer(listData);


        } catch (IOException i) {
            i.printStackTrace();
            throw new SampleDataFileNotFoundException("File not found");
        }
    }
}
