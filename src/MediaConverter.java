import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MediaConverter {
    private JProgressBar progressBar;

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void convertMedia(String inputFilePath, String outputFilePath) {
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
                JOptionPane.showMessageDialog(null, "Conversion complete!");
            } else {
                JOptionPane.showMessageDialog(null, "Conversion failed. Please check the input file and try again.");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred during conversion.");
        }
    }
}

