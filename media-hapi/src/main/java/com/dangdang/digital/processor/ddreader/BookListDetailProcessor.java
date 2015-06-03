package com.dangdang.digital.processor.ddreader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnMediaFactory;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.vo.ReturnMediaBaseVo;
import com.dangdang.digital.vo.ReturnMediaVo;
import com.dangdang.digital.vo.ReturnPaperMediaVo;
import com.dangdang.digital.vo.ReturnPublishedMediaVo;
/**
 * 
 * @author lvxiang
 * @date   2015年5月15日 下午3:53:58
 * 书单接口
 */
@Component("hapibookListDetailprocessor")
public class BookListDetailProcessor extends BaseApiProcessor {
	
	
	
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		
		
		List<ReturnMediaBaseVo> list = new ArrayList<ReturnMediaBaseVo>();
		
		ReturnMediaVo baseVo = ReturnMediaFactory.getEbookInstanceByType(2);
		setCommonFields(baseVo);
		
		ReturnPublishedMediaVo returnVo = (ReturnPublishedMediaVo) baseVo;
		returnVo.setMediaType(2);
		
		returnVo.setPrice(1233L);
		
		returnVo.setBorrowDurationTime(24*40*60*1000L);
		returnVo.setBorrowDuration(5*24*40*60*1000);
		returnVo.setBorrowStartTime(new Date().getTime());
		
		returnVo.setRenewStartTime(new Date().getTime());
		returnVo.setRenewDurationTime(24*40*60*1000L);
		returnVo.setSaleId(321321321L);
		returnVo.setShelfStatus(1);
		returnVo.setSpeaker("");
		//精装
		returnVo.setChapterCnt(28);
		returnVo.setCpShortName("纵横中文网");
		returnVo.setHasNew(0);
		returnVo.setIsBorrowed(1);
		returnVo.setIsbn("SDIOWFLS-SDFOI123");
		returnVo.setIsChapterAuthority(1);
		returnVo.setIsFull(1);
		returnVo.setIsSupportFullBuy(1);
		returnVo.setIsWholeAuthority(0);
		returnVo.setLastIndexOrder(1234);
		returnVo.setLastPullChapterDate(new Date().getTime());
		returnVo.setLastUpdateChapter("1234");
		returnVo.setMediaId(123123123L);
		
		returnVo.setMonthlyEndTime(1431302704000L);
		returnVo.setPaperMediaPrice(999L);
		returnVo.setPaperMediaProductId("1231233");
		returnVo.setPublisher("机械工业出版社");
		returnVo.setPublishDate(1431302704000L);
		returnVo.setPriceUnit(50);
		
		list.add(returnVo);
		
		ReturnMediaVo baseVo2 = ReturnMediaFactory.getEbookInstanceByType(1);
		ReturnMediaVo returnVo2 = (ReturnMediaVo) baseVo2;
		setCommonFields(baseVo2);
		
		returnVo2.setMediaType(1);
		
		returnVo2.setPrice(23233L);
		
		returnVo2.setSaleId(321321321L);
		returnVo2.setShelfStatus(1);
		returnVo2.setSpeaker("");
		//精装
		returnVo2.setChapterCnt(28);
		returnVo2.setCpShortName("纵横中文网");
		returnVo2.setHasNew(0);
		returnVo2.setIsChapterAuthority(1);
		returnVo2.setIsFull(1);
		returnVo2.setIsSupportFullBuy(1);
		returnVo2.setIsWholeAuthority(0);
		returnVo2.setLastIndexOrder(1234);
		returnVo2.setLastPullChapterDate(new Date().getTime());
		returnVo2.setLastUpdateChapter("1234");
		returnVo2.setMediaId(123123123L);
		returnVo2.setMonthlyEndTime(1431302704000L);
		returnVo2.setPriceUnit(50);
		list.add(returnVo2);
		
		
		ReturnPaperMediaVo mediaBaseVo = new ReturnPaperMediaVo();
		
		mediaBaseVo.setAuthorHeadPic("http://img37.ddimg.cn/imghead/32/26/7658402885627-1_e.png?1432185399146");
		mediaBaseVo.setAuthorId(213L);
		mediaBaseVo.setAuthorIntroduction("作者曾任中国劳动关系学院教师。主业：政治经济学，偏重政治学方面。1967年自愿赴内蒙古额仑草原插队。1978年返城。");
		mediaBaseVo.setAuthorPenname("姜戎");
		//精装
		mediaBaseVo.setBookReviewCount(89L);
		mediaBaseVo.setCategoryIds("XHQH");
		mediaBaseVo.setCategorys("小说");
		mediaBaseVo.setChannelId(1000023L);
		mediaBaseVo.setCoverPic("http://a.hiphotos.baidu.com/baike/g%3D0%3Bw%3D268/sign=a4cada3496dda144ca0969b9c58ae294/a50f4bfbfbedab64ab5233e7f636afc379311ef0.jpg");
		mediaBaseVo.setDescs("《狼图腾》是一部以狼为叙述主体的小说，讲述了上个世纪六七十年代一位知青在内蒙古草原插队时与草原狼、游牧民族相依相存的故事。1971年起腹稿于内蒙古锡盟东乌珠穆沁草原，1997年初稿于北京，2003年岁末定稿于北京，2004年4月出版。该书在中国出版后，被译为30种语言，在全球110个国家和地区发行。截至2014年4月，在中国大陆再版150多次，正版发行近500万册，连续6年蝉联文学图书畅销榜的前十名，获得各种奖项几十余种。海内外报刊和网络新媒体对《狼图腾》的研究论文和论著有上千篇、种。");
		mediaBaseVo.setMediaType(3);
		mediaBaseVo.setRecommandWords("故事的背景发生在20世纪60年代末，中国大陆内蒙古最后一块靠近边境的原始草原。这里的蒙古牧民还保留着游牧民族的生态特点，他们自由而浪漫地在草原上放养着牛、羊，与成群的强悍的草原狼共同维护着草原的生态平衡。他们憎恨着狼――狼是侵犯他们家园的敌人；他们同时也敬畏着狼――草原狼帮助蒙古牧民猎杀着草原上不能够过多承载的食草动物：黄羊、兔子和大大小小的草原鼠。草原狼是蒙古民族的原始图腾。狼的凶悍、残忍、智慧和团队精神，狼的军事才能和组织分工，曾经是13世纪蒙古军队征战欧亚大陆的天然教官和进化的发动机。");
		mediaBaseVo.setScore(5F);
		mediaBaseVo.setSubTitle("");
		mediaBaseVo.setTitle("狼图腾");
		mediaBaseVo.setWordCnt(585858L);
		mediaBaseVo.setChapterCnt(50);
			
		mediaBaseVo.setChannelId(1000023L);
		mediaBaseVo.setEbookSaleId(1980000221L);
		mediaBaseVo.setHasEbook(1);
		mediaBaseVo.setIsbn("SDIOWFLS-SDFOI123");
		mediaBaseVo.setMediaType(3);
		
		mediaBaseVo.setOriginalPrice(1899L);
		
		mediaBaseVo.setPublisher("机械工业出版社");
		mediaBaseVo.setPrice(999L);
		mediaBaseVo.setProductId("52164589");
		mediaBaseVo.setPublishDate(1431302704000L);
		
		list.add(mediaBaseVo);
		
		int start = SafeConvert.convertStringToInteger(request.getParameter("start"), 0);
		int end = SafeConvert.convertStringToInteger(request.getParameter("end"), 10);
		
		end = end+1;
		
		if( start>list.size()-1 || end <0){
			sender.put("mediaList", new ArrayList<ReturnMediaBaseVo>());
		}else {
			if( end > list.size()){
				end = list.size();
			}else if( start<0 ){
				start = 0 ;
			}
			sender.put("mediaList", list.subList(start, end));
		}
		
		sender.put("total", list.size());
		sender.success(response);
	}
	
	private void setCommonFields(ReturnMediaBaseVo mediaBaseVo) {
		
		mediaBaseVo.setAuthorHeadPic("http://img37.ddimg.cn/imghead/32/26/7658402885627-1_e.png?1432185399146");
		mediaBaseVo.setAuthorId(213L);
		mediaBaseVo.setAuthorIntroduction("作者曾任中国劳动关系学院教师。主业：政治经济学，偏重政治学方面。1967年自愿赴内蒙古额仑草原插队。1978年返城。");
		mediaBaseVo.setAuthorPenname("姜戎");
		
		//精装
		mediaBaseVo.setBookReviewCount(89L);
		mediaBaseVo.setCategoryIds("XHQH");
		mediaBaseVo.setCategorys("小说");
		mediaBaseVo.setChannelId(1000023L);
		
		mediaBaseVo.setCoverPic("http://a.hiphotos.baidu.com/baike/g%3D0%3Bw%3D268/sign=a4cada3496dda144ca0969b9c58ae294/a50f4bfbfbedab64ab5233e7f636afc379311ef0.jpg");
		mediaBaseVo.setDescs("《狼图腾》是一部以狼为叙述主体的小说，讲述了上个世纪六七十年代一位知青在内蒙古草原插队时与草原狼、游牧民族相依相存的故事。1971年起腹稿于内蒙古锡盟东乌珠穆沁草原，1997年初稿于北京，2003年岁末定稿于北京，2004年4月出版。该书在中国出版后，被译为30种语言，在全球110个国家和地区发行。截至2014年4月，在中国大陆再版150多次，正版发行近500万册，连续6年蝉联文学图书畅销榜的前十名，获得各种奖项几十余种。海内外报刊和网络新媒体对《狼图腾》的研究论文和论著有上千篇、种。");
//		mediaBaseVo.setMediaType(3);
		mediaBaseVo.setRecommandWords("故事的背景发生在20世纪60年代末，中国大陆内蒙古最后一块靠近边境的原始草原。这里的蒙古牧民还保留着游牧民族的生态特点，他们自由而浪漫地在草原上放养着牛、羊，与成群的强悍的草原狼共同维护着草原的生态平衡。他们憎恨着狼――狼是侵犯他们家园的敌人；他们同时也敬畏着狼――草原狼帮助蒙古牧民猎杀着草原上不能够过多承载的食草动物：黄羊、兔子和大大小小的草原鼠。草原狼是蒙古民族的原始图腾。狼的凶悍、残忍、智慧和团队精神，狼的军事才能和组织分工，曾经是13世纪蒙古军队征战欧亚大陆的天然教官和进化的发动机。");
		
		mediaBaseVo.setScore(5F);
		mediaBaseVo.setSubTitle("");
		mediaBaseVo.setTitle("狼图腾");
		mediaBaseVo.setWordCnt(585858L);
	}

}
