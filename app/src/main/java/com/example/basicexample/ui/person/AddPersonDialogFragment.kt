package com.example.basicexample.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.basicexample.R
import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.ui.MyTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class AddPersonDialogFragment() : Fragment() {


    private val personViewModel: PersonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose the Composition when viewLifecycleOwner is destroyed
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                MaterialTheme {
                    AddPersonRoot(
                        personViewModel = personViewModel,
                        navigateToBack = :: navigateBack
                    )
                }
            }
        }
    }

    private fun navigateBack(){
        findNavController().navigateUp()
    }

}

@Composable
fun AddPersonRoot(modifier: Modifier = Modifier, personViewModel: PersonViewModel, navigateToBack: () -> Unit) {
    MyTheme() {
        val textDialog by personViewModel.textDialog.collectAsState()
        AddPersonScreen(
            modifier = modifier,
            personViewModel = personViewModel
        )

        if(textDialog !=  "") {
            Toast.makeText(LocalContext.current, textDialog, Toast.LENGTH_LONG).show()
            if(textDialog == "Пользователь добавлен"){navigateToBack()}
            personViewModel.changeTextDialog()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPersonScreen(modifier: Modifier = Modifier, personViewModel: PersonViewModel) {

    var nameValue by remember { mutableStateOf(TextFieldValue("")) }
    var isNameError by rememberSaveable { mutableStateOf(false) }

    var surnameValue by remember { mutableStateOf(TextFieldValue("")) }
    var isSurnameError by rememberSaveable { mutableStateOf(false) }

    var surname2Value by remember { mutableStateOf(TextFieldValue("")) }
    var isSurname2Error by rememberSaveable { mutableStateOf(false) }

    var phoneValue by remember { mutableStateOf(TextFieldValue("")) }
    var isPhoneError by rememberSaveable { mutableStateOf(false) }

    var payIdValue by remember { mutableStateOf(TextFieldValue("")) }
    var isPayIdError by rememberSaveable { mutableStateOf(false) }

    var emailValue by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailError by rememberSaveable { mutableStateOf(false) }

    var accNumberValue by remember { mutableStateOf(TextFieldValue("")) }
    var isAccNumberError by rememberSaveable { mutableStateOf(false) }

    var binansIdValue by remember { mutableStateOf(TextFieldValue("")) }
    var isBinansIdError by rememberSaveable { mutableStateOf(false) }

    fun inspectionData(): Boolean {
        var correctData = true

        if (nameValue.text == "") {
            isNameError = true
            correctData = false
        }

        if (surnameValue.text == "") {
            isSurnameError = true
            correctData = false
        }

        if (phoneValue.text == "") {
            isPhoneError = true
            correctData = false
        }

        if (payIdValue.text == "") {
            isPayIdError = true
            correctData = false
        }

        if (!emailValidation(emailValue.text)) {
            isEmailError = true
            correctData = false
        }

        if (accNumberValue.text == "") {
            isAccNumberError = true
            correctData = false
        }

        if (binansIdValue.text == "") {
            isBinansIdError = true
            correctData = false
        }

        return correctData
    }

    fun save() {
        if (inspectionData()) {
            val personInfo = PersonInfo(
                name = nameValue.text,
                surname = surnameValue.text,
                surname2 = surname2Value.text,
                phone = phoneValue.text,
                email = emailValue.text,
                payId = payIdValue.text,
                exchangeAccountNumber = accNumberValue.text,
                binansId = binansIdValue.text,
                dateRegistration = LocalDateTime.now().toString()
            )

            personViewModel.addPerson(personInfo)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.verticalScroll(
            rememberScrollState()
        )) {

            Spacer(modifier = modifier.height(24.dp).verticalScroll(rememberScrollState()))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = nameValue,
                onValueChange = {
                    nameValue = it
                    isNameError = false
                },
                placeholder = { Text("Имя") },
                singleLine = true,
                isError = isNameError,
                supportingText = {
                    if (isNameError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите Имя",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isNameError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = surnameValue,
                onValueChange = {
                    surnameValue = it
                    isSurnameError = false
                },
                placeholder = { Text("фамилия") },
                singleLine = true,
                isError = isSurnameError,
                supportingText = {
                    if (isSurnameError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите фамилию",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isSurnameError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = surname2Value,
                onValueChange = {
                    surname2Value = it
                    isSurname2Error = false
                },
                placeholder = { Text("отчество") },
                singleLine = true,
                isError = isSurname2Error,
                supportingText = {
                    if (isSurname2Error) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите отчество",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isSurname2Error)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = phoneValue,
                onValueChange = {
                    phoneValue = it
                    isPhoneError = false
                },
                placeholder = { Text("телефон") },
                singleLine = true,
                isError = isPhoneError,
                supportingText = {
                    if (isPhoneError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите телефон",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isPhoneError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = payIdValue,
                onValueChange = {
                    payIdValue = it
                    isPayIdError = false
                },
                placeholder = { Text("pay id") },
                singleLine = true,
                isError = isPayIdError,
                supportingText = {
                    if (isPayIdError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите pay id",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isPayIdError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = emailValue,
                onValueChange = {
                    emailValue = it
                    isEmailError = false
                },
                placeholder = { Text("email") },
                singleLine = true,
                isError = isEmailError,
                supportingText = {
                    if (isEmailError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите email",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isEmailError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = accNumberValue,
                onValueChange = {
                    accNumberValue = it
                    isAccNumberError = false
                },
                placeholder = { Text("номер счета на бирже") },
                singleLine = true,
                isError = isAccNumberError,
                supportingText = {
                    if (isAccNumberError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите номер счета на бирже",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isAccNumberError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = binansIdValue,
                onValueChange = {
                    binansIdValue = it
                    isBinansIdError = false
                },
                placeholder = { Text("binans id") },
                singleLine = true,
                isError = isBinansIdError,
                supportingText = {
                    if (isBinansIdError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите binans id",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isBinansIdError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            Button(
                onClick = {
                    save()
                }, modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                shape = RoundedCornerShape(0)
            ) {
                Text(
                    text = "Сохранить"
                )
            }

            Spacer(modifier = modifier.height(100.dp))

        }
    }
}

private fun emailValidation(email: String): Boolean {
    val regex =
        Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")
    return regex.matches(email)
}