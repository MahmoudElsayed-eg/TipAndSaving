package com.example.tipandsaving

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tipandsaving.databinding.SettingBinding
import java.util.*

class SettingDialog: DialogFragment() {
    private var _binding:SettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SettingBinding.inflate(LayoutInflater.from(context))
        binding.egImg.setOnClickListener {
            setAppLocale(requireContext(),"ar")
            dismissAllowingStateLoss()
            requireActivity().recreate()
        }
        binding.usImg.setOnClickListener {
            setAppLocale(requireContext(),"en")
            dismissAllowingStateLoss()
            requireActivity().recreate()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}