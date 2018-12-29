package com.ibeetl.bbs.service.impl;


import com.ibeetl.bbs.bean.BBSTopic;
import com.ibeetl.bbs.mapper.BBSTopicMapper;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.service.BBSTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author ljy
 * @date 2018/12/26
 */
@Service
public class BBSTopicServiceImpl implements BBSTopicService {

    @Autowired
    private BBSTopicMapper bbsTopicMapper;

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
}
