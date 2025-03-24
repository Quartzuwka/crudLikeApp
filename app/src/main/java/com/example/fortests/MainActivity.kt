package com.example.fortests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fortests.components.RandomDuckImage
import com.example.fortests.db.User
import com.example.fortests.navigation.Destinations
import com.example.fortests.navigation.TopLevelDestinations
import com.example.fortests.second.SecondScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val viewModel: UserViewModel = hiltViewModel()

            LaunchedEffect(key1 = Unit, block = {
                viewModel.fetchAllImagesCount()
            })
            Surface {
                val statusBarHeight = WindowInsets.statusBars
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = statusBarHeight.asPaddingValues().calculateTopPadding()
                        )
                        .consumeWindowInsets(WindowInsets.statusBars)
                        .statusBarsPadding(),
                    bottomBar = {
                        BottomBarNav(navController = navController)
                    }) { innerPadding ->
                    AppNavigation(
                        navController = navController, modifier = Modifier.padding(innerPadding)
                    )
                }
            }

        }
    }
}

@Composable
fun BottomBarNav(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination
    TopLevelDestinations.entries.map { it.route::class }.any { route ->

        currentDestination?.hierarchy?.any {
            it.hasRoute(route)
        } == true
    }
    BottomAppBar(
        modifier = Modifier.clip(shape = RoundedCornerShape(30.dp)),
        containerColor = Color(0xFF6A6565),
        contentColor = Color(0xFF393838)
    ) {

        TopLevelDestinations.entries.map { bottomNavigationItem ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.hasRoute(bottomNavigationItem.route::class) } == true

            if (currentDestination != null) {
                NavigationBarItem(
                    selected = isSelected, colors = NavigationBarItemColors(
                    selectedIconColor = Color(0xFF232222),
                    selectedTextColor = Color(0xFF999797),
                    selectedIndicatorColor = Color(0xFFF2EDED),
                    unselectedIconColor = Color(0xFFB0ACAC),
                    unselectedTextColor = Color(0xFF625F5F),
                    disabledIconColor = Color.Green,
                    disabledTextColor = Color.Green
                ), onClick = {
                    navController.navigate(bottomNavigationItem.route)
                }, icon = {
                    Icon(
                        imageVector = if (isSelected) bottomNavigationItem.selectedIcon else bottomNavigationItem.unselectedIcon,
                        contentDescription = bottomNavigationItem.label
                    )
                }, alwaysShowLabel = isSelected, label = {
                    Text(bottomNavigationItem.label)
                })
            }
        }
    }
}


@Composable
fun Main(vm: UserViewModel = viewModel()) {
    val userList by vm.userList.observeAsState(listOf())
    Column {
        Box {
            RandomDuckImage(vm.imageString)
        }
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button({ vm.loadImages() }, Modifier.padding(8.dp)) { Text("Add", fontSize = 22.sp) }
            Button({ vm.deleteAll() }, Modifier.padding(8.dp)) {
                Text("Delete all", fontSize = 22.sp)
            }
        }
        UserList(
            users = userList,
            delete = { vm.deleteUser(it) },
            display = { vm.displayImage(it) })
    }
}

@Composable
fun UserList(users: List<User>, delete: (Int) -> Unit, display: (User) -> Unit) {
    LazyColumn(Modifier
        .fillMaxWidth()
        .padding(bottom = 80.dp)) {
        item { UserTitleRow() }
        items(users) { u -> UserRow(u, { delete(u.id) }, { display(u) }) }
    }
}

@Composable
fun UserRow(user: User, delete: (Int) -> Unit, display: (User) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(user.id.toString(), Modifier.weight(0.1f), fontSize = 22.sp)
        Text(user.name, Modifier.weight(0.2f), fontSize = 22.sp)
        Text(
            "Display",
            Modifier
                .weight(0.2f)
                .clickable { display(user) },
            color = Color(0xFF6650a4),
            fontSize = 22.sp
        )
        Text(
            "Delete",
            Modifier
                .weight(0.2f)
                .clickable { delete(user.id) },
            color = Color(0xFF6650a4),
            fontSize = 22.sp
        )
    }
}

@Composable
fun UserTitleRow() {
    Row(
        Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text("Id", color = Color.White, modifier = Modifier.weight(0.1f), fontSize = 22.sp)
        Text("Name", color = Color.White, modifier = Modifier.weight(0.2f), fontSize = 22.sp)

        Spacer(Modifier.weight(0.2f))
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    val viewModel: UserViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Destinations.One) {

        composable<Destinations.One> {
            Main(viewModel)
        }
        composable<Destinations.Two> {
            SecondScreen()
        }
    }
}


