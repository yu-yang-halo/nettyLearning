package cn.netty.network.time;


import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		
		System.out.println("TimeClientHandler active");
		
	}
 
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	 UnixTime m = (UnixTime) msg;
    	 System.out.println(m);
    	 ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
