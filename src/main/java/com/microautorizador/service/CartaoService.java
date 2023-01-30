package com.microautorizador.service;

import com.microautorizador.dto.CartaoDTO;
import com.microautorizador.entity.Cartao;
import com.microautorizador.exception.NotFoundException;
import com.microautorizador.exception.UnprocessableEntityException;
import com.microautorizador.repository.CartaoRepository;
import com.microautorizador.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartaoService {

    private static final int SALDO_INCIAL_CARTAO = 500;

    @Autowired
    CartaoRepository repository;

    public Cartao pesquisarCartao(String numeroCartao) {
        return new Cartao();
    }

    public Optional<Cartao> pesquisarCartaoPorNumeroCartao(String numeroCartao) {
        return repository.pesquisarPorNumeroCartao(numeroCartao);
    }

    public ResponseEntity<BigDecimal> getSaldo(String numeroCartao) {
        return new ResponseEntity<BigDecimal>(new BigDecimal(500), HttpStatus.OK);
    }

    public Cartao pesqusiarCartao(String cardNumber) {
        var cartaoOptional = pesquisarCartaoPorNumeroCartao(cardNumber);
        var cartao = cartaoOptional.stream().findFirst().orElseThrow(() -> new NotFoundException());
        return cartao;
    }

    public Cartao salvarCartao(Cartao cartao){
        return repository.save(cartao);
    }

    public ResponseEntity<CartaoDTO> criarCartao(CartaoDTO cartaoDTO) {

        var newCardOp = pesquisarCartaoPorNumeroCartao(cartaoDTO.getNumeroCartao()).stream().findFirst();
        newCardOp.ifPresentOrElse((card) ->{
                    throw new UnprocessableEntityException( Util.convertObjectToString(cartaoDTO));
                },
                () -> {
                    instanciarCartao(cartaoDTO);

                });

        return new ResponseEntity<CartaoDTO>(cartaoDTO, HttpStatus.CREATED);
    }

    private Cartao instanciarCartao(CartaoDTO cartaoDTO){
        return salvarCartao(Cartao.builder()
                .valor(new BigDecimal(SALDO_INCIAL_CARTAO))
                .numeroCartao(cartaoDTO.getNumeroCartao())
                . senhaCartao(cartaoDTO.getSenha().toLowerCase()).build());
    }
}