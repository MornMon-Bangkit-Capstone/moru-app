package com.capstone.moru.ui.routines

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.capstone.moru.databinding.FragmentBooksRoutineListBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.adapter.BooksRoutineListAdapter

class BooksRoutineListFragment : Fragment() {
    private var _binding: FragmentBooksRoutineListBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBooksRoutineListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireContext())

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoutine.layoutManager = layoutManager

        routineViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            routineViewModel.getAllBooksRoutine(token)
        }

        routineViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        routineViewModel.bookRoutine.observe(viewLifecycleOwner) { routine ->
            Log.e("ROUTINE", routine.toString())
            initRecyclerView(routine)
        }

        routineViewModel.error.observe(viewLifecycleOwner) {
            retry(it)
            if (it){
                routineViewModel.message.observe(viewLifecycleOwner) {
                    displayToast(it)
                }
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun retry(it: Boolean?) {
        if (it!!) {
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
        } else {
            binding.btnRetry.apply {
                visibility = View.GONE
                isEnabled = false
            }
        }
    }

    private fun initRecyclerView(routine: List<com.capstone.moru.data.api.response.BookListItem?>?) {
        val adapter = BooksRoutineListAdapter(routine)
        binding.rvRoutine.adapter = adapter

        adapter.setOnItemClickCallback(object : BooksRoutineListAdapter.OnItemClickCallback {
            override fun onItemClicked(routineBooks: com.capstone.moru.data.api.response.BookListItem?) {
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}