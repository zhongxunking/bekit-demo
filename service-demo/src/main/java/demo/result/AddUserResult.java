/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2020-01-07 19:10 创建
 */
package demo.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 新增用户result
 */
@Getter
@Setter
public class AddUserResult extends AbstractResult {
    // 用户id
    private int userId;

    @Override
    public String toString() {
        return String.format("AddUserResult{status=%s,message=%s,userId=%d}", getStatus(), getMessage(), userId);
    }
}
