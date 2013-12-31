package is.godswill.utils;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public class IDBuilder {
	public static String buildIdFromPath(String path) {
		String[] splits = path.split("/");
		String result = "";

		if (splits.length == 4) {
			// This is at the bible level --> reutrn bible node name
			result = splits[3];
		} else if (splits.length > 4) {
			splits = Arrays.copyOfRange(splits, 4, splits.length);
			result = StringUtils.join(splits, "_");
		}
		return result;
	}
}
