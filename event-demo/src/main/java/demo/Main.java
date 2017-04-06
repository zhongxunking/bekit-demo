/* 
 * 作者：钟勋 (e-mail:zhongxun@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-06 22:15 创建
 */
package demo;

import demo.event.AddUserEvent;
import demo.event.DeleteUserEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import top.bekit.event.EventPublisher;

/**
 * 事件总线使用演示
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        // 事件发布器可以直接从spring容器中获取，这里为了方便展示，直接调用spring容器获取，大家使用的时候可以通过@Autowired注入方式得到
        EventPublisher eventPublisher = applicationContext.getBean(EventPublisher.class);
        // 发布添加用户事件，对应的监听器会受到事件
        eventPublisher.publish(new AddUserEvent("123", "张三"));
        // 发布删除用户事件，对应的监听器会受到事件
        eventPublisher.publish(new DeleteUserEvent("123", "张三"));
    }
}
