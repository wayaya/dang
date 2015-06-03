package com.dangdang.digital.model;
import java.io.Serializable;

/**
 * 
 * Description: 单品销售实体Media简要信息,
 * 只保存需要的字段saleId,mediaId,mediaCover,media_author_penname,
 * All Rights Reserved.
 * @version 1.0  2014年12月10日 下午4:33:19  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class SingleSaleMedia implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long mediaId;

    private String title;

    private String descs;

    private String coverPic;

    private Long saleId;
    
	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}


    
   
	
}