package wang.dreamland.www.service;

import wang.dreamland.www.entity.Role;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:12
 */
public interface RoleService {
    /*根据主表id查询实体*/
    public Role findById(Long id);

    /*删除实体*/
    public void deleteById(Long id);

    /*更新实体*/
    public void update(Role resource);
}
