package wang.dreamland.www.service;

import wang.dreamland.www.entity.Resource;

import java.util.List;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0005 20:58
 */
public interface ResourceService {
    /**
     * 添加资源
     * @param resource
     */
    void add(Resource resource);

    /**
     * 根据资源id查询资源
     * @param id
     * @return
     */
    Resource findById(Long id);

    /**
     * 查询所有资源
     * @return
     */
    List<Resource> findAll();

    /**
     * 根据资源id删除资源
     * @param id
     */
    void deleteById(Long id);

    /**
     * 更新资源
     * @param resource
     */
    void update(Resource resource);
}
