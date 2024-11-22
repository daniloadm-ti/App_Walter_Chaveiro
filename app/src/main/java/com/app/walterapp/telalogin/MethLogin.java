package com.app.walterapp.telalogin;

public class MethLogin {

    // Aqui você pode definir os usuários e senhas válidos
    private static final String VALID_USERNAME = "";
    private static final String VALID_PASSWORD = "";

    public boolean login(String username, String password) {
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }
}
