package wang.dreamland.www.service;

import wang.dreamland.www.entity.Comment;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:07
 */
public interface CommentService {
    /*根据主表id查询实体*/
    public Comment findById(Long id);

    /*删除实体*/
    public void deleteById(Long id);

    /*更新实体*/
    public void update(Comment comment);
}
