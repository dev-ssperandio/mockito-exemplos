package me.dio.mockito.exemplos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class CadastrarPessoaTeste {

    @Mock
    private ApiDosCorreios apiDosCorreios;

    @InjectMocks
    private CadastrarPessoa cadastrarPessoa;

    @Test
    void validarDadosDeCadastro() {
        DadosLocalizacao dadosLocalizacao = new DadosLocalizacao("SP", "Jundiaí", "Nome da Rua", "Casa", "Nome do Bairro");
        Mockito.when(apiDosCorreios.buscaDadosComBaseNoCep(anyString())).thenReturn(dadosLocalizacao);
        Pessoa pessoa = cadastrarPessoa.cadastrarPessoa("Sidney", "123.456.789/00", LocalDate.now(), "12.345-67");

        assertEquals("Sidney", pessoa.getNome());
        assertEquals("123.456.789/00", pessoa.getDocumento());
        assertEquals("SP", pessoa.getEndereco().getUf());
        assertEquals("Casa", pessoa.getEndereco().getComplemento());
    }

    @Test
    void lancarExeptionQuandoChamarApiDosCorreios() {
        doThrow(IllegalArgumentException.class)
                        .when(apiDosCorreios)
                                .buscaDadosComBaseNoCep(anyString());

        Assertions.assertThrows(IllegalArgumentException.class, () -> cadastrarPessoa.cadastrarPessoa("Sidney", "123.456.789/00", LocalDate.now(), "12.345-67"));
    }
}
