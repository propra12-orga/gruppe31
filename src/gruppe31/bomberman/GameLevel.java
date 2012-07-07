package gruppe31.bomberman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 
 * @author gruppe 31 - bomberman
 * Diese Klasse hat methode, die die aktuelle Status der Spiel und Punkten vom xml Datei einliest 
 * und in xml Datei einschreibt.
 *
 */
public class GameLevel {

	public static int currentLevel = 0;
	/**
	 * liest die aktuelle Status der Spiel von GameLevel.xml ein
	 * @return the Spiel Status
	 */
	public String readCurrentLevel() {

		try {
			File file = new File("GameLevel.xml");
			if (file == null) {
				return null;
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("bomberman");
			
			Node fstNode = nodeLst.item(0);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("level");
					Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					NodeList fstNm = fstNmElmnt.getChildNodes();
					return ((Node) fstNm.item(0)).getNodeValue();
					//System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
<<<<<<< HEAD
	public String readPlayer1Score() {

		try {
			File file = new File("GameLevel.xml");
			if (file == null) {
				return null;
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("bomberman");
			
			Node fstNode = nodeLst.item(0);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("player1score");
					Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					NodeList fstNm = fstNmElmnt.getChildNodes();
					return ((Node) fstNm.item(0)).getNodeValue();
					//System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String readPlayer2Score() {

		try {
			File file = new File("GameLevel.xml");
			if (file == null) {
				return null;
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("bomberman");
			
			Node fstNode = nodeLst.item(0);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("player1score");
					Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					NodeList fstNm = fstNmElmnt.getChildNodes();
					return ((Node) fstNm.item(0)).getNodeValue();
					//System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

=======
>>>>>>> 9c7bde9840243e7a6b2f9e205a159642302c0589
	/**
	 * schriebt die aktuelle Status der Spiel und Punkten in GameLevel.xml. 
	 * @param Status
	 */
<<<<<<< HEAD
	public void writeLevelIntoFile(int level, int player1score, int player2score) {
=======
	public void writeLevelIntoFile(int level, int score) {
>>>>>>> 9c7bde9840243e7a6b2f9e205a159642302c0589
		File file = new File("GameLevel.xml");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer xmlBuffer = new StringBuffer();
		xmlBuffer.append("<?xml version=\"1.0\"?><bomberman><level>");
		xmlBuffer.append(Integer.toString(level));
		xmlBuffer.append("</level>");
<<<<<<< HEAD
		xmlBuffer.append("<player1score>");
		xmlBuffer.append(Integer.toString(player1score));
		xmlBuffer.append("</player1score><player2score>");
		xmlBuffer.append(Integer.toString(player2score));
		xmlBuffer.append("</player2score></bomberman>");
=======
		xmlBuffer.append("<score>");
		xmlBuffer.append(Integer.toString(score));
		xmlBuffer.append("</score></bomberman>");
>>>>>>> 9c7bde9840243e7a6b2f9e205a159642302c0589
		
		try {
			fos.write(xmlBuffer.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
