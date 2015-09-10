package com.u8.server.common;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract interface IHibernateTemplate<T, PK extends Serializable>
{
  public abstract Session getSession();

  public abstract T get(PK id);

  public abstract void save(T data);

  public abstract void delete(T data);

  public abstract void delete(PK id);

  public abstract List<T> findAll(OrderParameters orderParams);

  public abstract Criteria getCriteria();

  public abstract List<T> find(String sql, Object[] params, OrderParameters orderParams);

  public abstract Page<T> find(PageParameter page, String sql, Object[] params, OrderParameters orderParams);

  public abstract Query createQuery(String sql, Object[] params);

  public abstract Object findUnique(String sql, Object[] params);

  public abstract Integer findInt(String sql, Object[] params);

  public abstract Long findLong(String sql, Object[] params);

  public abstract SessionFactory getSessionFactory();
}