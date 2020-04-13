package com.baizhi.zw.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.zw.entity.Emp;
import com.baizhi.zw.entity.Leader;
import com.baizhi.zw.entity.Photo;
import org.apache.poi.ss.usermodel.Workbook;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEasyPoi {

    @Test  //导出
    public void testExport(){
        //创建集合
        List<Emp> emps = new ArrayList<>();

        emps.add(new Emp("1", "小何1", 11,5000.0,new Date()));
        emps.add(new Emp("2", "小何2",  32, 2520.0, new Date()));
        emps.add(new Emp("3", "小何3",  38, 269.6, new Date()));
        emps.add(new Emp("4", "小何4",  19, 698.2, new Date()));
        emps.add(new Emp("5", "小何5",  32, 520.0, new Date()));
        emps.add(new Emp("6", "小何6",  26, 198.9, new Date()));
        emps.add(new Emp("7", "小何7",  22, 1499.9, new Date()));


        //导出  参数:标题，工作表名
        ExportParams exportParams = new ExportParams("百知员工信息表","员工信息1");
        //配置工作表的参数：导出参数的对象，要导出的对象，导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,Emp.class,emps);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("D:\\easyPoi.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test  //多表导出
    public void testExports(){
        //创建集合
        List<Emp> emps1 = new ArrayList<>();
        List<Emp> emps2 = new ArrayList<>();

        emps1.add(new Emp("1", "小何1", 11,5000.0,new Date()));
        emps1.add(new Emp("2", "小何2",  32, 2520.0, new Date()));
        emps1.add(new Emp("3", "小何3",  38, 269.6, new Date()));
        emps1.add(new Emp("4", "小何4",  19, 698.2, new Date()));
        emps2.add(new Emp("5", "小何5",  32, 520.0, new Date()));
        emps2.add(new Emp("6", "小何6",  26, 198.9, new Date()));
        emps2.add(new Emp("7", "小何7",  22, 1499.9, new Date()));


        List<Leader> leaders = new ArrayList<>();
        leaders.add(new Leader("1","hxz","教学部",emps1));
        leaders.add(new Leader("2","zcn","教学部",emps2));

        //导出  参数:标题，工作表名
        ExportParams exportParams = new ExportParams("百知员工信息表","员工信息1");
        //配置工作表的参数：导出参数的对象，要导出的对象，导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,Leader.class,leaders);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("D:\\easyPois.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test  //导入
    public void testImport(){
        ImportParams importParams = new ImportParams();
        //配置导入相关参数
        importParams.setTitleRows(1);  //表格标题行数，默认0
        importParams.setHeadRows(1);    //表头所占行数，默认1

        try {
            List<Emp> emps = ExcelImportUtil.importExcel(new FileInputStream(new File("D:\\easyPoi.xls")), Emp.class, importParams);
                for (Emp emp : emps) {
                    System.out.println("员工信息 = " + emp);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test //多表导入
    public void testImports(){
        ImportParams importParams = new ImportParams();
        //配置导入相关参数
        importParams.setTitleRows(1);  //表格标题行数，默认0
        importParams.setHeadRows(2);    //表头所占行数，默认1

        try {
            List<Leader> leaders = ExcelImportUtil.importExcel(new FileInputStream(new File("D:\\easyPois.xls")), Leader.class, importParams);
            for (Leader leader : leaders) {
                System.out.println("领导信息 = " + leader);

                List<Emp> emps = leader.getEmps();
                for (Emp emp : emps) {
                    System.out.println("员工信息 = " + emp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test  //导出图片
    public void testExportPhoto(){
        //创建集合
        List<Photo> photos = new ArrayList<>();

        photos.add(new Photo("1", "小何1","src/main/webapp/upload/img/1ew.jpg"));
        photos.add(new Photo("2", "小何2","src/main/webapp/upload/img/1iew.jpg"));
        photos.add(new Photo("3", "小何3", "src/main/webapp/upload/img/1w.jpg"));
        photos.add(new Photo("4", "小何4", "src/main/webapp/upload/img/2.jpg") );
        photos.add(new Photo("5", "小何5", "src/main/webapp/upload/img/2iew.jpg"));


        //导出  参数:标题，工作表名
        ExportParams exportParams = new ExportParams("头像信息表","头像信息1");
        //配置工作表的参数：导出参数的对象，要导出的对象，导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,Photo.class,photos);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("D:\\easyPoiPhoto.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test  //导入
    public void testImportPhoto(){
        ImportParams importParams = new ImportParams();
        //配置导入相关参数
        importParams.setTitleRows(1);  //表格标题行数，默认0
        importParams.setHeadRows(1);    //表头所占行数，默认1

        try {
            List<Photo> photos = ExcelImportUtil.importExcel(new FileInputStream(new File("D:\\easyPoiPhoto.xls")), Photo.class, importParams);
            for (Photo photo : photos) {
                System.out.println("图片信息 = " + photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
