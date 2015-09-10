package com.u8.server.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

/***
 * Hibernate Dao 层默认继承该类
 * @param <T>
 * @param <PK>
 */
public class UHibernateTemplate<T, PK extends Serializable>
  implements IHibernateTemplate<T, PK>
{
  public SessionFactory sessionFactory;
  protected Class<T> entityClass;

  @SuppressWarnings("unchecked")
  public UHibernateTemplate()
  {
    this.entityClass = null;
    Class localClass = getClass();
    Type localType = localClass.getGenericSuperclass();
    if ((localType instanceof ParameterizedType))
    {
      Type[] arrayOfType = ((ParameterizedType)localType).getActualTypeArguments();
      this.entityClass = ((Class)arrayOfType[0]);
    }
  }

  public UHibernateTemplate(SessionFactory sessionFactory, Class<T> clazz)
  {
    this.sessionFactory = sessionFactory;
    this.entityClass = clazz;
  }

  public Session getSession()
  {
    return this.sessionFactory.getCurrentSession();
  }

  @SuppressWarnings("unchecked")
  public T get(PK id)
  {
    return (T)getSession().get(this.entityClass, id);
  }

  public void save(T data)
  {
    Assert.notNull(data);
    getSession().saveOrUpdate(data);
  }

  public void delete(T data)
  {
    Assert.notNull(data);
    getSession().delete(data);
  }

  public void delete(PK id)
  {
    delete(get(id));
  }

  @SuppressWarnings("unchecked")
  public List<T> findAll(OrderParameters orderParams)
  {
    Criteria localCriteria = getCriteria();
    if (orderParams != null)
    {
      Iterator localIterator = orderParams.getParameters().iterator();
      while (localIterator.hasNext())
      {
        OrderParameter localOrderParameter = (OrderParameter)localIterator.next();
        localCriteria.addOrder(localOrderParameter.getOrder());
      }
    }
    return new ArrayList(localCriteria.list());
  }

  public List<T> findAll(){

    return findAll(null);
  }

  public Criteria getCriteria()
  {
    return getSession().createCriteria(this.entityClass);
  }

  @SuppressWarnings("unchecked")
  public List<T> find(String sql, Object[] params, OrderParameters orderParams)
  {
    Assert.hasText(sql);
    sql = sql + (orderParams==null?"":orderParams.toString());
    return new ArrayList(createQuery(sql, params).list());
  }

  @SuppressWarnings("unchecked")
  public Page<T> find(PageParameter page, String sql, Object[] params, OrderParameters orderParams)
  {
    Assert.notNull(page);
    Assert.notNull(sql);
    Page localPage = new Page();
    localPage.setPageParameter(page);
    Query localQuery;
    if (page.isAutoCount())
    {
      String str = "select count(*) " + sql;
      localQuery = createQuery(str, params);
      localPage.setTotalCount(localQuery.list().size());
    }
    if (localPage.getTotalCount() > 0)
    {
      sql = sql + orderParams.toString();
      localQuery = createQuery(sql, params);
      if (page.isFirstSetted())
        localQuery.setFirstResult(page.getFirst());
      if (page.isPageSizeSetted())
        localQuery.setMaxResults(page.getPageSize());
      localPage.setResultList(localQuery.list());
      return localPage;
    }
    return null;
  }

  public Query createQuery(String sql, Object[] params)
  {
    Assert.hasText(sql);
    Query localQuery = getSession().createQuery(sql);
    if (params != null)
      for (int i = 0; i < params.length; i++)
        localQuery.setParameter(i, params[i]);
    return localQuery;
  }

  public Object findUnique(String sql, Object... params)
  {
    return createQuery(sql, params).uniqueResult();
  }

  public Integer findInt(String sql, Object[] params)
  {
    return Integer.valueOf(findLong(sql, params).intValue());
  }

  public Long findLong(String sql, Object[] params)
  {
    return (Long)findUnique(sql, params);
  }

  public SessionFactory getSessionFactory()
  {
    return this.sessionFactory;
  }

  @Autowired
  public void setSessionFactory(@Qualifier("sessionFactory") SessionFactory paramSessionFactory)
  {
    this.sessionFactory = paramSessionFactory;
  }

  public Class<T> getEntityClass()
  {
    return this.entityClass;
  }

  public void setEntityClass(Class<T> paramClass)
  {
    this.entityClass = paramClass;
  }
}