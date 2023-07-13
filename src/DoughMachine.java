import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class DoughMachine {

    private final static int REQUIRED_FLOUR = 300;
    private final static int REQUIRED_MILK = 100;
    private final static int REQUIRED_YEAST = 50;
    private final static int REQUIRED_EGGS = 2;

    private int flour;
    private int milk;
    private int yeast;
    private int eggs;

    DoughMachine(int flour, int milk, int yeast, int eggs) {
        this.flour = flour;
        this.milk = milk;
        this.yeast = yeast;
        this.eggs = eggs;
    }

    boolean checkIngredients() {
        boolean enoughAmount = true;
        if(REQUIRED_FLOUR > flour) {
            System.out.println("Amount of flour is not enough.");
            enoughAmount = false;
        }
        if(REQUIRED_MILK > milk) {
            System.out.println("Amount of milk is not enough.");
            enoughAmount = false;
        }
        if(REQUIRED_YEAST > yeast) {
            System.out.println("Amount of yeast is not enough.");
            enoughAmount = false;
        }
        if(REQUIRED_EGGS > eggs) {
            System.out.println("Amount of eggs is not enough.");
            enoughAmount = false;
        }
        return enoughAmount;
    }

    void prepareDough() {
        System.out.println("Your dough is ready!");
    }

    void reduceIngredients() {
        String reduceAmountOfFlour = "UPDATE INGREDIENTS SET AMOUNT = " +
                (this.flour - REQUIRED_FLOUR) + " WHERE PRODUCT = 'FLOUR'";
        String reduceAmountOfMilk = "UPDATE INGREDIENTS SET AMOUNT = " +
                (this.milk - REQUIRED_MILK) + " WHERE PRODUCT = 'MILK'";
        String reduceAmountOfYeast = "UPDATE INGREDIENTS SET AMOUNT = " +
                (this.yeast - REQUIRED_YEAST) + " WHERE PRODUCT = 'YEAST'";
        String reduceAmountOfEggs = "UPDATE INGREDIENTS SET AMOUNT = " +
                (this.eggs - REQUIRED_EGGS) + " WHERE PRODUCT = 'EGGS'";

        String takeAmountOfFlour = "SELECT AMOUNT FROM INGREDIENTS WHERE PRODUCT = 'FLOUR'";
        String takeAmountOfMilk = "SELECT AMOUNT FROM INGREDIENTS WHERE PRODUCT = 'MILK'";
        String takeAmountOfYeast = "SELECT AMOUNT FROM INGREDIENTS WHERE PRODUCT = 'YEAST'";
        String takeAmountOfEggs = "SELECT AMOUNT FROM INGREDIENTS WHERE PRODUCT = 'EGGS'";

        try (
                Connection connection = DriverManager.getConnection(DatabaseAccess.databaseAddress,
                        DatabaseAccess.username, DatabaseAccess.password);
                Statement statementForFlour = connection.createStatement();
                Statement statementForMilk = connection.createStatement();
                Statement statementForYeast = connection.createStatement();
                Statement statementForEggs = connection.createStatement()
        ) {
            statementForFlour.executeUpdate(reduceAmountOfFlour);
            statementForMilk.executeUpdate(reduceAmountOfMilk);
            statementForYeast.executeUpdate(reduceAmountOfYeast);
            statementForEggs.executeUpdate(reduceAmountOfEggs);
            try (
            ResultSet resultSetForFlour = statementForFlour.executeQuery(takeAmountOfFlour);
            ResultSet resultSetForMilk = statementForMilk.executeQuery(takeAmountOfMilk);
            ResultSet resultSetForYeast = statementForYeast.executeQuery(takeAmountOfYeast);
            ResultSet resultSetForEggs = statementForEggs.executeQuery(takeAmountOfEggs)
            ) {
                resultSetForFlour.next();
                this.flour = resultSetForFlour.getInt("AMOUNT");
                resultSetForMilk.next();
                this.milk = resultSetForMilk.getInt("AMOUNT");
                resultSetForYeast.next();
                this.yeast = resultSetForYeast.getInt("AMOUNT");
                resultSetForEggs.next();
                this.eggs = resultSetForEggs.getInt("AMOUNT");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void showIngredients() {
        System.out.println("\tAVAILABLE INGREDIENTS:");
        System.out.println("\tFlour: " + this.flour + ", " + REQUIRED_FLOUR + " needed for a dough.");
        System.out.println("\tMilk: " + this.milk + ", " + REQUIRED_MILK + " needed for a dough.");
        System.out.println("\tYeast: " + this.yeast + ", " + REQUIRED_YEAST + " needed for a dough.");
        System.out.println("\tEggs: " + this.eggs + ", " + REQUIRED_EGGS + " needed for a dough.");
    }
}
