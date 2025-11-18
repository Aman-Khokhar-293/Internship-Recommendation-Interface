package com.example.internshiprecommendationinterface.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internshiprecommendationinterface.R
import com.example.internshiprecommendationinterface.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostNewJobScreen(viewModel: MainViewModel, onPostJobClick: () -> Unit, onBackClick: () -> Unit) {
    var jobTitle by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var jobDescription by remember { mutableStateOf("") }
    var requiredSkills by remember { mutableStateOf("") }
    var jobType by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var salaryRange by remember { mutableStateOf("") }
    var requiredExperience by remember { mutableStateOf("0") }

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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Text(stringResource(id = R.string.job_title_label), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                StyledTextField(value = jobTitle, onValueChange = { jobTitle = it }, placeholder = "e.g. Senior React Developer")
                Spacer(modifier = Modifier.height(16.dp))

                Text(stringResource(id = R.string.company_name_label), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                StyledTextField(value = companyName, onValueChange = { companyName = it }, placeholder = stringResource(id = R.string.company_hint))
                Spacer(modifier = Modifier.height(16.dp))

                Text(stringResource(id = R.string.job_description_label), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                StyledTextField(value = jobDescription, onValueChange = { jobDescription = it }, placeholder = "Describe the role, responsibilities, and requirements...", singleLine = false, modifier = Modifier.height(150.dp))
                Spacer(modifier = Modifier.height(16.dp))

                Text(stringResource(id = R.string.required_skills_label), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                StyledTextField(value = requiredSkills, onValueChange = { requiredSkills = it }, placeholder = "e.g. React, TypeScript, Redux")
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(Modifier.fillMaxWidth()) {
                    Column(Modifier.weight(1f)) {
                        Text(stringResource(id = R.string.job_type_label), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        JobTypeDropdown(selectedType = jobType, onTypeSelected = { jobType = it })
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Required Experience (years)", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        StyledTextField(value = requiredExperience, onValueChange = { requiredExperience = it }, placeholder = "0")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(Modifier.fillMaxWidth()) {
                    Column(Modifier.weight(1f)) {
                        Text(stringResource(id = R.string.location_label), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        StyledTextField(value = location, onValueChange = { location = it }, placeholder = "e.g. Remote, New York, etc.")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text(stringResource(id = R.string.salary_range_label), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        StyledTextField(value = salaryRange, onValueChange = { salaryRange = it }, placeholder = "e.g. $80k - $120k")
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onBackClick, 
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Cancel", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { 
                            viewModel.addJob(jobTitle, companyName, jobDescription, requiredSkills.split(",").map { it.trim() }, location)
                            onPostJobClick()
                        },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(stringResource(id = R.string.create_job), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
                 Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = singleLine,
        readOnly = readOnly,
        trailingIcon = trailingIcon
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobTypeDropdown(selectedType: String, onTypeSelected: (String) -> Unit) {
    val jobTypes = listOf("Full-time", "Part-time", "Internship", "Contract")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        StyledTextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            placeholder = "Select type",
            modifier = Modifier.menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            jobTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}