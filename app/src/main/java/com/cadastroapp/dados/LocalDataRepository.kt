package com.cadastroapp.dados

import androidx.compose.runtime.mutableStateListOf
import com.cadastroapp.modelos.Categoria
import com.cadastroapp.modelos.Produto

object LocalDataRepository {
    val produtosList = mutableStateListOf(
        Produto(id = 1, nome = "Vaso de Cerâmica", categoria = "Decoração", preco = "R$ 45,00", descricao = "Vaso feito à mão"),
        Produto(id = 2, nome = "Bolsa de Crochê", categoria = "Acessórios", preco = "R$ 80,00", descricao = "Bolsa resistente"),
        Produto(id = 3, nome = "Quadro Entalhado", categoria = "Arte", preco = "R$ 120,00", descricao = "Arte entalhada em madeira")
    )

    fun adicionarProduto(produto: Produto) {
        produtosList.add(produto)
    }

    fun removerProduto(id: Int) {
        produtosList.removeAll { it.id == id }
    }

    val categoriasList = mutableStateListOf(
        Categoria(id = 1, nome = "Decoração", descricao = "Itens para enfeitar a casa"),
        Categoria(id = 2, nome = "Acessórios", descricao = "Itens de uso pessoal"),
        Categoria(id = 3, nome = "Arte", descricao = "Obras artísticas feitas à mão")
    )

    fun adicionarCategoria(categoria: Categoria) {
        categoriasList.add(categoria)
    }

    fun removerCategoria(id: Int) {
        val categoriaParaRemover = categoriasList.find { it.id == id }

        if (categoriaParaRemover != null) {
            produtosList.removeAll { it.categoria == categoriaParaRemover.nome }
            categoriasList.remove(categoriaParaRemover)
        }
    }
}