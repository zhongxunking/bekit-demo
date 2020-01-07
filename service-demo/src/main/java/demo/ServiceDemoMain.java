/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-06 22:15 创建
 */
package demo;

import demo.order.AddUserOrder;
import demo.result.AddUserResult;
import lombok.extern.slf4j.Slf4j;
import org.bekit.service.ServiceEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 服务引擎使用展示
 * （请在application.properties修改数据库配置信息）
 * <p>
 * 本demo展示使用服务引擎执行转账交易服务
 * 服务引擎可以不用通过流程引擎来执行业务，如果交易、订单类场景建议结合流程引擎一起使用，
 * 如果服务引擎和流程引擎一起使用，建议服务引擎不用开启事务，事务由流程引擎控制
 * <p>
 * 重点看：AddUserService、OrderCheckServiceListener、ProcessResultServiceListener
 */
@SpringBootApplication
@Slf4j
public class ServiceDemoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ServiceDemoMain.class, args);
        // 服务引擎从spring容器中获取（可以通过@Autowired获取）
        ServiceEngine serviceEngine = applicationContext.getBean(ServiceEngine.class);
        AddUserResult addUserResult = serviceEngine.execute("addUserService", buildAddUserOrder());
        log.info("服务执行结果：{}", addUserResult);

        log.info("===================service-demo演示结束===================");
    }

    private static AddUserOrder buildAddUserOrder() {
        AddUserOrder order = new AddUserOrder();
        order.setUsername("zhangsan");
        order.setAge(20);

        return order;
    }
}
