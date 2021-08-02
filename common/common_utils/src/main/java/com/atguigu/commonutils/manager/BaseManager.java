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

    public TransactionTemplate getDataSourceTransactionManager() {
        return new TransactionTemplate(this.transactionManager);
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
