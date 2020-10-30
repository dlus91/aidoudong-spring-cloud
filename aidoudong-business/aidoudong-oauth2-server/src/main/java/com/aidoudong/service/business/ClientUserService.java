package com.aidoudong.service.business;

import com.aidoudong.entity.business.ClientUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * IService<T>提供了对T表操作的很多抽象方法，比如：批量操作
 */
public interface ClientUserService extends IService<ClientUser> {
	
	/**
	 * 通过用户名查询用户信息
	 * @param username 用户名
	 * @return
	 */
	ClientUser findByUsername(String username);
	
	/**
	 * 通过手机号查询用户信息
	 * @param mobile 手机号
	 * @return
	 */
	ClientUser findByMobile(String mobile);
	
}
