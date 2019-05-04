package wang.dreamland.www.service.impl;

import org.springframework.stereotype.Service;
import wang.dreamland.www.common.PageHelper;
import wang.dreamland.www.entity.Comment;
import wang.dreamland.www.entity.UserContent;
import wang.dreamland.www.service.UserContentService;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 22:55
 */
@Service
public class UserContentServiceImpl implements UserContentService{

    @Override
    public UserContent findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(UserContent userContent) {

    }

    @Override
    public PageHelper.Page<UserContent> findAll(UserContent content, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public PageHelper.Page<UserContent> findAll(UserContent content, Comment comment, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public PageHelper.Page<UserContent> findAllByUpvote(UserContent content, Integer pageNum, Integer pageSize) {
        return null;
    }
}
