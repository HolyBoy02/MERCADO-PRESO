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
fun ListaCategoriasScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Categorias") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Rotas.CADASTRO_CATEGORIA) }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Categoria")
            }
        }
    ) { paddingValues ->
        if (LocalDataRepository.categoriasList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Nenhuma categoria cadastrada.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(LocalDataRepository.categoriasList) { categoria ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable {
                            navController.navigate("detalhes_categoria/${categoria.id}")
                        },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = categoria.nome, style = MaterialTheme.typography.titleLarge)
                                Text(text = categoria.descricao, style = MaterialTheme.typography.bodyMedium)
                            }
                            IconButton(onClick = { LocalDataRepository.removerCategoria(categoria.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Remover", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}