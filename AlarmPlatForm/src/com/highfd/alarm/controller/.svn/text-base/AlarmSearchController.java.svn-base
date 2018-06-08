package com.highfd.alarm.controller;

import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.highfd.alarm.model.AlarmInfo;
import com.highfd.alarm.service.AlarmService;
import com.highfd.common.PageInfo;
import com.highfd.common.Excerl.ExcelStyle;
import com.highfd.common.param.CheckParam;

/**
 * 短信猫发送的短信 查询
 */
@Component
@Controller
@RequestMapping(value="/alarmSendSearch")
public class AlarmSearchController {
	
	@Autowired
	AlarmService alarmService;
	
	//查询报警信息
	@RequestMapping(value = "/queryAlarmInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryAlarmInfoList(HttpServletRequest request) throws Exception {
		
		String startTime = request.getParameter("startTime");
		startTime = CheckParam.checkStartTime(startTime);
		
		String endTime = request.getParameter("endTime");
		endTime = CheckParam.checkStartTime(endTime);
		
		String isFlag = request.getParameter("isFlag");
		
		PageInfo pageinfo = new PageInfo();
		String pageSize = request.getParameter("pageSize");
		pageinfo.setPageSize(Integer.valueOf(pageSize));
		String currentPage = request.getParameter("currentPage");
		if(!("").equals(currentPage)&&currentPage!=null){
			pageinfo.setCurrentPage(Integer.parseInt(currentPage));
		}
		
		List<AlarmInfo> queryAlarmInfoList = alarmService.queryAlarmInfoList(startTime, endTime, isFlag,pageinfo);
		pageinfo.setList(queryAlarmInfoList);
		JSONArray json = JSONArray.fromObject(pageinfo);
		return json.toString();
	}
	
	
	
	//报警数据导出
	@RequestMapping("dataExcel")  
	public void dataExcel(HttpServletRequest request, HttpServletResponse response){
		
		String startTime = request.getParameter("startTime");
		startTime = CheckParam.checkStartTime(startTime);
		
		String endTime = request.getParameter("endTime");
		endTime = CheckParam.checkStartTime(endTime);
		
		String isFlag = request.getParameter("isFlag");
		String pageSize = request.getParameter("pageSize");
		PageInfo pageinfo = new PageInfo(); 
		pageinfo.setPageSize(Integer.valueOf(pageSize));
		String currentPage = request.getParameter("currentPage");
		if(!("").equals(currentPage)&&currentPage!=null){
			pageinfo.setCurrentPage(Integer.parseInt(currentPage));
		}
		
		List<AlarmInfo> queryAlarmInfoList = alarmService.queryAlarmInfoList(startTime, endTime, isFlag,pageinfo);
		
		String fileName="";
		if(null==startTime || endTime==null){
			fileName +="全部";
		}else{
			fileName +=startTime+"-"+endTime;
		}
	
		if(null==isFlag || "".equals(isFlag)){
			
		}else{
			if(isFlag.indexOf("true")>-1){
				fileName +="已结束";
			}else if(isFlag.indexOf("false")>-1){
				fileName +="未结束";
			}
		}
		fileName +="报警数据";
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
	         Sheet sheet = workbook.createSheet("报警数据"); 
	         int rowNumber = 0;//行序列
	         //标题
	         Row upRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell upCell = upRow.createCell(0);//创建一列
	         upCell.setCellValue(fileName);
	         upCell.setCellStyle(ExcelStyle.setTextStyle(workbook));
	         sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));//合并单元格

	         //标题行
        	 Row titleRow = sheet.createRow(rowNumber++);//创建标题行
        	 titleRow.setHeight((short)400); 
	         List<String> columnList = new ArrayList<String>();
	         columnList.add("序号");columnList.add("所属省份");columnList.add("台站名称");columnList.add("台站编号");
	         columnList.add("故障类型");columnList.add("故障开始时间");columnList.add("故障结束时间");
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
	         for (AlarmInfo info:queryAlarmInfoList) {
	             Row row = sheet.createRow(rowNumber++);//创建行
	             row.setHeight((short)350);
	             int dayNumber = 0;
	             Cell cell = null;
	             ExcelStyle.setDataCell(row,cell, info.getRm(),dayNumber++, workbook);
	             ExcelStyle.setDataCell(row,cell, info.getZoneName(), dayNumber++,workbook);
	             ExcelStyle.setDataCell(row,cell, info.getSiteName(), dayNumber++,workbook);
	             ExcelStyle.setDataCell(row,cell, info.getSiteNumber(),dayNumber++, workbook);
	             
	             ExcelStyle.setDataCell(row,cell, info.getDescription(), dayNumber++,workbook);
	             ExcelStyle.setDataCell(row,cell, info.getStartTimeStr(), dayNumber++,workbook);
	             ExcelStyle.setDataCell(row,cell, info.getEndTimeStr(), dayNumber++,workbook);
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
	
	@RequestMapping(value="/getMapState",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getMapState(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		List<AlarmInfo> list = alarmService.getMapState();
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}
	
	@RequestMapping(value="/getMapAupsState",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getMapAupsState(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		List<AlarmInfo> list = alarmService.getMapAupsState();
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}
	@RequestMapping(value="/getMapDupsState",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getMapDupsState(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		List<AlarmInfo> list = alarmService.getMapDupsState();
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}
}