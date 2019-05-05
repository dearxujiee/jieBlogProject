package wang.dreamland.www.service;

import wang.dreamland.www.entity.UserInfo;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0005 20:59
 */
public interface UserInfoService {

    /**
     * 根据用户id查找用户详细信息
     * @param id
     * @return
     */
    UserInfo findByUid(Long id);

    /**
     * 更新用户详细信息
     * @param userInfo
     */
    void update(UserInfo userInfo);

    /**
     * 添加用户详细新
     * @param userInfo
     */
    void add(UserInfo userInfo);

}
