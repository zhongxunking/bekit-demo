/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-07 00:34 创建
 */
package demo.flow.transfer;

import demo.enums.ResultStatus;
import top.bekit.flow.annotation.flow.EndNode;
import top.bekit.flow.annotation.flow.Flow;
import top.bekit.flow.annotation.flow.StartNode;
import top.bekit.flow.annotation.flow.StateNode;

/**
 * 转账交易流程
 * （为方便展示，我们设计一个简单的转账交易场景：A转账给B（第一步：付款人A下帐，第二步：收款人B上账），同时本系统是交易系统，
 * 而用户的帐是记在账务系统里的，也就是说上面两步是交易系统通过远程调用账务系统完成的，也就是会出现调用超时情况；
 * 同时假设每一步操作账务系统都可能返回成功、失败、处理中这三种情况。
 * 本示例展示通过使用流程引擎控制每一步的执行，最后达到业务最终结果（要么全成功，要么全失败————数据一致性））
 * <p>
 * （题外话：有的账务系统本身就提供转账功能，可以直接通过数据库事务保证数据一致性，但是像一些复合型转账，账务系统就不能再提供了，
 * 因为账务系统是个底层核心系统，不应该夹杂这样的业务属性，也就是说像复合型转账这类业务还是需要一个上层系统来保证数据的最终一致性）
 * <p>
 * （流程引擎并不能帮你保证数据的一致性，一致性需要你自己合理的设计流程，
 * 流程引擎能帮你的是：简化流程的定义、简化流程的调度，增加流程的可复用性）
 */
@Flow
public class TransferFlow {

    @StartNode
    public String start() {
        return "downPayer";
    }

    @StateNode(processor = "downPayerProcessor")
    public String downPayer(ResultStatus resultStatus) {
        switch (resultStatus) {
            case SUCCESS:
                return "upPayee";
            case FAIL:
                return "fail";
            case PROCESS:
                return null;
            default:
                throw new RuntimeException("处理器返回结果不合法");
        }
    }

    @StateNode(processor = "upPayeeProcessor")
    public String upPayee(ResultStatus resultStatus) {
        switch (resultStatus) {
            case SUCCESS:
                return "success";
            case FAIL:
                return "restorePayer";
            case PROCESS:
                return null;
            default:
                throw new RuntimeException("处理器返回结果不合法");
        }
    }

    @StateNode(processor = "restorePayerProcessor")
    public String restorePayer(ResultStatus resultStatus) {
        switch (resultStatus) {
            case SUCCESS:
                return "fail";
            case PROCESS:
                return null;
            default:
                throw new RuntimeException("处理器返回结果不合法");
        }
    }

    @StateNode
    public String success() {
        return "end";
    }

    @StateNode
    public String fail() {
        return "end";
    }

    @EndNode
    public void end() {
    }

}
