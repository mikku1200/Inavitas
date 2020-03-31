package com.aas;

import com.aas.jsonutilityimpl.CSVToJsonImpl;
import com.aas.Jsonutility.CSVToJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is entry point of this application.
 *
 */

public class Main {


    public static void main(String[] args) throws IOException {

        //E:\SampleData_.csv
        /**
         * InputStreamReader and BufferedReader is used for taking user Input.
         * User Input will be path of file.
         * When when application starts running it will instruct you to enter path of CSV file on the  console;
         */
        InputStreamReader in=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(in);
        System.out.println("Please enter file path eg.'E:\\SampleData_.csv'");
        String path=br.readLine();
        //creating Object of CSVTOJson to proceed further. This class will convert all csv file data into our entity object.
        CSVToJson csvToJson=new CSVToJsonImpl();
        csvToJson.createJson(path);
    }
}
