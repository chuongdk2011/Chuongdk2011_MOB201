package com.example.chuongdkph26546_mob201_asm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chuongdkph26546_mob201_asm.DAO.UserDAO;
import com.example.chuongdkph26546_mob201_asm.DTO.UserDTO;

public class SignupActivity extends AppCompatActivity {

    Button btn_dk;
    EditText ed_dktk,ed_dkmk,ed_nlmk;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        btn_dk = findViewById(R.id.btn_dk);
        ed_dktk = findViewById(R.id.ed_dktk);
        ed_dkmk= findViewById(R.id.ed_dkmk);
        ed_nlmk = findViewById(R.id.ed_nlmk);

        UserDAO userDAO = new UserDAO(this);
        btn_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDTO dto = new UserDTO();

                String tk = ed_dktk.getText().toString();
                String mk = ed_dkmk.getText().toString();
                String nlmk = ed_nlmk.getText().toString();


                if (tk.equals("")||mk.equals("")||nlmk.equals("")){

                    Toast.makeText(SignupActivity.this, "Không Để Trống", Toast.LENGTH_SHORT).show();
                }else{

                    if (mk.equals(nlmk)){
                        Boolean checkuser = userDAO.checkuser(tk);
                        if (checkuser==false){

                            dto.setUsername(tk);
                            dto.setPassword(mk);

                            userDAO.open();
                            long kq = userDAO.insert(dto);
                            if (kq > 0) {
                                Toast.makeText(getBaseContext(), "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                Toast.makeText(getBaseContext(), "Đăng Ký Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignupActivity.this, "Tên tài khoản đã tồn tại! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignupActivity.this, "Mật Khẩu Không Trùng Khớp", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });
        img_back = findViewById(R.id.img_backdk);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}