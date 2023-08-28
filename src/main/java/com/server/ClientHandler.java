package com.server;

import com.sinkingships.AppGlobal;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;
    private int hitCount = 0;

    private boolean ready = false;

    private HashMap<String, boolean[][]> boards = new HashMap<>();

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + userName + " has entered game!");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    private void finishGame() throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.bufferedWriter.write("FINISH");
            clientHandler.bufferedWriter.newLine();
            clientHandler.bufferedWriter.flush();
        }
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                // HIT HANDLING
                if (messageToSend.contains("HIT")) {
                    String[] parts = messageToSend.split(":");
                    String user = "";
                    if (parts.length > 0) {
                        user = parts[0].trim();
                    } else {
                        System.out.println("No delimiter found in the input string.");
                    }
                    String finalUser = user;
                    Optional<ClientHandler> opponentHandler = clientHandlers.stream()
                                                                            .filter(h -> !h.userName.equals(finalUser)).findFirst();
                    Optional<ClientHandler> yourHandler = clientHandlers.stream()
                                .filter(h -> h.userName.equals(finalUser)).findFirst();

                    boolean opponentBoard[][] = null;
                    if (opponentHandler.isPresent())
                        opponentBoard = opponentHandler.get().boards.get(opponentHandler.get().userName);

                    if (didItHit(messageToSend.substring(messageToSend.length() - 2, messageToSend.length()), opponentBoard)) {
                        if (yourHandler.isPresent()) {
                            yourHandler.get().bufferedWriter.write("HIT");
                            yourHandler.get().bufferedWriter.newLine();
                            yourHandler.get().bufferedWriter.flush();
                            yourHandler.get().hitCount ++;
                            if (yourHandler.get().hitCount >= 16) {
                                clientHandler.bufferedWriter.write("WIN " + "Player " + yourHandler.get().userName + " has won a game");
                                clientHandler.bufferedWriter.newLine();
                                clientHandler.bufferedWriter.flush();
                                finishGame();

                                /*Alert winningAlert = new Alert(Alert.AlertType.NONE);
                                winningAlert.setHeaderText("WIN");
                                winningAlert.setContentText("Player " + yourHandler.get().userName + " has won a game");
                                winningAlert.showAndWait();*/
                            }
                        }
                    }

                    if (clientHandler.userName.equals(user)) {
                        clientHandler.bufferedWriter.write("OPPONENTS_TURN");
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    } else {
                        clientHandler.bufferedWriter.write("YOUR_TURN");
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    }
                } else if(messageToSend.contains("READY_ME")) {

                    // Sending of array
                    String[] parts = messageToSend.split(">");
                    String result = "";
                    if (parts.length > 1) {
                        result = parts[1]; // This will be "testingmessage"
                    } else {
                        System.out.println("Delimiter not found or input format is incorrect.");
                    }

                    // parse string to boolean
                    String[] rows = result.split("\\], \\["); // Split rows

                    int numRows = rows.length;
                    int numCols = rows[0].split(", ").length; // Assuming all rows have the same number of columns

                    boolean[][] matrix = new boolean[numRows][numCols];

                    for (int i = 0; i < numRows; i++) {
                        String[] rowValues = rows[i].replaceAll("[\\[\\]]", "").split(", ");
                        for (int j = 0; j < numCols; j++) {
                            matrix[i][j] = Boolean.parseBoolean(rowValues[j]);
                        }
                    }
                    // save players game state to client handler
                    boards.putIfAbsent(clientHandler.userName, matrix);


                    ready = true;
                    boolean bothReady = true;
                    if (clientHandlers.size() > 1) {
                        for (ClientHandler clientHandlerSec : clientHandlers) {
                            if (!clientHandlerSec.ready)
                                bothReady = false;
                        }
                    } else
                        bothReady = false;

                    if (bothReady)
                        for (ClientHandler clientHandlerSec : clientHandlers) {
                            clientHandlerSec.bufferedWriter.write("BOTH_READY");
                            clientHandlerSec.bufferedWriter.newLine();
                            clientHandlerSec.bufferedWriter.flush();

                            //Giving first player to log first play right
                            clientHandlers.get(0).bufferedWriter.write("YOUR_TURN");
                            clientHandlers.get(0).bufferedWriter.newLine();
                            clientHandlers.get(0).bufferedWriter.flush();
                        }
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private boolean didItHit(String coordinates, boolean[][] opponentsBoard) {
        if (opponentsBoard[Integer.parseInt(String.valueOf(coordinates.charAt(1)))][Integer.parseInt(String.valueOf(coordinates.charAt(0)))]) {
            opponentsBoard[Integer.parseInt(String.valueOf(coordinates.charAt(1)))][Integer.parseInt(String.valueOf(coordinates.charAt(0)))] = false;
            return true;
        } else
            return false;
    }
    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + userName + " has left the chat");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (socket != null)
                socket.close();
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
