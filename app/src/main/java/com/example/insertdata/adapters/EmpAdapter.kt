package com.example.insertdata.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.insertdata.Models.EmployeeModel
import com.example.insertdata.R
import com.example.insertdata.databinding.EmpListItemBinding

class EmpAdapter(private val empList:ArrayList<EmployeeModel>):RecyclerView.Adapter<EmpAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null


    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: EmployeeModel)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpAdapter.ViewHolder {
       val itemView =EmpListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: EmpAdapter.ViewHolder, position: Int) {
        val currentEmp=empList[position]
        holder.binding.tvEmpName.text= currentEmp.empName

        holder.itemView.setOnClickListener {
            if (onClickListener!=null){
                onClickListener!!.onClick(position,currentEmp)
            }
        }


    }

    override fun getItemCount(): Int {
        return empList.size
    }
    class ViewHolder( val binding: EmpListItemBinding): RecyclerView.ViewHolder(binding.root){



    }

}