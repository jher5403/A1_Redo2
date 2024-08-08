package com.lab1.a1_redo2.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lab1.a1_redo2.R
import com.lab1.a1_redo2.UIText.UIText
import com.lab1.a1_redo2.ViewModel.CreateAccountViewModel
import com.lab1.a1_redo2.ViewModel.SharedViewModel

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel = viewModel(),
    sharedViewModel: SharedViewModel = viewModel(),
    switchScreen: () -> Unit,
    toTaskScreen: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.4F)
                    .fillMaxWidth()
                    .padding(paddingValues),

                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = UIText.StringResource(R.string.todo_label).asString(),
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.8F),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val nameTextField = viewModel.name.observeAsState().value!!
                val nameError = viewModel.nameIsError.observeAsState().value!!
                val nameErrorText = viewModel.nameErrorText.observeAsState().value!!

                val emailTextField = viewModel.email.observeAsState().value!!
                val emailError = viewModel.emailIsError.observeAsState().value!!
                val emailErrorText = viewModel.emailErrorText.observeAsState().value!!

                val passwordTextField = viewModel.password.observeAsState().value!!
                val passwordError = viewModel.passIsError.observeAsState().value!!
                val passwordErrorText = viewModel.passErrorText.observeAsState().value!!


                // Name
                OutlinedTextField(
                    value = nameTextField,
                    onValueChange = { viewModel.updateNameTextField(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(UIText.StringResource(R.string.name_label).asString()) },
                    isError = nameError,
                    singleLine = true,
                    supportingText = {
                        if (nameError) {
                            Text(text = nameErrorText)
                        }
                    }
                )

                // Email
                OutlinedTextField(
                    value = emailTextField,
                    onValueChange = { viewModel.updateEmailTextField(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(UIText.StringResource(R.string.email_label).asString()) },
                    isError = emailError,
                    singleLine = true,
                    supportingText = {
                        if (emailError) {
                            Text(text = emailErrorText)
                        }
                    }
                )

                //Password
                OutlinedTextField(
                    value = passwordTextField,
                    onValueChange = { viewModel.updatePasswordTextField(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(UIText.StringResource(R.string.password_label).asString()) },
                    isError = passwordError,
                    singleLine = true,
                    supportingText = {
                        if (passwordError) {
                            Text(text = passwordErrorText)
                        }
                    }
                )
                // Create Account Button
                Button(
                    onClick = {
                        viewModel.registerUser {
                            sharedViewModel.passData(viewModel.user.value!!)
                            toTaskScreen()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(text = UIText.StringResource(R.string.create_account_label).asString())
                }
                // Login Button
                TextButton(
                    modifier = Modifier,
                    onClick = { switchScreen() }
                ) {
                    Text(text = UIText.StringResource(R.string.login_label).asString())
                }
            }
        }
    }
}