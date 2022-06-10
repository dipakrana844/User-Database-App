package com.executor.userdatabaseapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.executor.userdatabaseapp.DB.UserEntity
import com.executor.userdatabaseapp.DB.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class UserDetailActivity : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)


        val myCalender = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updatable(myCalender)
        }

        tvContactCalender.setOnClickListener {
            DatePickerDialog(this,
                datePicker,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)).show()

        }

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        btnSave.setOnClickListener {
            insertDataToDatabase()
        }

    }

    private fun updatable(myCalender: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvContactCalender.text = sdf.format(myCalender.time)
    }

    private fun insertDataToDatabase() {
        val fName = etContactFirstName.text.toString()
        val lName = etContactLastName.text.toString()
        val dob = tvContactCalender.text.toString()
        val number = etContactNumber.text

        if (!TextUtils.isEmpty(fName) && !TextUtils.isEmpty(lName) && !TextUtils.isEmpty(dob) && number.isNotEmpty()
        ) {
            val user = UserEntity(0, fName, lName, dob, Integer.parseInt(number.toString()), Date())
            mUserViewModel.insertUser(user)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Successfully Added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,
                "Please fill out all Fields ",
                Toast.LENGTH_SHORT)
                .show()
        }
    }

}