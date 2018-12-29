package com.ibeetl.bbs.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ljy
 * @date 2018/12/26
 */
@Table(name = "bbs_comment")
public class BBSComment implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Integer id;
    @Column
    private Integer topicId;
    @Column
    private Integer commentParentId;
    @Column
    private String commentContent;
    @Column
    private Integer userId;
    @Column
    private String userName;
    @Column
    private Integer praiseCount;
    @Column
    private Long commentTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(Integer commentParentId) {
        this.commentParentId = commentParentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Long commentTime) {
        this.commentTime = commentTime;
    }
}
