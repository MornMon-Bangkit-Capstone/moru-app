package com.capstone.moru.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.capstone.moru.databinding.FragmentProfileBinding
import com.capstone.moru.ui.customview.LogoutDialog
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.profile.profile_data.ProfileDataActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        binding.profileData.setOnClickListener {
            val intentToProfileData = Intent(requireContext(), ProfileDataActivity::class.java)
            startActivity(intentToProfileData)

        }

        binding.logout.setOnClickListener {
            val logoutDialog = LogoutDialog()
            logoutDialog.show(parentFragmentManager, "Dialog")

        }

        profileViewModel.getUserEmail().observe(viewLifecycleOwner) {
            binding.tvEmail.text = it
        }

        profileViewModel.getUsername().observe(viewLifecycleOwner){
            binding.tvName.text = it
        }

        profileViewModel.getImageUser().observe(viewLifecycleOwner){
            Glide.with(requireContext()).load(it).into(binding.ivProfile)
        }
    }


}