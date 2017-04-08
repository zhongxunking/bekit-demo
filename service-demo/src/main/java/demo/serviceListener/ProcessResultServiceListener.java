/* 
 * 作者：钟勋 (e-mail:zhongxun@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 22:06 创建
 */
package demo.serviceListener;

import demo.enums.Status;
import demo.exception.DemoException;
import demo.order.AbstractOrder;
import demo.result.AbstractResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.bekit.event.annotation.listener.Listen;
import top.bekit.service.annotation.listener.ServiceListener;
import top.bekit.service.engine.ServiceContext;
import top.bekit.service.event.ServiceApplyEvent;
import top.bekit.service.event.ServiceExceptionEvent;
import top.bekit.service.event.ServiceFinishEvent;

/**
 *
 */
@ServiceListener(priority = 2)
public class ProcessResultServiceListener {
    private static final Logger logger = LoggerFactory.getLogger(ProcessResultServiceListener.class);

    @Listen
    public void listenServiceApplyEvent(ServiceApplyEvent event) {
        ServiceContext<AbstractOrder, AbstractResult> serviceContext = event.getServiceContext();
        AbstractResult result = serviceContext.getResult();
        result.setStatus(Status.SUCCESS);
        result.setDescription("执行成功");
    }

    @Listen
    public void listenServiceFinishEvent(ServiceFinishEvent event) {

    }

    @Listen
    public void listenServiceExceptionEvent(ServiceExceptionEvent event) {
        ServiceContext<AbstractOrder, AbstractResult> serviceContext = event.getServiceContext();
        AbstractResult result = serviceContext.getResult();

        Throwable throwable = event.getTargetException();
        if (throwable instanceof DemoException) {
            DemoException demoException = (DemoException) throwable;
            result.setStatus(demoException.getStatus());
            result.setDescription(demoException.getDescription());
        } else {
            result.setStatus(Status.PROCESS);
            result.setDescription("处理中");
            logger.error("服务执行发生异常：", throwable);
        }
    }

}
