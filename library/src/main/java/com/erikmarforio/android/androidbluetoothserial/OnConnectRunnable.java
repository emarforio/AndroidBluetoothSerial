package com.erikmarforio.android.androidbluetoothserial;

public class OnConnectRunnable implements Runnable {

    private ConnectionListener cl;

    public OnConnectRunnable(ConnectionListener cl) {
        this.cl = cl;
    }

    @Override
    public void run() {
        cl.onConnect();
    }
}