package com.example.internshiprecommendationinterface.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.internshiprecommendationinterface.ui.theme.*
import com.example.internshiprecommendationinterface.viewmodel.MainViewModel

object Routes {
    const val ROLE_SELECTION = "role_selection"
    const val STUDENT_DASHBOARD = "student_dashboard"
    const val RECRUITER_FLOW = "recruiter_flow"
    const val RECRUITER_DASHBOARD = "recruiter_dashboard"
    const val POST_JOB = "post_job"
    const val JOB_DETAILS = "job_details/{jobId}"
    const val APPLICATION_DETAILS = "application_details/{applicationId}"

    fun jobDetails(jobId: String): String = "job_details/$jobId"
    fun applicationDetails(applicationId: String): String = "application_details/$applicationId"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ROLE_SELECTION,
        modifier = modifier
    ) {
        composable(Routes.ROLE_SELECTION) {
            RoleSelectionScreen(
                onStudentClick = { navController.navigate(Routes.STUDENT_DASHBOARD) },
                onRecruiterClick = { navController.navigate(Routes.RECRUITER_FLOW) }
            )
        }
        composable(Routes.STUDENT_DASHBOARD) {
            StudentDashboardScreen(
                viewModel = viewModel,
                onJobClick = { jobId -> navController.navigate(Routes.jobDetails(jobId)) }
            )
        }
        recruiterGraph(navController, viewModel)
        composable(
            route = Routes.JOB_DETAILS,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")
            val job = jobId?.let { viewModel.getJob(it) }
            if (job != null) {
                JobDetailsScreen(
                    job = job,
                    onBackClick = { navController.popBackStack() },
                    onApplyClick = { 
                        viewModel.applyForJob(job)
                        navController.popBackStack() 
                    },
                    onDeleteClick = {},
                    isPostedJob = false
                )
            } else {
                ErrorScreen { navController.popBackStack() }
            }
        }
    }
}

fun NavGraphBuilder.recruiterGraph(navController: NavHostController, viewModel: MainViewModel) {
    navigation(
        startDestination = Routes.RECRUITER_DASHBOARD,
        route = Routes.RECRUITER_FLOW
    ) {
        composable(Routes.RECRUITER_DASHBOARD) {
            RecruiterDashboardScreen(
                viewModel = viewModel,
                onApplicationClick = { appId -> navController.navigate(Routes.applicationDetails(appId)) },
                onPostJobClick = { navController.navigate(Routes.POST_JOB) },
                onJobClick = { jobId -> navController.navigate(Routes.jobDetails(jobId)) }
            )
        }
        composable(Routes.POST_JOB) {
            PostNewJobScreen(
                viewModel = viewModel,
                onPostJobClick = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = Routes.JOB_DETAILS,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")
            val job = jobId?.let { viewModel.getJob(it) }
            if (job != null) {
                JobDetailsScreen(
                    job = job,
                    onBackClick = { navController.popBackStack() },
                    onApplyClick = {},
                    onDeleteClick = {
                        viewModel.deleteJob(job.id)
                        navController.popBackStack()
                    },
                    isPostedJob = true
                )
            } else {
                ErrorScreen { navController.popBackStack() }
            }
        }
        composable(
            route = Routes.APPLICATION_DETAILS,
            arguments = listOf(navArgument("applicationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val applicationId = backStackEntry.arguments?.getString("applicationId")
            val application = applicationId?.let { viewModel.getApplication(it) }
            if (application != null) {
                ApplicationDetailsScreen(
                    application = application,
                    onBackClick = { navController.popBackStack() },
                    onRejectClick = { navController.popBackStack() }, // Replace with actual reject logic
                    onShortlistClick = { navController.popBackStack() } // Replace with actual shortlist logic
                )
            } else {
                ErrorScreen { navController.popBackStack() }
            }
        }
    }
}

@Composable
fun ErrorScreen(onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Sorry, something went wrong.")
    }
    LaunchedEffect(Unit) {
        onBackClick()
    }
}