package com.ibeetl.bbs.service.impl;

import com.ibeetl.bbs.mapper.BBSPostMapper;
import com.ibeetl.bbs.model.BbsPost;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ljy
 * @date 2018/12/31
 */
@Service
public class BBSPostServiceImpl implements BBSPostService {


    @Autowired
    private BBSPostMapper bbsPostMapper;

    @Override
    public BbsPost selectPostById(Integer id) {

        BbsPost bbsPost = new BbsPost();
        bbsPost.setTopicId(id);

        return bbsPostMapper.selectOne(bbsPost);
    }

    @Override
    public List<BbsPost> selectPostsByTopicId(Integer topicId) {
        List<BbsPost> bbsPosts = bbsPostMapper.selectPostByTopicId(topicId);
        return bbsPosts;
    }

    @Override
    public BbsPost getPostById(Integer postId) {
        BbsPost bbsPost = new BbsPost();
        bbsPost.setId(postId);
        return bbsPostMapper.selectOne(bbsPost);
    }

    @Override
    @Transactional
    public void saveSupport(BbsPost bbsPost) {

        bbsPostMapper.updateByPrimaryKey(bbsPost);
    }

    @Override
    @Transactional
    public void savePost(Integer topicId, String content, BbsUser user) {

        BbsPost bbsPost = new BbsPost();
        bbsPost.setTopicId(topicId);
        bbsPost.setCreateTime(new Date());
        bbsPost.setUserId(user.getId());
        bbsPost.setContent(content);
        bbsPost.setHasReply(0);
        bbsPostMapper.insert(bbsPost);
    }

    @Override
    @Transactional
    public void updatePost(Integer postId, String content) {

        BbsPost bbsPost1 = new BbsPost();
        bbsPost1.setId(postId);
        BbsPost bbsPost = bbsPostMapper.selectOne(bbsPost1);
        bbsPost.setContent(content);
        bbsPost.setUpdateTime(new Date());

        bbsPostMapper.updateContentById(content,new Date(),postId);
    }

    @Override
    @Transactional
    public void deletePostById(Integer id) {

        bbsPostMapper.deleteByPrimaryKey(id);
    }

}
