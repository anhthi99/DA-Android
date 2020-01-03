package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class QuenMatKhau extends AppCompatActivity {

    final int LAY_MK = 1;
    Context context = this;
    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        ed = findViewById(R.id.txtUserQMK);
    }

    private LoaderManager.LoaderCallbacks<String> guiMail = new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new GuiMailLoader(context,ed.getText().toString());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                boolean s = jsonObject.getBoolean("success");
                String message = jsonObject.getString("message");
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    };

    public void guiMail(View view) {
        getSupportLoaderManager().initLoader(LAY_MK,null,guiMail);
    }

    public void quayVe(View view) {
        finish();
    }
}

class GuiMailLoader extends AsyncTaskLoader<String>{

    String email;
    public GuiMailLoader(@NonNull Context context, String email) {
        super(context);
        this.email = email;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONData("guimail/"+email,"GET",null);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
