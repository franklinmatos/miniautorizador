package com.microautorizador.test.mock;

import com.microautorizador.dto.CartaoDTO;
import com.microautorizador.util.Util;

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
                .numeroCartao(Util.gerarNumeroCartao())
                .senha(SENHA)
                .build();
    }


}
