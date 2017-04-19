/* 
 * 作者：钟勋 (e-mail:zhongxun@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-06 22:15 创建
 */
package demo;

import demo.entity.Transfer;
import demo.enums.TransferStatus;
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
 * <p>
 * （为方便展示，我们设计一个简单的转账交易场景：A转账给B（第一步：付款人A下帐，第二步：收款人B上账），同时本系统是交易系统，
 * 而用户的帐是记在账务系统里的，也就是说上面两步是交易系统通过远程调用账务系统完成的，也就是会出现调用超时情况；
 * 同时假设每一步操作账务系统都可能返回成功、失败、处理中这三种情况。如果第一步成功，而第二步失败了，那么就需要将付款人的账恢复回去，而且必须得成功。
 * 本示例展示通过使用流程引擎控制每一步的执行，最后达到业务最终结果（要么全成功，要么全失败————数据一致性））
 * <p>
 * <p>
 * （题外话：有的账务系统本身就提供转账功能，可以直接通过数据库事务保证数据一致性，但是像一些复合型转账，账务系统就不能再提供了，
 * 因为账务系统是个底层核心系统，不应该夹杂这样的业务属性，也就是说像复合型转账这类业务还是需要一个上层系统来保证数据的最终一致性）
 * <p>
 * （流程引擎并不能帮你保证数据的一致性，一致性需要你自己合理的设计流程，
 * 流程引擎能帮你的是：简化流程的定义、简化流程的调度，增加流程的可复用性）
 * <p>
 * 重点看：TransferFlow、TransferFlowListener、TransferFlowTx、DownPayerProcessor
 */
@SpringBootApplication
public class FlowDemoMain {
    private static final Logger logger = LoggerFactory.getLogger(FlowDemoMain.class);
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(FlowDemoMain.class, args);
        // 流程引擎从spring容器获取（可以通过@Autowired获取）
        FlowEngine flowEngine = applicationContext.getBean(FlowEngine.class);
        for (int i = 0; i < 1; i++) {
            try {
                Transfer transfer = flowEngine.insertTargetAndStart("transferFlow", buildTransfer(), null);
                logger.info("转账交易执行结果：{}", transfer);
            } catch (Throwable e) {
                logger.error("转账交易发生异常：{}", e.getMessage());
            }
        }
    }

    private static Transfer buildTransfer() {
        Transfer transfer = new Transfer();
        transfer.setOrderNo(OID.newId());
        transfer.setBizNo(OID.newId());
        transfer.setPayerAccountNo(OID.newId());    // 为了方便演示，直接生成账号
        transfer.setPayeeAccountNo(OID.newId());    // 为了方便演示，直接生成账号
        transfer.setAmount((long) RANDOM.nextInt(10000));   // 金额随机生成
        transfer.setStatus(TransferStatus.DOWN_PAYER);

        return transfer;
    }
}
