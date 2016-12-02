//create KB final

import java.awt.List;
import java.awt.SecondaryLoop;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;



public class createKB {

	static ArrayList<myNode> myNodeList=new ArrayList<myNode>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String returnIsDataClause=isDataClause("x");
		//System.out.println(returnIsDataClause);
		
		LinkedList<String> inputs=new LinkedList<String>();
		 
		 
		 inputs.add("~A(x) | H(x)");
		 //inputs.add("~D(x,y) | ~H(y) | ~H(z)");  //note ~H(z) is EXTRAAAAA!!!!
		 inputs.add("~D(x,y) | ~H(y) ");
		 inputs.add("~B(x,y) | ~C(x,y) | A(x)");
		 inputs.add("B(John,Alice)");
		 inputs.add("B(John,Bob)");
		 inputs.add("~D(x,y) | ~Q(y) | C(x,y)");
		 inputs.add("D(John,Alice)");
		 inputs.add("Q(Bob)");
		 inputs.add("D(John,Bob)");
		 
		 //inputs.add("~A(x) | C(x) & ~B(x) | C(x)");   //checking and
		 
		 inputs.add("~F(x) | G(x)");
		 inputs.add("~G(x) | H(x)");
		 inputs.add("~H(x) | F(x)");
		 inputs.add("~R(x) | H(x)");
		 inputs.add("R(Tom)");
		 
		
		 
		 createKB ckb= new createKB();
		 LinkedList<String> a=ckb.splitAND(inputs);  //Split clauses in case there is &
		
		 
		 for(int i=0;i<a.size();i++){
			 
			 myNode inputClause=new myNode(a.get(i));
			 myNodeList.add(inputClause);
			 
		 }
		 System.out.println(myNodeList.size());
		 
		 ArrayList<myNode> myNodeListQuery=new ArrayList<myNode>();
		 
		 //Collections.copy(myNodeListQuery, myNodeList);	//Copy to new KB for each query
		 
		 myNodeListQuery= copyKB(myNodeList);  //calling  COPYKB 
		 
		
		 String q="F(Bob)",query="";
		 
		 if(q.charAt(0)=='~'){
			 query=q.substring(1, q.length());
			 System.out.println(query);
		 }
		 else{
			 query="~"+q;
			 System.out.println(query);
		 }
		 
		 /*--- solve Query ---*/
		 //String queryAns= solveQuery(query, myNodeListQuery);
		 ArrayList<String> Q= new ArrayList<String>() ;
		 Q.add(query);
		 String queryA=solveQuery(Q,myNodeListQuery,0);
		 System.out.println(queryA);
		 
		 /*---- If you want to check access --- */
		 //myNode inputClause=new myNode("~Ancestor(x) | Human(x) | Ancestor(y)");
		 //myNodeList.add(inputClause); 
		 //System.out.println(myNodeList.get(myNodeList.size()-1).clause.get("Ancestor"));
		
	}
	
	public static LinkedList<String> solveR(LinkedList<String> inputs1, LinkedList<String> queries){
		
		LinkedList<String> returnToHome= new LinkedList<String>();
		createKB ckb= new createKB();
		LinkedList<String> a=ckb.splitAND(inputs1);  //Split clauses in case there is &
		for(int i=0;i<a.size();i++){
			 
			 myNode inputClause=new myNode(a.get(i));
			 myNodeList.add(inputClause);
			 
		 }
		
		
		for (String q : queries) {
			
			 
			 
			 
			 ArrayList<myNode> myNodeListQuery=new ArrayList<myNode>();
			 myNodeListQuery= copyKB(myNodeList);
			 
			 String query="";
			 
			 if(q.charAt(0)=='~'){
				 query=q.substring(1, q.length());
				 System.out.println(query);
			 }
			 else{
				 query="~"+q;
				 System.out.println(query);
			 }
			 System.out.println("*--- solve Query ---*");
			 //String queryAns= solveQuery(query, myNodeListQuery);
			 ArrayList<String> Q= new ArrayList<String>() ;
			 Q.add(query);
			 String queryA=solveQuery(Q,myNodeListQuery,0);
			 System.out.println(queryA);
			 returnToHome.add(queryA);
			
		}
		
		
		return returnToHome;
	}

	public LinkedList<String> splitAND(LinkedList<String> input){	
		
		LinkedList splitANDAns= new LinkedList<String>();
		
		for (int i = 0; i < input.size(); i++) {
			
			String p=input.get(i);
			if(p.contains("&")){
		
				String[] splitp=p.split("&");
				for (int j = 0; j < splitp.length; j++) {
					splitANDAns.add(splitp[j].trim());
				}
			}
			else{
				splitANDAns.add(input.get(i));
			}
			
		}
		for (int i = 0; i < splitANDAns.size(); i++) {
			System.out.println(splitANDAns.get(i));
		}
		
		return splitANDAns;
	
	
	}
	
	public static String solveQuery(ArrayList<String> QU, ArrayList<myNode> kbNodeList,int depth){
		
		if(depth>1000){
			return "FALSE";
		}
		
		for (int k = 0; k < QU.size(); k++) {
			
			String query=QU.get(k);
			//System.out.println(query);
			//System.out.println(kbNodeList.size());
			int g=0;
			String searchPredicate="",stringOfConstants="";
			//char[] queryChar=query.toCharArray();
			while(query.charAt(g)!='('){
				searchPredicate+= Character.toString(query.charAt(g));
				g++;
			}
			g++;
			while(query.charAt(g)!=')'){
				
				stringOfConstants+= Character.toString(query.charAt(g));
				g++;
			}
			System.out.println("Constant: "+ stringOfConstants);
			System.out.println("searchPredicate: "+searchPredicate);
			String searchPredicateOpp="";
			if( searchPredicate.charAt(0)=='~'){
				searchPredicateOpp=searchPredicate.substring(1, searchPredicate.length());
			}
			else{
				 searchPredicateOpp="~"+searchPredicate;
			}
			
			System.out.println("searchPredicateOpp "+searchPredicateOpp);
			
			for (int i = 0; i < kbNodeList.size(); i++) {
				String c="";
				if(kbNodeList.get(i).clause.containsKey(searchPredicateOpp)){
					
					ArrayList compare=kbNodeList.get(i).clause.get(searchPredicateOpp);
					if(compare.size()>1){
						System.out.println("Compare size greater than one");
						//needs to be handled
						for (int j = 0; j < compare.size(); j++) {
							System.out.println(compare.get(j).toString());
						}
						 c=compare.get(0).toString();
					}
					else{
							
						if(compare.isEmpty()){
							//return "TRUE";
							continue;
						}
						 c=compare.get(0).toString();
					}
					
				}
				if((!c.equals("")) && c.charAt(0)==searchPredicateOpp.charAt(0)){
					System.out.println("Matching predicate found "+ c);
					String mpAns=findArgs(c);
					//get args
					System.out.println("Clauses to further match");
					
					Map<String, ArrayList> subClauses = new HashMap<String, ArrayList>();
					subClauses=kbNodeList.get(i).clause;
					for (Map.Entry<String, ArrayList> entry : subClauses.entrySet())
					{
						ArrayList<String> sendToUnify=new ArrayList<String>();
						
						for (int j = 0; j < entry.getValue().size(); j++) {
							
							//if there are more than 1 values for a particular key
							System.out.println(entry.getValue().get(j));  
							sendToUnify.add(entry.getValue().get(j).toString());
						}
					   
					}
					
					if(QU.size()>1){
						
						for (int j = 0; j < QU.size(); j++) {
							
							String checkQU=QU.get(j);
							
							
							boolean check=checkQU.contains(searchPredicate);
							
							System.out.println("Boolean check "+ check);
							if(!(QU.get(j).toString().contains(searchPredicate))){
							
								int h=0;
								String pkey="";
								//char[] queryChar=query.toCharArray();
								while(QU.get(j).charAt(h)!='('){
									pkey+= Character.toString(checkQU.charAt(h));
									h++;
								}
								if(subClauses.containsKey(pkey)){
									
									subClauses.get(pkey).add(QU.get(j));
								}
								else{
									subClauses.putIfAbsent(pkey, new ArrayList<String>());
									subClauses.get(pkey).add(QU.get(j));
								}
								
							}
							
							
						}
						
					}
					//check if Unification is possible
					
					System.out.println("Send to unify from here?");
					Map<String,ArrayList> recursiveQ=unify(subClauses,stringOfConstants,c);
					
					String argsOfMatchingPred=findArgs(c);
					
					
					
					if(recursiveQ.isEmpty()){
						
						
						/*if(isDataClause(argsOfMatchingPred).equals("TRUE")){
							return "TRUE";
						}*/
							
						//return "FALSE";
						continue;
					}
					else{
						
						if(recursiveQ.containsKey(searchPredicateOpp)){
							
							ArrayList o=recursiveQ.get(searchPredicateOpp);
							for (int ke = 0; ke < o.size(); ke++) {
								
								if(o.get(ke).toString().contains(mpAns)){
									//o.remove(k);
									recursiveQ.get(searchPredicateOpp).remove(ke);
								}
								
							}
						}
						if(recursiveQ.size()==1){
							
							//code taken from unify
							
							
							if(recursiveQ.isEmpty()){
								System.out.println("Here!!!");
								continue;
							}
								//return "TRUE";
								
							
							else
								return "TRUE";
						}	
						System.out.println("RecursiveQ "+recursiveQ);
						ArrayList<String> send=new ArrayList<String>();
						for (Map.Entry<String, ArrayList> r : recursiveQ.entrySet()) {
							
							for(int j = 0; j < r.getValue().size(); j++){
								
								/* ORIGINAL CODE
								 * if(!(r.getKey().equals(searchPredicateOpp) && r.getValue().get(j).toString().contains(stringOfConstants))){
								 
									send.add(r.getValue().get(j).toString());
								}*/
								if(!(r.getKey().equals(searchPredicateOpp) && r.getValue().get(j).toString().contains(stringOfConstants))){
									 
									send.add(r.getValue().get(j).toString());
								}
								//check which predicate is already satisfied and don't add those
								// get each value to be added to ArrayList "send"
								
								
							}
							
						}
						//System.out.println(send);
						 ArrayList<myNode> kbNodeListCopy=new ArrayList<myNode>(kbNodeList);
						//Collections.copy(kbNodeListCopy, kbNodeList);
						 kbNodeListCopy=copyKB(kbNodeList); 
						
						String recCreateKb=solveQuery(send, kbNodeListCopy,++depth);
						if(depth>1000){
							return "FALSE";
						}
						if(recCreateKb.equals("FALSE")){
							continue;
							//return "FALSE";
						}
						else{
							return "TRUE";
						}
					}
					
				}
			}
		}
		
		
		
		return "FALSE";
	}
	
	
	public static Map<String,ArrayList>  unify(Map<String,ArrayList> hash, String constants, String matchingPredicate){
		
		Map<String,ArrayList> returnFromUnify= new HashMap<String, ArrayList>();
		System.out.println("------UNIFY-----");
		System.out.println("Hash: "+hash);
		System.out.println("Constants: "+constants);
		System.out.println("Matching predicate: "+matchingPredicate);
		char c=matchingPredicate.charAt(0);
			
			int g=0;
			String searchPredicate="",matchingPredicateVariables="";
			//char[] queryChar=query.toCharArray();
			while(matchingPredicate.charAt(g)!='('){
				searchPredicate+= Character.toString(matchingPredicate.charAt(g));
				g++;
			}
			g++;
			while(matchingPredicate.charAt(g)!=')'){
				
				matchingPredicateVariables+= Character.toString(matchingPredicate.charAt(g));
				g++;
			}
			System.out.println("matching Predicate Variables: "+ matchingPredicateVariables);
			System.out.println("searchPredicate: "+searchPredicate);
			String[] mpvArray= matchingPredicateVariables.split(",");  
			String[] constantsArray=constants.split(",");
			
			if(isDataClause(matchingPredicateVariables).equals("TRUE")){
				//if MPV is a data clause
				
				if(isDataClause(constants).equals("FALSE")){
					//if constants is not a data clause
					//Part B
					
					for (int i = 0; i < constantsArray.length; i++) {
						
						String record="",replaceWith="",srs="";
						if(Character.isLowerCase(constantsArray[i].charAt(0)) &&  (!Character.isLowerCase(mpvArray[i].charAt(0)))){
							
							record=constantsArray[i];
							replaceWith=mpvArray[i];
							for (Map.Entry<String,ArrayList> each : hash.entrySet()) {
								
								for(int j = 0; j < each.getValue().size(); j++){
									
									String s=each.getValue().get(j).toString();
									String args=findArgs(s);
									String[] argsArray=args.split(",");
									for (String string : argsArray) {
										
										if(string.contains(constantsArray[i].charAt(0)+"")){
											
											//each.getValue().remove(j);
											String getO=each.getValue().remove(j).toString();
											srs=getO.replaceAll("\\b"+record+"\\b", replaceWith);
											each.getValue().add(srs);
										}
									}
									
								}
							}
							
						}
						else if((!Character.isLowerCase(constantsArray[i].charAt(0))) &&  (!Character.isLowerCase(mpvArray[i].charAt(0)))){
							
							if(!(constantsArray[i].equals(mpvArray[i]))){
								return Collections.EMPTY_MAP;
							}
						}
					}
				}
				else if(isDataClause(constants).equals("TRUE")){
					
					for (int i = 0; i < constantsArray.length; i++) {
						if((!Character.isLowerCase(constantsArray[i].charAt(0))) &&  (!Character.isLowerCase(mpvArray[i].charAt(0)))){
							if(!(constantsArray[i].equals(mpvArray[i]))){
								return Collections.EMPTY_MAP;
							}
						}
					}
					
				}
				
			}
			
			else{
				if(isDataClause(matchingPredicateVariables).equals("FALSE")){
					//matching predicate contains variables
					
					//CHECK THIS CONDITION
					if(isDataClause(constants).equals("TRUE")){
						
						for (int i = 0; i < constantsArray.length; i++) {
							
							String record="",replaceWith="",srs="";
							if(!(Character.isLowerCase(constantsArray[i].charAt(0))) &&  (Character.isLowerCase(mpvArray[i].charAt(0)))){
								
								record=mpvArray[i];
								replaceWith=constantsArray[i];
								for (Map.Entry<String,ArrayList> each : hash.entrySet()) {
									
									for(int j = 0; j < each.getValue().size(); j++){
										
										String s=each.getValue().get(j).toString();
										String args=findArgs(s);
										String[] argsArray=args.split(",");
										for (String string : argsArray) {
											
											if(string.contains(mpvArray[i].charAt(0)+"")){
												
												//each.getValue().remove(j);
												String getO=each.getValue().remove(j).toString();
												srs=getO.replaceAll("\\b"+record+"\\b", replaceWith);
												each.getValue().add(srs);
											}
										}
										
									}
								}
								
							}
							
						}
					}
					
					else if(isDataClause(constants).equals("FALSE")){
						
						for (int i = 0; i < constantsArray.length; i++) {
							
							String record="",replaceWith="",srs="";
							if(Character.isLowerCase(constantsArray[i].charAt(0)) && (!Character.isLowerCase(mpvArray[i].charAt(0)))){
								
								record=constantsArray[i];
								replaceWith=mpvArray[i];
								for (Map.Entry<String,ArrayList> each : hash.entrySet()) {
									
									for(int j = 0; j < each.getValue().size(); j++){
										
										String s=each.getValue().get(j).toString();
										String args=findArgs(s);
										String[] argsArray=args.split(",");
										for (String string : argsArray) {
											
											if(string.contains(constantsArray[i].charAt(0)+"")){
												
												//each.getValue().remove(j);
												String getO=each.getValue().remove(j).toString();
												srs=getO.replaceAll("\\b"+record+"\\b", replaceWith);
												each.getValue().add(srs);
											}
										}
										
									}
								}
								
							}
							else if((!Character.isLowerCase(constantsArray[i].charAt(0))) && Character.isLowerCase(mpvArray[i].charAt(0))){
								
								record=mpvArray[i];
								replaceWith=constantsArray[i];
								for (Map.Entry<String,ArrayList> each : hash.entrySet()) {
									
									for(int j = 0; j < each.getValue().size(); j++){
										
										String s=each.getValue().get(j).toString();
										String args=findArgs(s);
										String[] argsArray=args.split(",");
										for (String string : argsArray) {
											
											if(string.contains(mpvArray[i].charAt(0)+"")){
												
												//each.getValue().remove(j);
												String getO=each.getValue().remove(j).toString();
												srs=getO.replaceAll("\\b"+record+"\\b", replaceWith);
												each.getValue().add(srs);
											}
										}
										
									}
								}
								
								
							}
							else if((!Character.isLowerCase(constantsArray[i].charAt(0))) &&  (!Character.isLowerCase(mpvArray[i].charAt(0)))){
								if(!(constantsArray[i].equals(mpvArray[i]))){
									return Collections.EMPTY_MAP;
								}
							}
							else if(Character.isLowerCase(constantsArray[i].charAt(0)) &&  Character.isLowerCase(mpvArray[i].charAt(0))){
								
								//if both are lowercase and variaables
								if(!(constantsArray[i].equals(mpvArray[i]))){
									
									continue;
								}
							}
						}
						
					}
					
				}
			}
			
		
		
		
		System.out.println("---- END OF UNIFY ----");
		for (Map.Entry<String,ArrayList> each : hash.entrySet()) {
			
			
			
		}
		/*if(hash.containsKey(searchPredicate)){
			
			ArrayList o=hash.get(searchPredicate);
			for (int k = 0; k < o.size(); k++) {
				
				if(o.get(k).toString().contains(matchingPredicate)){
					//o.remove(k);
					hash.get(searchPredicate).remove(k);
				}
				
			}
		}*/
		
		return hash;
	}
	
	/*OLD UNIFY
	 * public static Map<String,ArrayList>  unify(Map<String,ArrayList> hash, String constants, String matchingPredicate){
		
		Map<String,ArrayList> returnFromUnify= new HashMap<String, ArrayList>();
		System.out.println("------UNIFY-----");
		System.out.println(hash);
		System.out.println(constants);
		System.out.println(matchingPredicate);
		char c=matchingPredicate.charAt(0);
			
			int g=0;
			String searchPredicate="",matchingPredicateVariables="";
			//char[] queryChar=query.toCharArray();
			while(matchingPredicate.charAt(g)!='('){
				searchPredicate+= Character.toString(matchingPredicate.charAt(g));
				g++;
			}
			g++;
			while(matchingPredicate.charAt(g)!=')'){
				
				matchingPredicateVariables+= Character.toString(matchingPredicate.charAt(g));
				g++;
			}
			System.out.println("matching Predicate Variables: "+ matchingPredicateVariables);
			System.out.println("searchPredicate: "+searchPredicate);
			String[] mpvArray= matchingPredicateVariables.split(",");
			String[] constantsArray=constants.split(",");
			
			if(hash.size()==1){
				
				//IS DATA CLAUSE
				String returnIsDataClause=isDataClause(matchingPredicateVariables);
				
				
				if(returnIsDataClause.equals("TRUE")){
					
					for (int i = 0; i < constantsArray.length; i++) {
						
						if( (constantsArray[i].charAt(0)>='A'&&constantsArray[i].charAt(0)<='Z') && (mpvArray[i].charAt(0)>='A' && mpvArray[i].charAt(0)<='Z')){
							
							if(!(constantsArray[i].equals(mpvArray[i]))){
								//if any of the variables dont match
								return Collections.EMPTY_MAP;
							}
						}
						if((Character.isLowerCase(constantsArray[i].charAt(0)))){
							
							constants=constants.replaceAll("\\b"+constantsArray[i].charAt(0)+"\\b", mpvArray[i]);
							String p= searchPredicate+"("+constants+")";
							if(returnFromUnify.containsKey(searchPredicate)){
								
								returnFromUnify.get(searchPredicate).add(p);
							}
							else{
								returnFromUnify.putIfAbsent(searchPredicate, new ArrayList<String>());
								returnFromUnify.get(searchPredicate).add(p);
							}
							//Do something here!!
							//return returnFromUnify;
						}
						
					}
					return returnFromUnify;
				}
				
			}
			
			
			
			System.out.println(hash.size());
			for (Map.Entry<String, ArrayList> entry : hash.entrySet())
			{
				
				
				for (int j = 0; j < entry.getValue().size(); j++) {
					
					//if there are more than 1 values for a particular key
					System.out.println(entry.getValue().get(j));  
					String p=entry.getValue().get(j).toString();
					if(isDataClause(findArgs(p)).equals("TRUE")){
						
						for (int i = 0; i < constantsArray.length; i++) {
							
							if( (constantsArray[i].charAt(0)>='A'&&constantsArray[i].charAt(0)<='Z') && (mpvArray[i].charAt(0)>='A' && mpvArray[i].charAt(0)<='Z')){
								
								if(!(constantsArray[i].equals(mpvArray[i]))){
									//if any of the variables dont match
									return Collections.EMPTY_MAP;
								}
							}
							if((Character.isLowerCase(constantsArray[i].charAt(0)))){
								String constants1="";
								String recordReplace=""+constantsArray[i].charAt(0);
								constants1=constants.replaceAll("\\b"+constantsArray[i].charAt(0)+"\\b", mpvArray[i]);
								String pp= searchPredicate+"("+constants1+")";
								if(returnFromUnify.containsKey(searchPredicate)){
									
									returnFromUnify.get(searchPredicate).add(pp);
								}
								else{
									returnFromUnify.putIfAbsent(searchPredicate, new ArrayList<String>());
									returnFromUnify.get(searchPredicate).add(pp);
								}
								//Do something here!!
								//return returnFromUnify;
							}
							
						}
						
					}
					else{
						String pkey=entry.getKey();
						
						
						
						if(p.contains(matchingPredicateVariables)){
							p=p.replaceAll("\\b"+matchingPredicateVariables+"\\b", constants);
							System.out.println(p);
							if(returnFromUnify.containsKey(pkey)){
								
								returnFromUnify.get(pkey).add(p);
							}
							else{
								returnFromUnify.putIfAbsent(pkey, new ArrayList<String>());
								returnFromUnify.get(pkey).add(p);
							}
							
						}
						else{
							if(returnFromUnify.containsKey(pkey)){
								
								returnFromUnify.get(pkey).add(p);
							}
							else{
								returnFromUnify.putIfAbsent(pkey, new ArrayList<String>());
								returnFromUnify.get(pkey).add(p);
							}
						}
					}
					
				}
			   
			}
		
		
		
		return returnFromUnify; 
		
	}	*/
	
	
	public static ArrayList<myNode> copyKB(ArrayList<myNode> source){
		
		ArrayList<myNode> dest= new ArrayList<myNode>();
		
		
		for (int i = 0; i < source.size(); i++) {
			
			
			Map<String, ArrayList> destHM = new HashMap<String, ArrayList>();
			Map<String, ArrayList> temp = new HashMap<String, ArrayList>();
			temp=source.get(i).clause;
			
			for (Map.Entry<String, ArrayList> entry : temp.entrySet())
			{
				String key= entry.getKey();
				
				for (int j = 0; j < entry.getValue().size(); j++) {
					
					//if there are more than 1 values for a particular key
					ArrayList<String> copyList=new ArrayList<String>();
					Object c=new Object();
					c=entry.getValue().get(j);
					String sc=c.toString();
					//System.out.println("Object is "+c);
					copyList.add(sc);

					if(destHM.containsKey(key)){
						
						destHM.get(key).add(copyList);
					}
					else{
						destHM.putIfAbsent(key, copyList);
						
					}
					
					
				}
			   
			}
			//System.out.println(destHM);
			//System.out.println("New Hashmap");
			myNode putting=new myNode(destHM);
			dest.add(putting);
		}
		//System.out.println(source);
		//System.out.println(dest);
				
				
		return dest;
		
	}
	
	
	public static String isDataClause(String input){
		
		String[] inputSplit=input.split(",");
		char c;
		
		for (int i = 0; i < inputSplit.length; i++) {
			
			c=inputSplit[i].charAt(0);
			if(c>='A'&&c<='Z')
				continue;
			if(c>='a'&&c<='z'){
				return "FALSE";
			}
		}
		
		return "TRUE";
	}

public static String findArgs(Object o){
	
	String s=o.toString();
	int g=0;
	String searchPredicate="",stringOfConstants="";
	//char[] queryChar=query.toCharArray();
	while(s.charAt(g)!='('){
		searchPredicate+= Character.toString(s.charAt(g));
		g++;
	}
	g++;
	while(s.charAt(g)!=')'){
		
		stringOfConstants+= Character.toString(s.charAt(g));
		g++;
	}
	//System.out.println("Constant: "+ stringOfConstants);
	//System.out.println("searchPredicate: "+searchPredicate);
	
	return stringOfConstants;
}
	
}


	

class myNode{
	
	/* http://stackoverflow.com/questions/19541582/storing-and-retrieving-arraylist-values-from-hashmap */
	
	Map<String, ArrayList> clause = new HashMap<String, ArrayList>();
	ArrayList<String> parts=new ArrayList<String>();
	boolean isPartofOr=false;
	
	myNode(){
		
		
		
	}
	
	myNode(Map<String, ArrayList> insert){
		
		this.clause=insert;
		
		
		
	}
	
	myNode(String s){
		
		if(s.contains("|")){
			
			isPartofOr=true;
			String[] splitAtOr=s.split("\\|");
			for (int i = 0; i < splitAtOr.length; i++) {
				
				String predicate=splitAtOr[i].trim();
				
				//TO CREATE SAME KEY D FOR ~D(x,y) and D(x,y)
				/*if(predicate.charAt(0)=='~'){
					
					int g=1;
					String pkey="";
					while(predicate.charAt(g)!='('){
						
						 pkey= pkey +  Character.toString(predicate.charAt(g));
						 g++;
					}
					
					//System.out.println(pkey);
					
					if(clause.containsKey(pkey)){
						//ArrayList<String> temp=clause.get(pkey);
						//temp.add(predicate);
						//clause.put(pkey, temp);
						clause.get(pkey).add(predicate);
					}
					else{
						clause.putIfAbsent(pkey, new ArrayList<String>());
						clause.get(pkey).add(predicate);
					}
					
					
				} 
				else{
					
					int g=0;
					String pkey="";
					while(predicate.charAt(g)!='('){
						
						 pkey= pkey +  Character.toString(predicate.charAt(g));
						 g++;
					}
					//System.out.println(pkey);
					
					if(clause.containsKey(pkey)){
						//ArrayList<String> temp=clause.get(pkey);
						//temp.add(predicate);
						//clause.put(pkey, temp);
						clause.get(pkey).add(predicate);
					}
					else{
						clause.putIfAbsent(pkey, new ArrayList<String>());
						clause.get(pkey).add(predicate);
					}
				}*/
				
				//TO CREATE SEPARATE KEY D FOR ~D(x,y) and D(x,y)
				int g=0;
				String pkey="";
				while(predicate.charAt(g)!='('){
					
					 pkey= pkey +  Character.toString(predicate.charAt(g));
					 g++;
				}
				//System.out.println(pkey);
				
				if(clause.containsKey(pkey)){
					//ArrayList<String> temp=clause.get(pkey);
					//temp.add(predicate);
					//clause.put(pkey, temp);
					clause.get(pkey).add(predicate);
				}
				else{
					clause.putIfAbsent(pkey, new ArrayList<String>());
					clause.get(pkey).add(predicate);
				}
			}
		
		
		}
		else{
			
			isPartofOr=false;
			if(s.charAt(0)=='~'){
				
				int g=1;
				String pkey="";
				while(s.charAt(g)!='('){
					
					 pkey= pkey +  Character.toString(s.charAt(g));
					 g++;
				}
				
				//System.out.println(pkey);
				
				if(clause.containsKey(pkey)){
					//ArrayList<String> temp=clause.get(pkey);
					//temp.add(predicate);
					//clause.put(pkey, temp);
					clause.get(pkey).add(s);
				}
				else{
					clause.putIfAbsent(pkey, new ArrayList<String>());
					clause.get(pkey).add(s);
				}
				
				
			}
			else{
				
				int g=0;
				String pkey="";
				while(s.charAt(g)!='('){
					
					 pkey= pkey +  Character.toString(s.charAt(g));
					 g++;
				}
				//System.out.println(pkey);
				
				if(clause.containsKey(pkey)){
					//ArrayList<String> temp=clause.get(pkey);
					//temp.add(predicate);
					//clause.put(pkey, temp);
					clause.get(pkey).add(s);
				}
				else{
					clause.putIfAbsent(pkey, new ArrayList<String>());
					clause.get(pkey).add(s);
				}
			}
		}
		System.out.println(clause);
	}
	
}

