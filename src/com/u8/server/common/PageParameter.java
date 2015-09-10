package com.u8.server.common;

/***
 * 分页参数
 */
public class PageParameter
{
  protected int pageNo = 1;
  protected int pageSize = -1;
  protected boolean autoCount = false;

  public PageParameter(int pageSize)
  {
    this.pageSize = pageSize;
  }

  public PageParameter(int pageNo, int pageSize, boolean autoCount)
  {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
    this.autoCount = autoCount;
  }

  public boolean isPageSizeSetted()
  {
    return this.pageSize > -1;
  }

  public int getFirst()
  {
    if ((this.pageNo < 1) || (this.pageSize < 1))
      return -1;
    return (this.pageNo - 1) * this.pageSize;
  }

  public boolean isFirstSetted()
  {
    return (this.pageNo > 0) && (this.pageSize > 0);
  }

  public int getPageNo()
  {
    return this.pageNo;
  }

  public void setPageNo(int pageNo)
  {
    this.pageNo = pageNo;
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }

  public boolean isAutoCount()
  {
    return this.autoCount;
  }

  public void setAutoCount(boolean autoCount)
  {
    this.autoCount = autoCount;
  }
}