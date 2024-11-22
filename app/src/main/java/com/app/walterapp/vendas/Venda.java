package com.app.walterapp.vendas;

public class Venda {
    private String codigoVenda;
    private String cpfCliente;
    private String codigoProduto;
    private int quantidade;
    private double valorTotal;

    // Construtor atualizado para incluir o valorTotal
    public Venda(String codigoVenda, String cpfCliente, String codigoProduto, int quantidade, double valorTotal) {
        this.codigoVenda = codigoVenda;
        this.cpfCliente = cpfCliente;
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters para todos os campos, se necessário
    public String getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(String codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
