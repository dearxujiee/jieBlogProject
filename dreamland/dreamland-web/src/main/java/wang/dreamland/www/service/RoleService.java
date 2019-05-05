package wang.dreamland.www.service;

import wang.dreamland.www.entity.Role;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0005 20:59
 */
public interface RoleService {
    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    Role findById(long id);

    /**
     * 添加角色
     * @param role
     * @return
     */
    int add(Role role);
}
