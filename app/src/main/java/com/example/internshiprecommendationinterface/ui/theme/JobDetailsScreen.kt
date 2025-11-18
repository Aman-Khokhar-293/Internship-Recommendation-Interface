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
import com.example.internshiprecommendationinterface.data.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsScreen(
    job: Job,
    onBackClick: () -> Unit,
    onApplyClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isPostedJob: Boolean
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.job_details), fontWeight = FontWeight.Bold) },
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
            if (isPostedJob) {
                Button(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete Job", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onApplyClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(id = R.string.apply_now), fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                        imageVector = Icons.Default.Business,
                        contentDescription = stringResource(id = R.string.company_label),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(job.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(job.company, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(stringResource(id = R.string.match_percentage, job.match), color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold)
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 24.dp))
                DetailRow(icon = Icons.Default.LocationOn, text = job.location)
                DetailRow(icon = Icons.Default.Work, text = job.type)
                DetailRow(icon = Icons.Default.MonetizationOn, text = job.salary)
                DetailRow(icon = Icons.Default.DateRange, text = stringResource(id = R.string.posted_date_placeholder))
                Divider(modifier = Modifier.padding(vertical = 24.dp))
                Text(stringResource(id = R.string.description), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(job.description, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(24.dp))
                Text(stringResource(id = R.string.requirements), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(id = R.string.requirements_placeholder), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun DetailRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}
