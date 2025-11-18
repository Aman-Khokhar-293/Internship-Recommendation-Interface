package com.example.internshiprecommendationinterface.ui.theme

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internshiprecommendationinterface.R
import com.example.internshiprecommendationinterface.data.Job
import com.example.internshiprecommendationinterface.viewmodel.MainViewModel

@Composable
fun StudentDashboardScreen(viewModel: MainViewModel, onJobClick: (String) -> Unit) {
    val jobs by viewModel.jobs.collectAsState()
    val postedJobs by viewModel.postedJobs.collectAsState()
    val allJobs = jobs + postedJobs
    val uploadedResume by viewModel.uploadedResume.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // In a real app, you'd get the file URI from result.data
            viewModel.onResumeUploaded("my_resume.pdf")
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        item {
            Text(stringResource(id = R.string.student_dashboard), fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
            Text(stringResource(id = R.string.your_resume), fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            UploadResumeCard(uploadedResume = uploadedResume, onUploadClick = {
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "*/*" }
                launcher.launch(intent)
            })
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(id = R.string.matched_jobs), fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text(stringResource(id = R.string.matches_count, allJobs.size), fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(allJobs) { job ->
            JobCard(job = job, onClick = { onJobClick(job.id) })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun UploadResumeCard(uploadedResume: String?, onUploadClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onUploadClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = if (uploadedResume != null) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uploadedResume != null) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Uploaded", tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(uploadedResume, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.tertiary)
            } else {
                Icon(Icons.Default.CloudUpload, contentDescription = stringResource(id = R.string.upload_resume), tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(id = R.string.upload_resume), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(stringResource(id = R.string.upload_resume_description), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun JobCard(job: Job, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(Icons.Default.Business, contentDescription = stringResource(id = R.string.company_label), modifier = Modifier.size(40.dp).align(Alignment.CenterVertically), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(job.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(job.company, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(job.location, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(job.type, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                }
                Text(job.salary, fontSize = 14.sp, color = MaterialTheme.colorScheme.tertiary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(job.description, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), maxLines = 2)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(stringResource(id = R.string.match_percentage, job.match), color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold)
            }
        }
    }
}