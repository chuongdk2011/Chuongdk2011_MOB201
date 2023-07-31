package com.example.chuongdkph26546_mob201_asm.Rss;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;


import com.example.chuongdkph26546_mob201_asm.Adapter.TinTucAdapter;
import com.example.chuongdkph26546_mob201_asm.DTO.TinTucDTO;
import com.example.chuongdkph26546_mob201_asm.MainActivity;
import com.example.chuongdkph26546_mob201_asm.R;
import com.example.chuongdkph26546_mob201_asm.WebviewActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadTintuc extends AsyncTask<String,Void, List<TinTucDTO> > {

        MainActivity activity = null;
        public  DownloadTintuc(MainActivity activity){
            this.activity = activity;
        }
        TinTucLoader loader = new TinTucLoader();
        List<TinTucDTO> list = new ArrayList<TinTucDTO>();
        @Override
        protected List<TinTucDTO> doInBackground(String... strings) {



            // tạo url Connection để tải RSS
            String urlRss = strings[0];

            try {
                URL url = new URL(urlRss);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                if(urlConnection.getResponseCode() ==200){
                    // kết nối thành công thì mới lấy luồng dữ liệu
                    InputStream inputStream = urlConnection.getInputStream();
                    list = loader.getTinTucList( inputStream );

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return list;
        }

        @Override
        protected void onPostExecute(List<TinTucDTO> tinTucs) {
            super.onPostExecute(tinTucs);

            Log.d("zzzzz", "onPostExecute: số lượng bài viết = " + tinTucs.size());


            try {
                ListView lv_tnb = (ListView) activity.findViewById(R.id.lv_tnb);


                TinTucAdapter adapter = new TinTucAdapter(tinTucs, activity.getBaseContext(), new TinTucAdapter.onclicktintuc() {
                    @Override
                    public void hihi(TinTucDTO tinTucDTO) {
                        Intent intent = new Intent(activity.getApplicationContext(), WebviewActivity.class);

                        intent.putExtra("link",tinTucDTO.getLink());

                        activity.startActivity(intent);

                    }
                });
                if (adapter!=null){
                    lv_tnb.setAdapter(adapter);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }




        }


}
