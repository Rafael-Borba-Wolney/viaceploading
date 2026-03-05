package com.rafael.util;

public class CepUtils {

    private CepUtils() {
    }

    public static String limparCep(String cep) {
        if (cep == null) {
            return "";
        }
        return cep.replaceAll("\\D", "");
    }

    public static boolean cepValido(String cep) {
        String cepLimpo = limparCep(cep);
        return cepLimpo.matches("\\d{8}");
    }
}