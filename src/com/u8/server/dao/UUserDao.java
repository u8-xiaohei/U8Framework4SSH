package com.u8.server.dao;

import com.u8.server.common.UHibernateTemplate;
import com.u8.server.data.UUser;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问类
 */
@Repository("userDao")
public class UUserDao extends UHibernateTemplate<UUser, Integer>{



}
