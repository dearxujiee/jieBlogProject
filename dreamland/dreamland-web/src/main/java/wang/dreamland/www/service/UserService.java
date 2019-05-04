package wang.dreamland.www.service;

import wang.dreamland.www.entity.User;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:02
 */
public interface UserService {

    /*用户注册*/
    public int regist(User user);

    /*用户登录*/
    public User login(String name, String password);

    /*根据email查询用户*/
    public User findByEmail(String email);

    /*根据电话号查询用户*/
    public User findByPhone(String phone);

    /*根据主表id查询实体*/
    public User findById(Long id);

    /*删除实体*/
    public void deleteByEmail(String email);

    /*更新实体*/
    public void update(User user);

}
