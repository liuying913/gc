package com.highfd.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.highfd.vo.Item;

public class SaveExcelFile {
	
	
    public boolean exportExcel2(String title, List<Item> itemList,Map<Long, Map> map, OutputStream out ) throws ParseException{  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        HSSFCellStyle style = workbook.createCellStyle();  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        style.setFont(font);  
		if(map.size() > 0){
			title = title.replace(":", "-");
	        HSSFSheet sheet = workbook.createSheet("数据列表");  
	        //设置表格默认列宽度为15个字符  
	        sheet.setDefaultColumnWidth(20);  
	        //产生表格标题行  
	        HSSFRow row = sheet.createRow(0); 
	        HSSFCell cell = row.createCell(0);
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString("日期");  
            cell.setCellValue(text);  

            HSSFRow rowUnit = sheet.createRow(1); 
            HSSFCell cellUnit = rowUnit.createCell(0);
	        cellUnit.setCellStyle(style);  
            HSSFRichTextString textUnit = new HSSFRichTextString("单位");  
            cellUnit.setCellValue(textUnit); 
            
            
	        for(int i = 0; i<itemList.size();i++){  
	            HSSFCell cell2 = row.createCell(i+1);  
	            cell2.setCellStyle(style);  
	            HSSFRichTextString text2 = new HSSFRichTextString(itemList.get(i).getItemName()+"数值");  
	            cell2.setCellValue(text2);  
	            
	            HSSFCell cellUnit2 = rowUnit.createCell(i+1);  
	            cellUnit2.setCellStyle(style);  
	            HSSFRichTextString textUnit2 = new HSSFRichTextString(itemList.get(i).getSourceName());  
	            cellUnit2.setCellValue(textUnit2);  
	        }
	        
	        //
	       
	        
	        HSSFCellStyle cellDateStyle = workbook.createCellStyle();
            HSSFDataFormat format= workbook.createDataFormat();
            cellDateStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));

            HSSFCellStyle cellValueStyle = workbook.createCellStyle();  
            HSSFDataFormat df = workbook.createDataFormat();
            cellValueStyle.setDataFormat(df.getFormat("0,0.00")); 
            
            int i=0;
            Iterator<Entry<Long, Map>> it = map.entrySet().iterator();
            while (it.hasNext()) {
            	Entry<Long, Map> entry = it.next();
            	//System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            	Map itemListDataMap = entry.getValue();
	            row = sheet.createRow(i+2);
	            i++;
	            HSSFCell dateCell = row.createCell(0);
	            dateCell.setCellValue(TimeCommon.getTimeLongToDate(entry.getKey()));
	            dateCell.setCellStyle(cellDateStyle);
	            for(int q=0;q<itemListDataMap.size();q++){
            		HSSFCell valueCell = row.createCell(q+1);
            		String date = itemListDataMap.get(q).toString();
            		if(null==date || "".equals(date)){valueCell.setCellValue("");}else{valueCell.setCellValue(Double.parseDouble(itemListDataMap.get(q).toString()));}
     	            valueCell.setCellStyle(cellValueStyle);
	            }
	        } 
		}
        try {  
            workbook.write(out);  
        } catch (IOException e) {
            e.printStackTrace(); 
            return false;
        }  
        return true;
    }  
	
	public static String getChartEncod(String nameParam) throws UnsupportedEncodingException{
		return new String(nameParam.getBytes("ISO-8859-1"), "UTF-8");//本地的时候
		//return nameParam;//打包的时候
	}
	/**
	 * 生成excel并导出到客户端（本地） 
	 * @param title
	 * @param headers
	 * @param mapList
	 * @param out
	 * @param pattern
	 */
    public boolean exportExcel(String title, List<ArrayList> list, OutputStream out ){  
        //声明一个工作簿  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        //生成一个样式，用来设置标题样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        //设置这些样式  
        //style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        //生成一个字体  
        HSSFFont font = workbook.createFont();  
        //font.setColor(HSSFColor.VIOLET.index);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        //把字体应用到当前的样式  
        style.setFont(font);  
  
        HashMap<String,String> highFDmap = new HashMap<String,String>();
        Iterator itr = list.iterator();
        while(itr.hasNext()){
			ArrayList high = (ArrayList) itr.next();
			if(high.size() > 0){
				highFDmap = (HashMap) high.get(0); 
				title = title.replace(":", "-");
		        HSSFSheet sheet = workbook.createSheet(title);  
		        //设置表格默认列宽度为15个字符  
		        sheet.setDefaultColumnWidth(20);  
		        //产生表格标题行  
		        HSSFRow row = sheet.createRow(0);  
		        String headers[] = {"日期",title+"数值"};
		        for(int i = 0; i<headers.length;i++){  
		            HSSFCell cell = row.createCell(i);  
		            cell.setCellStyle(style);  
		            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
		            cell.setCellValue(text);  
		        } 
		        
		        HSSFCellStyle cellDateStyle = workbook.createCellStyle();
	            HSSFDataFormat format= workbook.createDataFormat();
	            cellDateStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));

	            HSSFCellStyle cellValueStyle = workbook.createCellStyle();  
	            HSSFDataFormat df = workbook.createDataFormat();
	            //cellValueStyle.setDataFormat(df.getFormat("#,#0.0000")); 
	            cellValueStyle.setDataFormat(df.getFormat("0,0.00")); 
	            for(int i=0; i<high.size(); i++){
		        	highFDmap = (HashMap) high.get(i);  
		            row = sheet.createRow(i+1);  
		            
		            HSSFCell dateCell = row.createCell(0);
		            dateCell.setCellValue(TimeCommon.getDates(highFDmap.get("sdate").toString().split(" ")[0]));
		            dateCell.setCellStyle(cellDateStyle);
		            
		            HSSFCell valueCell = row.createCell(1);
		            //valueCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		            valueCell.setCellValue(Double.parseDouble(highFDmap.get("value").toString()));
		            valueCell.setCellStyle(cellValueStyle);
		        } 
			}
        }
        
        try {  
            workbook.write(out);  
        } catch (IOException e) {
            e.printStackTrace(); 
            return false;
        }  
        return true;
    }  
    
	 public static String toUtf8String(String s){ 
	     StringBuffer sb = new StringBuffer(); 
	       for (int i=0;i<s.length();i++){ 
	          char c = s.charAt(i); 
	          if (c >= 0 && c <= 255){sb.append(c);} 
	        else{ 
	        byte[] b; 
	         try { b = Character.toString(c).getBytes("utf-8");} 
	         catch (Exception ex) { 
	             System.out.println(ex); 
	                  b = new byte[0]; 
	         } 
	            for (int j = 0; j < b.length; j++) { 
	             int k = b[j]; 
	              if (k < 0) k += 256; 
	              sb.append("%" + Integer.toHexString(k).toUpperCase()); 
	              } 
	     } 
	  } 
	  return sb.toString(); 
	}
}
//cellValueStyle.setDataFormat(df.getFormat("#,#0.00")); //数据格式只显示整数，如果是小数点后保留两位，可以写contentStyle.setDataFormat(df.getFormat("#,#0.00"));  
		        
