/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 21:45 创建
 */
package demo.result;


import demo.enums.Status;
import lombok.Getter;
import lombok.Setter;

/**
 * 抽象result，定义公共的返回结果
 */
@Getter
@Setter
public abstract class AbstractResult {
    // 结果状态
    private Status status;
    // 结果描述
    private String message;
}
