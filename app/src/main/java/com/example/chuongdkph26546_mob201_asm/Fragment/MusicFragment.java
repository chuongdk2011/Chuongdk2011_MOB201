package com.example.chuongdkph26546_mob201_asm.Fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.chuongdkph26546_mob201_asm.DAO.UserDAO;
import com.example.chuongdkph26546_mob201_asm.DTO.UserDTO;
import com.example.chuongdkph26546_mob201_asm.PrefUtil;
import com.example.chuongdkph26546_mob201_asm.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicFragment extends Fragment {

    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();

        return fragment;
    }
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 100;

    ProgressBar musicBar;
    Button btnPlayStop,btnPause;
    TextView tvCurrentTime;
    Button btn1,btn2;

    MediaPlayer myMusicPlayer; // không đặt tên mediaPlayer vì có thể trùng biến ở một số hàm khác


    final String mp3 = "https://www.w3schools.com/html/horse.mp3";
    Uri uri = Uri.parse(mp3);
    View view;
    boolean isSigning = PrefUtil.get(getContext()).isSigning();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view  = inflater.inflate(R.layout.music_frag,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        musicBar = view.findViewById(R.id.musicBar);
        btnPlayStop = view.findViewById(R.id.btnPlayStop);
        tvCurrentTime = view.findViewById(R.id.currentTime);
        btnPause = view.findViewById(R.id.btnPause);
        btnPause.setEnabled(false);
        initPlayList();



        btnPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayOrStopMusic();
            }
        });



        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PauseMusic();
            }
        });
        checkAndRequestStoragePermission();
    }
    private void checkAndRequestStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền truy cập vào kho âm nhạc nếu chưa được cấp
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            // Quyền truy cập đã được cấp, thực hiện các hành động cần thiết
            // Ví dụ: load danh sách nhạc
            loadMusicList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền truy cập được cấp, thực hiện các hành động cần thiết
                // Ví dụ: load danh sách nhạc
                loadMusicList();
            } else {
                // Quyền truy cập bị từ chối, hiển thị thông báo hoặc thực hiện các xử lý phù hợp khác
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadMusicList() {
        // Thực hiện tải danh sách nhạc từ kho âm nhạc của người dùng
        getSongList();
    }
    public void startOrStop(View view) {
        // sự kiện bấm vào nút play

    }

    public void pauseMusic(View view) {
        // sự kiện bấm vào nút pause
        PauseMusic();
    }



    /**
     * hàm định dạng lại thời gian hiển thị
     * @param duration
     * @return
     */
    private String formatDuration(long duration) {
        long minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
        long seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES);

        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * runnable là dùng để tạo ra 1 tiến trình riêng chạy nhạc, cái này thay cho việc tạo service.
     */
    Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (myMusicPlayer != null    ){

                int totalTime = myMusicPlayer.getDuration();
                int currentTime = myMusicPlayer.getCurrentPosition();
                musicBar.setMax(totalTime);
                musicBar.setProgress(currentTime);
                handler.postDelayed(this, 100);

                Log.e("zzzzz","Đang chạy... " + currentTime);

                tvCurrentTime.setText(formatDuration(currentTime));
            }
        }
    };


    // xử lý tạm dừng. Lưu ý myMusicPlayer phải được khai báo ở phạm vi class thì mới điều khiển khắp nơi được
    void PauseMusic(){
        if (myMusicPlayer != null) {
            myMusicPlayer.pause();
            btnPlayStop.setText("Continue");
            btnPause.setEnabled(false);
        }
    }

    //xử lý chạy nhạc, dựa vào biến uri khởi tạo ở trên.
    // sau này có play list thì khi bấm vào playlist sẽ chỉ cần gán lại uri và gọi hàm play này là ok
    void PlayOrStopMusic(){
        // bấm 1 lần thì chạy sau đó chuyển nút bấm về stop
        // bấm lại thì stop

        if (myMusicPlayer == null) {
            // chưa chạy
            myMusicPlayer = MediaPlayer.create(getContext(), uri);

            int s = myMusicPlayer.getDuration();

            TextView tvTotal = view.findViewById(R.id.totalTime);

            tvTotal.setText(formatDuration(s));
            myMusicPlayer.start();

            runnable.run();
            btnPlayStop.setText("Stop");
            btnPause.setEnabled(true);

            myMusicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    myMusicPlayer.release();
                    myMusicPlayer = null;
                    btnPlayStop.setText("Play");
                    btnPause.setEnabled(false);

                }
            });

        } else {
            // media khác nulll

            if ( myMusicPlayer.isPlaying()) {
                myMusicPlayer.stop();
                myMusicPlayer.release();
                myMusicPlayer = null;
                btnPlayStop.setText("Play");
                btnPause.setEnabled(false);

            } else {
                // media đang dừng
                try {
                    if (myMusicPlayer.isLooping())
                        myMusicPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myMusicPlayer.start();

                btnPlayStop.setText("Stop");
                btnPause.setEnabled(true);

            }

        }

    }
    // khai báo bên ngoài hàm để truy cập cho tiện
    private ArrayList<MusicDTO> songArrayList;
    private ListView lvSong;
    MusicDTO musicDTO;
    void initPlayList(){
        lvSong = view.findViewById(R.id.lv_song);

        songArrayList = new ArrayList<MusicDTO>();

        getSongList();  // lấy danh sách cho vào list xong rồi đổi lên adapter

        ArrayAdapter<MusicDTO> adapter = new ArrayAdapter<MusicDTO>(
                getContext(), android.R.layout.simple_list_item_1,songArrayList
        );

        lvSong.setAdapter(adapter);

        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MusicDTO musicDTO = songArrayList.get(i);
                uri = Uri.parse(musicDTO.file_path);

                Log.d("zzzz", "onItemClick: play = " + musicDTO.file_path);

                if (myMusicPlayer!= null ) {
                    myMusicPlayer.stop();
                    myMusicPlayer.release();
                    myMusicPlayer = null;
                    btnPlayStop.setText("Play");
                    btnPause.setEnabled(false);
                }
                PlayOrStopMusic();

            }
        });

        UserDAO dao = new UserDAO(getContext());
        ArrayList<UserDTO> list = null;
        lvSong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Thêm vào nhac yêu thích")
                        .setMessage("Bạn có muốn thêm bài này vào nhạc yêu thích?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

                return false;
            }
        });

    }

    // lấy danh sách bài nhạc trong điện thoại có sẫn
    public void getSongList() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        ContentResolver contentResolver = getActivity().getContentResolver();
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION
        };
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = contentResolver.query(musicUri, projection, selection, null, null, null);


        if (musicCursor != null && musicCursor.moveToFirst()) {

            //     xem thử xem có những cột gì tương ứng vơi những tên gì
            for (int i = 0; i < musicCursor.getColumnCount(); i++) {
                Log.d("zzzzzzz", "getSongList: " + i + "====" + musicCursor.getColumnName(i) + "===== " + musicCursor.getString(i));
            }


            //get Columns
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int file_path_Column = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            String title = musicCursor.getString(0);
            String artist = musicCursor.getString(1);
            String path = musicCursor.getString(2);
            String songDuration = musicCursor.getString(4);
            // Store the title, id and artist name in Song Array list.
            do {
                musicDTO = new MusicDTO();
                String thisTitle = musicCursor.getString(titleColumn);
                // bạn có thể lấy nhiều thuộc tính khác để trình bày lên giao diện cho đẹp.
                Log.i("chuongdk====", "getSongList: " + path);
                if (path.endsWith(".mp3")){
                    musicDTO.name = thisTitle;
                    musicDTO.file_path = musicCursor.getString(file_path_Column);  // lấy đường dẫn file nhạc

                    songArrayList.add(musicDTO);
                }
//                musicDTO.name = thisTitle;
//                musicDTO.file_path = musicCursor.getString(file_path_Column);  // lấy đường dẫn file nhạc
//
//                songArrayList.add(musicDTO); // đưa vào danh sách
//

            }
            while (musicCursor.moveToNext());

            // For best practices, close the cursor after use.
            musicCursor.close();
        }
    }
}
