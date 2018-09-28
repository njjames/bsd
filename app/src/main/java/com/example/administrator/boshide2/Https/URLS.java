package com.example.administrator.boshide2.Https;

/**
 * 网络请求接口
 * Created by Administrator on 2017-4-12.
 */


public class URLS {
//    public static String ocrUrl =    "http://www.jy-epc.com/api-show/NqAfterMarketDataServlet?operatorCode=hr@bossed.com.cn&operatorPwd=1549951fb018139c21fc84681306be78&requestCode=700102";
//    public static String ocrUrlxcb = "http://www.jy-epc.com/api-show/NqAfterMarketDataServlet?operatorCode=hr@bossed.com.cn&operatorPwd=1549951fb018139c21fc84681306be78&requestCode=700101";
    /**
     * 博士德王者之路
     */
    public static String WZZL = "http://bsdip.bsd102.com/ReturnIpM.aspx";
    /**
     *
     */
    //public static String BSD = "http://java.base.bossed.com.cn:1658/BSD/";
//    public static String BSD = "http://192.168.1.115:8888/";

//    public static String BSD = "http://192.168.1.222:8081/BSD/";

    //public static String BSD = "http://192.168.1.222:8081/BSD/";
    //public static String BSD="http://";
//    public static String BSD = "http://liouhongde.xicp.net:8083/";

//                                         198.168.16.83
//    public static String BSD = "http://192.168.1.120:8888/";
//
//    public static String BSD = "http://wdpbjd.bsd268.com:8888/";
//    http://wdpbjd.bsd268.com:8888/login/gongsi
//                                  http://wdpbjd.bsd268.com:8888/
//    public static String BSD = "http://wdpbjd.bsd268.com:8888/";
    //MyApplication.shared.getString("ip", "")
    public String BSD = "";


    /**

     public static String BSD = "http://192.168.1.111:8888/";
     // public static  String BSD="http://192.168.1.222:8081/BSD/";
     /**
     * 基类
     */

    // public static String BSD = "http://47.92.6.20:8080/BSD/";

//    /**
//     * 快速报价查询
//     */
//    public static String BSD_xinxi = BSD + "offer/xinxi";

    /**
     * 图片上传
     */
    public String BSD_upload_pic = "public/uploadFile";


    /**
     * 历史报价
     */
    public String BSD_LSBJ = "lsbj";


    /**
     * 维修项目
     */
    public String BSD_WXXM = "offer/xiangm";
    /**
     * 维修材料
     */
    public String BSD_WXCL = "offer/cail";
    /***
     * 车辆品牌
     */
    public String BSD_PINPAI = "public/pinpai";

    /***
     * 根据名称模糊查询车辆品牌
     */
    public String BSD_PINPAI_1 = "public/pinpai1";

    /**
     * 车系
     */
    public String BSD_CX = "public/chexi";
    /**
     * 车组
     */
    public String BSD_CZ = "public/chezu";
    /**
     * 车型
     */
    public String BSD_Chexing = "public/chexing";

    /**
     * 根据vin返回车型内码以及名称
     */
    public String BSD_getcxnm_byvin = "public/getvin";


    /**
     * 得到会员卡特殊配件价格
     */
    public String BSD_getcardpj_price = "public/getcardpj_price";
    /**
     * 判断车牌号和客户编码是否对应
     */
    public String BSD_kuaixiu_check = "kuaixiu/check";


    /**
     * 根据车型内码返回车牌、车系、车组、车型
     */
    public String BSD_getcx_byvindm = "public/getche";

    /**
     *查看配件的库存
     */
    public String BSD_wxcl_kc="public/peijandck";

    /**
     * 车辆信息BSD/car/clxx
     */
    public String BSD_CAR = "car/clxx";

    /**
     * 车辆信息---->维修历史
     */
    public String BSD_CL_WX = "lswx";
    /**
     * 车辆信息---->维修历史
     */
    public String BSD_CL_WX2 = "lsbj";

    /**
     * 历史维修
     */
    public String BSD_LSWX = "lswx";
    /**
     * 会员管理--已付款
     */
    public String BSD_HYGL_YFK = "card/CardXxy";
    /**
     * 会员管理---代付款
     */
    public String BSD_HYGL_DFK = "card/CardXxn";
    /**
     * 历史维修--维修项目明细
     */
    public String BSD_lswx_wxxm = "lswx/wxxm";
    /**
     * 历史维修--维修材料明细
     */
    public String BSD_lswx_wxcl = "lswx/cail";
    /**
     * 添加会员
     */
    public String BSD_HYGL_ADD = "card/add";
    /**
     * 添加会员---pop--部门下拉
     */
    public String BSD_HYGL_ADD_TYPE = "public/dept";
    /**
     * 添加会员---pop--部门下拉
     */
    public String BSD_HYGL_ADD_Jingbanren = "public/ITjb";

    /**
     * 卡类型
     */
    public String BSD_HYGL_ADD_KA_TYPE = "card/kxx";
    /**
     * 车牌
     */
    public String BSD_HYGL_ADD_che = "public/cheliang";

    /**
     * 修改车辆、客户信息
     */
    public String BSD_MRKX_SAVE_cheKehu = "public/updatejb";

    /**
     * 登陆页面请求公司
     */
    public String BSD_gongsi = "login/gongsi";
    /**
     * 登陆页面请求用户
     */
    public String BSD_user = "user/userList";
    /**
     * 登陆
     */
    public String BSD_login = "login";
    /**
     * 维修预约车牌输入后列表显示
     */
    public String BSD_wxyy_LieBiao = "yuyue/jbxx";
    /**
     * 维修预约单号输入项目显示
     */
    public String BSD_wxyy_xm = "yuyue/wxxm";
    /**
     * 维修预约车牌输入项目显示
     */
    public String BSD_XMXZ = "public/wxxm";
    /**
     * 维修预约右边数据
     */
    public String BSD_xmxz_reight = "public/wxnr";
    /**
     * 维修预约生成单号
     * yuyue/addjbxx
     */
    public String BSD_wxyy_DH = "yuyue/addjbxx";
    /**
     * 维修预约存档添加项目
     * /yuyue/addwxxm?
     */
    public String BSD_wxyy_CD = "yuyue/addwxxm";
    /**
     * 维修预约有订单号修改
     */
    public String BSD_wxyy_up = "yuyue/addjb";

    /**
     * 维修预约ft材料列表
     */
    public String BSD_wxyy_cl = "yuyue/cail";

    /**
     * 维修预约材料列表pop
     */
    public String BSD_wxyy_cllb = "public/cail";
    /**
     * 维修预约材料内容pop
     */
    public String BSD_wxyy_cainr = "public/cailnr";
    /**
     * 维修预约材料内容提交存档
     */
    public String BSD_wxyy_clcd = "yuyue/addcail";
    /**
     * 维修预约材料价钱更改显示
     */
    public String BSD_wxyy_xljq = "public/cailjg";
    /**
     * 维修预约根据车牌获取客户编号
     */
    public String BSD_wxyy_kehubianhao = "public/cheliang";

    /**
     * 获取基本价钱的id
     */
    public String BSD_JQ_ID = "public/jia";

    /**
     * 获取维修项目的单价
     */
    public String BSD_xm_dj = "public/wxxmjg";

    /**
     * 快速报价基本信息查询
     */
    public String BSD_ksbj_jbxx = "offer/xinxi";
    /**
     * 快速报价快速报价维修项目列表
     */
    public String BSD_ksbj_wxxm = "offer/xiangm";
    /**
     * 快速报价快速报价维材料列表
     */
    public String BSD_ksbj_wxcl = "offer/cail";
    /**
     * 快速报价基本信息添加
     */
    public String BSD_ksbj_jbxxtj = "offer/addjbxx";
    /**
     * 维修接单基本信息
     */
    public String BSD_wxjd_jbxx = "order/jbxx";

    /**
     * 维修历史主表信息
     */
    public String BSD_wxls_zbxx = "lswx/lswxxx";

    /**
     * 维修历史项目明细
     */
    public String BSD_wxls_xmmx = "lswx/lswxxmxx";


    /**
     * 维修历史用料明细
     */
    public String BSD_wxls_ylmx = "lswx/lswxylxx";



    /**
     * 维修建议主表信息
     */
    public String BSD_wxlsjy_zbxx = "lswx/lswxjyzb";



    /**
     * 维修建议项目明细
     */
    public String BSD_wxjy_xmmx = "lswx/lswxxmjy";


    /**
     * 维修用料明细
     */
    public String BSD_wxjy_ylmx = "lswx/lswxpjjy";




    /**
     * 维修接单进厂打印
     */
    public String BSD_wxjd_print = "order/dayin";


    /**
     * 美容快修发送微信
     */
    public String BSD_mrkx_weixin = "kuaixiu/kxweixin";


    /**
     * 维修接单发送微信
     */
    public String BSD_wxjd_weixin = "kuaixiu/wxjdweixin";


    /**
     * 完工发送微信
     */
    public String BSD_wg_weixin = "kuaixiu/wgweixin";

    /**
     * 上传图片
     */
    public String BSD_upload_image = "public/upload";

    /**
     * 删除图片
     */
    public String BSD_delete_image  = "public/deletefile";




    /**
     * 维修接单根据车牌返回车辆信息和客户信息
     */
    public String BSD_wxjd_clandkh = "public/clandkh";


    /**
     * 维修接单基本信息
     */
    public String BSD_wxjd_jbxxs = "order/jbxxXiangXi";
    /**
     * 快速报价添加材料
     */
    public String BSD_ksbj_tjcl = "offer/addcail";
    /**
     * 快速报价添加项目
     */
    public String BSD_ksbj_tjxm = "offer/addwxxm";
    /**
     * 快速报价添加项目
     */
    public String BSD_ksbj_bcjbxx = "offer/addjb";


    /**
     * 更新会员工时费
     */
    public String BSD_card_wxjd_xm = "public/getcardwxxm_price";


    /**
     * 维修接单项目列表
     */
    public String BSD_wxjd_xmlb = "order/wxxm";

    public String BSD_wxjd_lsxm="order/lswxxm";
    /**
     * 维修接单材料列表
     */
    public String BSD_wxjd_cllb = "order/cail";

    public String BSD_wxjd_lscl="order/lscail";
    /**
     * 维修接单基本信息添加
     */
    public String BSD_wxjd_jbxxtj = "order/addjb";
    /**
     * 维修接单添加项目
     */

    public String BSD_wxjd_addxm = "order/addwxxm";

    /**
     * 维修接单添加新的项目
     */

    public String BSD_wxjd_addnewxm = "order/addnewwxxm";

    /**
     * 维修接单添加新的配件
     */

    public String BSD_wxjd_addnewcl = "order/addnewwxcl";
    /**
     * 维修接单添加材料
     */

    public String BSD_wxjd_addcl = "order/addcail";
    /**
     * 快速报价进厂
     */

    public String BSD_ksbj_jc = "offer/jinchang";
    /**
     * 维修接单进厂
     */
    public String BSD_WXJD_jc = "order/wx_jinchang";
    /**
     * 维修预约
     */
    public String BSD_wxyy_yuls = "yuyue/lsyy";

    /**
     * 在场调度ListView
     */
    public String BSD_zcdu_list = "paigong/paiGongList";
    /**
     * 维修接单-->历史接单
     */
    public String BSD_LSJD = "order/lsjd";
//  /**
//     * 预约历史
//     */
//    public static  String BSD_wxyy_yuls=BSD+"yuyue/lsyy";

    /**
     * 价钱等级数据请求public/getJiaGeDengJi
     */
    public String BSD_JiaQian = "public/getJiaGeDengJi";
    /**
     * 价钱等级请求
     */
    public String BSD_JiaQian_BaoCun = "public/saveJia";
    /**
     * 根据车牌获取信息
     */
    /**
     * 修改密码
     */
    public String PSWEDIT = "user/pswdEdit";
    /**
     * 版本更新
     */
    public String BSD_BANBEN = "getAppVersion.html";

    /**
     * 根据车牌号获取客户信息
     */
    public String BSD_ChePaiXinXi = "card/getkehuByCheNo";
    /**
     * 整体派工
     */
    public String BSD_zhengTiPaiGong = "paigong/saveAllPgxx";

    /**
     * 整体派工
     */
    public String PaiGongAll = "paigong/paigongall";

    /**
     * 单项派工
     */
    public String BSD_danxiangPaiGong = "paigong/savePgxx";

    /**
     * 单项派工
     */
    public String PaiGongSingle = "paigong/paigongsingle";
    /**
     * 派工人员详细
     */
    public String BSD_PaiGong_XiangXi = "paigong/getPgxxByWxxm";
    /**
     * 删除所有派工
     */
    public String BSD_paigong_delPGAllRx = "paigong/delPGAllRx";

    /**
     * 派工删除
     */
    public String BSD_paigongdelPgxx = "paigong/delPgxx";

    /**
     * 车辆图片
     */
    public String BSD_Carphoto = "public/getCarImage";
    /**
     * 车辆图片
     */
    public String BSD_getImgUrl = "public/getImgUrl";
//    public String BSD_getImgUrl = "public/getBspicportno";
    /**
     * 修改工时单价价钱
     */
    public String BSD_GsDj_xiugai = "paigong/savePgJgAndJe";

    /**
     * 完工
     */
    public String BSD_WG = "paigong/wangGong";
    /**
     * 打印
     */
    public String BSD_DY = "paigong/print";
    /**
     * 打印
     */
    public String BSD_ZZ = "paigong/stop";
    /**
     * 修改预约时间
     */
    public String BSD_xiugaishijian = "yuyue/editsj";
    /**
     * 派工项目删除
     */
    public String BSD_deletXM = "paigong/diaoduDelwxxm";
    /**
     * 派工材料删除
     */
    public String BSD_deletCL = "paigong/diaoduDelcl";
    /**
     * 派工项目修改工时单价
     */
    public String BSD_upxm = "paigong/editWxxm";
    /**
     * 派工项目修改工时单价
     */
    public String BSD_mrkx_upxm = "paigong/editWxxmByhyzk";
    /**
     * 派工材料修改数量单价
     */
    public String BSD_upcl = "paigong/editYl";
    /**
     * 派工材料修改数量单价新
     */
    public String BSD_upclxin = "paigong/editYl_zcduwxylxgdj";


    /**
     * 精友数据
     */
    public String BSD_JY = "public/getJyUser";
    /**
     * 预约进厂
     */
    public String BSDyuYue_jinChang="yuyue/yuyyejinchang";

    /**
     * 快修输入车牌进入接口
     */
    public String BSD_MRKX_IN="kuaixiu/mrkxJbxx";
    /**
     * 快修输入baocun
     */
    public String BSD_MRKX_UP="kuaixiu/addMrkxJbxx";
    /**
     * 快修结算1111
     */
    public String BSD_MRKX_JieSuan1="kuaixiu/jiesuan_1";
    /**
     * 快修结算222
     */
    public String BSD_MRKX_JieSuan2="kuaixiu/jiesuan_2";

    /**
     * 快修结算333
     */
    public String BSD_MRKX_JieSuan3="kuaixiu/jiesuan_3";

    /**
     * 会员卡查询接口
     */
    public String BSD_MRKX_HYK="card/getVipCardByCardNo";

    /**
     * 根据操作员获取仓库
     */
    public String BSD_MRKX_CK="public/getCangKuByCaoZuoYuan";

//    public/getNeiMa

    /**
     * 获取
     */
    public String BSD_chexing_fansuan="public/getNeiMa";

    /**
     * 获取
     */
    public String BSD_meirongxiugai="paigong/editYl_mrkx";

    /**
     * 根据车型型号获取4级结构
     */
    public String BSD_public_getcheIDBycheXingMingCheng="public/getcheIDBycheXingMingCheng";
    /**
     * 根据车id
     * 获取整个字符串
     */
    public String BSD_public_get4JIjiegouMingcheng="public/get4JIjiegouMingcheng";
    /**
     * MY
     */
    public String BSD_MY_gywm="public/getGongSiJs";


    public static String CHECK_CAR_CANUSED = "public/carcanused";

}
