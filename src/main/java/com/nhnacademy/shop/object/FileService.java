package com.nhnacademy.shop.object;

import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 설명
 *
 * @Author : 박병휘
 * @Date : 2024/04/24
 */
@Data
public class FileService {
    String imageUrl = "http://image.aladdin.co.kr/coveretc/book/coversum/8984016373_1.jpg";
    String destinationFile = "image.jpg";

    public InputStream getFileByUrl(String imageUrl) {
        try {
            // URL에서 이미지 다운로드
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
