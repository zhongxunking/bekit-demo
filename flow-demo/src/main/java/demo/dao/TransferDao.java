/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:27 创建
 */
package demo.dao;

import demo.entity.Transfer;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.RepositoryDefinition;

import javax.persistence.LockModeType;

/**
 *
 */
@RepositoryDefinition(domainClass = Transfer.class, idClass = Long.class)
public interface TransferDao {

    Transfer save(Transfer transfer);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Transfer findLockByBizNo(String bizNo);

    Transfer findByBizNo(String bizNo);

}
