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
import org.bekit.flow.annotation.locker.StateLock;
import org.bekit.flow.annotation.locker.TheFlowLocker;
import org.bekit.flow.engine.FlowContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 转账交易流程事务
 */
@TheFlowLocker(flow = "transferFlow")
public class TransferFlowLocker {

    @Autowired
    private TransferDao transferDao;

    @StateLock     // 锁目标对象
    public Transfer lockTarget(FlowContext<Transfer> context) {
        // 在并发情况下需要用锁来控制并发，你需要在这里实现具体锁住目标对象的代码
        // 这里采用数据库行级悲观锁的形式锁住目标对象
        Transfer transfer = context.getTarget();
        transfer = transferDao.findLockByBizNo(transfer.getBizNo());
        return transfer;
    }

}
