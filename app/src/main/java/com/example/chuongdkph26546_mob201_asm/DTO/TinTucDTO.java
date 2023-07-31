package com.example.chuongdkph26546_mob201_asm.DTO;

import java.sql.Blob;

public class TinTucDTO {
    String title;
    String description;
    String Link;





    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
