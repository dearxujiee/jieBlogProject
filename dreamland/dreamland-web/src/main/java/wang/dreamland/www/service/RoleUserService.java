package wang.dreamland.www.service;

import wang.dreamland.www.entity.RoleUser;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:13
 */
public interface RoleUserService {
    /*根据主表id查询实体*/
    public RoleUser findById(Long id);

    /*删除实体*/
    public void deleteById(Long id);

    /*更新实体*/
    public void update(RoleUser roleUser);
}
