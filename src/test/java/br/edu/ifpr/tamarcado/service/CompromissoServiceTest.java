package br.edu.ifpr.tamarcado.service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.ifpr.tamarcado.model.Compromisso;
import br.edu.ifpr.tamarcado.repository.CompromissoRepository;

@ExtendWith(MockitoExtension.class)
public class CompromissoServiceTest {

    @Mock
    private CompromissoRepository repository;

    @InjectMocks
    private CompromissoService service;

    private LocalDateTime dataFutura;

    @BeforeEach
    void setUp() {
        dataFutura = LocalDateTime.now().plusDays(1);
    }

    @Test
    @DisplayName("CT01 - Caixa-Branca: Deve validar caminho de erro para data no passado")
    void testCriarCompromissoDataPassada() {
        LocalDateTime dataPassada = LocalDateTime.now().minusDays(1);

        assertThrows(IllegalArgumentException.class, () -> {
            new Compromisso(1L, 100L, "Dentista", dataPassada, "Saúde");
        });
    }

    @Test
    @DisplayName("CT02 - Caixa-Branca: Deve validar caminho de erro para título em branco")
    void testCriarCompromissoTituloInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Compromisso(1L, 100L, "   ", dataFutura, "Saúde");
        });
    }

    @Test
    @DisplayName("CT03 - Caixa-Branca: Deve validar desvio condicional de conflito de horário")
    void testAgendarCompromissoComConflito() {
        Compromisso compromisso = new Compromisso(1L, 100L, "Reunião de Projeto", dataFutura, "Trabalho");

        // Mockito simulando a condição de borda: Existe conflito
        when(repository.existeConflitoDeHorario(100L, dataFutura)).thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            service.agendarCompromisso(compromisso);
        });

        assertEquals("Aviso: Conflito de horário detectado para este usuário!", exception.getMessage());
        verify(repository, never()).salvar(any());
    }

    @Test
    @DisplayName("CT04 - Caixa-Branca: Deve validar caminho de sucesso do agendamento")
    void testAgendarCompromissoComSucesso() {
        Compromisso compromisso = new Compromisso(1L, 100L, "Aula de POO", dataFutura, "Estudo");

        // Mockito simulando ausência de conflito
        when(repository.existeConflitoDeHorario(100L, dataFutura)).thenReturn(false);
        when(repository.salvar(compromisso)).thenReturn(compromisso);

        Compromisso resultado = service.agendarCompromisso(compromisso);

        assertNotNull(resultado);
        assertEquals("Aula de POO", resultado.getTitulo());
        verify(repository, times(1)).salvar(compromisso);
    }
}