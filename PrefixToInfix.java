//MY CODE FOR PREFIX TO INFIX
import java.util.LinkedList;
import java.util.Stack;

public class PrefixToInfix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		String s="[or, [not, A(x)], H(x)]";
		
		s=s.replaceAll("\\bor\\b", "|");
		s=s.replaceAll("\\band\\b", "&");
		s=s.replaceAll("\\bnot\\b", "~");
		System.out.println(s);
		
		String answer=PreToInf(s);
		System.out.println(answer);
		*/
		
	}
	
	public static LinkedList<String>  receiveFromCnf(LinkedList<Object> inputs){
		
		LinkedList<String> receiveFromCnfAnswer = new LinkedList<String>();
		for (int i = 0; i < inputs.size(); i++) {
			
			String ss=inputs.get(i).toString();
			ss=ss.replaceAll("\\bor\\b", "|");
			ss=ss.replaceAll("\\band\\b", "&");
			ss=ss.replaceAll("\\bnot\\b", "~");
			//System.out.println(ss);
			
			String answer=PreToInf(ss);
			if(answer.contains("~~")){
				//System.out.println("True");
				answer=answer.replace("~~", "");
			}
			//System.out.println(answer);
			receiveFromCnfAnswer.add(answer);
			
		}
		
		//System.out.println(receiveFromCnfAnswer);
		
		return receiveFromCnfAnswer;
	}
	

	public static String PreToInf(String s){
		
		String[] splits=s.split(" ");
		
		Stack st=new Stack();
		
		for (int i = 0; i < splits.length; i++) {
			
			//char x=s.charAt(s.length()-1-i);
			String x=splits[splits.length-1-i];
			
			if(x.contains("|") || x.contains("&") || x.contains("~") ){
				
				//System.out.println("Operator found "+ x);
				if(x.contains("~")){
					
					String op1=st.pop().toString();
					//op1=op1.replaceAll("]", "");
					String putback= "~"+op1 + "";
					st.push(putback);
				}
				if(x.contains("|")||x.contains("&")){
					
					String op1=st.pop().toString();
					//op1=op1.replaceAll("]", "");
					String op2=st.pop().toString();
					//op2=op2.replaceAll("]", "");
					if(x.contains("|")){
						String putback= "" + op1 + " " + "|" + " " + op2 + "";
						st.push(putback);
					}
					if(x.contains("&")){
						String putback= "" + op1 + " " + "&" + " " + op2 + "";
						st.push(putback);
					}
				}
				
				
			}
			else{
				
				x=x.replace("]", "");
				x=x.replace("[", "");
				if(x.charAt(x.length()-1)==','){
					x=x.substring(0, x.length()-1);
				}
				st.push(x);
			}
			
		}
		String a=st.pop().toString();
		
		return a;
	}
}
