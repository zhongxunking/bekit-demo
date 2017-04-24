/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 21:45 创建
 */
package demo.result;


import demo.enums.Status;

/**
 * 抽象result，定义公共的返回结果
 */
public abstract class AbstractResult {
    // 结果状态
    private Status status;
    // 结果描述
    private String description;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("status=%s,description=%s", status, description);
    }
}
