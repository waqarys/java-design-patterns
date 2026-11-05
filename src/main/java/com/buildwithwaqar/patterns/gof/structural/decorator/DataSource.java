package com.buildwithwaqar.patterns.gof.structural.decorator;

public interface DataSource {
    String read();
    void write(String data);
}
