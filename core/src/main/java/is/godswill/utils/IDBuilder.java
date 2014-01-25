package is.godswill.utils;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public class IDBuilder {
	public static String buildIdFromPath(String path) {
		String[] splits = path.split("/");
		String result = "";

		if (splits.length == 5) {
			// This is at the bible level --> reutrn bible node name
			result = splits[4];
		} else if (splits.length > 5) {
			splits = Arrays.copyOfRange(splits, 5, splits.length);
			result = StringUtils.join(splits, "_");
		}
		return result;
	}
}
