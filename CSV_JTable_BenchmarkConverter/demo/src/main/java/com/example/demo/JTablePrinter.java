package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class JTablePrinter extends BenchmarkBuilder{
    DefaultTableModel model;
    DefaultTableModel model2;
    String pathname1, pathname2;

    public JTablePrinter(){

    }

    public JTablePrinter(String pathname1, String pathname2){
        this.pathname1 = pathname1;
        this.pathname2 = pathname2;
        model = new DefaultTableModel(0, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model2 = new DefaultTableModel(0, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }


    public DefaultTableModel getModel1(){
        return this.model;
    }
    public DefaultTableModel getModel2(){
        return this.model2;
    }

    @Override
    public void buildTable1(ArrayList<BenchmarkObjects> obj) {
        for (BenchmarkObjects benchmarkObjects : obj){
            String name = benchmarkObjects.getName();
            name = getShortNames(name);
            String command = getCommand(name);
            String modeName = benchmarkObjects.getMode();
            String threads = benchmarkObjects.getThreadNum();
            String batch = benchmarkObjects.getBatchSize();
            String time = benchmarkObjects.getTime();
            String unit = benchmarkObjects.getTimeUnit();

            // for jtable
            model.addRow(new Object[] {name, command, modeName, threads, batch, time, unit});
        }
    }

    @Override
    public void buildTable2(ArrayList<BenchmarkObjects> obj1, ArrayList<BenchmarkObjects> obj2) {
        DecimalFormat format2Dec = new DecimalFormat("#.00");
        ArrayList<BenchmarkObjects> forComparison = buildTable2_helper(obj1, obj2);
        for (BenchmarkObjects things : forComparison){
            model2.addRow(new Object[]
                    {things.getName(), getCommand(things.getName()), things.getBatchSize(),
                            things.getTimeDiff(), things.getTimeUnit(), "N/A"});
        }
        Double sumOfDiff = getSumOfDiff();
        double avgDiff = sumOfDiff / forComparison.size();
        model2.addRow(new Object[]{ "Summary", "", "",
                "", "", format2Dec.format(avgDiff)});

    }


    private void setJTable(String title_table1, String heading_table1_name, String[] columns_table1, boolean table2){
        System.setProperty("java.awt.headless", "false");
        JTable table1 = new JTable();


        final JFrame frame_table1 = new JFrame(title_table1);
        JScrollPane scrollPane_table1 = new JScrollPane(table1);
        table1.setFillsViewportHeight(true);
        JLabel heading_table1 = new JLabel(heading_table1_name);
        heading_table1.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));
        setFrame(frame_table1, heading_table1, scrollPane_table1);

        if (!table2){
            this.model.setColumnIdentifiers(columns_table1);
            table1.setModel(model);
        } else {
            this.model2.setColumnIdentifiers(columns_table1);
            table1.setModel(model2);
        }

    }


    private void setFrame(JFrame frame, JLabel lblHeading, JScrollPane scrollPane){
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(lblHeading, BorderLayout.PAGE_START);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 200);
        frame.setVisible(true);
    }

    public String getCommand(String test) {
        int indexOfLastDot = test.lastIndexOf(".");
        test = test.substring(++indexOfLastDot);
        return test;
    }

    public String getShortNames(String test) {
        int indexOfDots = test.indexOf(".");
        indexOfDots = test.indexOf(".", ++indexOfDots);
        indexOfDots = test.indexOf(".", ++indexOfDots);
        test = test.substring(indexOfDots + 1);
        return test;
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

        String title_table = "JMH Benchmark (For all methods)";
        String title_table2 = "JMH Benchmark (For Comparison)";


        String heading_table_name = "Benchmark Report (server folder)";
        String heading_table_name2 = "Benchmark Comparison Report (server folder)";


        String[] columns_table1 = {"Name", "Command", "Mode","#Thread", "Batch", "Time", "Time Units"};
        String[] columns_table2 = {"Name", "Command", "Batch", "Time Diff", "Time Units", "Average Diff"};

        setJTable(title_table, heading_table_name, columns_table1, false);

        buildTable1(obj1);
        buildTable1(obj2);

        setJTable(title_table2, heading_table_name2, columns_table2, true);

        buildTable2(obj1, obj2);


    }



}
