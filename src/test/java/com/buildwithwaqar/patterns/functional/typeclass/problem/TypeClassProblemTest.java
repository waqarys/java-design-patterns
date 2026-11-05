package com.buildwithwaqar.patterns.functional.typeclass.problem;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TypeClassProblemTest {
    @Test void jsonIncludesCurrencyAndItems() {
        var o = new TypeClasses.Order("o1", List.of(
                new TypeClasses.LineItem("A", 1, new TypeClasses.Money("USD", 100))));
        String json = TypeClasses.jsonOrder.encode(o);
        assertTrue(json.contains("\"currency\":\"USD\""));
        assertTrue(json.contains("\"sku\":\"A\""));
    }

    @Test void eqDetectsDifference() {
        var a = new TypeClasses.Money("USD", 1);
        var b = new TypeClasses.Money("USD", 2);
        assertFalse(TypeClasses.eqMoney.eqv(a, b));
    }

    @Test void naiveOmitsCurrency() {
        assertFalse(NaiveJson.moneyJson(new TypeClasses.Money("EUR", 5)).contains("EUR"));
    }
}
