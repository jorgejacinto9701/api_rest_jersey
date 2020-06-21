package com.example.rest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MySqlDBConexion {
	private static ResourceBundle rb = ResourceBundle.getBundle("database", Locale.getDefault());

	static {
		try {
			Class.forName(rb.getString("driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	
	public static Connection getConexion() {
		Connection salida = null;
		try {
			salida = DriverManager.getConnection(
					rb.getString("url"), 
					rb.getString("username"),
					rb.getString("password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return salida;
	}

}
