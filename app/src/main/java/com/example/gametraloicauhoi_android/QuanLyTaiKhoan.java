package com.example.gametraloicauhoi_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;

public class QuanLyTaiKhoan extends AppCompatActivity {
    public static final String UPLOAD_KEY = "hinh_anh";
    public static final String UPLOAD_URL = "upload";
    final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);
        imageView = findViewById(R.id.imgAnhDaiDien);

    }
    public String encodeBitmapToString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodeimage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeimage;
    }
    public String getFileName(Uri uri){
        String result = null;
        if(uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try {
                if (cursor != null && cursor.moveToNext()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if ( result == null ) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut+1);
            }
        }
        return result;
    }
    public void uploadImage(View view){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(QuanLyTaiKhoan.this,"Uploading image","Please wait ...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                URL url = null;
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String hinh = jsonObject.getString("hinh_anh");
                    Log.d("HINHANH",hinh);
                    Picasso.get().load(hinh).into(imageView);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Bitmap... bitmaps) {
                Bitmap bitmap = bitmaps[0];
                String uploadImage = encodeBitmapToString(bitmap);
                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY,uploadImage);
                data.put("ten_hinh",getFileName(filePath));
                String result = NetworkUtils.postRequest(UPLOAD_URL,data);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void chooseImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),PICK_IMAGE_REQUEST);
    }
}
