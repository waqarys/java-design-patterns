package com.buildwithwaqar.patterns.gof.structural.decorator;
import com.buildwithwaqar.patterns.gof.structural.decorator.*;
/** Problem: store secrets with compression then encryption without changing client code. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Decorator problem: compress+encrypt store ===");
    DataSource ds = DataSourceDecorators.encryptedCompressedMemory();
    ds.write("token = super secret");
    System.out.println("roundTrip=" + ds.read());
  }
}
