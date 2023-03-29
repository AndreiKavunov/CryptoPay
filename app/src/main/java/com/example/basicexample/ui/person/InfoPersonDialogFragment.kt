package com.example.basicexample.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.basicexample.R
import com.example.basicexample.databinding.DialogInfoPersonBinding
import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.ui.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoPersonDialogFragment: Fragment() {

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
                    InfoPersonRoot(
                        personViewModel = personViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun InfoPersonRoot(modifier: Modifier = Modifier, personViewModel: PersonViewModel){
    MyTheme() {
        InfoPersonScreen(
            modifier = modifier,
            personViewModel = personViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoPersonScreen(modifier: Modifier = Modifier, personViewModel: PersonViewModel){

    var personIdValue by remember { mutableStateOf(TextFieldValue())}
    var personIdErrorValue by rememberSaveable { mutableStateOf(false) }

    val personInfo by personViewModel.person.collectAsState()

    fun getInfoPerson(){
        if(personIdValue.text == ""){
            personIdErrorValue = true
        }else{
            personViewModel.getPersonInfo(personIdValue.text)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxSize().verticalScroll(
                    rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally
        ){

            Spacer(modifier = modifier.height(24.dp))

            TextField( modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp),
                value = personIdValue,
                onValueChange = {
                    personIdValue= it
                    personIdErrorValue = false
                },
                placeholder = { Text("Binans id") },
                singleLine = true,
                isError = personIdErrorValue,
                supportingText = {
                    if (personIdErrorValue) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите binans id",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (personIdErrorValue)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = modifier.height(16.dp))

            PersonInfo(modifier, personInfo)

            Spacer(modifier = modifier.height(16.dp))

            Button(
                onClick = {
                    getInfoPerson()
                }, modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                shape = RoundedCornerShape(0)
            ) {
                Text(
                    text = "Получить информацию"
                )
            }
        }
    }


}

@Composable
fun PersonInfo(modifier: Modifier, personInfo: PersonInfo){
    if(personInfo.binansId == "empty"){
        Text(text = "Пользователь не найден")
    }else if(personInfo.binansId != "") {
        Column(horizontalAlignment = Alignment.Start, modifier = modifier.verticalScroll(
            rememberScrollState()).padding(start = 60.dp)) {
            Text(text = "Имя: ${personInfo.name}")
            Text(text = "Фамилия: ${personInfo.surname}")
            Text(text = "Отчество: ${personInfo.surname2}")
            Text(text = "Телефон: ${personInfo.phone}")
            Text(text = "пай ид: ${personInfo.payId}")
            Text(text = "email: ${personInfo.email}")
            Text(text = "номер счета на бирже: ${personInfo.exchangeAccountNumber}")
            Text(text = "бинанс: ${personInfo.binansId}")
            Text(text = "дата регистрации: ${personInfo.dateRegistration}")
        }
    }

}

@Composable
fun PersonInfoEmpty(){
        Text(text = "Пользователь не найден")
}