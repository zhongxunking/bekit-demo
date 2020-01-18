/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:02 创建
 */
package demo.entity;

import demo.enums.TransferStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 转账实体
 */
@Entity
@Getter
@Setter
public class Transfer {
    // 主键id
    @Id
    @GeneratedValue
    @Column
    private Long id;

    // 流水号
    @Column(length = 20, unique = true)
    private String bizNo;

    // 付款人账号
    @Column(length = 20)
    private String payerAccountId;

    // 收款人账号
    @Column(length = 20)
    private String payeeAccountId;

    // 金额
    @Column
    private Long amount;

    // 状态
    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    @Override
    public String toString() {
        return String.format("Transfer{id=%d,bizNo=%s,payerAccountId=%s,payeeAccountId=%s,amount=%d,status=%s}", id, bizNo, payerAccountId, payeeAccountId, amount, status);
    }
}
