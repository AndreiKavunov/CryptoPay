package com.example.basicexample.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.basicexample.databinding.FragmentDashboardBinding
import kotlinx.coroutines.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)
//
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
//
//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val jobForLength1 = lifecycleScope.async {
            delay(6000)
            "https://webdesign.tutsplus.com".length

        }
        val jobForLength2:  Deferred<Int> = lifecycleScope.async {
            delay(2000)
            "https:.comu".length
        }

        lifecycleScope.launch(Dispatchers.Main) {
            val sum = jobForLength1.await() + jobForLength2.await()
            binding.textDashboard.text = "Downloaded $sum "
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}