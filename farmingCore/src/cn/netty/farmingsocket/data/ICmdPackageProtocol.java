package cn.netty.farmingsocket.data;

public interface ICmdPackageProtocol {
	/*
	 * 定义发送数据包
	 */
	public void sendFuckHeart();
	public void sendFuckControlCmd(int num,int cmdStatus,String deviceId,IDataCompleteCallback completeCallback);
	public void rangSetOrGet(MethodType type ,int max,int min);
	public void modeStatusSetOrGet(MethodType type,short mode);





	public void closeContext();
	public void registerDataCompleteCallback(IDataCompleteCallback callback,boolean tempYN);

	public void setDeviceId(String deviceId);




	public static enum  MethodType{
		GET,POST
	}

	public static final  short AUTO_MODE=0xAC;
	public static final  short MANUAL_MODE=0xDC;


}

