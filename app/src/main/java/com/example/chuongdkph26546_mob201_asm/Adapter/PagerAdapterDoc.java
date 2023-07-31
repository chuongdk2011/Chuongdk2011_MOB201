package com.example.chuongdkph26546_mob201_asm.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chuongdkph26546_mob201_asm.Fragment.FragTheLoai.FragDoiSong;
import com.example.chuongdkph26546_mob201_asm.Fragment.FragTheLoai.FragHaiHuoc;
import com.example.chuongdkph26546_mob201_asm.Fragment.FragTheLoai.FragSucKhoe;
import com.example.chuongdkph26546_mob201_asm.Fragment.FragTheLoai.FragTheGioi;
import com.example.chuongdkph26546_mob201_asm.Fragment.FragTheLoai.FragThoiSu;
import com.example.chuongdkph26546_mob201_asm.Fragment.FragTheLoai.FragTinNoiBat;
import com.example.chuongdkph26546_mob201_asm.Fragment.FragTheLoai.FragTinXemNhieu;

public class PagerAdapterDoc extends FragmentStateAdapter {

    int soluongPage = 7;
    public PagerAdapterDoc(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return  new FragTinNoiBat();
            case 1:
                return new FragTheGioi();
            case 2:
                return new FragThoiSu();
            case 3:
                return new FragSucKhoe();
            case 4:
                return new FragDoiSong();
            case 5:
                return new FragTinXemNhieu();
            case 6:
                return new FragHaiHuoc();
            default:
                return new FragTinNoiBat();
        }

    }

    @Override
    public int getItemCount() {
        return soluongPage;
    }
}
