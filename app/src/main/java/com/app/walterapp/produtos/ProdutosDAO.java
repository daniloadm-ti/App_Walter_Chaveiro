package com.app.walterapp.produtos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.walterapp.bancosDados.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {
    private DBHelper dbHelper;

    // Construtor para inicializar o DBHelper
    public ProdutosDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Método para adicionar um produto ao banco de dados
    public void adicionarProduto(int codigo, String descricao, int quantidade, double preco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Verifica se o código do produto já existe
        Cursor cursor = db.query("produtos", new String[]{"codigo"}, "codigo=?", new String[]{String.valueOf(codigo)}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            throw new IllegalArgumentException("Código do produto já cadastrado!");
        }

        // Inserir novo produto
        ContentValues values = new ContentValues();
        values.put("codigo", codigo);
        values.put("descricao", descricao);
        values.put("quantidade", quantidade);
        values.put("preco", preco);
        db.insert("produtos", null, values);

        cursor.close();
        db.close();
    }

    // Método para buscar um produto pelo código (agora usando String como tipo de código)
    public Produtos buscarProduto(String codigo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("produtos", new String[]{"codigo", "descricao", "quantidade", "preco"},
                "codigo=?", new String[]{codigo}, null, null, null);

        if (cursor.moveToFirst()) {
            String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
            String quantidade = cursor.getString(cursor.getColumnIndex("quantidade"));
            String preco = cursor.getString(cursor.getColumnIndex("preco"));
            Produtos produto = new Produtos(codigo, descricao, quantidade, preco);
            cursor.close();
            db.close();
            return produto;
        } else {
            cursor.close();
            db.close();
            return null;
        }
    }




    // Método para obter todos os produtos
    public List<String> obterProdutos() {
        List<String> produtos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("produtos", new String[]{"descricao"}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
                produtos.add(descricao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return produtos;
    }

    // Método para excluir um produto pelo código
    public void excluirProduto(String codigo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("produtos", "codigo=?", new String[]{codigo});
        db.close();
    }

    // Método para atualizar um produto
    public void atualizarProduto(String codigo, String novaDescricao, String novaQuantidade, String novoPreco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Verifica se o produto existe
        Cursor cursor = db.query("produtos", new String[]{"codigo"}, "codigo=?", new String[]{codigo}, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            throw new IllegalArgumentException("Produto não encontrado!");
        }

        // Atualizar o produto
        ContentValues values = new ContentValues();
        values.put("descricao", novaDescricao);
        values.put("quantidade", novaQuantidade);
        values.put("preco", novoPreco);

        // Atualizar o produto no banco de dados com o código fornecido
        db.update("produtos", values, "codigo=?", new String[]{codigo});

        cursor.close();
        db.close();
    }

}
