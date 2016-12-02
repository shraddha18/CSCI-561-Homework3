



import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;

public class CnfAlt {

	public static void main(String args[]) throws Exception {
		// BufferedReader br = new BufferedReader(new InputStreamReader(new
		// FileInputStream("source")));
		// int n = Integer.parseInt(br.readLine());
		LinkedList<Object> as = new LinkedList<Object>();
		String input = "['implies','H(x)','F(x)']";
		
		LinkedList<String> inputs=new LinkedList<String>();
		
		/*
		inputs.add("['implies','A(x)','H(x)']");
		inputs.add("['implies','D(x,y)',['not','H(y)']]");
		inputs.add("['implies',['and','B(x,y)','C(x,y)'],'A(x)']");
		inputs.add("['implies',['and','D(x,y)','Q(y)'],'C(x,y)']");
		inputs.add("['implies','F(x)','G(x)']");
		inputs.add("['implies','G(x)','H(x)']");
		inputs.add("['implies','H(x)','F(x)']");
		inputs.add("['implies','R(x)','H(x)']");
		*/
		
		inputs.add("['implies',['or','A(x)','B(x)'],'C(x)']");
		
		inputs.add("['not','A(x)']");
		inputs.add("['not',['not',['not',['not','A(x)']]]]");
		
		//CnfAlt alt=new CnfAlt();
				/*-------------- FINAL ANSWER FROM CNF ------------------- */
				LinkedList<Object> finalAnswer=new LinkedList<Object>();
				finalAnswer=CnfAlt.callCNF(inputs);
				
				for (int i = 0; i < finalAnswer.size(); i++) {
					System.out.println(finalAnswer.get(i));
				}
		
				PrefixToInfix pti=new PrefixToInfix();
				pti.receiveFromCnf(finalAnswer);
				
				
		
		/* ----- ORIGINAL CODE ---- 		
		CnfAlt c = new CnfAlt();
		as = c.myParse(input);
		System.out.println(as);
		System.out.println(as.size());
		
		
		

		LinkedList<Object> result = c.parseImpl(as);
		System.out.println("After parseImpl:");
		System.out.println(result);
		LinkedList<Object> result1 = c.pNot(result);
		System.out.println("After pNot:");
		System.out.println(result1);
		//simplify
		LinkedList<Object> result2 = c.distPar(result1);
		System.out.println("After distPar:");
		System.out.println(result2);
		
		-------*/
				
		//parseCleanup
		/*
		 * LinkedList<Object> result3 = c.modDuplicates(result2);
		 * System.out.println("After modDuplicates:");
			System.out.println(result3);
		 */
		
		//modDuplicates

	}

	public static LinkedList<Object> callCNF(LinkedList<String> inputs){
		
		LinkedList<Object> callCnfAns=new LinkedList<Object>();
		LinkedList<Object> sendToMethods = new LinkedList<Object>();
		for(int i=0;i<inputs.size();i++){
			String sendInput=inputs.get(i);
			//System.out.println(sendInput);
			CnfAlt p=new CnfAlt();
			sendToMethods= p.myParse(sendInput);
			//System.out.println(sendToMethods);
			if(sendToMethods.isEmpty()){
				callCnfAns.add(sendInput);
				continue;
			}
			LinkedList<Object> sendToMethodsA1 = p.parseImpl(sendToMethods);
			if(sendToMethodsA1.isEmpty()){
				callCnfAns.add(sendInput);
				continue;
			}
			//System.out.println("After parseImpl:");
			//System.out.println(sendToMethodsA1);
			LinkedList<Object> sendToMethodsA2 = p.pNot(sendToMethodsA1);
			if(sendToMethodsA2.isEmpty()){
				callCnfAns.add(sendInput);
				continue;
			}
			//System.out.println("After pNot:");
			//System.out.println(sendToMethodsA2);
			//simplify
			LinkedList<Object> sendToMethodsA3 = p.distPar(sendToMethodsA2);
			if(sendToMethodsA3.isEmpty()){
				callCnfAns.add(sendInput);
				continue;
			}
			//System.out.println("After distPar:");
			//System.out.println(sendToMethodsA3);
			callCnfAns.add(sendToMethodsA3);
			
			
		}
		
		
		
		
		return callCnfAns;
	}
	
	
	public static LinkedList<Object> distPar(LinkedList<Object> statement) {
		// TODO Auto-generated method stub
		if (checkIfDC(statement)) {
			statement = distOR(statement);
		}
		for (int i = 1; i < statement.size(); i++) {
			if (statement.get(i).getClass() != (new String().getClass())) {
				LinkedList<Object> f = (LinkedList<Object>) statement.get(i);
				if (f.size() > 1) {
					statement.set(i, distPar(f));

				}
			}
		}
		if (checkIfDC(statement)) {

			statement = distOR(statement);
		}
		return statement; // needs simplify
	}
	
	
	//Redundant
		public LinkedList<Object> eliDuplicate(LinkedList<Object> statement){
			
			if(statement.size()>2){
				LinkedList<Object> result=new LinkedList<Object>();
				result.add(statement.getFirst());
				result.add(statement.get(1));
				
				for(int i = 2; i < statement.size(); i++)
				{
					
					if(!(inResult(result,(LinkedList<Object>)statement.get(i)))){  //check typecasting
						result.add(statement.get(i));
					}
					
						
				}
				if(result.size()==2){
					result.add(result.get(1));
				}
				return result;
			}
			else
				return statement;
			
		}

	public static LinkedList<Object> distOR(LinkedList<Object> statement) {
		// TODO Auto-generated method stub
		LinkedList<Object> result = new LinkedList<Object>();
		LinkedList<Object> f = new LinkedList<Object>();
		LinkedList<Object> effOne = new LinkedList<Object>();
		result.add("and");
		if (statement.get(1).getClass() != (new String().getClass())) {
			f=(LinkedList<Object>)statement.get(1);
		}
		else
		{
			f.add(statement.get(1));
		}
		if (statement.get(2).getClass() != (new String().getClass())) {
			effOne=(LinkedList<Object>)statement.get(2);
		}
		else
		{
			effOne.add(statement.get(2));
		}
			
			if (effOne.getFirst().equals("and") && f.getFirst().equals("and")  ) {
				LinkedList<Object> effThree = new LinkedList<Object>();
				effThree.add("or");
				effThree.add(f.get(1));
				effThree.add(effOne.get(1));
				result.add(distPar(effThree));
				LinkedList<Object> effFour = new LinkedList<Object>();
				effFour.add("or");
				effFour.add(f.get(1));
				effFour.add(effOne.get(2));
				result.add(distPar(effFour));
				LinkedList<Object> effFive = new LinkedList<Object>();
				effFive.add("or");
				effFive.add(f.get(2));
				effFive.add(effOne.get(1));
				result.add(distPar(effFive));
				LinkedList<Object> effSix = new LinkedList<Object>();
				effSix.add("or");
				effSix.add(f.get(2));
				effSix.add(effOne.get(2));
				result.add(distPar(effSix));

			} else { //start else
				if (f.getFirst().equals("and")) {
					if (effOne.size() > 2) {
						if (checkIfDC(effOne)) {
							statement.set(2, distPar(effOne));
							LinkedList<Object> effThree = new LinkedList<Object>();
							effThree.add("or");
							effThree.add(f.get(1));
							effThree.add(effOne.get(1));
							result.add(distPar(effThree));
							LinkedList<Object> effFour = new LinkedList<Object>();
							effFour.add("or");
							effFour.add(f.get(1));
							effFour.add(effOne.get(2));
							result.add(distPar(effFour));
							LinkedList<Object> effFive = new LinkedList<Object>();
							effFive.add("or");
							effFive.add(f.get(2));
							effFive.add(effOne.get(1));
							result.add(distPar(effFive));
							LinkedList<Object> effSix = new LinkedList<Object>();
							effSix.add("or");
							effSix.add(f.get(2));
							effSix.add(effOne.get(2));
							result.add(distPar(effSix));
						} else {
							LinkedList<Object> effThree = new LinkedList<Object>();
							effThree.add("or");
							effThree.add(f.get(1));
							effThree.add(effOne);
							result.add(distPar(effThree));
							LinkedList<Object> effFive = new LinkedList<Object>();
							effFive.add("or");
							effFive.add(f.get(2));
							effFive.add(effOne);
							result.add(distPar(effFive));
						}
					} else {
						LinkedList<Object> effThree = new LinkedList<Object>();
						effThree.add("or");
						effThree.add(f.get(1));
						effThree.add(effOne);
						result.add(distPar(effThree));
						LinkedList<Object> effFive = new LinkedList<Object>();
						effFive.add("or");
						effFive.add(f.get(2));
						effFive.add(effOne);
						result.add(distPar(effFive));
					}

				} else {
					if (f.size() > 2) {
						if (checkIfDC(f)) {
							statement.set(1, distPar(f));
							LinkedList<Object> effThree = new LinkedList<Object>();
							effThree.add("or");
							effThree.add(f.get(1));
							effThree.add(effOne.get(1));
							result.add(distPar(effThree));
							LinkedList<Object> effFour = new LinkedList<Object>();
							effFour.add("or");
							effFour.add(f.get(1));
							effFour.add(effOne.get(2));
							result.add(distPar(effFour));
							LinkedList<Object> effFive = new LinkedList<Object>();
							effFive.add("or");
							effFive.add(f.get(2));
							effFive.add(effOne.get(1));
							result.add(distPar(effFive));
							LinkedList<Object> effSix = new LinkedList<Object>();
							effSix.add("or");
							effSix.add(f.get(2));
							effSix.add(effOne.get(2));
							result.add(distPar(effSix));
						} else {
							LinkedList<Object> effThree = new LinkedList<Object>();
							effThree.add("or");
							effThree.add(f);
							effThree.add(effOne.get(1));
							result.add(distPar(effThree));
							LinkedList<Object> effFive = new LinkedList<Object>();
							effFive.add("or");
							effFive.add(f);
							effFive.add(effOne.get(2));
							result.add(distPar(effFive));
						}
					} else {
						LinkedList<Object> effThree = new LinkedList<Object>();
						effThree.add("or");
						effThree.add(f);
						effThree.add(effOne.get(1));
						result.add(distPar(effThree));
						LinkedList<Object> effFive = new LinkedList<Object>();
						effFive.add("or");
						effFive.add(f);
						effFive.add(effOne.get(2));
						result.add(distPar(effFive));
					}
				}
			} //end else
		return result;
	}

	public static LinkedList<Object> pNot(LinkedList<Object> statement) {
		// TODO Auto-generated method stub
		if (checkIfNPCand(statement)) {
			statement = moveInNot(statement);
		}
		for (int i = 1; i < statement.size(); i++) {
			if (statement.get(i).getClass() != (new String().getClass())) {
				LinkedList<Object> f = (LinkedList<Object>) statement.get(i);
				if (f.size() > 1) {
					statement.set(i, pNot(f));
				}
			}
		}
		if (checkIfNPCand(statement)) {
			statement = moveInNot(statement);
		}
		return statement;
	}

	
	public LinkedList<Object> parsestatement(LinkedList<Object> statement) {
		if (statement.size() == 0)
			return statement;
		if (statement.size() == 1)
			return statement;

		return statement;
	}
	
	public static LinkedList<Object> moveInNot(LinkedList<Object> statement) {
		// TODO Auto-generated method stub
		LinkedList<Object> result = new LinkedList<Object>();
		if (statement.get(1).getClass() != (new String().getClass())) {
			LinkedList<Object> f = (LinkedList<Object>) statement.get(1);
			if (f.getFirst().equals("or"))
				result.add("and");
			else if (f.getFirst().equals("and"))
				result.add("or");
			else if (f.getFirst().equals("not")) {
				LinkedList<Object> pnot=new LinkedList<Object>();
						pnot.add(f.get(1));
				return pnot;
			}

			for (int i = 1; i < f.size(); i++) {
				if (f.get(i).getClass()!=(new String().getClass())) {
					LinkedList<Object> effThree = new LinkedList<Object>();
					effThree.add("not");
					effThree.add(f.get(i));
					result.add(moveInNot(effThree));
				} else {
					LinkedList<Object> effThree = new LinkedList<Object>();
					effThree.add("not");
					effThree.add(f.get(i));
					result.add(effThree);
				}
			}
		}
		return result;
	}

	
	//Redundant function
		public LinkedList<Object> modDuplicates(LinkedList<Object> statement){
			
			statement=eliDuplicate(statement);
			for(int i = 1; i < statement.size(); i++)
			{
				if(statement.get(i).getClass() != (new String().getClass()))
				{
					LinkedList<Object> f = (LinkedList<Object>)statement.get(i);
					if(f.size() > 1)
					{
						statement.set(i,modDuplicates((LinkedList<Object>)statement.get(i)));
					}
					
				}
				
					
			}
			statement=eliDuplicate(statement);
			return statement;
			
		}
	
	
	

	public LinkedList<Object> parseImpl(LinkedList<Object> statement) {
		if (checkIfImpl(statement)) {
			statement = eliminateImpl(statement);
		}
		// System.out.println(statement);
		for (int i = 1; i < statement.size(); i++) {
			if (statement.get(i).getClass() != (new String().getClass())) {
				LinkedList<Object> f = (LinkedList<Object>) statement.get(i);
				if (f.size() > 1) {
					statement.set(i, parseImpl(f));
				}

			}

		}
		if (checkIfImpl(statement)) {
			statement = eliminateImpl(statement);
		}
		return statement;
	}

	public static boolean checkIfNPCand(LinkedList<Object> statement) {

		if (statement.getFirst().equals("not") && statement.size() == 2 && statement.getFirst().getClass() == (new String().getClass()) 
				&& statement.get(1).getClass() != (new String().getClass())) {
			return true;
		}
		return false;
	}	
	
	//Redundant
		public boolean inResult(LinkedList<Object> result,LinkedList<Object> statement){
			
			for(int i=1; i<result.size();i++){
				if(isEqual((LinkedList<Object>)result.get(i),statement)){
					return true;
				}
					
			}
			return false;
		}
		public boolean isEqual(LinkedList<Object> result,LinkedList<Object> statement){
			
			
			return true;
		}

		
		
	
	public LinkedList<Object> eliminateImpl(LinkedList<Object> statement) {
		LinkedList<Object> result = new LinkedList<Object>();
		result.add("or");
		LinkedList<Object> mid = new LinkedList<Object>();
		mid.add("not");
		mid.add(statement.get(1));
		result.add(mid);
		result.add(statement.get(2));
		return result;
	}

	public boolean checkIfImpl(LinkedList<Object> statement) {
		if (statement.getFirst().equals("implies")
				&& statement.size() == 3 && statement.getFirst().getClass() == (new String().getClass())  ) {
			return true;
		} else {
			return false;
		}

	}

	public static LinkedList<Object> myParse(String s) {
		
		LinkedList<Object> pas = new LinkedList<Object>();
		int i = 1;
		while (i <= s.length() - 2) {
			if (s.charAt(i) == '\'') {
				
				int j = i + 1;
				while (j < s.length() && s.charAt(j) != '\'')
					j++;
				pas.add(s.substring(i + 1, j));
				i = j + 1;

			} else if (s.charAt(i) == '[') {
				int j = i;
				int count = 0;
				while (s.charAt(j) != ']') {
					if (s.charAt(j) == '[')
						count++;
					j++;
				}
				j = i;
				while (count != 0) {
					if (s.charAt(j) == ']')
						count--;
					j++;
				}
				pas.add(myParse(s.substring(i, j)));
				i = j;
			}
			i++;

		}
		
		return pas;
	}


	public static boolean checkIfDC(LinkedList<Object> statement) {
		if (statement.getFirst().getClass() == (new String().getClass()) && statement.getFirst().equals("or")) {
			for (int i = 1; i < statement.size(); i++) {
				if (statement.get(i).getClass() != (new String().getClass())) {
					LinkedList<Object> f = (LinkedList<Object>) statement.get(i);
					if (f.size() > 1) {
						if (f.getFirst().equals("and")  && f.getFirst().getClass() == (new String().getClass()) ) {
							return true;
						}
					}

				}
			}
		}
		return false;
	}
	
	
	
	//MODIFIED METHOD NOT YET USED

	
	
	
	
	
}
