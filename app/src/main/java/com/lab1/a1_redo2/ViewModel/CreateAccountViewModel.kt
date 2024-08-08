package com.lab1.a1_redo2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lab1.a1_redo2.Data.RegisterRequest
import com.lab1.a1_redo2.Data.UserResponse
import com.lab1.a1_redo2.Model.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response


class CreateAccountViewModel(val api: ApiClient) : ViewModel() {
    private val apikey = "db98b5d4-1b18-46f6-bc3f-13add13ef441"

    val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    // Name Block
    val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    val _nameIsError = MutableLiveData<Boolean>()
    val nameIsError: LiveData<Boolean> = _nameIsError

    val _nameErrorText = MutableLiveData<String>()
    val nameErrorText: LiveData<String> = _nameErrorText

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

    init {
        _name.value = ""
        _nameIsError.value = false
        _nameErrorText.value = ""

        _email.value = ""
        _emailIsError.value = false
        _emailErrorText.value = ""

        _password.value = ""
        _passIsError.value = false
        _passErrorText.value = ""
    }

    fun registerUser(
        passUser: () -> Unit
    ) = runBlocking {
        var response: Response<UserResponse>? = null
        val job = launch {
            val user = RegisterRequest(_email.value!!, _name.value!!, _password.value!!)
            response = api.getService().register(apikey, user)
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

    fun resetError() {
        _nameIsError.value = false
        _nameErrorText.value = ""

        _emailIsError.value = false
        _emailErrorText.value = ""

        _passIsError.value = false
        _passErrorText.value = ""
    }

    fun handleError(code: Int) {
        resetError()
        if (code == 422) {
            if (name.value!!.isBlank()) {
                updateNameError(true)
                updateNameErrorText("Must enter a name")
            }
            if (!email.value!!.contains("@") or email.value!!.isBlank()) {
                updateEmailError(true)
                updateEmailErrorText("Invalid email input")
            }
            if (password.value!!.length < 8) {
                updatePassError(true)
                updatePassErrorText("Password must be at least 8 characters long")
            }
            // Email in use
        } else if (code == 400) {
            updateEmailError(true)
            updateEmailErrorText("Email already in use")
        }

    }

    fun updateUser(newUser: UserResponse) {
        _user.value = newUser
    }

    // Name
    fun updateNameTextField(field: String) {
        _name.value = field
    }

    fun updateNameError(newBool: Boolean) {
        _nameIsError.value = newBool
    }

    fun updateNameErrorText(newStr: String) {
        _nameErrorText.value = newStr
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

}