package com.ibeetl.bbs.service.impl;


import com.ibeetl.bbs.bean.BBSTopic;
import com.ibeetl.bbs.mapper.BBSPostMapper;
import com.ibeetl.bbs.mapper.BBSReplyMapper;
import com.ibeetl.bbs.mapper.BBSTopicMapper;
import com.ibeetl.bbs.model.BbsPost;
import com.ibeetl.bbs.model.BbsReply;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ljy
 * @date 2018/12/26
 */
@Service
public class BBSTopicServiceImpl implements BBSTopicService {

    private static final Logger logger = LoggerFactory.getLogger(BbsUserServiceImpl.class);

    @Autowired
    private BBSTopicMapper bbsTopicMapper;

    @Autowired
    private BBSPostMapper bbsPostMapper;

    @Autowired
    private BBSReplyMapper bbsReplyMapper;

    @Override
    public List<BbsTopic> findAll() {
        List<BbsTopic> bbsTopics = bbsTopicMapper.selectAll();
        return bbsTopics;
        //return bbsTopicMapper.selectAll();
    }

    @Override
    public List<BbsTopic> selectByExample(Example example) {

        return bbsTopicMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public void saveTopic(Integer moduleId, String title, String postContent, BbsUser user) {

        BbsTopic bbsTopic = new BbsTopic();
        bbsTopic.setUserId(user.getId());
        bbsTopic.setPostCount(0);
        bbsTopic.setContent(title);
        bbsTopic.setModuleId(moduleId);
        bbsTopic.setIsNice(0);
        bbsTopic.setIsUp(0);
        bbsTopic.setReplyCount(0);
        bbsTopic.setCreateTime(new Date());

        int i = bbsTopicMapper.insertSelective(bbsTopic);

        if(i == 1){
            BbsPost bbsPost = new BbsPost();
            bbsPost.setContent(postContent);
            bbsPost.setUserId(user.getId());
            bbsPost.setTopicId(bbsTopic.getId());
            bbsPost.setCreateTime(new Date());
            bbsPost.setHasReply(0);

            bbsPostMapper.insert(bbsPost);
        }

    }

    @Override
    public List<BbsTopic> selectTopic() {

        Example example = new Example(BbsTopic.class);
        example.setOrderByClause("CREATE_TIME DESC");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isUp",1 );
        List<BbsTopic> bbsTopics = bbsTopicMapper.selectByExample(example);


        Example example1 = new Example(BbsTopic.class);
        example1.setOrderByClause("CREATE_TIME DESC");
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("isUp",0 );
        List<BbsTopic> bbsTopics1 = bbsTopicMapper.selectByExample(example1);

        if(bbsTopics != null && !bbsTopics.isEmpty()){
            bbsTopics.addAll(bbsTopics1);
        }

        return bbsTopics;
    }

    @Override
    @Transactional
    public void changeIsUpStatus(Integer id) {

        BbsTopic bbsTopic1 = new BbsTopic();
        bbsTopic1.setId(id);
        BbsTopic bbsTopic = bbsTopicMapper.selectOne(bbsTopic1);
        if(bbsTopic != null){
            Integer isUp = bbsTopic.getIsUp();
            isUp = isUp > 0 ? 0:1;
            //bbsTopic.setIsUp(isUp);
            bbsTopicMapper.upStatusById(isUp,id);
            //bbsTopicMapper.updateByPrimaryKey(bbsTopic);
            logger.info("修改id为"+id+"帖子的置顶状态为"+isUp);
        }else {
            logger.info("该记录已删除");
        }
    }

    @Override
    @Transactional
    public void changeIsNiceStatus(Integer id) {

        BbsTopic bbsTopic1 = new BbsTopic();
        bbsTopic1.setId(id);
        BbsTopic bbsTopic = bbsTopicMapper.selectOne(bbsTopic1);
        if(bbsTopic != null){
            Integer isNice = bbsTopic.getIsNice();
            isNice = isNice > 0 ? 0:1;
            //bbsTopic.setIsUp(isUp);
            bbsTopicMapper.upNiceStatusById(isNice,id);
            //bbsTopicMapper.updateByPrimaryKey(bbsTopic);
            logger.info("修改id为"+id+"帖子的精华状态为"+isNice);
        }else {
            logger.info("该记录已删除");
        }
    }

    @Override
    @Transactional
    public void deleteTopic(Integer id) {

        bbsTopicMapper.deleteByPrimaryKey(id);
        BbsPost bbsPost = new BbsPost();
        bbsPost.setTopicId(id);
        bbsPostMapper.delete(bbsPost);

        BbsReply bbsReply = new BbsReply();
        bbsReply.setTopicId(id);
        bbsReplyMapper.delete(bbsReply);
        logger.info("删除id为"+id+"的帖子");
    }

    @Override
    public BbsTopic selectTopicById(Integer id) {

        BbsTopic bbsTopic = new BbsTopic();
        bbsTopic.setId(id);
        BbsTopic bbsTopic1 = bbsTopicMapper.selectOne(bbsTopic);
        return bbsTopic1;
    }

    @Override
    @Transactional
    public void changPVcount(Integer id) {
        BbsTopic bbsTopic = new BbsTopic();
        bbsTopic.setId(id);
        BbsTopic bbsTopic1 = bbsTopicMapper.selectOne(bbsTopic);

        if(bbsTopic1 != null){
            bbsTopic1.setPv(bbsTopic1.getPv()+1);
            bbsTopicMapper.updateByPrimaryKey(bbsTopic1);
            logger.info("修改id为"+id+"的帖子的浏览次数为"+bbsTopic1.getPv());
        }
    }

    @Override
    @Transactional
    public void changePostCount(Integer topicId) {
        BbsTopic bbsTopic = new BbsTopic();
        bbsTopic.setId(topicId);
        BbsTopic bbsTopic1 = bbsTopicMapper.selectOne(bbsTopic);

        if(bbsTopic1 != null){
            bbsTopic1.setPostCount(bbsTopic1.getPostCount()+1);
            bbsTopicMapper.updateByPrimaryKey(bbsTopic1);
            logger.info("修改id为"+topicId+"的帖子的评论次数为"+bbsTopic1.getPostCount());
        }
    }

}
