package com.highfd.teqc.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.common.PageInfo;
import com.highfd.teqc.controller.TEQCController;
import com.highfd.teqc.dao.TEQCDao;
import com.highfd.teqc.model.FileInfo;
import com.highfd.teqc.service.TEQCService;
import com.highfd.teqc.teqcTool.FileTypeTransform;

@Service
public class TEQCServiceImpl implements TEQCService {

	@Autowired
	TEQCDao dao;

	/**
	 * 查询所有的o文件，并进行计算入库
	 */
	public void analysisAll_o(final File dir) throws Exception{
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					analysisAll_o(fs[i]);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if(fileName.toLowerCase().endsWith("o")){//注意大小写
					FileInfo gnssFileInfo = FileTypeTransform.getFileInfoBy_o_Path(fs[i]);
					String year2= gnssFileInfo.getFileYear().substring(2,4);
					String z_file_ResultPath =  TEQCController.oFileRootPath+"/"+gnssFileInfo.getFileYear()+"/"+gnssFileInfo.getFileDayYear()+"/"+gnssFileInfo.getSiteNumber().toLowerCase()+gnssFileInfo.getFileDayYear()+"0."+year2+"d.Z";
					goToDB(gnssFileInfo.getFileYear() ,  gnssFileInfo, fs[i].getPath(),  z_file_ResultPath);//入库操作
				}
			}
		}
	}

	
	//通过正则表达式 计算历元数
	public void goToDB(String year4, FileInfo fileInfo,String o_file, String z_file_ResultPath){
		
		//入库操作
		if(fileInfo.getFileFlag()!=1 && fileInfo.getFileFlag()!=2 && fileInfo.getFileFlag()!=3){//ftp下载的有问题,那么还是以o文件为准
			System.out.println("没有入库操作！！！ ");
		}
		
		FileInfo select_30sDataFiles = dao.select_30SData_Files(year4, fileInfo);
		if(select_30sDataFiles==null){
			List<FileInfo> list = new ArrayList<FileInfo>();list.add(fileInfo);dao.insert_30SData_Files(year4, list);//入 关系库
		}else{
			dao.update_30S_apply(year4, fileInfo);
		}
	}
	
	/**
	 * 根据 开始时间 结束时间 台站类别  台站经纬度坐标 进行查询
	 */
	public List<FileInfo>  getFileInfoList(String startTime,String endTime,String fileYear,String fileFlag,final boolean isYear,PageInfo pageinfo){
		return dao.getFileInfoList(startTime, endTime, fileYear, fileFlag, isYear, pageinfo);
	}
	
}