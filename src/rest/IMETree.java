package rest;


import java.util.*;

/**
 * A tree node implementing a char in the syntax tree.
 */
public class IMETree {
	/**Map<Frequency,Map<Transliteration,Set<Characters>>*/
    private Map<Integer,Map<String,Set<String>>> cachewords;
    /**Indicates if this node can be treated as a word.*/
    private Boolean isWord;
    /**The transliteration and the characters.*/
    private String word,chars;
    /**The frequency of the word/transliteration occurance.*/
    private Integer frequency;
    /**List of children of this tree node.*/
    private List<IMETree> children;
    /**
     * Constructor for this class.
     * @param word the transliteration
     * @param chars the characters
     * @param frequency the frequency of the word occurance
     */
    public IMETree(String word,String chars,Integer frequency){
        this.children=new LinkedList<>();
        this.cachewords=new TreeMap<>();
        this.isWord=true;
        this.word=word;
        this.chars=chars;
        this.frequency=frequency;
    }
    /**
     * Constructor for a blank node.
     */
    public IMETree(){
        this.children=new LinkedList<>();
        this.cachewords=new TreeMap<>();
        this.isWord=false;
        this.word="";
        this.chars="";
        this.frequency=0;
    }
    /**
     * Gets the frequency of this node.
     * @return the node frequency as Integer
     */
    public Integer getFrequency() {
		return frequency;
	}
    /**
     * Sets the frequency of this node.
     * @param frequency the frequency as Integer
     */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	/**
	 * Sets the characters of this node.
	 * @param chars the characters as String
	 */
	public void setChars(String chars) {
		this.chars = chars;
	}
	/**
	 * Gets the characters of this node.
	 * @return The characters as String
	 */
	public String getChars() {
        return chars;
    }
	/**
	 * Checks if this node has child nodes.
	 * @return true if child nodes exist, false otherwise
	 */
    public Boolean hasChildren(){
        return !this.children.isEmpty();
    }
    /**
     * Gets the list of children of this node.
     * @return the list of children
     */
    public List<IMETree> getChildren(){
        return this.children;
    }
    /**
     * Gets the map of cached words for this node.
     * @return the map of cached words
     */
    public Map<Integer,Map<String, Set<String>>> getCachewords() {
        return cachewords;
    }
    
    /**
     * Sets the map of cached words for this node.
     * @param cachewords the map to set
     */
    public void setCachewords(final Map<Integer,Map<String, Set<String>>> cachewords) {
        this.cachewords = cachewords;
    }
    /**
     * Checks if this node indicates a word.
     * @return true if it is, false otherwise
     */
    public Boolean getIsWord() {
        return isWord;
    }
    /**
     * Sets the flag if this node can be treated as a word.
     * @param isWord word indicator flag
     */
    public void setIsWord(final Boolean isWord) {
        this.isWord = isWord;
    }
    /**
     * Gets the current transcription.
     * @return The transcription as String
     */
    public String getWord() {
        return word;
    }
    /**
     * Sets the current word transcription.
     * @param word the transcription as String
     */
    public void setWord(final String word) {
        this.word = word;
    }
    
    /**
     * Sets the children of this node.
     * @param children the list of children to set
     */
    public void setChildren(final List<IMETree> children) {
        this.children = children;
    }
    /**
     * Adds a child to this node.
     * @param child the child to add
     */
    public void addChild(IMETree child){
        this.children.add(child);
    }
    /**
     * Checks if this node contains a specific child and returns it if found. 
     * @param nodevalue the nodevalue of the child
     * @return the child if found, null otherwise
     */
    public IMETree containsChild(String nodevalue){
        for(IMETree tree:this.children){
            if(tree.word.equals(nodevalue)){
                return tree;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Nodevalue: "+word+" IsWord: "+isWord+" Freq: "+frequency+" Children: "+this.children.size()+" "+this.cachewords.toString();
    }
}
