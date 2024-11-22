package com.app.walterapp.clientes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.walterapp.R;

public class CadastroUsuarios extends AppCompatActivity {

    private ClienteDAO clienteDAO;
    private EditText editTextCpf, editTextNome, editTextEndereco, editTextTelefone, editTextObs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_usuarios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa o DAO e os campos de entrada
        clienteDAO = new ClienteDAO(this);
        editTextCpf = findViewById(R.id.editTextCPF);
        editTextNome = findViewById(R.id.editTextNome);
        editTextEndereco = findViewById(R.id.editTextEndereco);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextObs = findViewById(R.id.editTextObs);

        Button buttonSalvar = findViewById(R.id.buttonBuscar);
        Button buttonExcluir = findViewById(R.id.buttonExcluir);
        Button buttonVoltar = findViewById(R.id.buttonVoltar);

        // Configura o ouvinte do botão Salvar
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = editTextCpf.getText().toString();
                String nome = editTextNome.getText().toString();
                String endereco = editTextEndereco.getText().toString();
                String telefone = editTextTelefone.getText().toString();
                String observacoes = editTextObs.getText().toString();

                try {
                    clienteDAO.adicionarCliente(cpf, nome, endereco, telefone, observacoes);
                    Toast.makeText(CadastroUsuarios.this, "Cliente adicionado!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(CadastroUsuarios.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configura o ouvinte do botão Excluir
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = editTextCpf.getText().toString();
                clienteDAO.excluirCliente(cpf);
                Toast.makeText(CadastroUsuarios.this, "Cliente excluído!", Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        });

        // Configura o ouvinte do botão Voltar
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Volta para a tela anterior
            }
        });
    }

    // Método para limpar os campos após a ação
    private void limparCampos() {
        editTextCpf.setText("");
        editTextNome.setText("");
        editTextEndereco.setText("");
        editTextTelefone.setText("");
        editTextObs.setText("");
    }
}
