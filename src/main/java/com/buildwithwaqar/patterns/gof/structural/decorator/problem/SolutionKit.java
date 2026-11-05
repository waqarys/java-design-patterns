package com.buildwithwaqar.patterns.gof.structural.decorator.problem;

import java.util.*;

interface DataSource { String read(); void write(String data); }
final class InMemoryDataSource implements DataSource {
  private String data=""; public String read(){return data;} public void write(String d){data=d;}
}
abstract class DataSourceDecorator implements DataSource {
  protected final DataSource wrappee; protected DataSourceDecorator(DataSource w){ wrappee=w; }
  public String read(){ return wrappee.read(); } public void write(String d){ wrappee.write(d); }
}
final class EncryptionDecorator extends DataSourceDecorator {
  public EncryptionDecorator(DataSource w){ super(w); }
  public void write(String d){ wrappee.write("enc("+d+")"); }
  public String read(){ String s=wrappee.read(); return s.startsWith("enc(")? s.substring(4,s.length()-1):s; }
}
final class CompressionDecorator extends DataSourceDecorator {
  public CompressionDecorator(DataSource w){ super(w); }
  public void write(String d){ wrappee.write("zip("+d+")"); }
  public String read(){ String s=wrappee.read(); return s.startsWith("zip(")? s.substring(4,s.length()-1):s; }
}
