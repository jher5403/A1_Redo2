package com.lab1.a1_redo2.Model

import com.lab1.a1_redo2.Data.IntToBoolean
import com.lab1.a1_redo2.Data.LoginRequest
import com.lab1.a1_redo2.Data.NewTaskRequest
import com.lab1.a1_redo2.Data.NewTaskResponse
import com.lab1.a1_redo2.Data.RegisterRequest
import com.lab1.a1_redo2.Data.UpdateTaskRequest
import com.lab1.a1_redo2.Data.UpdateTaskResponse
import com.lab1.a1_redo2.Data.UserResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonAdapter.Factory
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Type


class ApiClient private constructor() {
    companion object {
        private var instance: ApiClient? = null
        private const val BASE_URL = "https://todos.simpleapi.dev/api/"

        private val moshi: Moshi = Moshi.Builder()
            .add(BooleanAdapterFactory())
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        fun getInstance(): ApiClient {
            if (instance == null) {
                instance = ApiClient()
            }
            return instance!!
        }

    }

    fun getService(): ApiService {
        return service
    }
}

interface ApiService {
    // Register User (DONE & REFACTORED)
    @POST("/api/users/register")
    suspend fun register(
        @Query("apikey") apikey: String,
        @Body user: RegisterRequest
    ): Response<UserResponse>

    // Log user in (DONE & REFACTORED)
    @POST("/api/users/login")
    suspend fun logIn(
        @Query("apikey") apikey: String,
        @Body loginRequest: LoginRequest
    ): Response<UserResponse>

    // Add new task for current user (DONE & REFACTORED)
    @POST("/api/users/{user_id}/todos")
    suspend fun addNewTask(
        @Header("Authorization") auth: String,
        @Path("user_id") userId: Int,
        @Query("apikey") apikey: String,
        @Body task: NewTaskRequest
    ): Response<NewTaskResponse>

    // Get all user tasks (DONE & REFACTORED)
    @GET("/api/users/{user_id}/todos")
    suspend fun getUserTasks(
        @Header("Authorization") auth: String,
        @Path("user_id") userId: Int,
        @Query("apikey") apikey: String,
    ): Response<List<NewTaskResponse>>

    // Update user task (DONE & REFACTORED)
    @PUT("/api/users/{user_id}/todos/{id}")
    suspend fun updateTask(
        @Header("Authorization") auth: String,
        @Path("user_id") userId: Int,
        @Path("id") taskId: Int,
        @Query("apikey") apikey: String,
        @Body updateTaskRequest: UpdateTaskRequest
    ): Response<UpdateTaskResponse>

    // Log user out (DONE & REFACTORED)
    @POST("/api/users/logout")
    suspend fun logOut(
        @Header("Authorization") auth: String,
        @Query("apikey") apikey: String
    ): Response<Void>

}

class BooleanAdapterFactory : Factory {
    override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<*>? {
        val intAsBoolean = annotations.find { it is IntToBoolean }
        return if (intAsBoolean != null) {
            IntToBooleanAdapter()
        } else {
            null
        }
    }
}

class IntToBooleanAdapter : JsonAdapter<Boolean>() {
    @FromJson
    override fun fromJson(reader: JsonReader): Boolean? {
        return when (reader.peek()) {
            JsonReader.Token.NUMBER -> reader.nextInt() == 1
            JsonReader.Token.BOOLEAN -> reader.nextBoolean()
            else -> null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Boolean?) {
        writer.value(value?.let { if (it) 1 else 0 })
    }
}
