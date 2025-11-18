package com.example.internshiprecommendationinterface.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internshiprecommendationinterface.R
import com.example.internshiprecommendationinterface.data.Application
import com.example.internshiprecommendationinterface.data.Job
import com.example.internshiprecommendationinterface.viewmodel.MainViewModel

@Composable
fun RecruiterDashboardScreen(
    viewModel: MainViewModel, 
    onApplicationClick: (String) -> Unit,
    onPostJobClick: () -> Unit,
    onJobClick: (String) -> Unit
) {
    val applications by viewModel.applications.collectAsState()
    val postedJobs by viewModel.postedJobs.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        item {
            Text(stringResource(id = R.string.recruiter_dashboard), fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
            PostNewJobCard(onClick = onPostJobClick)
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Posted Jobs", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text("${postedJobs.size} total", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(postedJobs) { job ->
            JobCard(job = job, onClick = { onJobClick(job.id) })
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(id = R.string.applications), fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text(stringResource(id = R.string.applications_total, applications.size), fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(applications) { application ->
            ApplicationCard(application = application, onClick = { onApplicationClick(application.id) })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PostNewJobCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.post_new_job), tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(stringResource(id = R.string.post_new_job), fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                Text(stringResource(id = R.string.post_new_job_description), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
fun ApplicationCard(application: Application, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = "Applicant", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(50)).padding(8.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(application.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(application.email, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
                StatusChip(status = application.status)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(application.jobTitle, fontWeight = FontWeight.Bold)
            Text("${application.resume} Applied ${application.date}", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                application.skills.forEach {
                    SkillChip(skill = it)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val color = when (status) {
        "Pending" -> MaterialTheme.colorScheme.error
        "Shortlisted" -> MaterialTheme.colorScheme.tertiary
        else -> Color.Gray
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(status, color = color, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SkillChip(skill: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(skill, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
    }
}