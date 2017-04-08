/* 
 * 作者：钟勋 (e-mail:zhongxun@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 22:15 创建
 */
package demo.serviceListener;

import demo.order.AbstractOrder;
import demo.result.AbstractResult;
import top.bekit.event.annotation.listener.Listen;
import top.bekit.service.annotation.listener.ServiceListener;
import top.bekit.service.engine.ServiceContext;
import top.bekit.service.event.ServiceApplyEvent;

/**
 *
 */
@ServiceListener(priority = 1)
public class OrderCheckServiceListener {

    @Listen
    public void listenServiceApplyEvent(ServiceApplyEvent event) {
        ServiceContext<AbstractOrder, AbstractResult> serviceContext = event.getServiceContext();
        AbstractOrder order = serviceContext.getOrder();
        // 校验order
    }

}
