package br.com.gabriel.desafio_celcoin.utils;

public abstract class StringUtils {
    
    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
