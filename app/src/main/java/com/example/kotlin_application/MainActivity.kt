package com.example.kotlin_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolaMundo()
        }
    }
}

@Composable
fun HolaMundo() {
    // Usamos recursos locales en lugar de URLs
    val menu = listOf(
        Dish("Pizza", R.drawable.pizza),
        Dish("Hamburguesa", R.drawable.hamburguesa),
        Dish("Tacos", R.drawable.tacos),
        Dish("Lasagna", R.drawable.lasagna),
        Dish("Spaghetti", R.drawable.spaghetti),
        Dish("Torta", R.drawable.torta),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // TopAppBar
        TopAppBar(
            title = { Text("Menú", color = Color.White, fontWeight = FontWeight.Bold) },
            backgroundColor = Color(0xFF83100F)
        )

        // Espaciado y lista de menú
        Spacer(modifier = Modifier.height(16.dp)) //density-independent pixels
        menuList(menu)
    }
}

@Composable
fun menuList(menu: List<Dish>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(menu) { dish ->
            dishItem(dish)
        }
    }
}

@Composable
fun dishItem(dish: Dish) {
    var count by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Usamos painterResource para cargar imágenes locales
            val painter = painterResource(id = dish.imageRes)

            // Imagen del platillo
            Image(
                painter = painter,
                contentDescription = dish.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            // Detalles del platillo
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = dish.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "Cantidad: $count",
                    style = MaterialTheme.typography.body2
                )
            }

            // Botón de decremento (solo si count es mayor a 0)
            IconButton(onClick = { if (count > 0) count-- }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Decrementar",
                    tint = Color.Red
                )
            }

            // Botón de incremento
            IconButton(onClick = {
                count++
                showDialog = true // Mostrar el diálogo cuando se incrementa
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Incrementar",
                    tint = Color.Blue
                )
            }

            // Mostrar el diálogo modal
            if (showDialog) {
                IncrementDialog(
                    onDismiss = { showDialog = false }, // Cerrar el diálogo
                    dishName = dish.name // Pasamos el nombre del plato al diálogo
                )
            }
        }
    }
}

@Composable
fun IncrementDialog(onDismiss: () -> Unit, dishName: String) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("¡Pediste $dishName!") }, // Usamos la interpolación aquí
        text = { Text("¡Excelente elección!") },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cerrar")
            }
        },
    )
}


data class Dish(val name: String, val imageRes: Int)
