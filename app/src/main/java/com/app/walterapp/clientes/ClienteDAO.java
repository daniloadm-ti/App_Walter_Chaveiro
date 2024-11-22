package com.app.walterapp.clientes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.walterapp.bancosDados.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private DBHelper dbHelper;

    public ClienteDAO(Context context) {
        dbHelper = new DBHelper(context);
    }


    public Cliente obterClientePorCpf(String cpf) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("clientes", null, "cpf=?", new String[]{cpf}, null, null, null);
        Cliente cliente = null;

        if (cursor.moveToFirst()) {
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String endereco = cursor.getString(cursor.getColumnIndex("endereco"));
            String telefone = cursor.getString(cursor.getColumnIndex("telefone"));
            String observacoes = cursor.getString(cursor.getColumnIndex("observacoes"));
            // Crie um objeto Cliente ou use uma classe que você já tenha
            cliente = new Cliente(cpf, nome, endereco, telefone, observacoes);
        }

        cursor.close();
        db.close();
        return cliente;
    }

    // Método para adicionar um cliente
    public void adicionarCliente(String cpf, String nome, String endereco, String telefone, String observacoes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("clientes", new String[]{"cpf"}, "cpf=?", new String[]{cpf}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        ContentValues values = new ContentValues();
        values.put("cpf", cpf); // Certifique-se de que o CPF seja único
        values.put("nome", nome);
        values.put("endereco", endereco);
        values.put("telefone", telefone);
        values.put("observacoes", observacoes);
        db.insert("clientes", null, values);
        cursor.close();
        db.close();
    }

    // Método para obter todos os clientes
    public List<String> obterClientes() {
        List<String> clientes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("clientes", new String[]{"nome"}, null, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("nome"));
                clientes.add(nome);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return clientes;
    }

    // Método para excluir um cliente
    public void excluirCliente(String cpf) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("clientes", "cpf=?", new String[]{cpf}); // Usando CPF como identificador
        db.close();
    }
}

