package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class DataExtractor {
    // Do I need to initialize an ArrayList<BenchmarkObjects>?
    DataExtractor(){

    }

    public ArrayList<BenchmarkObjects> extractDataFromFile(JsonNode tryNode){
        ArrayList<BenchmarkObjects> file1_obj = new ArrayList();

        for(JsonNode nodeCreat: tryNode){
            JsonNode versionNode=nodeCreat.path("benchmark");
            JsonNode modeNode = nodeCreat.path("mode");
            JsonNode paramNode = nodeCreat.path("params");
            JsonNode primDataNode = nodeCreat.path("primaryMetric");
            JsonNode threadNode = nodeCreat.path("threads");
            BenchmarkObjects benchmarkObjects = new BenchmarkObjects();

            benchmarkObjects.setName(versionNode.asText());
            benchmarkObjects.setCommand(getCommand(versionNode.asText()));

            benchmarkObjects.setMode(modeNode.asText());

            benchmarkObjects.setThreadNum(threadNode.asText());

            if (!paramNode.isMissingNode()) {        // if "param" node is exist (if batch size exists)
                System.out.println("Trying out" + paramNode.path("batchSize").asText());

                benchmarkObjects.setBatchSize(paramNode.path("batchSize").asText());
                //System.out.println("firstName : " + threadNode.path("batchSize").asText());
            } else{
                benchmarkObjects.setBatchSize("-10");
            }
            // if primary metric exists (contains time & unit)
            if(!primDataNode.isMissingNode()){
                benchmarkObjects.setTime(primDataNode.path("score").asText());
                benchmarkObjects.setTimeUnit(primDataNode.path("scoreUnit").asText());
            }
            file1_obj.add(benchmarkObjects);
        }

        System.out.println("Checking the file 1 list" + file1_obj);
        //access the list
        for (BenchmarkObjects obj : file1_obj) {
            System.out.println("Accessing the time:"+ obj.getTime());
        }

        // file1_obj stores every JSON object from file 1, this is how you access it
        System.out.println("accessing1"+file1_obj.get(0));
        System.out.println("accessing2"+file1_obj.get(1));
        return file1_obj;
    }

    private String getCommand(String test) {
        int indexOfLastDot = test.lastIndexOf(".");
        test = test.substring(++indexOfLastDot);
        return test;
    }

    private String getShortNames(String test) {
        int indexOfDots = test.indexOf(".");
        indexOfDots = test.indexOf(".", ++indexOfDots);
        indexOfDots = test.indexOf(".", ++indexOfDots);
        test = test.substring(indexOfDots + 1);
        return test;
    }

}
