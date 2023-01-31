package com.microautorizador.test.service;

import com.microautorizador.dto.CartaoDTO;
import com.microautorizador.entity.Cartao;
import com.microautorizador.exception.NotFoundException;
import com.microautorizador.exception.UnprocessableEntityException;
import com.microautorizador.repository.CartaoRepository;
import com.microautorizador.service.CartaoService;
import com.microautorizador.test.mock.CartaoDTOMock;
import com.microautorizador.test.mock.CartaoMock;
import io.micrometer.common.util.StringUtils;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceTest {

    public static final String NUMERO_CARTAO = "123456789";
    public static final String NUMERO_CARTAO_ERRO = "123456788";
    @InjectMocks
    CartaoService service;

    @Mock
    CartaoRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void pesquisarNumeroCartao_sucesso() {
        var cartaoSalvo = Optional.of(mock(Cartao.class));
        cartaoSalvo = Optional.of(CartaoMock.umCartaoInicializado());
        when(service.pesquisarCartaoPorNumeroCartao(NUMERO_CARTAO)).thenReturn(cartaoSalvo);
        var cartaoPesquisado = service.pesquisarCartaoPorNumeroCartao(NUMERO_CARTAO);
        Assertions.assertEquals(cartaoPesquisado, cartaoSalvo);

    }

    @Test
    void pesquisarNumeroCartao_erro_notFoundException() {

        var exception = assertThrows(NotFoundException.class, () -> {
            service.pesqusiarCartao(NUMERO_CARTAO_ERRO);
        });

        assert(exception.getClass().getName().contains(("NotFoundException")));

    }

    @Test
    void criarCartaoNovo_sucesso() {
        var cartaoDTO = CartaoDTOMock.umCartaoDTO();
        var cartaoCriado = service.criarCartao(cartaoDTO);

        assert(cartaoCriado.getBody().getNumeroCartao().equals(cartaoDTO.getNumeroCartao()));
        assert(cartaoCriado.getBody().getSenha().equals(cartaoDTO.getSenha()));
    }

    @Test
    void criarCartaoNovo_erro() {
        var cartaoDTO = CartaoDTOMock.umCartaoDTO();
        var cartaoNovo = CartaoMock.converterCartaoDTOInCartaoComID(cartaoDTO);
        when(repository.findByNumeroCartao(any())).thenReturn(Optional.of(cartaoNovo));
        var response = new ResponseEntity<CartaoDTO>(cartaoDTO, HttpStatus.CREATED);
        var exception =  assertThrows(UnprocessableEntityException.class, () -> {
            service.criarCartao(cartaoDTO);
        });
        assert(exception.getMessage().contains(cartaoDTO.getNumeroCartao()));

    }

    @Test
    void salvarCartaoNovo_sucesso() {
        var cartaoDTO = CartaoDTOMock.umCartaoComNumeroAleatorioDTO();
        var cartaoSa = CartaoMock.converterCartaoDTOInCartaoComID(cartaoDTO);
        when(repository.save(any())).thenReturn(cartaoSa);

        var cartao = service.salvarCartao(CartaoMock.converterCartaoDTOInCartaoSemID(cartaoDTO));
        assert(cartao.getNumeroCartao().equals(cartaoDTO.getNumeroCartao()));
        assert(cartao.getSenhaCartao().equals(cartaoDTO.getSenha()));
        assert(StringUtils.isNotEmpty(cartao.getId().toString()));
        assert(cartao.getValor().equals(new BigDecimal(500)));
    }
}
