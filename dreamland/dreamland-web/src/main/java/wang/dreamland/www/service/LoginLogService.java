package wang.dreamland.www.service;

import wang.dreamland.www.entity.LoginLog;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:10
 */
public interface LoginLogService {
    /*根据主表id查询实体*/
    public LoginLog findById(Long id);

    /*删除实体*/
    public void deleteById(Long id);

    /*更新实体*/
    public void update(LoginLog loginLog);
}
