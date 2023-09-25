import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

interface FileOperation {
    void execute();
}

class ListFilesOperation implements FileOperation {
    private final String directoryPath;

    public ListFilesOperation(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void execute() {
        File rootDir = new File(directoryPath);
        if (rootDir.exists() && rootDir.isDirectory()) {
            String[] files = rootDir.list();
            if (files != null) {
                List<String> fileList = new ArrayList<>();
                Collections.addAll(fileList, files);
                Collections.sort(fileList);
                System.out.println("\n\033[34mFiles in Ascending Order:\033[0m");
                for (String file : fileList) {
                    System.out.println(file);
                }
            } else {
                System.out.println("\n\033[31mNo files found in the directory.\033[0m");
            }
        } else {
            System.out.println("\n\033[31mError:\033[0m Root directory does not exist or is not a directory.");
        }
    }
}

class AddFileOperation implements FileOperation {
    private final String directoryPath;
    private final String fileName;

    public AddFileOperation(String directoryPath, String fileName) {
        this.directoryPath = directoryPath;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        File file = new File(directoryPath, fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("\n\033[32mFile added successfully👍.\033[0m");
            } else {
                System.out.println("\n\033[31mFile already exists.\033[0m");
            }
        } catch (IOException e) {
            System.out.println("\n\033[31mAn error occurred while adding the file.\033[0m");
        }
    }
}

class DeleteFileOperation implements FileOperation {
    private final String directoryPath;
    private final String fileName;

    public DeleteFileOperation(String directoryPath, String fileName) {
        this.directoryPath = directoryPath;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        File file = new File(directoryPath, fileName);

        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("\n\033[32mFile deleted successfully👍.\033[0m");
            } else {
                System.out.println("\n\033[31mUnable to delete the file.\033[0m");
            }
        } else {
            System.out.println("\n\033[31mFile not found.\033[0m");
        }
    }
}

class SearchFileOperation implements FileOperation {
    private final String directoryPath;
    private final String fileName;

    public SearchFileOperation(String directoryPath, String fileName) {
        this.directoryPath = directoryPath;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        File file = new File(directoryPath, fileName);

        if (file.exists()) {
            System.out.println("\n\033[32mFile found: " + file.getAbsolutePath() + "\033[0m");
        } else {
            System.out.println("\n\033[31mFile not found.\033[0m");
        }
    }
}

public class LockedMeApp {
	
    private static final String directoryPath = "C:";//Setting the working directory 
    private static String fileName;
    private static final String homePage =
            "\033[36m=============================================\n" +
            "       Welcome to LockedMe.com😁!\n" +
            "=============================================\033[0m\n" +
            "Developer: Elelwani Magoba\n";

    public static void main(String[] args) {
        System.out.println(homePage);
        while (true) {
            displayMainMenu();
            int choice = getUserChoice();
            if (choice == -1) {
                continue;
            }

            switch (choice) {
                case 1:
                    FileOperation listFilesOperation = new ListFilesOperation(directoryPath);
                    listFilesOperation.execute();
                    break;
                case 2:
                    handleFileOperations();
                    break;
                case 3:
                    System.out.println("\n\033[36mExiting LockedMe.com. Goodbye😊!\033[0m");
                    System.exit(0);
                default:
                    System.out.println("\n\033[31mInvalid choice. Please select a valid option.\033[0m");
                    break;
            }
            System.out.println("\n=================================================================================");
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n\033[33mMain Menu:\033[0m");
        System.out.println("\033[34m1. List Files (Ascending Order)\033[0m");
        System.out.println("\033[34m2. Add/Delete/Search Files\033[0m");
        System.out.println("\033[34m3. Exit\033[0m");
    }

    private static int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        try {
            System.out.print("\n\033[32mEnter your choice: \033[0m");
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("\n\033[31mInvalid input. Please enter a valid choice.\033[0m");
            choice = -1;//Given that the user inserts a non integer value, the choice will be set to -1
            			//This was done to avoid the select statement to be executed.
        }

        return choice;
    }

    private static String getFileName() {
        Scanner scanner = new Scanner(System.in);
        String fileName = "";

        try {
            System.out.print("\033[32mEnter the file name: \033[0m");
            fileName = scanner.nextLine();
        } catch (Exception e) {
            System.out.println("\033[31mInvalid input. Please enter a valid file name.\033[0m");
        }

        return fileName;
    }

    private static void handleFileOperations() {
        while (true) {
            System.out.println("\n\033[33mFile Operations Menu:\033[0m");
            System.out.println("\033[34m1. Add a File\033[0m");
            System.out.println("\033[34m2. Delete a File\033[0m");
            System.out.println("\033[34m3. Search for a File\033[0m");
            System.out.println("\033[34m4. Back to Main Menu\033[0m");

            int operationChoice = getUserChoice();

            if (operationChoice == -1) {
                continue;
            }

            switch (operationChoice) {
                case 1:
                    fileName = getFileName();
                    FileOperation addFileOperation = new AddFileOperation(directoryPath, fileName);
                    addFileOperation.execute();
                    break;
                case 2:
                    fileName = getFileName();
                    FileOperation deleteFileOperation = new DeleteFileOperation(directoryPath, fileName);
                    deleteFileOperation.execute();
                    break;
                case 3:
                    fileName = getFileName();
                    FileOperation searchFileOperation = new SearchFileOperation(directoryPath, fileName);
                    searchFileOperation.execute();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("\n\033[31mInvalid choice. Please select a valid option.\033[0m");
            }
            System.out.println("\n=================================================================================");
        }
    }
}
