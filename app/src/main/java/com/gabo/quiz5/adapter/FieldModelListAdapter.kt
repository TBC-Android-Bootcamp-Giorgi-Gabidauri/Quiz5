package com.gabo.quiz5.adapter

import android.annotation.SuppressLint
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.gabo.quiz5.databinding.FieldItemViewBinding
import com.gabo.quiz5.helpers.onTextChanged
import com.gabo.quiz5.model.FieldModel

class FieldModelListAdapter() : RecyclerView.Adapter<FieldModelListAdapter.FieldVH>() {
    private var list: List<FieldModel> = emptyList()
    val extractedList: MutableLiveData<MutableMap<Int, String>> = MutableLiveData()
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<FieldModel>) {
        this.list = list
        notifyDataSetChanged()
    }


    inner class FieldVH(private val binding: FieldItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: FieldModel) {
            with(binding) {
                root.hint = model.hint
                root.inputType = if (model.keyboard == "number") {
                    InputType.TYPE_CLASS_NUMBER
                } else {
                    InputType.TYPE_CLASS_TEXT
                }
                root.onTextChanged {
                    extractedList.value?.put(model.fieldId, it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldVH {
        val binding =
            FieldItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FieldVH(binding)
    }

    override fun onBindViewHolder(holder: FieldVH, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}