package com.example.basicexample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.basicexample.R
import com.example.basicexample.ui.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose the Composition when viewLifecycleOwner is destroyed
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                MaterialTheme {
                    HomeRoot(
                        homeViewModel = homeViewModel,
                        navigateToRegistry = ::openRegistration
                    )
                }
            }
        }
    }

    private fun openRegistration() {
        val direction = HomeFragmentDirections.actionNavigationPersonToRegistrationDialogFragment()
        findNavController().navigate(direction)
    }
}

@Composable
fun HomeRoot(
    homeViewModel: HomeViewModel,
    navigateToRegistry: () -> Unit
) {
    MyTheme() {
        val user by homeViewModel.user.collectAsState()
        val textDialog by homeViewModel.textDialog.collectAsState()
        if (user.email.isNotEmpty()) ShowAuthorizedHome(homeViewModel = homeViewModel) else ShowAnonymousHome(
            navigateToRegistry = navigateToRegistry,
            homeViewModel = homeViewModel
        )

        if (textDialog != "") {
            Toast.makeText(LocalContext.current, textDialog, Toast.LENGTH_LONG).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAuthorizedHome(modifier: Modifier = Modifier, homeViewModel: HomeViewModel) {
    var sumValue by remember { mutableStateOf(TextFieldValue("")) }
    var personIdValue by remember { mutableStateOf(TextFieldValue("")) }
    var isErrorSum by rememberSaveable { mutableStateOf(false) }
    var isErrorPersonId by rememberSaveable { mutableStateOf(false) }

    fun goTransaction() {
        if (sumValue.text != "" && personIdValue.text != "") {
            homeViewModel.getBalance(sum = sumValue.text.toFloat(), personId = personIdValue.text)
        } else {
            if (sumValue.text == "") isErrorSum = true
            if (personIdValue.text == "") isErrorPersonId = true

        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.verticalScroll(
            rememberScrollState())) {
            Image(
                painter = painterResource(id = R.drawable.i8crypto_logo),
                contentDescription = "logo"
            )

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = sumValue,
                onValueChange = {
                    sumValue = it
                    isErrorSum = false
                },
                placeholder = { Text("Сумма") },
                singleLine = true,
                isError = isErrorSum,
                supportingText = {
                    if (isErrorSum) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите сумму",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isErrorSum)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = modifier.height(34.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = personIdValue,
                onValueChange = {
                    personIdValue = it
                    isErrorPersonId = false
                },
                placeholder = { Text("Бинанс клиента") },
                singleLine = true,
                isError = isErrorPersonId,
                supportingText = {
                    if (isErrorPersonId) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите isPersonId",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isErrorPersonId)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )
            Spacer(modifier = modifier.height(34.dp))

            Button(
                onClick = {
                    goTransaction()
                }, modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                shape = RoundedCornerShape(0)
            ) {
                Text(
                    text = "Оплата"
                )
            }

            Image(
                painter = painterResource(id = R.drawable.rectangle_6),
                contentDescription = "logo"
            )

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAnonymousHome(
    modifier: Modifier = Modifier,
    navigateToRegistry: () -> Unit,
    homeViewModel: HomeViewModel
) {
    var loginValue by remember { mutableStateOf(TextFieldValue("")) }
    var passwordValue by remember { mutableStateOf(TextFieldValue("")) }
    var isErrorLogin by rememberSaveable { mutableStateOf(false) }
    var isErrorPassword by rememberSaveable { mutableStateOf(false) }


    fun signIn() {
        if (emailValidation(loginValue.text) && passwordValue.text != "") {
            homeViewModel.signIn(loginValue.text, passwordValue.text)
        } else {
            if (!emailValidation(loginValue.text)) isErrorLogin = true
            if (passwordValue.text == "") isErrorPassword = true

        }
    }
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.verticalScroll(
            rememberScrollState()
        )) {
            Image(
                painter = painterResource(id = R.drawable.i8crypto_logo),
                contentDescription = "logo"
            )

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = loginValue,
                onValueChange = {
                    loginValue = it
                    isErrorLogin = false
                },
                placeholder = { Text("Login") },
                singleLine = true,
                isError = isErrorLogin,
                supportingText = {
                    if (isErrorLogin) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите корректный email",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isErrorLogin)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )

            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                value = passwordValue,
                onValueChange = {
                    passwordValue = it
                    isErrorPassword = false
                },
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = isErrorPassword,
                supportingText = {
                    if (isErrorPassword) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Введите пароль",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isErrorPassword)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                },
            )
            Spacer(modifier = modifier.height(16.dp))

            Button(
                onClick = { signIn() }, modifier = modifier
                    .padding(horizontal = 60.dp)
                    .fillMaxWidth()
//                    .shadow()
                    ,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp,
                    disabledElevation = 0.dp
                ),
                shape = RoundedCornerShape(0)
            ) {
                Text(
                    text = "Войти", color = Color.Black
                )
            }

            Spacer(modifier = modifier.height(16.dp))

            TextButton(
                onClick = { navigateToRegistry() },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
            ) {
                Text(
                    text = "Зарегистрироваться",
                    color = colorResource(id = R.color.grey),
                    textDecoration = TextDecoration.Underline
                )
            }

            Image(
                painter = painterResource(id = R.drawable.rectangle_6),
                contentDescription = "logo"
            )

        }

    }

}

private fun emailValidation(email: String): Boolean {
    val regex =
        Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")
    return regex.matches(email)
}

private fun Modifier.bottomElevation(): Modifier = this.then(Modifier.drawWithContent {
    val paddingPx = 8.dp.toPx()
    clipRect(
        left = 0f,
        top = 0f,
        right = size.width,
        bottom = size.height + paddingPx
    ) {
        this@drawWithContent.drawContent()
    }
})