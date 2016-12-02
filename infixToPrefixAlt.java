//INFIX TO PREFIX TAKEN FROM HW3 SAMPLE FOLDER

import java.util.LinkedList;
import java.util.Stack;

public class infixToPrefixAlt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String a="((~(A(x) | B(x)) => C(x)))"; 
		String a="A(Bob,Alice)";
		a=bracketed(a);
		System.out.println(a);
		a=convert(a);
		System.out.println(a);
		a=a.replace("{", "(");
		a=a.replace("}", ")");
		System.out.println(a);
		
		
	}
	
	public static LinkedList<String> callInfixToPrefix(LinkedList<String> inputs){
		
		LinkedList<String> returnToCalling=new LinkedList<String>();
		
		for (String s : inputs) {
			String a=s;
			a=bracketed(a);
			//System.out.println(a);
			a=convert(a);
			//System.out.println(a);
			a=a.replace("{", "(");
			a=a.replace("}", ")");
			//System.out.println(a);
			returnToCalling.add(a);
		}
		
		return returnToCalling;
		
	}
	
	public static String bracketed(String s){
		
		char a[]=s.toCharArray();
		
		for (int i = 1; i < a.length-1; i++) {
			if(a[i]=='('){
				if(Character.isLetter(a[i-1])&&Character.isLetter(a[i+1])){
					a[i]='{';
					while(a[i]!=')'){
						i++;
					}
					a[i]='}';
					
				}
			}
		}
		
		
		return new String(a);
	}

	public static boolean precedenceCheck(char one,char two){
		// Returns true if 'op2' has higher or same precedence as 'op1',
	    // otherwise returns false.
		if (two == '(' || two == ')')
           return false;
		int oneone=0, twotwo=0;
		switch(one){
		
				case '(':
					oneone=5;
					break;
				case '~':
					oneone=4;
					break;
				case '&':
					oneone=3;
					break;
				case '|':
					oneone=2;	
					break;
				case '=':
					oneone=1;	
					break;
			
		
		}
		switch(two){
				
				case '(':
					twotwo=5;
					break;
				case '~':
					twotwo=4;
					break;
				case '&':
					twotwo=3;
					break;
				case '|':
					twotwo=2;
					break;
				case '=':
					twotwo=1;
					break;
					
				
				}
		
		if(oneone>twotwo){
			return false;
		}
		
		else 
			return true;
	}
	
	
	public static String convert(String s){
		
		char token[]=s.toCharArray();
		Boolean notflag=false,predicateIComma=false;
		
		Stack<Character> ops=new Stack();
		Stack<String> values =new Stack();
		
		for (int i = 0; i < token.length; i++) {
			
			if(token[i]==' ')
				continue;
			
			if(token[i]=='(')
				ops.push(token[i]);
			
			if (token[i] == ')')
            {
                while (ops.peek() != '(')
                  values.push(applyOp(values.pop(), values.pop(),ops.pop()));
                ops.pop();
            }
			
			if(token[i]=='&' || token[i]=='|' || token[i]=='=' ){
				
				while (!ops.empty() && precedenceCheck(token[i], ops.peek()))
	                  values.push(applyOp(values.pop(), values.pop(), ops.pop()));
	 
	                // Push current token to 'ops'.
	                ops.push(token[i]);
				
			}
			if(Character.isLetter(token[i])  || token[i]=='{' || token[i]=='}' || token[i]=='~'|| token[i]==','){
				StringBuffer sample= new StringBuffer();
				String returnFromcall= new String();
				StringBuffer sbuf=new StringBuffer();
				if(token[i]=='~' && i<s.length()){
					notflag=true;
					if(Character.isLetter(token[i+1])){
						sbuf.append("['not','");
						predicateIComma=true;
					}
					else{
						sbuf.append("['not',");
					}
					i++;
					if(token[i]=='('){
						int count=0;
						while(true){
							
							if(token[i]=='('){
								count++;
							}
							if(token[i]==')'){
								count--;
							}
							if(count!=0){
								sample.append(token[i]);
								i++;
							}
							if(count==0){
								returnFromcall= convert(sample.toString());
								break;
							}
						}
						
						
						
					}
					
					sbuf.append(returnFromcall);
					
					while (i<token.length && (Character.isLetter(token[i]) || token[i]==','  || token[i]=='~' || token[i]=='{' || token[i]=='}'))
						sbuf.append(token[i++]);
						if(i==token.length || token[i]==' ' || notflag==true)
							if(predicateIComma==true){
								sbuf.append("']");
							}
							else{
								sbuf.append("]");
							}
					
				}
				while (i < token.length && (Character.isLetter(token[i]) || token[i]==','  || token[i]=='{' || token[i]=='}'))
                    sbuf.append(token[i++]);
               
                values.push(sbuf.toString());
			}
			 
		}
		
		// Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
		outerloop:
		while (!ops.empty()  ){
			
			if(values.size()==1)
				break outerloop;
			
			values.push(applyOp( values.pop(), values.pop(),ops.pop()));
			while( !ops.isEmpty()){
				ops.pop();
			}
		}
            
		
		
		String returnS=values.pop();
		while(returnS.isEmpty()){
			returnS=values.pop();
		}
		
        return returnS;
		
	}
	
	public static String applyOp(String part2,String part1, char operator){
		
		if(operator=='&'){
			if(part1.charAt(0)=='[' && part1.charAt(part1.length()-1)==']' && part2.charAt(0)!='['){
				return "['and',"+part1+",'"+part2+"']";
			}
			if(part2.charAt(0)=='[' && part2.charAt(part2.length()-1)==']' && part1.charAt(0)!='['){
				return "['and','"+part1+"',"+part2+"]";
			}
			if(part2.charAt(0)=='[' && part2.charAt(part2.length()-1)==']' && part1.charAt(0)=='['){
				return "['and',"+part1+","+part2+"]";
			}
			return "['and','"+part1+"','"+part2+"']";
			
		}
		
		if(operator=='|'){
			if(part1.charAt(0)=='[' && part1.charAt(part1.length()-1)==']' && part2.charAt(0)!='['){
				return "['or',"+part1+",'"+part2+"']";
			}
			if(part2.charAt(0)=='[' && part2.charAt(part2.length()-1)==']' && part1.charAt(0)!='['){
				return "['or','"+part1+"',"+part2+"]";
			}
			if(part2.charAt(0)=='[' && part2.charAt(part2.length()-1)==']' && part1.charAt(0)=='['){
				return "['or',"+part1+","+part2+"]";
			}
			return "['or','"+part1+"','"+part2+"']";
		}
		
		if(operator=='='){
			if(part1.charAt(0)=='[' && part1.charAt(part1.length()-1)==']' && part2.charAt(0)!='['){
				return "['implies',"+part1+",'"+part2+"']";
			}
			if(part2.charAt(0)=='[' && part2.charAt(part2.length()-1)==']' && part1.charAt(0)!='['){
				return "['implies','"+part1+"',"+part2+"]";
			}
			if(part2.charAt(0)=='[' && part2.charAt(part2.length()-1)==']' && part1.charAt(0)=='['){
				return "['implies',"+part1+","+part2+"]";
			}
			return "['implies','"+part1+"','"+part2+"']";
		}
		
		return null;
	}
	
}
