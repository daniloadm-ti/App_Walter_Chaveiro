package com.app.walterapp.vendas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.walterapp.CarrinhoAdapter;
import com.app.walterapp.clientes.Cliente;
import com.app.walterapp.produtos.Produtos;
import com.app.walterapp.R;
import com.app.walterapp.clientes.ClienteDAO;
import com.app.walterapp.produtos.ProdutosDAO;

import java.util.ArrayList;
import java.util.List;

public class RealizarVendas extends AppCompatActivity {

    private EditText editTextCPF, editTextCliente, editTextCodigoProduto, editTextProduto;
    private Button buttonAdicionarCarrinho, buttonConcluirVenda, buttonVoltar;
    private TextView textViewValorTotal;
    private ListView listViewCarrinho;
    private ClienteDAO clienteDAO;
    private ProdutosDAO produtosDAO;
    private List<Produtos> carrinho = new ArrayList<>();
    private double valorTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_vendas);

        // Inicializando componentes
        editTextCPF = findViewById(R.id.editTextCPF);
        editTextCliente = findViewById(R.id.editTextCliente);
        editTextCodigoProduto = findViewById(R.id.editTextCodigoProduto);
        editTextProduto = findViewById(R.id.editTextProduto);
        buttonAdicionarCarrinho = findViewById(R.id.buttonAdicionarCarrinho);
        buttonConcluirVenda = findViewById(R.id.buttonConcluirVenda);
        buttonVoltar = findViewById(R.id.buttonVoltar);
        textViewValorTotal = findViewById(R.id.textViewValorTotal);
        listViewCarrinho = findViewById(R.id.listViewCarrinho);
        clienteDAO = new ClienteDAO(this);
        produtosDAO = new ProdutosDAO(this);
        carrinho = new ArrayList<>();

// Inicializar o adapter personalizado
        CarrinhoAdapter adapter = new CarrinhoAdapter(this, carrinho);
        listViewCarrinho.setAdapter(adapter);

// Quando adicionar um item ao carrinho, atualize o adapter
        buttonAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoProduto = editTextCodigoProduto.getText().toString();
                Produtos produto = produtosDAO.buscarProduto(codigoProduto);

                if (produto == null) {
                    Toast.makeText(RealizarVendas.this, "Produto não encontrado", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Adiciona o objeto Produtos ao carrinho
                carrinho.add(produto);

                // Atualizar o valor total e exibir no TextView
                valorTotal += Double.parseDouble(produto.getValor());
                textViewValorTotal.setText(String.format("Valor Total: R$ %.2f", valorTotal));

                // Notificar o adapter para atualizar a ListView
                adapter.notifyDataSetChanged();

                Toast.makeText(RealizarVendas.this, "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
            }
        });




        // Gerando o código da venda e exibindo no EditText
        String codigoVenda = null;
        try {
            codigoVenda = gerarCodigoVenda();
            if (codigoVenda != null) {
                EditText editTextCodigoVenda = findViewById(R.id.editTextCodigoVenda);
                editTextCodigoVenda.setText(codigoVenda);
            } else {
                Toast.makeText(RealizarVendas.this, "Erro ao gerar código de venda", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RealizarVendas.this, "Erro ao acessar o banco de dados para gerar o código de venda", Toast.LENGTH_SHORT).show();
        }

        // Adicionando TextWatcher para preencher automaticamente os campos
        editTextCPF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                // Verifica se o texto digitado tem 11 caracteres (tamanho do CPF)
                String cpf = s.toString();
                if (cpf.length() == 11) {
                    // Só realiza a pesquisa se o CPF tiver 11 dígitos
                    Cliente cliente = clienteDAO.obterClientePorCpf(cpf);
                    if (cliente != null) {
                        editTextCliente.setText(cliente.getNome());
                    } else {
                        editTextCliente.setText("");
                        Toast.makeText(RealizarVendas.this, "Cliente não encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        editTextCodigoProduto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String codigo = s.toString();
                Produtos produto = produtosDAO.buscarProduto(codigo);
                if (produto != null) {
                    editTextProduto.setText(produto.getDescricao());
                } else {
                    editTextProduto.setText("");
                    Toast.makeText(RealizarVendas.this, "Produto não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Evento para o botão "Adicionar ao Carrinho"
        buttonAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricaoProduto = editTextProduto.getText().toString();
                Produtos produto = produtosDAO.buscarProduto(editTextCodigoProduto.getText().toString());
                carrinho.add(produto);

                // Atualizar o valor total e exibir no TextView
                valorTotal += Double.parseDouble(produtosDAO.buscarProduto(editTextCodigoProduto.getText().toString()).getValor());
                textViewValorTotal.setText(String.format("Valor Total: R$ %.2f", valorTotal));
                // Atualizar ListView (você pode usar um Adapter personalizado se necessário)
                Toast.makeText(RealizarVendas.this, "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento para o botão "Concluir Venda"
        // Evento para o botão "Concluir Venda"
        buttonConcluirVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carrinho.isEmpty()) {
                    Toast.makeText(RealizarVendas.this, "Carrinho vazio, adicione itens ao carrinho", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obter o CPF do cliente
                String cpfCliente = editTextCPF.getText().toString();

                // Verificar se o CPF foi preenchido
                if (cpfCliente.isEmpty()) {
                    Toast.makeText(RealizarVendas.this, "Por favor, preencha o CPF do cliente", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gerar o código da venda
                VendasDAO vendasDAO = new VendasDAO(RealizarVendas.this);
                String codigoVenda = String.valueOf(vendasDAO.obterUltimoCodigoVenda() + 1);  // Garante que o código seja único

                double valorTotal = 0.0;  // Inicializa o valor total

                // Iterar sobre o carrinho e registrar cada item
                for (Produtos produto : carrinho) {
                    // Buscar o produto pelo nome/descrição
                    String descricaoProduto = produto.getDescricao();

                    if (produto != null) {
                        // Calcular o valor total da venda
                        try {
                            valorTotal += Double.parseDouble(produto.getValor());
                        } catch (NumberFormatException e) {
                            // Lidar com o erro, talvez ignorar ou exibir uma mensagem de erro
                            e.printStackTrace(); // Exemplo de tratamento, pode ser substituído conforme necessário
                        }

                        // Salvar cada produto no banco de dados
                        vendasDAO.criarVenda(codigoVenda, cpfCliente, produto.getCodigo(), 1, valorTotal); // Salva a venda com o valor total
                    }
                }

                // Exibir uma mensagem de sucesso
                Toast.makeText(RealizarVendas.this, "Venda concluída com sucesso!", Toast.LENGTH_SHORT).show();

                // Limpar os campos para uma nova venda
                limparCampos();
            }
        });



        // Evento para o botão "Voltar"
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Voltar para a tela anterior
            }
        });
    }

    private void limparCampos() {
        editTextCPF.setText("");
        editTextCliente.setText("");
        editTextCodigoProduto.setText("");
        editTextProduto.setText("");
        valorTotal = 0.0;
        carrinho.clear();
        textViewValorTotal.setText("Valor Total: R$ 0,00");
    }
    private String gerarCodigoVenda() {
        // Aqui você pode gerar o código da venda. Exemplo simples usando timestamp:
        VendasDAO vendasDAO = new VendasDAO(this);
        long ultimoCodigo = vendasDAO.obterUltimoCodigoVenda();
        long novoCodigo = ultimoCodigo + 1;
        return String.valueOf(novoCodigo);
    }
}
