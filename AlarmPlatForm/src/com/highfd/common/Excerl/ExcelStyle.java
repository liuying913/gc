package com.highfd.common.Excerl;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelStyle {
	
	/**
	 * 设置数据的样式 和值 
	 */
	public static void setDataCell(Row row,Cell cell,String value,int dayNumber,XSSFWorkbook workbook){
		System.out.println(dayNumber);
		cell = row.createCell(dayNumber);//创建列  
		cell.setCellType(Cell.CELL_TYPE_STRING);  
        cell.setCellValue(value);  
        cell.setCellStyle(ExcelStyle.getStyle(workbook,IndexedColors.WHITE.getIndex(),false));//设置背景颜色
	}
	
	
	/**
	 * 设置标题行的样式 和 值
	 */
	public static void setTitleCell(Row titleRow,String value,int widthNum, int column,Sheet sheet,XSSFWorkbook workbook){
         Cell tilteCell = titleRow.createCell(column);//创建一列
         tilteCell.setCellValue(value);
         tilteCell.setCellStyle(ExcelStyle.getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),false));//设置背景颜色
         sheet.setColumnWidth(column, widthNum);
	}
	
	//设置文字样式
	public static CellStyle setTextStyle(final Workbook workbook){
		 CellStyle style = workbook.createCellStyle();
		 Font ztFont = workbook.createFont();  
         //ztFont.setItalic(true);                     // 设置字体为斜体字  
         ztFont.setColor(IndexedColors.GREY_80_PERCENT.getIndex());            // 将字体设置为“红色”  
         ztFont.setFontHeightInPoints((short)22);    // 将字体大小设置为18px  
         //ztFont.setFontName("华文行楷");             // 将“华文行楷”字体应用到当前单元格上  
         //ztFont.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）  
         //ztFont.setStrikeout(true);                  // 是否添加删除线  
         style.setFont(ztFont);                    // 将字体应用到样式上面  
         style.setAlignment(CellStyle.ALIGN_CENTER); // 居中
         style.setFillForegroundColor(IndexedColors.WHITE.getIndex());//背景颜色
         style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.getIndex());
 		 style.setFillPattern(CellStyle.SOLID_FOREGROUND);
         return style;
         
	}
	
	   public static CellStyle getTitleStyle(final Workbook workbook,final short index,final boolean isYear){
	        CellStyle style = workbook.createCellStyle();
	        
	        Font headerFont = workbook.createFont(); // 字体
	        headerFont.setFontHeightInPoints((short)14);
	        headerFont.setColor(HSSFColor.RED.index);
	        headerFont.setFontName("宋体");
	        headerFont.setColor(IndexedColors.WHITE.getIndex());//白色字体
	        style.setFont(headerFont);
	/*        style.setAlignment(CellStyle.ALIGN_CENTER); 
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        // 设置单元格字体
	        Font headerFont = workbook.createFont(); // 字体
	        headerFont.setFontHeightInPoints((short)14);
	        //headerFont.setColor(HSSFColor.RED.index);
	        headerFont.setFontName("宋体");
	        style.setFont(headerFont);
	        style.setWrapText(true);

	        style.setFillBackgroundColor(HSSFCellStyle.ALIGN_RIGHT);
	        
	        // 设置单元格边框及颜色
	        style.setBorderBottom((short)1);
	        style.setBorderLeft((short)1);
	        style.setBorderRight((short)1);
	        style.setBorderTop((short)1);
	        style.setWrapText(true);*/
	        //short index = IndexedColors.RED.getIndex();
			style.setFillForegroundColor(index);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setAlignment(CellStyle.ALIGN_CENTER); // 上下居中
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
			
		    //        设置单元格边框  
	        //============================  
	      
	        // 创建单元格样式对象  
	        // 设置单元格边框样式  
	        // CellStyle.BORDER_DOUBLE      双边线  
	        // CellStyle.BORDER_THIN        细边线  
	        // CellStyle.BORDER_MEDIUM      中等边线  
	        // CellStyle.BORDER_DASHED      虚线边线  
	        // CellStyle.BORDER_HAIR        小圆点虚线边线  
	        // CellStyle.BORDER_THICK       粗边线  
			style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.getIndex());
			style.setLeftBorderColor(IndexedColors.LIGHT_BLUE.getIndex());
			style.setRightBorderColor(IndexedColors.LIGHT_BLUE.getIndex());
			if(!isYear){
				style.setBorderBottom(CellStyle.BORDER_THIN);  
				//style.setBorderTop(CellStyle.BORDER_THIN);  
				style.setBorderLeft(CellStyle.BORDER_THIN);  
				style.setBorderRight(CellStyle.BORDER_THIN);  
			}else{
				style.setBorderBottom(CellStyle.BORDER_THIN);  
				//style.setBorderTop(CellStyle.BORDER_THIN);  
				style.setBorderLeft(CellStyle.BORDER_THIN);  
				style.setBorderRight(CellStyle.BORDER_THIN);  
			}
	        // 设置单元格边框颜色  
			//style.setBottomBorderColor(new XSSFColor(java.awt.Color.RED));  
			//style.setTopBorderColor(new XSSFColor(java.awt.Color.GREEN));  
			//style.setLeftBorderColor(new XSSFColor(java.awt.Color.BLUE));  
	        //borderCell.setCellStyle(style); 
	        return style;
	    }
	   
    public static CellStyle getStyle(final Workbook workbook,final short index,final boolean isYear){
        CellStyle style = workbook.createCellStyle();
        
/*        style.setAlignment(CellStyle.ALIGN_CENTER); 
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 设置单元格字体
        Font headerFont = workbook.createFont(); // 字体
        headerFont.setFontHeightInPoints((short)14);
        //headerFont.setColor(HSSFColor.RED.index);
        headerFont.setFontName("宋体");
        style.setFont(headerFont);
        style.setWrapText(true);

        style.setFillBackgroundColor(HSSFCellStyle.ALIGN_RIGHT);
        
        // 设置单元格边框及颜色
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setWrapText(true);*/
        //short index = IndexedColors.RED.getIndex();
		style.setFillForegroundColor(index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		style.setAlignment(CellStyle.ALIGN_CENTER); // 上下居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		
	    //        设置单元格边框  
        //============================  
      
        // 创建单元格样式对象  
        // 设置单元格边框样式  
        // CellStyle.BORDER_DOUBLE      双边线  
        // CellStyle.BORDER_THIN        细边线  
        // CellStyle.BORDER_MEDIUM      中等边线  
        // CellStyle.BORDER_DASHED      虚线边线  
        // CellStyle.BORDER_HAIR        小圆点虚线边线  
        // CellStyle.BORDER_THICK       粗边线  
		if(!isYear){
			style.setBottomBorderColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			style.setLeftBorderColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			style.setRightBorderColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			style.setBorderBottom(CellStyle.BORDER_THIN);  
			//style.setBorderTop(CellStyle.BORDER_THIN);  
			style.setBorderLeft(CellStyle.BORDER_THIN);  
			style.setBorderRight(CellStyle.BORDER_THIN);  
		}else{
			//年度 无边框
			style.setBorderBottom(CellStyle.BORDER_NONE);  
			//style.setBorderTop(CellStyle.BORDER_THIN);  
			style.setBorderLeft(CellStyle.BORDER_NONE);  
			style.setBorderRight(CellStyle.BORDER_NONE);  
		}
        // 设置单元格边框颜色  
		//style.setBottomBorderColor(new XSSFColor(java.awt.Color.RED));  
		//style.setTopBorderColor(new XSSFColor(java.awt.Color.GREEN));  
		//style.setLeftBorderColor(new XSSFColor(java.awt.Color.BLUE));  
        //borderCell.setCellStyle(style); 
        return style;
    }
}
