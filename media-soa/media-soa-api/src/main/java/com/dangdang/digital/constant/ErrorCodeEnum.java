package com.dangdang.digital.constant;

public enum ErrorCodeEnum {

	/* 全局 10000-11000 */
	/**
	 * 未知错误！
	 */
	ERROR_CODE_10000(10000, "未知错误！"),
	
	/**
	 * 网络错误!
	 */
	ERROR_CODE_10001(10001, "网络错误!"),
	/**
	 * 参数非法!
	 */
	ERROR_CODE_10002(10002, "参数非法!"),
	/**
	 * 未登录用户
	 */
	ERROR_CODE_10003(10003, "未登录用户！"),
	/**
	 * 没有权限！
	 */
	ERROR_CODE_10004(10004, "没有权限！"),
	
	/**
	 * 系统没有配置相应参数
	 */
	ERROR_CODE_10005(10005, "系统没有配置相应参数！"),
	
	/**
	 * 参数内容不存在
	 */
	ERROR_CODE_10006(10006, "参数内容不存在！"),
	
	
	ERROR_CODE_10007(10007, "没有数据！"),
	/**
	 * 服务器内容已经被上次请求删除
	 */
	ERROR_CODE_10008(10008, "不存在,已删除！"),
	ERROR_CODE_10009(10009, "书籍已经下架"),
	ERROR_CODE_10010(10010, "销售主体已经下架"),
	ERROR_CODE_10011(10011, "未找到所请求的action"),
	
	/* 书城 11001-12000 */
	/**
	 * 根据栏目标识,没有取到数据
	 */
	ERROR_CODE_11002(11002,"没有取到栏目数据"),
	
	ERROR_CODE_11003(11003,"根据栏目编号查询缓存出错"),
	
	ERROR_CODE_11004(11004,"没有取到榜单数据"),
	
	ERROR_CODE_11005(11005,"火辣限免没有数据"),
	
	ERROR_CODE_11006(11006,"此条目已收藏"),
	
	ERROR_CODE_11007(11007,"不存在的公告类型标识"),
	ERROR_CODE_11008(11008,"公告类型下没内容"),
	
	ERROR_CODE_11009(11009,"该分类已经禁用"),
	
	ERROR_CODE_11100(11100,"专题没有关系栏目"),
	
	/*
	 * 根据块标识，获取块   【11101-11200】
	 */
	ERROR_CODE_11102(11102,"块标识不存在"),

	/* 书架 12001-13000 */
	/**
	 * HTML下载方式不能下载多章!
	 */
	ERROR_CODE_12001(12001, "HTML下载方式不能下载多章!"),
	ERROR_CODE_12002(12002, "查询不到资源或章节!"),
	ERROR_CODE_12003(12003, "加解密出错！"),

	/* 购物车 13001-14000 */
	
	/* 买了又买 14001-14300*/
	ERROR_CODE_14001(14001, "mediaId参数非法!"),
	ERROR_CODE_14002(14002, "start参数非法!"),
	ERROR_CODE_14003(14003, "end参数非法!"),
	ERROR_CODE_14004(14004, "token参数非法!"),
	/* 看了又看 14301-14600*/
	ERROR_CODE_14301(14301, "mediaId参数非法!"),
	ERROR_CODE_14302(14302, "start参数非法!"),
	ERROR_CODE_14303(14303, "end参数非法!"),
	ERROR_CODE_14304(14304, "token参数非法!"),
	
	/* 猜你喜欢 14601-15000*/	
	ERROR_CODE_14601(14601, "token参数非法!"),
	ERROR_CODE_14602(14602, "start参数非法!"),
	ERROR_CODE_14603(14603, "end参数非法!"),
	
	
	/* 福袋【抽奖】 15001-15300*/
	ERROR_CODE_15001(15001, "没有抽奖次数!"),
	ERROR_CODE_15002(15002, "奖品超过日限制!"),
	ERROR_CODE_15003(15003, "奖品超过总限制!"),
	ERROR_CODE_15004(15004, "金币奖励接口发放失败!"),
	ERROR_CODE_15005(15005, "章节奖励接口发放失败!"),
	ERROR_CODE_15006(15006, "道具奖励接口发放失败!"),
	ERROR_CODE_15007(15007, "包月奖励接口发放失败!"),
	ERROR_CODE_15008(15008, "不存在此用户!"),
	ERROR_CODE_15009(15009, "有效奖品不足3个!"),
	ERROR_CODE_15010(15010, "调用发奖接口失败!"),
	ERROR_CODE_15011(15011, "获取福袋道具列表失败!"),
	ERROR_CODE_15200(15200, "分享系统错误!"),
	ERROR_CODE_15201(15201, "custId为空!"),
	
	/* 膜拜 15301-15500*/
	ERROR_CODE_15301(15301, "操作成功!"),
	ERROR_CODE_15302(15302, "这么任性？不能膜拜自己呦!"),
	ERROR_CODE_15303(15303, "超过该日限制的膜拜数，明日再来吧!"),
	ERROR_CODE_15304(15304, "膜拜给辅账号加钱，交易失败!"),
	ERROR_CODE_15400(15400, "膜拜系统错误!"),
	
	/* 分享 15501-15700*/
	ERROR_CODE_15501(15501, "操作成功!获得福袋，获得红包。"),//1-1
	ERROR_CODE_15502(15502, "操作成功!获得福袋，没获得红包。"),//1-0
	ERROR_CODE_15503(15503, "操作成功!没获得福袋，获得红包。"),//0-1
	ERROR_CODE_15504(15504, "操作成功!没获得福袋，没获得红包。"),//0-0
	ERROR_CODE_15505(15505, "福袋超日限，红包超日限!"),
	ERROR_CODE_15506(15506, "福袋超日限，红包没超日限!"),
	ERROR_CODE_15507(15507, "福袋没超日限，红包超日限!"),
	ERROR_CODE_15508(15508, "福袋没超日限，红包没超日限!"),
	ERROR_CODE_15511(15511, "红包没有了!"),
	ERROR_CODE_15512(15512, "用户的红包超过每日获取的限制!"),
	ERROR_CODE_15513(15513, "给辅账号加红包，交易失败!"),
	ERROR_CODE_15600(15600, "分享系统错误!"),
	
	/*打赏 */
	ERROR_CODE_15800(15800, "打赏消费错误，交易失败!"),
	ERROR_CODE_15801(15800, "现实比理想骨感，余额不足耶~"),
	ERROR_CODE_15802(15800, "打赏君出现错误了耶 ~ 稍候再试试好不好？"),
	
	
	/*每日领福袋 */
	ERROR_CODE_15810(15810, "今天领过福袋了!"),
	/*撒金币 */
	ERROR_CODE_15820(15820, "人家不能重复被捡了啦~"),
	ERROR_CODE_15821(15821, "超出今日上限，捡不了人家了啦~"),
	
	/* 活动榜单相关 17001-18000*/
	ERROR_CODE_17801(17801, "获取数量不在【1--500】直接!"),
	ERROR_CODE_17802(17802, "不是单品的saleId!"),
	
	
	
	/* 充值购买  18001-19000*/
	ERROR_CODE_18001(18001, "用户交易失败!"),
	ERROR_CODE_18002(18002, "商品不可购买!"),
	ERROR_CODE_18003(18003, "获取最近一次支付方式失败!"),
	ERROR_CODE_18004(18004, "查询用户充值记录失败!"),
	ERROR_CODE_18005(18005, "查询用户余额失败!"),
	ERROR_CODE_18006(18006, "获取充值方式选择列表失败！"),
	ERROR_CODE_18007(18007, "获取购买包月页面数据失败！"),
	ERROR_CODE_18008(18008, "首次登陆送包月失败！"),
	ERROR_CODE_18009(18009, "首次分享送包月失败！"),
	ERROR_CODE_18010(18010, "ios支付校验失败！"),
	ERROR_CODE_18011(18011, "MD5参数签名校验失败！"),
	/* 书评、弹幕 19001-20000*/
	ERROR_CODE_19001(19001, "添加评论失败!"),
	ERROR_CODE_19002(19002, "书评点赞失败!"),
	ERROR_CODE_19003(19003, "添加书评回复失败!"),
	ERROR_CODE_19004(19004, "该用户已经点过赞!"),
	ERROR_CODE_19101(19101, "添加弹幕失败!"),
	
	
	/** 发现20000-21000*/
	ERROR_CODE_20001(20001, "查询不到发现!"),
	ERROR_CODE_20002(20002, "添加发现评论出错!"),
	
	/** 百度云推送 21001-22000*/
	ERROR_CODE_21001(21001, "appId不能为空!"),
	ERROR_CODE_21002(21002, "extUserId不能为空!"),
	ERROR_CODE_21003(21003, "extChannelId不能为空!"),
	ERROR_CODE_21004(21004, "deviceSerialNo不能为空!"),
	ERROR_CODE_21006(21006, "clientVersionNo不能为空!"),
	ERROR_CODE_21007(21007, "deviceTypeNo不能为空!"),
	ERROR_CODE_21008(21008, "channelId不能为空!"),
	ERROR_CODE_21009(21009, "deviceTypeNo不合法!"),
	ERROR_CODE_21010(21010, "planId不能为空!"),
	ERROR_CODE_21011(21011, "planId不合法!"),
	ERROR_CODE_21012(21012, "appId不合法!"),
	ERROR_CODE_21013(21013, "mediaId不能为空!"),
	ERROR_CODE_21014(21014, "mediaId不合法!"),
	ERROR_CODE_21015(21015, "operationType不能为空!"),
	ERROR_CODE_21016(21016, "operationType不合法!"),
	ERROR_CODE_21017(21017, "notificationStatus不能为空!"),
	ERROR_CODE_21018(21018, "notificationStatus不合法!"),
	ERROR_CODE_21019(21019, "该appId下此设备号的记录不存在!"),

	/** 精品阅读22001-23000 */
	ERROR_CODE_22001(22001, "阅读详情不存在!"),
	ERROR_CODE_22002(22002, "不支持此次请求!"),
	ERROR_CODE_22003(22003, "文章已存在，不要重复收藏哦!"),
	ERROR_CODE_22004(22004, "取消收藏失败!"),
	ERROR_CODE_22005(22005, "文集名称不超过16个字符长度!"),
	ERROR_CODE_22006(22006, "文集名称只能输入中文字母数字等字符哦!"),
	ERROR_CODE_22007(22007, "您已使用该名称，换个名字吧!"),
	ERROR_CODE_22008(22008, "新建文集失败，请重试!"),
	ERROR_CODE_22009(22009, "该文集不存在!"),
	ERROR_CODE_22010(22010, "修改文集失败，请重试!"),
	ERROR_CODE_22011(22011, "删除文集或文章失败，请重试!"),
	ERROR_CODE_22012(22012, "对不起，您没有编辑该文集的权限!"),
	ERROR_CODE_22013(22013, "上传图片失败，请重试!"),
	ERROR_CODE_22014(22014, "获取图片失败，请重试!"),
	ERROR_CODE_22015(22015, "对不起，您没有删除该文集或文章的权限!"),
	ERROR_CODE_22016(22016, "对不起，您没有查看该文集的权限!"),
	ERROR_CODE_22017(22017, "该篇儿文章不存在!"),
	ERROR_CODE_22018(22018, "评论内容至少输入2个字符!"),
	ERROR_CODE_22019(22019, "评论内容不能多于600个字符!"),
	ERROR_CODE_22020(22020, "发表评论失败，请重试!"),
	ERROR_CODE_22021(22021, "亲，您因发布恶意内容，已被禁止发言!"),
	ERROR_CODE_22022(22022, "亲，请不要频繁发布评论，先休息会儿吧!"),
	ERROR_CODE_22023(22023, "该评论不存在!"),
	ERROR_CODE_22024(22024, "亲，您发表评论内容包含不合法关键字，请重新输入!"),
	ERROR_CODE_22025(22025, "请输入合法的电话号码或邮箱，做为联系方式!"),
	ERROR_CODE_22026(22026, "请选择图片上传!"),
	ERROR_CODE_22027(22027, "输入内容不超过500个字符哦!"),
	ERROR_CODE_22028(22028, "请选择待加入文集的文章!"),
	ERROR_CODE_22029(22029, "文章加入文集失败，请重试!"),
	ERROR_CODE_22030(22030, "文章已在该文集中，请不要重复添加!"),
	ERROR_CODE_22031(22031, "您已经赞过了"),
	
	/** 与精品阅读重复,修改 lvxiang**/
	/** 塞红包活动  23001-24000 */
	ERROR_CODE_23001(23001, "receiver参数非法!"),
	ERROR_CODE_23002(23002, "不能给自己塞红包!"),
	ERROR_CODE_23003(23003, "type参数非法!"),
	ERROR_CODE_23004(23004, "amount参数非法!"),
	ERROR_CODE_23005(23005, "塞金币消费失败!"),
	ERROR_CODE_23006(23006, "塞金币充值失败!"),
	ERROR_CODE_23007(23007, "创建个人消息失败!"),
	ERROR_CODE_23008(23008, "扣除福袋失败, 福袋数量不足!"),
	ERROR_CODE_23009(23009, "增加福袋失败!"),
	ERROR_CODE_23010(23010, "deviceType参数非法"),
	/** 塞红包活动子项目之微信授权  **/
	ERROR_CODE_23011(23011, "参数错误"),
	ERROR_CODE_23012(23012, "获得微信信息失败"),
	
	/** 银铃铛有效期 **/
	ERROR_CODE_24001(24001, "sequenceWay非法"),
	ERROR_CODE_24002(24002, "lastAttachItemId非法");
	
	private Integer errorCode;
	private String errorMessage;

	private ErrorCodeEnum(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public static ErrorCodeEnum getByErrorCode(int errorCode){
		for(ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()){
			if(errorCodeEnum.errorCode.intValue() == errorCode){
				return errorCodeEnum;
			}
		}
		return null;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
