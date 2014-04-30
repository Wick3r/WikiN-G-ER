package part.offline.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnector {
	
	private Connection con;
	PreparedStatement prepStmt;
	
	/**
	 * Init the connection to the given database
	 * @param host
	 * @param port
	 * @param database
	 * @param user
	 * @param passwd
	 */
	public void init(String host, int port, String database, String user, String passwd){
//		prepStmt = new PreparedStatement[threads];
		
		try 
		{ 
		    Class.forName("org.gjt.mm.mysql.Driver"); 
		} 
		catch(ClassNotFoundException cnfe) 
		{ 
		    System.out.println("Driver couldn�t be loaded: "+cnfe.getMessage()); 
		}
		
		try 
		{ 
		    con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, passwd);

		} 
		catch(SQLException sqle) 
		{ 
		    System.out.println("Verbindung ist fehlgeschlagen: " + sqle.getMessage()); 
		}
		try {
			prepStmt = con.prepareStatement("SELECT page_id FROM page WHERE page_title = ? OR page_title = ? OR page_title = ?;");
//			for (int i = 0; i < prepStmt.length; i++) {
//				prepStmt[i] = con.prepareStatement("SELECT page_id FROM page WHERE page_title = ? OR page_title = ? OR page_title = ?;");
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}
	
	/**
	 * Search the page_ids for a given cityname
	 * @param cityName
	 * @param threadID deprecated parameter, if you use threads, use their id, otherwise use something else
	 * @return
	 */
	public int[] getPageIDs(String cityName, int threadID){
		//Statement stmt;
		cityName = cityName.replaceAll(" ", "_");
		cityName = cityName.replaceAll("\'", "%");
		try {
		//	stmt = con.createStatement();
			//System.out.println("SELECT page_id FROM page WHERE page_title LIKE '"+cityName+"' OR page_title LIKE '"+cityName+",_%'"+" OR page_title LIKE '"+cityName+"_%';");
			//ResultSet rs = stmt.executeQuery("SELECT page_id FROM page WHERE page_title = '"+cityName+"' OR page_title = '"+cityName+",_%'"+" OR page_title = '"+cityName+"_%';");
			//ResultSet rs = stmt.executeQuery("SELECT page_id FROM page WHERE page_title LIKE '"+cityName+"';");
		//	System.out.println("rc erhalten");
			prepStmt.setString(1, cityName);
			prepStmt.setString(2, cityName+"\\_");
			prepStmt.setString(3, cityName+",\\_");
			ResultSet rs = prepStmt.executeQuery();
			rs.last();
			int[] rc = new int[rs.getRow()];
			int i = 0;
			rs.beforeFirst();
			while(rs.next()){
				rc[i++]=rs.getInt(1);
			}
			
			return rc;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Return rev_ids to given page_ids
	 * @param pageIDs
	 * @return
	 */
	public int[] getRevIDs(int[] pageIDs){
		Statement stmt;
		StringBuffer query = new StringBuffer(150);
		query.append("SELECT rev_id FROM revision WHERE rev_page = '"+pageIDs[0]+"'");
		for (int i = 1; i < pageIDs.length; i++) {
			query.append(" OR rev_page = '"+pageIDs[i]+"'");
		}
		query.append(";");
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			
			rs.last();
			int[] rc = new int[rs.getRow()];
			int i = 0;
			rs.beforeFirst();
			while(rs.next()){
				rc[i++]=rs.getInt(1);
			}
			
			return rc;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns the wikitext to given rev_ids
	 * @param revIDs
	 * @return
	 */
	public String[] getTexts(int[] revIDs){
		Statement stmt;
		StringBuffer query = new StringBuffer(150);
		query.append("SELECT old_text FROM text WHERE old_id = '"+revIDs[0]+"'");
		for (int i = 1; i < revIDs.length; i++) {
			query.append(" OR old_id = '"+revIDs[i]+"'");
		}
		query.append(";");
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			
			rs.last();
			String[] rc = new String[rs.getRow()];
			int i = 0;
			rs.beforeFirst();
			while(rs.next()){
				rc[i++]=rs.getString(1);
			}
			
			rs.close();
			stmt.close();
			return rc;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
