import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Log {
    private JButton exitButton;
    private JPanel panel;
    private JTextArea textArea1;

    Log() {
        class CustomOutputStream extends OutputStream {
            @Override
            public void write(int b) throws IOException {
                textArea1.append(String.valueOf((char)b));
                textArea1.setCaretPosition(textArea1.getDocument().getLength());
            }
        }
        System.setOut(new PrintStream(new CustomOutputStream()));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
