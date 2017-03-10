package udpTcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;    
import java.net.DatagramSocket;    
import java.net.InetAddress;
import java.net.Socket;    
public class TcpClient {    
    public static void main(String[] args) {    
        try {   
        	
        	String message="Hello,你好！";
        	
        	
        	byte[] data=message.getBytes();
        	
        	
        	// 1.根据指定的server地址和端口，建立socket连接。  
        	Socket socket = new Socket("127.0.0.1", 8999);  
        	// 2. 根据socket实例获取InputStream, OutputStream进行数据读写。  
        	InputStream in = socket.getInputStream();  
        	OutputStream out = socket.getOutputStream();  
        	out.write(data);  
        	//3.操作结束，关闭socket.  
        	socket.close();  
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    
    }    
}   