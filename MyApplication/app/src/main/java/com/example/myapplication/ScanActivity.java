package com.example.myapplication;

import static com.example.myapplication.MainActivity.CAMERA_PERMISSION_CODE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private TextView textView;
    private BarcodeDetector barcodeDetector;
    private Button camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        camera = findViewById(R.id.btncamera);
        camera.setOnClickListener(view -> checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE));


        surfaceView = findViewById(R.id.camera);
        textView = findViewById(R.id.text);

        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource= new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();

                surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED){
                            return;

                    }try {
                            cameraSource.start(surfaceHolder);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        }

                    @Override
                    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                    }

                    @Override
                    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

                    }
                });

                barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                        final SparseArray<Barcode> qrcode =detections.getDetectedItems();
                        if (qrcode.size() != 0){
                            textView.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(qrcode.valueAt(0) .displayValue);

                                }
                            });
                        }
                    }
                });
            }

    public void checkPermission (String permission, int requestCode){
        if (ContextCompat.checkSelfPermission(ScanActivity.this, permission)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(ScanActivity.this, new String[] {permission},
                    requestCode);
            camera.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==CAMERA_PERMISSION_CODE){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted",Toast.LENGTH_SHORT).show();
                camera.setVisibility(View.GONE);

            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
               // camera.setVisibility(View.GONE);
            }
        }
    }


}