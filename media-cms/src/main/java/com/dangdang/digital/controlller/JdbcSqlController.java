/**
 * Description: JdbcSqlController.java
 * All Rights Reserved.
 * @version 1.0  2015年2月3日 下午3:57:04  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.controlller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dangdang.digital.service.IJdbcSqlService;

/**
 * Description: 自定sql查询
 * All Rights Reserved.
 * @version 1.0  2015年2月3日 下午3:57:04  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Controller
@RequestMapping("jdbcSql")
public class JdbcSqlController extends BaseController {

	@Resource
	private IJdbcSqlService jdbcSqlService;
	
	@RequestMapping("list")
	public String list(Model model, String sql, String operateFlag) {
		if (StringUtils.isNotBlank(operateFlag) && operateFlag.equals("1")) {
			if (StringUtils.isBlank(sql)) {
				model.addAttribute("errorMsg", "骚年，你咋啥也不写就让俺执行呢！");
			} else {
				String sqlStr = sql.toLowerCase().trim();
				if (!sqlStr.startsWith("select")) {
					model.addAttribute("errorMsg", "骚年，偷偷用线上数据库，只可以查询，不可以嚣张的！");
				} else {
					try {
						model.addAttribute("resultList",
								jdbcSqlService.queryList(sqlStr));
					} catch (Exception e) {
						e.printStackTrace();
						model.addAttribute("errorMsg", "骚年，dba把咱封杀了，默哀吧！");
					}

				}
			}
			model.addAttribute("vo", sql);
		}
		return "jdbcSql/list";
	}
}
