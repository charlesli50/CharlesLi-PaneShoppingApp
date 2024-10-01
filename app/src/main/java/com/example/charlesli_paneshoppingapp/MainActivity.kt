package com.example.charlesli_paneshoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.charlesli_paneshoppingapp.ui.theme.CharlesLiPaneShoppingAppTheme

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharlesLiPaneShoppingAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Product(val name: String, val price: String, val description: String)

val products = listOf(
    Product("Product A", "$100", "This is a great product A."),
    Product("Product B", "$150", "This is product B with more features."),
    Product("Product C", "$200", "Premium product C.")
)

@Composable
fun ProductApp(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val (selectedProduct, setSelectedProduct) = remember { mutableStateOf<Product?>(null) }

    if (isPortrait) {
        if (selectedProduct == null) {
            ProductList(products = products, onProductClick = setSelectedProduct)
        } else {
            ProductDetails(product = selectedProduct, onBackClick = { setSelectedProduct(null) })
        }
    } else {
        Row {
            ProductList(products = products, onProductClick = setSelectedProduct, modifier = Modifier.weight(1f))
            ProductDetails(product = selectedProduct, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ProductList(products: List<Product>, onProductClick: (Product) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(products) { product ->
            Text(
                text = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductClick(product) }
                    .padding(16.dp)
            )
            Divider()
        }
    }
}

@Composable
fun ProductDetails(product: Product?, onBackClick: (() -> Unit)? = null, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (onBackClick != null) {
            Button(onClick = onBackClick) {
                Text("Back")
            }
        }

        if (product != null) {
            Text(text = product.name,  modifier = Modifier.padding(bottom = 8.dp))
            Text(text = product.price, modifier = Modifier.padding(bottom = 16.dp))
            Text(text = product.description)
        } else {
            Text(text = "Select a product to view details.")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductAppPreview() {
    ProductApp()
}