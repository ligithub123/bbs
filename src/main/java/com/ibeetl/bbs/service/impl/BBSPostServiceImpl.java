package com.ibeetl.bbs.service.impl;

import com.ibeetl.bbs.exception.NumException;
import com.ibeetl.bbs.mapper.BBSPostMapper;
import com.ibeetl.bbs.mapper.UserMapper;
import com.ibeetl.bbs.model.BbsPost;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    @Autowired
    UserMapper userMapper;

    @Override
    public BbsPost selectPostById(Integer id) {

        BbsPost bbsPost = new BbsPost();
        bbsPost.setTopicId(id);

        return bbsPostMapper.selectOne(bbsPost);
    }

    @Override
    public List<BbsPost> selectPostsByTopicId(Integer topicId) {
        List<BbsPost> bbsPosts = bbsPostMapper.selectPostByTopicId(topicId);

        getUserByUserId(bbsPosts);
        return bbsPosts;
    }

    private void getUserByUserId(List<BbsPost> bbsPosts) {

        for (BbsPost bbsPost : bbsPosts) {
            BbsUser bbsUser = new BbsUser();
            bbsUser.setId(bbsPost.getUserId());
            BbsUser bbsUser1 = userMapper.selectOne(bbsUser);
            bbsPost.setUser(bbsUser1);
        }
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

    @Override
    @Transactional
    public void changeHasReplyStatus(Integer postId, Integer topicId) {

        BbsPost bbsPost = new BbsPost();
        bbsPost.setId(postId);
        bbsPost.setTopicId(topicId);
        BbsPost bbsPost1 = bbsPostMapper.selectOne(bbsPost);
        if(bbsPost1.getHasReply() == 0){

            bbsPostMapper.updateHasReplyStatus(postId,topicId);
        }


    }

    @Override
    public List<BbsPost> selectPostByIdLimit5(Integer topicId) {

        Example example = new Example(BbsPost.class);
        example.setOrderByClause("PROS DESC");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("topicId",topicId );
        //List<BbsTopic> bbsTopics = bbsTopicMapper.selectByExample(example);
        List<BbsPost> bbsPosts = bbsPostMapper.selectByExample(example);
        if (bbsPosts !=null && bbsPosts.size() >= 5){
            //返回前5
            List<BbsPost> bbsPosts1 = bbsPosts.subList(0, 5);
            getUserByUserId(bbsPosts1);
            return bbsPosts1;
        }else {
            throw new NumException(" less than five comments");
        }
    }

}
