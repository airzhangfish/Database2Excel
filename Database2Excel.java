package com.ikags.databasetools;

import com.ikags.utils.SQLDataDictUtils;

public class Database2Excel {

    public static void main(String[] args) {
    	
        String url = "192.168.1.240:3306/saas";  //mysql地址
        String user = "XXXXX";  //帐号
        String pw = "XXXXXX";  //密码
        String kuname = "cksaas";  //数据库
        String csvpath_ku = "C:\\Users\\airzhangfish\\Desktop\\ku.csv"; //导出库表格
        String csvpath_table = "C:\\Users\\airzhangfish\\Desktop\\table.csv"; //导出表结构

        SQLDataDictUtils.SQL2Excel(url, user, pw, kuname, csvpath_ku, csvpath_table); 
    }
    
    
    


}
