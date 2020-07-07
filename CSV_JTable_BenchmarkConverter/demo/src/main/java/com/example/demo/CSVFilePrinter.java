package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVFilePrinter extends BenchmarkBuilder {
    ArrayList<BenchmarkObjects> export;
    ArrayList<BenchmarkObjects> export2;
    String pathname1, pathname2, exportTo_table1, exportTo_table2;

    CSVFilePrinter(String pathname1, String pathname2, String exportTo_table1, String exportTo_table2){
        this.pathname1 = pathname1;
        this.pathname2 = pathname2;
        this.exportTo_table1 = exportTo_table1;
        this.exportTo_table2 = exportTo_table2;
        this.export = new ArrayList<>();
        this.export2 = new ArrayList<>();
    }
    @Override
    public void buildTable1(ArrayList<BenchmarkObjects> obj) {
        // for csv
        this.export.addAll(obj);

//        for (BenchmarkObjects benchmarkObjects : obj){
//            // for csv
//            this.export.add(benchmarkObjects);
//        }
    }

    @Override
    public void buildTable2(ArrayList<BenchmarkObjects> obj1, ArrayList<BenchmarkObjects> obj2) {
        ArrayList<BenchmarkObjects> forComparison = buildTable2_helper(obj1, obj2);
        for (BenchmarkObjects things : forComparison){
            BenchmarkObjects added = new BenchmarkObjects(things.getName(), getCommand(things.getName()),
                    things.getBatchSize(), things.getTimeDiff(), things.getTimeUnit(), 0.0);
            export2.add(added);
        }
        double sumOfDiff = getSumOfDiff();
        double avgDiff = sumOfDiff / forComparison.size();
        BenchmarkObjects forAverage = new BenchmarkObjects();
        forAverage.setName("Summary");
        forAverage.setAvrgTimeDiff(avgDiff);
        export2.add(forAverage);
    }

    @Override
    public void export() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Use your own address for the JSON file results
        JsonNode tryNode = mapper.readTree(new File(pathname1));
        JsonNode tryNode2 = mapper.readTree(new File(pathname2));

        DataExtractor dataExtractor = new DataExtractor();
        ArrayList<BenchmarkObjects> obj1 = dataExtractor.extractDataFromFile(tryNode);
        ArrayList<BenchmarkObjects> obj2 = dataExtractor.extractDataFromFile(tryNode2);


        String[] columns_table1 = {"Name", "Command", "Mode", "#Thread", "Batch", "Time", "Time_Units"};
        String[] columns_table2 = {"Name", "Command", "Batch", "Time_Diff", "Time_Units", "Average_Diff"};

        buildTable1(obj1);
        buildTable1(obj2);

        exportToCSV(exportTo_table1,
                columns_table1, export, true);

        buildTable2(obj1, obj2);


        exportToCSV(exportTo_table2,
                columns_table2, export2, false);




    }

    public boolean exportToCSV(String pathToExportTo,
                                      String[] columns, ArrayList<BenchmarkObjects> export, boolean table1) {

        try {
            // file 1: "Name", "Command", "#Thread", "Mode", "Time", "Time Unit", "Batch Size"
            // file 2: "Name", "Mode","Batch ","diff on batch", "Units", "Avg diff"
            FileWriter csv = new FileWriter(new File(pathToExportTo));


            for(String name: columns){
                csv.write(name + ",");
            }
            csv.write("\n");


            for (int i = 0; i< export.size(); i++) {
                BenchmarkObjects element = export.get(i);

                ArrayList<String> elementData;
                if (table1){
                    elementData = element.dataForTable1(element);
                }else{
                    elementData = element.dataForTable2(element);
                }
                for(int j = 0; j< elementData.size(); j++){

                    csv.write(elementData.get(j) + ",");
                }
                csv.write("\n");
            }

            csv.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



}
