/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 02:33 创建
 */
package demo.flow.modifyAccount;

import demo.entity.ModifyAccount;
import org.bekit.common.util.EnumUtils;
import org.bekit.flow.annotation.mapper.MappingNode;
import org.bekit.flow.annotation.mapper.TheFlowMapper;
import org.bekit.flow.engine.FlowContext;

/**
 * 修改账务流程事务
 */
@TheFlowMapper(flow = "modifyAccountFlow")
public class ModifyAccountMapper {

    @MappingNode
    public String targetMapping(FlowContext<ModifyAccount> context) {
        return EnumUtils.getCamelCaseName(context.getTarget().getStatus());
    }
}
