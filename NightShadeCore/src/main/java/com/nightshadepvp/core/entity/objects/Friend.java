package com.nightshadepvp.core.entity.objects;

import java.util.Date;
import java.util.UUID;

public class Friend {

    private UUID friendUUID;
    private Date dateAdded;


    public Friend(UUID friendUUID, Date dateAdded) {
        this.friendUUID = friendUUID;
        this.dateAdded = dateAdded;
    }

    public UUID getFriendUUID() {
        return friendUUID;
    }

    public void setFriendUUID(UUID friendUUID) {
        this.friendUUID = friendUUID;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
