package com.example.gametraloicauhoi_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TVTCDialogFragment extends DialogFragment {

    TextView t1,t2,t3;
    Button btn1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tu_van_tai_cho,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        t1 = view.findViewById(R.id.txtTV1);
        t2 = view.findViewById(R.id.txtTV2);
        t3 = view.findViewById(R.id.txtTV3);
        btn1 = view.findViewById(R.id.btnThanks);
        Bundle data = getArguments();
        int da = data != null ? data.getInt("dap_an") : 0;
        t1.setText(t1.getText()+" tôi chọn "+ chonDA(da));
        t2.setText(t2.getText()+" tôi chọn "+ chonDA(da));
        t3.setText(t3.getText()+" tôi chọn "+ chonDA(da));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    String chonDA(int i){
       String [] da = new String[]{"A","B","C","D"};
       return da[i];
    }
}
