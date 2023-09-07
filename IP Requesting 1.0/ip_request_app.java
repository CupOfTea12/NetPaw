import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class IpRequestApp extends JFrame {

    private JTextField ipField;
    private JLabel resultLabel;

    public IpRequestApp() {
        setTitle("IP Tools - Requesting");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel ipLabel = new JLabel("Enter IP Address:");
        ipField = new JTextField(20);
        JButton requestButton = new JButton("Request");
        topPanel.add(ipLabel);
        topPanel.add(ipField);
        topPanel.add(requestButton);

        resultLabel = new JLabel("");
        resultLabel.setHorizontalAlignment(JLabel.CENTER);

        requestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                requestIp();
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);
    }

    private void requestIp() {
        String ipAddress = ipField.getText();
        String resultText = "";

        try {
            Document document = Jsoup.connect("https://api.ipify.org/?format=json&ipAddress=" + ipAddress).get();
            Element element = document.selectFirst("pre");

            if (element != null) {
                String jsonText = element.text();
                resultText = "Your IP address is: " + jsonText;
            } else {
                resultText = "Error: Invalid response from API";
            }
        } catch (IOException e) {
            resultText = "Error: " + e.getMessage();
        }

        resultLabel.setText(resultText);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                IpRequestApp app = new IpRequestApp();
                app.setVisible(true);
            }
        });
    }
}
