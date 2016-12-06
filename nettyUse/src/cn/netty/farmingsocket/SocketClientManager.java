package cn.netty.farmingsocket;

import java.util.concurrent.TimeUnit;

import cn.netty.farmingsocket.data.ICmdPackageProtocol;
import cn.netty.farmingsocket.data.IDataAnalysis;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class SocketClientManager implements ICmdPackageProtocol{
	private static final int READ_IDEL_TIME_OUT = 4; // 读超时
	private static final int WRITE_IDEL_TIME_OUT = 5;// 写超时
	private static final int ALL_IDEL_TIME_OUT = 7; // 所有超时
	private static final String HOST_IP="183.78.182.98";
	private static final int HOST_PORT =9101;
	
	private SocketClientHandler handler;
	
	
	private static SocketClientManager instance;
	
	private SocketClientManager(){
		handler=new SocketClientHandler();
	}
	public static SocketClientManager getInstance(){
		synchronized (SocketClientManager.class) {
			if(instance==null){
				instance=new SocketClientManager();
				
			}
		}
		return instance;
	}
	/*
	 * (non-Javadoc)
	 * @see cn.netty.farmingsocket.data.ICmdPackageProtocol#sendFuckHeart()
	 */
	
	@Override
	public void sendFuckHeart() {
		handler.sendFuckHeart();
	}
    
	
	
	public static void main(String[] args) throws Exception {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				 try {
					SocketClientManager.getInstance().beginConnect(new IDataAnalysis() {
						
						@Override
						public void analysisData(SPackage spackage) {
							/*
							 * 解析数据包
							 */
							 System.out.println("&&&&"+spackage);
						}
					});
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}).start();;
       
		new Thread().sleep(5000);
		
		SocketClientManager.getInstance().sendFuckHeart();
    
    }
    
	public SocketClientHandler getHandler() {
		return handler;
	}
	
	public void closeConnect(){
		handler.closeContext(true);
	}
	
	public void beginConnect(IDataAnalysis iDataAnalysis) throws InterruptedException{
		handler.setiDataAnalysis(iDataAnalysis);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new SocketEncoder(),new SocketDecoder(),handler
                    		,new IdleStateHandler(READ_IDEL_TIME_OUT,
                    				WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(HOST_IP,HOST_PORT).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
	}

    
    
    
    
    
}
