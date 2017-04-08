/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:02 创建
 */
package demo.entity;

import demo.enums.TransferStatus;

import javax.persistence.*;

/**
 * 转账实体
 */
@Entity
public class Transfer {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(length = 20, unique = true)
    private String orderNo;

    @Column(length = 20, unique = true)
    private String bizNo;

    @Column(length = 20)
    private String payerAccountNo;

    @Column(length = 20)
    private String payeeAccountNo;

    @Column
    private Long amount;

    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    public String getPayeeAccountNo() {
        return payeeAccountNo;
    }

    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Transfer{id=%d,orderNo=%s,bizNo=%s,payerAccountNo=%s,payeeAccountNo=%s,amount=%d,status=%s}", id, orderNo, bizNo, payerAccountNo, payeeAccountNo, amount, status);
    }
}
