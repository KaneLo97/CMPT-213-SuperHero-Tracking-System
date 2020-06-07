package HeroTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for printing the menu and implementing the menu options of the application.
 * @author Kane
 */
public class Menu {

    private String title;
    private String[] optionList;
    private final int NUM_OPTIONS = 7;

    /**
     * Constructor of Menu
     */
    public Menu() {
        this.title = "SuperHero Tracker";
        this.optionList = new String[]{"List all superheroes", "Add a new superhero", "Remove a superhero",
                                       "Update number of civilians saved by a superhero", "List Top 3 superheroes",
                                       "Debug Dump (toString)", "Exit"};
    }

    /**
     * Prints the menu options, title and stars around the title
     */
    public void print() {
        printStars();
        System.out.println(" " + title);
        printStars();

        for (int i = 1; i <= NUM_OPTIONS; i++) {
            System.out.println(i + ": " + optionList[i - 1]);
        }
    }

    /**
     * Prints the stars surrounding the menu title
     */
    private void printStars() {
        System.out.print("*");
        for (int i = 0; i < title.length(); i++) {
            System.out.print("*");
        }
        System.out.print("*");
        System.out.print("*");
        System.out.print("*");
        System.out.println("*");
    }

    /**
     * Menu first option printing all the superheroes in the list of superheroes
     * @param superHeroList current list of superheroes
     */
    public void listAllHeroFirstOption(List<SuperHero> superHeroList) {
        if (superHeroList.size() != 0) {
            printHeroList(superHeroList);
            System.out.print("\n");
        } else {
            System.out.println("No heroes present in the list. Add more..\n");
        }
    }

    /**
     * Menu second option to create and add the superhero with name,heightInCm and superpower to the superhero list
     * @param superHeroList list of current superheroes
     * @param name name of a superhero
     * @param height height in cm of a superhero
     * @param superPower superPower of a superPower
     * @return updated list of superheroes with new superhero added
     */
    public List<SuperHero> addHeroSecondOption(List<SuperHero> superHeroList, String name, double height, String superPower) {
        SuperHero hero = new SuperHero();

        hero.setName(name);
        hero.setHeightInCm(height);
        hero.setSuperPower(superPower);

        superHeroList.add(hero);
        System.out.println(hero.getName() + " has been added to the list of superheroes.\n");

        return superHeroList;
    }

    /**
     * Menu third option to remove a superhero from the list
     * @param superHeroList current list of superheroes
     * @param heroNumber hero option number in the list
     * @return updated list of superheroes after removing a superhero
     */
    public List<SuperHero> removeHeroThirdOption(List<SuperHero> superHeroList, int heroNumber) {
        return removeSuperhero(superHeroList, heroNumber);
    }

    /**
     * Menu fourth option to update the number of civilians saved by a superhero
     * @param heroList current list of superheroes
     * @param heroNumber hero option number in the list
     * @param civilianSaveCount number of civilians saved by the superHero with heroNumber
     * @return list of superheroes after updating the number of saved civilians
     */
    public List<SuperHero> updateSaveCountFourthOption(List<SuperHero> heroList, int heroNumber, int civilianSaveCount) {
        return updateCivilianCount(heroList, heroNumber, civilianSaveCount);
    }

    /**
     * Menu fifth option to get the top 3 superheroes with the largest number of civilians saved
     * @param heroList current list of superheroes
     */
    public void getTopThreeHeroFifthOption(List<SuperHero> heroList) {
        // The list has less than 3 superheroes
        if (heroList.size() < 3) {
            System.out.println("There is not enough superheroes in the list. Please add more.\n");
        } else {
            // Checks if there are at least 3 superheroes saving at least 1 civilian
            if (hasEnoughValidHero(heroList)) {
                // Sorts the superheroes present in the heroList
                List<SuperHero> superHeroList = sortList(heroList);
                // Displays the number of civilians saved by the top 3 superheroes
                for (int i = 1; i <= 3; i++) {
                    SuperHero hero = superHeroList.get(i - 1);
                    System.out.println(i + ". " + hero.getName() + " has saved " + hero.getCivilianSaveCount() + " civilians");
                }
                System.out.println();
            } else {
                System.out.println("The superheroes have not saved enough civilians.\n");
            }
        }
    }

    /**
     * Menu sixth option to print and debug the details of all the superheroes in the list
     * @param superHeroList current list of superheroes
     */
    public void debugDumpSixthOption(List<SuperHero> superHeroList) {
        if (superHeroList.size() != 0) {
            for (int i = 1; i <= superHeroList.size(); i++) {
                System.out.print(i + ". ");
                String str = superHeroList.get(i - 1).toString();
                System.out.println(str);
            }
            System.out.println();
        } else {
            System.out.println("No superheroes to show. Add more. \n");
        }
    }

    /**
     * Menu seventh option printing a thank you message
     */
    public void quitSeventhOption() {
        System.out.print("Thank you for using the system.");
    }

    /**
     * Prints the details of all superheroes in the current list
     * @param superHeroList current list of superheroes
     */
    public void printHeroList(List<SuperHero> superHeroList) {
        for (int i = 1; i <= superHeroList.size(); i++) {
            SuperHero hero = superHeroList.get(i - 1);
            System.out.println(i + ". Hero name: " + hero.getName() + ", height: " + hero.getHeightInCm() + "cm, superpower: "
                               + hero.getSuperPower() + ", saved " + hero.getCivilianSaveCount() + " civilians.");
        }
    }

    /**
     * Removes the superhero from the given list based on the hero number chosen by user
     * @param superHeroList current list of superheroes
     * @param heroNumber hero number chosen by user
     * @return list of superheroes based on valid hero number or number 0
     */
    private List<SuperHero> removeSuperhero(List<SuperHero> superHeroList, int heroNumber) {
        //hero number is either 0 or positive
        if (heroNumber != 0) { // Valid hero number
            SuperHero hero = superHeroList.get(heroNumber - 1);
            // Deletes the chosen superhero from the list
            superHeroList.remove(heroNumber - 1);

            System.out.println(hero.getName() + " has been removed from the list of superheroes.");
        }
        return superHeroList;
    }

    /**
     * Updates the count of civilians saved by a superhero based on hero number chosen by user
     * @param superHeroList current list of superheroes
     * @param heroNumber hero number chosen by user
     * @param civilianSaveCount number of civilians saved by superhero with heroNumber
     * @return list of superheroes with updated superhero
     */
    private List<SuperHero> updateCivilianCount(List<SuperHero> superHeroList, int heroNumber, int civilianSaveCount) {
        SuperHero hero = superHeroList.get(heroNumber - 1);
        // Saves the original number of civilians saved by the hero
        int originalCivilianSaveCount = hero.getCivilianSaveCount();
        // Changes the number of the civilians saved by the chosen superhero
        superHeroList.get(heroNumber - 1).setCivilianSaveCount(civilianSaveCount);
        System.out.println("Number of civilians saved by " + hero.getName() + " has been updated from " +
                            originalCivilianSaveCount + " to " + hero.getCivilianSaveCount() + ".");
        return superHeroList;
    }

    /**
     * Checks if the superhero list contains at least 3 superheroes that have saved at least 1 civilian
     * @param superHeroList current list of superheroes
     * @return true if there are enough superheroes with at least 1 civilian saved, false if not
     */
    private static boolean hasEnoughValidHero(List<SuperHero> superHeroList) {
        List<SuperHero> validHeroList = new ArrayList<>();
        // Add all superheroes who have saved at least 1 civilian in the valid hero list
        for (SuperHero hero : superHeroList) {
            if (hero.getCivilianSaveCount() >= 1) {
                validHeroList.add(hero);
            }
        }
        // Checks if there are at least 3 superheroes who have saved at least 1 civilian
        if (validHeroList.size() >= 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sorts the superHeroList in descending order of number of civilians saved by the superheroes
     * @param superHeroList current list of superheroes
     * @return sorted list of superheroes in descending order of civilians saved by superheroes
     */
    private List<SuperHero> sortList(List<SuperHero> superHeroList) {
        for (int i = 0; i < superHeroList.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < superHeroList.size(); j++) {
                if (superHeroList.get(j).getCivilianSaveCount() > superHeroList.get(maxIndex).getCivilianSaveCount()) {
                    maxIndex = j;
                }
            }
            // Swap the superhero with the largest count with the superhero in the current index i
            SuperHero currentHero = superHeroList.get(i);
            SuperHero maxSuperHero = superHeroList.get(maxIndex);
            superHeroList.set(i, maxSuperHero) ;
            superHeroList.set(maxIndex, currentHero);
        }
        return superHeroList;
    }

}//Menu.java
