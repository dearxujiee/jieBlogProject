package wang.dreamland.www.service;

import wang.dreamland.www.entity.RoleResource;

import java.util.List;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0005 20:58
 */
public interface RoleResourceService {
    /**
     * 添加roleRource
     * @param roleResource
     */
    void add(RoleResource roleResource);

    /**
     * 根据id查询RoleResource
     * @param id
     * @return
     */
    RoleResource findById(Long id);

    /**
     * 根据角色id查询角色资源集合
     * @param rid
     * @return
     */
    List<RoleResource> findByRoleId(Long rid);

    /**
     * 根据id删除RoleResource
     * @param id
     */
    void deleteById(Long id);


}
