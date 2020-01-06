/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:36 创建
 */
package demo.flow.transfer;

import demo.entity.Transfer;
import demo.enums.TransferStatus;
import org.bekit.common.util.EnumUtils;
import org.bekit.flow.annotation.mapper.MappingNode;
import org.bekit.flow.annotation.mapper.TheFlowMapper;
import org.bekit.flow.engine.FlowContext;

/**
 * 转账交易流程映射器
 */
@TheFlowMapper(flow = "transferFlow")
public class TransferFlowMapper {

    // 映射出节点，需要根据目标对象的状态等信息映射到一个流程节点，流程引擎将从这个节点进行执行
    @MappingNode
    public String mappingNode(FlowContext<Transfer> context) {    // 入参就是流程上下文
        TransferStatus status = context.getTarget().getStatus();
        // bekit专门提供了EnumUtils工具类，用于在节点名称与状态枚举之间进行转换
        return EnumUtils.getCamelCaseName(status);
    }
}
