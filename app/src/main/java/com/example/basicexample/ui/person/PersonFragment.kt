package com.example.basicexample.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basicexample.R
import com.example.basicexample.ui.MyTheme

class PersonFragment : Fragment() {

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
                    PersonRoot(
                        navigateToInfoPerson = ::openInfoPerson,
                        navigateToAddPerson = ::openAddPerson
                    )
                }
            }
        }
    }

    private fun openAddPerson() {
        val direction = PersonFragmentDirections.actionNavigationPersonToAddPersonDialogFragment()
        findNavController().navigate(direction)
    }

    private fun openInfoPerson() {
        val direction = PersonFragmentDirections.actionNavigationPersonToInfoPersonDialogFragment()
        findNavController().navigate(direction)
    }

}

@Composable
fun PersonRoot(
    modifier: Modifier = Modifier,
    navigateToInfoPerson: () -> Unit,
    navigateToAddPerson: () -> Unit
) {
    MyTheme() {
        PersonScreen(
            modifier = modifier,
            navigateToInfoPerson = navigateToInfoPerson,
            navigateToAddPerson = navigateToAddPerson
        )
    }
}

@Composable
fun PersonScreen(
    modifier: Modifier = Modifier,
    navigateToInfoPerson: () -> Unit,
    navigateToAddPerson: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.greyback))
    ) {
        Column(
            modifier = modifier
                .fillMaxSize().verticalScroll(
                    rememberScrollState()
                    ), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.i8crypto_logo5),
                contentDescription = "logo"
            )

            Button(
                onClick = { navigateToInfoPerson() }, modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                shape = RoundedCornerShape(0)
            ) {
                Text(
                    text = "Информация о пользователе"
                )
            }

            Spacer(modifier = modifier.height(16.dp))

            Button(
                onClick = { navigateToAddPerson() }, modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                shape = RoundedCornerShape(0)
            ) {
                Text(
                    text = "Добавить пользователя"
                )
            }

            Spacer(modifier = modifier.height(16.dp))

            Text(
                text = "Создать аккаунт на криптобирже"
            )

            Spacer(modifier = modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.qr_binance),
                contentDescription = "qr code"
            )
//
//            Spacer(modifier = modifier.height(16.dp))
//
//            Image(
//                painter = painterResource(id = R.drawable.rectangle_6),
//                contentDescription = "background"
//            )
        }
    }
}