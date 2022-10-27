package Mreza;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

final class Server {
    static final int SERVER_TEST_PORT = 12345;


    public static void main(String[] args) {
        Server server = new Server(SERVER_TEST_PORT);
        server.execute();
    }


    private final int port;
    private final Set<UserThread> users;


    Server(int port) {
        this.port = port;
        this.users = Collections.synchronizedSet(new HashSet<>());
    }


    void execute() {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Chat server is listening on port: " + port);

            //noinspection InfiniteLoopStatement
            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected.");

                // We dispatch a new thread for each user in the chat
                UserThread user = new UserThread(client, this);
                this.users.add(user);
                user.start();

                // Also, we cannot use try-with-resources block on client socket
                // because it would be closed immediately after dispatching a
                // thread. We leave the thread to close the socket.
            }
        } catch (IOException ex) {
            System.err.println("Server errored: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void broadcast(UserThread sender, String message) {
        synchronized (this.users) {
            this.users.stream()
                    .filter(u -> u != sender)
                    .forEach(u -> u.sendMessage(message))
            ;
        }
    }

    void remove(UserThread user) {
        String username = user.getUsername();
        this.users.remove(user);
        System.err.println("Client disconnected: " + username);
    }

    List<String> getUserNames() {
        synchronized (this.users) {
            return this.users.stream()
                    .map(UserThread::getUsername)
                    .collect(Collectors.toList())
                    ;
        }
    }
}