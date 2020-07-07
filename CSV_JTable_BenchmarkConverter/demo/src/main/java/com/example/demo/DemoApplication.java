package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import jdk.nashorn.internal.parser.JSONParser;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class DemoApplication {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(DemoApplication.class, args);

		String pathname1 = "//Users//tongchuangbo//Desktop//test//correct_batch_size//jmh-result.json";
		String pathname2 = "//Users//tongchuangbo//Desktop//test//correct_batch_size//jmh-result2.json";
		String exportTo_table1 = "//Users//tongchuangbo//Desktop//testOutput.csv";
		String exportTo_table2 = "//Users//tongchuangbo//Desktop//testOutput_comparison.csv";

		BenchmarkPrinter jTablePrinter = new JTablePrinter(pathname1, pathname2);
		BenchmarkPrinter csvFilePrinter = new CSVFilePrinter(pathname1, pathname2, exportTo_table1, exportTo_table2);

		jTablePrinter.export();
		csvFilePrinter.export();

	}


}
