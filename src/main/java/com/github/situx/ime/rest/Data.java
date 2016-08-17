package com.github.situx.ime.rest;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * Singleton class to store the treebuilder data for several languages.
 * 
 */
public class Data {
	/**
	 * Map of language to treebuilders to use.
	 */
	private Map<String, TreeBuilder> treebuilders = new TreeMap<String, TreeBuilder>();

	private String methods = "", maps = "";
	/**
	 * The instance of this singleton class.
	 */
	private static Data instance;

	/**
	 * Gets the instance of this singleton class.
	 * 
	 * @return the instance
	 */
	public static Data getInstance() {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}

	/**
	 * Constructor for this class. Reads and builds imetrees from the given
	 * corpus file in the ime folder.
	 */
	private Data() {
		File folder = new File("ime/");
		System.out.println(folder.getAbsolutePath());
		for (String filee : folder.list()) {
			System.out.println(filee);
		}
		for (File fil : folder.listFiles()) {
			getTreebuilders().put(fil.getName(), new TreeBuilder(fil));
		}
		maps += "{";
		for (String file : this.treebuilders.keySet()) {
			methods += "<option value=\""
					+ file.substring(0, file.lastIndexOf('.')) + "\">"
					+ Character.toUpperCase(file.charAt(0))
					+ file.substring(1, file.lastIndexOf('.')) + "</option>";
			maps += "\"" + file.substring(0, file.lastIndexOf('.')) + "\":{},";
		}
		maps = maps.substring(0, maps.length() - 1) + "}";
	}

	/**
	 * Gets the generated treebuilders.
	 * 
	 * @return the treebuilder map
	 */
	public Map<String, TreeBuilder> getTreebuilders() {
		return treebuilders;
	}

	/**
	 * Sets the generated treebuilders.
	 * 
	 * @param treebuilders
	 *            the map of treebuilders to set
	 */
	public void setTreebuilders(Map<String, TreeBuilder> treebuilders) {
		this.treebuilders = treebuilders;
	}

	public String getMethods() {
		return this.methods;
	}

	public String getMaps() {
		return this.maps;
	}
}
