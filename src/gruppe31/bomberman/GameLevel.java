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

public class GameLevel {

	public static int currentLevel = 0;
	/**
	 * liest the aktuelle Status der Spiel von GameLevel.xml ein
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
	
	/**
	 * schriebt die aktuelle Status der Spiel in GameLevel.xml. 
	 * @param Status
	 */
	public void writeLevelIntoFile(int level) {
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
		xmlBuffer.append("</level></bomberman>");
		
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
