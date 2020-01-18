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
 * 流水号生成器
 */
public class UID {
    private static final Random RANDOM = new Random();

    /**
     * 生成20位的流水号
     * （这里为方便演示生成的流水号可能重复，在公司正式环境中流水号应该是唯一的）
     */
    public static String newId() {
        Format format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date()) + String.format("%03d", RANDOM.nextInt(1000));
    }

}
