package com.ft.base.common.utils;


import android.util.Patterns;
import android.webkit.URLUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串操作工具包
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");

    private final static Pattern URL = Pattern
            .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.getMessage();
        }
        if(inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }

    public static String getYearMonthDay(String dayStr) {
        String [] str = dayStr.split(" ");
        String data = "";
        if (null != str && str.length == 2) {
            String[] dataStr = str[0].split("-");
            if (null != dataStr && dataStr.length == 3) {
                data = dataStr[0] + "年" + dataStr[1] + "月" + dataStr[2] + "日";
            }

        }
        return data;
    }

    public static boolean monitorTime(int begin, int end) {
        boolean flag = false;

        // 获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        String beginTime = "";
        String endTime = "";

        // 定义每天的24h时间范围
        if (begin < 10) {
            beginTime = subDate + " 0" + begin + ":00:00";
        } else {
            beginTime = subDate + " " + begin + ":00:00";
        }

        if (end < 10) {
            endTime = subDate + " 0" + end + ":00:00";
        } else {
            endTime = subDate + " " + end + ":00:00";
        }

        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.getMessage();
        }
        if(nowDate.after(paseBeginTime) && nowDate.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }

    public static String getPrintSize(float size) {
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + " GB";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + " MB";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + " KB";
        } else {
            resultSize = size + " B";
        }
        return resultSize;
    }
    
    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getFormatTime2time(String sdate) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = toDate(sdate, sdf);
        if (date != null) {
            SimpleDateFormat sdf02 = new SimpleDateFormat("yyyy/MM/dd");
            time = sdf02.format(date);
        }
        return time;
    }


    public static String getDateString(Date date) {
        return dateFormater.get().format(date);
    }

    //新增
    public static String getTimeByLong(long date) {
        Date data = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d HH:mm:ss");
        String time = sdf.format(data);
        return time;
    }

    public static String getTimeByLong02(long date) {
        Date data = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(data);
        return time;
    }

    public static String getTimeByLong021(long date) {
        Date data = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String time = sdf.format(data);
        return time;
    }

    public static String getTime2Media(long date) {
        Date data = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(data);
        return time;
    }

    /**
     * 金额转换 分转元
     */
    public static String fenToYuan(String amount){
        long l = Long.valueOf(amount);
        double d = new Double(l)/100;
        Format f = new DecimalFormat("0.00");
        String str = f.format(d);
        String[] s = str.split("\\.");
        if (null != s && s.length == 2) {
            if (Integer.valueOf(s[1]) == 0) {
                return s[0];
            } else {
                int index = s[1].indexOf("0");
                if (index == 1 && s[1].length() == 2) {
                    return s[0] + "." + s[1].substring(0,1);
                } else {
                    return str;
                }
            }
        }
        return str;
    }
    // 元转为分
    private String yuanToFen(String amount){
        NumberFormat format = NumberFormat.getInstance();
        try{
            Number number = format.parse(amount);
            double temp = number.doubleValue() * 100.0;
            format.setGroupingUsed(false);
            // 设置返回数的小数部分所允许的最大位数
            format.setMaximumFractionDigits(0);
            amount = format.format(temp);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * 获取时间格式 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getTimeByLong03(long date) {
        Date data = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(data);
        return time;
    }

    public static String getTimeByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d HH:mm:ss");
        String time = sdf.format(date);
        return time;
    }

    public static String getTimeByDate02(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String time = sdf.format(date);
        return time;
    }

    /***
     * 是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
       // String formatStr = ([1-9]\d*\.?\d*)|(0\.\d*[1-9])
        Pattern pattern = Pattern.compile("[0-9]*|[0-9]\\d*\\.?\\d*|0\\.\\d*[0-9]");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 判别手机是否为正确手机号码
     */
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^(1[3,4,5,7,8,9][0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 将字符串时间转换成当前格式时间
     *
     * @param
     * @return
     */
    public static String getTimeStr(String dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = "";
        try {
            Date date = df.parse(dateTime);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            time = format1.format(date);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return time;
    }
    public static String getTimeString(String date) {
        long ll = Long.parseLong(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date(ll));
        return time;
    }

    public static String getTimeByString(String date) {
        long ll = Long.parseLong(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date(ll));
        return time;
    }

    public static String getTimeByStringBySSS(String date) {
        long ll = Long.parseLong(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        String time = sdf.format(new Date(ll));
        return time;
    }



    /**
     * 时间是否比预定时间大
     *
     * @param overdate
     * @return
     */
    public static boolean isOverDate(long overdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String timeStr = sdf.format(new Date(System.currentTimeMillis()));
        long nowtime = Long.parseLong(timeStr);
        if (nowtime <= overdate) {
            return true;
        }
        return false;
    }



    public static String friendly_time2(String sdate) {
        String res = "";
        if (isEmpty(sdate))
            return "";

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String currentData = StringUtils.getDataTime("MM-dd");
        int currentDay = toInt(currentData.substring(3));
        int currentMoth = toInt(currentData.substring(0, 2));

        int sMoth = toInt(sdate.substring(5, 7));
        int sDay = toInt(sdate.substring(8, 10));
        int sYear = toInt(sdate.substring(0, 4));
        Date dt = new Date(sYear, sMoth - 1, sDay - 1);

        if (sDay == currentDay && sMoth == currentMoth) {
            res = "今天 / " + weekDays[getWeekOfDate(new Date())];
        } else if (sDay == currentDay + 1 && sMoth == currentMoth) {
            res = "昨天 / " + weekDays[(getWeekOfDate(new Date()) + 6) % 7];
        } else {
            if (sMoth < 10) {
                res = "0";
            }
            res += sMoth + "/";
            if (sDay < 10) {
                res += "0";
            }
            res += sDay + " / " + weekDays[getWeekOfDate(dt)];
        }

        return res;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    public static boolean isTodays(String sdate) {
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        Date d = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);
            d = dateFormat.parse(sdate);
        } catch (ParseException e) {
            e.getMessage();
        }

        if(d.after(paseBeginTime) && d.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 返回long类型的今天的日期
     *
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    public static String getCurTimeStr() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater.get().format(cal.getTime());
        return curDate;
    }

    /***
     * 计算两个时间差，返回的是的秒s(天)
     *
     * @param dete1
     * @param date2
     * @return
     */
    public static long calDateDifferent(long dete1, long date2) {

        long diff = 0;

        Date d1 = null;
        Date d2 = null;

        try {
//            d1 = dateFormater.get().parse(dete1);
//            d2 = dateFormater.get().parse(date2);
            d1 = new Date(dete1);
            d2 = new Date(date2);
            // 毫秒ms
            diff = Math.abs(d2.getTime() - d1.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff / 1000 / 3600 / 24;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    /**
     * 判断是否为一个合法的url地址
     *
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return URL.matcher(str).matches();
    }


    /**
     * 判断是否为一个合法的url地址
     *
     * @param str
     * @return
     */
    public static boolean isHttpUrl(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }

        return Patterns.WEB_URL.matcher(str).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line + "<br>");
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    public static boolean getIsOrderTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String strdate = sdf.format(new Date());
        int time = Integer.parseInt(strdate);
        if (time < 20171230) {
            return true;
        }
        return false;
    }

    /***
     * 截取字符串
     *
     * @param start 从那里开始，0算起
     * @param num   截取多少个
     * @param str   截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @return
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    public static int[] getCurrentDate() {
        int[] dateBundle = new int[3];
        String[] temp = getDataTime("yyyy-MM-dd").split("-");

        for (int i = 0; i < 3; i++) {
            try {
                dateBundle[i] = Integer.parseInt(temp[i]);
            } catch (Exception e) {
                dateBundle[i] = 0;
            }
        }
        return dateBundle;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 解决文字在TextView中显示不齐问题
     *
     * @param str
     * @return
     */
    public static String StringFilter(String str) {
        if (str == null) {
            return null;
        }
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 判断字符串是否为null或者是“”
     *
     * @param str
     * @return
     */
    public static boolean isStringEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * utf-8编码
     *
     * @param text
     * @return
     */
    public static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return text;
        }
    }

    public static String text2short(String str) {
        if (!isEmpty(str) && str.length() > 10) {
            str = str.substring(0, 10) + "**";
        }
        return str;
    }

    public static List<String> splitString(String content, String rule) {
        List<String> result = new ArrayList<>();
        if (!isEmpty(content) && !isEmpty(rule) && content.contains(rule)) {
            int index = 0;
            int nextIndex = 0;
            while (index != -1) {
                index = content.indexOf(rule, nextIndex);
                nextIndex = content.indexOf(rule, index + rule.length());
                LogUtil.e("index-- : " + index + " " + nextIndex);
                if (nextIndex != -1) {
                    result.add(content.substring(index, nextIndex));
                } else {
                    result.add(content.substring(index));
                    index = -1;
                }
                LogUtil.e("index: " + index + " " + nextIndex);
            }
        } else {
            result.add(content);
        }
        LogUtil.e("split result: " + result.toString());
        return result;
    }
}
