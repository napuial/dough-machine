import java.sql.Connection;
import java.sql.DriverManager;
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

    private boolean checkIngredient(int amount, int requiredAmount, String product) {
        if (amount < requiredAmount) {
            System.out.println("Amount of " + product + " is not enough for another dough.");
            return false;
        }
        return true;
    }

    boolean checkIngredients() {
        return checkIngredient(this.flour, REQUIRED_FLOUR, "FLOUR")
                && checkIngredient(this.milk, REQUIRED_MILK, "MILK")
                && checkIngredient(this.yeast, REQUIRED_YEAST, "YEAST")
                && checkIngredient(this.eggs, REQUIRED_EGGS, "EGGS");
    }

    void prepareDough() {
        System.out.println("Congratulations!");
        System.out.println("""
                             
                            ▓▓▓▓▓▓▓▓▓▓  ▓▓▓▓▓▓   ▓▓▓▓
                          ▓▓▓░░░░░░░░▓▓▓░░░░░░▓▓▓░░░░▓▓▓
                        ▓▓░░░░░▓▓░░░░░░░░▓▓░░░░░░▓▓░░░░░▓▓
                      ▓▓▓░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▓▓
                   ▓▓░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▓▓
                  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
                              \u001B[32mYour dough is ready!\033[0m
                """);
    }

    private String updateAmountQuery(int amount, String product) {
        return "UPDATE INGREDIENTS SET AMOUNT = " +
                amount + " WHERE PRODUCT = '" + product + "'";
    }

    void reduceIngredients() {
        this.flour = this.flour - REQUIRED_FLOUR;
        this.milk = this.milk - REQUIRED_MILK;
        this.yeast = this.yeast - REQUIRED_YEAST;
        this.eggs = this.eggs - REQUIRED_EGGS;
        try (
                Connection connection = DriverManager.getConnection(DatabaseAccess.databaseAddress,
                        DatabaseAccess.username, DatabaseAccess.password);
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(updateAmountQuery(this.flour, "FLOUR"));
            statement.executeUpdate(updateAmountQuery(this.milk, "MILK"));
            statement.executeUpdate(updateAmountQuery(this.yeast, "YEAST"));
            statement.executeUpdate(updateAmountQuery(this.eggs, "EGGS"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showIngredient(int amount, int requiredAmount, String product) {
        System.out.printf("\t\u001B[32m%s\033[0m : \u001B[32m%d\033[0m, %d needed for a dough.\n",
                product, amount, requiredAmount);
    }

    void showIngredients() {
        System.out.println("\tAVAILABLE INGREDIENTS:");
        showIngredient(this.flour, REQUIRED_FLOUR, "FLOUR");
        showIngredient(this.milk, REQUIRED_MILK, "MILK");
        showIngredient(this.yeast, REQUIRED_YEAST, "YEAST");
        showIngredient(this.eggs, REQUIRED_EGGS, "EGGS");
        System.out.println();
    }
}
