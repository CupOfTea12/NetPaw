import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class IPBlacklistCheck extends JFrame {

    private JTextField ipField;
    private JLabel statusLabel;

    public IPBlacklistCheck() {
        setTitle("IP Blacklist Check");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel ipLabel = new JLabel("IP Address:");
        ipField = new JTextField(20);
        JButton checkButton = new JButton("Check");
        topPanel.add(ipLabel);
        topPanel.add(ipField);
        topPanel.add(checkButton);

        statusLabel = new JLabel("");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBlacklist();
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.CENTER);
    }

    private void checkBlacklist() {
        String ipAddress = ipField.getText();
        String statusText = "";

        try {
            Document document = Jsoup.connect("https://www.ipvoid.com/ip-blacklist-check/")
                    .data("ip", ipAddress)
                    .post();

            List<String> blacklists = new ArrayList<>();

            Element resultTable = document.selectFirst("table");
            if (resultTable != null) {
                for (Element row : resultTable.select("tr")) {
                    Elements columns = row.select("td");
                    if (columns.size() >= 2) {
                        String blacklistName = columns.get(0).text();
                        String blacklistStatus = columns.get(1).text();
                        if (blacklistStatus.equals("Listed")) {
                            blacklists.add(blacklistName);
                        }
                    }
                }
            }

            if (!blacklists.isEmpty()) {
                statusText = "Blacklisted on " + blacklists.size() + " lists: " + String.join(", ", blacklists);
            } else {
                statusText = "Not blacklisted";
            }
        } catch (IOException e) {
            statusText = "Error checking IP blacklist status: " + e.getMessage();
        }

        statusLabel.setText(statusText);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                IPBlacklistCheck checker = new IPBlacklistCheck();
                checker.setVisible(true);
            }
        });
    }
}
