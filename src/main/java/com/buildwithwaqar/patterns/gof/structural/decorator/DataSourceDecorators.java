package com.buildwithwaqar.patterns.gof.structural.decorator;

final class InMemoryDataSource implements DataSource {
    private String data = "";
    public String read() { return data; }
    public void write(String data) { this.data = data; }
}

abstract class DataSourceDecorator implements DataSource {
    protected final DataSource wrappee;
    protected DataSourceDecorator(DataSource wrappee) { this.wrappee = wrappee; }
    public String read() { return wrappee.read(); }
    public void write(String data) { wrappee.write(data); }
}

final class EncryptionDecorator extends DataSourceDecorator {
    EncryptionDecorator(DataSource wrappee) { super(wrappee); }

    @Override public void write(String data) {
        super.write(new StringBuilder(data).reverse().toString());
    }

    @Override public String read() {
        return new StringBuilder(super.read()).reverse().toString();
    }
}

/** Stackable decorator demo entry — compression over encryption over memory. */
public final class DataSourceDecorators {
    private DataSourceDecorators() {}

    public static DataSource encryptedCompressedMemory() {
        return new EncryptionDecorator(new CompressionDecorator(new InMemoryDataSource()));
    }
}

final class CompressionDecorator extends DataSourceDecorator {
    CompressionDecorator(DataSource wrappee) { super(wrappee); }

    @Override public void write(String data) {
        super.write("c:" + data.replace(" ", ""));
    }

    @Override public String read() {
        var s = super.read();
        return s.startsWith("c:") ? s.substring(2) : s;
    }
}
