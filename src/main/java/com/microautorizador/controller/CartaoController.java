package com.microautorizador.controller;

import com.microautorizador.dto.CartaoDTO;
import com.microautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService service;

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable("numeroCartao") String numeroCartao ){
        return  service.getSaldo(numeroCartao);
    }

    @PostMapping
    public ResponseEntity<CartaoDTO> criarCartao(@RequestBody CartaoDTO cartaoDTO){
        return service.criarCartao(cartaoDTO);
    }
}
