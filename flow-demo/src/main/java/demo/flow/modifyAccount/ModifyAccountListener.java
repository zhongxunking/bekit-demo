/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 02:40 创建
 */
package demo.flow.modifyAccount;

import demo.dao.ModifyAccountDao;
import demo.entity.ModifyAccount;
import demo.enums.ModifyAccountStatus;
import org.bekit.flow.annotation.listener.ListenFlowException;
import org.bekit.flow.annotation.listener.ListenNodeDecided;
import org.bekit.flow.annotation.listener.TheFlowListener;
import org.bekit.flow.engine.TargetContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 修改账务流程监听器
 */
@TheFlowListener(flow = "modifyAccountFlow")
public class ModifyAccountListener {
    private static final Logger logger = LoggerFactory.getLogger(ModifyAccountListener.class);

    @Autowired
    private ModifyAccountDao modifyAccountDao;

    @ListenNodeDecided
    public void listenNodeDecide(String node, TargetContext<ModifyAccount> targetContext) {
        ModifyAccount modifyAccount = targetContext.getTarget();
        ModifyAccountStatus status;
        switch (node) {
            case "modify":
                status = ModifyAccountStatus.MODIFY;
                break;
            case "generateRefOrderNo":
                status = ModifyAccountStatus.GENERATE_REF_ORDER_NO;
                break;
            case "success":
                status = ModifyAccountStatus.SUCCESS;
                break;
            case "fail":
                status = ModifyAccountStatus.FAIL;
                break;
            default:
                throw new RuntimeException("监听到非法的节点被选择");
        }
        modifyAccount.setStatus(status);
        modifyAccountDao.save(modifyAccount);
    }

    @ListenFlowException
    public void listenFlowException(Throwable throwable, TargetContext<ModifyAccount> targetContext) {
        logger.error("账户变动过程中发生异常：", throwable);
    }

}
