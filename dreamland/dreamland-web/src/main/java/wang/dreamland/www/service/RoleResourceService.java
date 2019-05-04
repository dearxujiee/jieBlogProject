package wang.dreamland.www.service;

import wang.dreamland.www.entity.RoleResource;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:12
 */
public interface RoleResourceService {
    /*根据主表id查询实体*/
    public RoleResource findById(Long id);

    /*删除实体*/
    public void deleteById(Long id);

    /*更新实体*/
    public void update(RoleResource roleResource);
}
