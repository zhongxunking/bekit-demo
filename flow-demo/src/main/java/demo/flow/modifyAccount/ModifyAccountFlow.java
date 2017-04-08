/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-07 23:36 创建
 */
package demo.flow.modifyAccount;

import demo.entity.ModifyAccount;
import demo.enums.ModifyAccountType;
import demo.enums.ResultStatus;
import top.bekit.flow.annotation.flow.*;
import top.bekit.flow.engine.TargetContext;

/**
 * 修改账务流程
 */
@Flow
public class ModifyAccountFlow {

    @StartNode(processor = "modifyProcessor")
    public String modify(ResultStatus resultStatus, TargetContext<ModifyAccount> targetContext) {
        ModifyAccount modifyAccount = targetContext.getTarget();
        switch (resultStatus) {
            case SUCCESS:
                return "success";
            case FAIL:
                if (modifyAccount.getType() == ModifyAccountType.NORMAL) {
                    return "fail";
                } else {
                    return "generateRefOrderNo";
                }
            case PROCESS:
                return null;
            default:
                throw new RuntimeException("处理器返回结果不合法");
        }
    }

    @WaitNode(processor = "generateRefOrderNoProcessor")
    public String generateRefOrderNo(ResultStatus resultStatus) {
        switch (resultStatus) {
            case SUCCESS:
                return "modify";
            default:
                throw new RuntimeException("处理器返回结果不合法");
        }
    }

    @EndNode
    public void success() {
    }

    @EndNode
    public void fail() {
    }

    @TargetMapping
    public String targetMapping(ModifyAccount modifyAccount) {
        switch (modifyAccount.getStatus()) {
            case MODIFY:
                return "modify";
            case GENERATE_REF_ORDER_NO:
                return "generateRefOrderNo";
            case SUCCESS:
                return "success";
            case FAIL:
                return "fail";
            default:
                throw new RuntimeException("非法的修改账务状态");
        }
    }

}
