package com.example.chuongdkph26546_mob201_asm.DTO;

import android.util.Log;

public class UserDTO {

    String username;
    String password;
    String fvtMusic;
    String  file_path;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String fvtMusic, String file_path) {
        this.username = username;
        this.password = password;
        this.fvtMusic = fvtMusic;
        this.file_path = file_path;
    }

    public String toString(){
        Log.d("zzzzzz", "toString: file path " + file_path);
        return  fvtMusic;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFvtMusic() {
        return fvtMusic;
    }

    public void setFvtMusic(String fvtMusic) {
        this.fvtMusic = fvtMusic;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
