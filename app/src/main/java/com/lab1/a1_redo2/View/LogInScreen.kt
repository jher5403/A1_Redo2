package com.lab1.a1_redo2.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.lab1.a1_redo2.ViewModel.LogInViewModel
import com.lab1.a1_redo2.ViewModel.SharedViewModel

@Composable
fun LogInScreen(
    viewModel: LogInViewModel = viewModel(),
    sharedViewModel: SharedViewModel = viewModel(),
    toCreateAccountScreen: () -> Unit,
    toTaskScreen: () -> Unit
) {
    Scaffold(
        modifier = Modifier.padding(12.dp)
    ) { paddingValues ->
        Column {
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
                    .fillMaxHeight(0.8F)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val emailTextField = viewModel.email.observeAsState().value!!
                val emailError = viewModel.emailIsError.observeAsState().value!!
                val emailErrorLabel = viewModel.emailErrorText.observeAsState().value!!

                val passwordTextField = viewModel.password.observeAsState().value!!
                val passwordError = viewModel.passIsError.observeAsState().value!!
                val passwordErrorLabel = viewModel.passErrorText.observeAsState().value!!

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
                            Text(text = emailErrorLabel)
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
                            Text(text = passwordErrorLabel)
                        }
                    }
                )

                // Log In Button, add load screen?
                Button(
                    onClick = {

                        viewModel.logIn {
                            sharedViewModel.passData(viewModel.user.value!!)
                            toTaskScreen()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(text = UIText.StringResource(R.string.login_label).asString())
                }

                // Switch Screen Button
                TextButton(
                    onClick = { toCreateAccountScreen() }
                ) {
                    Text(text = UIText.StringResource(R.string.create_account_label).asString())
                }
            }
        }

    }
}
