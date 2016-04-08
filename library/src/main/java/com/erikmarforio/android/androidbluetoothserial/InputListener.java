package com.erikmarforio.android.androidbluetoothserial;

import java.io.InputStream;

public interface InputListener {

    void onInputAvailable(InputStream is);

}
