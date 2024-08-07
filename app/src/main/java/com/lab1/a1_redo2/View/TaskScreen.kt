package com.lab1.a1_redo2.View

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lab1.a1_redo2.Data.NewTaskResponse
import com.lab1.a1_redo2.R
import com.lab1.a1_redo2.UIText.UIText
import com.lab1.a1_redo2.ViewModel.SharedViewModel
import com.lab1.a1_redo2.ViewModel.TaskListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    viewModel: TaskListViewModel = viewModel(),
    sharedViewModel: SharedViewModel = viewModel(),
    switchScreen: () -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.updateUser(sharedViewModel.data!!)
    }

    LaunchedEffect(Unit) {
        viewModel.getTaskList()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray
                ),
                title = {
                    Text(text = UIText.StringResource(R.string.todo_label).asString())
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.logOut()
                        switchScreen()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            UIText.StringResource(R.string.task_screen_floating_description)
                                .asString(),
                            modifier = Modifier
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showSheet = true
                },
                content = {
                    Icon(
                        Icons.Filled.Add,
                        UIText.StringResource(R.string.task_screen_floating_description).asString()
                    )
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
            {
                TaskListBlock(viewModel)
            }
            if (showSheet) {
                AddTaskMenuView(viewModel, context, closeSheet = { showSheet = false })
            }
        }
    )
}

// Displays columns of Tasks
@Composable
fun TaskListBlock(
    viewModel: TaskListViewModel = viewModel(),
) {
    val taskList: List<NewTaskResponse> by viewModel.taskList.observeAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .padding(12.dp)
    ) {

        items(taskList) { task ->
            TaskBlock(
                viewModel,
                task
            )
        }
    }
}

@Composable
fun TaskBlock(
    viewModel: TaskListViewModel = viewModel(),
    task: NewTaskResponse
) {
    var isChecked by remember { mutableStateOf(task.completed) }

    Row(
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(320.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = task.description,
            )
        }
        Checkbox(
            modifier = Modifier,
            checked = isChecked,
            onCheckedChange = {
                viewModel.updateTask(task, it)
                isChecked = it
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskMenuView(
    viewModel: TaskListViewModel = viewModel(),
    context: Context,
    closeSheet: () -> Unit
) {
    val textField = viewModel.taskTextField.observeAsState().value!!
    val labelText = viewModel.labelText.observeAsState().value!!
    val isError = viewModel.isError.observeAsState().value!!

    fun resetFields() {
        viewModel.updateTaskTextField("")
        viewModel.updateError(false)
        viewModel.updateLabelText("Enter new task")
    }
    ModalBottomSheet(
        onDismissRequest = { closeSheet() },
        modifier = Modifier,
        content = {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = textField,
                    onValueChange = { viewModel.updateTaskTextField(it) },
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    label = { Text(labelText) },
                    isError = isError,
                    singleLine = true,
                    trailingIcon = {
                        // Clear Button
                        IconButton(
                            onClick = { viewModel.updateTaskTextField("") },
                            modifier = Modifier
                                .border(width = 2.dp, Color.Black, CircleShape)
                                .size(20.dp),
                        ) {
                            Icon(
                                Icons.Filled.Clear,
                                UIText.StringResource(R.string.task_screen_floating_description)
                                    .asString(),
                                modifier = Modifier
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Save Button
                Button(
                    onClick = {
                        if (textField != "") {
                            viewModel.addTask()
                            viewModel.getTaskList()
                            resetFields()
                            closeSheet()
                        } else {
                            viewModel.updateError(true)
                            viewModel.updateLabelText(
                                UIText.StringResource(R.string.invalid_task_error).asString(context)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Text(text = UIText.StringResource(R.string.save_button_label).asString())
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Cancel Button
                OutlinedButton(
                    onClick = {
                        resetFields()
                        closeSheet()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Text(text = UIText.StringResource(R.string.cancel_button_label).asString())
                }
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
    )
}