package cn.edu.jxnu.blog.commons;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 在0~n内生成10个不同的随机数
 * 
 * @author： liguobin
 * @Description：
 * @时间： 2017-12-13 下午12:00:13
 * @version： V1.0
 * 
 */
public class ProdRandom {
	public static Set<Integer> set = new HashSet<Integer>(); // 不重复

	public static Set<Integer> getRandom(int n) {

		while (set.size() < 10) {
			int num = (int) (Math.random() * n);
			set.add(num);
		}
		return set;
	}

	public static void main(String[] args) {

		for (Iterator<Integer> iterator = getRandom(50).iterator(); iterator
				.hasNext();) {
			int type = (int) iterator.next();
			System.out.println(type);

		}

	}

}
