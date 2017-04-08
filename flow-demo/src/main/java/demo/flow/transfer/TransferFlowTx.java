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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import top.bekit.flow.annotation.transaction.FlowTx;
import top.bekit.flow.annotation.transaction.InsertTarget;
import top.bekit.flow.annotation.transaction.LockTarget;
import top.bekit.flow.engine.TargetContext;

/**
 * 转账交易流程事务
 */
@FlowTx(flow = "transferFlow")
public class TransferFlowTx {

    @Autowired
    private TransferDao transferDao;

    @LockTarget     // 锁目标对象
    public Transfer lockTarget(TargetContext<Transfer> targetContext) {
        // 在并发情况下需要用锁来控制并发，你需要在这里实现具体的代码
        // 这里采用数据库行级悲观锁的形式锁住目标对象
        Transfer transfer = targetContext.getTarget();
        transfer = transferDao.findLockByBizNo(transfer.getBizNo());
        return transfer;
    }

    @InsertTarget   // 插入目标对象到数据库
    public Transfer insertTarget(TargetContext<Transfer> targetContext) {
        // 当你调用流程引擎的flowEngine.insertTargetAndStart方法时，需要实现本方法，
        // 就是以新事务将目标对象插入到数据库，插入成功后会提交事务
        // 原因：流程引擎是以新事务来执行流程的，它在执行之前会先调用上面的lockTarget将目标对象锁住，
        // 也就是目标对象必须以一个新事务插入到数据库并提交，然后再执行流程引擎。
        Transfer transfer = targetContext.getTarget();
        try {
            transferDao.save(targetContext.getTarget());
        } catch (DuplicateKeyException e) {
            // DuplicateKeyException是索引异常，也就是这笔流水已经存在，根据幂等性原则，查处已存在的那笔流水进行执行
            transfer = transferDao.findByBizNo(transfer.getBizNo());
        }
        return transfer;
    }

}
