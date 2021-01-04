package com.aidoudong.controller;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultView;
import com.aidoudong.common.utils.PropertiesEnum;
import com.aidoudong.entity.business.ClientUser;
import com.aidoudong.service.business.client.ClientUserService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test2")
public class Test2Controller {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private BaseResultView fastJsonResultView;
	
	@GetMapping("/page")
	public String page(Page<ClientUser> page,ClientUser clientUser) {
		ClientUser user = new ClientUser();
		user.setUsername("test");
		return fastJsonResultView.codeMap(
				ResultView.success(clientUserService.selectPage(page, user)));
	}
	
	@GetMapping("/page/include")
	public String pageInclude(Page<ClientUser> page) {
		ClientUser user = new ClientUser();
		user.setUsername("test");
		return fastJsonResultView.include(
				ResultView.success(clientUserService.selectPage(page, user)),
				new String[] {"id","username","nickName","mobile"});
	}
	
	@GetMapping("/page/include2")
	public String pageInclude2(Page<ClientUser> page,ClientUser clientUser) {
		ClientUser user = new ClientUser();
		user.setUsername("test");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("accountNonExpired", "effective_type");
		map.put("accountNonLocked", "effective_type");
		map.put("enabled", "effective_type");
		ResultView resultView = ResultView.of(200,"成功",clientUserService.selectPage(page, user),map);
		return fastJsonResultView.include(
				resultView,
				new String[] {"id","username","nickName","mobile","email","accountNonExpired","accountNonLocked","enabled"});
//				new String[] {"id"});
	}


	@GetMapping("/page/include3")
	public String pageInclude3() {
		return fastJsonResultView.data(ResultView.success(PropertiesEnum.ERROR_CODE_EN.getProperties()));
	}

	@GetMapping("/page/include4")
	@ResponseBody
	public String pageInclude4(Page<ClientUser> page) {
		ClientUser user = new ClientUser();
		user.setUsername("test");
		return fastJsonResultView.output(ResultView.success(clientUserService.selectPage(page, user)),
				abstractResultView ->
					JSONObject.toJSONString(
						abstractResultView,
						SerializeConfig.globalInstance,
						null,
						abstractResultView.getDateFormatter(),
						JSONObject.DEFAULT_GENERATE_FEATURE,
						SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.PrettyFormat,
						SerializerFeature.WriteMapNullValue
					)
		);
	}

	
	@GetMapping("/fail")
	public String fail() {
		return fastJsonResultView.data(ResultView.fail("ERROR_EXIST"));
	}
	
}
