package com.github.situx.ime.rest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Main class for this project hosting the REST web service.
 * 
 */
@Path("/lndwa")
public class Main {
	/**
	 * Resultstring to encode the result as UTF-8 encoding.
	 */
	private static final String utf8text = MediaType.TEXT_PLAIN
			+ ";charset=UTF-8";

	/**
	 * The webservice which can be accessed by the homepage.
	 * 
	 * @param text
	 *            the text to query
	 * @param ime
	 *            the ime to use
	 * @param num
	 *            the number of results to get
	 * @return the results as a JSON representation of the following form:
	 */
	@GET
	@Path("suggestion")
	@Produces(utf8text)
	public String getSuggestions(
			@DefaultValue("") @QueryParam("text") String text,
			@DefaultValue("akkadian.xml") @QueryParam("ime") String ime,
			@DefaultValue("5") @QueryParam("num") Integer num,
			@DefaultValue("0") @QueryParam("page") Integer from) {
		System.out.println("Curtype: " + text + " Ime: " + ime);
		return Data.getInstance().getTreebuilders().get(ime + ".xml")
				.query(text, num, from);
	}

	@GET
	@Path("methods")
	@Produces(utf8text)
	public String getMethods() {
		return Data.getInstance().getMethods();
	}
	
	@GET
	@Path("sampletextview")
	@Produces(utf8text)
	public String getSampleTextForLanguage(@DefaultValue("hit") @QueryParam("lang") String lang) {
		StringBuilder builder=new StringBuilder();
		File filee=new File("webapps/IMETest2/lang/"+lang+"/text");
		if(filee.exists() && filee.isDirectory()){
			for(String file:filee.list()){
				builder.append("<option value=\"lang/"+lang+"/text/"+file+"\">"+file.substring(0,file.lastIndexOf('.'))+"</option>");
			}
		}
		return builder.toString();
	}
	
	private static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	@GET
	@Path("loadsampletext")
	@Produces(utf8text)
	public String loadSampleText(@DefaultValue("") @QueryParam("path") String path) {
		if(path.isEmpty())
			return "";
		File text=new File(path);
		if(!text.exists())
			return "";
		try {
			return readFile(path, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	@GET
	@Path("maps")
	@Produces(utf8text)
	public String getMaps() {
		return Data.getInstance().getMaps();
	}

}
