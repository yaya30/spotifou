package com.ayaco.exemple;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class QuickTests {
    @Test
    void testInstantComparison() {
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(1, ChronoUnit.SECONDS);

        assertTrue(instant1.isBefore(instant2));
        assertFalse(instant1.isAfter(instant2));
        assertEquals(1000, instant2.toEpochMilli() - instant1.toEpochMilli());
    }

    @Test
    void testInstantParsing() {
        Instant parsed = Instant.parse("2024-01-01T00:00:00Z");
        assertNotNull(parsed);
    }
}
