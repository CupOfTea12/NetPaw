import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingTool extends JFrame {

    private JTextField hostField;
    private JTextArea resultTextArea;

    public PingTool() {
        setTitle("Ping Tool");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel hostLabel = new JLabel("Host:");
        hostField = new JTextField(20);
        JButton pingButton = new JButton("Ping");
        topPanel.add(hostLabel);
        topPanel.add(hostField);
        topPanel.add(pingButton);

        resultTextArea = new JTextArea(15, 40);
        resultTextArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);

        pingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pingHost();
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(resultScrollPane, BorderLayout.CENTER);
    }

    private void pingHost() {
        String host = hostField.getText();
        String resultText = "";

        try {
            Process process = Runtime.getRuntime().exec("ping " + host);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                resultText += line + "\n";
            }

            reader.close();
        } catch (IOException e) {
            resultText = "Error: " + e.getMessage();
        }

        resultTextArea.setText(resultText);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PingTool tool = new PingTool();
                tool.setVisible(true);
            }
        });
    }
}
