import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class ReverseDNSLookupTool extends JFrame {

    private JTextField ipField;
    private JLabel resultLabel;

    public ReverseDNSLookupTool() {
        setTitle("Reverse DNS Lookup");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel ipLabel = new JLabel("IP Address:");
        ipField = new JTextField(20);
        JButton lookupButton = new JButton("Lookup");
        topPanel.add(ipLabel);
        topPanel.add(ipField);
        topPanel.add(lookupButton);

        resultLabel = new JLabel("");
        resultLabel.setHorizontalAlignment(JLabel.CENTER);

        lookupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performReverseDNSLookup();
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);
    }

    private void performReverseDNSLookup() {
        String ipAddress = ipField.getText();
        String resultText = "";

        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            String hostname = inetAddress.getCanonicalHostName();
            resultText = "Hostname: " + hostname;
        } catch (Exception e) {
            resultText = "No hostname found";
        }

        resultLabel.setText(resultText);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ReverseDNSLookupTool tool = new ReverseDNSLookupTool();
                tool.setVisible(true);
            }
        });
    }
}
