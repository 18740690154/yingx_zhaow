package com.baizhi.zw.test;




import com.baizhi.zw.entity.Emp;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestPoi {

    @Test
    public void test() {
        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();
        //创建一个Excel工作表 参数：工作薄名字(sheet1,shet2....)
        //不指定工作簿名 默认按照 sheet0，sheet1 命名
        Sheet sheet = workbook.createSheet("用户信息");

        //创建一个Excel的行 参数:行下标(从0开始)
        Row row = sheet.createRow(1);

        //创建一个Excel的单元格 参数:单元格下标(从0开始)
        Cell cell = row.createCell(1);

        //为单元格赋值
        cell.setCellValue("这是第二行第二个单元格");

        try {
            //导出单元格
            workbook.write(new FileOutputStream(new File("D://poi.xls")));
            //释放资源
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
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

        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();
        //创建一个Excel工作表 参数：工作薄名字(sheet1,shet2....)
        //不指定工作簿名 默认按照 sheet0，sheet1 命名
        Sheet sheet = workbook.createSheet("员工信息");

        //设置列宽 参数:列索引,列下标  列的宽度值
        sheet.setColumnWidth(4,20*256);

        //创建标题字体样式对象
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 20); //设置字号
        font.setFontName("微软雅黑");  //设置字体
        font.setBold(true);   //加粗
        font.setColor(Font.COLOR_RED); //字体颜色
        font.setFontName("宋体"); //设置字体
        font.setItalic(true); //设置斜体

        //创建标题样式对象
        CellStyle cellTitleStyle = workbook.createCellStyle();
        cellTitleStyle.setFont(font);  //将字体对象交给样式对象
        //标题文字居中
        cellTitleStyle.setAlignment(HorizontalAlignment.CENTER);
        //创建标题行
        Row rowTitle = sheet.createRow(0);
        //设置标题行高
        rowTitle.setHeight((short) (30*20));
        //为标题行创建单元格
        Cell cellTitle = rowTitle.createCell(0);
        //为标题行单元格赋值
        cellTitle.setCellValue("百知员工信息表");
        //为标题单元格设置指定样式
        cellTitle.setCellStyle(cellTitleStyle);


        //合并单元格 firstRow：行开始 lastRow：行结束  firstCol:列开始 lastRow:列结束
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 4);
        //将设置的合并单元格策略给sheet
        sheet.addMergedRegion(cellAddresses);

        //创建目录行
        Row rowContent = sheet.createRow(1);
        //设置目录行高 参数:行的高度值
        rowContent.setHeight((short) (20*20));
        //目录行数组
        String[] titles ={"ID","用户名","年龄","工资","生日"};

        //处理标题行数据
        for (int i = 0; i < titles.length; i++) {
            //创建标题单元格
            Cell cell = rowContent.createCell(i);
            //设置标题单元格值
            cell.setCellValue(titles[i]);
        }

        //创建日期格式对象
        DataFormat dataFormat = workbook.createDataFormat();
        //设置日期格式
        short format = dataFormat.getFormat("yyyy年MM月dd日");

        //创建标题样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //将设置好的日期格式给样式对象
        cellStyle.setDataFormat(format);

        //处理数据行
        for (int i = 0; i < emps.size(); i++) {
            //每遍历一次创建一行
            Row row = sheet.createRow(i + 2);

            //创建单元格并赋值
            row.createCell(0).setCellValue(emps.get(i).getId());
            row.createCell(1).setCellValue(emps.get(i).getName());
            row.createCell(2).setCellValue(emps.get(i).getAge());
            row.createCell(3).setCellValue(emps.get(i).getPrice());

            //创建日期单元格
            Cell cell = row.createCell(4);
            //为日期单元格赋值
            cell.setCellValue(emps.get(i).getBirthday());
            //为日期单元格设置指定样式
            cell.setCellStyle(cellStyle);
        }
        try {
            //导出单元格
            workbook.write(new FileOutputStream(new File("D://pois.xls")));
            //释放资源
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test //导入表格
    public void testImport(){
        //获取表格中数据读入程序中
        try {
            //获取要导入的文件
            Workbook workbook = new HSSFWorkbook(new FileInputStream(new File("D:\\pois.xls")));
            //根据工作表名获取工作表
            Sheet sheet = workbook.getSheet("员工信息");
            //获取最后一行数据下表
            int rowNum = sheet.getLastRowNum();

            for (int i = 2; i <= rowNum; i++) {
                //获取行
                Row row = sheet.getRow(i);
                //获取行数据
                String id = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                double ages = row.getCell(2).getNumericCellValue();
                int age = (int)ages;
                double price = row.getCell(3).getNumericCellValue();
                Date date = row.getCell(4).getDateCellValue();
                Emp emp = new Emp(id, name, age, price, date);
                System.out.println("向数据库插入的数据 " + emp);
                //将获取到的数据插入数据库
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}