package com.cadastroapp.ui.telas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cadastroapp.dados.LocalDataRepository
import com.cadastroapp.navegacao.Rotas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Produtos Artesanais") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Rotas.CADASTRO_PRODUTO) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Produto")
            }
        }
    ) { paddingValues ->
        if (LocalDataRepository.produtosList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhum produto cadastrado.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(LocalDataRepository.produtosList) { produto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("detalhes/${produto.id}")
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = produto.nome, style = MaterialTheme.typography.titleLarge)
                                Text(text = "Categoria: ${produto.categoria}", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    text = produto.preco,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            IconButton(onClick = { LocalDataRepository.removerProduto(produto.id) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remover Produto",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}