package com.highfd.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 启动短信猫服务
 */
@Component
@Controller
public class SkipController {
	
	//************URL 跳转**********************************************************
	@RequestMapping(value = "skip")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageName = request.getParameter("pageName");
		ModelAndView mav = new ModelAndView();
		if(null!=pageName && !"".equals(pageName)){
			if("smsList".equals(pageName)){//短信列表
				mav.setViewName("jsp/sms/smsList");
			}else if("alarmList".equals(pageName)){//报警列表
				mav.setViewName("jsp/alarm/alarmList");
			}else if("teqcList".equals(pageName)){//teqc处理的文件列表
				mav.setViewName("jsp/teqc/teqcList");
			}else if("siteList".equals(pageName)){//teqc处理的文件列表
				mav.setViewName("jsp/siteUser/siteUserList");
			}
			
			
			
			
			
			else if("2".equals(pageName)){//平台简介
				mav.setViewName("main/foreGround/iCV");
			}else if("21".equals(pageName)){//中心简介
				mav.setViewName("main/foreGround/iCenter");
			}else if("22".equals(pageName)){//中心理念
				mav.setViewName("main/foreGround/iCenterIdeals");
			}
			
			else if("3".equals(pageName)){
				mav.setViewName("main/foreGround/iNewList");
			}else if("4".equals(pageName)){
				mav.setViewName("main/foreGround/iHelp");
			}else if("5".equals(pageName)){
				mav.setViewName("main/foreGround/iUsers");
			}
			
			else if("6".equals(pageName)){//共享指南  使用说明
				mav.setViewName("main/foreGround/gxzn");
			}else if("61".equals(pageName)){//共享指南  注意事项
				mav.setViewName("main/foreGround/gxjnZysx");
			}else if("62".equals(pageName)){//共享指南  数据介绍
				
			}
			
			else if("7".equals(pageName)){//数据动态
				mav.setViewName("main/foreGround/sjdt");
			}else if("8".equals(pageName)){//下载专区
				mav.setViewName("main/foreGround/xzzq");
			}else if("9".equals(pageName)){//合作交流
				mav.setViewName("main/message/main");
			}else if("10".equals(pageName)){//新闻编辑
				mav.setViewName("main/newsEdit/newsEdit");
			}
			
			
			else{
				mav.setViewName("index");
			}
		}
		return mav;
	}
}