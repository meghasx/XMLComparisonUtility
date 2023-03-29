package net.codejava.javaee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;


/**
 * Servlet implementation class XMLComparer
 */
@WebServlet("/xmlComparer")
public class XMLComparer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XMLComparer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathXml1 = request.getParameter("xml1");
		String pathXml2 = request.getParameter("xml2");
		PrintWriter writer = response.getWriter();
		writer.println("<h1>Hello, Please wait..Comparison is in Process.</h1>");
		writer.close();
		try {
			compareTwoPayloads(pathXml1, pathXml2);
		} catch (SQLException | ParserConfigurationException | IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public void compareTwoPayloads(String path1, String path2) throws SQLException, ParserConfigurationException, IOException, SAXException {

//       String pathLWR = "C:\\Automation\\FrameworkExecutions\\Payloads_LWR_"+LWRQuoteRef;
//       String pathSOR = "C:\\Automation\\FrameworkExecutions\\Payloads_SOR_"+SORQuoteRef;
//
//       File xmlFile = new File(pathLWR+"\\LWR_"+payloadForComparison+".xml");
//       File xmlFile2 = new File(pathSOR+"\\SOR_"+payloadForComparison+".xml");
      // ExtentReports reports = new ExtentReports("Path of directory to store the resultant HTML file", true);



       File xmlFile = new File(path1);
       File xmlFile2 = new File(path2);

       //context.getReporter().setLevel(ReportLevel.CHILD).setReportStatus(Status.INFO).addPayload(Services.VehicleEnrichRequest, "LWR_PolarisRequest",getAllPayloads(LWRQuoteRef).getRequests().getVehicleEnrichAsync()).attachToReport();
       //context.getReporter().setLevel(ReportLevel.CHILD).setReportStatus(Status.INFO).addPayload(Services.VehicleEnrichRequest,"SOR_PolarisRequest", getAllPayloads(SORQuoteRef).getRequests().getVehicleEnrichAsync()).attachToReport();

       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder = factory.newDocumentBuilder();
       Document doc = builder.parse(xmlFile);
       Document doc2 = builder.parse(xmlFile2);

       NodeList node1 = doc.getDocumentElement().getChildNodes();
       NodeList node2 = doc2.getDocumentElement().getChildNodes();

       //  compareNodes(node1, node2);
       compareNodesWithValues(doc,doc2, node1, node2);
   }

   public void compareNodes(NodeList list1, NodeList list2){

       if(list1.getLength() == list2.getLength()) {

           for (int i = 0; i < list1.getLength(); i++) {

               if(list1.item(i).toString().equalsIgnoreCase(list2.item(i).toString())) {
                   //context.getReporter().addMessage(list1.item(i).toString()+"Schema is correct");
                   System.out.println(list1.item(i).toString()+"Schema is correct");

                   if(list1.item(i).getAttributes().getLength() != 0){

                       if(list1.item(i).getAttributes().getLength() == list2.item(i).getAttributes().getLength()){

                           for(int j= 0; j< list1.item(i).getAttributes().getLength(); j++){

                               if(list1.item(i).getAttributes().item(j).equals(list1.item(i).getAttributes().item(j)))
                               {
                                   System.out.println(list1.item(i).getAttributes().item(j) +"Attributes are correct");
                               }
                               else
                               {
                                   System.out.println(list1.item(i).getAttributes().item(j)+"Attributes are not correct");
                               }
                           }
                       }
                       else{
                           System.out.println(list1.item(i)+"Attributes count is not correct");
                       }

                   }
               }
               if(list1.item(i).hasChildNodes())
               {
                   NodeList l1= list1.item(i).getChildNodes();
                   NodeList l2= list2.item(i).getChildNodes();

                   compareNodes(l1, l2);
               }
           }
       }
       else
       {
           System.out.println(list1.toString() +"nodes are not present in payload 2");
       }

   }


   public void compareNodesWithValues(Document doc, Document doc1,NodeList list1, NodeList list2){

       //checking the length of the nodes under comparison
           if(list1.getLength() == list2.getLength()) {

           for (int i = 0; i < list1.getLength(); i++) {

               //excluding any blank nodes
             //  if(!list1.item(i).getTextContent().trim().isEmpty()) {

               if((list1.item(i).hasAttributes() || list1.item(i).hasChildNodes()) || !(list1.item(i).getTextContent().trim().isEmpty()) ) {
                   //comparing nodes while transvering through xml
                   if (list1.item(i).toString().equalsIgnoreCase(list2.item(i).toString())) {

                       //context.getReporter().addMessage(list1.item(i).toString()+" Schema is correct").setLevel(CHILD).attachToReport();
                       System.out.println(list1.item(i).toString() + "Schema is correct");

                       //checking if node is having any text values, if yes then comparing
                       if(list1.item(i).getNodeValue() != null)
                           if(list1.item(i).getNodeValue().equals(list2.item(i).getNodeValue())){
                               System.out.println(list1.item(i).getNodeValue() + "node text is correct");
                              // context.getReporter().addMessage(list1.item(i).getNodeValue()+" node text is correct").setLevel(CHILD).attachToReport();
                           }
                       else{
                               System.out.println(list1.item(i).getNodeValue() + "node text is not correct and is mismatched");
                               //context.getReporter().addMessage(list1.item(i).getNodeValue()+" node text is not correct and is mismatched").setLevel(CHILD).attachToReport();
                           }

                       //checking if nodes have any attributes
                       if (list1.item(i).getAttributes() != null) {

                           //checking if both nodes have same number of attributes
                           if (list1.item(i).getAttributes().getLength() == list2.item(i).getAttributes().getLength()) {

                               for (int j = 0; j < list1.item(i).getAttributes().getLength(); j++) {

                                   //comparing attributes of the nodes
                                   if (list1.item(i).getAttributes().item(j).equals(list1.item(i).getAttributes().item(j))) {
                                       System.out.println(list1.item(i).getAttributes().item(j) + "Attributes are correct");
                                 //      context.getReporter().addMessage(list1.item(i).getAttributes().item(j)+" Attributes are correct").setLevel(CHILD).attachToReport();
                                   } else {
                                       System.out.println(list1.item(i).getAttributes().item(j) + "Attributes are not correct");
                                   //    context.getReporter().addMessage(list1.item(i).getAttributes().item(j)+" Attributes are not correct").setLevel(CHILD).attachToReport();
                                   }
                               }
                               //if the attributes count does not match
                           } else {
                               System.out.println(list1.item(i) + "Attributes count does not match");
                           //    context.getReporter().addMessage(list1.item(i)+" Attributes count does not match").setLevel(CHILD).attachToReport();
                           }

                       }
                   }
                   //nodes are not found in the correct position
                   else {


                       if(!list1.item(i).getTextContent().trim().isEmpty()){

                           //saving our base payload node value we want to search
                           String nodeTextValue = list1.item(i).getTextContent();
                           NodeList nodes = null;

                           //checking if the node is an element node
                           if(list1.item(i).getNodeType() == Node.ELEMENT_NODE){
                               nodes = doc1.getElementsByTagName(list1.item(i).getNodeName());
                           }
                           //checking if the node is an text node
                           else if(list1.item(i).getNodeType() == Node.TEXT_NODE){
                               nodes = doc1.getElementsByTagName(list1.item(i).getParentNode().getNodeName());
                           }

                           if (nodes.getLength() > 0) {
                               for (int k = 0; k < nodes.getLength(); k++) {
                                   //searching the node containing specific value
                                   if (nodes.item(k).getTextContent().equals(nodeTextValue)) {
                                       System.out.println("node found but not in correct position");
                             //          context.getReporter().addMessage(nodes.item(k)+" node found but not in correct position").setLevel(CHILD).attachToReport();

                                       //checking for attributes
                                       if (list1.item(i).getAttributes() != null) {

                                           if (list1.item(i).getAttributes().getLength() == nodes.item(k).getAttributes().getLength()) {

                                               for (int j = 0; j < list1.item(i).getAttributes().getLength(); j++) {

                                                   if (list1.item(i).getAttributes().item(j).equals(nodes.item(k).getAttributes().item(j))) {
                                                       System.out.println(list1.item(i).getAttributes().item(j) + "Attributes are correct");
                               //                        context.getReporter().addMessage(list1.item(i).getAttributes().item(j)+" Attributes are correct").setLevel(CHILD).attachToReport();
                                                   } else {
                                                       System.out.println(list1.item(i).getAttributes().item(j) + "Attributes are not correct");
                                 //                      context.getReporter().addMessage(list1.item(i).getAttributes().item(j)+" Attributes are not correct").setLevel(CHILD).attachToReport();
                                                   }
                                               }
                                           } else {
                                               System.out.println(list1.item(i) + "Attributes count is not correct");
                                   //            context.getReporter().addMessage(list1.item(i)+" Attributes count is mismatched").setLevel(CHILD).attachToReport();
                                           }

                                       }
                                   }
                                   else {
                                       System.out.println(list1.item(i)+" node value '"+list1.item(i).getTextContent() + "' is mismatched from actual node value found :" + nodes.item(k).getTextContent());
                                     //  context.getReporter().addMessage(list1.item(i)+" node value '"+list1.item(i).getTextContent() + "' is mismatched from actual node value found :" + nodes.item(k).getTextContent()).setLevel(CHILD).attachToReport();
                                   }
                               }
                           }
                           else{
                               System.out.println(list1.item(i)+" not found");
                               //context.getReporter().addMessage(list1.item(i)+" not found").setLevel(CHILD).attachToReport();
                           }
                       }

                   }

                   //checking for the child nodes
                   if (list1.item(i).hasChildNodes()) {
                       
                       NodeList l1 = list1.item(i).getChildNodes();
                       NodeList l2 = list2.item(i).getChildNodes();

                       //recursive call to compare the child nodes again
                       compareNodesWithValues(doc, doc1, l1, l2);
                   }
               }
           }
       }
       else
       {
           //logic when node length does not match
           System.out.println(list1 +" nodes length mis matches");
         //  context.getReporter().addMessage(list1 +" nodes length mis matches").setLevel(CHILD).attachToReport();
           for (int i = 0; i < list1.getLength(); i++){

               if(!list1.item(i).getTextContent().trim().isEmpty()){

                   String nodeTextValue = list1.item(i).getTextContent();
                   NodeList nodes = null;
                   //checking if the node is an element node
                   if(list1.item(i).getNodeType() == Node.ELEMENT_NODE){
                       nodes = doc1.getElementsByTagName(list1.item(i).getNodeName());
                   }
                   //checking if the node is a text node
                   else if(list1.item(i).getNodeType() == Node.TEXT_NODE){
                       nodes = doc1.getElementsByTagName(list1.item(i).getParentNode().getNodeName());
                   }

                   if (nodes.getLength() > 0) {
                       for (int k = 0; k < nodes.getLength(); k++) {
                           //searching the node containing specific value
                           if (nodes.item(k).getTextContent().equals(nodeTextValue)) {
                               System.out.println(nodes.item(k)+ " node found but not in correct position");
           //                    context.getReporter().addMessage(nodes.item(k)+ " node found but not in correct position").setLevel(CHILD).attachToReport();
                               //checking for attributes
                               if (list1.item(i).getAttributes() != null) {

                                   if (list1.item(i).getAttributes().getLength() == nodes.item(k).getAttributes().getLength()) {

                                       for (int j = 0; j < list1.item(i).getAttributes().getLength(); j++) {

                                           if (list1.item(i).getAttributes().item(j).equals(nodes.item(k).getAttributes().item(j))) {
                                               System.out.println(list1.item(i).getAttributes().item(j) + " Attributes are correct");
             //                                  context.getReporter().addMessage(list1.item(i).getAttributes().item(j) + "Attributes are correct").setLevel(CHILD).attachToReport();
                                           } else {
                                               System.out.println(list1.item(i).getAttributes().item(j) + " Attributes are not correct");
               //                                context.getReporter().addMessage(list1.item(i).getAttributes().item(j) + "Attributes are not correct").setLevel(CHILD).attachToReport();
                                           }
                                       }
                                   } else {
                                       System.out.println(list1.item(i) + " Attributes count does not match");
                 //                      context.getReporter().addMessage(list1.item(i) + " Attributes count does not match").setLevel(CHILD).attachToReport();
                                   }

                               }
                               //checking for the child nodes
                               if (list1.item(i).hasChildNodes()) {

                                   NodeList l1 = list1.item(i).getChildNodes();
                                   NodeList l2 = nodes.item(k).getChildNodes();

                                   //recursive call to compare the child nodes again
                                   compareNodesWithValues(doc, doc1, l1, l2);
                               }
                           }
                           else {
                               System.out.println(list1.item(i)+" node value '"+list1.item(i).getTextContent() + "' is mismatched from actual node value found: " + nodes.item(k).getTextContent());
                   //            getReporter().addMessage(list1.item(i)+" node value '"+list1.item(i).getTextContent() + "' is mismatched from actual node value found: " + nodes.item(k).getTextContent()).setLevel(CHILD).attachToReport();
                           }
                       }
                   }
                   else{
                       System.out.println(list1.item(i)+" not found");
                     //  context.getReporter().addMessage(list1.item(i)+" not found").setLevel(CHILD).attachToReport();
                   }
               }


           }

       }

   }

}
