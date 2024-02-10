package com.wlj.sportgoods.sys.common;

public class    Constast {

    /**
     * 状态码  正常 200  错误  -1
     */
    public static final Integer OK=200;
    public static final Integer ERROR=-1;

    /**
     * 用户默认密码
     */
    public static final String USER_DEFAULT_PWD="123456";


    public static final String TYPE_PERMISSION = "permission";

    /**
     * 用户类型   0 超级管理员   1 系统用户
     */
    public static final Integer USER_TYPE_SUPER = 4;


    /**
     * 菜单是否展开 0不展开  1展开
     */
    public static final Integer OPEN_TRUE = 1;
    public static final Integer OPEN_FALSE = 0;

    /**
     * 商品默认图片
     */
    public static final String DEFAULT_IMG_GOODS = "/images/noDefaultImage.jpg";

    /**
     * hash散列次数
     */
    public static final Integer HASHITERATIONS = 2;

    /**
     * 用户默认图片
     */
    public static final String DEFAULT_IMG_USER="/images/defaultusertitle.jpg";
}
