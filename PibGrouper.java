import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class that takes in files of the following format:
 * 
 * 1. 你好, 这是, 中国
 * 3. 我是, 可爱, 哈哈
 * 
 * Where the '. ' and ', ' can be replaced with other delimiters.
 * The default delimeters are '. ' and ', ' (with spaces) 
 * but that can be changed below.
 * 
 * Some things that could be better:
 * - preventing duplicates
 * - less messy lesson marker thing
 * - less messy delimiter
 **/
public class PibGrouper {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java PibGrouper path/to/input.txt " + 
				"path/to/output.txt <file # delim> <word delim>");
			System.exit(1);
		}

		String inputName = args[0];
		String outputName = args[1];
		String lineDelim = "\\. "; // . is special regex char so must be escaped
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
				String[] line = ln.split(lineDelim);
				String lesson = ""; // lesson marker w/ parenthesis etc
				String[] inWords;

				if (line.length == 1) {
					System.out.println("No line delimiter found.");
					inWords = ln.split(wordDelim);
					//System.exit(1);
				} else {
					lesson = String.format(" (%s)", line[0]);
					inWords = line[1].split(wordDelim);
				}

				if (inWords.length == 1) {
					System.out.println("Word delimiter is incorrect.");
					System.exit(1);
				}

				ArrayList<String> wordGroup;
				//System.out.println(ln);
				for (String word : inWords) {
					for (char c : word.toCharArray()) {
						if ((wordGroup = charMap.get(c)) == null) {
							wordGroup = new ArrayList<>();
							//System.out.println(String.format(
							//"New char: %s from %s", Character.toString(c), ln);
						}

						if (wordGroup.contains)
						wordGroup.add(String.format("%s%s", word, lesson));
						charMap.put(c, wordGroup);
					}
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