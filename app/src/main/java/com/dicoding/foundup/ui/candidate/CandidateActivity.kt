package com.dicoding.foundup.ui.candidate

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.foundup.adapter.CandidateAdapter
import com.dicoding.foundup.databinding.ActivityCandidateBinding

class CandidateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCandidateBinding
    private val viewModel: CandidateViewModel by viewModels()

    private val candidateAdapter = CandidateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCandidateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menerima POST_ID dari Intent
        val postIds = intent.getIntArrayExtra("POST_ID")
        postIds?.let {
            viewModel.setPostIds(it)
        } ?: run {
            Log.d("CandidateActivity", "No Post IDs received")
        }

        bindInit()
        bindView()
        bindObserver()
    }


    private fun bindInit() {
        viewModel.getCandidate()
        Toast.makeText(this, "Candidate", Toast.LENGTH_SHORT).show()
    }

    private fun bindView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CandidateActivity)
            adapter = candidateAdapter
        }
    }

    private fun bindObserver() {
        viewModel.candidates.observe(this) {
            if (!it.error!!) {
                candidateAdapter.setData(it.data!!.applications)
            }
        }
    }
}
