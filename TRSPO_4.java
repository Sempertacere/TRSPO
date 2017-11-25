package trspo;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class TRSPO_4 {
    public static void main(String[] args) {
        try {
            File inputFile = new File("E:\\input.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("player");
            System.out.println("----------------------------");
       
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element: " + nNode.getNodeName());
            
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Player no: " 
                        + eElement.getAttribute("no"));
                    System.out.println("Nickname: " 
                        + eElement
                        .getElementsByTagName("nickname")
                        .item(0)
                        .getTextContent());
                    System.out.println("Email: " 
                        + eElement
                        .getElementsByTagName("email")
                        .item(0)
                        .getTextContent());
                    System.out.println("Score: " 
                        + eElement
                        .getElementsByTagName("score")
                        .item(0)
                        .getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
