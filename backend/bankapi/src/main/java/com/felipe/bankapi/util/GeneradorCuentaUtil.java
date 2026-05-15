package com.felipe.bankapi.util;

import java.security.SecureRandom;

import com.felipe.bankapi.entity.enums.TipoCuenta;

public class GeneradorCuentaUtil {
    private static final SecureRandom random = new SecureRandom();

    private GeneradorCuentaUtil() {
    }

    public static String generarNumeroCuenta(TipoCuenta tipoCuenta) {
        String prefijo;
        if (tipoCuenta == TipoCuenta.AHORROS) {
            prefijo = "53";
        } else if (tipoCuenta == TipoCuenta.CORRIENTE) {
            prefijo = "33";
        } else {
            throw new IllegalArgumentException("Tipo de cuenta inválido");
        }
        StringBuilder numeroCuenta = new StringBuilder(prefijo);
        while (numeroCuenta.length() < 10) {
            numeroCuenta.append(random.nextInt(10));
        }
        return numeroCuenta.toString();
    }
}