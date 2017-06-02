import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;


public class Headlines {

	private static Gson gson;
	private static ArrayList<HeadlineEntry> headlineEntries;
	
	private static FileWriter fw = null; 
	private static BufferedWriter bw = null; 
	private static PrintWriter pw = null;
	
	private static String filePath = "headlines.txt";
	
	public static void main(String[] args) throws IOException {
		gson = new Gson();
		fw = new FileWriter(filePath, true); 
		bw = new BufferedWriter(fw);
		pw = new PrintWriter(bw);
		headlineEntries = new ArrayList<HeadlineEntry>();
		
		try {
			Document document = getDocument("http://rust.facepunch.com/blog/");
			headlineEntries = loadHeadlineEntries("headlines.txt");
			headlineEntries = findHeadlines(document);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<HeadlineEntry> loadHeadlineEntries(String filePath) throws IOException {
		ArrayList<HeadlineEntry> entries = new ArrayList<HeadlineEntry>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       HeadlineEntry entry = gson.fromJson(line, HeadlineEntry.class);
		       entries.add(entry);
		    }
		}
		
		return entries;
	}
	
	public static ArrayList<HeadlineEntry> findHeadlines(Document document) throws IOException {
		ArrayList<HeadlineEntry> entries = new ArrayList<HeadlineEntry>();
		
		Elements headlines = document.select(".hero-body");
		
		boolean anythingNewFound = false;
		for(Element headline : headlines) {
			String title = headline.select(".title").html();
			String url = headline.absUrl("href");
			
			HeadlineEntry headlineEntry = new HeadlineEntry();
			headlineEntry.date = new Date();
			headlineEntry.url = url;
			headlineEntry.title = title;
			
			boolean found = false;
			for(HeadlineEntry oldEntry : headlineEntries) {
				if(headlineEntry.url.equals(oldEntry.url)) {
					found = true;
				}
			}
			
			if(!found) {
				anythingNewFound = true;
				entries.add(headlineEntry);
				System.out.println(headlineEntry.title);
				System.out.println("\tUrl: " + headlineEntry.url);
				System.out.println("\tAdded: " + headlineEntry.date);
				System.out.println("---");
				pw.println(gson.toJson(headlineEntry));
			}
		}
		
		if(!anythingNewFound) {
			System.out.println("Didn't find new headlines.");
		}
		
		pw.flush();
		
		return entries;
	}
	
	public static Document getDocument(String url) throws IOException {
		// User agent thingy lent from Frank Korving
		return Jsoup.connect(url)
				.timeout(4000)
				.userAgent(
						"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
				.referrer("http://www.google.com")
				.get();
	}

}
