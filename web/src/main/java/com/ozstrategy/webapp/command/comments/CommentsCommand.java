package com.ozstrategy.webapp.command.comments;

import com.ozstrategy.model.comments.Comments;

import java.util.Date;

/**
 * Created by lihao1 on 5/25/15.
 */
public class CommentsCommand {
    private Long id;
    private String comment;
    private String contract;
    private String username;
    private Date createDate;

    public CommentsCommand() {
    }
    public CommentsCommand(Comments comments) {
        this.id=comments.getId();
        this.comment=comments.getComments();
        this.contract=comments.getContract();
        this.username=comments.getCreator().getUsername();
        this.createDate=comments.getCreateDate();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
