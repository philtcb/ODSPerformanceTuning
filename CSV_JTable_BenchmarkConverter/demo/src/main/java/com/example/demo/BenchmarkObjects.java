package com.example.demo;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BenchmarkObjects {
    String name;
    String command;
    String batchSize;
    String threadNum;
    String mode;
    String time;
    String timeUnit;
    String diff;
    Double avrgDiff;

    public BenchmarkObjects(){

    }

    public BenchmarkObjects(String name, String command, String batchSize, String diff, String timeUnit, Double avrgDiff){
        this.name = name;
        this.command = command;
        this.batchSize = batchSize;
        this.diff = diff;
        this.timeUnit = timeUnit;
        this.avrgDiff = avrgDiff;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCommand(String command){
        this.command = command;
    }
    public void setMode(String mode){
        this.mode = mode;
    }
    public void setBatchSize(String batchSize){
        this.batchSize = batchSize;
    }
    public void setThreadNum(String threadNum){
        this.threadNum = threadNum;
    }
    public void setTimeUnit(String timeUnit){
        this.timeUnit = timeUnit;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setTimeDiff(String diff){
        this.diff = diff;
    }
    public void setAvrgTimeDiff(Double avrgDiff){
        this.avrgDiff = avrgDiff;
    }

    public String getName(){
        return this.name;
    }
    public String getCommand(){
        return this.command;
    }
    public String getMode(){
        return this.mode;
    }
    public String getBatchSize(){
        return this.batchSize;
    }
    public String getThreadNum(){
        return this.threadNum;
    }
    public String getTimeUnit(){
        return this.timeUnit;
    }
    public String getTime(){
        return this.time;
    }
    public String getTimeDiff(){
        return this.diff;
    }
    public Double getAvrgTimeDiff(){
        return this.avrgDiff;
    }

    public ArrayList<String> dataForTable1(BenchmarkObjects benchmarkObjects){
        ArrayList<String> result = new ArrayList<>();
        result.add(benchmarkObjects.getName());
        result.add(benchmarkObjects.getCommand());
        result.add(benchmarkObjects.getMode());
        result.add(benchmarkObjects.getThreadNum());
        result.add(benchmarkObjects.getBatchSize());
        result.add(benchmarkObjects.getTime());
        result.add(benchmarkObjects.getTimeUnit());
        return result;
    }

    public ArrayList<String> dataForTable2(BenchmarkObjects benchmarkObjects){
        DecimalFormat format2Dec = new DecimalFormat("#.00");
        ArrayList<String> result = new ArrayList<>();
        result.add(benchmarkObjects.getName());
        result.add(benchmarkObjects.getCommand());
        result.add(benchmarkObjects.getBatchSize());
        result.add(benchmarkObjects.getTimeDiff());
        result.add(benchmarkObjects.getTimeUnit());
        result.add(format2Dec.format(benchmarkObjects.getAvrgTimeDiff()));
        return result;
    }





}
