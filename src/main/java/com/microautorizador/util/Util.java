package com.microautorizador.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
