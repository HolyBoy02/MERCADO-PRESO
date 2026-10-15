package com.cadastroapp.ui.telas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cadastroapp.dados.LocalDataRepository
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesProdutoScreen(navController: NavController, produtoId: Int?) {
    val produto = LocalDataRepository.produtosList.find { it.id == produtoId }

    var cupom by remember { mutableStateOf("") }
    var precoCalculado by remember { mutableStateOf(produto?.preco ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Produto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            if (produto != null) {
                Text(text = produto.nome, style = MaterialTheme.typography.headlineMedium)
                Text(text = "Categoria: ${produto.categoria}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Descrição: ${produto.descricao}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Preço Original: ${produto.preco}", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Simular Desconto (Cupom: 10OFF)", style = MaterialTheme.typography.labelLarge)
                        OutlinedTextField(
                            value = cupom,
                            onValueChange = { texto ->
                                cupom = texto
                                if (texto.uppercase() == "10OFF") {
                                    val valorNumerico = produto.preco.replace("R$", "").replace(",", ".").trim().toDoubleOrNull() ?: 0.0
                                    val valorComDesconto = valorNumerico * 0.9
                                    precoCalculado = String.format(Locale.getDefault(), "R$ %.2f", valorComDesconto).replace(".", ",")
                                } else {
                                    precoCalculado = produto.preco
                                }
                            },
                            label = { Text("Digite o cupom") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Valor Final: $precoCalculado", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                    }
                }
            } else {
                Text("Produto não encontrado.")
            }
        }
    }
}