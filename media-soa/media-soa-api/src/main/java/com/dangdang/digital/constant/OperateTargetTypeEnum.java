/**
 * Description: OperateTargetTypeEnum.java
 * All Rights Reserved.
 * @version 1.0  2015年1月14日 下午4:40:40  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.constant;

import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ActivitySale;
import com.dangdang.digital.model.ActivityType;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.Block;
import com.dangdang.digital.model.BlockGroup;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.ColumnContent;
import com.dangdang.digital.model.ConsumerConsume;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.model.ContentProvider;
import com.dangdang.digital.model.CpContract;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.model.ListCategory;
import com.dangdang.digital.model.ListRanking;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaResource;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.OrderMain;
import com.dangdang.digital.model.Prize;
import com.dangdang.digital.model.RankConsToBook;
import com.dangdang.digital.model.ResourceDirectory;
import com.dangdang.digital.model.Role;
import com.dangdang.digital.model.Usercms;

/**
 * Description:操作日志操作对象类型枚举类 
 * All Rights Reserved.
 * @version 1.0  2015年1月14日 下午4:40:40  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public enum OperateTargetTypeEnum {

	CONSUMER_DEPOSIT("充值记录","consumer_deposit",ConsumerDeposit.class.getName()),
	ACTIVITY_INFO("促销活动","activity_info",ActivityInfo.class.getName()),
	ACTIVITY_SALE("活动商品","activity_sale",ActivitySale.class.getName()),
	ACTIVITY_TYPE("活动类型","activity_type",ActivityType.class.getName()),
	AUTHOR("作者信息","author",Author.class.getName()),
	BLOCK("块信息","block",Block.class.getName()),
	BLOCK_GROUP("块组信息","block_group",BlockGroup.class.getName()),
	CATETORY("分类信息","catetory",Catetory.class.getName()),
	CHAPTER("章节信息","chapter",Chapter.class.getName()),
	COLUMN("栏目信息","column",Column.class.getName()),
	COLUMN_CONTENT("栏目内容","column_content",ColumnContent.class.getName()),
	CONSUMER_CONSUME("消费记录","consumer_consume",ConsumerConsume.class.getName()),
	CONTENT_PROVIDER("CP信息","content_provider",ContentProvider.class.getName()),
	CP_CONTRACT("CP合同","cp_contract",CpContract.class.getName()),
	DISCOVERY("封面信息","discovery",Discovery.class.getName()),
	LIST_RANKING("销售榜单","list_ranking",ListRanking.class.getName()),
	LIST_CATEGORY("榜单分类","list_category",ListCategory.class.getName()),
	MEDIA("作品信息","media",Media.class.getName()),
	MEDIA_SALE("销售实体","media_sale",MediaSale.class.getName()),
	ORDER_MAIN("订单信息","order_main",OrderMain.class.getName()),
	PRIZE("奖品信息","prize",Prize.class.getName()),
	RANK_CONS_TO_BOOK("奖品信息","rank_cons_to_book",RankConsToBook.class.getName()),
	ROLE("后台角色","role",Role.class.getName()),
	RESOURCE_DIRECTORY("资源目录","resource_directory",ResourceDirectory.class.getName()),
	RESOURCE("资源目录","resource",MediaResource.class.getName()),
	USER_CMS("后台用户","user_cms",Usercms.class.getName());
	
	private String name;
	private String type;
	private String className;
	private OperateTargetTypeEnum(String name,String type,String className){
		this.name = name;
		this.type = type;
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getClassName() {
		return className;
	}
	
}
