package com.ibeetl.bbs.mapper;


import com.ibeetl.bbs.bean.BBSTopic;
import com.ibeetl.bbs.model.BbsTopic;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BBSTopicMapper extends Mapper<BbsTopic> {

    void upStatusById(@Param("isUp")Integer isUp, @Param("id")Integer id);

    void upNiceStatusById(@Param("isNice")Integer isNice, @Param("id")Integer id);
}
