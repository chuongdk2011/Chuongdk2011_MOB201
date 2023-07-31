package com.example.chuongdkph26546_mob201_asm.Fragment.FragTheLoai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chuongdkph26546_mob201_asm.MainActivity;
import com.example.chuongdkph26546_mob201_asm.R;
import com.example.chuongdkph26546_mob201_asm.Rss.DownloadTintuc;

public class FragTinXemNhieu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tin_xem_nhieu,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String urlRss = "https://vnexpress.net/rss/tin-xem-nhieu.rss";

        DownloadTintuc downloadTinTuc = new DownloadTintuc((MainActivity) getActivity());
        downloadTinTuc.execute(urlRss);
    }
}
