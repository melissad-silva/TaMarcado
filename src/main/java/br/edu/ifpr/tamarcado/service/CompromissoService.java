package br.edu.ifpr.tamarcado.service;

import br.edu.ifpr.tamarcado.model.Compromisso;
import br.edu.ifpr.tamarcado.repository.CompromissoRepository;

public class CompromissoService {
    private final CompromissoRepository repository;

    public CompromissoService(CompromissoRepository repository) {
        this.repository = repository;
    }

    public Compromisso agendarCompromisso(Compromisso compromisso) {
        // Teste de Caminho: Verifica se há conflito de horário no repositório
        if (repository.existeConflitoDeHorario(compromisso.getUsuarioId(), compromisso.getDataHora())) {
            throw new IllegalStateException("Aviso: Conflito de horário detectado para este usuário!");
        }
        return repository.salvar(compromisso);
    }
}