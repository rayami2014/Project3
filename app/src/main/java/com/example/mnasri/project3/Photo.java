package com.example.mnasri.project3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayDeque;
import java.util.Arrays;

import static android.app.PendingIntent.getActivity;

/**
 * Created by M.NASRI on 10/22/2018.
 */
public class Photo {

    String path;
    Bitmap bitmap;
    int width , hieght;
    Mat pixels;
    Photo(Bitmap bitmap){
        this.bitmap=bitmap;
        width=bitmap.getWidth();
        hieght=bitmap.getHeight();
        pixels = new Mat(hieght , width);
    }
    Photo(String path){
        this.path=path;
        //bitmap = BitmapFactory.decodeFile(path);
        //imageView = (ImageView) getActivity().findViewById(R.id.imageView);
        //imageView.setImageBitmap(BitmapFactory.decodeFile(path));
    }
    public Mat getGrayImage(){

        int pixel , co;
        //Bitmap gray = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), bitmap.getConfig());
        Mat gray=new Mat(hieght , width);
        for(int i=0;i<hieght;i++){
            for(int j=0;j<width;j++){
                pixel=bitmap.getPixel(j , i);
                co= Color.red(pixel)+Color.blue(pixel)+Color.green(pixel);
                co/=3;
                pixels.mat[i][j] = co;
                gray.mat[i][j]=co;
                //gray.setPixel(j , i , Color.argb(Color.alpha(pixel) ,co , co , co));
            }
        }
        return gray;
    }

    public float gaussienFun(int x , int y , float seg){
        float res;
        float r=((x*x)+(y*y))/(2*seg*seg);
        res=(float)((Math.exp(-r))/((2*Math.PI*seg*seg)));
        return res;

    }
    public Mat gaussienBlur(int h , int w , float seg) {
        float g[][] = new float[h][w], res ;
        Mat im=new Mat(hieght , width);
        int pixel,i,j,k,k1;
        float sum =0 ;
        for (k = 0; k < h ; k++) {
            for (k1 = 0; k1 < w ; k1++) {
                g[k][k1] = gaussienFun(Math.abs(k-h/2),Math.abs(k1-w/2),seg);
                sum += g[k][k1];
            }
        }
        for (k = 0; k < h ; k++) {
            for (k1 = 0; k1 < w ; k1++) {
                g[k][k1]/=sum;
            }
        }
        for (i = 0; i < hieght; i++) {
            for (j = 0; j < width; j++) {
                res = 0;
                for (k = Math.max(i - (h / 2), 0); k <= Math.min(i + (h / 2),hieght-1); k++) {
                    for (k1 = Math.max(j - (w / 2),0); k1 <= Math.min(j + (w / 2), width - 1); k1++) {
                        res += ((g[k-i + h/2][k1-j+w/2]) * pixels.mat[k][k1]);

                    }
                }
                im.mat[i][j]=res;
            }
        }

        return im;

    }
    public Mat edgeDetiction(int n ,int h , int w ,float seg){
        Mat []im=new Mat[n];
        Mat res=new Mat(hieght , width);
        this.pixels=this.getGrayImage();
        for(int i=0;i<n;i++){
            im[i]=this.gaussienBlur(h ,w ,seg);
            //System.out.println(im[i]);
            this.pixels=im[i];
            if(i>0){
                res=im[i-1].sub(im[i]);
            }

        }
        //System.out.println("_______________________");
        float r;
        for(int i1=0;i1<hieght;i1++){
            for(int j=0;j<width;j++){
                //if(res.mat[i1][j]<0){
                    //res.mat[i1][j]*=-1;//Math.abs(res.mat[i1][j]);
                    r=res.mat[i1][j]+(128);
                    r/=256;
                    r*=255;
                    res.mat[i1][j]=r;
                    System.out.println(res.mat[i1][j]);

                //}

                //System.out.println(res.mat[i1][j]);
            }
        }
        return res;
    }
}
