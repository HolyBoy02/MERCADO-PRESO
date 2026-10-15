package com.cadastroapp.ui.telas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cadastroapp.dados.LocalDataRepository
import com.cadastroapp.modelos.Categoria

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroCategoriaScreen(navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    val isFormValid = nome.isNotBlank() && descricao.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Categoria") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome da Categoria") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                maxLines = 3
            )

            Button(
                onClick = {
                    val novoId = (LocalDataRepository.categoriasList.maxOfOrNull { it.id } ?: 0) + 1
                    val novaCategoria = Categoria(id = novoId, nome = nome, descricao = descricao)
                    LocalDataRepository.adicionarCategoria(novaCategoria)
                    navController.popBackStack()
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Categoria")
            }
        }
    }
}