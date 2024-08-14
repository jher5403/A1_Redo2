package com.lab1.a1_redo2.ViewModel

import com.lab1.a1_redo2.Data.UserResponse
import com.lab1.a1_redo2.Model.ApiClient
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class LogInViewModelTest {

    @Test
    fun `logInTest() should be correct`() = runTest {
        val user = mockk<UserResponse>()
        val apiClient = mockk<ApiClient>()
        val apiService = apiClient.getService()

        every {user.name} returns "username"
        every {user.email} returns "user@email.com"
        every {user.id} returns 1
        every {user.token} returns "token"
        every { user.admin } returns false

        coEvery {apiService.logInTest("db98b5d4-1b18-46f6-bc3f-13add13ef441", any())} returns user

        val viewModel = LogInViewModel(apiClient)
        viewModel.logInTest("user@email.com", "password")

        coVerify { apiService.logInTest("db98b5d4-1b18-46f6-bc3f-13add13ef441", any()) }
    }

    @Test
    fun `logInTest() should be incorrect`() = runTest {
        val user = mockk<UserResponse>()
        val apiClient = mockk<ApiClient>()
        val apiService = apiClient.getService()

        every {user.name} returns "username"
        every {user.email} returns "user@email.com"
        every {user.id} returns 1
        every {user.token} returns "token"
        every { user.admin } returns false

        coEvery {apiService.logInTest("db98b5d4-1b18-46f6-bc3f-13add13ef441", any())} returns user

        val viewModel = LogInViewModel(apiClient)
        viewModel.logInTest("user@email.com", "pass")

        coVerify { apiService.logInTest("db98b5d4-1b18-46f6-bc3f-13add13ef441", any()) }
    }

}