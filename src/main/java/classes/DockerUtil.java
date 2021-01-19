package classes;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

public class DockerUtil {
    static DockerClientConfig custom = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost("tcp://localhost:2375")
            .withDockerTlsVerify(false)
            .build();

    static DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(custom.getDockerHost())
            .sslConfig(custom.getSSLConfig())
            .build();

    static DockerClient dockerClient = DockerClientImpl.getInstance(custom, httpClient);

    static String customPort = "9997";
    static String pwd = System.getProperty("user.dir");

    static HostConfig hostConfig = new HostConfig().withPortBindings(PortBinding.parse("9999:" + customPort))
            .withBinds(Bind.parse(pwd + "\\application:/data"));

    public static void runContainerWithCommand(String hostName, String command) {
        String folderName = hostName;
        if (hostName.startsWith("target")) {
            folderName = hostName.substring(0,6);
        }

        CreateContainerResponse container
                = dockerClient.createContainerCmd("node:latest")
                .withCmd(command + " " + folderName)
                .withWorkingDir("/data")
                .withName(hostName)
                .withHostName(hostName)
                .withHostConfig(hostConfig).exec();

        dockerClient.startContainerCmd(container.getId()).exec();
    }

    public static void copyFilesFromContainer(String container, String srcPath, String destPath) throws Exception {
        String[] dockerUpCmd = {"cmd.exe", "/c", "docker cp " + container + ":" + srcPath + " " +  destPath};

        CommandLineProcesses.runCommand(dockerUpCmd);
    }

}
