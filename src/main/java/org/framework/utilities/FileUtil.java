package org.framework.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileUtil {

    private FileUtil(){

    }

    private static final Logger logger = Logger.getLogger(FileUtil.class.getName());

    public static String getTempDirecotryPath() {
        return null;
    }


    // Method to create a new file
    public static void createFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Method to create a new directory
    public static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directory.getName());
            } else {
                System.out.println("Failed to create directory.");
            }
        } else {
            System.out.println("Directory already exists.");
        }
    }

    // Method to rename a file or directory
    public static void rename(String oldName, String newName) {
        File oldFile = new File(oldName);
        File newFile = new File(newName);
        if (oldFile.exists()) {
            if (oldFile.renameTo(newFile)) {
                System.out.println("Renamed successfully.");
            } else {
                System.out.println("Failed to rename.");
            }
        } else {
            System.out.println("File or directory doesn't exist.");
        }
    }

    // Method to delete a file or directory
    public static void delete(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Deleted successfully.");
            } else {
                System.out.println("Failed to delete.");
            }
        } else {
            System.out.println("File or directory doesn't exist.");
        }
    }

    // Method to copy a file
    public static void copyFile(String sourcePath, String destinationPath) {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);
        try {
            Files.copy(source, destination);
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.out.println("Failed to copy file.");
            e.printStackTrace();
        }
    }

    // Method to move a file
    public static void moveFile(String sourcePath, String destinationPath) {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);
        try {
            Files.move(source, destination);
            System.out.println("File moved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to move file.");
            e.printStackTrace();
        }
    }

    // Method to list files and directories in a directory
    public static void listFiles(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("Not a directory.");
        }
    }


    // Method to create a new file
    public static void cleanDirectory(File filePath) {
        // Need to Implement
    }

}
