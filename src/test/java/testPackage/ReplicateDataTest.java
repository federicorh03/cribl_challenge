package testPackage;

import classes.DockerUtil;
import classes.FileUtil;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ReplicateDataTest extends BaseTest {
    @Test
    public static void main() throws Exception {
        String pwd = System.getProperty("user.dir");
        String filePath = pwd + "\\application\\events.log";
        String[] targetContainers = {"target_1", "target_2"};

        FileUtil.waitForFileToExist(filePath);
        for (String target : targetContainers
             ) {
            DockerUtil.copyFilesFromContainer(target, "data/events.log", pwd + "\\artifacts\\" + target + "\\");
        }

        SoftAssert softAssert = new SoftAssert();
        int target_2_lines = FileUtil.countLines(pwd + "\\artifacts\\target_2\\events.log");
        int target_1_lines = FileUtil.countLines(pwd + "\\artifacts\\target_1\\events.log");
        softAssert.assertEquals(target_2_lines, 1000000, "File from target 2 is wrong");
        softAssert.assertEquals(target_1_lines, 1000000, "File from target 1 is wrong");
        softAssert.assertAll();
    }
}
