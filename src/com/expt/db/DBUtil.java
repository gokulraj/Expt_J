//$Id$
package com.expt.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.sql.*;

public class DBUtil {


	public static String getElementValue( Node elem )
	{
		Node kid;
		if( elem != null && (elem.hasChildNodes()))
		{

			for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() )
			{
				if( kid.getNodeType() == Node.TEXT_NODE  )
				{
					return kid.getNodeValue();
				}
				if( kid.getNodeType() == Node.CDATA_SECTION_NODE )
				{
					return kid.getNodeValue();
				}
			}
		}
		return "";
	}
	public NodeList getTableList(String filename) throws Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db  = dbf.newDocumentBuilder();
		Document doc = db.parse(filename);

		NodeList nList = doc.getElementsByTagName("tab");
		return nList;
	}
	public void createTables() throws Exception{

		NodeList nList = getTableList(System.getProperty("user.dir") + "\\conf\\db.xml");
		Connect c = new Connect();
		Statement stmt = c.getCon().createStatement();
		String sql = null;

		try{
			for (int i = 0; i< nList.getLength(); i++) {

				Node nNode = nList.item(i);
				c.getCon().setAutoCommit(false);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					sql = getElementValue(eElement);
					stmt.executeUpdate(sql);
					System.out.println( eElement.getAttribute("name") + " Table created ");	
				}
			}
			
			//stmt.executeUpdate("ALTER TABLE user AUTO_INCREMENT = 1001;");
			stmt.executeUpdate("ALTER TABLE expense AUTO_INCREMENT = 1001;");
			stmt.executeUpdate("ALTER TABLE reports AUTO_INCREMENT = 1001;");
			c.getCon().commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(c.getCon()!=null)
					c.getCon().close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}


	}

	
}
