package com.ft.base.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java常用工具类
 */
public class CommonUtil {

    private CommonUtil() {
    }


    public static boolean isEmpty(String s) {
        if (s == null || s.trim().length() == 0) return true;
        else return false;
    }


    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }


    public static String defaultString(String str) {
        return defaultString(str, "");
    }

    public static String defaultString(String str, String defaultStr) {
        if (isEmpty(str)) return defaultStr;
        else return str;
    }

    /**
     * 读取文件内容
     */
    public static String readFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                stringBuilder.append(lineTxt);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (fileInputStream != null) fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 如果字符串不够指定长度，左边用指定字符补足
     *
     * @param str      原字符串
     * @param iLength  指定长度
     * @param leftChar 左边补位的字符
     */
    public static String formatStringLeftChar(String str, int iLength, String leftChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iLength - str.length(); i++) {
            sb.append(leftChar);
        }
        sb.append(str);
        return sb.toString();
    }

    public enum Type {
        /**
         * 中文
         */
        chinese,
        /**
         * 字母a-zA-Z
         */
        english,
        /**
         * 数字0-9
         */
        number
    }

    /**
     * 是否只有指定类型或空
     */
    public static boolean isOnlyAppointedType(String str, Type... types) {
        if (isEmpty(str)) return true;
        StringBuffer rule = new StringBuffer();
        rule.append("[");
        for (Type type : types) {
            switch (type) {
                case chinese:
                    rule.append("\u4E00-\u9FBF");
                    break;
                case english:
                    rule.append("WXEntryActivity-zA-Z");
                    break;
                case number:
                    rule.append("0-9");
                    break;
            }
        }
        rule.append("]+");
        Pattern patt = Pattern.compile(rule.toString());
        Matcher m = patt.matcher(str);
        return m.matches();
    }

    /**
     * 过滤字符串，只保留指定类型的字节
     */
    public static String filterAppointedType(String str, Type... types) {
        if (isEmpty(str)) return "";
        StringBuffer rule = new StringBuffer();
        rule.append("[");
        for (Type type : types) {
            switch (type) {
                case chinese:
                    rule.append("\u4E00-\u9FBF");
                    break;
                case english:
                    rule.append("WXEntryActivity-zA-Z");
                    break;
                case number:
                    rule.append("0-9");
                    break;
            }
        }
        rule.append("]+");
        Pattern patt = Pattern.compile(rule.toString());
        char[] chars = str.toCharArray();
        StringBuffer sbf = new StringBuffer();
        for (char c : chars) {
            if (patt.matcher(String.valueOf(c)).find()) sbf.append(c);
        }
        return sbf.toString();
    }

    /**
     * 是否全是数字
     */
    public static boolean isOnlyNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判别手机是否为正确手机号码
     */
    public static boolean isMobileNum(String mobile) {
        if (isEmpty(mobile) || mobile.length() != 11) return false;
        Pattern p = Pattern.compile("^(1[345789][0-9])\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 判断邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null==email || "".equals(email)) return false;
        //  Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
      //  Pattern p = Pattern.compile("^^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.([a-zA-Z0-9]+)+$");
        Pattern p = Pattern.compile("^^[a-zA-Z0-9._-]+@([a-zA-Z0-9]+\\.)+([a-zA-Z0-9]+)+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }


    /**
     * 指定字符串在原串中出现的次数
     */
    public static int count(String src, String findStr) {
        if (src == null) return 0;
        int index = 0;
        int count = 0;
        while (index != -1) {
            index = src.indexOf(findStr);
            if (index == -1) break;
            src = src.substring(index + findStr.length());
            count++;
        }
        return count;
    }

    /**
     * 查找字符串在指定string中出现指定次数的位置
     *
     * @param src     原串
     * @param findStr 查找的字符串
     * @param time    第几次出现(从0开始)
     */
    public static int getIndex(String src, String findStr, int time) {
        if (src == null || src.indexOf(findStr) == -1) return -1;
        int postion = 0;
        int index = 0;
        int count = 0;
        while (index != -1 && count <= time) {
            index = src.indexOf(findStr);
            if (index == -1) break;
            postion += index;
            if (count != 0) postion += findStr.length();
            src = src.substring(index + findStr.length());
            count++;
        }
        if (time >= count) return -1;
        else return postion;
    }


    /**
     * bytes  ->  十六进制字符串
     */
    public static String bytes2Hex(byte[] bytes) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 16)
                buf.append("0");
            buf.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * 十六进制字符串  --> bytes
     */
    public static byte[] hex2Bytes(String hex) {
        char chars[] = hex.toCharArray();
        byte bytes[] = new byte[chars.length / 2];
        int byteCount = 0;
        for (int i = 0; i < chars.length; i += 2) {
            int newByte = 0;
            newByte |= hexChar2Byte(chars[i]);
            newByte <<= 4;
            newByte |= hexChar2Byte(chars[i + 1]);
            bytes[byteCount] = (byte) newByte;
            byteCount++;
        }
        return bytes;
    }


    /**
     * 十六进制字节  --> byte
     */
    private static byte hexChar2Byte(char ch) {
        switch (ch) {
            case 48: // '0'
                return 0;
            case 49: // '1'
                return 1;
            case 50: // '2'
                return 2;
            case 51: // '3'
                return 3;
            case 52: // '4'
                return 4;
            case 53: // '5'
                return 5;
            case 54: // '6'
                return 6;
            case 55: // '7'
                return 7;
            case 56: // '8'
                return 8;
            case 57: // '9'
                return 9;
            case 97: // 'WXEntryActivity'
                return 10;
            case 98: // 'b'
                return 11;
            case 99: // 'c'
                return 12;
            case 100: // 'd'
                return 13;
            case 101: // 'e'
                return 14;
            case 102: // 'f'
                return 15;
            case 58: // ':'
            case 59: // ';'
            case 60: // '<'
            case 61: // '='
            case 62: // '>'
            case 63: // '?'
            case 64: // '@'
            case 65: // 'A'
            case 66: // 'B'
            case 67: // 'C'
            case 68: // 'D'
            case 69: // 'E'
            case 70: // 'F'
            case 71: // 'G'
            case 72: // 'H'
            case 73: // 'I'
            case 74: // 'J'
            case 75: // 'K'
            case 76: // 'L'
            case 77: // 'M'
            case 78: // 'N'
            case 79: // 'O'
            case 80: // 'P'
            case 81: // 'Q'
            case 82: // 'R'
            case 83: // 'S'
            case 84: // 'T'
            case 85: // 'U'
            case 86: // 'V'
            case 87: // 'W'
            case 88: // 'X'
            case 89: // 'Y'
            case 90: // 'Z'
            case 91: // '['
            case 92: // '\\'
            case 93: // ']'
            case 94: // '^'
            case 95: // '_'
            case 96: // '`'
            default:
                return 0;
        }
    }

    /**
     * 四舍五入
     * @param money
     * @return
     */
    public static String transMoney(double money) {
        BigDecimal b = new BigDecimal((money) / 100);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String transMoney(String money) {
        return transMoney(Double.valueOf(money));
    }

}
