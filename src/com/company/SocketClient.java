package com.company;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * La classe SocketClient permet d'ouvrir une socket
 * pour contacter le serveur et lui transférer des données
 *
 * @author  David Delemotte
 * @version 1.0
 * @since   2017-09-12
 */

public class SocketClient {

    public SocketClient(Datas datas) {

            String host = "localhost";
            int port = 19998;

            StringBuffer serverResponse = new StringBuffer();
            System.out.println("SocketClient initialized");

            try {
                /* Initialisation de la connexion au serveur*/
                InetAddress address = InetAddress.getByName(host);
                Socket socket = new Socket(address, port);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                /* Envoi des données au serveur */
                outputStream.writeObject(datas);
                outputStream.flush();

                //outputStream.close(); // a mettre à la fin

                /* Lecture des données reçues via la socket */

                BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "US-ASCII");

                int c;
                while ((c = inputStreamReader.read()) != 13) {
                    serverResponse.append((char) c);
                }

                /* Affichage de la réponse du serveur */
                ClientResultWindow window = new ClientResultWindow(serverResponse.toString());

                /* Fermeture de la socket */
                socket.close();
            }
            catch (IOException e) {
                System.out.println(e);
            }
            catch (Exception e) {
                System.out.println(e);
            }
    }
}
