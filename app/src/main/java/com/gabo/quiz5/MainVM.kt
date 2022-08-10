package com.gabo.quiz5

import androidx.lifecycle.ViewModel
import com.gabo.quiz5.helpers.ResultHandler
import com.gabo.quiz5.model.FieldModel
import com.gabo.quiz5.service.RetrofitInstance.getService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainVM : ViewModel() {
    fun getList(): Flow<ResultHandler<List<List<FieldModel>>>> = flow {
        val answerFromServer = getService().getItems()
        val response: ResultHandler<List<List<FieldModel>>> = when {
            answerFromServer.isSuccessful -> {
                ResultHandler.Success(list = answerFromServer.body()!!)
            }
            else -> {
                ResultHandler.Error(errorMSg = answerFromServer.errorBody().toString())
            }
        }
        emit(response)
    }
}