package com.app.walterapp.bancosDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "banco_walter.db";
    private static final int DATABASE_VERSION = 3;

    // Definição da tabela clientes
    private static final String TABLE_CLIENTES = "clientes";
    private static final String COLUMN_CPF = "cpf";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_ENDERECO = "endereco";
    private static final String COLUMN_TELEFONE = "telefone";
    private static final String COLUMN_OBSERVACOES = "observacoes";

    // Definição da tabela produtos
    private static final String TABLE_PRODUTOS = "produtos";
    private static final String COLUMN_CODIGO = "codigo";
    private static final String COLUMN_DESCRICAO = "descricao";
    private static final String COLUMN_QUANTIDADE = "quantidade";
    private static final String COLUMN_PRECO = "preco";

    //Definição da tabela vendas
    private static final String TABLE_VENDAS = "vendas";
    private static final String COLUMN_CODIGO_VENDA = "codigoVenda";
    private static final String COLUMN_CPF_CLIENTE = "cpfCliente";
    private static final String COLUMN_CODIGO_PRODUTO = "codigoProduto";
    private static final String COLUMN_QUANTIDADEV = "quantidade";
    private static final String COLUMN_VALOR_TOTAL = "valorTotal";

    // Comando SQL para criar a tabela clientes
    private static final String CREATE_TABLE_CLIENTES =
            "CREATE TABLE " + TABLE_CLIENTES + " (" +
                    COLUMN_CPF + " TEXT PRIMARY KEY UNIQUE, " + // CPF como texto e chave primária
                    COLUMN_NOME + " TEXT, " +
                    COLUMN_ENDERECO + " TEXT, " +
                    COLUMN_TELEFONE + " TEXT, " +
                    COLUMN_OBSERVACOES + " TEXT);";

    // Comando SQL para criar a tabela produtos
    private static final String CREATE_TABLE_PRODUTOS =
            "CREATE TABLE " + TABLE_PRODUTOS + " (" +
                    COLUMN_CODIGO + " INTEGER PRIMARY KEY UNIQUE, " + // Código como chave primária
                    COLUMN_DESCRICAO + " TEXT, " +
                    COLUMN_QUANTIDADE + " INTEGER, " +
                    COLUMN_PRECO + " REAL);";

    //Comando SQL para criar a tabela vendas
    private static final String CREATE_TABLE_VENDAS =
            "CREATE TABLE " + TABLE_VENDAS + " (" +
                    COLUMN_CODIGO_VENDA + " TEXT PRIMARY KEY, " +
                    COLUMN_CPF_CLIENTE + " TEXT, " +
                    COLUMN_CODIGO_PRODUTO + " TEXT, " +
                    COLUMN_QUANTIDADEV + " INTEGER, " +
                    COLUMN_VALOR_TOTAL + " REAL, " +
                    "FOREIGN KEY (" + COLUMN_CPF_CLIENTE + ") REFERENCES clientes(cpf), " +
                    "FOREIGN KEY (" + COLUMN_CODIGO_PRODUTO + ") REFERENCES produtos(codigo));";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENTES); // Cria a tabela clientes
        db.execSQL(CREATE_TABLE_PRODUTOS); // Cria a tabela produtos
        db.execSQL(CREATE_TABLE_VENDAS); //Cria a tabela vendas
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDAS);
        onCreate(db);
    }
}
