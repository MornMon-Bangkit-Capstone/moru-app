package com.capstone.moru.ui.add_routine.pick_routine

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.data.api.response.BookListItem
import com.capstone.moru.databinding.FragmentPickBooksRoutineBinding
import com.capstone.moru.ui.add_routine.pick_routine.adapter.PickBookRoutineAdapter
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.RoutineViewModel


class PickBooksRoutineFragment : Fragment() {
    private var _binding: FragmentPickBooksRoutineBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickBooksRoutineBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        routineViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        routineViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            routineViewModel.getAllBooksRoutine(token)
        }

        routineViewModel.bookRoutine.observe(viewLifecycleOwner) { routines ->
            initRecyclerView(routines)
        }

        routineViewModel.error.observe(viewLifecycleOwner){
            retry(it)
        }

        routineViewModel.message.observe(viewLifecycleOwner){
            displayToast(it)
        }

        binding.edSearchRoutines.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                event?.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                val query = binding.edSearchRoutines.text.toString().trim()
                if (query.isNotEmpty()) {
                    routineViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
                        routineViewModel.findBookRoutine(token, query)
                    }
                }

                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.edSearchRoutines.windowToken, 0)

                binding.edSearchRoutines.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun retry(it: Boolean?) {
        if (it!!){
            binding.progressBar.visibility = View.GONE
            binding.btnRetry.apply {
                visibility = View.VISIBLE
                isEnabled = true
                setOnClickListener {
                    routineViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
                        routineViewModel.getAllBooksRoutine(token)
                    }
                    visibility = View.GONE
                    isEnabled = false
                }
            }
        }else{
            binding.btnRetry.apply {
                visibility = View.GONE
                isEnabled = false
            }
        }
    }

    private fun initRecyclerView(routines: List<com.capstone.moru.data.api.response.BookListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoutine.layoutManager = layoutManager

        val adapter = PickBookRoutineAdapter(routines)

        binding.rvRoutine.adapter = adapter
        adapter.setOnItemClickCallback(object : PickBookRoutineAdapter.OnItemClickCallback {
            override fun onItemClicked(item: com.capstone.moru.data.api.response.BookListItem?) {
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}