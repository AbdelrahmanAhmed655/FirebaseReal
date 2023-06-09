package com.example.insertdata.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.insertdata.Models.EmployeeModel
import com.example.insertdata.databinding.ActivityInsertionBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsertionBinding
    private lateinit var dpRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dpRef=FirebaseDatabase.getInstance().getReference("Employees")

        binding.btnSave.setOnClickListener {
            SaveEmployeeData()
        }


    }

    private fun  SaveEmployeeData() {
        //getting values
        val empName = binding.etEmpName.text.toString()
        val empAge =binding.etEmpAge.text.toString()
        val empSalary=binding.etEmpSalary.text.toString()



        if (empName.isEmpty()){
            binding.etEmpName.error="please enter name"
        }
        if (empAge.isEmpty()){
            binding.etEmpAge.error="please enter age"
        }
        if (empSalary.isEmpty())
        {
            binding.etEmpSalary.error="please enter salary"
        }

        val empId=dpRef.push().key!!

        val employee= EmployeeModel(empId,empName,empAge,empSalary)

        dpRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this,"Data inserted successfully",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {e->
                Toast.makeText(this,"error${e.message}",Toast.LENGTH_LONG).show()
            }


    }

}