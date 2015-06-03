package com.dangdang.digital.service;

import org.springframework.web.multipart.MultipartFile;

import com.dangdang.digital.model.EpubImage;

public interface IEpubImageService extends IBaseService<EpubImage, Long> {
	public boolean uploadImage(Long mediaId, MultipartFile file) throws Exception;
}
