package com.ahah.crawling.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class ImgCrawler {

    public static String getImg(String url, String id) {
        // 이미지 경로 입력
        URL imgUrl = null;
        try {
            imgUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String path = "./resources/img";
        File folder = new File(path);

        if(!folder.exists()) {
            try {
                folder.mkdirs();
                System.out.println("./resources/img Created");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        UUID uuid = UUID.randomUUID();
        assert imgUrl != null;
        BufferedImage img = null;
        try {
            img = ImageIO.read(imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(path + "/" + uuid + id + ".jpg");
        assert img != null;
        try {
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uuid.toString();
    }

}
