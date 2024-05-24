package com.palash.rotation_vector_sensor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.palash.rotation_vector_sensor.R
import com.palash.rotation_vector_sensor.databinding.FragmentHomeBinding
import com.palash.rotation_vector_sensor.view_model.SensorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sensorViewModel by viewModels<SensorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorViewModel.rotationVectorData.observe(viewLifecycleOwner, Observer {data ->
            binding.textViewX.text = "X: ${data.first}"
            binding.textViewY.text = "Y: ${data.second}"
            binding.textViewZ.text = "Z: ${data.third}"
        })
    }

    override fun onResume() {
        super.onResume()
        sensorViewModel.startListening()
    }

    override fun onPause() {
        super.onPause()
        sensorViewModel.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}