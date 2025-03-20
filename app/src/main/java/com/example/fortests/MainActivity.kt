package com.example.fortests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: UserViewModel = hiltViewModel()

            LaunchedEffect(key1 = Unit, block = {
                viewModel.fetchAllImagesCount()
            })

            Column {
                RandomDuckImage("https://random-d.uk/api/185.jpg")
                Text(modifier = Modifier, text = viewModel.image_сount.toString())
                Main(viewModel)
            }


        }
    }
}


@Composable
fun Main(vm: UserViewModel = viewModel()) {
    val userList by vm.userList.observeAsState(listOf())
    Column {
        Button({ vm.loadImages() }, Modifier.padding(8.dp)) { Text("Add", fontSize = 22.sp) }
        UserList(users = userList, delete = { vm.deleteUser(it) }, display = { vm.displayImage(it) })
    }
}

@Composable
fun UserList(users: List<User>, delete: (Int) -> Unit, display: (User) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item { UserTitleRow() }
        items(users) { u -> UserRow(u, { delete(u.id) }, { display(u) }) }
    }
}

@Composable
fun UserRow(user: User, delete: (Int) -> Unit, display: (User) -> Unit) {
    Row(Modifier
        .fillMaxWidth()
        .padding(5.dp)) {
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
    Row(Modifier
        .background(Color.LightGray)
        .fillMaxWidth()
        .padding(5.dp)) {
        Text("Id", color = Color.White, modifier = Modifier.weight(0.1f), fontSize = 22.sp)
        Text("Name", color = Color.White, modifier = Modifier.weight(0.2f), fontSize = 22.sp)

        Spacer(Modifier.weight(0.2f))
    }
}
