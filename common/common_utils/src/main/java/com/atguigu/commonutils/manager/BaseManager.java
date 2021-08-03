package com.atguigu.commonutils.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Author hgk
 * @Date 2021/8/2 21:04
 * @description
 */
public class BaseManager {

    @Autowired
    private PlatformTransactionManager transactionManager;

    public BaseManager() {
    }

    /**
     * 获取到编程式事务管理模板
     * @return TransactionTemplate：编程式事务管理模板
     */
    public TransactionTemplate getDataSourceTransactionManager() {
        return new TransactionTemplate(this.transactionManager);
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
