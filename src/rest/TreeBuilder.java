package rest;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Builds the ime tree to handle.
 */
public class TreeBuilder extends DefaultHandler2{
	/**The root element of the tree.*/
    private IMETree root=new IMETree();
    /**
     * Constructor for this class.
     * @param file the corpus file to build the tree from
     */
    public TreeBuilder(File file){
        System.setProperty("avax.xml.parsers.SAXParserFactory",
                "org.apache.xerces.parsers.SAXParser");
    	SAXParser parser;
		try {
			parser = SAXParserFactory.newInstance().newSAXParser();
	    	parser.parse(file,this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
    /**
     * Adds a combination of transcription,characters and frequency in a new treenode to the tree.
     * @param translit the transcription to add
     * @param chars the characters to add
     * @param frequency the frequency to add
     * @param depth the current depth of the tree
     */
    private void addWordComboToTree(String translit,String chars,Integer frequency,Integer depth){
        IMETree curnode=root;
        for(int i=0;i<translit.length();i++){
        	String wordchar=translit.substring(i,i+1);
            IMETree res=curnode.containsChild(curnode.getWord()+wordchar);
            if(res==null){
                curnode=depthbuildWODictHandler(curnode, wordchar,chars,translit,frequency, (i == translit.length() - 1),++depth,translit.length());
                //System.out.println("Created Curnode: "+curnode.toString()+" "+curnode.getCachewords().toString());
            }else{
                curnode=res;
                //System.out.println("Found Curnode: "+curnode.toString()+" "+curnode.getCachewords().toString());
                if(!curnode.getCachewords().containsKey(frequency)){
                	curnode.getCachewords().put(frequency, new TreeMap<String,Set<String>>());
                }
                if(i+1==translit.length()){
                	curnode.setIsWord(true);
                	if(!curnode.getCachewords().get(frequency).containsKey(curnode.getWord())){
                		curnode.getCachewords().get(frequency).put(curnode.getWord(),new TreeSet<String>());
                	}
                	curnode.getCachewords().get(frequency).get(curnode.getWord()).add(chars);
                }else{
                	if(!curnode.getCachewords().get(frequency).containsKey(curnode.getWord())){
                		curnode.getCachewords().get(frequency).put(curnode.getWord(),new TreeSet<String>());
                	}
                	curnode.getCachewords().get(frequency).get(curnode.getWord()).add(chars+translit.substring(++depth,translit.length()));                   
                }
            }
        }
    }
    /**
     * Builds the tree recursively.
     * @param node the current node
     * @param newchar the next char to extend the tree with
     * @param chars the characters of the final word to add
     * @param translit the transliteration of the final word to add
     * @param frequency the frequency of the final word to add
     * @param isWord indicator for every step if a word has been detected
     * @param depth the current depth of the current node in the tree
     * @param curmaxdepth the maximum depth of the tree
     * @return the current newly build node
     */
    public IMETree depthbuildWODictHandler(IMETree node,String newchar,String chars,String translit,Integer frequency,Boolean isWord,Integer depth,Integer curmaxdepth){
        IMETree newnode=new IMETree();
        newnode.setIsWord(isWord);
        newnode.setWord(node.getWord()+newchar);
        if(isWord){
        	newnode.setFrequency(frequency);
            newnode.setChars(chars);
        	if(!newnode.getCachewords().containsKey(newnode.getFrequency())){
        		newnode.getCachewords().put(newnode.getFrequency(),new TreeMap<String,Set<String>>());
        	}
        	if(!newnode.getCachewords().get(frequency).containsKey(newnode.getWord())){
        		newnode.getCachewords().get(frequency).put(newnode.getWord(),new TreeSet<String>());
        	}
        	newnode.getCachewords().get(frequency).get(newnode.getWord()).add(newnode.getChars());                   
        
        }else{
        	newnode.setFrequency(frequency);
            newnode.setChars(chars);
        	if(!newnode.getCachewords().containsKey(newnode.getFrequency())){
        		newnode.getCachewords().put(newnode.getFrequency(),new TreeMap<String,Set<String>>());
        	}
        	if(!newnode.getCachewords().get(frequency).containsKey(newnode.getWord())){
        		newnode.getCachewords().get(frequency).put(newnode.getWord(),new TreeSet<String>());
        	}
        	newnode.getCachewords().get(frequency).get(newnode.getWord()).add(newnode.getChars()+translit.substring(depth,curmaxdepth));                   
        }
        node.addChild(newnode);
        return newnode;
    }
    /**
     * Recursion call for the query method.
     * @param node the start node for querying
     * @param query querystring consisting of the already typed characters
     * @param result the map of cached words used as result set
     * @return The set of cached words for the target node
     */
    private Map<Integer,Map<String,Set<String>>> query(IMETree node,String query,Map<Integer,Map<String,Set<String>>> result){
        if(query.isEmpty()){
            return node.getCachewords();
        }
        IMETree curnode=node.containsChild(node.getWord()+query.substring(0,1));
        if(curnode==null){
            return new TreeMap<>();
        }else{
            result=this.query(curnode, query.substring(1, query.length()), result);
        }
        return result;
    }
    
    /**
     *  
     * @param map
     * @return
     */
    public static Map<Integer, Map<String ,String>> sortByValues(final Map<Integer, Map<String ,String>> map) {
    	Comparator<Integer> valueComparator =  new Comparator<Integer>() {
    	    public int compare(Integer k1, Integer k2) {
    	        int compare = k2.compareTo(k1);
    	        if (compare == 0) return 1;
    	        else return compare;
    	    }
    	};
    	Map<Integer, Map<String ,String>> sortedByValues = new TreeMap<Integer, Map<String ,String>>(valueComparator);
    	sortedByValues.putAll(map);
    	return sortedByValues;
    }

    /**
     * Queries a set of candidates with a fixed amount of candidates for the querystring.
     * @param query the querystring to use
     * @param num the maximum number of results
     * @return the set of candidates as JSON representation
     */
    public String query(String query,Integer num,Integer from){
    	Map<Integer,Map<String,Set<String>>> result=this.query(this.root, query, new TreeMap<Integer,Map<String,Set<String>>>());
    	if(result.isEmpty()){
    		return "";
    	}
    	String ret="_callbacks_.loadWords([\"SUCCESS\",[[\""+query+"\",[";
    	System.out.println(result.toString());
    	//result=sortByValues(result);
    	int i=0;
    	Iterator<Integer> iter1=result.keySet().iterator();
    	System.out.println(result.toString());
    	for(;iter1.hasNext();){
    		Integer outerkey=iter1.next();
    		for(String middlekey:result.get(outerkey).keySet()){
    			for(String key:result.get(outerkey).get(middlekey)){
        			if(i>num){
        				break;
        			}
        			if(i>=from){
                		ret+="\""+key+"\",";
        			}
            		i++;
    			}

    		}
    	}
    	ret=ret.substring(0,ret.length()-1);
    	ret+="]]]])";
    	System.out.println("Queryresult for "+query+": "+ret);
        return ret;
    }
    
    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        switch(qName){
            case "word": this.addWordComboToTree(attributes.getValue("translit"),attributes.getValue("chars"),Integer.valueOf(attributes.getValue("freq")),0);break;
        }
    }
    
}
