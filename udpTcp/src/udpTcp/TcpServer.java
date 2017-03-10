package udpTcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @zww Administrator
 * 
 */
public class TcpServer {
	public static void main(String[] args) {
		ServerSocket servSock =null;
		try {
			//1. 构造ServerSocket实例，指定服务端口。  
		   servSock = new ServerSocket(8999);  
			  
			  
			  
			  
			while(true)  
			{  
			       // 2.调用accept方法，建立和客户端的连接  
			           Socket clntSock = servSock.accept();  
			           SocketAddress clientAddress =      
			                clntSock.getRemoteSocketAddress();  
			           System.out.println("Handling client at " + clientAddress);  
			  
			  
			        // 3. 获取连接的InputStream,OutputStream来进行数据读写  
			            InputStream in = clntSock.getInputStream();  
			            OutputStream out = clntSock.getOutputStream();  
			            
			            byte[] receiveBuf=new byte[1024];
			            int recvMsgSize=-1;
			  
			            while((recvMsgSize = in.read(receiveBuf)) != -1)  
			            {  
			               // out.write(receiveBuf, 0, recvMsgSize);  
			                
			                System.out.println(new String(receiveBuf));
			            }     
			            
			            
			        // 4.操作结束，关闭socket.  
			            clntSock.close();  
			} 


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}finally {
			try {
				servSock.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}