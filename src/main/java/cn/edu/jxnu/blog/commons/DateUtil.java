package cn.edu.jxnu.blog.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 日期工具类
 */
public class DateUtil {

    /**
     * 日期对象转字符串
     * @author xp
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date,String format){
        String result="";
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        if(date!=null){
            result=sdf.format(date);
        }
        return result;
    }

    /**
     * 按照yyyyMMddhhmmss 获取当前日期
     * @return
     */
    public static String getCurrentDateStr() {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        return sdf.format(date);
    }

}
