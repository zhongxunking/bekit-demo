/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-06 23:23 创建
 */
package demo.event;

/**
 * 删除用户事件
 */
public class DeleteUserEvent {
    // 用户id
    private String userId;
    // 用户名
    private String userName;

    public DeleteUserEvent(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return String.format("DeleteUserEvent{userId=%s,userName=%s}", userId, userName);
    }
}
