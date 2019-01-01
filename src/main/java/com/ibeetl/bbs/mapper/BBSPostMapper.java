package com.ibeetl.bbs.mapper;

import com.ibeetl.bbs.model.BbsPost;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface BBSPostMapper extends Mapper<BbsPost> {

    List<BbsPost> selectPostByTopicId(@Param("topicId") Integer topicId);

    void updateContentById(@Param("content")String content, @Param("date")Date date, @Param("postId")Integer postId);
}
