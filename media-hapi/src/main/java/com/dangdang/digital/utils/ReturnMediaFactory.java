package com.dangdang.digital.utils;

import com.dangdang.digital.vo.ReturnMediaBaseVo;
import com.dangdang.digital.vo.ReturnMediaVo;
import com.dangdang.digital.vo.ReturnPaperMediaVo;
import com.dangdang.digital.vo.ReturnPublishedMediaVo;

public class ReturnMediaFactory {

	public static ReturnMediaBaseVo getInstanceByType(int type) {

		switch (type) {
			case 1: {
				
				ReturnMediaVo vo = new ReturnMediaVo();
				vo.setMediaType(type);
				return vo;
			}
			case 2: {
				ReturnPublishedMediaVo vo = new ReturnPublishedMediaVo();
				vo.setMediaType(type);
				return vo;
			}
			case 3: {
				ReturnPaperMediaVo vo = new ReturnPaperMediaVo();
				vo.setMediaType(type);
				return vo;
			}
			default:{
				ReturnMediaVo vo = new ReturnMediaVo();
				vo.setMediaType(1);
				return vo;
			}
		}
	}
	
	public static ReturnMediaVo getEbookInstanceByType(int type) {

		switch (type) {
			case 1: {
				
				ReturnMediaVo vo = new ReturnMediaVo();
				vo.setMediaType(type);
				return vo;
			}
			case 2: {
				ReturnPublishedMediaVo vo = new ReturnPublishedMediaVo();
				vo.setMediaType(type);
				return vo;
			}
			default:{
				ReturnMediaVo vo = new ReturnMediaVo();
				vo.setMediaType(1);
				return vo;
			}
		}
	}

}
