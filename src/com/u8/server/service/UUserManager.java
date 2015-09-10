package com.u8.server.service;

import com.u8.server.common.OrderParameter;
import com.u8.server.common.OrderParameters;
import com.u8.server.common.Page;
import com.u8.server.common.PageParameter;
import com.u8.server.dao.UUserDao;
import com.u8.server.data.UUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 这里处理UUser相关的逻辑操作
 */
@Service("userManager")
public class UUserManager {

    @Autowired
    private UUserDao userDao;

    //根据渠道用户ID获取用户信息
    public UUser getUserByCpID(int appID, int channelID, String cpUserID){

        String hql = "from UUser where appID = ? and channelID = ? and channelUserID = ?";

        return (UUser)userDao.findUnique(hql, appID, channelID, cpUserID);

    }

    //获取指定渠道下的所有用户
    public List<UUser> getUsersByChannel(int channelID){
        String hql = "from UUser where channelID = ?";

        return userDao.find(hql, new Object[]{channelID}, null);
    }

    //分页查找
    public Page<UUser> queryPage(int currPage, int num){

        PageParameter page = new PageParameter(currPage, num, true);
        OrderParameters order = new OrderParameters();
        order.add("id", OrderParameter.OrderType.DESC);
        String hql = "from UUser";
        return userDao.find(page, hql, null, order);
    }


    public UUser getUser(int userID){

        return userDao.get(userID);
    }

    public void saveUser(UUser user){
        userDao.save(user);
    }

    public void deleteUser(UUser user){
        userDao.delete(user);
    }
}
