package com.highfd.siteUser.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.highfd.common.Excerl.ExcelStyle;
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.siteUser.model.ZoneInfo;
import com.highfd.siteUser.service.SiteUserService;

/**
 * 界面方面
 */
@Component
@Controller
@RequestMapping(value="/siteUser")
public class SiteUserController {
	
	@Autowired
	SiteUserService siteUserService;
	
	//修改台站信息
	@RequestMapping(value = "/updateSiteInfo", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateSiteInfo(HttpServletRequest request,@ModelAttribute SiteInfo siteInfo) throws Exception {
		siteUserService.updateBaseSiteInfo(siteInfo);
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"保存成功！\"}]}";
	}
	
	//获得单个台站信息
	@RequestMapping(value = "/getBaseSiteInfoById", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getBaseSiteInfoById(HttpServletRequest request) throws Exception {
		String siteNumber = request.getParameter("siteNumber");
		SiteInfo siteInfo = siteUserService.getBaseSiteInfoById(siteNumber);
		JSONArray json = JSONArray.fromObject(siteInfo);
		return json.toString();
	}
	
	//获得站点列表
	@RequestMapping(value = "/getSiteInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getSiteInfoList(HttpServletRequest request) throws UnsupportedEncodingException {
		String searchParam = request.getParameter("searchParam");
		//String str = new String(searchParam.getBytes("ISO8859-1"),"utf-8");   
		//System.out.println(searchParam+" | "+str);
		System.out.println("查询参数："+searchParam);
		List<SiteInfo> siteInfoList = siteUserService.getSiteInfoList(searchParam);
		JSONArray json = JSONArray.fromObject(siteInfoList);
		return json.toString();
	}
	
	//获得省份列表
	@RequestMapping(value = "/getZoneInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getZoneInfoList(HttpServletRequest request) {
		List<ZoneInfo> zoneInfoList = siteUserService.getZoneInfoList();
		JSONArray json = JSONArray.fromObject(zoneInfoList);
		return json.toString();
	}
	
	/**
	 * 是否存在管理员（未完成）
	 */
	@RequestMapping(value = "/getManagerList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getManagerList(HttpServletRequest request) {
		return null;
	}
	
	
	
	
	
	
	//报警数据导出
	@RequestMapping("dataExcel")  
	public void dataExcel(HttpServletRequest request, HttpServletResponse response){
		
		String searchParam = request.getParameter("searchParam");
		List<SiteInfo> siteInfoList = siteUserService.getSiteInfoList(searchParam);
		
		String fileName="报警数据";
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	         // 进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         // 产生工作簿对象  
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         
	         CellStyle style = workbook.createCellStyle();
	         style.setAlignment(CellStyle.ALIGN_CENTER); // 居中
	         
	         //产生工作表对象  
	         Sheet sheet = workbook.createSheet("台站数据"); 
	         int rowNumber = 0;//行序列
	         //标题
	         Row upRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell upCell = upRow.createCell(0);//创建一列
	         upCell.setCellValue(fileName);
	         upCell.setCellStyle(ExcelStyle.setTextStyle(workbook));
	         sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));//合并单元格

	         //标题行
        	 Row titleRow = sheet.createRow(rowNumber++);//创建标题行
        	 titleRow.setHeight((short)400); 
	         List<String> columnList = new ArrayList<String>();
	         columnList.add("序号");columnList.add("所属省份");columnList.add("台站名称");columnList.add("台站编号");
	         columnList.add("联系人");columnList.add("联系方式");
	         for(int column=0;column<columnList.size();column++){
		         if(column==3 ||column==4 ||column==6 ||column==7 ||column==9){
		        	 ExcelStyle.setTitleCell(titleRow, columnList.get(column), 6000, column, sheet, workbook);
	        	 }else if(column==0) {
	        		 ExcelStyle.setTitleCell(titleRow, columnList.get(column), 1500, column, sheet, workbook);
	        	 }else{
	        		 ExcelStyle.setTitleCell(titleRow, columnList.get(column), 4500, column, sheet, workbook);
	        	 }
	         }
	         
	         //数据
	         for (SiteInfo info:siteInfoList) {
	             Row row = sheet.createRow(rowNumber++);//创建行
	             row.setHeight((short)350);
	             int dayNumber = 0;
	             Cell cell = null;
	             ExcelStyle.setDataCell(row,cell, info.getRm(),dayNumber++, workbook);
	             ExcelStyle.setDataCell(row,cell, info.getZoneName(), dayNumber++,workbook);
	             ExcelStyle.setDataCell(row,cell, info.getSiteName(), dayNumber++,workbook);
	             ExcelStyle.setDataCell(row,cell, info.getSiteNumber(),dayNumber++, workbook);
	             
	             ExcelStyle.setDataCell(row,cell, info.getSite_person(), dayNumber++,workbook);
	             ExcelStyle.setDataCell(row,cell, info.getSite_phone(), dayNumber++,workbook);
	         }
	        
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成...");  
	}

	
}