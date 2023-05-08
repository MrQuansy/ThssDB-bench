import org.apache.thrift.TException;

public class TestEntrance {

    private static int successStatusCode = 0;

    public static void main(String args[]) throws TException {

        Client client = new Client("127.0.0.1",6667);

        System.out.println("\n--- Start simple bench! ---");

        TestExecutor testExecutor = new TestExecutor();

        //create database
        testExecutor.createAndUseDB(client);
        System.out.println("Create database finished!");

        // create table
        testExecutor.createTable(client);
        System.out.println("Create table finished!");

        //insert data:
        testExecutor.insertData(client);
        System.out.println("Insert data finished!");

        //query data:
        testExecutor.queryData(client);
        System.out.println("Query data finished!");
        //update and query data

        System.out.println("Update and re-query data finished!");
        //delete and query data

        System.out.println("Delete and re-query data finished!");

        System.out.println("\n--- Finish testing! ---");
    }

}
