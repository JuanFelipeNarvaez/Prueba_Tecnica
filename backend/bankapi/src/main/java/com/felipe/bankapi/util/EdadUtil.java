package com.felipe.bankapi.util;

import java.time.LocalDate;
import java.time.Period;

public class EdadUtil {

    private EdadUtil() {
    }

    public static boolean esMayorDeEdad(LocalDate fechaNacimiento) {

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();

        return edad >= 18;
    }
}