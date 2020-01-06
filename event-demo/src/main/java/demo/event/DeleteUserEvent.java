/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-06 23:23 创建
 */
package demo.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 删除用户事件
 */
@AllArgsConstructor
@Getter
public class DeleteUserEvent {
    // 用户id
    private final String userId;
    // 用户名
    private final String userName;

    @Override
    public String toString() {
        return String.format("DeleteUserEvent{userId=%s,userName=%s}", userId, userName);
    }
}
