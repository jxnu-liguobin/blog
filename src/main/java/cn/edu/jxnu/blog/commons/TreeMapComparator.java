package cn.edu.jxnu.blog.commons;

import java.util.Comparator;
import java.util.Map;
/**
 * @Description TreeMap比较器
 * @author liguobin
 *
 */
public class TreeMapComparator implements Comparator<String> {

	Map<String, Integer> base;

	// 这里需要将要比较的map集合传进来
	public TreeMapComparator(Map<String, Integer> base) {
		this.base = base;
	}

	@Override
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		}
		if (base.get(a) < base.get(b)) {
			return 1;
		} else {
			return 0;
		}
	}
}
