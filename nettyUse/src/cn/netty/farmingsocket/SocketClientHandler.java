package cn.netty.farmingsocket;

import java.util.Date;

import cn.netty.farmingsocket.SPackage.DeviceType;
import cn.netty.farmingsocket.data.ICmdPackageProtocol;
import cn.netty.farmingsocket.data.IDataAnalysis;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

public class SocketClientHandler extends ChannelInboundHandlerAdapter implements ICmdPackageProtocol{
	private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
			.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat", CharsetUtil.UTF_8)); // 1
	private ChannelHandlerContext currentContext;
	private IDataAnalysis iDataAnalysis;
	
	
	public void closeContext(boolean closeConnectYN) {
		System.out.println("连接 closeConnectYN。。。"+closeConnectYN);
		if(currentContext!=null){
			currentContext.close();
			System.out.println("连接关闭》。。。");
		}
	}
	
	
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		this.currentContext=ctx;
		System.out.println("userEventTriggered....");
		if (evt instanceof IdleStateEvent) { // 2
			IdleStateEvent event = (IdleStateEvent) evt;
			String type = "";
			if (event.state() == IdleState.READER_IDLE) {
				type = "read idle";
			} else if (event.state() == IdleState.WRITER_IDLE) {
				type = "write idle";
			} else if (event.state() == IdleState.ALL_IDLE) {
				type = "all idle";
			}
			sendFuckHeart(ctx);
			System.out.println(ctx.channel().remoteAddress() + "超时类型：" + type);
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		this.currentContext=ctx;
		System.out.println("TimeClientHandler active");
		

		//sendFuckHeart(ctx);
	}

	
	
	@Override
	public void sendFuckHeart() {
		// TODO Auto-generated method stub
		if(currentContext==null){
			return;
		}

		SPackage data1 = new SPackage(DeviceType.Android, "00-00-04-01",
				new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }, (short) 0x0001, (byte) 0x00, (short) 0);

		currentContext.writeAndFlush(data1).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
		
	}
	
	

	public void sendFuckHeart(ChannelHandlerContext ctx) {
		if(ctx==null){
			ctx=currentContext;
		}
//		SPackage data0 = new SPackage(DeviceType.Water, "00-00-04-01",
//				new byte[] { 0x12, 0x78, (byte) 0xA0, (byte) 0x9C, 0x00, 0x00, 0x00, 0x00 }, (short) 0x0002,
//				(byte) 0x00, (short) 1);
//		data0.setContents(new byte[] { (byte) 0xFF });

		SPackage data1 = new SPackage(DeviceType.Android, "00-00-04-01",
				new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }, (short) 0x0001, (byte) 0x00, (short) 0);

		ctx.writeAndFlush(data1).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
		//ctx.writeAndFlush(data0).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		this.currentContext=ctx;
		SPackage m = (SPackage) msg;
		if(this.iDataAnalysis!=null){
			this.iDataAnalysis.analysisData(m);
		}
		
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}



	public IDataAnalysis getiDataAnalysis() {
		return iDataAnalysis;
	}



	public void setiDataAnalysis(IDataAnalysis iDataAnalysis) {
		this.iDataAnalysis = iDataAnalysis;
	}



	
	
}
