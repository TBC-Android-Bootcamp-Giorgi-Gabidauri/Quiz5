package com.gabo.quiz5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gabo.quiz5.customViews.CustomFieldsView
import com.gabo.quiz5.databinding.ActivityMainBinding
import com.gabo.quiz5.helpers.ResultHandler
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainVM by viewModels()
    private val fieldValues: MutableMap<Int, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btRegister.setOnClickListener {
            val fieldsChildCount = binding.llFieldsWrapper.childCount
            for (index in (0..fieldsChildCount)) {
                val field = binding.llFieldsWrapper.getChildAt(index)
                if (field is CustomFieldsView) {
                    field.getFieldValues().forEach {
                        fieldValues[it.key] = it.value
                    }
                }
            }
            val values = mutableListOf<String>()
            for (i in fieldValues) {
                values.add(i.value)
            }
            binding.tvFieldValues.text = values.toString()
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getList().collect {
                    when (it) {
                        is ResultHandler.Success -> {
                            binding.llFieldsWrapper.removeAllViews()
                            it.list.forEach {
                                val fieldView = CustomFieldsView(this@MainActivity).apply { addFields(it) }
                                binding.llFieldsWrapper.addView(fieldView)
                            }
                        }
                        is ResultHandler.Error -> {
                            Toast.makeText(this@MainActivity, it.errorMSg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}