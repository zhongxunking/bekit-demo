/* 
 * 作者：钟勋 (e-mail:zhongxun@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 21:45 创建
 */
package demo.result;


import demo.enums.Status;

/**
 *
 */
public abstract class AbstractResult {

    private Status status;

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
