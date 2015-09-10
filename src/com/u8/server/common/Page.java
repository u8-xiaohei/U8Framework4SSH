package com.u8.server.common;

import java.util.List;

/***
 * 分页
 * @param <T>
 */
public class Page<T>
{
  private PageParameter pageParameter;
  private List<T> resultList = null;
  private int totalCount = -1;

  public Page()
  {
    this.pageParameter = new PageParameter(1, 10, false);
  }

  public Page(PageParameter params)
  {
    this.pageParameter = params;
  }

  public int getTotalPages()
  {
    if (this.totalCount == -1)
      return -1;
    int i = this.totalCount / this.pageParameter.getPageSize();
    if (this.totalCount % this.pageParameter.getPageSize() > 0)
      i++;
    return i;
  }

  public List<T> getResultList()
  {
    return this.resultList;
  }

  public void setResultList(List<T> paramList)
  {
    this.resultList = paramList;
  }

  public int getTotalCount()
  {
    return this.totalCount;
  }

  public void setTotalCount(int paramInt)
  {
    this.totalCount = paramInt;
  }

  public PageParameter getPageParameter()
  {
    return this.pageParameter;
  }

  public void setPageParameter(PageParameter params)
  {
    this.pageParameter = params;
  }
}