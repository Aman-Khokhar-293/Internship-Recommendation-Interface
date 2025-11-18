package com.example.internshiprecommendationinterface.data

import java.util.UUID

data class Job(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val company: String,
    val location: String,
    val type: String,
    val salary: String,
    val description: String,
    val requiredSkills: List<String>,
    val requiredExperience: Int,
    val match: Int
)

data class Application(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val jobTitle: String,
    val resume: String,
    val date: String,
    val skills: List<String>,
    val status: String
)
