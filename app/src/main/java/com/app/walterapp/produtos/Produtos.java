package com.app.walterapp.produtos;

public class Produtos {

    private String codigo;
    private String descricao;
    private String quantidade;
    private String valor;

    public Produtos(String codigo, String descricao, String quantidade, String valor){
        this.codigo = codigo;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor = valor;
    }
    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public String getValor() {
        return valor;
    }

    // Setters
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
