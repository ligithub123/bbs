package com.ibeetl.bbs.service;



import com.ibeetl.bbs.bean.BBSTopic;
import com.ibeetl.bbs.model.BbsTopic;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface BBSTopicService {

    List<BbsTopic> findAll();

    List<BbsTopic> selectByExample(Example example);
}
