package com.ikags.databasetools;

import com.ikags.utils.SQLDataDictUtils;

public class Database2Excel {

    public static void main(String[] args) {
    	
        String url = "192.168.1.240:3306/saas";  //mysql��ַ
        String user = "XXXXX";  //�ʺ�
        String pw = "XXXXXX";  //����
        String kuname = "cksaas";  //���ݿ�
        String csvpath_ku = "C:\\Users\\airzhangfish\\Desktop\\ku.csv"; //��������
        String csvpath_table = "C:\\Users\\airzhangfish\\Desktop\\table.csv"; //������ṹ

        SQLDataDictUtils.SQL2Excel(url, user, pw, kuname, csvpath_ku, csvpath_table); 
    }
    
    
    


}
