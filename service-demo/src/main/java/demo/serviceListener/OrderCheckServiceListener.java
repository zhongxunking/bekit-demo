/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 22:15 创建
 */
package demo.serviceListener;

import demo.order.AbstractOrder;
import demo.result.AbstractResult;
import org.bekit.event.annotation.listener.Listen;
import org.bekit.service.annotation.listener.ServiceListener;
import org.bekit.service.engine.ServiceContext;
import org.bekit.service.event.ServiceApplyEvent;

/**
 * order校验
 */
@ServiceListener(priority = 1)      // 服务监听器，监听的是所有服务的事件
public class OrderCheckServiceListener {

    @Listen
    public void listenServiceApplyEvent(ServiceApplyEvent event) {      // 服务申请事件
        ServiceContext<AbstractOrder, AbstractResult> serviceContext = event.getServiceContext();
        AbstractOrder order = serviceContext.getOrder();
        // 可以对order的入参校验，比如JSR303校验
    }

}
