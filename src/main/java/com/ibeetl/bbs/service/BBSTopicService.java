package com.ibeetl.bbs.service;



import com.ibeetl.bbs.bean.BBSTopic;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.model.BbsUser;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface BBSTopicService {

    List<BbsTopic> findAll();

    List<BbsTopic> selectByExample(Example example);

    void saveTopic(Integer moduleId, String title, String postContent, BbsUser user);

    /** 查询所有的帖子，置顶帖子在最前面*/
    List<BbsTopic> selectTopic();

    /** 改变是否置顶的状态*/
    void changeIsUpStatus(Integer id);

    /** 改变是否精华的状态*/
    void changeIsNiceStatus(Integer id);

    void deleteTopic(Integer id);

    BbsTopic selectTopicById(Integer id);

    /** 修改帖子的浏览次数*/
    void changPVcount(Integer id);

    /** 修改帖子的评论次数*/
    void changePostCount(Integer topicId);
}
