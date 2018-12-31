package com.ibeetl.bbs.service;

import com.ibeetl.bbs.model.BbsReply;

import java.util.List;

public interface BBSReplyService {

    List<BbsReply> seletPostReply(Integer id, Integer topicId);
}
