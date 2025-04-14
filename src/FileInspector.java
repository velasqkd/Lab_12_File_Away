import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while (reader.ready()) {
                    rec = reader.readLine();
                    lineCount++;

                    if (!rec.trim().isEmpty()) {
                        String[] words = rec.trim().split("\\s+");
                        wordCount += words.length;
                    }

                    charCount += rec.length();

                    System.out.printf("\nLine %4d %-60s", lineCount, rec);
                }
                reader.close();

                System.out.println("\n\nData file read!\n");
                System.out.println("Summary Report:");
                System.out.println("File Name: " + selectedFile.getName());
                System.out.println("Number of Lines: " + lineCount);
                System.out.println("Number of Words: " + wordCount);
                System.out.println("Number of Characters: " + charCount);

            } else {
                System.out.println("No file selected!!! ... exiting.\nRun the program again and select a file.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
