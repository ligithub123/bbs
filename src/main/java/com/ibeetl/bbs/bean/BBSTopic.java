package com.ibeetl.bbs.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ljy
 * @date 2018/12/26
 */
@Table(name = "bbs_topic")
public class BBSTopic implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Integer id;
    @Column
    private String topicTitle;
    @Column
    private String topicContent;
    @Column
    private Integer topicCount;//浏览次数
    @Column
    private Integer replyCount;//回复次数
    @Column
    private Integer userId;
    @Column
    private Long createTime;
    @Column
    private String isNice;
    @Column
    private String isUp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getIsNice() {
        return isNice;
    }

    public void setIsNice(String isNice) {
        this.isNice = isNice;
    }

    public String getIsUp() {
        return isUp;
    }

    public void setIsUp(String isUp) {
        this.isUp = isUp;
    }
}
