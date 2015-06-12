package com.ozstrategy.dao.comments.impl;

import com.ozstrategy.dao.comments.CommentsDao;
import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.model.comments.Comments;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/25/15.
 */
@Repository("commentsDao")
public class CommentsDaoImpl extends GenericDaoHibernate<Comments,Long> implements CommentsDao {
    public CommentsDaoImpl() {
        super(Comments.class);
    }
}
