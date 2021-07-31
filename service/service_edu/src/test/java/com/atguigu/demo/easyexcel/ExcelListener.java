package com.atguigu.demo.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author hgk
 * @Date 2021/7/31 15:47
 * @description
 */
//创建读取excel监听器
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //一行一行去读取excle内容
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {

        System.out.println("***"+demoData);
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

}
