package com.app.walterapp.produtos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.walterapp.telalogin.MainActivity;
import com.app.walterapp.R;

public class ConsultaProdutos extends AppCompatActivity {

    private EditText editTextCodigo, editTextDescricao, editTextQuantidade, editTextValor;
    private Button buttonBuscar, buttonExcluir, buttonAtualizar, buttonVoltar;
    private ProdutosDAO produtosDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_produtos);

        // Inicializando os componentes
        editTextCodigo = findViewById(R.id.editTextCodigo);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        editTextValor = findViewById(R.id.editTextValor);
        buttonBuscar = findViewById(R.id.buttonBuscar);
        buttonExcluir = findViewById(R.id.buttonExcluir);
        buttonAtualizar = findViewById(R.id.buttonAtualizar);
        buttonVoltar = findViewById(R.id.buttonVoltar);

        // Inicializando o DAO para interagir com o banco de dados
        produtosDAO = new ProdutosDAO(this);

        // Configuração do botão "Buscar"
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarProduto();
            }
        });

        // Configuração do botão "Excluir"
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirProduto();
            }
        });

        // Configuração do botão "Atualizar"
        buttonAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarProduto();
            }
        });

        // Configuração do botão "Voltar"
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarParaMain();
            }
        });
    }

    // Método para buscar um produto no banco de dados
    private void buscarProduto() {
        String codigoStr = editTextCodigo.getText().toString();

        if (codigoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o código do produto", Toast.LENGTH_SHORT).show();
            return;
        }

        Produtos produto = produtosDAO.buscarProduto(codigoStr); // Alteração aqui para usar a classe Produtos
        if (produto != null) {
            editTextDescricao.setText(produto.getDescricao());
            editTextQuantidade.setText(produto.getQuantidade());
            editTextValor.setText(produto.getValor());
        } else {
            Toast.makeText(this, "Produto não encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para excluir um produto
    private void excluirProduto() {
        String codigoStr = editTextCodigo.getText().toString();

        if (codigoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o código do produto", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            produtosDAO.excluirProduto(codigoStr);
            Toast.makeText(this, "Produto excluído com sucesso", Toast.LENGTH_SHORT).show();
            limparCampos();
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao excluir produto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Método para atualizar um produto
    private void atualizarProduto() {
        String codigoStr = editTextCodigo.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String quantidade = editTextQuantidade.getText().toString();
        String valor = editTextValor.getText().toString();

        if (codigoStr.isEmpty() || descricao.isEmpty() || quantidade.isEmpty() || valor.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            produtosDAO.atualizarProduto(codigoStr, descricao, quantidade, valor);
            Toast.makeText(this, "Produto atualizado com sucesso", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao atualizar produto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Método para limpar os campos
    private void limparCampos() {
        editTextCodigo.setText("");
        editTextDescricao.setText("");
        editTextQuantidade.setText("");
        editTextValor.setText("");
    }

    // Método para voltar para a MainActivity
    private void voltarParaMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Opcional, para garantir que a tela atual seja fechada
    }
}
