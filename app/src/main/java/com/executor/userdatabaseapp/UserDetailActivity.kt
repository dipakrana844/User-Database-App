package com.executor.userdatabaseapp

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageRequest
import com.executor.userdatabaseapp.DB.UserEntity
import com.executor.userdatabaseapp.DB.UserViewModel
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class UserDetailActivity : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel

    private val CAMERA_REQUEST = 100
    private val STORAGE_REQUEST = 101

    lateinit var cameraPermission: Array<String>
    lateinit var storagePermission: Array<String>

    var imagePath: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        cameraPermission =
            arrayOf(android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        ivProfile.setOnClickListener {
            val pictureId = 0;
            if (pictureId == 0) {
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                } else {
                    pickFromGallery()
                }
            } else if (pictureId == 1) {
                if (!checkStoragePermission()) {
                    requestStoragePermission()
                } else {
                    pickFromGallery()
                }
            }
//            val image = BitmapFactory.decodeFile(imagePath)

        }

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
//    private fun getBitmap(): Bitmap {
//
//        val request = ImageRequest.Builder(this)
//            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
//            .build()
//
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }

    private fun requestCameraPermission() {
        requestPermissions(cameraPermission, STORAGE_REQUEST)
    }

    private fun requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST)
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED)
    }

    private fun pickFromGallery() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this);

    }


    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED)
        val result2 = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED)
        return result && result2

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
//                Toast.makeText(this, "$resultUri", Toast.LENGTH_SHORT).show()
//                Picasso.get().load(resultUri).into(ivProfile)
                val pic = Picasso.get()
                    .load(resultUri)
                    .resize(1080, 1080)
                    .centerCrop()
                    .into(ivProfile)

                imagePath = resultUri.path
                Log.d("Image path", "onActivityResult: $imagePath")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST -> {
                if (grantResults.isNotEmpty()) {
                    val camera_accepted = grantResults[0] == (PackageManager.PERMISSION_GRANTED)
                    val storage_accepted = grantResults[1] == (PackageManager.PERMISSION_GRANTED)
                    if (camera_accepted && storage_accepted) {
                        pickFromGallery()
                    } else {
                        Toast.makeText(this,
                            "Please enable to the camera and storage permission",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
            STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty()) {
                    val storage_accepted = grantResults[0] == (PackageManager.PERMISSION_GRANTED)
                    if (storage_accepted) {
                        pickFromGallery()
                    } else {
                        Toast.makeText(this,
                            "Please enable to storage permission",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun updatable(myCalender: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvContactCalender.text = sdf.format(myCalender.time)
    }

    private fun insertDataToDatabase() {
        val img = BitmapFactory.decodeFile(imagePath)

        val stream = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG, 20, stream)
        val imageByte: ByteArray = stream.toByteArray()

//        Log.d("Image", "insertDataToDatabase: $img")
        val fName = etContactFirstName.text.toString()
        val lName = etContactLastName.text.toString()
        val dob = tvContactCalender.text.toString()
        val number = etContactNumber.text

        if (number != null) {
            if (!TextUtils.isEmpty(fName) && !TextUtils.isEmpty(lName) && !TextUtils.isEmpty(dob) && number.isNotEmpty()
            ) {
                val user =
                    UserEntity(0,
                        imageByte,
                        fName,
                        lName,
                        dob,
                        Integer.parseInt(number.toString()),
                        Date())

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

}