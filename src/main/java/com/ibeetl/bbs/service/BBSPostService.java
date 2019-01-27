package com.ibeetl.bbs.service;

import com.ibeetl.bbs.model.BbsPost;
import com.ibeetl.bbs.model.BbsUser;

import java.util.List;

public interface BBSPostService {

    BbsPost selectPostById(Integer id);

    List<BbsPost> selectPostsByTopicId(Integer topicId);

    BbsPost getPostById(Integer postId);

    /** 点赞或踩时保存*/
    void saveSupport(BbsPost bbsPost);

    void savePost(Integer topicId, String content, BbsUser user);

    void updatePost(Integer postId, String content);

    void deletePostById(Integer id);

    /** 已有评论是否有其他评论*/
    void changeHasReplyStatus(Integer postId, Integer topicId);

    /**按点赞数获取排列前5的 */
    List<BbsPost> selectPostByIdLimit5(Integer id);
}
