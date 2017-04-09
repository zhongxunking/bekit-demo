/* 
 * 作者：钟勋 (e-mail:zhongxun@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-06 22:15 创建
 */
package demo;

import demo.order.TransferOrder;
import demo.result.TransferResult;
import demo.utils.OID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import top.bekit.service.ServiceEngine;

import java.util.Random;

/**
 * 服务引擎使用展示
 * （请在application.properties修改数据库配置信息）
 * <p>
 * 本demo展示使用服务引擎执行转账交易服务
 *
 * 重点看：TransferService、ProcessResultServiceListener、OrderCheckServiceListener
 */
@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        // 服务引擎从spring容器中获取（可以通过@Autowired获取）
        ServiceEngine serviceEngine = applicationContext.getBean(ServiceEngine.class);
        for (int i = 0; i < 1; i++) {
            TransferResult result = serviceEngine.execute("transferService", buildTransferOrder(), new TransferResult());
            logger.info("服务执行结果：{}", result);
        }
    }

    private static TransferOrder buildTransferOrder() {
        TransferOrder order = new TransferOrder();
        order.setOrderNo(OID.newId());
        order.setPayerAccountNo(OID.newId());
        order.setPayeeAccountNo(OID.newId());
        order.setAmount((long) RANDOM.nextInt(10000));

        return order;
    }

}
