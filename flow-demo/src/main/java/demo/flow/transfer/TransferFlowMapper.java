/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:36 创建
 */
package demo.flow.transfer;

import demo.entity.Transfer;
import org.bekit.common.util.EnumUtils;
import org.bekit.flow.annotation.mapper.MappingNode;
import org.bekit.flow.annotation.mapper.TheFlowMapper;
import org.bekit.flow.engine.FlowContext;

/**
 * 转账交易流程事务
 */
@TheFlowMapper(flow = "transferFlow")
public class TransferFlowMapper {

    // 目标对象映射方法，需要根据目标对象的状态等信息映射到一个流程节点，流程引擎将从这个节点进行执行
    @MappingNode
    public String targetMapping(FlowContext<Transfer> context) {    // 入参就是目标对象
        return EnumUtils.getCamelCaseName(context.getTarget().getStatus());
    }
}
