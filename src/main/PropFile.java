package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class PropFile {
	PropFile(){}

	static public boolean readParemeters(Properties p) throws JDOMException, IOException{
		File f = new File("resources/properties.xml");
		if(!f.canRead()) return false;
		
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(f);
	    	//some setup
		Element propElement = doc.getRootElement();

		//Access a child element
		Element posElement = propElement.getChild("window").getChild("position");
		//show success or failure
		if(posElement != null) {
		    p.setPosX(Integer.parseInt(posElement.getChild("X").getText()));
		    p.setPosY(Integer.parseInt(posElement.getChild("Y").getText()));
		    
		} else {
		    System.out.println("Something is wrong.  We did not find a propperties.xml");
		    return false;
		}
		posElement = propElement.getChild("window").getChild("size");
		//show success or failure
		if(posElement != null) {
		    p.setSize(Integer.parseInt(posElement.getText()));
		} else {
		    System.out.println("Something is wrong.  We did not find a propperties.xml");
		    return false;
		}
		posElement = propElement.getChild("timer").getChild("reloadWork");
		//show success or failure
		if(posElement != null) {
		    p.setWorkHour(Integer.parseInt(posElement.getChild("hour").getText()));
		    p.setWorkMin(Integer.parseInt(posElement.getChild("minutes").getText()));
		} else {
		    System.out.println("Something is wrong.  We did not find a propperties.xml");
		    return false;
		}
	    
		posElement = propElement.getChild("timer").getChild("reloadNotWork");
	      //show success or failure
	    if(posElement != null) {
	         p.setPlayHour(Integer.parseInt(posElement.getChild("hour").getText()));
	         p.setPlayMin(Integer.parseInt(posElement.getChild("minutes").getText()));
	      } else {
	          System.out.println("Something is wrong.  We did not find a propperties.xml");
	          return false;
	      }

	    
	    return true;
	}
    static public boolean writeParemeters(Properties p) throws IOException{
	File f = new File("resources/properties.xml");
	if(!f.canWrite()) return false;
		
	//Document doc = new Document();
		
	// Create the root element
        Element prop = new Element("parameters");
        //create the document
        Document myDocument = new Document(prop);

        //add a comment
        prop.addContent(new Comment("Properties of eyeSaver"));

        //add some child elements
        /*
         * Note that this is the first approach to adding an element and
         * textual content.  The second approach is commented out.
         */
        Element win = new Element("window");
        Element pos = new Element("position");
        pos.addContent(new Element("X").setText(String.valueOf(p.getPosX())));
        pos.addContent(new Element("Y").setText(String.valueOf(p.getPosY())));
              
        win.addContent(pos);
        win.addContent(new Element("size").setText(String.valueOf(p.getSize())));
        prop.addContent(win);
        
         
        Element timer = new Element("timer");
        Element timeW = new Element("reloadWork");
        timeW.addContent(new Element("hour").setText(String.valueOf(p.getWorkHour())));
        timeW.addContent(new Element("minutes").setText(String.valueOf(p.getWorkMin())));
      
        Element timeNW = new Element("reloadNotWork");
        timeNW.addContent(new Element("hour").setText(String.valueOf(p.getPlayHour())));
        timeNW.addContent(new Element("minutes").setText(String.valueOf(p.getPlayMin())));
      
        
        timer.addContent(timeW);
        timer.addContent(timeNW);
        prop.addContent(timer);
  	
	XMLOutputter outputter = new XMLOutputter();
		
	//output to a file
        FileWriter writer = new FileWriter(f);
        outputter.output(myDocument, writer);
        writer.close();

	return true;
    }
}
/*
Sample config file

 <?xml version="1.0" ?> 
<!DOCTYPE parameters> 
<parameters>
<window>
	<position>
		<X>100</X>
		<Y>100</Y>
	</position>
	<size>1</size>
</window>
<timer>
	<reloadWork>
		<hour>1</hour>
		<minutes>0</minutes> 
	</reloadWork>
	<reloadNotWork>
		<hour>0</hour>
		<minutes>1</minutes> 
	</reloadNotWork>
</timer>
</parameters> 
 */
