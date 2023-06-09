package com.example.insertdata.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.insertdata.Models.EmployeeModel
import com.example.insertdata.R
import com.example.insertdata.databinding.ActivityEmployeeDetailsBinding
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setValuesToViews()

        binding.btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )

        }
        binding.btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun deleteRecord(id: String) {
        val dbRef=FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask=dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Employee data deleted",Toast.LENGTH_LONG).show()
            val intent=Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)


        }.addOnFailureListener { error->
            Toast.makeText(this,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()
        }


    }


    private fun openUpdateDialog(empId: String, empName: String) {
        val  mDialog=AlertDialog.Builder(this)
        val inflater =layoutInflater
        val mDialogView=inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etEmpName=mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etEmpAge=mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etEmpSalary=mDialogView.findViewById<EditText>(R.id.etEmpSalary)
        val btnUpdateData=mDialogView.findViewById<Button>(R.id.btnUpdateData)


        etEmpName.setText(intent.getStringExtra("empName").toString())
        etEmpAge.setText(intent.getStringExtra("empAge").toString())
        etEmpSalary.setText(intent.getStringExtra("empSalary").toString())

        mDialog.setTitle("updating $empName Record")
        val alertDialog=mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpName.text.toString(),
                etEmpAge.text.toString(),
                etEmpSalary.text.toString()
            )
            Toast.makeText(this,"Employee Data Updated ",Toast.LENGTH_LONG).show()

            //we are setting updated data to our textViews
            binding.tvEmpName.text=etEmpName.text.toString()
            binding.tvEmpAge.text=etEmpAge.text.toString()
            binding.tvEmpSalary.text=etEmpSalary.text.toString()


            alertDialog.dismiss()

        }

    }

    private fun updateEmpData(id: String, name: String, age: String, salary: String) {
        val dbRef =FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo=EmployeeModel(id,name,age,salary)
        dbRef.setValue(empInfo)


    }

    private fun setValuesToViews(){
        binding.tvEmpId.text=intent.getStringExtra("empId")
        binding.tvEmpName.text=intent.getStringExtra("empName")
        binding.tvEmpAge.text=intent.getStringExtra("empAge")
        binding.tvEmpSalary.text=intent.getStringExtra("empSalary")

    }
}