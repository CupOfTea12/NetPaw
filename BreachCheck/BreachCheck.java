import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BreachChecker {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Breach Check");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JLabel emailLabel = new JLabel("Email Address:");
        JTextField emailEntry = new JTextField(20);

        JButton checkButton = new JButton("Check");
        JTextArea resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailEntry.getText();
                String result = performBreachCheck(email);
                resultTextArea.setText(result);
            }
        });

        frame.add(emailLabel);
        frame.add(emailEntry);
        frame.add(checkButton);
        frame.add(resultScrollPane);

        frame.setVisible(true);
    }

    private static String performBreachCheck(String email) {
        try {
            String url = "https://haveibeenpwned.com/api/v3/breachedaccount/" + email;
            Document doc = Jsoup.connect(url).ignoreContentType(true).userAgent("Breach Check GUI App").get();
            
            if (doc.text().equals("[]")) {
                return "Your email address has not been breached.";
            } else {
                Elements breaches = doc.select("div.pwned-entry");
                StringBuilder result = new StringBuilder();
                for (Element breach : breaches) {
                    String name = breach.select("h3").text();
                    String description = breach.select("p").text();
                    String breachDate = breach.select("span[data-pwned-date]").attr("data-pwned-date");
                    String dataClasses = breach.select("span.data-classes").text();
                    
                    result.append(name).append("\n");
                    result.append(description).append("\n");
                    result.append("Breach Date: ").append(breachDate).append("\n");
                    result.append("Data Classes: ").append(dataClasses).append("\n\n");
                }
                return "Your email address has been breached in the following:\n\n" + result.toString();
            }
        } catch (IOException e) {
            return "An error occurred while checking your email address. Please try again later.";
        }
    }
}
