package com.example.warcabydobre;


import org.junit.Test;


import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;


public class SocketTest {

    ServerSocket serverSocket;
    @Test
    public void testServerSocketWithSpecificPort() throws IOException {
        final int testPort = 4444;
        ServerSocket serverSocket = new ServerSocket(testPort);
        assertEquals(serverSocket.getLocalPort(), testPort);
    }

}