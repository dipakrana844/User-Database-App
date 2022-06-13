package com.executor.userdatabaseapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.executor.userdatabaseapp.DB.UserEntity
import kotlinx.android.synthetic.main.row_contact.view.*

class UserAdapter(private var listener: RowClickListener) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private var myUser = emptyList<UserEntity>()

    fun setListData(data: List<UserEntity>) {
        this.myUser = data
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View, private val listener: RowClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val fName = itemView.tvContactfName
        val lName = itemView.tvContactlName
        val number = itemView.tvContactNumber
        val age = itemView.tvContactAge
        val deleteUserID = itemView.ibContactDeleteBtn
        fun bind(data: UserEntity) {
            deleteUserID.setOnClickListener {
                listener.onDeleteUserClickListener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_contact, parent, false)
        return MyViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userInfo = myUser[position]
        holder.fName.text = userInfo.fName
        holder.lName.text = userInfo.lName
        holder.age.text = userInfo.age.toString()
        holder.number.text = userInfo.number.toString()

        holder.itemView.setOnClickListener {
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener.onItemClickListener(myUser[position])
            }
        }
        holder.bind(myUser[position])
    }

    override fun getItemCount(): Int {
        return myUser.size
    }

    interface RowClickListener {
        fun onDeleteUserClickListener(user: UserEntity)
        fun onItemClickListener(user: UserEntity)
    }

//    fun setOnItemClickListener(listener: RowClickListener) {
//        this.listener = listener
//    }
}
