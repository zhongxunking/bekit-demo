/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:36 创建
 */
package demo.flow.transfer;

import demo.dao.TransferDao;
import demo.entity.Transfer;
import lombok.extern.slf4j.Slf4j;
import org.bekit.flow.annotation.locker.StateLock;
import org.bekit.flow.annotation.locker.TheFlowLocker;
import org.bekit.flow.engine.FlowContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 转账交易流程事务
 */
@TheFlowLocker(flow = "transferFlow")
@Slf4j
public class TransferFlowLocker {
    @Autowired
    private TransferDao transferDao;

    // 状态加锁，主要用于事务中对目标对象加锁
    @StateLock
    public Transfer stateLock(FlowContext<Transfer> context) {
        log.info("对流程[transfer]加状态锁（利用数据库的行级悲观锁实现）");
        // 在并发情况下需要用锁来控制并发，你需要在这里实现具体锁住目标对象的代码
        // 这里采用数据库行级悲观锁的形式锁住目标对象
        Transfer transfer = context.getTarget();
        transfer = transferDao.findLockByBizNo(transfer.getBizNo());
        return transfer;
    }
}
