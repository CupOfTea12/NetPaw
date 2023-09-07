import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ProxyChecker {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Advanced Proxy Check");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new FlowLayout());

        JLabel proxyLabel = new JLabel("Proxy:");
        JTextField proxyEntry = new JTextField(20);

        JButton checkButton = new JButton("Check");
        JLabel resultLabel = new JLabel();

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String proxy = proxyEntry.getText();
                String result = checkProxy(proxy);
                resultLabel.setText(result);
            }
        });

        frame.add(proxyLabel);
        frame.add(proxyEntry);
        frame.add(checkButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }

    private static String checkProxy(String proxy) {
        try {
            String url = "https://www.ipvoid.com/proxy-check/" + proxy + "/";
            Document doc = Jsoup.connect(url).get();
            Element resultElement = doc.select("div.panel-body").get(1);
            return resultElement.text().trim();
        } catch (IOException e) {
            return "Error checking proxy.";
        }
    }
}
