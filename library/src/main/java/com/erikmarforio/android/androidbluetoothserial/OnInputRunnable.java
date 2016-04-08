package com.erikmarforio.android.androidbluetoothserial;

import java.io.InputStream;

public class OnInputRunnable implements Runnable {

    private InputListener il;
    private InputStream is;

    public OnInputRunnable(InputListener il, InputStream is) {
        this.il = il;
        this.is = is;
    }

    @Override
    public void run() {
        il.onInputAvailable(is);
    }
}
