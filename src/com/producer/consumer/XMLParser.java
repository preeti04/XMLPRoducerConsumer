package com.producer.consumer;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Hashtable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


/**
 * 
 * Using SAXParser, XML files are parsed.
 * Notify waiting queue onces parsing is  done
 * which in turn inform all threads.
 *
 */
public class XMLParser extends DefaultHandler {
        private String url;
        private WaitingQueue queue;
        private Hashtable doc = null;
        private String elem = null;
        private String elemmark = "master";
        private StringBuffer sbData = new StringBuffer();


        public XMLParser(String url, WaitingQueue queue) {
                this.url = url;
                this.queue = queue;
        }

        public void parse() {
                try {

                        SAXParserFactory factory = SAXParserFactory.newInstance();
                       
                        SAXParser saxparser = factory.newSAXParser();

                        XMLReader parser = saxparser.getXMLReader();

                      
                        parser.setContentHandler(this);
                       
                        URL urlObj = new URL(url);
                        System.out.println(urlObj.toString());

                        parser.parse((new URI(urlObj.toString())).toString());

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        
        @Override
        public void startDocument() throws SAXException {
                System.out.println("*******************Start XML read*******************");
        }

        @Override
        public void endDocument() throws SAXException {
                queue.end();
                System.out.println("*******************End XML read.............");
        }

        @Override
        public void startElement(
                String namespaceURI,
                String localName,
                String qName,
                Attributes atts)
                throws SAXException {
                System.out.println(
                        " Starting Element        " + localName + " " + qName);
                if (qName.equalsIgnoreCase(elemmark)) {
                        doc = new Hashtable();
                }
                elem = qName;
        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
                String s = new String(ch, start, length);
                if (s.trim().equals("")) {
                        return;
                }

                sbData.append(s);


        }

        /**
         * Read and print end tag of the element
         */
        @Override
        public void endElement(String namespaceURI, String localName, String qName)
                throws SAXException {

                String s = sbData.toString();

                System.out.println("Element  is    " + elem + "  value is   " + s);
                if ((doc != null) & (s != null) & !(s.trim().equals("")))
                        doc.put(elem, s);

                sbData = new StringBuffer();

                System.out.println(" Ending element         " + qName);

                if (qName.equalsIgnoreCase(elemmark)) {
                      /*  System.out.println(
                                " End  Element     " + localName); */
                        queue.put(doc);
                        doc = null;
                }
        }

   
}
