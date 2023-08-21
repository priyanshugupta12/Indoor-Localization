package com.iitram.indoorlocalization2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.location.LocationProvider
//import android.widget.Toast
import android.os.Environment
import java.io.File
import java.io.FileWriter
import java.io.IOException

class MainActivity : AppCompatActivity(),SensorEventListener,View.OnClickListener{

    private lateinit var sensorManager: SensorManager
    private var magnetometer: Sensor?=null
    private lateinit var textView: TextView
    lateinit var ButtonEND:Button

    private val csvFileName = "location_data.csv"
    private lateinit var csvFile: File
    private lateinit var csvFileWriter: FileWriter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView=findViewById(R.id.textView)
        ButtonEND=findViewById(R.id.ButtonEND)

        ButtonEND.setOnClickListener {
            Toast.makeText(this@MainActivity,"Stopped",Toast.LENGTH_SHORT).show()

            var intent=Intent(this@MainActivity,StartActivity::class.java)
            startActivity(intent)
        }

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),0)
        }

        csvFile = File(Environment.getExternalStorageDirectory(),csvFileName)

        try {
            csvFileWriter = FileWriter(csvFile,true)

            csvFileWriter.append("Latitude,Longitude\n")
        }catch (e:IOException){
            e.printStackTrace()
        }

        val location = Location("dummyProvider")
        location.latitude = 37.12345
        location.longitude = -122.6789
        writeLocationToCsv(location)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    private fun writeLocationToCsv(location: Location){
        try {
            csvFileWriter.append("${location.latitude},${location.longitude}\n")
            csvFileWriter.flush()
        }catch (e:IOException){
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 && grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            println("Permission granted")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            csvFileWriter.close()
        }catch (e:IOException){
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        magnetometer?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        if (sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            if (accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
                print("low")
            } else {
                print("normal")
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            textView.text = "Magnetic Fields:\nX: $x\nY: $y\nZ: $z"
        }
    }
    override fun onClick(p0: View?) {

    }
    fun onLocationChanged(location: Location?){
        location?.let{
            val latitude = location.latitude
            val longitude= location.longitude
            textView.text="current Location: $latitude,$longitude"
        }


    }
}


