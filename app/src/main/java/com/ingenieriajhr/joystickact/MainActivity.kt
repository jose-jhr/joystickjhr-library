package com.ingenieriajhr.joystickact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.TextView
import com.ingenieriajhr.joystickjhr.JoystickJhr
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        joystick.setOnTouchListener(OnTouchListener { view, motionEvent ->
            joystick.move(motionEvent)
            distanciaX.setText("X = " + joystick.joyX())
            distanciaY.setText("Y = " + joystick.joyY())
            angle.setText("angle = " + joystick.angle())
            distancia.setText("distancia = " + joystick.distancia())
            val dir: Int = joystick.getDireccion()
            if (dir == joystick.stick_up()) {
                dirKey.setText("Direction : Up")
            } else if (dir == joystick.stick_upRight()) {
                dirKey.setText("Direction : Up Right")
            } else if (dir == joystick.stick_right()) {
                dirKey.setText("Direction : Right")
            } else if (dir == joystick.stick_downRight()) {
                dirKey.setText("Direction : Down Right")
            } else if (dir == joystick.stick_down()) {
                dirKey.setText("Direction : Down")
            } else if (dir == joystick.stick_downLeft()) {
                dirKey.setText("Direction : Down Left")
            } else if (dir == joystick.stick_left()) {
                dirKey.setText("Direction : Left")
            } else if (dir == joystick.stick_upLeft()) {
                dirKey.setText("Direction : Up Left")
            } else if (dir == joystick.stick_none()) {
                dirKey.setText("Direction : Center")
            }
            true
        })



    }
}