package com.app.walterapp.produtos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.walterapp.R;

public class CadastroProdutos extends AppCompatActivity {
    private ProdutosDAO produtosDAO;
    private EditText editTextCodigo, editTextDescricao, editTextQuantidade, editTextPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);

        // Inicializa o DAO
        produtosDAO = new ProdutosDAO(this);

        // Referências aos campos de entrada
        editTextCodigo = findViewById(R.id.editTextCodigo);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        editTextPreco = findViewById(R.id.editTextPreco);

        // Configuração do botão "Salvar"
        Button buttonSalvar = findViewById(R.id.buttonBuscar);
        buttonSalvar.setOnClickListener(v -> {
            try {
                int codigo = Integer.parseInt(editTextCodigo.getText().toString());
                String descricao = editTextDescricao.getText().toString();
                int quantidade = Integer.parseInt(editTextQuantidade.getText().toString());
                double preco = Double.parseDouble(editTextPreco.getText().toString());

                produtosDAO.adicionarProduto(codigo, descricao, quantidade, preco);
                Toast.makeText(this, "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Erro ao salvar o produto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Configuração do botão "Voltar"
        Button buttonVoltar = findViewById(R.id.buttonVoltar);
        buttonVoltar.setOnClickListener(v -> finish()); // Volta para a tela anterior
    }
}