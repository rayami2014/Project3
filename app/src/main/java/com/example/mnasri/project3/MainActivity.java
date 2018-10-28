package com.example.mnasri.project3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.graphics.Color;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
        bitmap = Bitmap.createScaledBitmap(bitmap,50,50,true);

        Photo ph=new Photo(bitmap);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        //Bitmap g = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), bitmap.getConfig());
        Mat m=ph.edgeDetiction( 5 , 3 , 3 , 15.8f);
        //bitmap=ph.getGrayImage();
        //bitmap=ph.gaussienBlur(3 , 3 , 0.84089642f);


        //Mat m1=ph.getGrayImage();
        //Mat m=ph.gaussienBlur(3,3, 0.84089642f);
        int pixel ,l;
        for(int i=0;i<ph.hieght;i++){
            for(int j=0;j<ph.width;j++){
                pixel=bitmap.getPixel(j , i);
               // System.out.println(m.mat[i][j]+" "+m1.mat[i][j]);
                bitmap.setPixel(j , i , Color.argb(Color.alpha(pixel) ,(int)m.mat[i][j] , (int)m.mat[i][j] ,(int) m.mat[i][j]));
            }
        }
        bitmap = Bitmap.createScaledBitmap(bitmap,400,400,true);

        imageView.setImageBitmap(bitmap);
    }


    //android:src="@drawable/pic"

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

        // long time = System.nanoTime();
        //ph.gaussienBlur(3 , 3  , 0.84089642f);
        //time = System.nanoTime() - time;
        //System.out.println("time = "+time);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
