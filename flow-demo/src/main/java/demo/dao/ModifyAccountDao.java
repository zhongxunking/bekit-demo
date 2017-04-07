/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:33 创建
 */
package demo.dao;

import demo.entity.ModifyAccount;
import demo.enums.TransferStatus;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.RepositoryDefinition;

import javax.persistence.LockModeType;

/**
 *
 */
@RepositoryDefinition(domainClass = ModifyAccount.class, idClass = Long.class)
public interface ModifyAccountDao {

    ModifyAccount save(ModifyAccount modifyAccount);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ModifyAccount findLockByTransferBizNoAndTransferStatus(String transferBizNo, TransferStatus transferStatus);

    ModifyAccount findTransferBizNoAndTransferStatus(String transferBizNo, TransferStatus transferStatus);

}
