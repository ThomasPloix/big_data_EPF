package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UserRecommendation implements Writable {
    private String userId;
    private String recomendationFriendId;
    private int commonFriendCount;


    public UserRecommendation() {
        this.userId = "";
        this.recomendationFriendId = "";
        this.commonFriendCount = 0;
    }

    // Constructeur avec paramètres
    public UserRecommendation(String userId, String recommendedId, int commonFriends) {
        this.userId = userId;
        this.recomendationFriendId = recommendedId;
        this.commonFriendCount = commonFriends;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getRecommendedId() { return recomendationFriendId; }
    public int getCommonFriends() { return commonFriendCount; }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(userId);
        out.writeUTF(recomendationFriendId);
        out.writeInt(commonFriendCount);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        userId = in.readUTF();
        recomendationFriendId = in.readUTF();
        commonFriendCount = in.readInt();
    }

    @Override
    public String toString() {
        return recomendationFriendId + "(" + commonFriendCount + ")";
    }

}
