import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSelectorPanel extends JPanel {
    private JTextField fileField;
    private JButton browseButton;

    public FileSelectorPanel(String label, String buttonText) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel fileLabel = new JLabel(label);
        fileField = new JTextField(20);
        browseButton = new JButton(buttonText);
        browseButton.addActionListener(new BrowseButtonListener());

        add(fileLabel);
        add(fileField);
        add(browseButton);
    }

    public String getSelectedFilePath() {
        return fileField.getText();
    }

    private class BrowseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("MP4 Files", "mp4");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showDialog(null, "Select File");
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
                fileField.setText(selectedFile);
            }
        }
    }
}
