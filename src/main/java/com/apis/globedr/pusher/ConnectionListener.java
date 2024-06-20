package com.apis.globedr.pusher;

import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

public class ConnectionListener implements ConnectionEventListener {


    @Override
    public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {

        System.out.println("State changed from " + connectionStateChange.getPreviousState() +
                " to " + connectionStateChange.getCurrentState());
    }

    @Override
    public void onError(String code, String message, Exception e) {
        System.out.println("There was a problem connecting! " +
                "\n code: " + code +
                "\n message: " + message +
                "\n Exception: " + e
        );
    }



}
