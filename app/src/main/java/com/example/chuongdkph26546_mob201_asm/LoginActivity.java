package com.example.chuongdkph26546_mob201_asm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chuongdkph26546_mob201_asm.DAO.UserDAO;

public class LoginActivity extends AppCompatActivity {

    TextView tv_dk;
    Button btn_dn;
    EditText ed_tk, ed_mk;
    ImageView img_back;

    PrefUtil prefUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefUtil = PrefUtil.get(this);
        tv_dk = findViewById(R.id.tv_dk);
        btn_dn = findViewById(R.id.btn_dn);
        ed_tk = findViewById(R.id.ed_tk);
        ed_mk = findViewById(R.id.ed_mk);
        UserDAO dao = new UserDAO(this);
        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_mk.getText().toString().equals("") || ed_tk.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Không để trống", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkuserpass = dao.checkuserpass(ed_tk.getText().toString(), ed_mk.getText().toString());
                    if (checkuserpass == true) {
                        Toast.makeText(LoginActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();

                        prefUtil.setSigning(true);
                        onBackPressed();
                    } else {
                        Toast.makeText(LoginActivity.this, "Thông Tin Không Hợp Lệ!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tv_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        img_back = findViewById(R.id.img_backdn);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}