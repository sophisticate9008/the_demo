package com.wlj.sportgoods.sys.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {
    private Integer code;
    private String msg;

    public static final ResultObj Nostock = new ResultObj(Constast.ERROR,"商品无货");

    public static final ResultObj LOGIN_SUCCESS= new ResultObj(Constast.OK,"登陆成功");
    public static final ResultObj LOGIN_ERROR_PASS= new ResultObj(Constast.ERROR,"用户名或密码错误");
    public static final ResultObj LOGIN_ERROR_CODE= new ResultObj(Constast.ERROR,"验证码错误");
    public static final ResultObj LOGIN_ERROR_BAN= new ResultObj(Constast.ERROR,"用户当前不可登录"); 

    public static final ResultObj BUY_SUCCESS = new ResultObj(Constast.OK,"交易成功");
    public static final ResultObj BUY_ERROR = new ResultObj(Constast.ERROR,"交易失败");

    public static final ResultObj OP_SUCCESS = new ResultObj(Constast.OK,"操作成功");
    public static final ResultObj OP_ERROR = new ResultObj(Constast.ERROR,"操作失败");

    

    public static final ResultObj ADD_SUCCESS = new ResultObj(Constast.OK,"添加成功");
    public static final ResultObj ADD_ERROR = new ResultObj(Constast.ERROR,"添加失败");

    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constast.OK,"删除成功");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constast.ERROR,"删除失败");

    public static final ResultObj REGISTER_SUCCESS = new ResultObj(Constast.OK,"注册成功");
    public static final ResultObj REGISTER_ERROR = new ResultObj(Constast.ERROR,"注册失败");

    public static final ResultObj UPDATE_SUCCESS = new ResultObj(Constast.OK,"修改成功");
    public static final ResultObj UPDATE_ERROR = new ResultObj(Constast.ERROR,"修改失败");

    public static final ResultObj RESET_SUCCESS = new ResultObj(Constast.OK,"重置成功");
    public static final ResultObj RESET_ERROR = new ResultObj(Constast.ERROR,"重置失败");

    public static final ResultObj DISPATCH_SUCCESS = new ResultObj(Constast.OK,"分配成功");
    public static final ResultObj DISPATCH_ERROR = new ResultObj(Constast.ERROR,"分配失败");

    public static final ResultObj EXCEED_PERMISSION = new ResultObj(Constast.ERROR, "越权操作");

    public static final ResultObj BACKINPORT_SUCCESS = new ResultObj(Constast.OK,"退货成功");
    public static final ResultObj BACKINPORT_ERROR = new ResultObj(Constast.ERROR,"退货失败");
    public static final ResultObj SYNCCACHE_SUCCESS = new ResultObj(Constast.OK,"同步缓存成功");

}
