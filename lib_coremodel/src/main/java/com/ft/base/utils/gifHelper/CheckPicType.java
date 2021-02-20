package com.ft.base.utils.gifHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CheckPicType {
    /**
     * jpg格式图片文件头hex信息
     */
    public static final String JPG_HEX = "FFD8FF";
    /**
     * png格式图片文件头hex信息
     */
    public static final String PNG_HEX = "89504E47";
    /**
     * gif格式图片文件头hex信息
     */
    public static final String GIF_HEX = "47494638";
    /**
     * bmp格式图片文件头hex信息
     */
    public static final String BMP_HEX = "424D";

    /**
     * jpg格式
     */
    public static final String JPG = "jpg";
    /**
     * jpeg格式
     */
    public static final String JPEG = "jpeg";
    /**
     * png格式
     */
    public static final String PNG = "png";
    /**
     * gif格式
     */
    public static final String GIF = "gif";
    /**
     * bmp格式
     */
    public static final String BMP = "bmp";

    /**
     * 获取图片类型
     * JPG图片头信息:FFD8FF
     * PNG图片头信息:89504E47
     * GIF图片头信息:47494638
     * BMP图片头信息:424D
     *
     * @param is 图片文件流
     * @return 图片类型:jpg|png|gif|bmp
     */
    public static String getImageType(InputStream is) {
        String type = null;
        if (is != null) {
            byte[] b = new byte[4];
            try {
                is.read(b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String hexStr = HexConverter.byteArrayToHexString(b, true);//图片文件流前4个字节的头信息（子文字母）
            if (hexStr != null) {
                if (hexStr.startsWith(JPG_HEX)) {
                    type = JPG;
                } else if (hexStr.startsWith(PNG_HEX)) {
                    type = PNG;
                } else if (hexStr.startsWith(GIF_HEX)) {
                    type = GIF;
                } else if (hexStr.startsWith(BMP_HEX)) {
                    type = BMP;
                }
            }
        }
        return type;
    }

    public static String getImageType(byte[] b) {
        String type = null;
        String hexStr = HexConverter.byteArrayToHexString(b, true);//图片文件流前4个字节的头信息（子文字母）
        if (hexStr != null) {
            if (hexStr.startsWith(JPG_HEX)) {
                type = JPG;
            } else if (hexStr.startsWith(PNG_HEX)) {
                type = PNG;
            } else if (hexStr.startsWith(GIF_HEX)) {
                type = GIF;
            } else if (hexStr.startsWith(BMP_HEX)) {
                type = BMP;
            }
        }

        return type;
    }


    public static String getImageTypeFile(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            return getImageType(fileInputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] readStream(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
                outStream.flush();
            }
            outStream.close();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return outStream.toByteArray();
    }

}
