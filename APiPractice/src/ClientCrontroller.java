import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCrontroller {

    private static String allMessage = "";
    private static String clientSide = "";
    public JFXTextArea txtArFace;
    public TextField txtTyper;
    public JFXButton btnSend;
    public JFXTextArea txtArFaceClient;
    private Socket skt = null;

    public void initialize() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println("hello");

            try {
                while (true) {
                    System.out.println("debug1");
                    skt = new Socket("localhost", 9090);
                    BufferedReader br = new BufferedReader(new InputStreamReader(skt.getInputStream()));
                    clientSide += br.readLine() + "\n\n";
                    txtArFaceClient.setText(clientSide);
                    System.out.println("debug2");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t1.start();
    }

    public void sendMessage() throws IOException {

        String message = txtTyper.getText();
        String currentMessage = txtArFace.getText();

        currentMessage += message +"\n\n";
        txtArFace.setText(currentMessage);
        txtTyper.setText("");
        PrintWriter prt = new PrintWriter(skt.getOutputStream(), true);
        prt.println(message);
        System.out.println(message);

        System.out.println("finished");
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {

        sendMessage();
    }

    public void txtOnAction() throws IOException {

        sendMessage();
    }
}
