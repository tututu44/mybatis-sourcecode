package com.mybatis.source.now.xml;

import com.sun.xml.internal.bind.v2.runtime.output.FastInfosetStreamWriterOutput;
import org.apache.ibatis.io.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

public class XPathTest {
    public static void main(String[] args) throws Exception {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream resource = Resources.getResourceAsStream("person.xml");
        Document document = documentBuilder.parse(resource);
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList)xPath.evaluate("/users/*", document, XPathConstants.NODESET);
        for (int i = 1; i < nodeList.getLength() + 1; i++) {
            String path = "/users/user["+i+"]";
            System.out.println((String) xPath.evaluate(path + "/@id",document,XPathConstants.STRING));
            System.out.println((String) xPath.evaluate(path + "/name",document,XPathConstants.STRING));
            System.out.println((String) xPath.evaluate(path + "/createTime",document,XPathConstants.STRING));
            System.out.println((String) xPath.evaluate(path + "/password",document,XPathConstants.STRING));
            System.out.println((String) xPath.evaluate(path + "/phone",document,XPathConstants.STRING));
            System.out.println((String) xPath.evaluate(path + "/nickName",document,XPathConstants.STRING));
        }
    }
}
