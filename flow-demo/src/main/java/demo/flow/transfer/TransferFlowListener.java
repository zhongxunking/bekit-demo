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
import top.bekit.flow.annotation.listener.ListenNodeDecide;
import top.bekit.flow.annotation.listener.TheFlowListener;
import top.bekit.flow.engine.TargetContext;

/**
 *
 */
@TheFlowListener(flow = "transferFlow")
public class TransferFlowListener {
    private static final Logger logger = LoggerFactory.getLogger(TransferFlowListener.class);

    @Autowired
    private TransferDao transferDao;

    @ListenNodeDecide
    public void listenNodeDecide(String node, TargetContext<Transfer> targetContext) {
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
                return;
        }
        transfer.setStatus(status);
        transferDao.save(transfer);
    }

    @ListenFlowException
    public void listenFlowException(Throwable throwable, TargetContext<Transfer> targetContext) {
        logger.info("转账执行过程中发生异常：", throwable);
    }

}
