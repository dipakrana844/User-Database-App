package com.executor.userdatabaseapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.executor.userdatabaseapp.DB.UserEntity
import com.executor.userdatabaseapp.DB.UserViewModel
import kotlinx.android.synthetic.main.activity_update.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateActivity : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel

    companion object {
        private var myAge = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

//        val fName =
//        val lName = intent.getStringExtra("lname")
//        val number = intent.getIntExtra("number",0)


        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updatable(myCalender)
        }

        tvUpdateCalender.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        etUpdateFirstName.setText(intent.getStringExtra("fname"))
        etUpdateLastName.setText(intent.getStringExtra("lname"))
        etUpdateNumber.setText(intent.getStringExtra("number"))
        tvUpdateCalender.text = intent.getStringExtra("dob")


        btnUpdate.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun updatable(myCalender: Calendar) {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        Toast.makeText(this, "$currentYear", Toast.LENGTH_SHORT).show()
        val birthYear = myCalender.get(Calendar.YEAR)
        Toast.makeText(this, "$birthYear", Toast.LENGTH_SHORT).show()
        myAge = currentYear - birthYear
//        Toast.makeText(this, "$myAge", Toast.LENGTH_SHORT).show()
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvUpdateCalender.text = sdf.format(myCalender.time)
    }

    private fun insertDataToDatabase() {

        val fName = etUpdateFirstName.text.toString()
        val lName = etUpdateLastName.text.toString()
        val number = etUpdateNumber.text.toString()
        val dob = tvUpdateCalender.text.toString()

        if (!TextUtils.isEmpty(fName) && !TextUtils.isEmpty(lName) && !TextUtils.isEmpty(dob) && number.isNotEmpty()
        ) {
            val user =
                UserEntity(
                    0,
                    "hi",
                    fName,
                    lName,
                    dob,
                    myAge,
                    (number.toString()).toLong(),
                    Date()
                )

            mUserViewModel.updateUser(user)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(
                this,
                "Please fill out all Fields ",
                Toast.LENGTH_SHORT
            )
                .show()
        }

    }
}
