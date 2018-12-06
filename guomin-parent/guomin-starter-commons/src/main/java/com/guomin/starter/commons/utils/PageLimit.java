package com.guomin.starter.commons.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PageLimit<T> implements Iterable<T>, Serializable {
    private static final long serialVersionUID = -3720998571176536865L;  
    private List<T> context = new ArrayList<>();

    //总记录数
    private long total;  
    private int pageNo;  
    private int pageSize;

    //是否有前一页
    private Boolean previousStatus;

    //是否有下一页
    private Boolean nextStatus;

    //是否是第一页
    private Boolean firstStatus;

    //总页数
    private Integer totalPage;

    //是否有记录
    private Boolean contentStatus;


    public PageLimit() {
        super();
    }  
  
    public PageLimit(List<T> content, long total, int pageNo, int pageSize) {
        this.context = content;
        this.total = total;  
        this.pageNo = pageNo;  
        this.pageSize = pageSize;
        this.previousStatus=pageNo >1?true :false;
        this.firstStatus = !previousStatus;
        this.totalPage = pageSize == 0 ? 1 : (int) Math.ceil((double) total / (double) getPageSize());
        this.nextStatus = pageNo < totalPage ? true:false;
        this.contentStatus = content.size()>0 ? true:false;
    }


    public long getTotal() {
        return total;  
    }  
  
    public void setTotal(long total) {  
        this.total = total;  
    }  
  

    public int getTotalPage() {  
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }


    public List<T> getContext() {
        return Collections.unmodifiableList(context);
    }  
  
    public void setContext(List<T> context) {
        this.context = context;
    }


    public int getDataSize() {
        return context.size();
    }  
  

    public int getPageSize() {  
        return pageSize;  
    }  
  
    public void setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
    }  
  

    public int getPageNo() {  
        return pageNo;  
    }  
  

    public void setPageNo(int pageNo) {  
        this.pageNo = pageNo;  
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(Boolean previousStatus) {
        this.previousStatus = previousStatus;
    }

    public Boolean getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(Boolean nextStatus) {
        this.nextStatus = nextStatus;
    }

    public Boolean getFirstStatus() {
        return firstStatus;
    }

    public void setFirstStatus(Boolean firstStatus) {
        this.firstStatus = firstStatus;
    }

    public Boolean getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(Boolean contentStatus) {
        this.contentStatus = contentStatus;
    }

    @Override
    public Iterator<T> iterator() {
        return getContext().iterator();
    }  
}  