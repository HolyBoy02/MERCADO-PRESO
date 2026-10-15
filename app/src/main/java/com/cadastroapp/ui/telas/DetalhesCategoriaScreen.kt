package com.cadastroapp.ui.telas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cadastroapp.dados.LocalDataRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesCategoriaScreen(navController: NavController, categoriaId: Int?) {
    val categoria = LocalDataRepository.categoriasList.find { it.id == categoriaId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Categoria") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            if (categoria != null) {
                Text(text = categoria.nome, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Descrição:", style = MaterialTheme.typography.titleMedium)
                Text(text = categoria.descricao, style = MaterialTheme.typography.bodyLarge)
            } else {
                Text("Categoria não encontrada.")
            }
        }
    }
}