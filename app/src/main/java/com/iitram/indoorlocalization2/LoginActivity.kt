package com.iitram.indoorlocalization2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.content.Intent

class LoginActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var edtName:EditText
    lateinit var edtPassword:EditText
    lateinit var butLogin:Button
    lateinit var imgLogin:ImageView
    val ValidName="Priyanshu Gupta"
    val ValidPassword="1234"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        edtName=findViewById(R.id.edtName)
        edtPassword=findViewById(R.id.edtPassword)
        butLogin=findViewById(R.id.butLogin)
        imgLogin=findViewById(R.id.imgLogin)


        butLogin.setOnClickListener {
            val Name=edtName.text.toString()
            val Password=edtPassword.text.toString()

            if ((Name==ValidName) && (Password==ValidPassword)){

                var intent=Intent(this@LoginActivity,StartActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this@LoginActivity,"Incorrect User Name or Password",Toast.LENGTH_SHORT).show()
            }
        }



    }

    override fun onClick(p0: View?) {

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}