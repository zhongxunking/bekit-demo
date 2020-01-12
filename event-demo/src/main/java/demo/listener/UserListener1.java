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
 * 用户监听器1
 */
@DomainListener(priority = 1)
@Slf4j
public class UserListener1 {
    // 监听AddUserEvent事件
    // 结合@DomainListener(priority = 1)中priority属性，表示删除用户事件发生后本监听方法第一个执行
    // 方法名可以任意取，这里取名为listenAddUserEvent是为了更贴合业务含义，你愿意的话取成abc也可以
    @Listen
    public void listenAddUserEvent(AddUserEvent event) {
        log.info("UserListener1监听到添加用户事件：{}", event);
        // TODO 具体业务代码
    }

    // 监听DeleteUserEvent事件
    // @Listen的属性priorityType = PriorityType.DESC表示按照优先级倒叙排序
    // 结合@DomainListener(priority = 1)中priority属性，表示删除用户事件发生后本监听方法倒数第一个执行
    @Listen(priorityType = PriorityType.DESC)
    public void listenDeleteUserEvent(DeleteUserEvent event) {
        log.info("UserListener1监听到删除用户事件：{}", event);
        // TODO 具体业务代码
    }
}
