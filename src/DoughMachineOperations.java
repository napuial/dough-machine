import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class DoughMachineOperations {

    private DoughMachine doughMachine;

    DoughMachineOperations() {
        String takeIngredients = "SELECT * FROM INGREDIENTS";
        try (
                Connection connection = DriverManager.getConnection(DatabaseAccess.databaseAddress,
                        DatabaseAccess.username, DatabaseAccess.password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(takeIngredients);
        ) {
            Map<String, Integer> amounts = new HashMap<>();
            while(resultSet.next()) {
                amounts.put(resultSet.getString("PRODUCT"), resultSet.getInt("AMOUNT"));
            }
            this.doughMachine = new DoughMachine(
                    amounts.get("FLOUR"), amounts.get("MILK"), amounts.get("YEAST"), amounts.get("EGGS")
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void menuMessage() {
        System.out.println("""
               "run" -> Prepare a Dough
               type anything else for exit
                """);
    }

    private String takeUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    private boolean responseForUserInput(String input) {
        if(input.equals("run")) {
            doughMachine.prepareDough();
            doughMachine.reduceIngredients();
            doughMachine.showIngredients();
            return true;
        } else {
            return false;
        }
    }

    void runDoughMachine() {
        menuMessage();
        String input = takeUserInput();
        while(doughMachine.checkIngredients() && responseForUserInput(input)) {
            menuMessage();
            input = takeUserInput();
        }
    }
}
