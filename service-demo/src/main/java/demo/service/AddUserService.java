/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 21:41 创建
 */
package demo.service;

import demo.enums.Status;
import demo.exception.DemoException;
import demo.order.AddUserOrder;
import demo.result.AddUserResult;
import lombok.extern.slf4j.Slf4j;
import org.bekit.service.annotation.service.ServiceAfter;
import org.bekit.service.annotation.service.ServiceBefore;
import org.bekit.service.annotation.service.ServiceExecute;
import org.bekit.service.engine.ServiceContext;

import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * 转账交易服务
 */
@org.bekit.service.annotation.service.Service(enableTx = true)
@Slf4j
public class AddUserService {
    private static final Random RANDOM = new Random();

    // 服务前置处理（可以进行业务参数检查，比如校验账号存不存），本方法执行前不会主动开始事务
    @ServiceBefore
    public void before(ServiceContext<AddUserOrder, AddUserResult> context) {
        log.info("执行AddUserService.before");
    }

    // 服务执行，真正开始执行业务（如果@Service的enableTx=true，则会主动开启事务，本方法执行结束后会提交事务）
    @ServiceExecute
    public void execute(ServiceContext<AddUserOrder, AddUserResult> context) throws TimeoutException {
        log.info("执行AddUserService.execute");
        AddUserOrder order = context.getOrder();
        AddUserResult result = context.getResult();

        // 这里以随机数模拟调用账务系统的结果
        int i = RANDOM.nextInt(100);
        if (i < 50) {
            log.info("模拟50%概率执行成功");
            result.setUserId(123);
        } else if (i < 80) {
            log.info("模拟30%概率执行失败");
            throw new DemoException(Status.FAIL, String.format("用户[%s]已存在", order.getUsername()));
        } else {
            throw new TimeoutException("模拟20%概率执行超时");
        }
    }

    // 服务后置处理（本方法执行前不会主动开始事务）
    @ServiceAfter
    public void after(ServiceContext<AddUserOrder, AddUserResult> context) {
        log.info("执行AddUserService.after");
    }
}
