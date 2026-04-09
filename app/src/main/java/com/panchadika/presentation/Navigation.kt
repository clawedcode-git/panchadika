package com.panchadika.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.panchadika.presentation.conversation.ConversationListScreen
import com.panchadika.presentation.settings.SettingsScreen
import com.panchadika.presentation.thread.ThreadScreen
import com.panchadika.presentation.newmessage.NewMessageScreen

sealed class Screen(val route: String) {
    data object ConversationList : Screen("conversations")
    data object Thread : Screen("thread/{threadId}/{contactName}/{address}") {
        fun createRoute(threadId: Long, contactName: String, address: String) =
            "thread/$threadId/${contactName.replace("/", "%2F")}/${address.replace("/", "%2F")}"
    }
    data object Settings : Screen("settings")
    data object NewMessage : Screen("new_message")
}

@Composable
fun PanchadikaNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ConversationList.route
    ) {
        composable(Screen.ConversationList.route) {
            ConversationListScreen(
                onConversationClick = { conversation ->
                    navController.navigate(
                        Screen.Thread.createRoute(
                            threadId = conversation.id,
                            contactName = conversation.contactName ?: conversation.address,
                            address = conversation.address
                        )
                    )
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onNewMessageClick = {
                    navController.navigate(Screen.NewMessage.route)
                }
            )
        }

        composable(
            route = Screen.Thread.route,
            arguments = listOf(
                navArgument("threadId") { type = NavType.LongType },
                navArgument("contactName") { type = NavType.StringType },
                navArgument("address") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val threadId = backStackEntry.arguments?.getLong("threadId") ?: 0L
            val contactName = backStackEntry.arguments?.getString("contactName") ?: ""
            val address = backStackEntry.arguments?.getString("address") ?: ""

            ThreadScreen(
                threadId = threadId,
                contactName = contactName,
                address = address,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.NewMessage.route) {
            NewMessageScreen(
                onBackClick = { navController.popBackStack() },
                onMessageSent = { address ->
                    navController.popBackStack()
                    navController.navigate(
                        Screen.Thread.createRoute(
                            threadId = 0L,
                            contactName = address,
                            address = address
                        )
                    )
                }
            )
        }
    }
}

fun NavHostController.canGoBack(): Boolean = currentBackStackEntry != null