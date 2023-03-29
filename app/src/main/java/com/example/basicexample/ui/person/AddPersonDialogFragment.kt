package com.example.basicexample.ui.person

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.basicexample.R
import com.example.basicexample.databinding.DialogAddPersonBinding
import com.example.basicexample.databinding.DialogRegistrationBinding
import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class AddPersonDialogFragment(val onClick: () -> Unit): DialogFragment() {


    private val personViewModel: PersonViewModel by viewModels()

    private lateinit var binding: DialogAddPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_BasicExample)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_person, container, false)
        binding = DialogAddPersonBinding.bind(view)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBt.setOnClickListener {
            personViewModel.getPersonInfo(binding.payIdTextTl.text.toString()).observe(viewLifecycleOwner){
                it.onSuccess {
                    if(it.payId.isEmpty()){
                        addNewPerson()
                    }else Toast.makeText(requireContext(), "Пользователь с таким payId уже есть в системе", Toast.LENGTH_SHORT).show()
                }.onFailure {

                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addNewPerson(){
        val id = binding.payIdTextTl.text.toString()
        val name = binding.nameTextTl.text.toString()
        val surname = binding.surnameTextTl.text.toString()
        val surname2 = binding.surname2TextTl.text.toString()
        val phone = binding.phoneTextTl.text.toString()
        val email = binding.emailTextTl.text.toString()
        val accNumber = binding.accNumbTextTl.text.toString()
        val binansId = binding.binansIdTextTl.text.toString()
        val date = LocalDateTime.now()
        val person = PersonInfo(name = name,
            surname = surname, surname2 = surname2, phone = phone, payId = id,
            email = email, exchangeAccountNumber = accNumber, binansId = binansId, dateRegistration = date.toString())
        personViewModel.addPerson(person).observe(viewLifecycleOwner){
            it.onSuccess {
                Toast.makeText(requireContext(), "Пользователь успешно зарегистрирован", Toast.LENGTH_SHORT).show()
                onClick()
            }.onFailure {
                Toast.makeText(requireContext(), "${it.message.toString()}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}