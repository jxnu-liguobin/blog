package cn.edu.jxnu.blog.test.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.jxnu.blog.domin.Blog;

public class Test {

	/**
	 * 测试Comparable
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Blog blog = new Blog();
		Blog blog2 = new Blog();
		Blog blog3 = new Blog();
		Blog blog4 = new Blog();
		Blog blog5 = new Blog();
		blog.setReplyHit(1);
		blog2.setReplyHit(12);
		blog3.setReplyHit(211);
		blog4.setReplyHit(112);
		blog5.setReplyHit(31);
		List<Blog> list = new ArrayList<>();
		list.add(blog);
		list.add(blog2);
		list.add(blog3);
		list.add(blog4);
		list.add(blog5);
		Collections.sort(list);
		for (Blog blog6 : list) {
			System.out.println(blog6.getReplyHit());
		}
		System.out.println();

	}

}
