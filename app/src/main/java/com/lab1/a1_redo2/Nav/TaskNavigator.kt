package com.lab1.a1_redo2.Nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lab1.a1_redo2.Model.ApiClient
import com.lab1.a1_redo2.View.CreateAccountScreen
import com.lab1.a1_redo2.View.LogInScreen
import com.lab1.a1_redo2.View.TaskScreen
import com.lab1.a1_redo2.ViewModel.CreateAccountViewModel
import com.lab1.a1_redo2.ViewModel.LogInViewModel
import com.lab1.a1_redo2.ViewModel.SharedViewModel
import com.lab1.a1_redo2.ViewModel.TaskListViewModel

@Composable
fun TaskNavigator() {
    val sharedViewModel = SharedViewModel()
    val navController = rememberNavController()
    val apiClient = ApiClient.getInstance()

    val LOGIN = "log_in_screen"
    val TASK = "task_screen"
    val CREATE_ACC = "create_account_screen"

    fun goto(route: String) {
        navController.navigate(route)
    }

    NavHost(
        navController = navController,
        startDestination = LOGIN
    ) {
        composable(TASK) {
            val viewModel: TaskListViewModel = remember { TaskListViewModel(apiClient) }
            TaskScreen(
                viewModel = viewModel,
                sharedViewModel = sharedViewModel,
                switchScreen = { goto(LOGIN) }
            )
        }
        composable(LOGIN) {
            val viewModel: LogInViewModel = remember { LogInViewModel(apiClient) }
            LogInScreen(
                viewModel = viewModel,
                sharedViewModel = sharedViewModel,
                toTaskScreen = { goto(TASK) },
                toCreateAccountScreen = { goto(CREATE_ACC) })
        }
        composable(CREATE_ACC) {
            val viewModel: CreateAccountViewModel = remember { CreateAccountViewModel(apiClient) }
            CreateAccountScreen(
                viewModel = viewModel,
                sharedViewModel = sharedViewModel,
                switchScreen = { goto(LOGIN) },
                toTaskScreen = { goto(TASK) },
            )
        }
    }
}