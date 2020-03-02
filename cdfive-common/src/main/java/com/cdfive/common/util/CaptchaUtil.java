package com.cdfive.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author cdfive
 */
public class CaptchaUtil {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaUtil.class);

    private static final int DEFAULT_CAPTCHA_LENGTH = 4;

    private static final int DEFALUT_WIDTH = 100;

    private static final int DEFAULT_HEIGHT = 18;

    private static final int DEFAULT_SIZE = 17;

    private static final char[] CODE_SEQUENCE = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String generateCaptcha() {
        return generateCaptcha(DEFAULT_CAPTCHA_LENGTH);
    }

    public static String generateCaptcha(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(CODE_SEQUENCE[random.nextInt(CODE_SEQUENCE.length)]);
        }
        return builder.toString();
    }

    public static void writeCaptcha(String captcha, HttpServletResponse response) {
        writeCaptcha(captcha, response, DEFALUT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SIZE);
    }

    public static void writeCaptcha(String captcha, HttpServletResponse response, int width, int height, int fontSize) {

        if (StringUtils.isBlank(captcha)) {
            return;
        }

        int length = captcha.length();

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        SecureRandom random = new SecureRandom();

        Graphics g = image.getGraphics();
        g.setColor(getRandColor(200, 250, random));
        g.fillRect(1, 1, width - 1, height - 1);
        g.setColor(new Color(133, 187, 210));
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
        g.setColor(getRandColor(160, 200, random));

        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.drawLine(x, y, x + xl, y + yl);
        }

        for (int i = 0; i < 70; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12) + 1;
            int yl = random.nextInt(6) + 1;
            g.drawLine(x, y, x - xl, y - yl);
        }

        int captchaLength = captcha.length();
        int xRatio = width / (length + 2);
        int yRatio = (height / 2) + (fontSize / 3);
        int fRatio = xRatio - fontSize;
        fRatio = fRatio < 0 ? 0 : fRatio;
        for (int i = 0; i < captchaLength; i++) {
            char c = captcha.charAt(i);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(String.valueOf(c), xRatio * (i + 1) + fRatio, yRatio);
        }
        g.dispose();

        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            logger.error("CaptchaUtil#writeCaptcha error,captcha={}:", captcha, e);
        }
    }

    private static Color getRandColor(int fc, int bc, Random random) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }
}
