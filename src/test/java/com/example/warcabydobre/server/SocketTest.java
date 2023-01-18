package com.example.warcabydobre.server;


import com.example.warcabydobre.view.CheckersGame;
import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SocketTest {

    ServerSocket serverSocket;
    @Test
    public void testServerSocketWithSpecificPort() throws IOException {
        final int testPort = 4444;
        ServerSocket serverSocket = new ServerSocket(testPort);
        assertEquals(serverSocket.getLocalPort(), testPort);
    }

    Socket mockedSocket;
    ByteArrayOutputStream out;

   /* @Before
    public void setUpSocket() throws IOException{
        mockedSocket = mock(Socket.class);
        InputStream in = new ByteArrayInputStream("1\nSIM_CLICK 13 4\nVALID_MARK \nSIM_CLICK 12 4\nVALID_MOVE".getBytes());
        out = new ByteArrayOutputStream();
        when(mockedSocket.getInputStream()).thenReturn(in);
        when(mockedSocket.getOutputStream()).thenReturn(out);
    }
*/


}