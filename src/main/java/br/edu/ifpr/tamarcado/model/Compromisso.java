//package br.edu.ifpr.tamarcado.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;

public class Compromisso {
    private int id;
    private int usuarioId;
    private String titulo;
    private LocalDateTime dataHora;
    private String categoria;

    public Compromisso(int id, int usuarioId, String titulo, LocalDateTime dataHora, String categoria) {
        /*if (dataHora != null && dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data do compromisso não pode ser no passado.");
        }*/
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título do compromisso é obrigatório.");
        }
        this.id = id;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.dataHora = dataHora;
        this.categoria = categoria;
    }


    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public String getTitulo() { return titulo; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getCategoria() { return categoria; }
}

class Main {
    public static void main(String[] args) {
        Compromisso compromisso = new Compromisso(1, 1, "Compromisso Teste", LocalDateTime.now(), "Categoria Teste");
        File arquivo = new File("compromisso.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))){
            String registro = compromisso.getId() + "," + compromisso.getUsuarioId() + "," + compromisso.getTitulo() + "," + compromisso.getDataHora() + "," + compromisso.getCategoria();
            writer.write(registro);
            writer.newLine();
            System.out.println("Compromisso salvo com sucesso!" );
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String registro;
            while((registro = reader.readLine()) != null) {
                String[] dados = registro.split(",");
                int id = Integer.parseInt(dados[0]);
                int usuarioId = Integer.parseInt(dados[1]);
                String titulo = dados[2];
                LocalDateTime dataHora = LocalDateTime.parse(dados[3]);
                String categoria = dados[4];

                Compromisso cp = new Compromisso(id, usuarioId, titulo, dataHora, categoria);
                System.out.println("Compromisso Recuperado: " + cp.getTitulo() + "Categoria: " + cp.getCategoria());
        
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
