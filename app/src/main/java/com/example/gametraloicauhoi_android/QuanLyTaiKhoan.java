package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class QuanLyTaiKhoan extends AppCompatActivity {
    public static final String UPLOAD_KEY = "hinh_anh";
    public static final String UPLOAD_URL = "upload";
    private static final String MAIN_URL = "http://10.0.3.2:8000";
    final int PICK_IMAGE_REQUEST = 1;
    final int CAP_NHAT = 1;
    final int THONG_TIN = 2;
    private Uri filePath;
    private Bitmap bitmap;
    Context _context = this;
    String data = "";
    String encoded = null;
    private ImageView img;
    EditText matKhauCu, matKhauMoi, nhapLaiMatKhau, email;
    private NguoiChoiAsync nguoiChoiAsync;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);
        img = findViewById(R.id.imgHinhDaiDien);
        matKhauCu = findViewById(R.id.txtMatKhauCu);
        matKhauMoi = findViewById(R.id.txtMatKhauMoi);
        nhapLaiMatKhau = findViewById(R.id.txtXacNhanMatKhau);
        email = findViewById(R.id.txtEmail);
        nguoiChoiAsync = new NguoiChoiAsync(this,null,img);
        //getSupportLoaderManager().initLoader(THONG_TIN,null,nguoiChoiAsync.nguoiChoi);
        //getSupportLoaderManager().destroyLoader(THONG_TIN);
    }
    public String encodeBitmapToString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);
    }
//    public String getFileName(Uri uri){
//        String result = null;
//        if(uri.getScheme().equals("content")) {
//            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
//            try {
//                if (cursor != null && cursor.moveToNext()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//        if ( result == null ) {
//            result = uri.getPath();
//            int cut = result.lastIndexOf('/');
//            if (cut != -1) {
//                result = result.substring(cut+1);
//            }
//        }
//        return result;
//    }
//    public void uploadImage(View view){
//        class UploadImage extends AsyncTask<Bitmap,Void,String> {
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(QuanLyTaiKhoan.this,"Uploading image","Please wait ...",true,true);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                URL url = null;
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    String hinh = jsonObject.getString("hinh_anh");
//                    Log.d("HINHANH",hinh);
//                    Picasso.get().load(hinh).into(pg);
//                } catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(Bitmap... bitmaps) {
//                Bitmap bitmap = bitmaps[0];
//                String uploadImage = encodeBitmapToString(bitmap);
//                HashMap<String,String> data = new HashMap<>();
//                data.put(UPLOAD_KEY,uploadImage);
//                data.put("ten_hinh",getFileName(filePath));
//                String result = NetworkUtils.postRequest(UPLOAD_URL,data);
//                return result;
//            }
//        }
//        UploadImage ui = new UploadImage();
//        ui.execute(bitmap);
//    }

    private LoaderManager.LoaderCallbacks<String> capNhatThongtin =
            new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new CapNhatThongTinLoader(_context,data);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                boolean success = jsonObject.getBoolean("success");
                if(success)
                    Toast.makeText(_context, "Cập nhật thông tin thành công",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(_context, "Cập nhật thông tin thất bại",
                            Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            getSupportLoaderManager().destroyLoader(CAP_NHAT);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == PICK_IMAGE_REQUEST)
            {
                Uri selectedImage = data != null ? data.getData() : null;
                img.setImageURI(selectedImage);
                getSupportLoaderManager().destroyLoader(THONG_TIN);
                BitmapDrawable bitmap = (BitmapDrawable) img.getDrawable();
                try {
                    encoded = encodeBitmapToString(bitmap.getBitmap());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }

    public void chooseImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),PICK_IMAGE_REQUEST);
    }

    boolean kiemTra(String mk, String nlmk, String email){

        if(!email.contains("@")){
            Toast.makeText(_context, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mk.length() == 0 || nlmk.length() == 0){
            Toast.makeText(_context, "Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!nlmk.equals(mk)){
            Toast.makeText(_context, "Mật khẩu nhập lại không trùng nhau", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void capNhatThongTin(View view){
        String mkcu = matKhauCu.getText().toString();
        String mail = email.getText().toString();
        String mkMoi = matKhauMoi.getText().toString();
        String nlMK = nhapLaiMatKhau.getText().toString();
        BitmapDrawable bitmap = (BitmapDrawable) img.getDrawable();
        encoded = encodeBitmapToString(bitmap.getBitmap());
        if(kiemTra(mkMoi,nlMK,mail)){
            data ="id="+URLEncoder.encode(String.valueOf(ManHinhChinh.ID))+
                    "&ten_dang_nhap="+URLEncoder.encode(ManHinhChinh.tenNguoiDung)+"&image="+URLEncoder.encode(encoded)+"&email="+URLEncoder.encode(mail)+"&mat_khau="+URLEncoder.encode(mkcu)+"&mat_khau_moi="+URLEncoder.encode(mkMoi);
            getSupportLoaderManager().initLoader(CAP_NHAT,null,capNhatThongtin);
        }
    }

}
class CapNhatThongTinLoader extends AsyncTaskLoader<String>{

    private String data;
    public CapNhatThongTinLoader(@NonNull Context context, String data) {
        super(context);
        this.data = data;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONPostData("cap-nhat-thong-tin",data,NguoiChoi.token);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}