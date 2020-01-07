/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 22:06 创建
 */
package demo.serviceListener;

import demo.enums.Status;
import demo.exception.DemoException;
import demo.result.AbstractResult;
import lombok.extern.slf4j.Slf4j;
import org.bekit.event.annotation.Listen;
import org.bekit.service.annotation.listener.ServiceListener;
import org.bekit.service.engine.ServiceContext;
import org.bekit.service.event.ServiceApplyEvent;
import org.bekit.service.event.ServiceExceptionEvent;
import org.bekit.service.event.ServiceFinishEvent;

/**
 * result处理
 */
@ServiceListener(priority = 2)
@Slf4j
public class ProcessResultServiceListener {

    @Listen
    public void listenServiceApplyEvent(ServiceApplyEvent event) {
        // 可以对result进行初始化
        ServiceContext<Object, AbstractResult> serviceContext = event.getContext();
        AbstractResult result = serviceContext.getResult();
        result.setStatus(Status.SUCCESS);
        result.setMessage("成功");
    }

    @Listen
    public void listenServiceFinishEvent(ServiceFinishEvent event) {  // 监听服务完成事件
    }

    @Listen
    public void listenServiceExceptionEvent(ServiceExceptionEvent event) {  // 监听服务异常事件
        // 可以根据异常类型对result设值，比如：如果是自己抛的异常就可以根据异常里的值对result设值，如果不是自己抛的异常，则返回给上层系统处理中，因为这时候你自己也不知道执行结果是什么
        ServiceContext<Object, AbstractResult> serviceContext = event.getContext();
        AbstractResult result = serviceContext.getResult();

        Throwable throwable = event.getThrowable();
        if (throwable instanceof DemoException) {
            DemoException demoException = (DemoException) throwable;
            result.setStatus(demoException.getStatus());
            result.setMessage(demoException.getDescription());
        } else {
            result.setStatus(Status.PROCESSING);
            result.setMessage("处理中");
            log.error("服务执行发生异常", throwable);
        }
    }
}
