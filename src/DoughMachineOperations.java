import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class DoughMachineOperations {

    DoughMachine doughMachine;

    DoughMachineOperations() {
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
                Statement statementForEggs = connection.createStatement();
                ResultSet resultSetForFlour = statementForFlour.executeQuery(takeAmountOfFlour);
                ResultSet resultSetForMilk = statementForMilk.executeQuery(takeAmountOfMilk);
                ResultSet resultSetForYeast = statementForYeast.executeQuery(takeAmountOfYeast);
                ResultSet resultSetForEggs = statementForEggs.executeQuery(takeAmountOfEggs)
        ) {
            resultSetForFlour.next();
            resultSetForMilk.next();
            resultSetForYeast.next();
            resultSetForEggs.next();

            this.doughMachine = new DoughMachine(
                    (Integer.parseInt(resultSetForFlour.getString("AMOUNT"))),
                    (Integer.parseInt(resultSetForMilk.getString("AMOUNT"))),
                    (Integer.parseInt(resultSetForYeast.getString("AMOUNT"))),
                    (Integer.parseInt(resultSetForEggs.getString("AMOUNT")))
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void runMachine() {
        doughMachine.showIngredients();
        System.out.println("Type \"run\" for prepare a dough or anything else for exit:");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        while(command.equals("run")) {
            if(doughMachine.checkIngredients()) {
                doughMachine.prepareDough();
                doughMachine.reduceIngredients();
                doughMachine.showIngredients();
                System.out.println("Type \"run\" for prepare a dough or anything else for exit:");
                command = scanner.next();
            } else {
                break;
            }
        }
    }
}
