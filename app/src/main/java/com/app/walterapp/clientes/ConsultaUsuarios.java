package com.app.walterapp.clientes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.walterapp.R;

public class ConsultaUsuarios extends AppCompatActivity {

    private EditText editTextCpf;
    private TextView textViewNomeValue;
    private TextView textViewEnderecoValue;
    private TextView textViewTelefoneValue;
    private TextView textViewObservacoesValue;
    private ClienteDAO clienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consulta_usuarios);

        editTextCpf = findViewById(R.id.editTextCpf);
        textViewNomeValue = findViewById(R.id.textViewNomeValue);
        textViewEnderecoValue = findViewById(R.id.textViewEnderecoValue);
        textViewTelefoneValue = findViewById(R.id.textViewTelefoneValue);
        textViewObservacoesValue = findViewById(R.id.textViewObservacoesValue);
        clienteDAO = new ClienteDAO(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonBuscar = findViewById(R.id.buttonBuscar);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCliente();
            }
        });

        Button buttonVoltar = findViewById(R.id.buttonVoltar);
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Voltar para a tela anterior
            }
        });
    }

    private void buscarCliente() {
        String cpf = editTextCpf.getText().toString();
        Cliente cliente = clienteDAO.obterClientePorCpf(cpf); // Método que você vai implementar
        if (cliente != null) {
            textViewNomeValue.setText(cliente.getNome());
            textViewEnderecoValue.setText(cliente.getEndereco());
            textViewTelefoneValue.setText(cliente.getTelefone());
            textViewObservacoesValue.setText(cliente.getObservacoes());
        } else {
            // Limpa os campos se o cliente não for encontrado
            textViewNomeValue.setText("");
            textViewEnderecoValue.setText("");
            textViewTelefoneValue.setText("");
        }
    }
}
