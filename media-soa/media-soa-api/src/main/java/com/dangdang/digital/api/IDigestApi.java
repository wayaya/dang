package com.dangdang.digital.api;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Digest;

public interface IDigestApi {

	/**
	 * 保存文章
	 * Description: 
	 * @Version1.0 2015年6月2日 下午2:45:21 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param digest
	 * @throws Exception
	 */
	public void saveDigest(Digest digest) throws Exception;

	/**
	 * 发现首页文章列表
	 * Description: 
	 * @Version1.0 2015年6月2日 下午2:45:28 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param sortPage
	 * @param dayNight
	 * @param pageSize
	 * @param types
	 * @param version
	 * @return
	 */
	public List<Digest> queryDigestsHomePage(Long sortPage, Integer dayNight,
			Integer pageSize, String types, String version);

	/**
	 * 查询频道文章列表
	 * Description: 
	 * @Version1.0 2015年6月2日 下午2:38:43 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param param   keyset<ids,isShow>
	 * @return
	 */
	public List<Digest> queryChannelDigests(Map<String, Object> param);
	
	/**
	 * 查询书吧文章列表
	 * Description: 
	 * @Version1.0 2015年6月2日 下午2:38:43 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param param   keyset<sortPage,sort(gt/lt),barId,authorId,isShow,isDel,type,pageSize(必填)>
	 * @return
	 */
	public List<Digest> queryBarDigests(Map<String, Object> param);

	/**
	 * 获取文章内容，不含content
	 * Description: 
	 * @Version1.0 2015年6月2日 下午2:44:51 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param id
	 * @return
	 */
	public Digest findDigestById(Long id);
	
	/**
	 * 获取文章内容，包含content
	 * Description: 
	 * @Version1.0 2015年6月2日 下午2:44:51 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param id
	 * @return
	 */
	public Digest findDigestContentById(Long id);
}
