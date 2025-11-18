package com.example.internshiprecommendationinterface.viewmodel

import androidx.lifecycle.ViewModel
import com.example.internshiprecommendationinterface.data.Application
import com.example.internshiprecommendationinterface.data.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    val jobs: StateFlow<List<Job>> = _jobs

    private val _postedJobs = MutableStateFlow<List<Job>>(emptyList())
    val postedJobs: StateFlow<List<Job>> = _postedJobs

    private val _applications = MutableStateFlow<List<Application>>(emptyList())
    val applications: StateFlow<List<Application>> = _applications

    private val _uploadedResume = MutableStateFlow<String?>(null)
    val uploadedResume: StateFlow<String?> = _uploadedResume

    fun getJob(jobId: String): Job? {
        return (_jobs.value + _postedJobs.value).find { it.id == jobId }
    }

    fun getApplication(applicationId: String): Application? {
        return _applications.value.find { it.id == applicationId }
    }

    fun addJob(jobTitle: String, companyName: String, description: String, requiredSkills: List<String>, location: String) {
        val newJob = Job(
            title = jobTitle,
            company = companyName,
            location = location,
            type = "Internship", // Default to Internship
            salary = "Not specified", // Default value
            description = description,
            requiredSkills = requiredSkills,
            requiredExperience = 0, // Default value
            match = (80..95).random() // Placeholder match percentage
        )
        _postedJobs.value = _postedJobs.value + newJob
    }

    fun deleteJob(jobId: String) {
        _postedJobs.value = _postedJobs.value.filter { it.id != jobId }
    }

    fun onResumeUploaded(fileName: String) {
        _uploadedResume.value = fileName
    }

    fun applyForJob(job: Job) {
        _uploadedResume.value?.let { resume ->
            val newApplication = Application(
                name = "New Applicant", // Replace with actual user data
                email = "applicant@email.com", // Replace with actual user data
                jobTitle = job.title,
                resume = resume,
                date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()),
                skills = listOf("Kotlin", "Jetpack Compose", "Android"), // Replace with actual skills
                status = "Pending"
            )
            _applications.value = _applications.value + newApplication
        }
    }
}