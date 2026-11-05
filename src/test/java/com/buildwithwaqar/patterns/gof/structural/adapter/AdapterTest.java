package com.buildwithwaqar.patterns.gof.structural.adapter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdapterTest {
    @Test void adaptsJsonCallsToXmlLogger() {
        var adapter = new XmlToJsonLoggerAdapter();
        JsonLogger logger = adapter;
        logger.logJson("{\"ok\":true}");
        assertEquals("<payload>{\"ok\":true}</payload>", adapter.lastXml());
    }
}
