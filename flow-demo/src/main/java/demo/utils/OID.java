/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 03:02 创建
 */
package demo.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 */
public class OID {
    private static final Random RANDOM = new Random();

    public static String newId() {
        Format format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date()) + String.format("%3s", RANDOM.nextInt(1000));
    }

}
