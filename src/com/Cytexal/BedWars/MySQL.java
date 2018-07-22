package com.Cytexal.BedWars;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MySQL {

	private String host;
	private int port;
	private String user;
	private String password;
	private String database;
	
	private Connection conn;
	
	public MySQL()
	{
		
		File f = new File("plugins/BedWars/", "database.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
	
		
		String db = "database.";
		cfg.addDefault(db + "host", "localhost");
		cfg.addDefault(db + "port", 3306);
		cfg.addDefault(db + "user", "user");
		cfg.addDefault(db + "password", "password");
		cfg.addDefault(db + "database", "database");
		cfg.options().copyDefaults(true);
		try {
			cfg.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		this.host = "localhost";
		this.password = "caff7530d46468320568dc08";
		this.port = 3306;
		this.user = "root";
		this.database = "BedWars";
		this.conn = this.openConnection();
		
		this.openConnection();
	}
	
	public Connection openConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
			this.conn = conn;
			return conn;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
		
	}
	public void queryUpdate(String query)
	{
		Connection con = this.conn;
		java.sql.PreparedStatement st = null;
		try {
			st = con.prepareStatement(query);
			st.executeUpdate();
			this.closeRecources(null, st);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		return this.conn;
	}
	
	public boolean hasConnection()
	{
		try {
			return this.conn != null || this.conn.isValid(1);
		} catch (SQLException e) {
			return false;
		}

		
	}
	
	public void closeRecources(ResultSet rs, java.sql.PreparedStatement st) throws SQLException
	{
		if(rs != null)
		{
			rs.close();
		}
		if(st != null)
		{
			st.close();
		}
	}
	
	public void closeConnection() throws SQLException
	{
		this.conn.close();
		this.conn = null;
	}
	
}
