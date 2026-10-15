package com.cadastroapp.ui.telas

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cadastroapp.dados.LocalDataRepository
import com.cadastroapp.modelos.Produto
import androidx.compose.material3.ExposedDropdownMenuDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroProdutoScreen(navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var menuExpandido by remember { mutableStateOf(false) }

    val isFormValid = nome.isNotBlank() && categoria.isNotBlank() && preco.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Novo Item") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Produto") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = menuExpandido,
                onExpandedChange = { menuExpandido = !menuExpandido },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                OutlinedTextField(
                    value = categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpandido) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = { menuExpandido = false }
                ) {
                    if (LocalDataRepository.categoriasList.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Nenhuma categoria - Cadastre primeiro") },
                            onClick = { menuExpandido = false }
                        )
                    } else {
                        LocalDataRepository.categoriasList.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat.nome) },
                                onClick = {
                                    categoria = cat.nome
                                    menuExpandido = false
                                }
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = preco,
                onValueChange = { preco = it },
                label = { Text("Preço (ex: R$ 50,00)") },
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
                    val novoId = (LocalDataRepository.produtosList.maxOfOrNull { it.id } ?: 0) + 1
                    val novoProduto = Produto(
                        id = novoId,
                        nome = nome,
                        categoria = categoria,
                        preco = preco,
                        descricao = descricao
                    )
                    LocalDataRepository.adicionarProduto(novoProduto)
                    Log.d("CADASTRO_LOG", "Novo produto cadastrado: ${novoProduto.nome}")

                    navController.popBackStack()
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar e Cadastrar")
            }
        }
    }
}
