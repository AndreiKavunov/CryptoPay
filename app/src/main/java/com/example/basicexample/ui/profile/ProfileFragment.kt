package com.example.basicexample.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.basicexample.R
import com.example.basicexample.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by activityViewModels()

    private val authorizationDialog = AuthorizationDialog { dismissAuthDialog() }

    private val registrationDialogFragment = RegistrationDialogFragment{ dismissRegisDialog() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.bind(root)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.user.collect(){
                showProfile(it) }

        }
    }

    private fun showProfile(user: CurrentUser){
        if (user.email.isNotEmpty()) showAuthorizedProfile(user) else showAnonymousProfile()
    }

    private fun showAnonymousProfile(){
        binding.incAnonymousProfile.root.isVisible = true
        binding.incAuthorizedProfile.root.isVisible = false

        binding.incAnonymousProfile.registryBt.setOnClickListener {
            goToRegistration()
        }


        binding.incAnonymousProfile.singInBt.setOnClickListener {
            authorizationDialog.show(childFragmentManager, "tag")
        }
    }

    private fun showAuthorizedProfile(user: CurrentUser){
        binding.incAnonymousProfile.root.isVisible = false
        binding.incAuthorizedProfile.root.isVisible = true

        binding.incAuthorizedProfile.infoUserBt.setOnClickListener {
            viewModel.getBalance()
        }

        binding.incAuthorizedProfile.personInformationTv.text = "email: ${user.email}  name: ${user.name}  id: ${user.uid}"
    }

    private fun goToRegistration() {
        registrationDialogFragment.show(childFragmentManager, "tag")
    }

    private fun dismissAuthDialog() {
        authorizationDialog.dismiss()
    }

    private fun dismissRegisDialog() {
        registrationDialogFragment.dismiss()
    }

}
