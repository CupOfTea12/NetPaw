import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import org.xbill.DNS.*;

public class TraceEmailAnalyzer extends JFrame {

    private JTextField emailField;
    private JTextArea headersTextArea;
    private JTextArea dnsRecordsTextArea;

    public TraceEmailAnalyzer() {
        setTitle("Trace Email Analyzer");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel emailLabel = new JLabel("Email Address:");
        emailField = new JTextField(20);
        JButton traceButton = new JButton("Trace Email");
        topPanel.add(emailLabel);
        topPanel.add(emailField);
        topPanel.add(traceButton);

        headersTextArea = new JTextArea(10, 40);
        headersTextArea.setEditable(false);
        JScrollPane headersScrollPane = new JScrollPane(headersTextArea);

        dnsRecordsTextArea = new JTextArea(10, 40);
        dnsRecordsTextArea.setEditable(false);
        JScrollPane dnsRecordsScrollPane = new JScrollPane(dnsRecordsTextArea);

        traceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                traceEmail();
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(headersScrollPane, BorderLayout.CENTER);
        add(dnsRecordsScrollPane, BorderLayout.SOUTH);
    }

    private void traceEmail() {
        String email = emailField.getText();
        String headers = getHeaders(email);
        headersTextArea.setText(headers);
        String dnsRecords = resolveDNS(email);
        dnsRecordsTextArea.setText(dnsRecords);
    }

    private String getHeaders(String email) {
        String headers = "";
        try {
            Properties properties = new Properties();
            Session session = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(email));
            Transport transport = session.getTransport("smtp");
            transport.connect("localhost", 25, email, email);
            headers = message.toString();
            transport.close();
        } catch (Exception e) {
            headers = "Error retrieving email headers: " + e.getMessage();
        }
        return headers;
    }

    private String resolveDNS(String email) {
        String dnsRecords = "";
        try {
            String[] parts = email.split("@");
            String domainName = parts[1];
            String[] recordTypes = {"A", "MX", "NS", "TXT"};
            Resolver resolver = new SimpleResolver();
            Lookup lookup;
            for (String recordType : recordTypes) {
                lookup = new Lookup(domainName, Type.value(recordType));
                Record[] records = lookup.run();
                if (records != null) {
                    for (Record record : records) {
                        dnsRecords += recordType + ": " + record.rdataToString() + "\n";
                    }
                } else {
                    dnsRecords += recordType + ": No records found\n";
                }
            }
        } catch (Exception e) {
            dnsRecords = "Error resolving DNS records: " + e.getMessage();
        }
        return dnsRecords;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TraceEmailAnalyzer analyzer = new TraceEmailAnalyzer();
                analyzer.setVisible(true);
            }
        });
    }
}
