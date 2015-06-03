package com.dangdang.digital.processor.ddreader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.mock.model.Book;
import com.dangdang.digital.mock.model.RankingList;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;
/**
 * 
 * @author lvxiang
 * @date   2015年5月15日 下午3:53:58
 * 专题列表接口
 */

@Component("hapispecialListprocessor")
public class SpecialListProcessor extends BaseApiProcessor {
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//榜单类型
		String listType ="";
		RankingListVo rlv = new RankingListVo();
		RankingList rankingList = new RankingList();
		rankingList.setListName("grb");
		rankingList.setListName("光荣榜");
		rlv.setList(rankingList);
		//书单里面的书籍信息
		List<Book> books = new ArrayList<Book>();
		Book b1 =  new Book();
		b1.setTitle("我讲个笑话,你可别……");
		b1.setDescription("是一本适合在除..以外的任何场合阅读的随笔集。全书包含三个部队....");
		b1.setCover("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		b1.setAuthor("张三丰");
		books.add(b1);
		
		Book b2 =  new Book();
		b2.setTitle("不畏将来,不怕过去……");
		b2.setDescription("222222是一本适合在除..以外的任何场合阅读的随笔集。全书包含三个部队....");
		b2.setAuthor("张三丰");
		b1.setCover("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		books.add(b2);
		rlv.setBooks(books);
		sender.put("rankingList", rlv);
		sender.success(response);
	}

}
