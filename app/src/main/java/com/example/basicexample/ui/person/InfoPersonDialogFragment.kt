package com.example.basicexample.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.basicexample.R
import com.example.basicexample.databinding.DialogInfoPersonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoPersonDialogFragment: DialogFragment() {


    private val personViewModel: PersonViewModel by viewModels()

    private lateinit var binding: DialogInfoPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_BasicExample)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_info_person, container, false)
        binding = DialogInfoPersonBinding.bind(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getInfo.setOnClickListener {
            if(binding.payIdTextTl.text.toString().isNotEmpty()) {
                personViewModel.getPersonInfo(binding.payIdTextTl.text.toString()).observe(viewLifecycleOwner){
                    it.onSuccess {
                        binding.infoLn.visibility = View.VISIBLE
                        binding.nameTv.text = "Имя: ${it.name}"
                        binding.surnameTv.text = "Фамилия: ${it.surname}"
                        binding.surname2Tv.text = "Отчество: ${it.surname2}"
                        binding.phoneTv.text = "Телефон: ${it.phone}"
                        binding.payIdTv.text = "пай ид: ${it.payId}"
                        binding.emailTv.text = "email: ${it.email}"
                        binding.exchangeAccountNumberTv.text = "номер счета на бирже: ${it.exchangeAccountNumber}"
                        binding.binansIdTv.text = "бинанс: ${it.binansId}"
                        binding.dateRegistrationTv.text = "дата регистрации: ${it.dateRegistration}"
                    }

                }
            }else Toast.makeText(requireContext(), "Введите pay id", Toast.LENGTH_SHORT).show()
        }
    }


}