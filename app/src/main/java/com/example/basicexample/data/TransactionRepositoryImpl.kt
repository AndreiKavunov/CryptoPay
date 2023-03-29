package com.example.basicexample.data

import android.util.Log
import com.example.basicexample.data.dto.TransactionDto
import com.example.basicexample.domain.models.Transaction
import com.example.basicexample.domain.repository.TransactionRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val firebase: Firebase) :
    TransactionRepository {

    companion object {
        const val COMPANY = "company"
        const val TRANSACTION = "transaction"
    }

    override suspend fun createTransaction(transaction: Transaction, sum: Float): Result<Unit> {
        return runCatching {


            val transactionDto = transaction.toDataModule()
            val companyId = firebase.auth.currentUser?.uid ?: ""

            transactionDto.companyId = companyId
            Log.d("tag1", "start ${transaction.date}")

            firebase.firestore
                .collection(TRANSACTION)
                .add(transactionDto)
                .await()
                .addSnapshotListener { value, error ->
                    Log.d("tag1", "end $value, ${error?.message}")
                }



            firebase.firestore
                .collection(COMPANY)
                .document(companyId)
                .update("sum", sum)
                .await()
        }
    }

    override suspend fun getTransactionsInDAte(startTime: String, endTime: String): Result<List<Transaction>> {
        return runCatching {
            val date = LocalDate.of(2021, 9, 13)
            val startTimestamp = dateFormatToTimeStamp(startTime)
            val endTimestamp = dateFormatToTimeStamp(endTime)

            Log.d("tag1", "startTimestamp  $startTimestamp   $startTime")
            Log.d("tag1", "startTimestamp  $endTimestamp   $endTime")
            // Запрашиваем все записи на эту дату
            val result = firebase.firestore.collection("transaction")
                .whereGreaterThanOrEqualTo("date", startTimestamp)
                .whereLessThanOrEqualTo("date", endTimestamp)
                .get()
                .await()

            val transactions: MutableList<Transaction> = mutableListOf()
            result.documents.forEach {
                transactions.add(
                    Transaction(
                        companyId = it.get("companyId") as? String ?: "",
                        personId = it.get("personId") as? String ?: "",
                        token = it.get("token") as? String ?: "",
                        date = it.get("date") as? String ?: "",
                        payId = it.get("payId") as? String ?: "",
                        binansId = it.get("binansId") as? String ?: "",
                        sumRub = it.get("sumRub") as? String ?: "",
                        sumToken = it.get("sumToken") as? String ?: "",
                    )
                )
            }
            transactions
//            Log.d("tag1", "transactions $transactions")
//
////            val workbook = WorkbookFactory.create(true)
////            val sheet = workbook.createSheet("Transactions")
////            var rowNum = 0
////            for (transaction in transactions) {
////                val row = sheet.createRow(rowNum++)
////                row.createCell(0).setCellValue(transaction["title"].toString())
////                row.createCell(1).setCellValue((transaction["amount"] as Long).toDouble())
////                row.createCell(2).setCellValue(Date(transaction["date"] as Long))
////            }
////            val fileOut = FileOutputStream("transactions_${date}.xlsx")
////            workbook.write(fileOut)
//
//            val workbook = WorkbookFactory.create(true)
//            val sheet = workbook.createSheet("Transactions") // Задаем название листа
//            var rowNum = 0
//// Создаем заголовок для каждого столбца в таблице
//            val headerRow = sheet.createRow(rowNum)
//            headerRow.createCell(0).setCellValue("sumRub")
//            headerRow.createCell(1).setCellValue("companyId")
//// Записываем данные в каждую строку таблицы
//            for (document in transactions) {
//                rowNum++
//                val row = sheet.createRow(rowNum)
//                row.createCell(0).setCellValue(document.sumRub)
//                row.createCell(1).setCellValue(document.companyId)
//
//                // Сохраняем Excel файл на устройстве
//                val file = File(context.cacheDir, "data.xlsx")
//                val os = FileOutputStream(file)
//                workbook.write(os)
//                os.close()
//
//

//            .get()
//            .map { it.data }

        }
    }
}


private fun Transaction.toDataModule(): TransactionDto {
    return TransactionDto(
        companyId = this.companyId,
        personId = this.personId,
        token = this.token,
        date = this.date.toLong(),
        payId = this.payId,
        binansId = this.binansId,
        sumRub = this.sumRub,
        sumToken = this.sumToken,
    )
}

private fun dateFormatToTimeStamp(formattedDate: String): Long {
    val formatter =
        SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()) // создаем форматтер даты
    formatter.timeZone = TimeZone.getDefault() // устанавливаем для форматтера текущий часовой пояс
    val date = formatter.parse(formattedDate) // преобразуем строку с датой в объект класса Date
    return date?.time ?: 0
}

private fun timeStampToDateFormat(timeStamp: Long): String {
    val date = Date(timeStamp) // создаем объект класса Date из timestamp
    val formatter =
        SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()) // создаем форматтер даты
    formatter.timeZone = TimeZone.getDefault() // устанавливаем для форматтера текущий часовой пояс
    return formatter.format(date) // преобразуем дату в строку в нужном формате
}