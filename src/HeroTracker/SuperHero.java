package HeroTracker;

/**
 * A class based on superhero for holding its information and creating a copy of the superhero
 * @author Kane
 */
public class SuperHero {

    private String name;
    private String superPower;
    private double heightInCm;
    private int civilianSaveCount;

    /**
     * Constructor of SuperHero
     */
    public SuperHero() {
        this.name = " ";
        this.heightInCm = 1.0; // default height
        this.civilianSaveCount = 0;
        this.superPower = " ";
    }

    /**
     * Constructor with parameters
     * @param name name of the superhero
     * @param heightInCm height of the superhero in cm
     * @param civilianSaveCount number of civilian saved by the superhero
     * @param superPower super power of the superhero
     */
    public SuperHero(String name, double heightInCm, int civilianSaveCount, String superPower) {
        this.name = name;
        this.heightInCm = heightInCm;
        this.civilianSaveCount = civilianSaveCount;
        this.superPower = superPower;
    }

    /**
     * Constructor with parameters
     * @param name name of the superhero\
     * @param superHero object of superHero
     * @param heightInCm height of the superhero in cm
     * @param civilianSaveCount number of civilian saved by the superhero
     * @param superPower super power of the superhero
     */
    public SuperHero(String name, SuperHero superHero, double heightInCm, int civilianSaveCount, String superPower) {
        this.name = name;
        this.heightInCm = heightInCm;
        this.civilianSaveCount = civilianSaveCount;
        this.superPower = superPower;
    }

    public String getName() {
        return this.name;
    }

    public double getHeightInCm() {
        return this.heightInCm;
    }

    public int getCivilianSaveCount() {
        return this.civilianSaveCount;
    }

    public String getSuperPower() {
        return this.superPower;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeightInCm(double heightInCm) {
        this.heightInCm = heightInCm;
    }

    public void setCivilianSaveCount(int civilianSaveCount) {
        this.civilianSaveCount = civilianSaveCount;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    /**
     * Displays the details of the superhero for debugging purpose
     * @return string of superHero details
     */
    @Override
    public String toString() {
        return ("SuperHero{name='" + this.getName() +"', heightInCm=" + this.getHeightInCm() +
                ", civilianSaveCount=" + this.getCivilianSaveCount() + ", superPower='" + this.getSuperPower() + "'}");
    }

    /**
     * Creates a new copy of superhero
     * @return new superhero copied
     */
    public Object clone() {
        SuperHero clonedHero = new SuperHero(this.name, this,heightInCm, this.civilianSaveCount, this.superPower);
        return clonedHero;
    }

}//SuperHero.java
