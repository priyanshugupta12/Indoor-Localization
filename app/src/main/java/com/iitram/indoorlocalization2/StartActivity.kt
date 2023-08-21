package com.iitram.indoorlocalization2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class StartActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var TxtIndoor: TextView
    lateinit var ImgIndoor: ImageView
    lateinit var ButStart: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        TxtIndoor=findViewById(R.id.TxtIndoor)
        ImgIndoor=findViewById(R.id.ImgIndoor)
        ButStart=findViewById(R.id.ButStart)

        ButStart.setOnClickListener {
            Toast.makeText(this@StartActivity,"Indoor Localization Started!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@StartActivity,MainActivity::class.java)
            startActivity(intent)
        }



    }

    override fun onClick(p0: View?) {

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}