package com.example.basicexample.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.basicexample.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    var homeViewModel: HomeViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("tag1", "onCreateView")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel?.getHorizontalCards()

        homeViewModel?.text?.observe(viewLifecycleOwner) {

            Log.d("tag1", "observe")


            binding.title.text = it.expiresIn.toString()
            binding.description.text = it.accessToken

        }

        binding.button.setOnClickListener{
            homeViewModel?.addCompany()
        }


    }

    override fun onStart() {
        super.onStart()
        Log.d("tag1", "onStart")

    }

    override fun onResume() {
        super.onResume()

        Log.d("tag1", "onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}