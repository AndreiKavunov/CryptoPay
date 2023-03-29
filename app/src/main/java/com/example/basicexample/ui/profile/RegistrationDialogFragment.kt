package com.example.basicexample.ui.profile

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.basicexample.R
import com.example.basicexample.databinding.DialogRegistrationBinding
import com.example.basicexample.domain.models.CompanyInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistrationDialogFragment(val onClick: () -> Unit) : DialogFragment() {


    private val profileModel: ProfileViewModel by activityViewModels()

    private lateinit var binding: DialogRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_BasicExample)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_registration, container, false)
        binding = DialogRegistrationBinding.bind(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.saveBt.setOnClickListener {
            val email = binding.emailTextTl.text.toString()
            val password = binding.passwordTextTl.text.toString()
            val nameOrg = binding.nameTextTl.text.toString()
            val phone = binding.phoneTextTl.text.toString()
            val address = binding.addressTextTl.text.toString()
            val inn = binding.innTextTl.text.toString()
            val bic = binding.bicTextTl.text.toString()
            val bankName = binding.bankNameTextTl.text.toString()
            val checkingAccount = binding.checkingAccountTextTl.text.toString()
            val payId = binding.payIdTextTl.text.toString()
            val binansId = binding.binansIdTextTl.text.toString()

            val companyInfo = CompanyInfo(
                id =  "",
                name_org = nameOrg,
                phone = phone,
                email = email,
                address = address,
                inn = inn,
                bic = bic,
                bank_name = bankName,
                checking_account =  checkingAccount,
                sum = 0,
                action = true,
                payId = payId,
                binansId = binansId


            )

            profileModel.createNewUser(companyInfo, password).observe(viewLifecycleOwner){
                it.onSuccess {
                    profileModel.getCurrentUser()
                    onClick()
                }.onFailure {
                    Toast.makeText(requireContext(), "${it.message.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.cancelBt.setOnClickListener {
            onClick()
        }




    }


}