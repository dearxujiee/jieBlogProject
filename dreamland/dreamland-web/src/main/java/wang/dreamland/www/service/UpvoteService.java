package wang.dreamland.www.service;

import wang.dreamland.www.entity.Upvote;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:14
 */
public interface UpvoteService {
    /*根据主表id查询实体*/
    public Upvote findById(Long id);

    /*删除实体*/
    public void deleteById(Long id);

    /*更新实体*/
    public void update(Upvote upvote);
}
