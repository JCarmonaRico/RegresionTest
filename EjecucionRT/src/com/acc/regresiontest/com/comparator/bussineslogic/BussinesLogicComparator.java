package com.acc.regresiontest.com.comparator.bussineslogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import com.acc.regresiontest.com.comparator.domains.Difference;
import com.acc.regresiontest.com.comparator.domains.Value;



/**
 * 
 * @author j.rodriguezguardeno
 *
 */
public class BussinesLogicComparator extends XMLTestCase {

	/**
	 * Compare diff two XML and Print txt
	 * 
	 * @param xml2String
	 * @param xml2String2
	 */

	public  String CompareXML(String xml2String, String xml2String2) {



		DetailedDiff diff = null;
		String json = null;

		try {
			// Render all differences between the two xml.

			diff = new DetailedDiff(XMLUnit.compareXML(xml2String, xml2String2));

			// Call the function to create the file.
			 json = createjsondiff(diff);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;

	}

	/**
	 * 
	 * create List with xml diff
	 * 
	 * @param diff
	 *            return List<Diferencia>
	 */

	public  List<Difference> GenerateList(DetailedDiff diff) {
		ArrayList<Difference> difference = new ArrayList<Difference>();
		String espectedValue;
		String valueFound;
		String route;
		String message;
		try {

			if (diff.identical()) {
				return difference;
			} else {

				// List containing all the differences
				List<?> allDifferences = diff.getAllDifferences();

				// travel all differences to remove the path value
				// expected and found value.
				for (Object o : allDifferences) {

					// It gives value to the indices for cutting values
					// differences

					message = o.toString();

					espectedValue = StringUtils.substringAfter(message, "value '");
					espectedValue = StringUtils.substringBefore(espectedValue, "' but");

					valueFound = StringUtils.substringAfter(message, "' but was '");
					valueFound = StringUtils.substringBefore(valueFound, "'");

					route = StringUtils.substringAfter(message, "at /");
					route = StringUtils.substringBefore(route, "/text()");

					Difference d = new Difference();
					Value v = new Value();

					//File dontSearch = new File("app/ucuadrom/temp/dontSearch.txt");
					//System.out.println("Working Directory = " +
				    //          System.getProperty("user.dir"));
					//System.out.println(dontSearch.canRead());
					//System.out.println(dontSearch.isFile());
					//List<String> dontSearchList = ReadFile(dontSearch);
					

					// takes the values and save

					v.setExpectedValue(espectedValue);
					v.setValueFound(valueFound);
					d.setValues(v);
					
					

					// Break the route to generate nodes
					String delimiter = "/";
					String[] tree;
					tree = route.split(delimiter);

					// Removes characters added to the route ([x])
					// stores the array of nodes and stores them in the list of
					// differences
					for (int i = 0; i < tree.length; i++) {

						tree[i] = StringUtils.substringBefore(tree[i], "[");
					}
					d.setTree(tree);

					//for (String s : dontSearchList) {
						int l = tree.length;
						if (tree[l-1].equals("GeneratedTime")) {
						}

						else {
							difference.add(d);
						}
					//}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return difference;

	}

	/**
	 * Create file with the tree Json differences
	 * 
	 * @param diff
	 */

	public String createjsondiff (DetailedDiff diff) {


		ArrayList<Difference> differences = (ArrayList<Difference>) GenerateList(diff);

		try {

			if (differences.isEmpty()) {
				return "{'text' : 'Report','children' : [{ 'text' : 'Identical' }]}";
				
			} else {
				/*
				for (Difference difference : differences) {
					for (String tree : difference.tree) {
						for(String campo : camposIngorar){
							if(tree.equals(campo)){
								differences.remove(index);
							}
						}
						
					}
				}
				*/
				String json = createXML2Json(differences);

				return json;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	/**
	 * Parse XML archive to one String Line
	 * 
	 * @param xmlFile
	 * @return
	 * @throws IOException
	 */
	public  String XMLtoString(File xmlFile) throws IOException {

		// Let's get XML file as String using BufferedReader
		// FileReader uses platform's default character encoding
		// if you need to specify a different encoding, use InputStreamReader

		Reader fileReader = new FileReader(xmlFile);
		BufferedReader bufReader = new BufferedReader(fileReader);

		StringBuilder sb = new StringBuilder();
		String line = bufReader.readLine();

		while (line != null) {
			sb.append(line).append("\n");
			line = bufReader.readLine();
		}
		String xml2String = sb.toString();
		bufReader.close();

		return xml2String;
	}
	
	

	public  List<String> ReadFile(File fileName) throws IOException {

		List<String> dontSearchList = new ArrayList<String>();

		Reader fileReader = new FileReader(fileName);
		BufferedReader bufReader = new BufferedReader(fileReader);
		String line = bufReader.readLine();

		while (line != null) {
			String dontSearch = line;
			dontSearchList.add(dontSearch);
			line = bufReader.readLine();
		}
		bufReader.close();
		return dontSearchList;

	}

	/**
	 * from a list of differences it generates a string structure xml and json
	 * transforms
	 * 
	 * @param differences
	 * @return
	 */
	public  String createXML2Json(List<Difference> differences) {

//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
//		xml += "<Report>";
//		String tree[];
//
//		// through the list of nodes and copy a string in XML format
//
//		for (int i = 0; i < differences.size(); i++) {
//
//			tree = differences.get(i).getTree();
//			for (int j = 0; j < tree.length; j++) {
//				xml += "<" + tree[j] + ">";
//				
//			}
//			
//			
//
//			// added value expected and value found
//
//			xml += "<found>" + differences.get(i).getValues().getValueFound() + "</found>";
//			xml += "<expected>" + differences.get(i).getValues().getExpectedValue() + "</expected>";
//
//			// closed loop nodes
//
//			for (int j = tree.length - 1; j >= 0; j--) {
//
//				xml += "</" + tree[j] + ">";
//			}
//		}
//
//		xml += "</Report>";
//
//		// transforms the string in json
//
//		int prettyPrintIndentFactor = 4;
//		String jsonPrettyPrintString = "";
//
//		try {
//			JSONObject xmlJSONObj = XML.toJSONObject(xml);
//
//			jsonPrettyPrintString = xmlJSONObj.toString(prettyPrintIndentFactor);
//
//			System.out.println("jsonCreado");
//		} catch (JSONException je) {
//			System.out.println(je.toString());
//
//		}
//
//		return jsonPrettyPrintString;
		
		JsTreeNodeElement rootElement = null;
		try{
			

			String ruta = "";
			String tree[];
			rootElement = new JsTreeNodeElement("Report", null);
			for(int i = 0; i < differences.size(); i++){
				tree = differences.get(i).getTree();
				ruta ="";
				for (int j = 0; j < tree.length-1; j++) {
					ruta += tree[j]+"/";
				}
				ruta += tree[tree.length-1];
				rootElement.addDifference(ruta	,differences.get(i).getValues().getExpectedValue(),differences.get(i).getValues().getValueFound());
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rootElement.toJSON();
		

	}

}
