package com.erikmarforio.android.androidbluetoothserial;

public class OnDisconnectRunnable implements Runnable {

    private ConnectionListener cl;

    public OnDisconnectRunnable(ConnectionListener cl) {
        this.cl = cl;
    }

    @Override
    public void run() {
        cl.onDisconnect();
    }
}
