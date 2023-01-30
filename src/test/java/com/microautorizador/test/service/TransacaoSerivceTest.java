package com.microautorizador.test.service;

import com.microautorizador.dto.CartaoDTO;
import com.microautorizador.dto.TransacaoRequest;
import com.microautorizador.entity.Cartao;
import com.microautorizador.exception.NotFoundException;
import com.microautorizador.exception.UnauthorizedException;
import com.microautorizador.repository.CartaoRepository;
import com.microautorizador.service.CartaoService;
import com.microautorizador.service.TransacaoService;
import com.microautorizador.test.mock.CartaoDTOMock;
import com.microautorizador.test.mock.CartaoMock;
import com.microautorizador.test.mock.TransacaoRequestMock;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoSerivceTest {

    @Mock
    CartaoService cartaoService;

    @InjectMocks
    TransacaoService service;

    @Mock
    CartaoRepository repository;

    CartaoDTO cartaoDTO;

    Optional<Cartao> cartaoOp;

    @Before
    public void init() {
        cartaoDTO = CartaoDTOMock.umCartaoComNumeroAleatorioDTO();
        cartaoService.criarCartao(cartaoDTO);
        cartaoOp = Optional.of(CartaoMock.converterCartaoDTOInCartaoComID(cartaoDTO));
    }

    @Test
    void criarTransacao_sucesso(){
        var cartaoDTO = CartaoDTOMock.umCartaoComNumeroAleatorioDTO();
        var cartaoOp = Optional.of(CartaoMock.converterCartaoDTOInCartaoComID(cartaoDTO));

        TransacaoRequest transacao = new TransacaoRequest(cartaoDTO.getNumeroCartao(),cartaoDTO.getSenha(),new BigDecimal(100.00));
        when(service.buscarCartao(transacao)).thenReturn(cartaoOp);
        when(cartaoService.pesquisarCartaoPorNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartaoOp);


        var result =    service.enviarTransacao(transacao);

        assert(result.getBody().contains("OK"));

    }

    @Test
    void criarTransacao_erro_sem_saldo(){
        var cartaoDTO = CartaoDTOMock.umCartaoComNumeroAleatorioDTO();
        var cartaoOp = Optional.of(CartaoMock.converterCartaoDTOInCartaoComID(cartaoDTO));

        TransacaoRequest transacao = new TransacaoRequest(cartaoDTO.getNumeroCartao(),cartaoDTO.getSenha(),new BigDecimal(700.00));
        when(service.buscarCartao(transacao)).thenReturn(cartaoOp);
        when(cartaoService.pesquisarCartaoPorNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartaoOp);

        var exception =  assertThrows(UnauthorizedException.class, () -> {
            service.enviarTransacao(transacao);
        });
        assert(exception.getMessage().contains("SALDO_INSUFICIENTE"));
    }

    @Test
    void criarTransacao_erro_senha_invalida(){
        var cartaoDTO = CartaoDTOMock.umCartaoComNumeroAleatorioDTO();
        var cartaoOp = Optional.of(CartaoMock.converterCartaoDTOInCartaoComID(cartaoDTO));

        TransacaoRequest transacao = new TransacaoRequest(cartaoDTO.getNumeroCartao(),"999999",new BigDecimal(100.00));
        when(service.buscarCartao(transacao)).thenReturn(cartaoOp);
        when(cartaoService.pesquisarCartaoPorNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartaoOp);

        var exception =  assertThrows(UnauthorizedException.class, () -> {
            service.enviarTransacao(transacao);
        });
        assert(exception.getMessage().contains("SENHA_INVALIDA"));
    }

    @Test
    void criarTransacao_erro_cartao_inexistente(){
        var cartaoDTO = CartaoDTOMock.umCartaoComNumeroAleatorioDTO();

        TransacaoRequest transacao = TransacaoRequestMock.umaRequisicaoDeTransacao(new BigDecimal(100.00), cartaoDTO.getNumeroCartao());
        var exception =  assertThrows(NotFoundException.class, () -> {
            service.enviarTransacao(transacao);
        });
        assert(exception.getMessage().contains("CARTAO_INEXISTENTE"));
    }
}
