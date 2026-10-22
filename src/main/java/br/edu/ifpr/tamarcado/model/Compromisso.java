package br.edu.ifpr.tamarcado.model;

import java.time.LocalDateTime;

public class Compromisso {
    private Long id;
    private Long usuarioId;
    private String titulo;
    private LocalDateTime dataHora;
    private String categoria;

    public Compromisso(Long id, Long usuarioId, String titulo, LocalDateTime dataHora, String categoria) {
        if (dataHora != null && dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data do compromisso não pode ser no passado.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título do compromisso é obrigatório.");
        }
        this.id = id;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.dataHora = dataHora;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public String getTitulo() { return titulo; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getCategoria() { return categoria; }
}