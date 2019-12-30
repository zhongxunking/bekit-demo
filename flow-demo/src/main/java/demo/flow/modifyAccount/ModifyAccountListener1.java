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
import org.bekit.common.util.EnumUtils;
import org.bekit.event.listener.PriorityType;
import org.bekit.flow.annotation.listener.ListenDecidedNode;
import org.bekit.flow.annotation.listener.ListenFlowException;
import org.bekit.flow.annotation.listener.TheFlowListener;
import org.bekit.flow.engine.FlowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 修改账务流程监听器
 */
@TheFlowListener(flow = "modifyAccountFlow", priority = 1)
public class ModifyAccountListener1 {
    private static final Logger logger = LoggerFactory.getLogger(ModifyAccountListener1.class);

    @Autowired
    private ModifyAccountDao modifyAccountDao;

    @ListenDecidedNode
    public void listenNodeDecided(String node, FlowContext<ModifyAccount> context) {
        logger.info("ModifyAccountListener1.listenNodeDecide");
        ModifyAccount modifyAccount = context.getTarget();
        ModifyAccountStatus status = EnumUtils.getRequiredEnum(ModifyAccountStatus.class, node);
        modifyAccount.setStatus(status);
        modifyAccountDao.save(modifyAccount);
    }

    @ListenFlowException(priorityType = PriorityType.DESC)
    public void listenFlowException(Throwable throwable, FlowContext<ModifyAccount> context) {
        logger.info("ModifyAccountListener1.listenFlowException");
        logger.error("账户变动过程中发生异常：{}", throwable.getMessage());
    }

}
