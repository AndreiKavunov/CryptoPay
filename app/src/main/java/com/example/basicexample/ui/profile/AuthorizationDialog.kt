package com.example.basicexample.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.basicexample.R
import com.example.basicexample.databinding.DialogAuthorizationBinding
import androidx.navigation.fragment.findNavController
import com.example.basicexample.databinding.DialogRegistrationBinding

class AuthorizationDialog(val onClick: () -> Unit) : DialogFragment() {


    private val profileModel: ProfileViewModel by activityViewModels()

    private lateinit var binding: DialogAuthorizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_BasicExample)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_authorization, container, false)
        binding = DialogAuthorizationBinding.bind(view)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.saveBt.setOnClickListener {
            sentEmail()
    }

        binding.cancelBt.setOnClickListener {
            onClick()
        }
    }

    private fun sentEmail(){
//        val email = binding.emailTextTl.text.toString()
//        val password = binding.passwordTextTl.text.toString()
//        if(email.isNotEmpty() && password.isNotEmpty()) {
//
//            profileModel.signIn(email, password).observe(viewLifecycleOwner){
//                it.onSuccess {
//                    if (it.isNotEmpty()) profileModel.getCurrentUser()
//                    onClick()
//                }.onFailure {
//                    Toast.makeText(requireContext(), "${it.message.toString()}", Toast.LENGTH_SHORT).show()
//
//                }
//            }
//        }else{
//            Log.d("tag1", "email $email  password: $password")
//        }
    }

}