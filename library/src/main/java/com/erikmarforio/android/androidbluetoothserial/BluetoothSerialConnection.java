package com.erikmarforio.android.androidbluetoothserial;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BluetoothSerialConnection extends Thread {

    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice btDevice = null;
    private BluetoothSocket btSocket = null;

    private InputStream is;
    private OutputStream os;

    private List<InputListener> inputListeners = new ArrayList<InputListener>();
    private List<ConnectionListener> connectionListeners = new ArrayList<ConnectionListener>();

    public BluetoothSerialConnection(String btAddress) {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        btDevice = btAdapter.getRemoteDevice(btAddress);
    }

    public void connect() {
        this.start();
    }

    @Override
    public void run() {
        try {
            btSocket = btDevice.createRfcommSocketToServiceRecord(btDevice.getUuids()[0].getUuid());
            btSocket.connect();

            if (btSocket.isConnected()) {
                for (ConnectionListener cl : connectionListeners) {
                    new Handler(Looper.getMainLooper()).post(new OnConnectRunnable(cl));
                }
                is = btSocket.getInputStream();
                os = btSocket.getOutputStream();

                while (true) {
                    if (is.available() > 0) {
                        for (InputListener il : inputListeners) {
                            new Handler(Looper.getMainLooper()).post(new OnInputRunnable(il, is));
                        }
                    }
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public void addInputListener(InputListener toAdd) {
        inputListeners.add(toAdd);
    }

    public void addConnectionListener(ConnectionListener toAdd) {
        connectionListeners.add(toAdd);
    }

    public void close() {
        try {
            inputListeners.clear();
            if (is != null) is.close();
            if (os != null) os.close();
            btSocket.close();
            for (ConnectionListener cl : connectionListeners) {
                cl.onDisconnect();
            }
            connectionListeners.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isConnected() {
        return btSocket.isConnected();
    }

    public int read() {
        try {
            return is.read();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void write(int oneByte) {
        try {
            os.write(oneByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(byte[] message) {
        try {
            os.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
