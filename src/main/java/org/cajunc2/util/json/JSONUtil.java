package org.cajunc2.util.json;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.json.simple.parser.JSONParser;

public class JSONUtil {

	@SuppressWarnings("unchecked")
	public static <T> T parse(File f) throws Exception {
		Reader r = new FileReader(f);
		try {
			return (T) new JSONParser().parse(r);
		} finally {
			r.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T parse(Reader r) throws Exception {
		return (T) new JSONParser().parse(r);
	}

	private JSONUtil() {
	}

}
