package com.Cytexal.BedWars;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.help.GenericCommandHelpTopic;
public class Funktionen {

	private Main pl;
	
	public Funktionen(Main pl)
	{
		this.pl = pl;
		Main.f = this;
	}
	
	
	
	public boolean setup(UUID p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		boolean rs;
		java.sql.PreparedStatement st = null;
		try {
		st = con.prepareStatement("INSERT INTO `Stats`(`UUID`, `Betten`, `Kills`, `Tode`, `Wins`, `Lose`, `Punkte`) VALUES (?,0,0,0,0,0,0)");
				st.setString(1, p.toString());
				rs = st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public int getRank(UUID p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		ResultSet rs = null;
		java.sql.PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT COUNT(*) FROM Stats WHERE CAST(Punkte AS DECIMAL) >= ?");
			st.setString(1, String.valueOf(getPunkte(p)));
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getLoses(UUID p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		ResultSet rs = null;
		java.sql.PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT Lose FROM `Stats` WHERE UUID = ?");
			st.setString(1, p.toString());
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.next();
		} catch (SQLException e) {
			return -1;
		}
		try {
			return rs.getInt(1);
		} catch (SQLException e) {
			return -1;
		}
	}
	
	public int getWins(UUID p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		ResultSet rs = null;
		java.sql.PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT Wins FROM `Stats` WHERE UUID = ?");
			st.setString(1, p.toString());
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int getDeaths(UUID p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		ResultSet rs = null;
		java.sql.PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT Tode FROM `Stats` WHERE UUID = ?");
			st.setString(1, p.toString());
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int getKills(UUID p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		ResultSet rs = null;
		java.sql.PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT Kills FROM `Stats` WHERE UUID = ?");
			st.setString(1, p.toString());
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	public int getPunkte(UUID p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		ResultSet rs = null;
		java.sql.PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT Punkte FROM `Stats` WHERE UUID = ?");
			st.setString(1, p.toString());
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int getBetten(UUID p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		ResultSet rs = null;
		java.sql.PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT Betten FROM `Stats` WHERE UUID = ?");
			st.setString(1, p.toString());
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	
	
	
	
	public boolean addBett(Player p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		boolean rs;
		java.sql.PreparedStatement st = null;
		try {
		st = con.prepareStatement("UPDATE `Stats` SET `Betten`=? WHERE `UUID`=?");
				st.setString(2, p.getUniqueId().toString());
				st.setString(1, String.valueOf(getBetten(p.getUniqueId())+1));
				rs = st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean addKill(Player p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		boolean rs;
		java.sql.PreparedStatement st = null;
		try {
		st = con.prepareStatement("UPDATE `Stats` SET `Kills`=? WHERE `UUID`=?");
				st.setString(2, p.getUniqueId().toString());
				st.setString(1, String.valueOf(Main.f.getKills(p.getUniqueId()) + 1));
				rs = st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean addDeath(Player p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		boolean rs;
		java.sql.PreparedStatement st = null;
		try {
		st = con.prepareStatement("UPDATE `Stats` SET `Tode`=? WHERE `UUID`=?");
				st.setString(2, p.getUniqueId().toString());
				st.setString(1, String.valueOf(Main.f.getDeaths(p.getUniqueId()) + 1));
				rs = st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addWin(Player p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		boolean rs;
		java.sql.PreparedStatement st = null;
		try {
		st = con.prepareStatement("UPDATE `Stats` SET `Wins`=? WHERE `UUID`=?");
				st.setString(2, p.getUniqueId().toString());
				st.setString(1, String.valueOf(Main.f.getWins(p.getUniqueId()) + 1));
				rs = st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addLose(Player p)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		boolean rs;
		java.sql.PreparedStatement st = null;
		try {
		st = con.prepareStatement("UPDATE `Stats` SET `Lose`=? WHERE `UUID`=?");
				st.setString(2, p.getUniqueId().toString());
				st.setString(1, String.valueOf(Main.f.getLoses(p.getUniqueId()) + 1));
				rs = st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public boolean setPunkte(Player p,int tokens)
	{
		MySQL sql = pl.getConnection();
		Connection con = sql.getConnection();
		boolean rs;
		java.sql.PreparedStatement st = null;
		try {
		st = con.prepareStatement("UPDATE `Stats` SET `Punkte`=? WHERE `UUID`=?");
				st.setString(2, p.getUniqueId().toString());
				st.setString(1, String.valueOf(tokens));
				rs = st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
