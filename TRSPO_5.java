package trspo;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class TRSPO_5 {
    public static void main(String argv[]) {

        try {
            DocumentBuilderFactory dbFactory =
            DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
         
            Element rootElement = doc.createElement("class");
            doc.appendChild(rootElement);
        
            Element player1 = doc.createElement("player");
            rootElement.appendChild(player1);
           
            Attr attr1 = doc.createAttribute("no");
            attr1.setValue("12");
            player1.setAttributeNode(attr1);
            
            Element nickname1 = doc.createElement("nickname");
            nickname1.appendChild(doc.createTextNode("Sempertacere"));
            player1.appendChild(nickname1);
            
            Element email1 = doc.createElement("email");
            email1.appendChild(doc.createTextNode("serglozovskij2@gmail.com"));
            player1.appendChild(email1);
            
            Element score1 = doc.createElement("score");
            score1.appendChild(doc.createTextNode("458"));
            player1.appendChild(score1);
            
            Element player2 = doc.createElement("player");
            rootElement.appendChild(player2);
           
            Attr attr2 = doc.createAttribute("no");
            attr2.setValue("15");
            player2.setAttributeNode(attr2);
            
            Element nickname2 = doc.createElement("nickname");
            nickname2.appendChild(doc.createTextNode("Netherstorm"));
            player2.appendChild(nickname2);
            
            Element email2 = doc.createElement("email");
            email2.appendChild(doc.createTextNode("netherlands@gmail.com"));
            player2.appendChild(email2);
            
            Element score2 = doc.createElement("score");
            score2.appendChild(doc.createTextNode("413"));
            player2.appendChild(score2);
            
            Element player3 = doc.createElement("player");
            rootElement.appendChild(player3);
           
            Attr attr3 = doc.createAttribute("no");
            attr3.setValue("18");
            player3.setAttributeNode(attr3);
            
            Element nickname3 = doc.createElement("nickname");
            nickname3.appendChild(doc.createTextNode("Earthquake"));
            player3.appendChild(nickname3);
            
            Element email3 = doc.createElement("email");
            email3.appendChild(doc.createTextNode("badnews17@gmail.com"));
            player3.appendChild(email3);
            
            Element score3 = doc.createElement("score");
            score3.appendChild(doc.createTextNode("478"));
            player3.appendChild(score3);
            
            Element player4 = doc.createElement("player");
            rootElement.appendChild(player4);
           
            Attr attr4 = doc.createAttribute("no");
            attr4.setValue("21");
            player4.setAttributeNode(attr4);
            
            Element nickname4 = doc.createElement("nickname");
            nickname4.appendChild(doc.createTextNode("Intercept"));
            player4.appendChild(nickname4);
            
            Element email4 = doc.createElement("email");
            email4.appendChild(doc.createTextNode("keepcalmandwin@gmail.com"));
            player4.appendChild(email4);
            
            Element score4 = doc.createElement("score");
            score4.appendChild(doc.createTextNode("505"));
            player4.appendChild(score4);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("E:\\players.xml"));
            transformer.transform(source, result);         
            
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
