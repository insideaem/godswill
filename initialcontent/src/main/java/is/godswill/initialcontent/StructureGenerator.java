package is.godswill.initialcontent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class StructureGenerator {
	static Logger logger = Logger.getLogger(StructureGenerator.class.getName());

	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			int maxBooks = Integer.parseInt(args[0]);

			for (int i = 1; i < args.length; i++) {
				String bibleFileName = args[i];
				process(maxBooks, bibleFileName);
			}
		}
	}

	private static void process(int maxBooks, String bibleFileName)
			throws IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		FileWriter fileWriter = null;
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();

			BibleHandler handler = new BibleHandler(maxBooks);
			URL bibleUrl = handler.getClass().getClassLoader()
					.getResource("SLING-INF/bam/bibles/" + bibleFileName);
			File bibleInput = new File(bibleUrl.toURI());
			String outputFileName = bibleInput.getName().replaceAll("\\.xml",
					".json");
			String outputDirName = bibleInput.getName()
					.replaceAll("\\.xml", "");
			String bibleOutputPath = bibleInput.getParentFile().getParentFile()
					.getParentFile().getPath()
					+ File.separator + "content" + File.separator + "godswill";

			new File(bibleOutputPath + File.separator + outputDirName).mkdirs();
			File bibleOutput = new File(bibleOutputPath + File.separator
					+ outputFileName);

			logger.log(Level.INFO, bibleInput.getPath());
			logger.log(Level.INFO, bibleOutput.getPath());
			saxParser.parse(bibleInput, handler);

			fileWriter = new FileWriter(bibleOutput);
			fileWriter.write(handler.getBible().toString(2));

			fileWriter.flush();

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (fileWriter != null) {
				fileWriter.close();

			}
		}
	}
}
