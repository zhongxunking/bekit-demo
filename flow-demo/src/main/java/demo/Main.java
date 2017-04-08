/* 
 * 作者：钟勋 (e-mail:zhongxun@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-06 22:15 创建
 */
package demo;

import demo.entity.Transfer;
import demo.utils.OID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import top.bekit.flow.FlowEngine;

import java.util.Random;

/**
 * 流程引擎使用展示
 * （请在application.properties修改数据库配置信息）
 */
@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        FlowEngine flowEngine = applicationContext.getBean(FlowEngine.class);
        Transfer transfer = flowEngine.insertTargetAndStart("transferFlow", buildTransfer(), null);
        logger.info("转账交易执行结果：{}", transfer);
    }

    private static Transfer buildTransfer() {
        Transfer transfer = new Transfer();
        transfer.setBizNo(OID.newId());
        transfer.setPayerAccountNo(OID.newId());
        transfer.setPayeeAccountNo(OID.newId());
        transfer.setAmount((long) RANDOM.nextInt(10000));

        return transfer;
    }
}
