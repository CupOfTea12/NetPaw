import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class IPTracker extends JFrame {

    private JTextField ipField;
    private JTextArea resultTextArea;

    public IPTracker() {
        setTitle("IP Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel ipLabel = new JLabel("IP Address:");
        ipField = new JTextField(20);
        JButton trackButton = new JButton("Track IP");
        topPanel.add(ipLabel);
        topPanel.add(ipField);
        topPanel.add(trackButton);

        resultTextArea = new JTextArea(15, 40);
        resultTextArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);

        trackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trackIP();
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(resultScrollPane, BorderLayout.CENTER);
    }

    private void trackIP() {
        String ipAddress = ipField.getText();
        String resultText = "";

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("https://rdap.arin.net/registry/ip/" + ipAddress);

            CloseableHttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(json);

                for (Map.Entry<String, Object> entry : jsonObject.toMap().entrySet()) {
                    resultText += entry.getKey() + ": " + entry.getValue() + "\n";
                }
            } else {
                resultText = "Error: Unable to fetch IP tracking information";
            }
        } catch (IOException | JSONException e) {
            resultText = "Error: " + e.getMessage();
        }

        resultTextArea.setText(resultText);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                IPTracker tracker = new IPTracker();
                tracker.setVisible(true);
            }
        });
    }
}
