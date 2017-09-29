/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-06 23:28 创建
 */
package demo.listener;

import demo.event.AddUserEvent;
import demo.event.DeleteUserEvent;
import org.bekit.event.annotation.BizListener;
import org.bekit.event.annotation.Listen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户监听器1
 */
@BizListener(priority = 1)
public class UserListener1 {
    private static final Logger logger = LoggerFactory.getLogger(UserListener1.class);

    @Listen
    public void listenAddUserEvent(AddUserEvent event) {    // 方法名可以任意取，这里取名为listenAddUserEvent是为了更贴合业务含义，你愿意的话取成abc也可以
        // 这里为了展示方便，只打印了日志，在真实业务场景中你肯定还会做其他事:)
        logger.info("UserListener1监听到添加用户事件：{}", event);
    }

    // @Listen的属性priorityAsc=false表示按照优先级倒叙排序，结合@BizListener(priority = 1)中priority属性，表示删除用户事件发生后本监听方法倒数第一个执行
    @Listen(priorityAsc = false)
    public void listenDeleteUserEvent(DeleteUserEvent event) {
        logger.info("UserListener1监听到删除用户事件：{}", event);
    }

}
