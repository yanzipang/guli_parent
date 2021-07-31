package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hgk
 * @Date 2021/7/31 16:52
 * @description 与Excel对应的实体类
 */
@Data
@NoArgsConstructor
public class SubjectExcelData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
