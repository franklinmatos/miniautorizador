package com.microautorizador.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;

public class Util {

    public static String convertObjectToString ( Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            return mapper.writeValueAsString(obj);

        } catch (JsonProcessingException e) {
            System.out.println("Ocorreu um problema ao tentar converter um objeto");
            e.printStackTrace();
        }
        return null;

    }

    public static String gerarNumeroCartao(){
        Random random = new Random();
        int numero = random.nextInt(123456789);
        return String.valueOf(numero);
    }
}
