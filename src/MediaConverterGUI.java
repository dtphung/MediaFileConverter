import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MediaConverterGUI extends JFrame {
    private FileSelectorPanel inputPanel;
    private FileSelectorPanel outputPanel;
    private JButton convertButton;
    private JProgressBar progressBar;

    public MediaConverterGUI() {
        setTitle("Media Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));

        inputPanel = new FileSelectorPanel("Input File:", "Browse");
        outputPanel = new FileSelectorPanel("Output File:", "Browse");

        convertButton = new JButton("Convert");
        convertButton.addActionListener(new ConvertButtonListener());

        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(300, 20));
        progressBar.setStringPainted(true);

        add(inputPanel);
        add(outputPanel);
        add(new JPanel());
        add(createButtonPanel());
        add(createProgressPanel());
        add(new JPanel());
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(convertButton);
        return panel;
    }

    private JPanel createProgressPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(progressBar);
        return panel;
    }

    private class ConvertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputFilePath = inputPanel.getSelectedFilePath();
            String outputFilePath = outputPanel.getSelectedFilePath();

            // Create an instance of the MediaConverter class
            MediaConverter mediaConverter = new MediaConverter();

            // Set the progress bar in the MediaConverterGUI
            mediaConverter.setProgressBar(progressBar);

            // Start the conversion process
            mediaConverter.convertMedia(inputFilePath, outputFilePath);
        }
    }
}
