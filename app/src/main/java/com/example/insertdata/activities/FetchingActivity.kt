package com.example.insertdata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.insertdata.Models.EmployeeModel
import com.example.insertdata.adapters.EmpAdapter
import com.example.insertdata.databinding.ActivityFetchingBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFetchingBinding
    private lateinit var empList:ArrayList<EmployeeModel>
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvEmp.layoutManager= LinearLayoutManager(this)
        binding.rvEmp.setHasFixedSize(true)


        empList= arrayListOf<EmployeeModel>()

        getEmployeesData()


    }

    private fun getEmployeesData() {
        binding.rvEmp.visibility=View.GONE
        binding.tvLoadingData.visibility=View.VISIBLE

        dbRef=FirebaseDatabase.getInstance().getReference("Employees")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for(empSnap in snapshot.children){
                        val empData =empSnap.getValue(EmployeeModel::class.java)
                        empList.add(empData!!)

                    }
                    val mAdapter=EmpAdapter(empList)
                    binding.rvEmp.adapter=mAdapter
                    mAdapter.setOnClickListener(object :EmpAdapter.OnClickListener{
                        override fun onClick(position: Int, model: EmployeeModel) {
                            val intent=Intent(this@FetchingActivity,EmployeeDetailsActivity::class.java)
                            // put Extras
                            intent.putExtra("empId",empList[position].empId)
                            intent.putExtra("empName",empList[position].empName)
                            intent.putExtra("empAge",empList[position].empAge)
                            intent.putExtra("empSalary",empList[position].empSalary)
                            startActivity(intent)
                        }

                    })
                    binding.rvEmp.visibility=View.VISIBLE
                    binding.tvLoadingData.visibility=View.GONE
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}