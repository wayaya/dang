package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.dangdang.digital.listenbook.model.ListenBookChapter;
import com.dangdang.digital.listenbook.model.ListenBookMedia;
import com.dangdang.digital.listenbook.model.PicUpCdnObj;
import com.dangdang.digital.listenbook.service.PicUpCdnThreadPoolExecutorService;
import com.dangdang.digital.listenbook.task.PicUpCdnSychTask;
import com.dangdang.digital.listenbook.util.ListenBookMediaConvertUtil;
import com.dangdang.digital.listenbook.util.TransactionTemplateUtils;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.CpPullChapter;
import com.dangdang.digital.model.CpPullMedia;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaRelation;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.IAuthorService;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.ICpPullChapterService;
import com.dangdang.digital.service.ICpPullMediaService;
import com.dangdang.digital.service.IDDCpCatetoryMapService;
import com.dangdang.digital.service.IListenBookDataSyncService;
import com.dangdang.digital.service.IMediaRelationService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;

@Service(value = "listenBookDataSyncService")
public class ListenBookDataSyncServiceImpl implements IListenBookDataSyncService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ListenBookDataSyncServiceImpl.class);
	@Resource
	private IMediaSaleService mediaSaleService;
	@Resource
	private IAuthorService authorService;
	@Resource
	private IMediaService mediaService;
	@Resource
	private IMediaRelationService mediaRelationService;
	
	@Resource
	private IChapterService chapterService;

	@Resource
	private ICatetoryService catetoryService;
	
	@Resource
	private ICpPullMediaService cpPullMediaService;
	@Resource
	private ICpPullChapterService cpPullChapterService;
	@Resource
	private IDDCpCatetoryMapService dDCpCatetoryMapService;
	
	@Resource
	private PicUpCdnThreadPoolExecutorService picUpCdnThreadPoolExecutorService;
	
	@Resource
	private PlatformTransactionManager transactionManager;

	@Override
	public int saveMedia(final ListenBookMedia lbMedia) {
		//insert media
		final Media media = ListenBookMediaConvertUtil.lbMedia2Media(lbMedia);
		try {
			if (media != null) {
				// A new thread pool is created...
				ThreadPoolExecutor executor = picUpCdnThreadPoolExecutorService
						.getPicUpCdnThreadPoolExecutor();
				executor.allowCoreThreadTimeOut(true);

				final PicUpCdnObj picUpCdnObj = new PicUpCdnObj();

				TransactionTemplate transactionTemplate = TransactionTemplateUtils
						.getDefaultTransactionTemplate(transactionManager);
				transactionTemplate
						.execute(new TransactionCallbackWithoutResult() {
							@Override
							protected void doInTransactionWithoutResult(
									TransactionStatus status) {
								
								mediaService.save(media);
								lbMedia.setDdMediaId(media.getMediaId());

								//异步upPicToCDN
								picUpCdnObj.setImgContent(lbMedia
										.getImageContent());
								picUpCdnObj.setMediaId(media.getMediaId());
								picUpCdnObj.setRetryNum(0);

								//insert media_book_catetory
								for (String str : lbMedia.getSelfCategorys()) {
									if (str != null && !str.equals("")) {
										int catetoryId = Integer.parseInt(str);
										media.setCatetoryId(catetoryId);
										mediaService.saveCatetorys(media);
									}
								}

								//insert media_relation
								MediaRelation target = new MediaRelation();
								target.setMediaId(media.getMediaId());
								target.setSaleId(lbMedia.getSaleId());
								mediaRelationService.save(target);

								//insert cpPullMedia
								CpPullMedia cpPullMedia = ListenBookMediaConvertUtil
										.lbMedia2CpPullMedia(lbMedia);
								if (cpPullMedia != null) {
									cpPullMediaService.save(cpPullMedia);
								}
							}
						});

				executor.execute(new PicUpCdnSychTask(picUpCdnObj,
						picUpCdnThreadPoolExecutorService));
			} else {
				return 0;
			}
		} catch (Exception e) {
			LOGGER.error("insert media's relation table has error . cpMediaId|albumId="+lbMedia.getAlbumId(),e);
			return 0;
		}
		return 1;
	}

	@Override
	public int saveMediaOfChapter(final ListenBookMedia lbMedia) {
		try {
			TransactionTemplate transactionTemplate = TransactionTemplateUtils.getDefaultTransactionTemplate(transactionManager);
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//insert chapter ,then get Cp2DdChapterIdMap
					Map<String,String> cp2DdChapterId = new HashMap<String,String>();
//        		saveChapterRetCp2DdChapterIdMap(lbMedia,cp2DdChapterId);
					if (lbMedia.getChapters()!=null&&lbMedia.getChapters().size()>0) {
//        			List<Chapter> chapterList = new ArrayList<Chapter>();
						for(ListenBookChapter lbChapter : lbMedia.getChapters()){
							Chapter chapter = new Chapter();
							chapter.setMediaId(lbMedia.getDdMediaId());
							chapter.setTitle(lbChapter.getName());
							chapter.setIndex(lbChapter.getOrder());
//        				TODO chapter需要增加一个外网字段，存放lbChapter.getStreams()
							List<String> streamsList = lbChapter.getStreams();
							StringBuffer streamsSB = new StringBuffer();
							for(String str : streamsList){
								streamsSB.append(str).append(",");
							}
							int streamsEndIdx = (streamsSB.length()>0) ? (streamsSB.length()-1) : 0;
							chapter.setFilePath(streamsSB.substring(0, streamsEndIdx).toString());
							chapter.setWordCnt(Long.parseLong(lbChapter.getSize()+""));
							chapter.setCreationDate(Calendar.getInstance().getTime());
							chapter.setLastModifyedDate(Calendar.getInstance().getTime());
							chapter.setIsShow(1);
							chapterService.save(chapter);
							cp2DdChapterId.put(lbChapter.getChapterId()+"", chapter.getId()+"");
//        				chapterList.add(chapter);
						}
//        			chapterService.save(chapterList);
//        			chapterService.findMasterByIds(ids);
					}
					
					//insert cpPullChapter
					List<CpPullChapter> cpPullChapterList = ListenBookMediaConvertUtil.lbMedia2CpPullChapter(lbMedia,cp2DdChapterId);
					if (cpPullChapterList!=null&&cpPullChapterList.size()>0) {
						cpPullChapterService.save(cpPullChapterList);
					}
				}
			});
		} catch (Exception e) {
			LOGGER.error("insert Chapter's relation table has error .please invoke addChapter interface. cpMediaId|albumId="+lbMedia.getAlbumId(),e);
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteMedia(final ListenBookMedia lbMedia) {
		try {
			TransactionTemplate transactionTemplate = TransactionTemplateUtils.getDefaultTransactionTemplate(transactionManager);
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//select id,dd_media_id,is_ful from media_cp_pull_media by cp_media_id
					CpPullMedia cpPullMediaQuery = new CpPullMedia();
					cpPullMediaQuery.setCpMediaId(lbMedia.getAlbumId().toString());
//					cpPullMedia = cpPullMediaService.findMasterUniqueByParamsObjs(cpPullMedia);
					List<CpPullMedia> cpPullMediaList = cpPullMediaService.findMasterListByParamsObjs(cpPullMediaQuery);
					if(cpPullMediaList!=null&&cpPullMediaList.size()>0){
						for(CpPullMedia cpPullMedia : cpPullMediaList){
							if(cpPullMedia!=null){
								lbMedia.setDdMediaId(cpPullMedia.getDdMediaId());
								
								//select sale_id from MediaRelation
								MediaRelation mediaRelation = new MediaRelation();
								mediaRelation.setMediaId(lbMedia.getDdMediaId());
								MediaRelation retMediaRelation = mediaRelationService.findMasterUniqueByParamsObjs(mediaRelation);
								lbMedia.setSaleId(retMediaRelation.getSaleId());
								
								mediaService.deleteById(lbMedia.getDdMediaId());
								mediaSaleService.deleteById(lbMedia.getSaleId());
								mediaRelationService.deleteById(retMediaRelation.getMediaRelationId());
								cpPullMediaService.deleteById(cpPullMedia.getId());
								catetoryService.delCatetoryByMediaId(lbMedia.getDdMediaId());
								
								chapterService.delChapterByMediaId(lbMedia.getDdMediaId());;
								cpPullChapterService.delCpPullChapterByMediaId(lbMedia.getDdMediaId());
							}
						}
					}
				}
			});
		} catch (Exception e) {
			LOGGER.error("delete all media's relation table has error . cpMediaId|albumId="+lbMedia.getAlbumId(),e);
			return 0;
		}
		return 1;
	}

	@Override
	public int saveChapter(final ListenBookMedia lbMedia) {
		try {
			//select dd_media_id from media_cp_pull_media by cp_media_id
			CpPullMedia cpPullMedia1 = new CpPullMedia();
			cpPullMedia1.setCpMediaId(lbMedia.getAlbumId().toString());
			cpPullMedia1.setCpCode(lbMedia.getCpCode());
			final CpPullMedia cpPullMedia = cpPullMediaService.findMasterUniqueByParamsObjs(cpPullMedia1);
			if(cpPullMedia!=null){
				TransactionTemplate transactionTemplate = TransactionTemplateUtils.getDefaultTransactionTemplate(transactionManager);
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						Map<String,String> cp2DdChapterId = new HashMap<String,String>();
						
//						if(cpPullMedia!=null){
							lbMedia.setDdMediaId(cpPullMedia.getDdMediaId());
						
							//insert chapter ,then get Cp2DdChapterIdMap
							saveChapterRetCp2DdChapterIdMap(lbMedia,cp2DdChapterId);
							
							//insert media_cp_pull_chapter
							List<CpPullChapter> cpPullChapterList = ListenBookMediaConvertUtil.lbMedia2CpPullChapter(lbMedia,cp2DdChapterId);
							if (cpPullChapterList!=null&&cpPullChapterList.size()>0) {
								cpPullChapterService.save(cpPullChapterList);
							}
							
							//TODO 需要保证size是总量，update media's last_index_order 
							Media media = new Media();
							media.setMediaId(lbMedia.getAuthorId());
							media.setLastIndexOrder(lbMedia.getEpisodesSize());
							mediaService.update(media);
//						}
					}
				});
			}else{
				LOGGER.error("添加章节错误。 请先添加专辑，否则没有专辑id-> CpPullMedia'ddMediaId cann't find . Cann't insert table: media_chapter and media_cp_pull_chapter ! cpMediaId|albumId="+lbMedia.getAlbumId());
				return 2;
			}
		} catch (Exception e) {
			LOGGER.error("添加章节错误。 -> CpPullMedia'ddMediaId cann't find . Cann't insert table: media_chapter and media_cp_pull_chapter ! cpMediaId|albumId="+lbMedia.getAlbumId(),e);
			return 0;
		}
		return 1;
	}

	@Override
	public int updateChapter(final ListenBookMedia lbMedia) {
		try {
			TransactionTemplate transactionTemplate = TransactionTemplateUtils.getDefaultTransactionTemplate(transactionManager);
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//select id,dd_media_id,is_ful from media_cp_pull_media by cp_media_id
					CpPullMedia cpPullMedia1 = new CpPullMedia();
					cpPullMedia1.setCpMediaId(lbMedia.getAlbumId().toString());
					CpPullMedia cpPullMedia = null;
					cpPullMedia = cpPullMediaService.findMasterUniqueByParamsObjs(cpPullMedia1);
					if(cpPullMedia!=null){
						lbMedia.setDdMediaId(cpPullMedia.getDdMediaId());
					
						String statusTemp = null;
						int isFull = 0;
						statusTemp = lbMedia.getStatus();
						if(statusTemp!=null&&!statusTemp.equals("")){
							isFull = Integer.parseInt(statusTemp);
						}
						
						//select sale_id from Media
						Media mediaTemp1 = new Media();
						mediaTemp1.setMediaId(lbMedia.getDdMediaId());
						Media retMedia = mediaService.findMasterById(lbMedia.getDdMediaId());
						
						if(cpPullMedia.getIsFull()!=null&&cpPullMedia.getIsFull()!=isFull){
							//update media_cp_pull_media set isFull by id
							CpPullMedia cpPullMediaTemp = new CpPullMedia();
							cpPullMediaTemp.setId(cpPullMedia.getId());
							cpPullMediaTemp.setIsFull(isFull);
							cpPullMediaTemp.setLastModifyDate(Calendar.getInstance().getTime());
							cpPullMediaService.update(cpPullMediaTemp);
							
							//update Media
							Media mediaTemp = new Media();
							mediaTemp.setMediaId(lbMedia.getDdMediaId());
							mediaTemp.setIsFull(isFull);
							mediaTemp.setLastModifyDate(Calendar.getInstance().getTime());
							mediaService.update(mediaTemp);
							
							//update MediaSale
							MediaSale mediaSale = new MediaSale();
							mediaSale.setSaleId(retMedia.getSaleId());
							mediaSale.setIsFull(isFull);
							mediaSale.setLastModifiedDate(Calendar.getInstance().getTime());
							mediaSaleService.update(mediaSale);
						}
					}else{
						LOGGER.error("更新专辑警告！！ -> CpPullMedia'ddMediaId cann't find . Cann't update table: media_cp_pull_media、 media、 MediaSale!");
					}
					
					//update media_chapter
					if(lbMedia.getChapters()!=null&&lbMedia.getChapters().size()>0){
						List<Chapter> chapterUpdateList = new ArrayList<Chapter>();
						List<String> cpChapterIds = new ArrayList<String>();
						for(ListenBookChapter lbChapter : lbMedia.getChapters()){
							cpChapterIds.add(lbChapter.getChapterId()+"");
						}
						//select cp2ddChapterIdMap
						Map<String,String> retCp2DdChapterIdMap = getCp2DdChapterIdMap(cpChapterIds);
						
						for(ListenBookChapter lbChapter : lbMedia.getChapters()){
							Chapter chapter = new Chapter();

							chapter.setId(Long.parseLong(retCp2DdChapterIdMap.get(lbChapter.getChapterId()+"")));
//							TODO chapter需要增加一个外网字段，存放lbChapter.getStreams()
							List<String> streamsList = lbChapter.getStreams();
							StringBuffer streamsSB = new StringBuffer();
							for(String str : streamsList){
								streamsSB.append(str).append(",");
							}
							int streamsEndIdx = (streamsSB.length()>0) ? (streamsSB.length()-1) : 0;
							chapter.setFilePath(streamsSB.substring(0, streamsEndIdx).toString());
							chapter.setLastModifyedDate(Calendar.getInstance().getTime());
							chapter.setWordCnt(Long.parseLong(lbChapter.getSize()+""));
							chapterUpdateList.add(chapter);
//							chapterService.update(chapter);
						}
						chapterService.updateChapterBatch(chapterUpdateList);
					}
				}
			});
		} catch (Exception e) {
			LOGGER.error("update Chapter's relation table has error .please invoke updateChapter interface again ! cpMediaId|albumId="+lbMedia.getAlbumId(),e);
			return 0;
		}
		return 1;
	}
	
	private Map<String,String> getCp2DdChapterIdMap(List<String> cpChapterIdlist){
		List<CpPullChapter> cpPullChapter = cpPullChapterService.findByCpChapterIds(cpChapterIdlist);
		Map<String,String> retMap = new HashMap<String,String>();
		for(CpPullChapter c : cpPullChapter){
			retMap.put(c.getCpChapterId(), c.getDdChapterId()+"");
		}
		return retMap;
	}
	
	
	private Map<String,String> saveChapterRetCp2DdChapterIdMap(ListenBookMedia lbMedia,Map<String,String> cp2DdChapterId){
		if (lbMedia.getChapters()!=null&&lbMedia.getChapters().size()>0) {
//			List<Chapter> chapterList = new ArrayList<Chapter>();
			for(ListenBookChapter lbChapter : lbMedia.getChapters()){
				Chapter chapter = new Chapter();
				chapter.setMediaId(lbMedia.getDdMediaId());
				chapter.setTitle(lbChapter.getName());
				chapter.setIndex(lbChapter.getOrder());
//				TODO chapter需要增加一个外网字段，存放lbChapter.getStreams()
				List<String> streamsList = lbChapter.getStreams();
				StringBuffer streamsSB = new StringBuffer();
				for(String str : streamsList){
					streamsSB.append(str).append(",");
				}
				int streamsEndIdx = (streamsSB.length()>0) ? (streamsSB.length()-1) : 0;
				chapter.setFilePath(streamsSB.substring(0, streamsEndIdx).toString());
				chapter.setWordCnt(Long.parseLong(lbChapter.getSize()+""));
				chapter.setCreationDate(Calendar.getInstance().getTime());
				chapter.setLastModifyedDate(Calendar.getInstance().getTime());
				chapterService.save(chapter);
				cp2DdChapterId.put(lbChapter.getChapterId()+"", chapter.getId()+"");
//				chapterList.add(chapter);
			}
//			chapterService.save(chapterList);
//			chapterService.findMasterByIds(ids);
		}
		return cp2DdChapterId;
	}
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
