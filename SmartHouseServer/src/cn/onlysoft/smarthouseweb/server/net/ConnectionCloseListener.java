package cn.onlysoft.smarthouseweb.server.net;
/**
 * 通道连接关闭时的监听器  add by rxm
 *
 */
public interface ConnectionCloseListener {
    /**
     * 当一个通道连接被关闭了执行
     */
    public void onConnectionClose(Object handback);

}
