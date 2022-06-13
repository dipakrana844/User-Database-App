package com.executor.userdatabaseapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.executor.userdatabaseapp.DB.UserEntity
import com.executor.userdatabaseapp.DB.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserAdapter.RowClickListener {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabAddBtn.setOnClickListener {
            val intent = Intent(this, UserDetailActivity::class.java)
            startActivity(intent)
        }

        val adapter = UserAdapter(this)
        rvUser.adapter = adapter
        rvUser.layoutManager = LinearLayoutManager(this)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.getAllUser.observe(this) {
            adapter.setListData(it)
        }

    }

    override fun onDeleteUserClickListener(user: UserEntity) {
        mUserViewModel.deleteUser(user)
    }

    override fun onItemClickListener(user: UserEntity) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("id", user.id)
        intent.putExtra("fname", user.fName)
        intent.putExtra("lname", user.lName)
        intent.putExtra("number", user.number)
        intent.putExtra("dob", user.dob)
        startActivity(intent)
    }

}
