import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class homework {

	public static void main(String args[]) throws IOException{
		FileReader fr=new FileReader("input.txt");
		BufferedReader br=new BufferedReader(fr);
		String line1=Files.readAllLines(Paths.get("input.txt")).get(0);
		int qn=Integer.parseInt(line1.trim());
		System.out.println("----Queries----");
		LinkedList<String> myQueries=new LinkedList<String>();
		for(int i=1;i<=1+qn-1;i++){
			
			String queryline=Files.readAllLines(Paths.get("input.txt")).get(i);
			System.out.println(queryline);
			myQueries.add(queryline);
			
		}
		
		String line2=Files.readAllLines(Paths.get("input.txt")).get(qn+1);
		//System.out.println(line2);
		int kn=Integer.parseInt(line2);
		System.out.println("----KB----");
		LinkedList<String> KB=new LinkedList<String>();
		for(int i=qn+2;i<=qn+kn+1;i++){
			
			String queryline=Files.readAllLines(Paths.get("input.txt")).get(i);
			KB.add(queryline);
			System.out.println(queryline);
		}
	
		System.out.println("----Infix To Prefix----");
		infixToPrefixAlt ip=new infixToPrefixAlt();
		LinkedList<String> returnFrominfToPreAlt= ip.callInfixToPrefix(KB);
		for (String s : returnFrominfToPreAlt) {
			System.out.println(s);
		}
		
		System.out.println("----CNF conversion----");
		CnfAlt ca=new CnfAlt();
		LinkedList<Object> returnCnfAlt=CnfAlt.callCNF(returnFrominfToPreAlt);
		for (Object object : returnCnfAlt) {
			System.out.println(object);
		}
		
		System.out.println("----Prefix to Infix----");
		PrefixToInfix p=new PrefixToInfix();
		LinkedList<String> returnFromPreToIn=p.receiveFromCnf(returnCnfAlt);
		for (String string : returnFromPreToIn) {
			System.out.println(string);
		}
		
		createKB finalip=new createKB();
		LinkedList<String> imSoDone=finalip.solveR(returnFromPreToIn, myQueries);
		
		System.out.println(imSoDone);
		
		try {

			//String content = "This is the content to write into file";

			File file = new File("output.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			//bw.write(content);
			for (String string : imSoDone) {
				bw.write(string);
				bw.write("\n");
			}
			
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//String s="[or, [or, [not, orange(x,y)], [not, b]], c]";
		
		//s=s.replaceAll("\\bor\\b", "|");
		//s=s.replaceAll("\\band\\b", "&");
		//s=s.replaceAll("\\bnot\\b", "~");
		
		//String blah=p.PreToInf(s);
		//System.out.println(blah);
		
		
		//String pass="['implies',['or','A(x)','B(x)'],'C(x)']";
		//LinkedList<Object> myParse=parser(pass);
		//System.out.println(myParse);
	}
	
	
}
