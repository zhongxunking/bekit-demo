/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 21:43 创建
 */
package demo.order;

import lombok.Getter;
import lombok.Setter;

/**
 * 新增用户order
 */
@Getter
@Setter
public class AddUserOrder {
    // 用户名
    private String username;
    // 年龄
    private int age;
}
