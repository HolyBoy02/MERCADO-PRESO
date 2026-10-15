package com.cadastroapp.navegacao

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.cadastroapp.ui.telas.CadastroProdutoScreen
import com.cadastroapp.ui.telas.CatalogoScreen
import com.cadastroapp.ui.telas.DetalhesProdutoScreen
import com.cadastroapp.ui.telas.DetalhesCategoriaScreen
import com.cadastroapp.ui.telas.ListaCategoriasScreen
import com.cadastroapp.ui.telas.CadastroCategoriaScreen
import com.cadastroapp.ui.telas.SobreScreen

object Rotas {
    const val CATALOGO = "catalogo"
    const val CADASTRO_PRODUTO = "cadastro_produto"
    const val DETALHES_PRODUTO = "detalhes/{produtoId}"
    const val LISTA_CATEGORIAS = "lista_categorias"
    const val CADASTRO_CATEGORIA = "cadastro_categoria"
    const val SOBRE = "sobre"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Produtos") },
                    label = { Text("Produtos") },
                    selected = currentRoute?.startsWith("catalogo") == true || currentRoute?.startsWith("cadastro_produto") == true,
                    onClick = {
                        navController.navigate(Rotas.CATALOGO) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Categorias") },
                    label = { Text("Categorias") },
                    selected = currentRoute?.startsWith("lista_categorias") == true || currentRoute?.startsWith("cadastro_categoria") == true,
                    onClick = {
                        navController.navigate(Rotas.LISTA_CATEGORIAS) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(androidx.compose.material.icons.Icons.Default.Info, contentDescription = "Sobre") },
                    label = { Text("Sobre") },
                    selected = currentRoute == Rotas.SOBRE,
                    onClick = {
                        navController.navigate(Rotas.SOBRE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Rotas.CATALOGO,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Rotas.CATALOGO) {
                CatalogoScreen(navController = navController)
            }
            composable(route = Rotas.DETALHES_PRODUTO) { backStackEntry ->
                val produtoId = backStackEntry.arguments?.getString("produtoId")?.toIntOrNull()
                DetalhesProdutoScreen(navController = navController, produtoId = produtoId)
            }
            composable(route = Rotas.CADASTRO_PRODUTO) {
                CadastroProdutoScreen(navController = navController)
            }

            composable(route = Rotas.LISTA_CATEGORIAS) {
                ListaCategoriasScreen(navController = navController)
            }
            composable(route = Rotas.CADASTRO_CATEGORIA) {
                CadastroCategoriaScreen(navController = navController)
            }
            composable(route = "detalhes_categoria/{categoriaId}") { backStackEntry ->
                val categoriaId = backStackEntry.arguments?.getString("categoriaId")?.toIntOrNull()
                DetalhesCategoriaScreen(navController = navController, categoriaId = categoriaId)
            }
            composable(route = Rotas.SOBRE) {
                SobreScreen(navController = navController)
            }
        }
    }
}