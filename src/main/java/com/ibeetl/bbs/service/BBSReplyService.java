package com.ibeetl.bbs.service;

import com.ibeetl.bbs.model.BbsReply;
import com.ibeetl.bbs.model.BbsUser;

import java.util.List;

public interface BBSReplyService {

    List<BbsReply> seletPostReply(Integer id, Integer topicId);

    void saveReply(Integer postId, Integer topicId, String content, BbsUser user);
}
