package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Description: 卷缓存，用于目录 All Rights Reserved.
 * 
 * @version 1.0 2014年12月15日 下午2:34:07 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ContentsVo implements Serializable {

	private static final long serialVersionUID = 825941075203768783L;

	private Long volumeId;

	private String title;

	private List<ChapterContentsVo> chapterList;

	public Long getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(Long volumeId) {
		this.volumeId = volumeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ChapterContentsVo> getChapterList() {
		return chapterList;
	}

	public void setChapterList(List<ChapterContentsVo> chapterList) {
		this.chapterList = chapterList;
	}

	public void addChapterList(ChapterContentsVo chapter) {
		if (chapterList != null) {
			chapterList.add(chapter);
		}
	}

}
