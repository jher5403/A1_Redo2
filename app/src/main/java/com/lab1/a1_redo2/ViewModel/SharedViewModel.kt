package com.lab1.a1_redo2.ViewModel

import androidx.lifecycle.ViewModel
import com.lab1.a1_redo2.Data.UserResponse

class SharedViewModel : ViewModel() {
    var data: UserResponse? = null

    fun passData(newData: UserResponse) {
        data = newData
    }
}