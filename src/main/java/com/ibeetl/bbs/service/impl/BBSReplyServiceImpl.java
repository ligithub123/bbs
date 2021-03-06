package com.ibeetl.bbs.service.impl;

import com.ibeetl.bbs.mapper.BBSReplyMapper;
import com.ibeetl.bbs.mapper.UserMapper;
import com.ibeetl.bbs.model.BbsReply;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSReplyService;
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
public class BBSReplyServiceImpl implements BBSReplyService {

    @Autowired
    private BBSReplyMapper bbsReplyMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<BbsReply> seletPostReply(Integer topicId, Integer id) {

        Example example = new Example(BbsReply.class);
        example.setOrderByClause("CREATE_TIME ASC");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("topicId",topicId );
        criteria.andEqualTo("postId", id);
        List<BbsReply> bbsReplys = bbsReplyMapper.selectByExample(example);
        for (BbsReply bbsReply : bbsReplys) {

            BbsUser bbsUser = new BbsUser();
            bbsUser.setId(bbsReply.getUserId());
            BbsUser bbsUser1 =userMapper.selectOne(bbsUser);
            bbsReply.setUserId(bbsUser1.getId());
            bbsReply.setUser(bbsUser1);
        }
        return  bbsReplys;
    }

    @Override
    @Transactional
    public void saveReply(Integer postId, Integer topicId, String content, BbsUser user) {

        BbsReply bbsReply = new BbsReply();
        bbsReply.setCreateTime(new Date());
        bbsReply.setPostId(postId);
        bbsReply.setUserId(user.getId());
        bbsReply.setTopicId(topicId);
        bbsReply.setContent(content);

        bbsReplyMapper.insert(bbsReply);
    }
}
