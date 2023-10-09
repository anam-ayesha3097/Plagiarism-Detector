import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PlagiarismDetector{
	static PlagiarismDetector pd = new PlagiarismDetector();

	public static void main(String args[]) {

		String_Return return_string;
		try {
		
		return_string = pd.readFile(args[0],args[1]);
		
		//Preprocessing : Tokenizing StopWords Removal and Stemming
		return_string = pd.preprocessing(return_string.getF1(), return_string.getF2());
		
		float total_words = (return_string.getF1_arry().length + return_string.getF2_arry().length);
		
		double startTime = System.nanoTime();
		
		float lps_value_f1_f2 = pd.KMP(return_string.getF1_arry(), return_string.getF2_arry());
		
		double endTime = System.nanoTime();
		
		double totalExecutionTime_f1_f2 = ((endTime - startTime)) * Math.pow(10, -9);
		
		double totalExecutionTime_f2_f1 = 0.0;
		
		float lps_plag_rate_f2_f1 = -1;
//		System.out.println(return_string.getF1_arry().length);
//		System.out.println(return_string.getF2_arry().length);
		
		if(return_string.getF2_arry().length > return_string.getF1_arry().length) 
		{
			startTime = System.nanoTime();
		
			float lps_value_f2_f1 = pd.KMP(return_string.getF2_arry(), return_string.getF1_arry());
		
			endTime = System.nanoTime();
		
			totalExecutionTime_f2_f1 = ((endTime - startTime)) * Math.pow(10, -9);
		
			lps_plag_rate_f2_f1 = ((lps_value_f2_f1) / total_words) * 100;
		
		}
		
		float lps_plag_rate_f1_f2 = ((lps_value_f1_f2) / total_words) * 100;
		
//		System.out.println("LPS Plag rate F1 F2 "+lps_plag_rate_f1_f2);
//		System.out.println("LPS Plag rate F2 F1 "+lps_plag_rate_f2_f1);
//		System.out.println("LPS Execution Time F1 F2 "+totalExecutionTime_f1_f2);
//		System.out.println("LPS Execution Time F2 F1 "+totalExecutionTime_f2_f1);
		
		
		if((lps_plag_rate_f1_f2 > 15.0) && (lps_plag_rate_f2_f1 > 15.0))
			System.out.println("1");
		else if((lps_plag_rate_f1_f2 >= 15.0) && (lps_plag_rate_f2_f1 == -1.0))
			System.out.println("1");
		else
			System.out.println("0");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String_Return readFile(String f1_path, String f2_path) {
		
		BufferedReader br = null;
		String f1_lines = "", f2_lines = "";
		List<String> f1_read = new ArrayList<String>();
		List<String> f2_read = new ArrayList<String>();
		
		String_Return return_str = new String_Return();
		
		try {
//			System.out.println("File Paths \n" +f1_path);
//			System.out.println(f2_path);
			br = new BufferedReader(new FileReader(f1_path));
			//f1_lines = br.readLine();
			while((f1_lines = br.readLine()) !=null) 
				f1_read.add(f1_lines);
				
			f1_lines = "";
			f1_lines = f1_read.toString();
			
			br = null;
			br = new BufferedReader(new FileReader(f2_path));
			while((f2_lines = br.readLine()) !=null) 
				f2_read.add(f2_lines);
			
			f2_lines = "";
			f2_lines = f2_read.toString();
			
			
			return_str.setF1(f1_lines);
			return_str.setF2(f2_lines);
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			} 
		catch (IOException e) {
		e.printStackTrace();	
		}
		
		return return_str;
	}
	
	public String_Return preprocessing(String f1_lines, String f2_lines) {
		String_Return ret_str = new String_Return();		
	try {	
//		System.out.println(f1_lines.length());
//		System.out.println(f2_lines.length());
		ArrayList<String> stopWords = new ArrayList<String>();
		
		Collections.addAll(stopWords, "a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below",
						   "between","both","but","by","can","can't","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from",
						   "further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself","his","how","how's",
						   "i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off",
						   "on","once","only","or","other","ought","our","ours","ourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should","shouldn't","so","some","such",
						   "than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through",
						   "to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while",
						   "who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves");

		
		ArrayList<String> f1_words = null, f2_words = null;
		
		//Tokenizing for f1
		f1_words = Stream.of(f1_lines.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"))
			 				 .collect(Collectors.toCollection(ArrayList<String>::new));
		
		
		//Stop Words Removal for f1
		f1_words.removeAll(stopWords);
		Collections.sort(f1_words);
		
			
		
		//Tokenizing for f2
		f2_words = Stream.of(f2_lines.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"))
					.collect(Collectors.toCollection(ArrayList<String>::new));
		
		//Stop Words Removal for f2
		f2_words.removeAll(stopWords);
		Collections.sort(f2_words);
		
		
//		System.out.println("Sorted f2 "+f2_words);
//		System.out.println("Stop words removed f1"+ f1_words);
//		System.out.println("Stop words removed f2"+ f2_words);
			 
		
		String[] f1_arr = new String[f1_words.size()];
		
		f1_arr = f1_words.toArray(f1_arr);
		

		String[] f2_arr = new String[f2_words.size()];
		
		f2_arr = f2_words.toArray(f2_arr);
		
		ret_str.setF1_arry(f1_arr);
		ret_str.setF2_arry(f2_arr);
	}
	catch(Exception e) {
		e.printStackTrace();
	}
		return ret_str;
	}
	
	public int KMP(String[] f1_text, String[] f2_text) {

		int lps_count = 0;
	try {
		lps_count = pd.createLPS(f1_text, f2_text);		
//		System.out.println("f1 size "+f1_text.length);
//		System.out.println("f2 size "+f2_text.length);
//		System.out.println("LPS Count "+lps_count);
	}
	catch(Exception e) {
		e.printStackTrace();
	}
		return lps_count;
	}
	
	public int createLPS(String[] f1_text, String[] f2_text) {
		int i = 0, j = -1, k=0;
		String t1 ="", t2= "", kt2="";
		int lps_count = 0;
		int[] lps = new int[f2_text.length];
		
		try {
		while(i < f1_text.length) {
			t1 = f1_text[i];
			if((j + 1) < f2_text.length)
				t2 = f2_text[j+1];
			if(t1.equals(t2)) 
			{
				if((j + 1) == 0) 
					lps[j+1] = 0;
				
				lps[j+1] = lps_count++;
				i++;
				j++;
			
			}
			else {
				if(j == -1) {
					lps[j + 1] = 0;
					i++;
					j++;
				}
				else 
				{
					k = lps[j];
					while(k < (j+1))
					{
						kt2 = f2_text[k];
						if(kt2.equals(t1)) 
						{ 
							lps[k] = lps_count++;
							lps[k+1] = lps[k];
							
						}
						if(k!=0 && (lps[k-1] > lps[k]))
							lps[k] = lps[k-1];
						
						lps[j+1] = lps[k];
						k++;
					}
					i++;
					j++;
					}
				}
			if((j+1) >= f2_text.length) {
				j = j - 1;
			}
		}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return lps_count;
	}

	

private class String_Return {

	private String f1, f2;

	private String f1_arry[], f2_arry[];
	
	public String[] getF1_arry() {
		return this.f1_arry;
	}
	public void setF1_arry(String[] f1_arry) {
		this.f1_arry = f1_arry;
	}
	public String[] getF2_arry() {
		return this.f2_arry;
	}
	public void setF2_arry(String[] f2_arry) {
		this.f2_arry = f2_arry;
	}
	
	
	public String getF1() {
		return this.f1;
	}
	public void setF1(String f1) {
		this.f1 = f1;
	}
	public String getF2() {
		return this.f2;
	}
	public void setF2(String f2) {
		this.f2 = f2;
	}
 }
}


