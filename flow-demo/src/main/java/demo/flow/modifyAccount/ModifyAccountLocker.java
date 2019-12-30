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
import org.bekit.flow.annotation.locker.StateLock;
import org.bekit.flow.annotation.locker.TheFlowLocker;
import org.bekit.flow.engine.FlowContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 修改账务流程事务
 */
@TheFlowLocker(flow = "modifyAccountFlow")
public class ModifyAccountLocker {

    @Autowired
    private ModifyAccountDao modifyAccountDao;

    @StateLock
    public ModifyAccount lockTarget(FlowContext<ModifyAccount> context) {
        ModifyAccount modifyAccount = context.getTarget();
        modifyAccount = modifyAccountDao.findLockByTransferBizNoAndTransferStatus(modifyAccount.getTransferBizNo(), modifyAccount.getTransferStatus());
        return modifyAccount;
    }
}
