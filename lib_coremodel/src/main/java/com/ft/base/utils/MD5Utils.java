package com.ft.base.utils;

import com.ft.base.net.BuildConfig;

import java.security.MessageDigest;
import java.util.Map;

public class MD5Utils {
    /**
     * MD5签名
     *
     * @param paramSrc
     *            the source to be signed
     * @return
     * @throws Exception
     */
    public static String sign(String paramSrc) {
        String sign = md5(paramSrc + "&key=" + BuildConfig.MD5_KEY);
        return sign;
    }

    public static String sign(String paramSrc, String key) {
        String sign = md5(paramSrc + "&key=" + key);
        return sign;
    }

    /**
     * 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
     */
    public static String getParamSrc(Map<String,Object> paramsMap) {
        StringBuffer paramstr = new StringBuffer();
        for (Object pkey : paramsMap.keySet()) {
            Object pvalue = paramsMap.get(pkey);
            if (null != pvalue && "" != pvalue) {// 空值不传递，不签名
                paramstr.append(pkey + "=" + pvalue + "&"); // 签名原串，不url编码
            }
        }
        // 去掉最后一个&
        String result = paramstr.substring(0, paramstr.length() - 1);
        return result;
    }

    /**
     * MD5验签
     *
     * @param source
     *            签名内容
     * @param bankSign
     *            签名值
     * @return
     */
    public static boolean verify(String source, String bankSign) {
        String sign = md5(source + "&key=MXizAUL5");
        System.out.println("自签结果：" + sign);
        return bankSign.equals(sign);
    }

    public final static String md5(String paramSrc) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = paramSrc.getBytes("UTF-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
