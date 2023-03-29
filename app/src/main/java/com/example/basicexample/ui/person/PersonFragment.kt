package com.example.basicexample.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.basicexample.R
import com.example.basicexample.databinding.FragmentPersonBinding
import com.example.basicexample.databinding.FragmentProfileBinding
import com.example.basicexample.ui.profile.AuthorizationDialog
import com.example.basicexample.ui.profile.ProfileFragmentDirections
import com.example.basicexample.ui.profile.ProfileViewModel
import kotlinx.coroutines.*

class PersonFragment : Fragment() {

    private lateinit var binding : FragmentPersonBinding

    private val addPersonDialogFragment = AddPersonDialogFragment { dismissAddPersonDialog() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_person, container, false)
        binding = FragmentPersonBinding.bind(root)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.infoUser.setOnClickListener {
            openInfoPerson()
        }

        binding.addUser.setOnClickListener {
            openAddPerson()
        }
    }

    private fun openAddPerson() {
        addPersonDialogFragment.show(childFragmentManager, "tag")
    }

    private fun openInfoPerson() {
        val direction = PersonFragmentDirections.actionNavigationPersonToInfoPersonDialogFragment()
        findNavController().navigate(direction)
    }

    private fun dismissAddPersonDialog(){
        addPersonDialogFragment.dismiss()
    }

}