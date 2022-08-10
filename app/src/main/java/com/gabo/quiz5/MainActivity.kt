package com.gabo.quiz5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.quiz5.adapter.FieldsAdapter
import com.gabo.quiz5.databinding.ActivityMainBinding
import com.gabo.quiz5.helpers.ResultHandler
import com.gabo.quiz5.model.Item
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainVM by viewModels()
    private val fieldsAdapterRequired: FieldsAdapter by lazy {
        FieldsAdapter()
    }
    private val fieldsAdapterIsNotRequired: FieldsAdapter by lazy {
        FieldsAdapter()
    }
    private val fieldsAdapterPin: FieldsAdapter by lazy {
        FieldsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAdapters()
        setupObservers()
    }

    private fun setupAdapters() {
        binding.rvRequired.adapter = fieldsAdapterRequired
        binding.rvRequired.layoutManager = LinearLayoutManager(this)
        binding.rvIsNotRequired.adapter = fieldsAdapterIsNotRequired
        binding.rvIsNotRequired.layoutManager = LinearLayoutManager(this)
        binding.rvPin.adapter = fieldsAdapterIsNotRequired
        binding.rvPin.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getList().collect {
                    when (it) {
                        is ResultHandler.Success -> {
                            val required = mutableListOf<Item.GroupItems.FieldModel>()
                            val isNotRequired = mutableListOf<Item.GroupItems.FieldModel>()
                            it.list.ListGroupItems.forEach { groupItems ->
                                groupItems.groupItems.forEach { fieldModel ->
                                    if (fieldModel.required) {
                                        required.add(fieldModel)
                                    } else {
                                        isNotRequired.add(fieldModel)
                                    }
                                }
                            }
                            fieldsAdapterRequired.submitList(required)
                            fieldsAdapterIsNotRequired.submitList(isNotRequired)
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
    private fun setupClicks(){

    }
    private fun check(){

    }
}