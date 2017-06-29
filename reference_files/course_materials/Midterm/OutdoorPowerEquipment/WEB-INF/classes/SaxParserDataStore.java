
/*********


http://www.saxproject.org/

SAX is the Simple API for XML, originally a Java-only API. 
SAX was the first widely adopted API for XML in Java, and is a “de facto” standard. 
The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java. 

The following URL from Oracle is the JAVA documentation for the API

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html


*********/
import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import  java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


////////////////////////////////////////////////////////////

/**************

SAX parser use callback function  to notify client object of the XML document structure. 
You should extend DefaultHandler and override the method when parsin the XML document

***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
    LawnMower lawnMower;
    SnowBlower snowBlower;
    
    static HashMap<String,LawnMower> lawnMowers;
    static HashMap<String,SnowBlower> snowBlowers;
    String consoleXmlFileName;
    String elementValueRead;
	String currentElement="";
    public SaxParserDataStore()
	{
	}
	public SaxParserDataStore(String consoleXmlFileName) {
    this.consoleXmlFileName = consoleXmlFileName;
    lawnMowers = new HashMap<String, LawnMower>();
	snowBlowers=new  HashMap<String, SnowBlower>();
	
	parseDocument();
    }

   //parse the xml using sax parser to get the data
    private void parseDocument() 
	{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try 
		{
	    SAXParser parser = factory.newSAXParser();
	    parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}

   

////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document. 
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.  
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////
	
	// when xml start element is parsed store the id into respective hashmap for SmartPhone,laptops etc 
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("lawnMower")) 
		{
			currentElement="lawnMower";
			lawnMower = new LawnMower();
            lawnMower.setId(attributes.getValue("id"));
		}
        if (elementName.equalsIgnoreCase("snowBlower"))
		{
			currentElement="snowBlower";
			tablet = new Tablet();
            tablet.setId(attributes.getValue("id"));
        }


    }
	// when xml end element is parsed store the data into respective hashmap for SmartPhone,laptops etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equals("lawnMower")) {
			phones.put(lawnMower.getId(),lawnMower);
			return;
        }
 
        if (element.equals("snowBlower")) {	
			tablets.put(tablet.getId(),snowBlower);
			return;
        }
        
        if (element.equalsIgnoreCase("image")) {
		    if(currentElement.equals("lawnMower"))
				lawnMower.setImage(elementValueRead);
        	if(currentElement.equals("snowBlower"))
				snowBlower.setImage(elementValueRead);         
			return;
        }
        

		if (element.equalsIgnoreCase("discount")) {
            if(currentElement.equals("lawnMower"))
				lawnMower.setDiscount(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("snowBlower"))
				snowBlower.setDiscount(Double.parseDouble(elementValueRead));      
			return;
	    }

	    if (element.equalsIgnoreCase("rebate")) {
            if(currentElement.equals("lawnMower"))
				lawnMower.setRebate(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("snowBlower"))
				snowBlower.setRebate(Double.parseDouble(elementValueRead));      
			return;
	    }

		if (element.equalsIgnoreCase("condition")) {
            if(currentElement.equals("lawnMower"))
				lawnMower.setCondition(elementValueRead);
        	if(currentElement.equals("snowBlower"))
				snowBlower.setCondition(elementValueRead);      
			return;  
		}

		if (element.equalsIgnoreCase("manufacturer")) {
            if(currentElement.equals("lawnMower"))
				lawnMower.setRetailer(elementValueRead);
        	if(currentElement.equals("snowBlower"))
				snowBlower.setRetailer(elementValueRead);         
			return;
		}

        if (element.equalsIgnoreCase("name")) {
            if(currentElement.equals("lawnMower"))
				lawnMower.setName(elementValueRead);
        	if(currentElement.equals("snowBlower"))
				snowBlower.setName(elementValueRead);         
			return;
	    }
	
        if(element.equalsIgnoreCase("price")){
			if(currentElement.equals("lawnMower"))
				lawnMower.setPrice(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("snowBlower"))
				snowBlower.setPrice(Double.parseDouble(elementValueRead));       
			return;
        }

	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////
	
//call the constructor to parse the xml and get product details
 public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME+"\\webapps\\BestDeals\\ProductCatalog.xml");
    } 
}
