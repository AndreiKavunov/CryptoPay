package com.example.basicexample.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.example.basicexample.BuildConfig
import com.example.basicexample.domain.models.Transaction
import com.example.basicexample.ui.MyTheme
import com.example.basicexample.ui.home.HomeViewModel
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Date

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by activityViewModels()

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                MaterialTheme {
                    // In Compose world
                    ProfileRoot(
                        homeViewModel = homeViewModel,
                        profileViewModel = viewModel,
                        activity = activity,
                        context = requireContext(),
                        requireActivity = requireActivity()
                    )
                }
            }
        }
    }


}

fun createExcelFile(
    transactions: List<Transaction>,
    activity: FragmentActivity?,
    context: Context,
    requireActivity: Activity
) {
    val workbook = WorkbookFactory.create(true)
    val sheet = workbook.createSheet("Items")
    val headers = arrayOf("companyId", "personId", "token", "date", "payId", "binansId", "sumRub", "sumToken")
    val headerRow: Row = sheet.createRow(0)

    // Add headers
    for (i in headers.indices) {
        headerRow.createCell(i).setCellValue(headers[i])
    }

    // Add data
    for (i in transactions.indices) {
        val item: Transaction = transactions[i]
        val row: Row = sheet.createRow(i + 1)
        row.createCell(0).setCellValue(item.companyId)
        row.createCell(1).setCellValue(item.personId)
        row.createCell(2).setCellValue(item.token)
        row.createCell(3).setCellValue(item.date)
        row.createCell(4).setCellValue(item.payId)
        row.createCell(5).setCellValue(item.binansId)
        row.createCell(6).setCellValue(item.sumRub)
        row.createCell(7).setCellValue(item.sumToken)
    }

    // Save file
    val fileName = "transaction.xlsx"
    val file = File(activity?.getExternalFilesDir(null), fileName)

    try {
        val outputStream = FileOutputStream(file)
        workbook.write(outputStream)
        Log.d("tag1", "outputStream $outputStream")
    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("tag1", "e ${e.message}")
    }


    val uri = FileProvider.getUriForFile(
        context,
        BuildConfig.APPLICATION_ID + ".provider", file
    )
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "application/vnd.ms-excel"
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    requireActivity.startActivity(Intent.createChooser(intent, "Share Excel file"))

}

@Composable
fun ProfileRoot(
    homeViewModel: HomeViewModel,
    profileViewModel: ProfileViewModel,
    activity: FragmentActivity?, context: Context,
    requireActivity: Activity
) {
    MyTheme() {
        ProfileScreen(
            profileViewModel = profileViewModel,
            homeViewModel = homeViewModel,
            activity = activity,
            context = context,
            requireActivity = requireActivity
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel,
    activity: FragmentActivity?,
    context: Context,
    requireActivity: Activity
) {

    val localLifecycleOwner = LocalLifecycleOwner.current
    val user by homeViewModel.user.collectAsState()

    var startDate by remember { mutableStateOf(TextFieldValue("")) }
    var startTime by remember { mutableStateOf(TextFieldValue("")) }
    var endDate by remember { mutableStateOf(TextFieldValue("")) }
    var endTime by remember { mutableStateOf(TextFieldValue("")) }

    var isStartDateError by rememberSaveable { mutableStateOf(false) }
    var isStartTimeError by rememberSaveable { mutableStateOf(false) }
    var isEndDateError by rememberSaveable { mutableStateOf(false) }
    var isEndTimeError by rememberSaveable { mutableStateOf(false) }


    val maxCharDate = 8
    val maxCharTime = 4

    fun inspectionDateStart(): String {
        var start = ""
        if (startDate.text.length == 8) {
            start = stringToDate(startDate.text)
            if(!dateValidation(start)){ isStartDateError = true}
        } else {
            isStartDateError = true
        }

        if (startTime.text.length == 4) {
            start += " " + stringToTime(startTime.text)
            if(!timeValidation(start.drop(1))) isStartTimeError = true
        } else if (startTime.text.isEmpty()) {
            start += " 00:00"
        } else {
            isStartTimeError = true
        }
        return start
    }

    fun inspectionDateEnd(): String {
        var end = ""
        if (endDate.text.length == 8) {
            end = stringToDate(endDate.text)
            if(!dateValidation(end)){ isEndDateError = true}
        } else if (endDate.text.isEmpty()) {
            return ""
        } else {
            isEndDateError = true
        }

        if (endTime.text.length == 4) {
            end += " " + stringToTime(endTime.text)
        } else if (endTime.text.isEmpty()) {
            end += " 00:00"
        } else {
            isEndTimeError = true
        }
        return end
    }

    fun transaction() {
        val start = inspectionDateStart()
        var end = inspectionDateEnd()

        if(end == ""){
            val localDate = LocalDate.parse(start.substring(0, 10), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            val formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            var period = Period.of(0, 0, 1)
            end = localDate.plus(period).format(formatters).toString() + " 00:00"
        }

        if(!isStartDateError && !isStartTimeError && !isEndDateError && !isEndTimeError){
            profileViewModel.getTransactions(start, end).observe(localLifecycleOwner) {
                it.onSuccess {
                    if (it.isNotEmpty()) {
                        createExcelFile(
                            activity = activity,
                            context = context,
                            transactions = it,
                            requireActivity = requireActivity
                        )
                    }else Toast.makeText(context, "Записей не найдено", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.verticalScroll(
            rememberScrollState())
        ) {

            Spacer(modifier = modifier.height(16.dp))

            Text(text = "email:   ${user.email}")

            Spacer(modifier = modifier.height(8.dp))

            Text(text = "balance:   ${user.balance}")

            Spacer(modifier = modifier.height(16.dp))

            Row(modifier = modifier.padding(horizontal = 16.dp)) {

                TextField(
                    modifier = modifier
                        .weight(1f),
                    value = startDate,
                    onValueChange = {
                        if (it.text.length <= maxCharDate) startDate = it
                        isStartDateError = false
                    },
                    placeholder = { Text("start date") },
                    isError = isStartDateError,
                    supportingText = {
                        if (isStartDateError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "неверное значение",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        if (isStartDateError)
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = MaterialTheme.colorScheme.error
                            )
                    },
                    singleLine = true,
                    visualTransformation = DateTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = modifier.width(4.dp))

                TextField(
                    modifier = modifier
                        .weight(1f),
                    value = startTime,
                    onValueChange = {
                        if (it.text.length <= maxCharTime) startTime = it
                        isStartTimeError = false
                    },
                    placeholder = { Text("start time") },
                    isError = isStartTimeError,
                    supportingText = {
                        if (isStartTimeError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "неверное значение",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        if (isStartTimeError)
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = MaterialTheme.colorScheme.error
                            )
                    },
                    singleLine = true,
                    visualTransformation = TimeTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Spacer(modifier = modifier.height(16.dp))

            Row(modifier = modifier.padding(horizontal = 16.dp)) {

                TextField(
                    modifier = modifier
                        .weight(1f),
                    value = endDate,
                    onValueChange = {
                        if (it.text.length <= maxCharDate) endDate = it
                        isEndDateError = false
                    },
                    placeholder = { Text("end date") },
                    isError = isEndDateError,
                    supportingText = {
                        if (isEndDateError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "неверное значение",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        if (isEndDateError)
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = MaterialTheme.colorScheme.error
                            )
                    },
                    singleLine = true,
                    visualTransformation = DateTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = modifier.width(4.dp))

                TextField(
                    modifier = modifier
                        .weight(1f),
                    value = endTime,
                    onValueChange = {
                        if (it.text.length <= maxCharTime) endTime = it
                        isEndTimeError = false
                    },
                    placeholder = { Text("end time") },
                    isError = isEndTimeError,
                    supportingText = {
                        if (isEndTimeError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "неверное значение",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        if (isEndTimeError)
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = MaterialTheme.colorScheme.error
                            )
                    },
                    singleLine = true,
                    visualTransformation = TimeTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Spacer(modifier = modifier.height(16.dp))

            Button(
//                onClick = { profileViewModel.getTransactions("2", "4") },
                onClick = {
                    transaction()
                },
                modifier = modifier.wrapContentSize()
            ) {
                Text(text = "Transaction")
            }
        }

    }
}

fun stringToDate(string: String): String {
    return string.dropLast(6) + "-" + string.drop(2).dropLast(4) + "-" + string.drop(4)
}

fun stringToTime(string: String): String {
    return string.dropLast(2) + ":" + string.drop(2)
}

private fun amountOfDays(month: Int, year: Int): Int {
    return when (month) {
        2 -> if (year % 4 == 0) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }
}
private fun dateValidation(dateStr: String?): Boolean {
    return try {
        val day = dateStr?.dropLast(8)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val date = LocalDate.parse(dateStr, formatter)

        date.year in 1901..2075 && day?.isNotEmpty() == true && day.toInt() <= amountOfDays(
            date.monthValue,
            date.year
        )

    } catch (e: DateTimeParseException) {

        false
    }

}

private fun timeValidation(time: String): Boolean{
    return try{
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val date = LocalTime.parse(time, formatter)
        true
    } catch(e: DateTimeParseException){

        false
    }
}



