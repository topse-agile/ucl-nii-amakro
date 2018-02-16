package com.fr_soft.demos.rfsample;

import static org.junit.Assert.*;
import java.sql.*;

import java.io.IOException;
import org.junit.Test;

public class CreateDBTest {

	@Test
	public void test_table_creation()throws IOException{
		try{
		    Class.forName("org.sqlite.JDBC");
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}

	    Connection connection = null;
	    try
	    {
	    	SqliteSample sqliteSample = new SqliteSample();
	        sqliteSample.createTable();
	    	
	      connection = DriverManager.getConnection("jdbc:sqlite:"+sqliteSample.getDbName());
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.

	      ResultSet rs = statement.executeQuery("select * from sqlite_master where type='table'");
	      rs.next();
	    	assertEquals(rs.getString("name"), "stock");
	    }
	    catch(Exception e)
	    {
	      System.err.println(e.getMessage());
	    }		
	}
	
	@Test
	public void test_count_stock()throws IOException{
		try{
		    Class.forName("org.sqlite.JDBC");
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}

	    Connection connection = null;
    	SqliteSample sqliteSample = new SqliteSample();
	    try
	    {
	        sqliteSample.createTable();
	    	
	      connection = DriverManager.getConnection("jdbc:sqlite:"+sqliteSample.getDbName());
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);
	      sqliteSample.insertDemoData();
	      
	      ResultSet rs = statement.executeQuery("select * from "+ sqliteSample.getTableName());

	      rs.next();
    	  assertEquals(rs.getString("name"), "apple");
    	  assertNotEquals(rs.getInt("amount"), 100);
    	  assertEquals(rs.getInt("threshold"), 11);

	      rs.next();
    	  assertEquals(rs.getString("name"), "orange");
    	  assertEquals(rs.getInt("amount"), 100);
    	  assertEquals(rs.getInt("threshold"), 10);

	      rs.next();
    	  assertEquals(rs.getString("name"), "mango");
    	  assertEquals(rs.getInt("amount"), 100);
    	  assertEquals(rs.getInt("threshold"), 9);
	    
	    }catch(Exception e){
	
	    }
	}
	
	@Test
	public void test_compareStock()throws IOException{
    	SqliteSample sqliteSample = new SqliteSample();
    	assertFalse(sqliteSample.compareStock(100, 10));
    	assertTrue(sqliteSample.compareStock(10, 10));
    	assertTrue(sqliteSample.compareStock(9, 10));
	}
	
	@Test
	public void test_send_Notification(){
    	SqliteSample sqliteSample = new SqliteSample();
		sqliteSample.sendNotification("Test Message");
	}
	
	
}

