package com.atguigu.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hgk
 * @Date 2021/7/31 15:22
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoData {

    /**
     * @ExcelProperty 注解设置Excel文件的表头
     *      value：在写时有用，表明表头是value的值
     *      index：在读时有用，表明读第几列
     */
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
