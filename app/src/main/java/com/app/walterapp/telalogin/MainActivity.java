package com.app.walterapp.telalogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.walterapp.R;
import com.app.walterapp.clientes.CadastroUsuarios;
import com.app.walterapp.clientes.ConsultaUsuarios;
import com.app.walterapp.produtos.CadastroProdutos;
import com.app.walterapp.produtos.ConsultaProdutos;
import com.app.walterapp.vendas.ConsultaVendas;
import com.app.walterapp.vendas.RealizarVendas;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button buttonCadastroUsuario = findViewById(R.id.buttonCadastroUsuario);
        Button buttonCadastroProduto = findViewById(R.id.buttonCadastroProduto);
        Button buttonConsultaUsuario = findViewById(R.id.buttonConsultaUsuario);
        Button buttonConsultaProduto = findViewById(R.id.buttonConsultaProduto);
        Button buttonRealizarVenda = findViewById(R.id.buttonRealizarVenda);
        Button buttonConsultarVendas = findViewById(R.id.buttonConsultarVendas);

        buttonCadastroUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroUsuarios.class);
            startActivity(intent);
        });

        buttonCadastroProduto.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroProdutos.class);
            startActivity(intent);
        });

        buttonConsultaUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConsultaUsuarios.class);
            startActivity(intent);
        });

        buttonConsultaProduto.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConsultaProdutos.class);
            startActivity(intent);
        });

        buttonRealizarVenda.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RealizarVendas.class);
            startActivity(intent);
        });

        buttonConsultarVendas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConsultaVendas.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}