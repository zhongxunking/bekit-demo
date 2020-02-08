/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:43 创建
 */
package demo.flow.transfer;

import demo.dao.TransferDao;
import demo.entity.Transfer;
import demo.enums.TransferStatus;
import lombok.extern.slf4j.Slf4j;
import org.bekit.common.util.EnumUtils;
import org.bekit.flow.annotation.listener.*;
import org.bekit.flow.engine.FlowContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 转账交易流程监听器
 */
@TheFlowListener(flow = "transferFlow")    // @TheFlowListener是只监听一个特定流程的监听器
@Slf4j
public class TransferFlowListener {
    @Autowired
    private TransferDao transferDao;

    @ListenFlowStart
    public void listenFlowStart(FlowContext<Transfer> context) {
        // 仅用于演示，可根据实际场景选择是否监听本事件
        log.info("流程[transfer]开始执行");
    }

    @ListenExecutingNode
    public void listenExecutingNode(String node, FlowContext<Transfer> context) {
        // 仅用于演示，可根据实际场景选择是否监听本事件
        log.info("流程[transfer]即将执行节点[{}]", node);
    }

    @ListenDecidedNode
    public void listenDecidedNode(String node, FlowContext<Transfer> context) {
        // 仅用于演示，可根据实际场景选择是否监听本事件
        log.info("流程[transfer]选择节点[{}]作为下一个要执行的节点", node);
    }

    // 监听节点选择事件（主要作用就是用来修改目标对象的状态）
    // 入参node表示被选择的状态节点，context是流程上下文
    @ListenDecidedStateNode
    public void listenDecidedStateNode(String node, FlowContext<Transfer> context) {
        // bekit专门提供了EnumUtils工具类，用于在节点名称与状态枚举之间进行转换
        TransferStatus status = EnumUtils.getEnum(TransferStatus.class, node);
        log.info("节点[{}]是状态节点，更新交易记录状态为[{}]", node, status);
        // 根据被选择的节点修改目标对象到对应的状态，
        Transfer transfer = context.getTarget();
        transfer.setStatus(status);
        transferDao.save(transfer);
    }

    // 监听流程异常事件，当流程发生任何异常时都会发送这个事件
    @ListenFlowException
    public void listenFlowException(Throwable throwable, FlowContext<Transfer> context) {
        // 仅用于演示，可根据实际场景选择是否监听本事件
        log.info("流程[transfer]执行过程中发生异常：{}", throwable.getMessage());
        // 本监听方法的作用就是在流程发生异常时可以做一些措施。

        // 当一个流程被某些原因中断时，我们可以通过定时任务扫描表将中间状态的交易查询出来，继续执行交易。
        // 这是个好方法，但是存在一个问题，当数据量过大时，定时任务的间隔时间不好确定，
        // 间隔时间短了就怕前面一个定时任务还没执行完第二个定时任务就开始执行了，间隔时间长了中间状态的交易就会长时间得不到执行

        // 一个更好的方式就是将发生异常的交易发送到MQ的延迟队列，同时本系统监听MQ消息，当监听到这种数据是就继续执行这笔交易
        // 也就是你可以在本方法里实现将数据发送到MQ
    }

    @ListenFlowEnd
    public void listenFlowEnd(FlowContext<Transfer> context) {
        // 仅用于演示，可根据实际场景选择是否监听本事件
        log.info("流程[transfer]执行结束");
    }
}
