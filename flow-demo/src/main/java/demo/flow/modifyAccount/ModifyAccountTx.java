/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 02:33 创建
 */
package demo.flow.modifyAccount;

import demo.dao.ModifyAccountDao;
import demo.entity.ModifyAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import top.bekit.flow.annotation.transaction.FlowTx;
import top.bekit.flow.annotation.transaction.InsertTarget;
import top.bekit.flow.annotation.transaction.LockTarget;
import top.bekit.flow.engine.TargetContext;

/**
 *
 */
@FlowTx(flow = "modifyAccountFlow")
public class ModifyAccountTx {

    @Autowired
    private ModifyAccountDao modifyAccountDao;

    @LockTarget
    public ModifyAccount lockTarget(TargetContext<ModifyAccount> targetContext) {
        ModifyAccount modifyAccount = targetContext.getTarget();
        modifyAccount = modifyAccountDao.findLockByTransferBizNoAndTransferStatus(modifyAccount.getTransferBizNo(), modifyAccount.getTransferStatus());
        return modifyAccount;
    }

    @InsertTarget
    public ModifyAccount insertTarget(TargetContext<ModifyAccount> targetContext) {
        ModifyAccount modifyAccount = targetContext.getTarget();
        try {
            modifyAccountDao.save(modifyAccount);
        } catch (DuplicateKeyException e) {
            modifyAccount = modifyAccountDao.findByTransferBizNoAndTransferStatus(modifyAccount.getTransferBizNo(), modifyAccount.getTransferStatus());
        }
        return modifyAccount;
    }

}
