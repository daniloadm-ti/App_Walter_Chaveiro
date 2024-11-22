package com.app.walterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.walterapp.produtos.Produtos;

import java.util.List;

public class CarrinhoAdapter extends BaseAdapter {
    private Context context;
    private List<Produtos> produtosList;

    // Construtor
    public CarrinhoAdapter(Context context, List<Produtos> produtosList) {
        this.context = context;
        this.produtosList = produtosList;
    }

    @Override
    public int getCount() {
        return produtosList.size();
    }

    @Override
    public Object getItem(int position) {
        return produtosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflar o layout do item, se necessário
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_carrinho, parent, false);
        }

        // Obter o item atual
        Produtos produto = produtosList.get(position);

        // Preencher os campos do layout com os dados do produto
        TextView textViewNome = convertView.findViewById(R.id.textViewProdutoNome);
        TextView textViewValor = convertView.findViewById(R.id.textViewProdutoValor);

        textViewNome.setText(produto.getDescricao());
        textViewValor.setText(String.format("R$ %.2f", produto.getValor()));

        return convertView;
    }
}
