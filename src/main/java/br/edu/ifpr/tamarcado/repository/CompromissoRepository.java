package br.edu.ifpr.tamarcado.repository;

import br.edu.ifpr.tamarcado.model.Compromisso;
import java.time.LocalDateTime;
import java.util.List;

public interface CompromissoRepository {
    boolean existeConflitoDeHorario(Long usuarioId, LocalDateTime dataHora);
    Compromisso salvar(Compromisso compromisso);
}