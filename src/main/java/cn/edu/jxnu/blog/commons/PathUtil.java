package cn.edu.jxnu.blog.commons;

/**
 * @Description 获取项目存储的根路径
 */
public class PathUtil {

    /**
     * 获取根项目路径
     * @return
     */
    public static String getRootPath(){
        String path=PathUtil.class.getResource("/").getPath();
        for (int i = 0; i < 5; i++) {
            int end = path.lastIndexOf("/");
            path = path.substring(0, end);
        }
        int index = path.indexOf(":");
        String path2 = path.substring(index+1);
        return path2;
    }
    public static void main(String[] args) {
    	System.out.println(PathUtil.getRootPath().subSequence(1,PathUtil.getRootPath().length()));
	}
}
