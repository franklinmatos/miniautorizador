package com.microautorizador.test.mock;

import com.microautorizador.dto.TransacaoRequest;
import com.microautorizador.util.Util;

import java.math.BigDecimal;

public class TransacaoRequestMock {

    public static TransacaoRequest umaRequisicaoDeTransacao(BigDecimal valor, String numeroCartao){
        return TransacaoRequest.builder()
                .numeroCartao(numeroCartao)
                .valor(valor)
                .build();
    }
}
