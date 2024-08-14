package com.lab1.a1_redo2.ViewModel

import com.lab1.a1_redo2.Data.UserResponse
import com.lab1.a1_redo2.Model.ApiClient
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith

/*
This code frankly doesn't work, and frankly, my implementation doesn't exactly make it easy to adapt for testing like demonstrated.
I've done what I could to adapt it, but given my API service cannot be accessed, and refactoring would take too much time, I've
decided to turn in what I can.
 */
@ExtendWith(CoroutinesTestExtension::class, InstantExecutorExtension::class)
class CreateAccountViewModelTest {

    @org.junit.Test
    fun `testRegisterUser() test should be correct`() = runTest {
        val user = mockk<UserResponse>()
        val apiClient = mockk<ApiClient>()
        val apiService = apiClient.getService()

        every {user.name} returns "username"
        every {user.email} returns "user@email.com"
        every {user.id} returns 1
        every {user.token} returns "token"
        every { user.admin } returns false

        coEvery {apiService.registerTest("db98b5d4-1b18-46f6-bc3f-13add13ef441", any())} returns user

        val viewModel = CreateAccountViewModel(apiClient)
        viewModel.registerUserTest("user@email.com", "username", "password")

        coVerify { apiService.registerTest("db98b5d4-1b18-46f6-bc3f-13add13ef441", any()) }

    }

    @org.junit.Test
    fun `testRegisterUser() test should be incorrect`() = runTest {
        val user = mockk<UserResponse>()
        val apiClient = mockk<ApiClient>()
        val apiService = apiClient.getService()

        every {user.name} returns "username"
        every {user.email} returns "user@email.com"
        every {user.id} returns 1
        every {user.token} returns "token"
        every { user.admin } returns false

        coEvery {apiService.registerTest("db98b5d4-1b18-46f6-bc3f-13add13ef441", any())} returns user

        val viewModel = CreateAccountViewModel(apiClient)
        viewModel.registerUserTest("invalid email", "username", "pass")

        coVerify { apiService.registerTest("db98b5d4-1b18-46f6-bc3f-13add13ef441", any()) }

    }

}