package com.glaserproject.vb_mappdocumentcreator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPdf();
            }
        });

    }

    private void createPdf() {

        PdfDocument document = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();

        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        paint.setColor(Color.RED);
        canvas.drawCircle(50,50,30, paint);

        paint.setColor(Color.BLACK);
        canvas.drawText("hovno", 80,50, paint);

        document.finishPage(page);


        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(100, 100, 50, paint);
        document.finishPage(page);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("hovno", "permission not granted - asking for it");


            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        123);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Log.e("hovno", "permission already granted");
            // Permission has already been granted
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.e("hovno", "mounted");
        } else {
            Log.e("hovno", "not mounted");
        }

        File absolutePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);;

        File folder = new File(absolutePath + "/test/");





        if (!folder.isFile()) {
            if (folder.mkdirs()){
                Log.e("TAG","MKDIRS: true");
            }
        }


        String fileName = "/test.pdf";

        String targetPdf = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test/" + fileName;
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "DONE", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            Log.e("hovno", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }



        document.close();




    }
}
