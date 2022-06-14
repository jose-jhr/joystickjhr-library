package com.ingenieriajhr.joystickjhr;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JoystickJhr extends View {

    private int colorFirst;
    private int colorSecond;
    private int alphaCircleFirst = 255;
    private int alphaCircleSecond = 255;
    private boolean displayShapeName;
    private Paint paintShape;
    private int anchoView = 20;
    private int altoView = 20;
    int radio;
    private Float radioDiv = 0.4f;
    private float ejeX = 0;
    private float ejeY = 0;
    private float angle = 0;
    private float posXjoy = 0;
    private float posYjoy = 0;
    private float dx;
    private float dy;
    private float angle1;
    private float c;
    private String textJoy = "";
    private float xtextOffset = 0;
    private float ytextOffset = 0;
    private float textSizeJoy = 30;
    private boolean relleno = true;
    private float rellenoSize = 5f;
    private int colorText = Color.BLACK;


    public static final int STICK_NONE = 0;
    public static final int STICK_UP = 1;
    public static final int STICK_UPRIGHT = 2;
    public static final int STICK_RIGHT = 3;
    public static final int STICK_DOWNRIGHT = 4;
    public static final int STICK_DOWN = 5;
    public static final int STICK_DOWNLEFT = 6;
    public static final int STICK_LEFT = 7;
    public static final int STICK_UPLEFT = 8;

    Vibrator vibrator;


    public JoystickJhr(Context context) {
        super(context);
        vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
    }


    public JoystickJhr(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        // Obtain a typed array of attributes
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attributeSet
                , R.styleable.CustomJoystickStyle, 0, 0);
        // Extract custom attributes into member variables
        try {
            colorFirst = a.getColor(R.styleable.CustomJoystickStyle_colorFirst, Color.BLACK);
            colorSecond = a.getColor(R.styleable.CustomJoystickStyle_colorSecond,Color.BLUE);
            radioDiv = a.getFloat(R.styleable.CustomJoystickStyle_radioCircle,0.4f);
            alphaCircleFirst = a.getInteger(R.styleable.CustomJoystickStyle_alphaCircleFirst,alphaCircleFirst);
            alphaCircleSecond = a.getInteger(R.styleable.CustomJoystickStyle_alphaCircleSecond,alphaCircleSecond);
            textJoy = a.getString(R.styleable.CustomJoystickStyle_textJoy);
            textSizeJoy = a.getFloat(R.styleable.CustomJoystickStyle_textSizeJoy,textSizeJoy);

            xtextOffset = a.getFloat(R.styleable.CustomJoystickStyle_xtextOffset,xtextOffset);
            ytextOffset = a.getFloat(R.styleable.CustomJoystickStyle_ytextOffset,ytextOffset);
            relleno = a.getBoolean(R.styleable.CustomJoystickStyle_relleno,relleno);
            rellenoSize = a.getFloat(R.styleable.CustomJoystickStyle_rellenoSize,rellenoSize);
            colorText = a.getColor(R.styleable.CustomJoystickStyle_colorText,Color.BLACK);

        } finally {
            // Los objetos TypedArray se comparten y deben reciclarse.
            a.recycle();
        }

        setupPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        anchoView = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        altoView = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        if (anchoView>altoView){ radio = altoView/2;}
        else {radio = anchoView/2;}

        radio = radio-20;



        posXjoy = anchoView/2;
        posYjoy = altoView/2;

       // altoView = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCirclefirst(canvas);
        drawCircleSecond(canvas);
        drawTextJoy(canvas);

    }

    private void drawTextJoy(Canvas canvas) {
        paintShape.setStyle(Paint.Style.FILL);
        paintShape.setColor(colorText);
        paintShape.setTextSize(textSizeJoy);
        if (textJoy!=null)canvas.drawText(textJoy,posXjoy+xtextOffset,posYjoy-ytextOffset,paintShape);
    }

    private void drawCircleSecond(Canvas canvas) {
        paintShape.setStyle(Paint.Style.FILL);
        paintShape.setColor(colorFirst);
        paintShape.setAlpha(alphaCircleFirst);
        canvas.drawCircle(posXjoy,posYjoy,radio*radioDiv,paintShape);
    }

    private void drawCirclefirst(Canvas canvas) {
        if (relleno ==  true){
            paintShape.setStyle(Paint.Style.STROKE);
            paintShape.setStrokeWidth(rellenoSize);
        }else{
            paintShape.setStyle(Paint.Style.FILL);
        }
        paintShape.setColor(colorSecond);
        paintShape.setAlpha(alphaCircleSecond);
        canvas.drawCircle(anchoView/2,altoView/2,radio,paintShape);
    }

    public void move (MotionEvent event){
        int action = event.getAction();

        ejeX = event.getX();
        ejeY = event.getY();


        if (action == MotionEvent.ACTION_MOVE){
            posYjoy = ejeY;
            posXjoy = ejeX;
            CalculateValues(ejeX,ejeY);


        }
        if (action == MotionEvent.ACTION_UP){
            posXjoy = anchoView/2;
            posYjoy = altoView/2;
        }
        postInvalidate();

    }


    /**variables de retorno
     * joyX
     * joyY
     * angle
     */
    public float joyX(){
        return posXjoy-anchoView/2;
    }

    public float joyY(){
        return altoView/2-posYjoy;
    }

    public float angle(){
        if (posXjoy-anchoView/2==0 && altoView/2-posYjoy == 0){
            return 0;
        }else{
            angle = (float)  calAngle(posXjoy-anchoView/2,altoView/2-posYjoy);
            return angle ;
        }

    }

    public float distancia(){
        return distHipotenusa(posYjoy-altoView/2,posXjoy-anchoView/2);
    }

    private float distHipotenusa(float catOpuesto,float catAdyacente) {
        return (float) Math.sqrt((Math.pow(catOpuesto,2)+Math.pow(catAdyacente,2)));
    }

    public void vibNow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(1);
        }
    }


    private void CalculateValues(float x_touch, float y_touch) {
        dx = x_touch-anchoView/2;
        dy = y_touch-altoView/2;


        angle1 = (float) Math.atan(Math.abs(dy/dx));
        //angle2 = Convierte_angulo((float)Math.toDegrees(Math.atan((dy/dx))),dx,dy);

        c = (float) Math.sqrt(dx*dx+dy*dy);

        if (c>radio){
            if(dx>0 && dy>0){//Botton Right
                //System.out.println("Abajo Derecha");
                posXjoy = (float) (anchoView/2+(radio*Math.cos(angle1)));
                posYjoy = (float) (altoView/2+(radio*Math.sin(angle1)));
            }else if (dx>0 && dy<0){//top Rigth
                //System.out.println("Arriba Derecha");
                posXjoy = (float) (anchoView/2+(radio*Math.cos(angle1)));
                posYjoy = (float) (altoView/2-(radio*Math.sin(angle1)));
            }else if (dx<0 && dy<0){//top left
                //System.out.println("Arriba Izquierda");
                posXjoy = (float) (anchoView/2-(radio*Math.cos(angle1)));
                posYjoy = (float) (altoView/2-(radio*Math.sin(angle1)));
            }else if (dx<0 && dy>0){//bottom left
                //System.out.println("Abajo Izquierda");
                posXjoy = (float) (anchoView/2-(radio*Math.cos(angle1)));
                posYjoy = (float) (altoView/2+(radio*Math.sin(angle1)));
            }


        }else{
            posXjoy = anchoView/2+dx;
            posYjoy = altoView/2+dy;
        }

    }

    public int getDireccion() {
        if(distancia()> 0) {
            if(angle >= 247.5 && angle < 292.5 ) {
                return STICK_DOWN;
            } else if(angle >= 292.5 && angle < 337.5 ) {
                return STICK_DOWNRIGHT;
            } else if(angle >= 337.5 || angle < 22.5 ) {
                return  STICK_RIGHT;
            } else if(angle >= 22.5 && angle < 67.5 ) {
                return  STICK_UPRIGHT;
            } else if(angle >= 67.5 && angle < 112.5 ) {
                return  STICK_UP;
            } else if(angle >= 112.5 && angle < 157.5 ) {
                return STICK_UPLEFT;
            } else if(angle >= 157.5 && angle < 202.5 ) {
                return  STICK_LEFT;
            } else if(angle >= 202.5 && angle < 247.5 ) {
                return  STICK_DOWNLEFT;
            }
        } else {
            return STICK_NONE;
        }
        return 0;
    }

    private double calAngle(float x, float y){
        if(x >= 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x));
        else if(x < 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x < 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x >= 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 360;
        return 0;




    }




    private void setupPaint() {
        paintShape = new Paint();
        paintShape.setStyle(Paint.Style.FILL);
        paintShape.setColor(colorFirst);
        paintShape.setTextSize(30);
    }

    public int getColorFirst() {
        return colorFirst;
    }
    public void setColorFirst(int color) {
        this.colorFirst = color;
        invalidate();
        requestLayout();
    }
    public void setColorSecond(int color) {
        this.colorSecond = color;
        invalidate();
        requestLayout();
    }
    public void setRadioDiv(float radioDiv) {
        this.radioDiv = radioDiv;
        invalidate();
        requestLayout();
    }
    public void setAlphaCircleFirst(int alphaCircleFirst){
        this.alphaCircleFirst = alphaCircleFirst;
        invalidate();
        requestLayout();
    }
    public void setAlphaCircleSecond(int alphaCircleSecond){
        this.alphaCircleSecond = alphaCircleSecond;
        invalidate();
        requestLayout();
    }
    public void setEjeY(String textJoy){
        this.textJoy = textJoy;
        invalidate();
        requestLayout();
    }

    public int stick_none(){return  STICK_NONE;}
    public int stick_up(){return  STICK_UP;}
    public int stick_upRight(){return  STICK_UPRIGHT;}
    public int stick_right(){return  STICK_RIGHT;}
    public int stick_downRight(){return  STICK_DOWNRIGHT;}
    public int stick_down(){return  STICK_DOWN;}
    public int stick_downLeft(){return  STICK_DOWNLEFT;}
    public int stick_left(){return  STICK_LEFT;}
    public int stick_upLeft(){return  STICK_UPLEFT;}



}
