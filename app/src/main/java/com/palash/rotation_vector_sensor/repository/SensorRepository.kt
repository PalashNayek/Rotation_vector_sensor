package com.palash.rotation_vector_sensor.repository

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class SensorRepository @Inject constructor(private val sensorManager: SensorManager) {

    private val _rotationVectorData = MutableLiveData<Triple<Float, Float, Float>>()
    val rotationVectorData: LiveData<Triple<Float, Float, Float>> = _rotationVectorData

    private val rotationVectorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if (it.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    _rotationVectorData.postValue(Triple(it.values[0], it.values[1], it.values[2]))
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // No action needed
        }
    }

    fun startListening() {
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.also { sensor ->
            sensorManager.registerListener(rotationVectorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(rotationVectorListener)
    }
}