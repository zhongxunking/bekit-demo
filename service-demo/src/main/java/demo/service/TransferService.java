/* 
 * 作者：钟勋 (e-mail:zhongxun@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 21:41 创建
 */
package demo.service;

import demo.entity.Transfer;
import demo.enums.Status;
import demo.enums.TransferStatus;
import demo.exception.DemoException;
import demo.order.TransferOrder;
import demo.result.TransferResult;
import demo.utils.OID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import top.bekit.flow.FlowEngine;
import top.bekit.service.annotation.service.Service;
import top.bekit.service.annotation.service.ServiceCheck;
import top.bekit.service.annotation.service.ServiceExecute;
import top.bekit.service.engine.ServiceContext;

/**
 *
 */
@Service
public class TransferService {
    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    @Autowired
    private FlowEngine flowEngine;

    @ServiceCheck
    public void serviceCheck(ServiceContext<TransferOrder, TransferResult> serviceContext) {
        logger.info("执行TransferService.serviceCheck");
    }

    @ServiceExecute
    public void serviceExecute(ServiceContext<TransferOrder, TransferResult> serviceContext) {
        Transfer transfer = flowEngine.insertTargetAndStart("transferFlow", buildTransfer(serviceContext.getOrder()), null);
        switch (transfer.getStatus()) {
            case SUCCESS:
                break;
            case FAIL:
                throw new DemoException(Status.FAIL, "执行失败");
            default:
                throw new DemoException(Status.PROCESS, "处理中");
        }
    }

    private Transfer buildTransfer(TransferOrder order) {
        Transfer transfer = new Transfer();
        transfer.setOrderNo(order.getOrderNo());
        transfer.setBizNo(OID.newId());
        transfer.setPayerAccountNo(order.getPayerAccountNo());
        transfer.setPayeeAccountNo(order.getPayeeAccountNo());
        transfer.setAmount(order.getAmount());
        transfer.setStatus(TransferStatus.DOWN_PAYER);

        return transfer;
    }
}
