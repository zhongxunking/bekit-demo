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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import top.bekit.flow.annotation.listener.ListenFlowException;
import top.bekit.flow.annotation.listener.ListenNodeDecided;
import top.bekit.flow.annotation.listener.TheFlowListener;
import top.bekit.flow.engine.TargetContext;

/**
 * 转账交易流程监听器
 */
@TheFlowListener(flow = "transferFlow")    // @TheFlowListener是只监听一个特定流程的监听器
public class TransferFlowListener {
    private static final Logger logger = LoggerFactory.getLogger(TransferFlowListener.class);

    @Autowired
    private TransferDao transferDao;

    @ListenNodeDecided   // 监听节点选择事件（主要作用就是用来修改目标对象的状态的）
    public void listenNodeDecide(String node, TargetContext<Transfer> targetContext) {  // 入参node表示被选择的节点，targetContext是目标上下文
        // 根据被选择的节点修改目标对象到对应的状态，
        Transfer transfer = targetContext.getTarget();
        TransferStatus status;
        switch (node) {
            case "downPayer":
                status = TransferStatus.DOWN_PAYER;
                break;
            case "upPayee":
                status = TransferStatus.UP_PAYEE;
                break;
            case "restorePayer":
                status = TransferStatus.RESTORE_PAYER;
                break;
            case "success":
                status = TransferStatus.SUCCESS;
                break;
            case "fail":
                status = TransferStatus.FAIL;
                break;
            default:
                throw new RuntimeException("监听到非法的节点被选择");
        }
        transfer.setStatus(status);
        transferDao.save(transfer);
    }

    @ListenFlowException    // 监听流程异常事件，当流程发生任何异常时都会发送这个事件
    public void listenFlowException(Throwable throwable, TargetContext<Transfer> targetContext) {
        logger.info("转账执行过程中发生异常：{}", throwable.getMessage());
        // 本监听方法的作用就是在流程发生异常时可以做一些措施。

        // 当一个流程被某些原因中断时，我们可以通过定时任务扫描表将中间状态的交易查询出来，继续执行交易。
        // 这是个好方法，但是存在一个问题，当数据量过大时，定时任务的间隔时间不好确定，
        // 间隔时间短了就怕前面一个定时任务还没执行完第二个定时任务就开始执行了，间隔时间长了中间状态的交易就会长时间得不到执行

        // 一个更好的方式就是将发生异常的交易发送到MQ的延迟队列，同时本系统监听MQ消息，当监听到这种数据是就继续执行这笔交易
        // 也就是你可以在本方法里实现将数据发送到MQ
    }

}
