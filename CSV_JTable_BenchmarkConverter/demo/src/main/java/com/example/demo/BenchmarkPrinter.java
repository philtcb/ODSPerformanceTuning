package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;

public interface BenchmarkPrinter {
    void buildTable1(ArrayList<BenchmarkObjects> obj);
    void buildTable2(ArrayList<BenchmarkObjects> obj1, ArrayList<BenchmarkObjects> obj2);
    void export() throws IOException;
}
