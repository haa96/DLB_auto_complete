import java.util.ArrayList;
import java.io.*;
public class DLBTrie {

	public DLBNode root = new DLBNode();
	public DLBTrie() { DLBNode root = new DLBNode();}
	public int n;
	final static ArrayList<String> predArray = new ArrayList<>(5);
	
	/**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
	public int size(){
		return n;
	}
 	/**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
	public boolean isEmpty(){
		return size()==0;
	}
	/**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
	public void put(DLBNode subRoot, String key, int d){ 
		DLBNode toAdd = new DLBNode();
		DLBNode cNode = new DLBNode();
		DLBNode sNode = new DLBNode();
		DLBNode curr = new DLBNode();
		DLBNode tempSib = new DLBNode();
		DLBNode tempChi = new DLBNode();
		toAdd = subRoot;
		
		while(toAdd != null)
		{
			if (key.charAt(d) ==toAdd.getKey())
			{
				if(d == key.length()-1)
				{
					toAdd.completeWord = true;
					n++;
					return;
				}
				if(toAdd.getChild() == null)
				{
					tempChi = toAdd;
					cNode.setKey(key.charAt(d+1));
					tempChi.setChild(cNode);
				}
				
				put(toAdd.getChild(),key,d+1);
				return;
			}
			
			curr = toAdd;
			toAdd = toAdd.getSibling();
		}//while
			
			tempSib = curr;
			sNode.setKey(key.charAt(d));
			tempSib.setSibling(sNode); 
			put(curr,key,d);
	}
	/**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
	public DLBNode get(DLBNode node, String key, int d){
		if( key == null) return null;
		if(d == key.length()) return node;
		char c = key.charAt(d);
		while (node != null)
		{	
			if (node.getKey()==c)
			{	if (d == key.length()-1)
					return node;
				if (d < key.length()-1) 
				return get(node.getChild(),key,d+1);
			}
			node = node.getSibling();
		}
		return null;
	}

	ArrayList<String> predictions = new ArrayList<>(5);
	public void collect( DLBNode node,  StringBuilder prefix)
	{	

		if(node == null) return;
		prefix.append(node.getKey());
		if(node.completeWord)
			{predictions.add(prefix.toString());}
		collect(node.getChild(),prefix);
		prefix.deleteCharAt(prefix.length()-1);
		collect(node.getSibling(),prefix);
		
	}
	/**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     */
	public void keysWithPrefix(String prefix){

		DLBNode subRoot = get(root, prefix,0);
		predictions.clear();
		if (subRoot == null) return;
		collect(subRoot, new StringBuilder(prefix));
		//System.out.println("after collect");
	}//keysWithPrefix
	/**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     */
	public static ArrayList<String> getHistoryWords(DLBNode node, StringBuilder prefix, ArrayList<String> list){
		if (node == null) return list;
		prefix.append(node.getKey());
		if(node.completeWord)
			list.add(prefix.toString());
		getHistoryWords(node.getChild(),prefix,list);
		prefix.deleteCharAt(prefix.length()-1);
		getHistoryWords(node.getSibling(),prefix,list);
		return list;
}
	
}// DLBTrie Class
 class DLBNode{
	
	private char key;
	private DLBNode sibling;
	private DLBNode child;
	public boolean completeWord = false;
	public DLBNode(){	key = '\0';	sibling = null;	child = null;}
	public char getKey(){ return key;}
	public void setKey( char newKey){ key = newKey;}
	public DLBNode getSibling(){ return sibling;}
	public void setSibling(DLBNode newSibling){ sibling = newSibling;}
	public DLBNode getChild(){return child;}
	public void setChild(DLBNode newChild){ child = newChild;}

}// DLBNode CLASS