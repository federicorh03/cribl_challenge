package testPackage;

import classes.CommandLineProcesses;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    @BeforeTest
    public void beforeTest() throws Exception {
        String[] dockerUpCmd = {"cmd.exe", "/c", "cd dockerFiles && docker-compose up -d"};
        //start containers via docker-compose
        CommandLineProcesses.runCommand(dockerUpCmd);
    }

    @AfterTest
    public void afterTest() throws Exception {
        String [] dockerDownCmd = {"cmd.exe", "/c", "cd dockerFiles && docker-compose down"};
        //stop containers
        CommandLineProcesses.runCommand(dockerDownCmd);
    }
}
