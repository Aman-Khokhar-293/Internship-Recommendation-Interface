package com.example.internshiprecommendationinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.internshiprecommendationinterface.navigation.NavGraph
import com.example.internshiprecommendationinterface.ui.theme.InternshipRecommendationInterfaceTheme
import com.example.internshiprecommendationinterface.ui.theme.RoleSelectionScreen
import com.example.internshiprecommendationinterface.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InternshipRecommendationInterfaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InternshipRecommendationApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun InternshipRecommendationApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InternshipRecommendationInterfaceTheme {
        RoleSelectionScreen(onStudentClick = {}, onRecruiterClick = {})
    }
}