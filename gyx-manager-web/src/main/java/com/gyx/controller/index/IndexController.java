package com.gyx.controller.index;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	/**
	 * 展示首页
	 * @author 高艺祥<br>
	 * 				最后修改日期：2017-04-17
	 * @return /gyx-manager-web/src/main/webapp/WEB-INF/jsp/welcome.jsp
	 */
	@RequestMapping("/")
	public String index(){
		return "welcome";
	}
}
