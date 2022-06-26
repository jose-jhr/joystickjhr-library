# joystickjhr-library
Joystick jhr Libreria usada para controlar dispositivos remotos como vehiculos robot o sistemas integrados moviles como videojuegos.
suscribete a mi canal para mas contenido https://www.youtube.com/ingenieriajhr
explicación del video: https://www.youtube.com/watch?v=I3LhEpoWWhI


 ```
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

 ```
 
 ```
 dependencies {
	        implementation 'com.github.jose-jhr:joystickjhr-library:0.1.0'
	}
 ```
 
![image](https://user-images.githubusercontent.com/66834393/173492533-d3c0f3e5-85bf-4b57-9890-2fd7709891af.png)
![image](https://user-images.githubusercontent.com/66834393/173492579-5f19d094-3cc4-48a6-902a-793ab19e6899.png)

Codigo de ejemplo:

activity_main.xml

```xml

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">



    <com.ingenieriajhr.joystickjhr.JoystickJhr
        android:id="@+id/joystick"
        android:layout_width="279dp"
        android:layout_height="273dp"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="20dp"
        app:alphaCircleFirst="200"
        app:alphaCircleSecond="255"
        app:colorFirst="#2979FF"
        app:colorSecond="#2979FF"
        app:radioCircle="0.5"
        app:textJoy="JHR"
        app:textSizeJoy="40"
        app:xtextOffset="-38"
        app:relleno="true"
        app:rellenoSize="12"
        app:colorText="#D5D8E7"
        app:ytextOffset="-15"
        />


    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/joystick"
        android:layout_marginTop="40dp"
        android:text="distancia X =  "
        android:id="@+id/distanciaX"
        >
    </TextView>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/distanciaY"
        android:layout_below="@id/distanciaX"
        android:text="distancia Y =  "
        >
    </TextView>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/distanciaY"
        android:id="@+id/angle"
        android:text="angle =  "
        >
    </TextView>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/angle"
        android:text="distancia"
        android:id="@+id/distancia"
        >
    </TextView>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Direccion"
        android:id="@+id/dirKey"
        android:layout_below="@id/distancia"
        ></TextView>




</RelativeLayout>

```

MainActivity.kt 

```kotlin

package com.ingenieriajhr.joystick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        joystick.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
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

```

