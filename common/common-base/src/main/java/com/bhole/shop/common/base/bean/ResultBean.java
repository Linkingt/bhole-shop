package com.bhole.shop.common.base.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * @program: bhole-shop-common-base
 * @description:
 * @author: joke
 * @date: 2023/5/30 13:27
 * @version: 1.0
 */
public class ResultBean<T> {

    /**
     * 是否成功
     */
    @JsonProperty(
            value = "success",
            index = 0
    )
    private boolean success;

    /**
     * 信息代码-国际化
     */
    private Integer code;

    /**
     * 操作消息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    /**
     * 错误消息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMsg;

    /**
     * http路径
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;

    /**
     * List结果集
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> resultList;

    /**
     * 单个结果
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T resultObject;

    /**
     * 翻页和结果集
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ListPager<T> listPager;

    /**
     * Map结果集
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> resultMap;

    /**
     * 翻页操作
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pager pager;

    /**
     * 总记录数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int totalCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long elapsedMilliseconds;

    private static final Integer SUCCESS = 200;

    private static final Integer FAILURE = 500;

    public boolean isSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getUrl() {
        return url;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public T getResultObject() {
        return resultObject;
    }

    public ListPager<T> getListPager() {
        return listPager;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public Pager getPager() {
        return pager;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public long getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    public void setElapsedMilliseconds(long elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    public ResultBean<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResultBean<T> setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public ResultBean<T> setResultList(List<T> resultList) {
        this.resultList = resultList;
        return this;
    }

    public ResultBean<T> setResultObject(T resultObject) {
        this.resultObject = resultObject;
        return this;
    }

    public ResultBean<T> setListPager(ListPager<T> listPager) {
        this.listPager = listPager;
        return this;
    }

    public ResultBean<T> setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
        return this;
    }

    public static ResultBean<Void> success(String msg){
        ResultBean<Void> rs = new ResultBean<>(true);
        rs.setMsg(msg);
        return rs;
    }

    public static ResultBean<Void> success(){
        return new ResultBean<Void>(true);
    }

    public static ResultBean<Void> fail(String msg){
        ResultBean<Void> rs =  new ResultBean(SUCCESS,false,msg);
        rs.setErrorMsg(msg);
        return rs;
    }
    public static ResultBean<Void> fail(Integer code, String msg){
        ResultBean<Void> rs =  new ResultBean(false,msg);
        rs.setErrorMsg(msg);
        return rs;
    }

    public static ResultBean<Void> buildResultBean(boolean success, String msg){
        ResultBean<Void> rs = new ResultBean();
        rs.success = success;
        if (success) {
            rs.msg = msg;
            rs.code = SUCCESS;
        } else {
            rs.code = FAILURE;
            rs.errorMsg = msg;
        }

        return rs;
    }

    public ResultBean() {
    }

    public ResultBean(boolean isSuccess) {
        success = isSuccess;
        if(success){
            //国际化
            code = SUCCESS;
        }else{
            code = FAILURE;
        }
    }

    public ResultBean(boolean success, List<T> resultList) {
        this.success = success;
        this.resultList = resultList;
    }

    public ResultBean(boolean success, T resultObject) {
        this.success = success;
        if(resultObject instanceof String){
            this.msg = resultObject.toString();
        }else{
            this.resultObject = resultObject;
        }
    }

    public ResultBean(Integer code, boolean success, T resultObject) {
        this.code = code;
        this.success = success;
        if(resultObject instanceof String){
            this.msg = resultObject.toString();
        }else{
            this.resultObject = resultObject;
        }
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", url='" + url + '\'' +
                ", resultList=" + resultList +
                ", resultObject=" + resultObject +
                ", listPager=" + listPager +
                ", resultMap=" + resultMap +
                ", pager=" + pager +
                ", totalCount=" + totalCount +
                '}';
    }
}
