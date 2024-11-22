package com.app.walterapp.vendas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.walterapp.bancosDados.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class VendasDAO {
    private DBHelper dbHelper;

    public VendasDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Método para criar uma nova venda
    public void criarVenda(String codigoVenda, String cpfCliente, String codigoProduto, int quantidade, double valorTotal) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("codigoVenda", codigoVenda);
        values.put("cpfCliente", cpfCliente);
        values.put("codigoProduto", codigoProduto);
        values.put("quantidade", quantidade);
        values.put("valorTotal", valorTotal); // Valor total da venda

        db.insert("vendas", null, values);
        db.close();
    }

    // Método para buscar uma venda pelo código
    public Venda buscarVendaPorCodigo(String codigoVenda) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("vendas", null, "codigoVenda=?", new String[]{codigoVenda}, null, null, null);

        Venda venda = null;
        if (cursor.moveToFirst()) {
            String cpfCliente = cursor.getString(cursor.getColumnIndex("cpfCliente"));
            String codigoProduto = cursor.getString(cursor.getColumnIndex("codigoProduto"));
            int quantidade = cursor.getInt(cursor.getColumnIndex("quantidade"));
            double valorTotal = cursor.getDouble(cursor.getColumnIndex("valorTotal")); // Recupera o valor total
            venda = new Venda(codigoVenda, cpfCliente, codigoProduto, quantidade, valorTotal);
        }

        cursor.close();
        db.close();
        return venda;
    }

    // Método para buscar todas as vendas
    public List<Venda> buscarTodasVendas() {
        List<Venda> vendas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("vendas", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String codigoVenda = cursor.getString(cursor.getColumnIndex("codigoVenda"));
                String cpfCliente = cursor.getString(cursor.getColumnIndex("cpfCliente"));
                String codigoProduto = cursor.getString(cursor.getColumnIndex("codigoProduto"));
                int quantidade = cursor.getInt(cursor.getColumnIndex("quantidade"));
                double valorTotal = cursor.getDouble(cursor.getColumnIndex("valorTotal")); // Recupera o valor total
                vendas.add(new Venda(codigoVenda, cpfCliente, codigoProduto, quantidade, valorTotal));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vendas;
    }

    // Método para atualizar uma venda
    public void atualizarVenda(String codigoVenda, String cpfCliente, String codigoProduto, int quantidade, double valorTotal) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cpfCliente", cpfCliente);
        values.put("codigoProduto", codigoProduto);
        values.put("quantidade", quantidade);
        values.put("valorTotal", valorTotal); // Atualiza o valor total

        db.update("vendas", values, "codigoVenda=?", new String[]{codigoVenda});
        db.close();
    }

    // Método para excluir uma venda
    public void excluirVenda(String codigoVenda) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("vendas", "codigoVenda=?", new String[]{codigoVenda});
        db.close();
    }

    // Método para obter o último código de venda
    public long obterUltimoCodigoVenda() {
        long ultimoCodigo = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Executa a consulta para obter o maior código de venda
        Cursor cursor = db.rawQuery("SELECT MAX(codigoVenda) FROM vendas", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Se o banco tiver registros, retorna o maior código de venda
                ultimoCodigo = cursor.getLong(0);
            }
            cursor.close();
        }
        db.close();

        // Caso o banco esteja vazio, inicia o código de venda com 1
        if (ultimoCodigo == 0) {
            return 0;
        }

        // Retorna o próximo código de venda
        return ultimoCodigo + 1;
    }
}
