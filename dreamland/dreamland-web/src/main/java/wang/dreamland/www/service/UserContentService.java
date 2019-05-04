package wang.dreamland.www.service;

import wang.dreamland.www.common.PageHelper;
import wang.dreamland.www.entity.Comment;
import wang.dreamland.www.entity.UserContent;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:15
 */
public interface UserContentService {
    /*根据主表id查询实体*/
    public UserContent findById(Long id);

    /*删除实体*/
    public void deleteById(Long id);

    /*更新实体*/
    public void update(UserContent userContent);

    /**
     * 查询所有Content并分页
     * @param content
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageHelper.Page<UserContent> findAll(UserContent content, Integer pageNum, Integer pageSize);
    PageHelper.Page<UserContent> findAll(UserContent content, Comment comment, Integer pageNum, Integer pageSize);
    PageHelper.Page<UserContent> findAllByUpvote(UserContent content, Integer pageNum, Integer pageSize);
}
