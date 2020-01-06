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
import lombok.extern.slf4j.Slf4j;
import org.bekit.event.annotation.DomainListener;
import org.bekit.event.annotation.Listen;
import org.bekit.event.listener.PriorityType;

/**
 * 用户监听器2
 */
@DomainListener(priority = 2)
@Slf4j
public class UserListener2 {
    @Listen
    public void listenAddUserEvent(AddUserEvent event) {    // 方法名可以任意取，这里取名为listenAddUserEvent是为了更贴合业务含义，你愿意的话取成abc也可以
        // 这里为了方便展示，只打印了日志，在真实业务场景中你肯定会做其他事:)
        log.info("UserListener2监听到添加用户事件：{}", event);
    }

    // @Listen的属性priorityType = PriorityType.DESC表示按照优先级倒叙排序，
    // 结合@BizListener(priority = 2)中priority属性，表示删除用户事件发生后本监听方法倒数第二个执行
    @Listen(priorityType = PriorityType.DESC)
    public void listenDeleteUserEvent(DeleteUserEvent event) {
        log.info("UserListener2监听到删除用户事件：{}", event);
    }
}
