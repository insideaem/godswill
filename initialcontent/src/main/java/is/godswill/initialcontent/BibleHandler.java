package is.godswill.initialcontent;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BibleHandler extends DefaultHandler {
	static Logger logger = Logger.getLogger(BibleHandler.class.getName());

	enum BOOK_ENUM {
		gen("Gen", "Genesis"), exo("Exo", "Exodus"), lev("Lev", "Leviticus"), num(
				"Num", "Numbers"), deu("Deu", "Deuteronomy"), jos("Jos",
				"Joshua"), judg("Judg", "Judges"), rut("Rut", "Ruth"), sam1(
				"1Sa", "1 Samuel"), sam2("2Sa", "2 Samuel"), ki1("1Ki",
				"1 Kings"), ki2("2Ki", "2 Kings"), ch1("1Ch", "1 Chronicles"), ch2(
				"2Ch", "2 Chronicles"), ezr("Ezr", "Ezra"), neh("Neh",
				"Nehemiah"), est("Est", "Esther"), job("Job", "Job"), psa(
				"Psa", "Psalms"), pro("Pro", "Proverbs"), ecc("Ecc",
				"Ecclesiastes"), song("Song", "Song of Songs"), isa("Isa",
				"Isaiah"), jer("Jer", "Jeremiah"), lam("Lam", "Lamentations"), eze(
				"Eze", "Ezekiel"), dan("Dan", "Daniel"), hos("Hos", "Hosea"), joe(
				"Joe", "Joel"), amo("Amo", "Amos"), obd("Obd", "Obadiah"), jon(
				"Jon", "JOnah"), mic("Mic", "Micah"), nah("Nah", "Nahum"), hab(
				"Hab", "Habakkuk"), zep("Zep", "Zephaniah"), hag("Hag",
				"Haggai"), zec("Zec", "Zechariar"), mal("Mal", "Malachi"), mat(
				"Mat", "Matthew"), mar("Mar", "Mark"), luk("Luk", "Luke"), joh(
				"Joh", "John"), act("Act", "Acts"), rom("Rom", "Romans"), cor1(
				"1Cor", "1 Corinthians"), cor2("2Cor", "2 Corinthians"), gal(
				"Gal", "Galatians"), eph("Eph", "Ephesians"), phili("Phili",
				"Philippians"), col("Col", "Colossians"), th1("1Th",
				"1 Thessalonians"), th2("2Th", "2 Thessalonians"), ti1("1Ti",
				"1 Timothy"), ti2("2Ti", "2 Timothy"), tit("Tit", "Titus"), phile(
				"Phile", "Philemon"), heb("Heb", "Hebrews"), jam("Jam", "James"), pe1(
				"1Pe", "1 Peter"), pe2("2Pe", "2 Peter"), jo1("1Jo", "1 John"), jo2(
				"2Jo", "2 John"), jo3("3Jo", "3 John"), jude("Jude", "Jude"), rev(
				"Rev", "Revelation");

		private String title;
		private String name;

		BOOK_ENUM(String name, String title) {
			this.name = name;
			this.title = title;
		}

		public String getTitle() {
			return this.title;
		}

		public String getName() {
			return this.name;
		}
	}

	JSONObject result = new JSONObject();
	JSONObject currentBook;
	JSONObject currentChapter;
	JSONObject currentVerse;
	String caption;
	boolean isCaption;
	String lastText;

	boolean skip = false;

	boolean isVerse = false;
	int maxBooks;

	public BibleHandler(int maxBooks) {
		super();
		this.maxBooks = maxBooks;
		try {
			result.put("sling:resourceType", "godswill/bible");
		} catch (JSONException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public JSONObject getBible() {
		return this.result;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		try {
			if (skip) {
				return;
			}

			if ("BIBLEBOOK".equals(qName)) {

				int bnumber = Integer.parseInt(attributes.getValue("bnumber"));

				if (bnumber > maxBooks) {
					skip = true;
				}

				if (skip) {
					return;
				}
				BOOK_ENUM be = BOOK_ENUM.values()[bnumber - 1];
				String bookName = be.getName();
				String bookTitle = be.getTitle();

				String readedBookTitle = attributes.getValue("bname");
				if (readedBookTitle != null)
					bookTitle = readedBookTitle;

				logger.log(Level.INFO, "Parsing book: " + bookTitle);

				currentBook = new JSONObject();
				currentBook.put("name", bookName);
				currentBook.put("title", bookTitle);
				currentBook.put("primaryNodeType", "sling:Folder");
				currentBook.put("sling:resourceType", "godswill/book");

				result.put(bookName.toLowerCase(), currentBook);

			} else if ("CHAPTER".equals(qName)) {
				currentChapter = new JSONObject();
				currentChapter.put("primaryNodeType", "sling:Folder");
				currentChapter.put("sling:resourceType", "godswill/chapter");

				String chapterNumber = attributes.getValue("cnumber");

				currentBook.put(chapterNumber, currentChapter);

			} else if ("CAPTION".equals(qName)) {
				isCaption = true;

			} else if ("VERS".equals(qName)) {
				currentVerse = new JSONObject();
				currentVerse.put("primaryNodeType", "sling:Folder");
				currentVerse.put("sling:resourceType", "godswill/verse");
				String verseNumber = attributes.getValue("vnumber");

				currentChapter.put(verseNumber, currentVerse);
				isVerse = true;
			}

		} catch (JSONException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (lastText.length() > 0) {
			try {
				if (isVerse) {
					currentVerse.put("text", lastText);
					if (StringUtils.isNotBlank(this.caption)) {
						// Caption available
						currentVerse.put("caption", this.caption);
						this.caption = null;
					}
					isVerse = false;

				} else {
					result.put(qName, lastText);
				}
			} catch (JSONException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}

	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {

		if (skip) {
			return;
		}

		lastText = new String(ch, start, length).trim();
		if (isCaption) {
			this.isCaption = false;
			this.caption = lastText;
		}

	}
}
