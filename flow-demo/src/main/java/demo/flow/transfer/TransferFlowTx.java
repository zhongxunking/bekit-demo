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
 *
 */
@FlowTx(flow = "transfer")
public class TransferFlowTx {

    @Autowired
    private TransferDao transferDao;

    @LockTarget
    public Transfer lockTarget(TargetContext<Transfer> targetContext) {
        Transfer transfer = targetContext.getTarget();
        transfer = transferDao.findLockByBizNo(transfer.getBizNo());
        return transfer;
    }

    @InsertTarget
    public Transfer insertTarget(TargetContext<Transfer> targetContext) {
        Transfer transfer = targetContext.getTarget();
        try {
            transferDao.save(targetContext.getTarget());
        } catch (DuplicateKeyException e) {
            transfer = transferDao.findByBizNo(transfer.getBizNo());
        }
        return transfer;
    }

}
