/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-06 22:15 创建
 */
package demo;

import demo.event.AddUserEvent;
import demo.event.DeleteUserEvent;
import org.bekit.event.EventPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 事件总线使用演示
 * 重点看：UserListener1、UserListener2
 */
@SpringBootApplication
public class EventDemoMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(EventDemoMain.class, args);
        // 时间发布器从spring容器获取（可以通过@Autowired获取）
        EventPublisher eventPublisher = applicationContext.getBean(EventPublisher.class);
        // 发布添加用户事件，对应的监听器会受到事件
        eventPublisher.publish(new AddUserEvent("123", "张三"));
        // 发布删除用户事件，对应的监听器会受到事件
        eventPublisher.publish(new DeleteUserEvent("123", "张三"));
    }
}
