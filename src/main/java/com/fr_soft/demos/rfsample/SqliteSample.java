package com.fr_soft.demos.rfsample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class SqliteSample
{
	private Connection connection;
	private Statement statement;
	private String url = "jdbc:sqlite:H://ucl-nii-amakro/sample";
	public SqliteSample() {
	
	}
	
	public String getDbName(){
		return "H://ucl-nii-amakro/sample";
	}
	
	public String getTableName(){
		return "stock";
	}
	
	public String getPath(){
		return new File(".").getAbsoluteFile().getParent();
	}

  public void createTable() throws ClassNotFoundException
  {
    Class.forName("org.sqlite.JDBC");
	
    try
    {
      	this.connection = DriverManager.getConnection(this.url);
      	this.statement = this.connection.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS " + this.getTableName()
            + "	  (id          	  INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "   name            TEXT,"
            + "   amount          INTEGER,"
            + "   threshold       INTEGER,"
            + "   updated_at	  TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "   notified_at	  TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP"
            +");";

      this.statement.execute(sql);
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(this.connection != null)
          this.connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }
public void insertDemoData() throws ClassNotFoundException
  {
    Class.forName("org.sqlite.JDBC");
	
    try
    {
      	this.connection = DriverManager.getConnection(this.url);
      	this.statement = this.connection.createStatement();

	    int flag = this.statement.executeUpdate("insert into "+ this.getTableName() 
	    +"(name, amount, threshold) values ('apple', 10, 11), ('orange', 100, 10), ('mango', 100, 9);");
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
 		this.connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }
  
  public void countStock() throws ClassNotFoundException
  {
    Class.forName("org.sqlite.JDBC");
	
    try
    {
      	this.connection = DriverManager.getConnection(this.url);
      	this.statement = this.connection.createStatement();

	    ResultSet rs = this.statement.executeQuery("select * from "+ this.getTableName());
	    while(rs.next()) {
	    	if (this.compareStock(rs.getInt("amount"), rs.getInt("threshold")))
	    	{
	    		System.out.println(rs.getString("name"));
	    	}
	    }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(this.connection != null)
          this.connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }
  
  public boolean compareStock(int amount, int threshold) {
  	return amount<=threshold;
  }
}