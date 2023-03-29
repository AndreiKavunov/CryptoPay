package com.example.basicexample.ui.profile

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.basicexample.R
import com.example.basicexample.domain.models.CompanyInfo
import com.example.basicexample.ui.MyTheme
import com.example.basicexample.ui.home.HomeViewModel


class RegistrationDialogFragment() : DialogFragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

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
                    CompanyRegistrationRoot(
                        homeViewModel = homeViewModel,
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
fun CompanyRegistrationRoot(modifier: Modifier = Modifier, homeViewModel: HomeViewModel, navigateToBack: () -> Unit) {
    MyTheme() {
        val textRegistrationDialog by homeViewModel.textRegistrationDialog.collectAsState()
        CompanyRegistrationScreen(
            modifier = modifier,
            homeViewModel = homeViewModel,
            navigateToBack = navigateToBack
        )

        if(textRegistrationDialog !=  "") {
            Toast.makeText(LocalContext.current, textRegistrationDialog, Toast.LENGTH_LONG).show()
            if(textRegistrationDialog == "Компания успешно зарегистрирована"){
                navigateToBack()}
            homeViewModel.changeTextRegistrationDialog()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyRegistrationScreen(modifier: Modifier, homeViewModel: HomeViewModel, navigateToBack: () -> Unit){

    var emailValue by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailError by rememberSaveable { mutableStateOf(false) }

    var passwordValue by remember { mutableStateOf(TextFieldValue("")) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }

    var nameValue by remember { mutableStateOf(TextFieldValue("")) }
    var isNameError by rememberSaveable { mutableStateOf(false) }

    var phoneValue by remember { mutableStateOf(TextFieldValue("")) }
    var isPhoneError by rememberSaveable { mutableStateOf(false) }

    var addressValue by remember { mutableStateOf(TextFieldValue("")) }
    var isAddressError by rememberSaveable { mutableStateOf(false) }

    var innValue by remember { mutableStateOf(TextFieldValue("")) }
    var isInnError by rememberSaveable { mutableStateOf(false) }

    var bicValue by remember { mutableStateOf(TextFieldValue("")) }
    var isBicError by rememberSaveable { mutableStateOf(false) }

    var bankNameValue by remember { mutableStateOf(TextFieldValue("")) }
    var isBankNameError by rememberSaveable { mutableStateOf(false) }

    var payIdValue by remember { mutableStateOf(TextFieldValue("")) }
    var isPayIdError by rememberSaveable { mutableStateOf(false) }

    var accNumberValue by remember { mutableStateOf(TextFieldValue("")) }
    var isAccNumberError by rememberSaveable { mutableStateOf(false) }

    var binansIdValue by remember { mutableStateOf(TextFieldValue("")) }
    var isBinansIdError by rememberSaveable { mutableStateOf(false) }

    fun inspectionData(): Boolean {
        var correctData = true

        if (!emailValidation(emailValue.text)) {
            isEmailError = true
            correctData = false
        }

        if (passwordValue.text == "") {
            isPasswordError = true
            correctData = false
        }

        if (nameValue.text == "") {
            isNameError = true
            correctData = false
        }

        if (phoneValue.text == "") {
            isPhoneError = true
            correctData = false
        }

        if (addressValue.text == "") {
            isAddressError = true
            correctData = false
        }

        if (innValue.text == "") {
            isInnError = true
            correctData = false
        }

        if (bicValue.text == "") {
            isBicError = true
            correctData = false
        }

        if (bankNameValue.text == "") {
            isBankNameError = true
            correctData = false
        }

        if (accNumberValue.text == "") {
            isAccNumberError = true
            correctData = false
        }

        if (payIdValue.text == "") {
            isPayIdError = true
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
            val companyInfo = CompanyInfo(
                id =  "",
                name_org = nameValue.text,
                phone = phoneValue.text,
                email = emailValue.text,
                address = addressValue.text,
                inn = innValue.text,
                bic = bicValue.text,
                bank_name = bankNameValue.text,
                checking_account =  accNumberValue.text,
                sum = 0,
                action = true,
                payId = payIdValue.text,
                binansId = binansIdValue.text
            )

            homeViewModel.createNewUser(companyInfo, passwordValue.text)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.verticalScroll(rememberScrollState())) {

            Spacer(modifier = modifier.height(24.dp))

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

            TextField( modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp),
                value = passwordValue,
                onValueChange = {
                    passwordValue = it
                    isPasswordError = false
                },
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = isPasswordError,
                supportingText = {
                    if (isPasswordError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите пароль",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isPasswordError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = nameValue,
                onValueChange = {
                    nameValue = it
                    isNameError = false
                },
                placeholder = { Text("Название организации") },
                singleLine = true,
                isError = isNameError,
                supportingText = {
                    if (isNameError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите название организации",
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
                value = addressValue,
                onValueChange = {
                    addressValue = it
                    isAddressError = false
                },
                placeholder = { Text("адрес") },
                singleLine = true,
                isError = isAddressError,
                supportingText = {
                    if (isAddressError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "адрес",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isAddressError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = innValue,
                onValueChange = {
                    innValue = it
                    isInnError = false
                },
                placeholder = { Text("ИНН") },
                singleLine = true,
                isError = isInnError,
                supportingText = {
                    if (isInnError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите ИНН",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isInnError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = bicValue,
                onValueChange = {
                    bicValue = it
                    isBicError = false
                },
                placeholder = { Text("БИК") },
                singleLine = true,
                isError = isBicError,
                supportingText = {
                    if (isBicError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите БИК",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isBicError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = bankNameValue,
                onValueChange = {
                    bankNameValue = it
                    isBankNameError = false
                },
                placeholder = { Text("Название банка") },
                singleLine = true,
                isError = isBankNameError,
                supportingText = {
                    if (isBankNameError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите название банка",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isBankNameError)
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