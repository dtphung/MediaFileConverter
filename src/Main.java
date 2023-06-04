import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame {
    private JTextField inputFileField;
    private JTextField outputFileField;
    private JButton inputBrowseButton;
    private JButton outputBrowseButton;
    private JButton convertButton;
    private JProgressBar progressBar;

    public Main() {
        setTitle("Media Converter");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Input file selection
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel inputLabel = new JLabel("Input File:");
        inputFileField = new JTextField(20);
        inputBrowseButton = new JButton("Browse");
        inputBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Media Files", "media");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
                    inputFileField.setText(selectedFile);
                }
            }
        });
        inputPanel.add(inputLabel);
        inputPanel.add(inputFileField);
        inputPanel.add(inputBrowseButton);

        // Output file selection
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel outputLabel = new JLabel("Output File:");
        outputFileField = new JTextField(20);
        outputBrowseButton = new JButton("Browse");
        outputBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("MP4", "mp4");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!selectedFile.toLowerCase().endsWith(".mp4")) {
                        selectedFile += ".mp4";
                    }
                    outputFileField.setText(selectedFile);
                }
            }
        });
        outputPanel.add(outputLabel);
        outputPanel.add(outputFileField);
        outputPanel.add(outputBrowseButton);

        // Convert button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputFilePath = inputFileField.getText();
                String outputFilePath = outputFileField.getText();
                convertMedia(inputFilePath, outputFilePath);
            }
        });
        buttonPanel.add(convertButton);

        // Progress bar
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(300, 20));
        progressBar.setStringPainted(true);
        progressPanel.add(progressBar);

        // Add components to the frame
        add(inputPanel);
        add(outputPanel);
        add(new JPanel()); // Add an empty panel for spacing
        add(buttonPanel);
        add(progressPanel);
        // Add an empty panel for spacing
        add(new JPanel());

        // Set the frame to be visible
        setVisible(true);
    }

    private void convertMedia(String inputFilePath, String outputFilePath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", inputFilePath, "-c:v", "copy", "-c:a", "aac", outputFilePath);

            Process process = processBuilder.start();

            // Read the output from ffmpeg command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this, "Conversion complete!");
            } else {
                JOptionPane.showMessageDialog(this, "Conversion failed. Please check the input file and try again.");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred during conversion.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main converterGUI = new Main();
            }
        });
    }
}

