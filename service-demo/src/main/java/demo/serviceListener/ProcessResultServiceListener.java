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
 * result处理
 */
@ServiceListener(priority = 2)
public class ProcessResultServiceListener {
    private static final Logger logger = LoggerFactory.getLogger(ProcessResultServiceListener.class);

    @Listen
    public void listenServiceApplyEvent(ServiceApplyEvent event) {
        // 可以对result进行初始化
        ServiceContext<AbstractOrder, AbstractResult> serviceContext = event.getServiceContext();
        AbstractResult result = serviceContext.getResult();
        result.setStatus(Status.SUCCESS);
        result.setDescription("执行成功");
    }

    @Listen
    public void listenServiceFinishEvent(ServiceFinishEvent event) {  // 监听服务完成事件

    }

    @Listen
    public void listenServiceExceptionEvent(ServiceExceptionEvent event) {  // 监听服务异常事件
        // 可以根据异常类型对result设值，比如：如果是自己抛的异常就可以根据异常里的值对result设值，如果不是自己抛的异常，则返回给上层系统处理中，因为这时候你自己也不知道执行结果是什么
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
