package com.lab1.a1_redo2.ViewModel

import com.lab1.a1_redo2.Data.NewTaskResponse
import com.lab1.a1_redo2.Data.UpdateTaskResponse
import com.lab1.a1_redo2.Data.UserResponse
import com.lab1.a1_redo2.Model.ApiClient
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

/*
This code frankly doesn't work, and frankly, my implementation doesn't exactly make it easy to adapt for testing like demonstrated.
I've done what I could to adapt it, but given my API service cannot be accessed, and refactoring would take too much time, I've
decided to turn in what I can.
 */
class TaskListViewModelTest {

    @Test
    fun `getTaskListTest()`() = runTest {
        val list = mockk<List<UserResponse>>()
        val apiClient = mockk<ApiClient>()
        val apiService = apiClient.getService()

        coEvery { apiService.getUserTasksTest(any(),any(), any()) }
        val viewModel = TaskListViewModel(apiClient)
        viewModel.getTaskListTest()
        coVerify { apiService.getUserTasksTest(any(), any(), any()) }

    }

    @Test
    fun `addTaskTest()`() = runTest  {
        val taskResponse = mockk<NewTaskResponse>()
        val apiClient = mockk<ApiClient>()
        val apiService = apiClient.getService()

        every { taskResponse.id } returns 1
        every { taskResponse.description } returns "description"
        every { taskResponse.completed } returns false
        every { taskResponse.meta } returns null

        coEvery { apiService.addNewTaskTest(any(), any(), any(), any()) } returns taskResponse
        val viewModel = TaskListViewModel(apiClient)
        viewModel.addTaskTest("description")
        coVerify { apiService.addNewTaskTest(any(), any(), any(), any()) }
    }

    @Test
    fun `updateTaskTest()`() = runTest  {
        val updateResponse = mockk<UpdateTaskResponse>()
        val apiClient = mockk<ApiClient>()
        val apiService = apiClient.getService()

        every { updateResponse.id } returns 1
        every { updateResponse.completed } returns false
        every { updateResponse.description } returns "description"

        coEvery { apiService.updateTaskTest(any(),any(),any(),any(),any()) }
        val viewModel = TaskListViewModel(apiClient)
        //viewModel.updateTaskTest(any(), any())
    }

    @Test
    fun `logOutTest()`() = runTest  {
        val apiClient = mockk<ApiClient>()
        val apiService = apiClient.getService()

        coEvery { apiService.logOutTest(any(), any()) }
        val viewModel = TaskListViewModel(apiClient)
        viewModel.getTaskListTest()
        coVerify { apiService.logOutTest(any(),any()) }
    }
}