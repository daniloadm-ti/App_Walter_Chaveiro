package com.app.walterapp.vendas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.walterapp.R;

import java.util.List;

public class ConsultaVendas extends AppCompatActivity {

    private TableLayout tabelaVendas;
    private Button btnBuscar;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_vendas);

        tabelaVendas = findViewById(R.id.tabelaVendas);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVoltar = findViewById(R.id.btnVoltar);

        // Botão de busca
        btnBuscar.setOnClickListener(v -> buscarVendas());

        // Botão de voltar
        btnVoltar.setOnClickListener(v -> onBackPressed());
    }

    // Método para buscar todas as vendas e exibi-las
    private void buscarVendas() {
        VendasDAO vendasDAO = new VendasDAO(this);
        List<Venda> vendas = vendasDAO.buscarTodasVendas();



        // Adiciona os dados na tabela
        for (Venda venda : vendas) {
            TableRow row = new TableRow(this);

            // Cria os elementos de cada venda
            TextView tvCodigoVenda = new TextView(this);
            tvCodigoVenda.setText(venda.getCodigoVenda());
            tvCodigoVenda.setPadding(8, 8, 8, 8);

            TextView tvCpfCliente = new TextView(this);
            tvCpfCliente.setText(venda.getCpfCliente());
            tvCpfCliente.setPadding(8, 8, 8, 8);

            TextView tvCodigoProduto = new TextView(this);
            tvCodigoProduto.setText(venda.getCodigoProduto());
            tvCodigoProduto.setPadding(8, 8, 8, 8);

            TextView tvQuantidade = new TextView(this);
            tvQuantidade.setText(String.valueOf(venda.getQuantidade()));
            tvQuantidade.setPadding(8, 8, 8, 8);

            TextView tvValorTotal = new TextView(this);
            tvValorTotal.setText(String.format("R$ %.2f", venda.getValorTotal()));
            tvValorTotal.setPadding(8, 8, 8, 8);

            // Adiciona as células na linha
            row.addView(tvCodigoVenda);
            row.addView(tvCpfCliente);
            row.addView(tvCodigoProduto);
            row.addView(tvQuantidade);
            row.addView(tvValorTotal);

            // Adiciona a linha na tabela
            tabelaVendas.addView(row);
        }
    }
}
