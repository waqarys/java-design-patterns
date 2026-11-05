package com.buildwithwaqar.patterns.gof.structural.adapter.problem;

/**
 * Third-party / legacy SDK we cannot change.
 * Speaks XML SOAP-style strings and uses dollars (not cents).
 */
public final class LegacySoapGateway {
    public String submitXml(String soapEnvelope) {
        // Extremely simplified parser for the demo
        if (!soapEnvelope.contains("<Amount>")) {
            return "<Fault>missing amount</Fault>";
        }
        int start = soapEnvelope.indexOf("<Amount>") + 8;
        int end = soapEnvelope.indexOf("</Amount>");
        double dollars = Double.parseDouble(soapEnvelope.substring(start, end).trim());
        if (dollars <= 0) {
            return "<Fault>non-positive</Fault>";
        }
        String cust = "unknown";
        if (soapEnvelope.contains("<Customer>")) {
            int cs = soapEnvelope.indexOf("<Customer>") + 10;
            int ce = soapEnvelope.indexOf("</Customer>");
            cust = soapEnvelope.substring(cs, ce).trim();
        }
        return "<Ack><Txn>SOAP-" + cust + "-" + (int) (dollars * 100) + "</Txn><Status>OK</Status></Ack>";
    }
}
