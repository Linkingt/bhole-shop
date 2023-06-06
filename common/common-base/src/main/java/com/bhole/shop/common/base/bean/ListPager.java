package com.bhole.shop.common.base.bean;

import java.util.List;

/**
 * @program: bhole-shop-common-base
 * @description:
 * @author: joke
 * @date: 2023/5/30 13:41
 * @version: 1.0
 */
public class ListPager <T> {


    public ListPager() {
    }

    public ListPager(List<T> objectList, Pager objectPager) {
        this.objectList = objectList;
        this.objectPager = objectPager;
    }

    private List<T> objectList;
    private Pager objectPager;

    public List<T> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<T> objectList) {
        this.objectList = objectList;
    }

    public Pager getObjectPager() {
        return objectPager;
    }

    public void setObjectPager(Pager objectPager) {
        this.objectPager = objectPager;
    }
}
