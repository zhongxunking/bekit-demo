/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:20 创建
 */
package demo.enums;

/**
 * 修改账状态
 */
public enum ModifyAccountStatus {
    /**
     * 修改
     */
    MODIFY,

    /**
     * 生成调用账务系统的订单号
     */
    GENERATE_REF_ORDER_NO,

    /**
     * 成功
     */
    SUCCESS,

    /**
     * 失败
     */
    FAIL,;

}
