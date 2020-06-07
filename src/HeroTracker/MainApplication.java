package HeroTracker;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for main application which asks for user input and saves and reads json file.
 * The class also calls the menu interface and updates the list of superhero whenever required.
 * @author Kane
 */
public class MainApplication {

    private static List<SuperHero> superHeroList = new ArrayList<>();
    private static Menu menu = new Menu();

    /**
     * Prompts the user to enter an input
     * @return the input of the user
     */
    private static String promptUserInput() {
        Scanner commandline = new Scanner(System.in);
        String userInput = commandline.nextLine();
        return userInput;
    }

    /**
     * Allows user to choose a valid menu option
     * @return the valid option number
     */
    private static int chooseOption() {
        while (true) {
            String input = promptUserInput();
            // User enters an empty string
            if (input.equals("")) {
                System.out.println("Please enter a menu option.");
            } else { // User enters a non empty string
                // Convert string into an integer
                int number = Integer.parseInt(input);
                if (number < 1 || number > 7) {
                    System.out.println("Invalid option. Please enter a valid menu option between 1 and 7.");
                    System.out.print("Enter >> ");
                } else {
                    return number;
                }
            }
        }
    }

    /**
     * Updates the current list of superhero
     * @param heroList the list of superhero
     * @return the updated list of superheroes
     */
    private static void updateSuperHeroList(List<SuperHero> heroList) {
        if (!heroList.isEmpty()) {
            superHeroList = heroList;
        }
    }

    /**
     * Converts the arraylist of superheroes into a JSON format to create a JSON file
     */
    private static void writeToJSON() {
        //create a Gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArr = new JsonArray();

        for (SuperHero hero : superHeroList) {
            JsonObject jsonObj = new JsonObject();

            jsonObj.addProperty("name", hero.getName());
            jsonObj.addProperty("heightInCm", hero.getHeightInCm());
            jsonObj.addProperty("civilianSaveCount", hero.getCivilianSaveCount());
            jsonObj.addProperty("superPower", hero.getSuperPower());

            jsonArr.add(jsonObj);
        }

        //Create a JSON string format to store the JsonArray
        String json = gson.toJson(jsonArr);

        try {
            FileWriter writer = new FileWriter("./SuperHeroList.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Errors in writing to a file.");
        }
    }

    /**
     * Reads from the named JSON file in the current folder
     * Stores the values in the JSON file in an arraylist
     */
    private static void readFromJSON() {
        File fileInput = new File("./SuperHeroList.json");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(fileInput));
            // Creates a json array
            JsonArray jsonHeroList = fileElement.getAsJsonArray();

            for (JsonElement heroElement : jsonHeroList) {
                // Converts the json element into json object
                JsonObject heroObject = heroElement.getAsJsonObject();

                String name = null;
                // Checks if the json object has a member called "name"
                // if yes, gets its associated value
                if (heroObject.has("name")) {
                    name = heroObject.get("name").getAsString();
                }

                double heightInCm = 0;
                // Checks if the json object has a member called "heightInCm"
                // if yes, gets its associated value
                if (heroObject.has("heightInCm")) {
                    heightInCm = heroObject.get("heightInCm").getAsDouble();
                }

                int civilianSaveCount = 0;
                // Checks if the json object has a member called "civilianSaveCount"
                // if yes, gets its associated value
                if (heroObject.has("civilianSaveCount")) {
                    civilianSaveCount = heroObject.get("civilianSaveCount").getAsInt();
                }

                String superPower = null;
                // Checks if the json object has a member called "superPower"
                // if yes, gets its associated value
                if (heroObject.has("superPower")) {
                    superPower = heroObject.get("superPower").getAsString();
                }

                SuperHero hero = new SuperHero(name, heightInCm, civilianSaveCount, superPower);
                superHeroList.add(hero);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error input File not found");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error processing input file");
            e.printStackTrace();
        }
    }

    /**
     * Searches for any JSON file and call readFromJSON() method
     */
    private static void readJsonFile() {
        String currentDirectory = System.getProperty("user.dir");
        File dir = new File(currentDirectory);
        // Go through all the file in the current directory
        // Gets any Json file present
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith((".json"))) {
                readFromJSON();
                break;
            }
        }
    }

    /**
     * Asks user to enter hero name
     * @return superhero's name entered by user
     */
    private static String getHeroName() {
        System.out.print("Enter Hero's name: ");
        return promptUserInput();
    }

    /**
     * Asks user to enter a height in cm until we get a valid positive height
     * @return valid superhero's height in cm entered by user
     */
    private static double getHeroHeight() {
        String heightString = printHeightPrompt();
        double height;
        // User has entered empty string
        if (heightString.equals("")) {
            height = 1.0;
        } else {
            height = Double.parseDouble(heightString);
        }
        // Entered height is invalid
        // Needs to get a valid height
        if (!hasValidHeight(height)) {
            double newHeight = getValidHeightInput(height);
            return newHeight;
        } else {
            return height;
        }
    }

    /**
     * Asks user to enter a super power for superhero
     * @return superhero's super power entered by user
     */
    private static String getHeroSuperPower() {
        System.out.print("Enter Hero's Superpower: ");
        return promptUserInput();
    }

    /**
     * Called only when the user enters an invalid height originally
     * Asks user to enter a valid height in cm of the superhero
     * If still invalid, ask the user to enter again till he enters a valid height
     * @param height value of height in cm of the superhero
     * @return valid height entered by the user
     */
    private static double getValidHeightInput(double height) {
        while (true) {
            System.out.println("Invalid height. Valid height value must be positive.");
            String heightPrompt = printHeightPrompt();
            // User has entered a value for height
            // Checks and gets a valid height
            if (!heightPrompt.equals("")) {
                double newHeight = Double.parseDouble(heightPrompt);
                boolean isValidHeight = hasValidHeight(newHeight);
                if (isValidHeight) {
                    return newHeight;
                }
            }
        }
    }

    /**
     * Checks if the input height is valid
     * @param height value of the height in cm of the superhero
     * @return true if valid and false if invalid height
     */
    private static boolean hasValidHeight(double height) {
        if (height <= 0) { // Invalid height
            return false;
        } else {
            return true;
        }
    }

    /**
     * Prompts user to enter the height in cm of the user
     * @return height entered which can be either valid or invalid
     */
    private static String printHeightPrompt() {
        System.out.print("Enter Hero's height in cm: ");
        String height = promptUserInput();
        return height;
    }

    /**
     * Prints the correct input message depending on the menu option number chosen by user
     * @param menuOptionNumber number of menu option chosen by user
     */
    private static void printHeroNumberPrompt(int menuOptionNumber) {
        if (menuOptionNumber == 3) {
            System.out.println("\nEnter Hero number to be removed or Enter 0 to cancel");
        } else if (menuOptionNumber == 4) {
            System.out.println("\nEnter Hero number to update civilian count or Enter 0 to cancel");
        }
        System.out.print("Enter >> ");
    }

    /**
     * Gets a valid hero number chosen by the user
     * If invalid, ask the user to enter a valid hero number
     * @param menuOptionNumber valid number of menu option chosen by the user
     * @return valid hero number
     */
    private static int getValidHeroNumberInput(int menuOptionNumber) {
        menu.printHeroList(superHeroList);
        int size = superHeroList.size();
        while (true) {
            printHeroNumberPrompt(menuOptionNumber);
            String heroOption = promptUserInput();
            // User has entered a hero number option
            // Gets a valid hero Number
            if (!heroOption.equals("")) {
                int heroNumber = Integer.parseInt(heroOption);
                if ((heroNumber > 0) && (heroNumber <= size)) { // hero number exists
                    return heroNumber;
                } else if ((heroNumber < 0) || (heroNumber > size)) { // hero number out of range
                    System.out.print("Invalid hero number. " + "Hero number must be a positive integer " +
                            "less than or equal to " + superHeroList.size() + ".");
                } else if (heroNumber == 0) {
                    break;
                }
            }
        }
        // User enters 0 for hero number which will result in Cancel
        return 0;
    }

    /**
     * Prompts user to enter the height in cm of the user
     * @return civilian save count entered which can be valid or invalid
     */
    private static String printCivilianSaveCountPrompt() {
        System.out.print("Enter new civilian save count: ");
        String count = promptUserInput();
        return count;
    }

    /**
     * Gets a valid civilian save count for the superhero
     * If invalid, ask the user to enter a valid civilian save count
     * @return valid civilian save count
     */
    private static int getValidCivilianCount(int currentCivilianSaveCount) {
        while (true) {
            String count = printCivilianSaveCountPrompt();
            // User has entered a value for number of civilians saved
            if (!count.equals("")) {
                int civilianSaveCount = Integer.parseInt(count);
                if (civilianSaveCount < 0) {
                    System.out.println("Invalid update. Update to civilian save count needs to be positive.");
                } else {
                    return civilianSaveCount;
                }
            }
        }
    }

    /**
     * Creates a copy of the superHeroList
     * so that any changes to the copied list will not cause changes to the superHeroList
     * @return a new copied list of the superHeroList
     */
    private static List<SuperHero> copyHeroList() {
        List<SuperHero> heroList = new ArrayList<>();
        for (SuperHero hero : superHeroList) {
            // Add new similar superHero objects into the heroList
            SuperHero clonedHero = (SuperHero) hero.clone();
            heroList.add(clonedHero);
        }
        return heroList;
    }

    /**
     * Main function for calling all the required methods
     */
    public static void main(String args[]) {
        // Fills in the superHeroList if any json file exists
        readJsonFile();

        // Automatically displays the menu interface
        // Until user chooses option 7 to quit
        while (true) {
            // Displays the menu interface
            menu.print();
            System.out.print("Enter >> ");
            // Gets the menu option chosen
            int number = chooseOption();
            // Creates a copy of the superHeroList
            List<SuperHero> heroList = copyHeroList();

            // Do the necessary tasks depending on the menu number chosen by user
            if (number == 1) {
                menu.listAllHeroFirstOption(superHeroList);
            } else if (number == 2) {
                String name = getHeroName();
                double height = getHeroHeight();
                String superPower = getHeroSuperPower();
                List<SuperHero> newHeroList = menu.addHeroSecondOption(heroList, name, height, superPower);
                updateSuperHeroList(newHeroList);
            } else if (number == 3) {
                if (superHeroList.size() != 0) {
                    // Gets a valid hero number entered
                    int heroNumber = getValidHeroNumberInput(3);
                    List<SuperHero> newHeroList = menu.removeHeroThirdOption(heroList, heroNumber);
                    System.out.print("\n");
                    superHeroList = newHeroList;
                } else {
                    System.out.println("List of superheroes is still empty. Add more before deleting. \n");
                }
            } else if (number == 4) {
                if (heroList.size() != 0) {
                    // Gets a valid hero number entered
                    int heroNumber = getValidHeroNumberInput(4);
                    // User has not cancelled the menu option 4
                    if (heroNumber != 0) {
                        SuperHero hero = superHeroList.get(heroNumber - 1);
                        int currentCivilianSaveCount = hero.getCivilianSaveCount();
                        int civilianSaveCount = getValidCivilianCount(currentCivilianSaveCount);
                        List<SuperHero> newHeroList = menu.updateSaveCountFourthOption(heroList, heroNumber, civilianSaveCount);
                        updateSuperHeroList(newHeroList);
                    }
                    System.out.print("\n");
                } else {
                    System.out.println("List of superheroes is still empty. Add more before updating. \n");
                }
            } else if (number == 5) {
                menu.getTopThreeHeroFifthOption(heroList);
            } else if (number == 6) {
                menu.debugDumpSixthOption(superHeroList);
            } else if (number == 7) {
                menu.quitSeventhOption();
                writeToJSON();
                break;
            }
        }
    }

}//MainApplication.java

