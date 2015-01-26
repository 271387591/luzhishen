package com.ozstrategy.webapp.command;

import com.ozstrategy.model.CreatorEntity;

/**
 * Created by lihao on 9/28/14.
 */
public class CreatorEntityCommand extends BaseEntityCommand {
    protected Long creatorId;
    protected String creatorFullName;
    protected Long lastUpdaterId;
    protected String lastUpdaterFullName;
    public CreatorEntityCommand(CreatorEntity baseObject) {
        super(baseObject);
        this.creatorId=baseObject.getCreator()!=null?baseObject.getCreator().getId():null;
        this.creatorFullName=baseObject.getCreator()!=null?baseObject.getCreator().getFullName():null;
        this.lastUpdaterId=baseObject.getLastUpdater()!=null?baseObject.getLastUpdater().getId():null;
        this.lastUpdaterFullName=baseObject.getLastUpdater()!=null?baseObject.getLastUpdater().getFullName():null;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorFullName() {
        return creatorFullName;
    }

    public void setCreatorFullName(String creatorFullName) {
        this.creatorFullName = creatorFullName;
    }

    public Long getLastUpdaterId() {
        return lastUpdaterId;
    }

    public void setLastUpdaterId(Long lastUpdaterId) {
        this.lastUpdaterId = lastUpdaterId;
    }

    public String getLastUpdaterFullName() {
        return lastUpdaterFullName;
    }

    public void setLastUpdaterFullName(String lastUpdaterFullName) {
        this.lastUpdaterFullName = lastUpdaterFullName;
    }
}
