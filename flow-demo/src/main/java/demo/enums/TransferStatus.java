/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:06 创建
 */
package demo.enums;

/**
 * 转账交易状态
 */
public enum TransferStatus {
    /**
     * 付款人下账
     */
    DOWN_PAYER,

    /**
     * 收款人上账
     */
    UP_PAYEE,

    /**
     * 恢复付款人资金
     */
    RESTORE_PAYER,

    /**
     * 成功
     */
    SUCCESS,

    /**
     * 失败
     */
    FAIL,;

}
