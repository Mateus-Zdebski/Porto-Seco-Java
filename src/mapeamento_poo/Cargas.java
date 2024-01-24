/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapeamento_poo;

import java.util.Date;

/**
 *
 * @author Mateus
 */
public class Cargas {

    private int id;
    private String tipo;
    private int quantidade;
    private float peso;
    private String dimensao;
    private String origem;
    private String destino;
    private String transportadora;
    private String documentacao;
    private String data_chegada;
    private String data_saida;
    private String status;

    // Construtor
    // Métodos get
    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public float getPeso() {
        return peso;
    }

    public String getDimensao() {
        return dimensao;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public String getTransportadora() {
        return transportadora;
    }

    public String getDocumentacao() {
        return documentacao;
    }

    public String getDataChegada() {
        return data_chegada;
    }

    public String getDataSaida() {
        return data_saida;
    }

    public String getStatus() {
        return status;
    }

    // Métodos set
    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setDimensao(String dimensao) {
        this.dimensao = dimensao;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setTransportadora(String transportadora) {
        this.transportadora = transportadora;
    }

    public void setDocumentacao(String documentacao) {
        this.documentacao = documentacao;
    }

    public void setDataChegada(String data_chegada) {
        this.data_chegada = data_chegada;
    }

    public void setDataSaida(String data_saida) {
        this.data_saida = data_saida;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
