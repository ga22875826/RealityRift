package com.teamsix.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class CaptchaGenerator {
	
	private static final int WIDTH = 200;
    private static final int HEIGHT = 80;
    private static final int FONT_SIZE = 40;
    private static final int CODE_LENGTH = 4;

    public static byte[] generateCaptchaImage() throws IOException {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 設置背景顏色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // 生成驗證碼
        String captchaCode = generateCaptchaCode();

        // 繪製驗證碼文本
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE));
        g2d.setColor(Color.BLACK);
        g2d.drawString(captchaCode, 20, 50);

        // 加入干擾線
        g2d.setColor(Color.GRAY);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g2d.drawLine(x1, y1, x2, y2);
        }

        g2d.dispose();

        // 將圖片轉換為 byte 陣列
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    private static String generateCaptchaCode() {
        // 生成驗證碼字元
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int num = random.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }
	
}
