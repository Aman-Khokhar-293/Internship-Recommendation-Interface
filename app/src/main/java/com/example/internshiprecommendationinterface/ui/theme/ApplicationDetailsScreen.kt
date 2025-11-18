package com.example.internshiprecommendationinterface.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internshiprecommendationinterface.R
import com.example.internshiprecommendationinterface.data.Application

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationDetailsScreen(
    application: Application, 
    onBackClick: () -> Unit, 
    onRejectClick: () -> Unit, 
    onShortlistClick: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.application_details), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onRejectClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f), contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(id = R.string.reject), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onShortlistClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary, contentColor = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Text(stringResource(id = R.string.shortlist), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.student),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(application.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    StatusChip(status = application.status)
                }

                Divider(modifier = Modifier.padding(vertical = 24.dp))
                InfoRow(icon = Icons.Default.Email, text = application.email)
                InfoRow(icon = Icons.Default.Work, text = application.jobTitle)
                InfoRow(icon = Icons.Default.DateRange, text = "Applied ${application.date}")
                
                Divider(modifier = Modifier.padding(vertical = 24.dp))
                Text(stringResource(id = R.string.experience), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(id = R.string.experience_placeholder), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))

                Spacer(modifier = Modifier.height(24.dp))
                Text(stringResource(id = R.string.skills), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    application.skills.forEach {
                        SkillChip(skill = it)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                Text(stringResource(id = R.string.resume), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(application.resume, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}