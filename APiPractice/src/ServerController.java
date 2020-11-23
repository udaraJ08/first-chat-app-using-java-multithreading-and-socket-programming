import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    private static String clientMsg = "";
    private static final String allMessage = "";
    public JFXTextArea txtArFace;
    public TextField txtTyper;
    public JFXButton btnSend;
    public JFXTextArea txtArFaceClient;
    private Socket skt = null;

    public void initialize() {

        System.out.println("Server is waiting...");
        Thread t1 = new Thread(() -> {
            try {
                ServerSocket srvSkt = new ServerSocket(9090);
                while (true) {
                    skt = srvSkt.accept();
                    BufferedReader sntBr = new BufferedReader(new InputStreamReader(skt.getInputStream()));
                    clientMsg += sntBr.readLine() + "\n\n";
                    txtArFaceClient.setText(clientMsg);
                }
            } catch (Exception er) {
                er.printStackTrace();
            }
        });
        t1.start();
    }

    private void sendMessage() throws IOException {

        String message = txtTyper.getText();
        String currentMessage = txtArFace.getText();

        currentMessage += message + "\n\n";
        txtArFace.setText(currentMessage);
        txtTyper.setText("");
        PrintWriter writer = new PrintWriter(skt.getOutputStream(), true);
        writer.println(message);

        System.out.println(skt.toString());
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {

        sendMessage();
    }

    public void txtOnAction(ActionEvent actionEvent) throws IOException {

        sendMessage();
    }
}
