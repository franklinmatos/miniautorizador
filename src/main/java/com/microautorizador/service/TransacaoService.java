package com.microautorizador.service;

import com.microautorizador.dto.TransacaoRequest;
import com.microautorizador.entity.Cartao;
import com.microautorizador.exception.NotFoundException;
import com.microautorizador.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransacaoService {

    private static final String MSG_SUCESSO = "OK";

    @Autowired
    CartaoService cartaoService;

    public ResponseEntity<String> enviarTransacao(TransacaoRequest transacao) {
        return new ResponseEntity<String>(criarTransacao(transacao), HttpStatus.CREATED);
    }

    private String criarTransacao(TransacaoRequest transacao) {
        Optional<Cartao> cardList = buscarCartao(transacao);
        atualizarCartao(cardList, transacao);
        return MSG_SUCESSO;
    }

    public Optional<Cartao> buscarCartao(TransacaoRequest transacao) {
        return cartaoService.pesquisarCartaoPorNumeroCartao(transacao.getNumeroCartao());
    }

    private void verificarCartao(Optional<Cartao> cartaoOp,TransacaoRequest transacao) {
        cartaoOp.stream().findFirst().orElseThrow(() -> new NotFoundException("CARTAO_INEXISTENTE"));
    }

    private void verificarSenha(Optional<Cartao> cartaoOp, TransacaoRequest transacao) {
        cartaoOp.stream().findFirst()
                .filter((element) -> element.getSenhaCartao().toLowerCase().equals(transacao.getSenhaCartao().toLowerCase()) )
                .orElseThrow( () -> new UnauthorizedException("SENHA_INVALIDA") );
    }

    private Cartao verificarSaldo(Optional<Cartao> cartaoOp, TransacaoRequest transacao) {
        return cartaoOp.stream().findFirst()
                .filter((element) -> element.getSenhaCartao().toLowerCase().equals(transacao.getSenhaCartao().toLowerCase()) )
                .filter((key) -> key.getValor().compareTo(transacao.getValor()) >= 0 )
                .orElseThrow( () -> new UnauthorizedException("SALDO_INSUFICIENTE") );
    }

    private void atualizarCartao(Optional<Cartao> cartaoOP, TransacaoRequest transacao) {
        verificarCartao(cartaoOP, transacao);
        verificarSenha(cartaoOP, transacao);
        Cartao card = verificarSaldo(cartaoOP, transacao);
        card.setValor(card.getValor().subtract(transacao.getValor()));
        cartaoService.salvarCartao(card);
    }
}
