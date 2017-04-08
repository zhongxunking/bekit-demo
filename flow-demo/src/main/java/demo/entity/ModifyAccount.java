/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:13 创建
 */
package demo.entity;

import demo.enums.Direction;
import demo.enums.ModifyAccountStatus;
import demo.enums.ModifyAccountType;
import demo.enums.TransferStatus;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(indexes = {@Index(unique = true, columnList = "transferBizNo,transferStatus")})
public class ModifyAccount {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(length = 20)
    private String transferBizNo;

    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private TransferStatus transferStatus;

    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private ModifyAccountType type;

    @Column(length = 20)
    private String accountNo;

    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Column
    private Long amount;

    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private ModifyAccountStatus status;

    @Column(length = 40)
    private String refOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransferBizNo() {
        return transferBizNo;
    }

    public void setTransferBizNo(String transferBizNo) {
        this.transferBizNo = transferBizNo;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public ModifyAccountType getType() {
        return type;
    }

    public void setType(ModifyAccountType type) {
        this.type = type;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public ModifyAccountStatus getStatus() {
        return status;
    }

    public void setStatus(ModifyAccountStatus status) {
        this.status = status;
    }

    public String getRefOrderNo() {
        return refOrderNo;
    }

    public void setRefOrderNo(String refOrderNo) {
        this.refOrderNo = refOrderNo;
    }
}
