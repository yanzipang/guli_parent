package com.atguigu.demo.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author hgk
 * @Date 2021/7/31 15:25
 * @description 实现Excel写操作
 */
public class TestEasyExcelWriter {

    public static void main(String[] args) {

        // 设置写入文件夹地址和写入的Excel文件名称
        String filename = "D://write.xlsx";

        List<DemoData> list = Stream.of(new DemoData(1, "周杰伦1"), new DemoData(2, "昆凌1")).collect(Collectors.toList());

        /**
         * write()方法中两个参数：
         *      第一个参数：要写入的文件名称
         *      第二个参数：实体类
         * sheet():设置sheet名称
         * doWrite():写入的集合
         */
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(list);
    }

    /**
     * Excel读操作
     * 注意：必须要给类加一个无参构造方法，否则读操作会报错
     */
    @Test
    public void testEasyExcelRead() {
        String filename = "D://write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
