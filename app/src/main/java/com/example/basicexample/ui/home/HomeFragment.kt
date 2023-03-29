package com.example.basicexample.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.basicexample.R
import com.example.basicexample.databinding.FragmentHomeBinding
import com.example.basicexample.databinding.FragmentProfileBinding
import com.example.basicexample.domain.models.Transaction
import com.example.basicexample.ui.profile.CurrentUser
import com.example.basicexample.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(root)

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            profileViewModel.user.collect(){
                showHome(it) }

        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showHome(user: CurrentUser){
        if (user.email.isNotEmpty()) showAuthorizedHome() else showAnonymousHome()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAuthorizedHome(){
        binding.incAnonymousHome.root.isVisible = false
        binding.incAuthorizedHome.root.isVisible = true

        binding.incAuthorizedHome.button.setOnClickListener{

            val sum = binding.incAuthorizedHome.sumTextTl.text.toString().toFloat()
            val peyId = binding.incAuthorizedHome.personIdTextTl.text.toString()

            homeViewModel.getBalance().observe(viewLifecycleOwner){
                it.onSuccess {sumBalance->
                    if(sumBalance >= sum){

                        homeViewModel.getPersonInfo(peyId).observe(viewLifecycleOwner){

                            it.onSuccess {person->
                                if(person.payId.isNotBlank()){
                                    val transaction = Transaction(companyId = "", personId = person.payId, token = "?",
                                        date = LocalDateTime.now().toString(), payId = "?", binansId = "?",
                                        sumRub = sum.toString(), sumToken = "?")
                                    homeViewModel.createTransaction(transaction, sumBalance - sum)

                                }else{
                                    Toast.makeText(requireContext(), "Клиент не найден", Toast.LENGTH_SHORT).show()
                                }
                            }.onFailure {

                            }

                        }

                    }else{
                        Toast.makeText(requireContext(), "Недостаточно средств", Toast.LENGTH_SHORT).show()
                    }

                }.onFailure {

                }

            }

        }
    }
    private fun showAnonymousHome(){
        binding.incAnonymousHome.root.isVisible = true
        binding.incAuthorizedHome.root.isVisible = false
    }

}