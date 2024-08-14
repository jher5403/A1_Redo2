package com.lab1.a1_redo2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lab1.a1_redo2.Data.NewTaskRequest
import com.lab1.a1_redo2.Data.NewTaskResponse
import com.lab1.a1_redo2.Data.UpdateTaskRequest
import com.lab1.a1_redo2.Data.UpdateTaskResponse
import com.lab1.a1_redo2.Data.UserResponse
import com.lab1.a1_redo2.Model.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class TaskListViewModel(val api: ApiClient) : ViewModel() {
    private val apikey = "db98b5d4-1b18-46f6-bc3f-13add13ef441"

    val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    val _taskList = MutableLiveData<List<NewTaskResponse>>()
    val taskList: LiveData<List<NewTaskResponse>> = _taskList

    val _taskTextField = MutableLiveData<String>()
    val taskTextField: LiveData<String> = _taskTextField

    val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    val _labelText = MutableLiveData<String>()
    val labelText: LiveData<String> = _labelText

    init {
        _taskList.value = emptyList()
        _taskTextField.value = ""
        _isError.value = false
        _labelText.value = "Enter new task"
    }

    fun getAuth(): String {
        return "Bearer ${_user.value!!.token}"
    }

    fun getUserId(): Int {
        return _user.value!!.id
    }

    fun updateTaskList(newTaskList: List<NewTaskResponse>) {
        _taskList.value = newTaskList
    }

    fun updateUser(newUser: UserResponse) {
        _user.value = newUser
    }

    fun updateTaskTextField(field: String) {
        _taskTextField.value = field
    }

    fun updateLabelText(field: String) {
        _labelText.value = field
    }

    fun updateError(err: Boolean) {
        _isError.value = err
    }

    fun getTaskList() = runBlocking {
        var response: Response<List<NewTaskResponse>>? = null
        val job = launch {
            response = api.getService().getUserTasks(getAuth(), getUserId(), apikey)
        }
        job.join()
        if (response!!.isSuccessful) {
            updateTaskList(response!!.body()!!)
        } else {
            println(response!!.code())
        }
    }

    fun addTask() = runBlocking {
        var response: Response<NewTaskResponse>? = null
        val request = NewTaskRequest(taskTextField.value!!, false, null)
        val job = launch {
            response = api.getService().addNewTask(getAuth(), getUserId(), apikey, request)
        }
        job.join()
        if (response!!.isSuccessful) {
            getTaskList()
        } else {
            println(response!!.code())
        }

    }

    fun updateTask(task: NewTaskResponse, newChecked: Boolean) = runBlocking {
        var response: Response<UpdateTaskResponse>? = null
        val taskId = task.id
        val desc = task.description
        val request = UpdateTaskRequest(desc, newChecked)
        val job = launch {
            response = api.getService().updateTask(getAuth(), getUserId(), taskId, apikey, request)
        }
        job.join()
        if (response!!.isSuccessful) {
            getTaskList()
        } else {
            println(response!!.code())
        }
    }

    fun logOut() = runBlocking {
        var response: Response<Void>? = null
        val job = launch {
            response = api.getService().logOut(getAuth(), apikey)
        }
        job.join()
        if (!response!!.isSuccessful) {
            println(response!!.code())
        }
    }

    // Everything below is for testing.

    fun getTaskListTest() = runBlocking {
        val job = launch {
            val result = api.getService().getUserTasksTest(getAuth(), getUserId(), apikey)
        }
        job.join()
        //updateTaskList(result)
    }

    fun addTaskTest(desc: String) = runBlocking {
        val request = NewTaskRequest(desc, false, null)
        val job = launch {
            val result = api.getService().addNewTaskTest(getAuth(), getUserId(), apikey, request)
        }
        job.join()
    }

    fun updateTaskTest(task: NewTaskResponse, newChecked: Boolean) = runBlocking {
        val taskId = task.id
        val desc = task.description
        val request = UpdateTaskRequest(desc, newChecked)
        val job = launch {
            val result = api.getService().updateTaskTest(getAuth(), getUserId(), taskId, apikey, request)
        }
        job.join()
    }

    fun logOutTest() = runBlocking {
        val job = launch {
            val result = api.getService().logOutTest(getAuth(), apikey)
        }
        job.join()
    }

}