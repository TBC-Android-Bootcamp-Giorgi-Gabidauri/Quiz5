package com.gabo.quiz5

import android.os.Bundle
import android.util.Log.d
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.quiz5.adapter.FieldModelListAdapter
import com.gabo.quiz5.adapter.FieldsAdapter
import com.gabo.quiz5.databinding.ActivityMainBinding
import com.gabo.quiz5.helpers.ResultHandler
import com.gabo.quiz5.helpers.itemProvider.pinField
import com.gabo.quiz5.model.FieldModel
import com.gabo.quiz5.model.Fields
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainVM by viewModels()
    private val fieldsAdapter: FieldsAdapter by lazy {
        FieldsAdapter().also {
            binding.rvRequired.adapter = it
            binding.rvRequired.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupClicks()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getList().collect {
                    when (it) {
                        is ResultHandler.Success -> {
                            val list = it.list.map {
                                Fields(it)
                            }.toMutableList()
                            list.add(pinField)
                            fieldsAdapter.submitList(list)

                        }
                        is ResultHandler.Error -> {
                            Toast.makeText(this@MainActivity, it.errorMSg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }

//        binding.root.removeAllViews()
//        repeat(5) {
//            val editText = EditText(this)
//            editText.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            binding.root.addView(editText)
//        }
    }

    private fun setupClicks() {
        binding.btnRegister.setOnClickListener {
            check()
        }
    }
    val myAdapter = FieldModelListAdapter()

    private fun check() {

    }
}