package com.u8.server.data;

import com.u8.server.utils.TimeFormater;
import net.sf.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户数据对象
 */

@Entity
@Table(name = "uuser")
public class UUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int appID;
    private int channelID;
    private String name;
    private String channelUserID;
    private String channelUserName;
    private String channelUserNick;
    private Date createTime;
    private Date lastLoginTime;
    private String token;

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("appID", appID);
        json.put("channelID", channelID);
        json.put("name", name);
        json.put("channelUserID", channelUserID);
        json.put("channelUserName", channelUserName);
        json.put("channelUserNick", channelUserNick);
        json.put("createTime", TimeFormater.format_default(createTime));
        json.put("lastLoginTime", TimeFormater.format_default(lastLoginTime));
        return json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelUserID() {
        return channelUserID;
    }

    public void setChannelUserID(String channelUserID) {
        this.channelUserID = channelUserID;
    }

    public String getChannelUserName() {
        return channelUserName;
    }

    public void setChannelUserName(String channelUserName) {
        this.channelUserName = channelUserName;
    }

    public String getChannelUserNick() {
        return channelUserNick;
    }

    public void setChannelUserNick(String channelUserNick) {
        this.channelUserNick = channelUserNick;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
