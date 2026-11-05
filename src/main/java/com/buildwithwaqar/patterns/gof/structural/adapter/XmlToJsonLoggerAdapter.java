package com.buildwithwaqar.patterns.gof.structural.adapter;

interface JsonLogger {
    void logJson(String json);
}

final class LegacyXmlLogger {
    String last;
    void logXml(String xml) { last = xml; }
}

/** Object adapter — wraps adaptee, translates calls. */
public final class XmlToJsonLoggerAdapter implements JsonLogger {
    private final LegacyXmlLogger legacy = new LegacyXmlLogger();

    @Override
    public void logJson(String json) {
        legacy.logXml("<payload>" + json + "</payload>");
    }

    String lastXml() { return legacy.last; }
}
