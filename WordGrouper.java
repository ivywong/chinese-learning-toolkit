import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class that takes in files of the following format:
 * 
 * 你好
 * 这是
 * 中国
 * 我是
 * 可爱
 * 哈哈
 * 
 * Where the only delimiters are newlines. This delimeter cannot be changed 
 * without changing the core code below.
 **/
public class WordGrouper {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java WordGrouper path/to/input.txt"
			+ " path/to/output.txt <file # delim> <word delim>");
			System.exit(1);
		}

		String inputName = args[0];
		String outputName = args[1];
		String lineDelim = ". ";
		String wordDelim = ", ";

		// Include optional delimiters
		if (args.length == 4) {
			lineDelim = args[2];
			wordDelim = args[3];
		}

		// try-with-resources automatically closes these streams afterwards
		// also I chained everything because laziness...
		try (BufferedReader reader = new BufferedReader(
			new InputStreamReader(new FileInputStream(inputName), "UTF-8"));
			BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8"))) {

			HashMap<Character, ArrayList<String>> charMap = new HashMap<>();
			String ln = reader.readLine();
			while (ln != null){
				ArrayList<String> words;
				//System.out.println(ln);
				for (char c : ln.toCharArray()) {
					if ((words = charMap.get(c)) == null) {
						words = new ArrayList<>();
						//System.out.println(String.format(
						//  "New char: %s from %s", Character.toString(c), ln);
					}

					words.add(ln);
					charMap.put(c, words);
				}
				ln = reader.readLine();
			}

			// Write groups to output file
			for (HashMap.Entry<Character, ArrayList<String>> kvpair : charMap.entrySet()) {
				// ran into this weird invisible character and here it is
				if (!kvpair.getKey().equals('\uFEFF')) {
					writer.write(String.format("%s: ", kvpair.getKey()));
					String words = kvpair.getValue()
						.stream().collect(Collectors.joining(", "));
					writer.write(words);
					writer.newLine();
				}
				
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException occurred.");
			System.out.println(e.getMessage());
		}
	}
}