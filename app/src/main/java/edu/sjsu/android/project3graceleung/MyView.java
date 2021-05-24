package edu.sjsu.android.project3graceleung;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

public class MyView extends View implements SensorEventListener {

    // You can change the ball size if you want
    private static final int BALL_SIZE = 150;
    private Bitmap field;
    private Bitmap ball;
    private float XOrigin;
    private float YOrigin;
    private float horizontalBound;
    private float verticalBound;
    private final Particle mBall = new Particle();


    // Paint object is used to draw your name
    private Paint paint = new Paint();

    private SensorManager manager;
    private Sensor acc;

    private Display rotation;

    private float x;
    private float y;
    private float z;
    private long timestamp;

    private static final String TAG = "MyView";


    public MyView(Context context) {
        super(context);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ball = Bitmap.createScaledBitmap(b, BALL_SIZE, BALL_SIZE, true);
        field = BitmapFactory.decodeResource(getResources(), R.drawable.field);

        manager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        if (manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            acc = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        else
        {
            Log.d(TAG, "Missing Accelerometer");
        }

        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        rotation = mWindowManager.getDefaultDisplay();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        XOrigin = w/2;
        YOrigin = h/2;
        horizontalBound = (w - BALL_SIZE) / 2;
        verticalBound = (h - BALL_SIZE) / 2;

        field = Bitmap.createScaledBitmap(field, w, h, true);

    }

    // TODO: set XOrigin, YOrigin, HorizontalBound and VerticalBound based on the screen size
    // HINT: there is a method in View class related to the size of the screen.
    //      The method's parameters include width and height of the screen.
    //      So override that method to set the 4 attributes.
    //      Also, you should createScaledBitmap for the basketball field,
    //      use the width and height of the screen in this method.

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the field and the ball

        mBall.updatePosition(x, y, z, timestamp);
        mBall.resolveCollisionWithBounds(horizontalBound, verticalBound);

        canvas.drawBitmap(field, 0, 0, null);
        canvas.drawBitmap(ball,
                (XOrigin - BALL_SIZE / 2) + mBall.mPosX,
                (YOrigin - BALL_SIZE / 2) - mBall.mPosY, null);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(100);

        canvas.drawText("Grace Leung", XOrigin - 250, YOrigin + 500, paint);

        invalidate();
    }

    public void startSimulation() {
        if (acc != null)
        {
            manager.registerListener(this, acc, SensorManager.SENSOR_DELAY_UI);
        }

    }

    public void stopSimulation() {
        manager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (rotation.getRotation() == Surface.ROTATION_0) {
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];
            } else if (rotation.getRotation() == Surface.ROTATION_90) {
                x = -sensorEvent.values[1];
                y = sensorEvent.values[0];
                z = sensorEvent.values[2];
            } else if (rotation.getRotation() == Surface.ROTATION_180) {
                x = -sensorEvent.values[0];
                y = -sensorEvent.values[1];
                z = sensorEvent.values[2];
            } else if (rotation.getRotation() == Surface.ROTATION_270) {
                x = sensorEvent.values[1];
                y = -sensorEvent.values[0];
                z = sensorEvent.values[2];
            }
            timestamp = sensorEvent.timestamp;
        }
        else
        {
            return;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}