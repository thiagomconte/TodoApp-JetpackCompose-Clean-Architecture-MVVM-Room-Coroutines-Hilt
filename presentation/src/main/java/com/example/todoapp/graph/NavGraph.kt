package com.example.todoapp.graph

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.todoapp.add_edit_todo.components.AddEditTodoScreen
import com.example.todoapp.todo_list.components.TodoListScreen
import com.example.todoapp.utils.Constants
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun NavGraph() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Constants.Routes.TODO_LIST) {
        composable(route = Constants.Routes.TODO_LIST, enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        }) {
            TodoListScreen(onNavigate = { event ->
                navController.navigate(event.route)
            })
        }
        composable(
            route = "${Constants.Routes.ADD_EDIT}?id={id}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditTodoScreen(
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
