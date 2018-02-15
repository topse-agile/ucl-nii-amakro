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
	      while(rs.next())
	      {  
	    	assertEquals(rs.getString("name"), "stock");
	      }
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
	    try
	    {
	    	SqliteSample sqliteSample = new SqliteSample();
	        sqliteSample.createTable();
	    	
	      connection = DriverManager.getConnection("jdbc:sqlite:"+sqliteSample.getDbName());
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);
	      
	      System.out.println("insert into "+ sqliteSample.getTableName() 
	      +"(name, amount, threshold) values ('apple', 100, 10);");
	      
	      int flag = statement.executeUpdate("insert into "+ sqliteSample.getTableName() 
	      +"(name, amount, threshold) values ('apple', 100, 10);");
	      
	      ResultSet rs = statement.executeQuery("select count(*) from "+ sqliteSample.getTableName());

	      while(rs.next()){
	    	  System.out.println(rs.getString("name"));
	      }
	    
	    }catch(Exception e){
	
	    }
	}
	
}

