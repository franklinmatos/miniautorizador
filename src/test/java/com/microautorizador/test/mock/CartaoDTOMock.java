package com.microautorizador.test.mock;

import com.microautorizador.dto.CartaoDTO;

import java.util.Random;

public class CartaoDTOMock {


    public static final String NUMERO_CARTAO = "999999887";
    public static final String SENHA = "123456";

    /**
     * numero - 999999887
     * senha  - 123456
     * @return
     */
    public static CartaoDTO umCartaoDTO(){
        return CartaoDTO.builder()
                .numeroCartao(NUMERO_CARTAO)
                .senha(SENHA)
                .build();
    }

    /**
     * numero - randon
     * senha  - 123456
     * @return
     */
    public static CartaoDTO umCartaoComNumeroAleatorioDTO(){
        return CartaoDTO.builder()
                .numeroCartao(gerarNumeroCartao())
                .senha(SENHA)
                .build();
    }

    public static String gerarNumeroCartao(){
        Random random = new Random();
        int numero = random.nextInt(123456789);
        return String.valueOf(numero);
    }
}
