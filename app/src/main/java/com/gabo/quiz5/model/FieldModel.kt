package com.gabo.quiz5.model

import com.google.gson.annotations.SerializedName

data class FieldModel(
    @SerializedName("field_id")
    val fieldId: Int,
    val hint: String,
    @SerializedName("field_type")
    val fieldType: String,
    val keyboard: String?,
    val required: Boolean,
    @SerializedName("is_active")
    val isActive: Boolean,
    val icon: String
)
