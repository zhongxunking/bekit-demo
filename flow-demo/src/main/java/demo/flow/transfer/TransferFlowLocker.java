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
import org.bekit.flow.annotation.locker.*;
import org.bekit.flow.engine.FlowContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 转账交易流程事务
 * 在并发情况下需要用锁来控制并发，你需要在这里实现具体锁住目标对象的代码
 */
@TheFlowLocker(flow = "transferFlow")
@Slf4j
public class TransferFlowLocker {
    @Autowired
    private TransferDao transferDao;

    // 加流程锁，流程执行前会调用本方法（可选）
    @FlowLock
    public Transfer flowLock(FlowContext<Transfer> context) {
        log.info("对流程[transfer]加流程锁（仅用于演示。实际场景可用分布式锁实现）");
        return context.getTarget();
    }

    // 状态加锁，每个状态节点执行前会调用本方法（可选）
    @StateLock
    public Transfer stateLock(FlowContext<Transfer> context) {
        log.info("对流程[transfer]加状态锁（利用数据库的行级悲观锁实现）");
        // 这里采用数据库行级悲观锁的形式锁住目标对象
        Transfer transfer = context.getTarget();
        transfer = transferDao.findLockByBizNo(transfer.getBizNo());
        return transfer;
    }

    // 解状态锁，下一个状态节点执行前会调用本方法（可选）
    @StateUnlock
    public void stateUnlock(FlowContext<Transfer> context) {
        log.info("对流程[transfer]解状态锁（仅用于演示。由于事务提交后，数据库的行级悲观锁会自动释放，所以无需手动解锁）");
    }

    // 解流程锁，流程执行后会调用本方法（可选）
    @FlowUnlock
    public void flowUnlock(FlowContext<Transfer> context) {
        log.info("对流程[transfer]解流程锁（仅用于演示）");
    }
}
