package http.querymapper;

import java.util.HashMap;
import java.util.Map;

public class QueryMapper {
	public static Map<String, String> resolveQuery(String query) {
		Map<String, String> res = new HashMap<>();
		String[] v = query.split("&");
		for (String s : v) {
			String[] q = s.split("=");
			res.put(q[0], q[1]);
		}
		return res;
	}
}
