package com.gabo.quiz5.customViews

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.text.InputType
import android.view.LayoutInflater
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import com.gabo.quiz5.databinding.FieldViewBinding
import com.gabo.quiz5.model.FieldModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CustomFieldsView(context: Context) : CardView(context) {
    private var binding = FieldViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var fields: List<FieldModel> = emptyList()
    private val fieldTexts: MutableMap<Int, String> = mutableMapOf()

    fun addFields(fieldList: List<FieldModel>) {
        fields = fieldList
        for (field in fieldList) {
            val customField = EditText(context)
            customField.layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            customField.hint = field.hint
            customField.inputType =
                when {
                    field.keyboard == "text" || field.hint == "Gender" -> InputType.TYPE_CLASS_TEXT
                    field.hint == "Birthday" -> InputType.TYPE_CLASS_DATETIME
                    else -> InputType.TYPE_CLASS_NUMBER
                }
            binding.llFields.addView(customField)
        }
    }

    fun getFieldValues(): MutableMap<Int, String> {
        val fieldsChildCount = binding.llFields.childCount
        for (index in (0..fieldsChildCount)) {
            val field = binding.llFields.getChildAt(index)
            if (field is EditText) {
                val fieldModel = fields.find { model ->
                    model.hint == field.hint
                }
                fieldModel?.let {
                    if (field.text.toString().isEmpty() && it.required) {
                        Snackbar.make(
                            this.rootView,
                            "${it.hint} field is required, Please fill it",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        fieldTexts[it.fieldId] = field.text.toString()
                    }
                }
            }
        }
        return fieldTexts
    }
}