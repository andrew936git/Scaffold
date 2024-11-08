package com.example.scaffold

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class AppView {
    private var index = 0

    @Composable
    fun MyScaffold(context: Context){
        val contactList = arrayListOf<String>()
        val input = rememberSaveable {
            mutableStateOf("")
        }

        Scaffold(
            topBar = {
                TopBar(context, contactList)
            },
            bottomBar = {
                BottomBar(context, contactList)
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { contactList.add(input.value)
                        input.value = "" },
                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                ) {
                    Icon(Icons.Filled.Add, "Add Contact")
                }
            }
        )
        {  innerPadding ->
            Content(innerPadding, input, contactList)

        }
    }

    @Composable
    private fun Content(
        innerPadding: PaddingValues,
        input: MutableState<String>,
        contactList: ArrayList<String>
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)

        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Black)
                    .padding(4.dp),
                value = input.value,
                placeholder = { Text(text = "Введите контакт") },
                onValueChange = { newText ->
                    input.value = newText
                })
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(contactList) { contact ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .border(2.dp, Color.White)
                            .padding(2.dp)
                            .clickable { index = contactList.indexOf(contact) }

                    ) {
                        Text(
                            text = contact,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(start = 5.dp)

                        )
                        Spacer(modifier = Modifier.weight(1f, true))
                        IconButton(
                            onClick = {
                                contactList.remove(contact)
                                input.value = "."
                                input.value = ""
                            }
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                "",
                                tint = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.padding(6.dp))
                    }
                }
            }
        }
    }

    @Composable

    private fun BottomBar(context: Context, contactList: ArrayList<String>) {
       BottomAppBar(actions = {
           IconButton(onClick = { Toast.makeText(context, "Контакт отредактирован ${contactList[index]}", Toast.LENGTH_SHORT).show() }) {
               Icon(Icons.Filled.Edit, "Edit Contact")
           }
           Spacer(modifier = Modifier.weight(1f, true))
           IconButton(onClick = { Toast.makeText(context, "Сообщение отправлено ${contactList[index]}", Toast.LENGTH_SHORT).show() }) {
               Icon(Icons.Filled.Send, "Send Message")
           }
       })
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar(context: Context, contactList: ArrayList<String>) {
        TopAppBar(
            title = { Text(text = "Телефонная книга") },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Menu, "")
                }
            },
            actions = {
                IconButton(onClick = {
                    Toast.makeText(context, "Звонок совершен ${contactList[index]}", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Filled.Call, "Exit")
                }
                val activity = (LocalContext.current as? Activity)
                IconButton(onClick = {
                    activity?.finishAffinity()
                }) {
                    Icon(Icons.Filled.Close, "Call")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                titleContentColor = Color.White,
                navigationIconContentColor = MaterialTheme.colorScheme.primaryContainer,
                actionIconContentColor = Color.LightGray
            )
        )
    }
}