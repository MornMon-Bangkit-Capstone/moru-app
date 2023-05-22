package com.capstone.moru.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.moru.R
import com.capstone.moru.databinding.FragmentProfileBinding
import com.capstone.moru.ui.profile.profile_data.ProfileDataActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileData.setOnClickListener {
            val intentToProfileData = Intent(requireContext(), ProfileDataActivity::class.java)
            startActivity(intentToProfileData)

        }

    }

    companion object {
    }
}