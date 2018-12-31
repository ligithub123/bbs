package com.ibeetl.bbs.service.impl;

import com.ibeetl.bbs.mapper.BBSReplyMapper;
import com.ibeetl.bbs.model.BbsReply;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.service.BBSReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author ljy
 * @date 2018/12/31
 */
@Service
public class BBSReplyServiceImpl implements BBSReplyService {

    @Autowired
    private BBSReplyMapper bbsReplyMapper;

    @Override
    public List<BbsReply> seletPostReply(Integer id, Integer topicId) {

        Example example = new Example(BbsReply.class);
        example.setOrderByClause("CREATE_TIME ASC");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("topicId",topicId );
        criteria.andEqualTo("postId", id);
        List<BbsReply> bbsReplys = bbsReplyMapper.selectByExample(example);
        return  bbsReplys;
    }
}
