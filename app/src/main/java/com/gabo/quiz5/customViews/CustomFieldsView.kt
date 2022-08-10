package com.gabo.quiz5.customViews

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.widget.EditText
import androidx.cardview.widget.CardView
import com.gabo.quiz5.databinding.FieldViewBinding
import com.gabo.quiz5.model.FieldModel

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
                if (field.keyboard == "text") InputType.TYPE_CLASS_TEXT else InputType.TYPE_CLASS_NUMBER
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
                    fieldTexts[it.fieldId] = field.text.toString()
                }
            }
        }
        return fieldTexts
    }
}