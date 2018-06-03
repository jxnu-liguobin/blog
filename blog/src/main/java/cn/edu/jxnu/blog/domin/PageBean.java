package cn.edu.jxnu.blog.domin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 分页bean封装
 */
public class PageBean<T>  {

	private int currPage; // 当前页数
	private int pageSize; // 每页显示的个数
	private long total; // 总记录数
	private int start; // limit（start,end）
	private int end;
	private List<T> result; // 分页查询的结果
	private long count ; //总页数
	public long getCount() {
		count = total % pageSize==0 ? total/pageSize : total/pageSize+1;
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private Map<String, Object> map = new HashMap<String, Object>(); // 查询条件

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	PageBean() {

	}

	public PageBean(int currPage, int pageSize) {
		this.currPage = currPage;
		this.pageSize = pageSize;
		this.start = (currPage - 1) * pageSize;
		this.end = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "PageBean{" + "currPage=" + currPage + ", pageSize=" + pageSize
				+ ", total=" + total + ", start=" + start + ", end=" + end
				+ ", result=" + result + '}';
	}
}