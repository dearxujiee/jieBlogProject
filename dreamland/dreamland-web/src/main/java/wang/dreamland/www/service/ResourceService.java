package wang.dreamland.www.service;

import wang.dreamland.www.entity.Resource;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:11
 */
public interface ResourceService {
    /*根据主表id查询实体*/
    public Resource findById(Long id);

    /*删除实体*/
    public void deleteById(Long id);

    /*更新实体*/
    public void update(Resource resource);
}
