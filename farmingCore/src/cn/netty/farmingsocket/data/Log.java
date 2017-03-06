package cn.netty.farmingsocket.data;

public class Log {
	public static void e(String title,String message){
		System.err.println("["+title+"] "+message);
	}
	
	public static void i(String title,String message){
		System.err.println("["+title+"] "+message);
	}
	public static void v(String title,String message){
		System.out.println("["+title+"] "+message);
	}

}
