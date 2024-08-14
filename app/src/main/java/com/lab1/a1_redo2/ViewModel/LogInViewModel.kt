package com.lab1.a1_redo2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lab1.a1_redo2.Data.LoginRequest
import com.lab1.a1_redo2.Data.UserResponse
import com.lab1.a1_redo2.Model.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response


class LogInViewModel(val api: ApiClient) : ViewModel() {

    private val apikey = "db98b5d4-1b18-46f6-bc3f-13add13ef441"

    val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    // Email Block
    val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    val _emailIsError = MutableLiveData<Boolean>()
    val emailIsError: LiveData<Boolean> = _emailIsError

    val _emailErrorText = MutableLiveData<String>()
    val emailErrorText: LiveData<String> = _emailErrorText

    // Password Block
    val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    val _passIsError = MutableLiveData<Boolean>()
    val passIsError: LiveData<Boolean> = _passIsError

    val _passErrorText = MutableLiveData<String>()
    val passErrorText: LiveData<String> = _passErrorText

    // Remember to set values to blank
    init {
        _userId.value = -1

        _email.value = ""
        _emailIsError.value = false
        _emailErrorText.value = ""

        _password.value = ""
        _passIsError.value = false
        _passErrorText.value = ""

    }

    fun updateUser(newUser: UserResponse) {
        _user.value = newUser
    }

    fun updateUserId(id: Int) {
        _userId.value = id
    }

    // Email
    fun updateEmailTextField(field: String) {
        _email.value = field
    }

    fun updateEmailError(newBool: Boolean) {
        _emailIsError.value = newBool
    }

    fun updateEmailErrorText(newLabel: String) {
        _emailErrorText.value = newLabel
    }

    // Password
    fun updatePasswordTextField(field: String) {
        _password.value = field
    }

    fun updatePassError(newBool: Boolean) {
        _passIsError.value = newBool
    }

    fun updatePassErrorText(newLabel: String) {
        _passErrorText.value = newLabel
    }

    // Everything else
    fun resetError() {
        _emailIsError.value = false
        _emailErrorText.value = ""

        _passIsError.value = false
        _passErrorText.value = ""
    }

    fun handleError(code: Int) {
        // Invalid email or pass
        resetError()
        if (code == 422) {
            if (!email.value!!.contains("@") or email.value!!.isBlank()) {
                updateEmailError(true)
                updateEmailErrorText("Invalid email input")
            }
            if (password.value!!.length < 8) {
                updatePassError(true)
                updatePassErrorText("Password must be at least 8 characters long")
            }

            // Not Found
        } else if (code == 404) {
            updateEmailError(true)
            updateEmailErrorText("Account not found")
            updatePassError(true)
            updatePassErrorText("Account not found")
        }
    }

    fun logIn(
        passUser: () -> Unit
    ) = runBlocking {
        var response: Response<UserResponse>? = null
        val job = launch {
            val user = LoginRequest(_email.value!!, _password.value!!)
            response = api.getService().logIn(apikey, user)
        }
        job.join()
        if (response!!.isSuccessful) {
            updateUser(response!!.body()!!)
            passUser()
        } else {
            val errCode = response!!.code()
            handleError(errCode)

        }
    }

    fun logInTest(
        email: String,
        password: String
    ) = runBlocking {
        val user = LoginRequest(email, password)
        val job = launch {
            val result = api.getService().logInTest(apikey, user)
        }
        job.join()
    }

}