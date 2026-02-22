package com.ayaco.exemple;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ScriptTests {
    public static void main(String[] args) {
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(1, ChronoUnit.SECONDS);

        System.out.println("instant1 : " + instant1);
        System.out.println("instant2 : " + instant2);
        System.out.println("instant1.isBefore(instant2) : " + instant1.isBefore(instant2));
    }
}