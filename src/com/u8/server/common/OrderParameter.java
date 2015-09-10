package com.u8.server.common;

import org.hibernate.criterion.Order;

/***
 * 排序参数
 */
public class OrderParameter
{
  private String fieldName;
  private OrderType orderType;

  public OrderParameter(String fieldName, OrderType orderType)
  {
    this.fieldName = fieldName;
    this.orderType = orderType;
  }

  public static OrderParameter asc(String fieldName)
  {
    return new OrderParameter(fieldName, OrderType.ASC);
  }

  public static OrderParameter desc(String fieldName)
  {
    return new OrderParameter(fieldName, OrderType.DESC);
  }

  public String getField()
  {
    return this.fieldName;
  }

  public Order getOrder()
  {
    Order localOrder = null;
    if (OrderType.ASC.equals(this.orderType))
      localOrder = Order.asc(this.fieldName);
    else if (OrderType.DESC.equals(this.orderType))
      localOrder = Order.desc(this.fieldName);
    return localOrder;
  }

  public boolean isAsc()
  {
    return this.orderType.equals(OrderType.ASC);
  }

  public boolean isDesc()
  {
    return this.orderType.equals(OrderType.DESC);
  }

  public static enum OrderType
  {
    ASC, DESC;
  }
}