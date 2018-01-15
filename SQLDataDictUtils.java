package com.ikags.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class SQLDataDictUtils {

	/**
	 * 数据库直接生成excel数据字典
	 * 
	 * @param url
	 * @param user
	 * @param pw
	 * @param csvpath_ku
	 * @param csvpath_table
	 */
	public static void SQL2Excel(String url, String user, String pw, String kuname, String csvpath_ku, String csvpath_table) {
		Connection con;
		String driver = "com.mysql.jdbc.Driver";
		String conurl = "jdbc:mysql://" + url + "?characterEncoding=utf8&&useInformationSchema=true";
		String conuser = "" + user;
		String password = "" + pw;
		StringBuffer sbuffer_ku = new StringBuffer();
		StringBuffer sbuffer_table = new StringBuffer();
		try {
			// 加载驱动程序
			Class.forName(driver);
			con = DriverManager.getConnection(conurl, conuser, password);
			if (!con.isClosed()) {
				System.out.println("Succeeded connecting to the Database!");
			}
			Statement statement = con.createStatement();

			String sql = "SELECT Table_schema, Table_name, Table_comment FROM information_schema.tables WHERE Table_schema=";// 'kksass'
			String sql2 = "select COLUMN_NAME,ORDINAL_POSITION,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,COLUMN_DEFAULT,COLUMN_COMMENT from information_schema.columns where TABLE_NAME=";

			Vector<TableInfo> tinfolist = new Vector<TableInfo>();
			ResultSet rs = statement.executeQuery(sql + "'" + kuname + "'");

			sbuffer_ku.append("库名,表名,注释" + ",,\r\n");
			while (rs.next()) {
				TableInfo tinfo = new TableInfo();
				tinfo.schema = rs.getString(1);
				tinfo.name = rs.getString(2);
				tinfo.comment = rs.getString(3);
				System.out.println("TABLE=" + tinfo.schema + "," + tinfo.name + "," + tinfo.comment);
				sbuffer_ku.append(tinfo.schema + "," + tinfo.name + "," + tinfo.comment.trim().replaceAll("\r", "").replaceAll("\n", "") + ",,\r\n");
				tinfolist.add(tinfo);
			}
			rs.close();

			sbuffer_table.append("表名,字段名,位置,类型,长度,默认值,注释" + ",,\r\n");
			for (int i = 0; i < tinfolist.size(); i++) {
				TableInfo mTableInfo = tinfolist.get(i);

				ResultSet rs_table = statement.executeQuery(sql2 + "'" + mTableInfo.name + "'");
				while (rs_table.next()) {
					String COLUMN_NAME = rs_table.getString(1);
					String ORDINAL_POSITION = rs_table.getString(2);
					String DATA_TYPE = rs_table.getString(3);
					String CHARACTER_MAXIMUM_LENGTH = rs_table.getString(4);
					String COLUMN_DEFAULT = rs_table.getString(5);
					String COLUMN_COMMENT = rs_table.getString(6);
					if (CHARACTER_MAXIMUM_LENGTH == null) {
						CHARACTER_MAXIMUM_LENGTH = "";
					}
					if (COLUMN_DEFAULT == null) {
						COLUMN_DEFAULT = "";
					}
					System.out.println(mTableInfo.name + "," + COLUMN_NAME + "," + ORDINAL_POSITION + "," + DATA_TYPE + "," + CHARACTER_MAXIMUM_LENGTH + "," + COLUMN_DEFAULT + "," + COLUMN_COMMENT);
					sbuffer_table.append(mTableInfo.name + "," + COLUMN_NAME + "," + ORDINAL_POSITION + "," + DATA_TYPE + "," + CHARACTER_MAXIMUM_LENGTH + "," + COLUMN_DEFAULT + "," + COLUMN_COMMENT.replaceAll("\r", "").replaceAll("\n", "") + ",,\r\n");
				}
				rs_table.close();
			}

			con.close();
			FileUtil.saveFile("", csvpath_ku, sbuffer_ku.toString());
			FileUtil.saveFile("", csvpath_table, sbuffer_table.toString());
		} catch (ClassNotFoundException e) {
			// 数据库驱动类异常处理
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			// 数据库连接失败异常处理
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("run over");
		}
	}

	public static class TableInfo {
		public String schema = null;
		public String name = null;
		public String comment = null;
	}

}
