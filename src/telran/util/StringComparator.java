package telran.util;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		int res = o1.length() - o2.length();
		return res == 0 ? o1.compareToIgnoreCase(o2) : res;
	}

}
