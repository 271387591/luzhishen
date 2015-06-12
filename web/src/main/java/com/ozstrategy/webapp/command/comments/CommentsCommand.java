package com.ozstrategy.webapp.command.comments;

/**
 * Created by lihao1 on 5/25/15.
 */
public class CommentsCommand {
    private Long id;
    private String comment;

    public CommentsCommand() {
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
}
