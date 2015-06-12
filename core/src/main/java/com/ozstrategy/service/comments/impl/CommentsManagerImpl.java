package com.ozstrategy.service.comments.impl;

import com.ozstrategy.model.comments.Comments;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.comments.CommentsManager;
import org.springframework.stereotype.Service;

/**
 * Created by lihao1 on 5/25/15.
 */
@Service("commentsManager")
public class CommentsManagerImpl extends GenericManagerImpl<Comments,Long> implements CommentsManager {
}
