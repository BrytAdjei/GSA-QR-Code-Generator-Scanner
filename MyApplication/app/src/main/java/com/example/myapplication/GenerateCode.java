package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class GenerateCode extends AppCompatActivity {
    public final static int QRCodeWidth = 500;
    Bitmap bitmap;
   private EditText text;
   private Button download;
   private Button generate;
   private ImageView iv;
    private ImageView imageViewBitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        text = findViewById(R.id.text);
        download =findViewById(R.id.download);
        download.setVisibility(View.INVISIBLE);
        generate = findViewById(R.id.generate);
        iv = findViewById(R.id.image);
generate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (text.getText().toString().trim().length()==0){
            Toast.makeText(GenerateCode.this, "Enter Text", Toast.LENGTH_SHORT).show();

        }
        else {
            try {
                bitmap = textToImageEncode(text.getText().toString());
                iv.setImageBitmap(bitmap);
                generate.setVisibility(view.GONE);
                download.setVisibility(View.VISIBLE);
                text.setVisibility(view.GONE);
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"code_scanner", null);
                        Toast.makeText(GenerateCode.this, "Saved to Gallery", Toast.LENGTH_SHORT).show();
                    }
                });

            }catch (WriterException e){
                e.printStackTrace();
            }
        }
    }
});
    }
    private Bitmap textToImageEncode(String value) throws WriterException{
        BitMatrix bitMatrix;
        try{
            bitMatrix = new MultiFormatWriter().encode(value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth, null);

        }catch (IllegalArgumentException e){
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y =0; y<bitMatrixHeight; y++){
            int offset = y*bitMatrixWidth;
            for(int x = 0;x<bitMatrixWidth; x++){
                pixels[offset +x] =bitMatrix.get(x, y)?
                        getResources().getColor(R.color.black): getResources().getColor(R.color.white);

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);

        return bitmap;



    }
}