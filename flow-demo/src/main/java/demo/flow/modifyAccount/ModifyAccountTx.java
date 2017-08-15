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
import org.bekit.flow.annotation.transaction.FlowTx;
import org.bekit.flow.annotation.transaction.InsertTarget;
import org.bekit.flow.annotation.transaction.LockTarget;
import org.bekit.flow.engine.TargetContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 修改账务流程事务
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

        ModifyAccount savedModifyAccount = modifyAccountDao.findLockByTransferBizNoAndTransferStatus(modifyAccount.getTransferBizNo(), modifyAccount.getTransferStatus());
        if (savedModifyAccount == null) {
            modifyAccountDao.save(modifyAccount);
        } else {
            modifyAccount = savedModifyAccount;
        }

        return modifyAccount;
    }

}
