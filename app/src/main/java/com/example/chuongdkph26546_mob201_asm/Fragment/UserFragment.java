package com.example.chuongdkph26546_mob201_asm.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chuongdkph26546_mob201_asm.CustomItem;
import com.example.chuongdkph26546_mob201_asm.ListfvtMusic;
import com.example.chuongdkph26546_mob201_asm.LoginActivity;
import com.example.chuongdkph26546_mob201_asm.PrefUtil;
import com.example.chuongdkph26546_mob201_asm.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserFragment extends Fragment {


    View view;

    public static UserFragment newInstance() {

        UserFragment fragment = new UserFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_frag, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isSigning = PrefUtil.get(getContext()).isSigning();
        EventBus.getDefault().post(new CustomItem("updateViews"));
        Button dangNhap = view.findViewById(R.id.btn_login);

        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSigning){
                    PrefUtil.get(getContext()).setSigning(false);
                    getActivity().finish();
                    EventBus.getDefault().post(new CustomItem("updateViews"));
                }else{
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    EventBus.getDefault().post(new CustomItem("updateViews"));
                }
            }
        });
        Log.i("chuongdk===", "onCreateView: " + isSigning);
        dangNhap.setText(isSigning ? R.string.logout : R.string.login);



        Button nhacYT = view.findViewById(R.id.btn_music);
        nhacYT.setEnabled(false);
        if (isSigning){
            nhacYT.setEnabled(true);

            nhacYT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ListfvtMusic.class);
                    startActivity(intent);
                }
            });
        }

        view.findViewById(R.id.btn_avt).setOnClickListener(v -> {
            if (isSigning){
                try {
                    Intent i = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 999);
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
            }else {
                Toast.makeText(getContext(), "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
            }

        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView imageView = view.findViewById(R.id.img);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }


        }
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CustomItem customItem) {

    }
    
}
