/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-07 00:34 创建
 */
package demo.flow.transfer;

import demo.entity.Transfer;
import demo.enums.ResultStatus;
import org.bekit.flow.annotation.flow.*;

/**
 * 转账交易流程
 */
@Flow
public class TransferFlow {

    // 付款人下账节点  @StartNode是开始节点，一个流程中必须存在一个开始节点
    @StartNode(processor = "downPayerProcessor")    // processor属性表示本节点对应的处理器，不填表示本节点没有处理器
    public String downPayer(ResultStatus resultStatus) {    // 处理器的返回结果会以入参形式传进来
        // 本方法的作用是：根据处理器的返回结果，选择下一个流程节点名称
        switch (resultStatus) {
            case SUCCESS:
                return "upPayee";
            case FAIL:
                return "fail";
            case PROCESS:
                return null;    // 当返回null时，流程引擎会中断流程的执行
            default:
                throw new RuntimeException("处理器返回结果不合法");
        }
    }

    // 收款人上账节点  @StateNode是状态节点，表示在本节点执行前会先提交事务然后开启新事务并调用流程事务锁住目标对象。
    @StateNode(processor = "upPayeeProcessor")
    public String upPayee(ResultStatus resultStatus) {
        switch (resultStatus) {
            case SUCCESS:
                return "success";
            case FAIL:
                return "restorePayer";
            case PROCESS:
                return null;    // 和上面情况一样需要返回null，因为处理器返回结果是处理中，需要暂停执行，直到得到明确结果后才能跳转到下个节点
            default:
                throw new RuntimeException("处理器返回结果不合法");
        }
    }

    // 恢复付款人资金节点，付款人下账成功，但是收款人上账失败了，也就是说这笔转账交易失败，
    // 但是需要把付款人的钱恢复回去，并且必须得保证恢复成功
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

    // 成功节点
    @EndNode    // @EndNode表示结束节点，一个流程的结束节点至少有一个
    public void success() {
    }

    // 失败节点
    @EndNode
    public void fail() {
    }

    // 目标对象映射方法，需要根据目标对象的状态等信息映射到一个流程节点，流程引擎将从这个节点进行执行
    @TargetMapping
    public String targetMapping(Transfer transfer) {    // 入参就是目标对象
        switch (transfer.getStatus()) {
            case DOWN_PAYER:
                return "downPayer";
            case UP_PAYEE:
                return "upPayee";
            case RESTORE_PAYER:
                return "restorePayer";
            case SUCCESS:
                return "success";
            case FAIL:
                return "fail";
            default:
                throw new RuntimeException("非法的转账状态");
        }
    }

}
