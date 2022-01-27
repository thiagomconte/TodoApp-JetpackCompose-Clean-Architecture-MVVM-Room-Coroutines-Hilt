package com.example.todoapp.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.add_edit_todo.components.AddEditTodoScreen
import com.example.todoapp.todo_list.components.TodoListScreen
import com.example.todoapp.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Constants.Routes.ADD_EDIT) {
        composable(route = Constants.Routes.TODO_LIST) {
            TodoListScreen(onNavigate = { event ->
                navController.navigate(event.route)
            })
        }
        composable(
            route = "${Constants.Routes.ADD_EDIT}?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditTodoScreen()
        }
    }
}
