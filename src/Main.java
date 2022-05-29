import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {

        String unzipDir = "F://Game/savegames/";
        String zipPath = "F://Game/savegames/ZipSaves.zip";

        openZip(zipPath, unzipDir);

        String unzipDate = "F://Game/savegames/save1.dat";
        System.out.println(openProgress(unzipDate));
    }

    public static void openZip(String zipPath, String unzipDir) {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry = zipIn.getNextEntry();
            String name = entry.getName();
            try (FileOutputStream fileOut = new FileOutputStream(unzipDir + name)) {
                for (int i = zipIn.read(); i != -1; i = zipIn.read()) {
                    fileOut.write(i);
                }
                fileOut.flush();
            }
            zipIn.closeEntry();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String filePath) {
        GameProgress progress = null;
        try (FileInputStream in = new FileInputStream(filePath);
             ObjectInputStream objIn = new ObjectInputStream(in)) {
            progress = (GameProgress) objIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return progress;
    }
}
