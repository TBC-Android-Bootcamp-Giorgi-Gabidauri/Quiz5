package com.gabo.quiz5.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabo.quiz5.databinding.FieldsRvItemViewBinding
import com.gabo.quiz5.model.Fields

class FieldsAdapter : RecyclerView.Adapter<FieldsAdapter.FieldVH>() {
    private var list: List<Fields> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Fields>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class FieldVH(private val binding: FieldsRvItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Fields) {
            with(binding) {
                val adapter = FieldModelListAdapter()
                binding.rvFields.adapter = adapter
                binding.rvFields.layoutManager = LinearLayoutManager(rvFields.context)
                adapter.submitList(model.fieldList)
//                adapter.extractedList.observe()
            //                Log.d("gabooo", adapter.extractedList.toList().toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldVH {
        val binding =
            FieldsRvItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FieldVH(binding)
    }

    override fun onBindViewHolder(holder: FieldVH, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}