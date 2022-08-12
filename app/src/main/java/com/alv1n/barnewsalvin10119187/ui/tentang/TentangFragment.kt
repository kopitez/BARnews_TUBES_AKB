package com.alv1n.barnewsalvin10119187.ui.tentang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alv1n.barnewsalvin10119187.databinding.FragmentTentangBinding

class TentangFragment : Fragment() {

    private var _binding: FragmentTentangBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTentangBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}