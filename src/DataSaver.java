import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> recs = new ArrayList<>();
        int idCounter = 1;
        boolean done = false;

        do {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter First Name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter Last Name");
            String idNumber = String.format("%06d", idCounter);
            String email = SafeInput.getNonZeroLenString(in, "Enter Email");
            int yearOfBirth = SafeInput.getRangedInt(in, "Enter Year of Birth", 1900, 2025);

            String record = String.format("%s,%s,%s,%s,%d", firstName, lastName, idNumber, email, yearOfBirth);
            recs.add(record);
            idCounter++;

            done = !SafeInput.getYNConfirm(in, "Add another record?");
        } while (!done);

        String fileName = SafeInput.getNonZeroLenString(in, "Enter the filename (without extension)");
        if (!fileName.endsWith(".csv")) {
            fileName += ".csv";
        }

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName);

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for (String rec : recs) {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("CSV file written to: " + file.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
