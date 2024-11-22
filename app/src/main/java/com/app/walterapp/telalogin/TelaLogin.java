package com.app.walterapp.telalogin;

import android.content.Intent;
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

public class TelaLogin extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private MethLogin methLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        methLogin = new MethLogin();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (methLogin.login(username, password)) {
                    Toast.makeText(TelaLogin.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                    // Iniciar a MainActivity
                    Intent intent = new Intent(TelaLogin.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Opcional: finaliza a TelaLogin
                } else {
                    Toast.makeText(TelaLogin.this, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}