import cn.netty.farmingsocket.SPackage;
import cn.netty.farmingsocket.SocketClientManager;
import cn.netty.farmingsocket.data.ICmdPackageProtocol;
import cn.netty.farmingsocket.data.IDataCompleteCallback;

public class Test {
    public static void main(String[] args) {
		SocketClientManager.getInstance().beginConnect("00-00-04-01", new IDataCompleteCallback() {
			
			@Override
			public void onDataComplete(SPackage spackage) {
				SocketClientManager.getInstance().getHandler().modeStatusSetOrGet(ICmdPackageProtocol.MethodType.POST,ICmdPackageProtocol.MANUAL_MODE);
			}
		});
	}
}
