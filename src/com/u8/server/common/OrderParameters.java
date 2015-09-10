package com.u8.server.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 * 多个排序组合
 */
public class OrderParameters
{
  private List<OrderParameter> parameters = new ArrayList<OrderParameter>();

  public void add(String fieldName, OrderParameter.OrderType orderType)
  {
    OrderParameter localOrderParameter = new OrderParameter(fieldName, orderType);
    this.parameters.add(localOrderParameter);
  }

  public String toString()
  {
    String str = "";
    if (getSize() > 0)
    {
      StringBuilder localStringBuilder = new StringBuilder(" order by ");
      Iterator<OrderParameter> localIterator = this.parameters.iterator();
      while (localIterator.hasNext())
      {
        OrderParameter localOrderParameter = (OrderParameter)localIterator.next();
        localStringBuilder.append(localOrderParameter.getField());
        if (localOrderParameter.isAsc())
          localStringBuilder.append(" asc,");
        else
          localStringBuilder.append(" desc,");
      }
      str = localStringBuilder.substring(0, localStringBuilder.length() - 1);
    }
    return str;
  }

  public List<OrderParameter> getParameters()
  {
    return this.parameters;
  }

  public void setParameters(List<OrderParameter> paramList)
  {
    this.parameters = paramList;
  }

  public int getSize()
  {
    return this.parameters.size();
  }
}
