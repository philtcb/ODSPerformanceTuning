package com.example.demo;

import java.util.ArrayList;

public abstract class BenchmarkBuilder implements BenchmarkPrinter{
    private double sumOfDiff = 0.0;

    public ArrayList<BenchmarkObjects> buildTable2_helper(ArrayList<BenchmarkObjects> obj1, ArrayList<BenchmarkObjects> obj2) {
        ArrayList<BenchmarkObjects> forComparison = new ArrayList<>();
        for (BenchmarkObjects map1: obj1){
            for (BenchmarkObjects map2 : obj2){
                if (map1.getName().equals(map2.getName())){
                    if (map1.getBatchSize().equals(map2.getBatchSize())){
                        double diff = Math.abs(Double.parseDouble(map1.getTime())-Double.parseDouble(map2.getTime()));

                        sumOfDiff += diff;

                        BenchmarkObjects jsonObject = new BenchmarkObjects();
                        jsonObject.setName(map1.getName());
                        jsonObject.setCommand(getCommand(map1.getName()));
                        jsonObject.setMode(map1.getMode());
                        jsonObject.setBatchSize(map1.getBatchSize());
                        jsonObject.setThreadNum(map1.getThreadNum());
                        jsonObject.setTimeUnit(map1.getTimeUnit());
                        jsonObject.setTimeDiff(Double.toString(diff));
                        jsonObject.setAvrgTimeDiff(0.0);
                        forComparison.add(jsonObject);
                    }
                }
            }
        }
        return forComparison;
    }

    public double getSumOfDiff(){
        return sumOfDiff;
    }

    public String getCommand(String test) {
        int indexOfLastDot = test.lastIndexOf(".");
        test = test.substring(++indexOfLastDot);
        return test;
    }

}
