package udpTcp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

/**
 * @zww Administrator
 * 
 */
public class UdpServer {
	public static void main(String[] args) {
		DatagramSocket serverSocket = null;
		try {
			// 确定接收方IP和端口号
			InetAddress ip = InetAddress.getLocalHost();
			int port = 11511;
			// 确定接收方的套接字
			serverSocket = new DatagramSocket(port, ip);
			// 创建接收byte[]
			byte[] rebuf = new byte[1024];
			// 创建接收类型数据报
			DatagramPacket getPacket = new DatagramPacket(rebuf, rebuf.length);
			// 通过套接字接收数据
			serverSocket.receive(getPacket);
			// 解析收到的消息
			String getMsg = new String(rebuf, 0, getPacket.getLength());
			System.out.println("收到的消息" + getMsg);
			// 获取发送方的IP
			InetAddress rmIP = getPacket.getAddress();
			int rmport = getPacket.getPort();
			System.out.println("rmIP: " + rmIP + "  rmport: " + rmport);

			// 获取发送放套接字地址
			SocketAddress rmSocket = getPacket.getSocketAddress();
			// 反馈消息
			String res = new String("yes I Have received "+rmSocket.toString());
			byte[] resbyte = res.getBytes();

			// 构建发送数据报
			DatagramPacket sendPacket = new DatagramPacket(resbyte, resbyte.length, rmSocket);
			// 通过套接字发送回复
			serverSocket.send(sendPacket);
			serverSocket.close();
		} catch (Exception e) {

			// TODO: handle exception
			e.printStackTrace();
		}
	}
}