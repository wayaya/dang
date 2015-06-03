package com.dangdang.digital.vo;

import java.io.Serializable;

public class MediaCacheSaleVo implements Serializable{

		private static final long serialVersionUID = 1562284051541505225L;

		private Long mediaId;
		
		private Long saleId;

	    private Short type;
	    
	    private Long price;

	    public Long getMediaId() {
			return mediaId;
		}

		public void setMediaId(Long mediaId) {
			this.mediaId = mediaId;
		}

		public Long getSaleId() {
			return saleId;
		}

		public void setSaleId(Long saleId) {
			this.saleId = saleId;
		}

		public Short getType() {
			return type;
		}

		public void setType(Short type) {
			this.type = type;
		}

		public Long getPrice() {
			return price;
		}

		public void setPrice(Long price) {
			this.price = price;
		}

}
