 package com.example.myapplication;

 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.cardview.widget.CardView;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener {
     //vars
    public static final int CAMERA_PERMISSION_CODE =100;

     //widgets
     private Button camera;
     private Button generate;
     private Button scan;

     //cardview button
     private CardView gen;
     private CardView sc;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         camera = findViewById(R.id.camera);
         generate = findViewById(R.id.generate);
         scan = findViewById(R.id.scan);

         //initializing cardview button
         gen =(CardView) findViewById(R.id.btngen);
         sc = (CardView) findViewById(R.id.btnscan);

         gen.setOnClickListener( this);
         sc.setOnClickListener(this);


         scan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                 startActivity(intent);

             }
         });


       // camera.setOnClickListener(view -> checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE));
        generate.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GenerateCode.class);
            startActivity(intent);
        });


    }



//    public void checkPermission (String permission, int requestCode){
//        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
//        == PackageManager.PERMISSION_DENIED){
//            ActivityCompat.requestPermissions(MainActivity.this, new String[] {permission},
//                    requestCode);
//        }
//        else {
//            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
//        }
//    }

//     @Override
//     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//         if (requestCode ==CAMERA_PERMISSION_CODE){
//             if (grantResults.length > 0
//             && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                 Toast.makeText(this, "Permission Granted",Toast.LENGTH_SHORT).show();
//             }
//             else{
//                 Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//             }
//         }
//     }

     @Override
     public void onClick(View view) {
         Intent i;
         switch (view.getId()){

             case R.id.btngen:
                 i = new Intent(this,GenerateCode.class);
                 startActivity(i);
                 break;

             case R.id.btnscan:
                 i = new Intent(this,ScanActivity.class);
                 startActivity(i);
                 break;
         }

     }
 }