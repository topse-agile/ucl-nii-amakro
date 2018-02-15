package com.fr_soft.demos.rfsample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class SqliteSample
{
	
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
  	String url = "jdbc:sqlite:H://ucl-nii-amakro/sample";
    Class.forName("org.sqlite.JDBC");
	
    Connection connection = null;
    try
    {
      	connection = DriverManager.getConnection(url);
      	Statement statement = connection.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS " + this.getTableName()
            + "	  (id          	  INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "   name            TEXT,"
            + "   amount          INTEGER,"
            + "   threshold       INTEGER,"
            + "   updated_at	  TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "   notified_at	  TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP"
            +");";

      statement.execute(sql);
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }
}