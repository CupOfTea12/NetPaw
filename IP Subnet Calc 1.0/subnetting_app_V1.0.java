import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubnettingApp extends JFrame {

    private JTextField ipField;
    private JTextField subnetMaskField;
    private JLabel resultLabel;

    public SubnettingApp() {
        setTitle("Subnetting App");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        JLabel ipLabel = new JLabel("IP Address:");
        ipField = new JTextField(20);
        JLabel subnetMaskLabel = new JLabel("Subnet Mask:");
        subnetMaskField = new JTextField(20);

        topPanel.add(ipLabel);
        topPanel.add(ipField);
        topPanel.add(subnetMaskLabel);
        topPanel.add(subnetMaskField);

        JPanel bottomPanel = new JPanel();
        JButton calculateButton = new JButton("Calculate");
        resultLabel = new JLabel("");
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateSubnet();
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }

    private void calculateSubnet() {
        String ipAddress = ipField.getText();
        String subnetMask = subnetMaskField.getText();

        try {
            String[] ipParts = ipAddress.split("\\.");
            String[] maskParts = subnetMask.split("\\.");

            if (ipParts.length != 4 || maskParts.length != 4) {
                throw new IllegalArgumentException("Invalid IP address or subnet mask");
            }

            StringBuilder networkAddress = new StringBuilder();
            StringBuilder broadcastAddress = new StringBuilder();

            for (int i = 0; i < 4; i++) {
                int ipPart = Integer.parseInt(ipParts[i]);
                int maskPart = Integer.parseInt(maskParts[i]);

                if (maskPart < 0 || maskPart > 255) {
                    throw new IllegalArgumentException("Invalid subnet mask");
                }

                networkAddress.append(ipPart & maskPart);
                broadcastAddress.append(ipPart | (~maskPart & 0xFF));

                if (i < 3) {
                    networkAddress.append(".");
                    broadcastAddress.append(".");
                }
            }

            resultLabel.setText("Network Address: " + networkAddress.toString() + "\nBroadcast Address: " + broadcastAddress.toString());
        } catch (NumberFormatException | IllegalArgumentException ex) {
            resultLabel.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SubnettingApp app = new SubnettingApp();
                app.setVisible(true);
            }
        });
    }
}
