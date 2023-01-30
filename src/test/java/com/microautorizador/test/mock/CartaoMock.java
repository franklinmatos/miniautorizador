package com.microautorizador.test.mock;

import com.microautorizador.dto.CartaoDTO;
import com.microautorizador.entity.Cartao;
import com.microautorizador.util.Util;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class CartaoMock {

    /**
     * numeroCartao - randon
     * senhaCartao - 123456
     * saldo - R$ 500.00
     * @return
     */
    public static Cartao umCartaoInicializado(){
        return Cartao.builder()
                .numeroCartao(Util.gerarNumeroCartao())
                .senhaCartao("123456")
                .valor(new BigDecimal(500.00)).build();
    }
    /**
     * numeroCartao - 123456788
     * senhaCartao - 123456
     * saldo - R$ 500.00
     * @return
     */
    public static Cartao umCartaoInicializadoComNumeroDiferente(){
        return Cartao.builder()
                .numeroCartao("123456788")
                .senhaCartao("123456")
                .valor(new BigDecimal(500.00)).build();
    }


    /**
     * numeroCartao - randon
     * senhaCartao - 123456
     * saldo - R$ 0.00
     * @return
     */
    public static Cartao umCartaoSemSaldo(){
        return Cartao.builder()
                .id(UUID.randomUUID())
                .numeroCartao(Util.gerarNumeroCartao())
                .senhaCartao("123456")
                .valor(new BigDecimal(0.00)).build();
    }

    /**
     * numeroCartao - random
     * senhaCartao - 123456
     * saldo - R$ 300.00
     * @return
     */
    public static Cartao umCartaoComSaldoUtilizadoParcialmente(){
        return Cartao.builder()
                .id(UUID.randomUUID())
                .numeroCartao(Util.gerarNumeroCartao())
                .senhaCartao("123456")
                .valor(new BigDecimal(300.00)).build();
    }

    public static Cartao converterCartaoDTOInCartaoComID(CartaoDTO dto){
        return Cartao.builder()
                .id(UUID.randomUUID())
                .numeroCartao(dto.getNumeroCartao())
                .senhaCartao(dto.getSenha())
                .valor(new BigDecimal(500.00)).build();
    }

    public static Cartao converterCartaoDTOInCartaoSemID(CartaoDTO dto){
        return Cartao.builder()
                .numeroCartao(dto.getNumeroCartao())
                .senhaCartao(dto.getSenha())
                .valor(new BigDecimal(500.00)).build();
    }


}
