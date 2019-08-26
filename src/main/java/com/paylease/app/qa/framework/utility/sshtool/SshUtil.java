package com.paylease.app.qa.framework.utility.sshtool;

import static com.paylease.app.qa.framework.utility.sshtool.SshDriver.CHANNEL_TYPE_SFTP;
import static com.paylease.app.qa.framework.utility.sshtool.SshDriver.CHANNEL_TYPE_SHELL;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SshUtil {

  private HashMap<String, String> envVars;

  public static final String LOG_FILE_TYPE_ERROR = "error";
  public static final String LOG_FILE_TYPE_ACCESS = "access";

  /**
   * Run command in background.
   *
   * @param command the command to run in the background.
   * @return pid of the background process.
   */
  private int runCommandInBackground(String command) {
    int pid = 0;
    String output = sshCommand(new String[]{command + " &"});

    Pattern pattern = Pattern.compile("^\\[1\\] (\\d+)$", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(output);
    if (matcher.find()) {
      pid = Integer.parseInt(matcher.group(1));
    }

    Logger.trace("Pid was: " + pid);

    return pid;
  }

  /**
   * Run queue worker in background.
   *
   * @param workerName worker to run
   * @return process id
   */
  public int runQueueWorker(String workerName) {
    String webDir = ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY);
    String logFile = webDir + "../logs/" + workerName + ".log";
    String command = getEnvVarsCommandPrefix()
        + "php " + webDir + "queue_workers/" + workerName + ".php"
        + " >> " + logFile + " 2>&1";

    return runCommandInBackground(command);
  }

  /**
   * Run the ipn worker in background.
   *
   * @throws Exception the exception
   */
  public void runIpnWorker() throws Exception {
    runQueueWorkerUntilComplete("ipn", "ipn", false);
  }

  /**
   * Run the worker in background until all jobs consumed.
   *
   * @throws Exception the exception
   */
  public void runQueueWorkerUntilComplete(String queueName) throws Exception {
    runQueueWorkerUntilComplete(queueName, queueName, false);
  }

  /**
   * Run worker in background until all jobs are consumed.
   *
   * @param queueWorkerName Name of queue worker
   * @param queueName Name of the gearman queue
   * @param emailWorker If worker is in Email folder then set to true
   * @throws Exception the exception
   */
  public void runQueueWorkerUntilComplete(String queueWorkerName, String queueName, boolean emailWorker) throws Exception {
    String email = emailWorker ? "Email/" : "";
    int pid = runQueueWorker(email + queueWorkerName + "_worker");

    while (getNumberOfJobsForWorker(queueName) != 0) {
      continue;
    }

    killProcess(pid);
  }

  public  void runIpnAndSlowIpnUntilBothComplete() throws Exception {
    int ipnPid = runQueueWorker("ipn_worker");
    int slowIpnPid = runQueueWorker("slow_ipn_worker");



    while (!(getNumberOfJobsForWorker("slow_ipn") == 0 && getNumberOfJobsForWorker("ipn") == 0)) {
        continue;
    }

    killProcess(ipnPid);
    killProcess(slowIpnPid);
  }

  /**
   * Kill the process.
   *
   * @param pid process id to kill
   */
  public void killProcess(int pid) {
    sshCommand(new String[]{"kill " + pid});
  }

  /**
   * Run batch script.
   *
   * @param scriptName the name of the script to run
   * @return command output
   */
  public String runBatchScript(String scriptName) {
    return runBatchScriptWithArgs(scriptName, null);
  }

  /**
   * Run batch script.
   *
   * @param scriptName the name of the script to run
   * @param argument string of argument(s) to append to script execution command
   * @return command output
   */
  public String runBatchScriptWithArgs(String scriptName, String argument) {
    return runScriptByDirectoryName(scriptName, argument, "batches/");
  }

  /**
   * Run batch script by directory name.
   *
   * @param scriptName the name of the script to run
   * @param argument string of argument(s) to append to script execution command
   * @param directory the directory where scriptName lives
   * @return command output
   */
  public String runScriptByDirectoryName(String scriptName, String argument, String directory) {
    String command = getEnvVarsCommandPrefix() + "php " + ResourceFactory.getInstance()
        .getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY) + directory + scriptName + ".php";

    if (argument != null) {
      command += " " + argument;
    }

    return runCommand(command);
  }

  /**
   * Run batch script.
   *
   * @param scriptName the name of the script to run
   * @param argument1 string of argument to append to script execution command
   * @param argument2 string of argument to append to script execution command
   * @return command output
   */
  public String runBatchScriptWithArgs(String scriptName, String argument1, String argument2) {
    String command = getEnvVarsCommandPrefix() + "php " + ResourceFactory.getInstance()
            .getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY) + "batches/" + scriptName + ".php";

    if (argument1 != null) {
      command += " " + argument1;
    }

    if (argument2 != null) {
      command += " " + argument2;
    }

    return runCommand(command);
  }

  /**
   * Run the autoExport script for the given pm id and return file name the script generates.
   *
   * @param pmId pm id
   * @return return the file name
   */
  public String runAutoExportScript(int pmId) {
    String fileName = null;

    if (pmId < 0) {
      return fileName;
    }

    String output = runBatchScriptWithArgs("auto_export", Integer.toString(pmId));

    Pattern pattern = Pattern.compile("^Saving copy to ftp: (.+)$", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(output);
    if (matcher.find()) {
      String filePath = matcher.group(1);
      String filePathSplit[] = filePath.split("/");
      fileName = filePathSplit[filePathSplit.length - 1];
    }

    return fileName;
  }

  /**
   * Look for a file on a remote path.
   *
   * @param filePath absolute path of the file to find
   * @return true if file exists
   */
  public boolean fileExists(String filePath) {
    String command = "ls " + filePath;
    String output = runCommand(command);

    Pattern pattern = Pattern.compile("^ls: cannot access ", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(output);

    return !matcher.find();
  }

  /**
   * Calculate the MD5sum of the given file.
   *
   * @param filePath Path to file
   * @return md5 of the file
   */
  public String getFileMd5(String filePath) {
    String command = "md5sum " + filePath;
    String output = runCommand(command);
    String md5 = "";

    Pattern pattern = Pattern.compile("^(\\w+)  " + filePath + "$", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(output);
    if (matcher.find()) {
      md5 = matcher.group(1);
    }

    Logger.trace("md5 was: " + md5);

    return md5;
  }

  /**
   * Run the command.
   *
   * @param command command to run
   * @return command output
   */
  public String runCommand(String command) {
    return sshCommand(new String[]{command});
  }

  /**
   * Get a count of jobs currently in the queue for the given worker.
   *
   * @param workerName name of worker to inspect
   * @return count of outstanding jobs
   */
  public int getNumberOfJobsForWorker(String workerName) throws Exception {
    String workerCommand = "^" + workerName + "\\s";
    String output = runCommand("gearadmin --status | column -t | grep '" + workerCommand + "'");

    Pattern pattern = Pattern.compile("^" + workerName + "\\s+(\\d+)", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(output);
    if (matcher.find()) {

      return Integer.parseInt(matcher.group(1));
    } else {
      throw new Exception("Could not find job");
    }
  }

  /**
   * Get a count of instances of "regex" in file "filename".
   *
   * @param regex string to find
   * @param filename filename to search
   * @return returns the count of instances
   */
  public int getStringMatchCountFromFile(String regex, String filename) {
    return getCountFromOutput(runCommand("grep -c \"" + regex + "\" " + filename));
  }

  /**
   * Parse a line from the output containing a single integer.
   *
   * @param output the string to search
   * @return the integer found
   */
  public int getCountFromOutput(String output) {
    Pattern pattern = Pattern.compile("^(\\d+)$", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(output);
    if (matcher.find()) {

      return Integer.parseInt(matcher.group(1));
    } else {
      Logger.warning("Could not find count in output");

      return 0;
    }
  }

  /**
   * Get the message of "regex" in file "filename".
   *
   * @param regex string to find
   * @param filename filename to search
   * @param groupToReturn the index of the matching group to return
   * @return returns the string of the match from the "filename"
   * @throws Exception if we did not find the output
   */
  public String getStringMatchFromFile(String regex, String filename, int groupToReturn) throws Exception {
    String command = "grep \"" + regex + "\" " + filename;
    String output = runCommand(command);
    output = output.replace(command, "");

    Pattern pattern = Pattern.compile("^.*" + regex + "(.*)$", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(output);
    String lastMatch = "";
    while (matcher.find()) {
      lastMatch = matcher.group(groupToReturn).trim();
    }

    if (lastMatch.isEmpty()) {
      throw new Exception("Could not find output");
    }

    return lastMatch;
  }

  /**
   * Get the message of "regex" in file "filename".
   *
   * @param regex string to find
   * @param filename filename to search
   * @return returns the string of the first group match from the "filename"
   * @throws Exception if we did not find the output
   */
  public String getStringMatchFromFile(String regex, String filename) throws Exception {
    return getStringMatchFromFile(regex, filename, 1);
  }

  /**
   * Wait for a log message to be present.
   *
   * @param message the log message to look for
   */
  public void waitForLogMessage(String message, String logName) {
    int retryLimit = 10;
    int retryInterval = 1000;

    for (int i = 0; i < retryLimit; i++) {
      Logger.trace("Attempt #" + (i + 1) + ": Looking for message.");

      if (1 <= getStringMatchCountFromFile(message, logName)) {
        return;
      }

      try {
        Thread.sleep(retryInterval);
      } catch (InterruptedException e) {
        Logger.debug("Sleep problem: " + e.getMessage());
      }
    }
  }

  /**
   * SSH and run commands.
   *
   * @param commands list of commands to run
   * @return output string
   */
  public String sshCommand(String[] commands) {
    String output = "";

    SshDriver sshDriver = new SshDriver();

    try {
      Channel channel = sshDriver.makeConnection(CHANNEL_TYPE_SHELL);
      channel = sshDriver.executeCommand(commands, channel);

      sshDriver.processCommandOutput(channel);
      output = sshDriver.getCommandOutput();

      Logger.trace(output);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return output;
  }

  /**
   * SSH and run commands.
   *
   * @param commands list of commands to run
   * @return output string
   */
  public String sshCommand(String[] commands, String hostKey, String privateKey, String passPhrase ) {
    String output = "";

    SshDriver sshDriver = new SshDriver();

    try {
      Channel channel = sshDriver.makeConnection(CHANNEL_TYPE_SHELL, hostKey, privateKey, passPhrase);
      channel = sshDriver.executeCommand(commands, channel);

      sshDriver.processCommandOutput(channel);
      output = sshDriver.getCommandOutput();

      Logger.trace(output);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return output;
  }

  /**
   * Creates SSH connect and FTP file to target directory.
   *
   * @param remoteTargetFtpDirPath target directory for ftp file
   * @param localPathOfFileToFTP path and file name of file to FTP
   */
  public Session ftpFileToRemoteServer(String remoteTargetFtpDirPath, String localPathOfFileToFTP)
      throws Exception {

    //Make the connection (channel = sftp)
    SshDriver sshDriver = new SshDriver();
    Channel channel = sshDriver.makeConnection(CHANNEL_TYPE_SFTP);
    Session session = channel.getSession();
    channel.connect();

    //Process the file FTP
    ChannelSftp channelSftp = (ChannelSftp) channel;
    try {
      channelSftp.stat(remoteTargetFtpDirPath);
    } catch (Exception e) {
      channelSftp.mkdir(remoteTargetFtpDirPath);
    }

    channelSftp.cd(remoteTargetFtpDirPath);
    File f = new File(localPathOfFileToFTP);
    channelSftp.put(new FileInputStream(f), f.getName());
    Logger.trace("File transferred successfully to host.");
    channelSftp.disconnect();
    channel.disconnect();

    return session;
  }

  /**
   * Checks if any batch processing script is running and returns a boolean statement.
   *
   * @return true if scripts are running.
   */
  public boolean checkIfAnyBrPtScriptIsRunning() {

    boolean isRunning = false;

    String[] command = {"ps -ef | grep br_pt.php | grep -v grep",
        "ps -ef | grep br_pt_profit_stars.php | grep -v grep",
        "ps -ef | grep br_process_cc.php | grep -v grep",
        "ps -ef | grep br_pt_fn_sp.php | grep -v grep",
        "ps -ef | grep br_sp_profitstars.php | grep -v grep",
        "ps -ef | grep br_fn_sp.php | grep -v grep",
        "ps -ef | grep br_fn_process_returns | grep -v grep",
        "ps -ef | grep br_profitstars_process_returns | grep -v grep",
        "ps -ef | grep br_fn_returns | grep -v grep",
        "ps -ef | grep br_sp_profitstars_new_world | grep -v grep"};

    String output = sshCommand(command);

    Logger.trace("Output of the command: " + output);

    if (output.contains("batches/br_pt.php") || output
        .contains("batches/br_pt_profit_stars.php") || output
        .contains("batches/br_process_cc.php") || output
        .contains("batches/br_pt_fn_sp.php") || output
        .contains("batches/br_sp_profitstars.php") || output
        .contains("batches/br_fn_sp.php") || output
        .contains("batches/br_sp_profitstars_new_world.php") || output
        .contains("batches/br_fn_process_returns.php") || output
        .contains("batches/br_profitstars_process_returns.php") || output
        .contains("batches/br_fn_returns.php")) {

      isRunning = true;
    }

    return isRunning;
  }

  /**
   * Run 'rm' command for a given file dir/name.
   *
   * @param fileDir the directory to the file to remove
   * @param fileName the file name to remove
   */
  public void deleteFile(String fileDir, String fileName) {
    String[] command = {"rm " + fileDir + fileName};

    sshCommand(command);
  }

  /**
   * Set env vars.
   *
   * @param envVars the env vars
   */
  public void setEnvVars(HashMap<String, String> envVars) {
    this.envVars = envVars;
  }

  private String getEnvVarsCommandPrefix() {
    String env = "";
    if (null != envVars) {
      for (Map.Entry<String, String> entry : envVars.entrySet()) {
        env += entry.getKey() + "=" + entry.getValue() + " ";
      }
    }

    return env;
  }

  /**
   * Read file and return contents.
   *
   * @param logFileType ERROR or ACCESS
   * @return file contents as a String
   */
  public String readLogFile(String logFileType) {
    SshDriver sshDriver = new SshDriver();
    StringBuilder stringBuilder = new StringBuilder();

    try {
      Channel channel = sshDriver.makeConnection(CHANNEL_TYPE_SFTP);
      channel.connect();

      ChannelSftp sftpChannel = (ChannelSftp) channel;
      sftpChannel.cd("/var/log/httpd");

      String sshUsername = ResourceFactory.getInstance()
          .getProperty(ResourceFactory.SSH_USERNAME_KEY);
      String fileName = "";

      switch (logFileType) {
        case LOG_FILE_TYPE_ACCESS:
          fileName = sshUsername + "_access_log";
          break;
        default:
          fileName = sshUsername + "_error_log";
          break;
      }

      Logger.info("Log File Name: " + fileName);
      InputStream inputStream = sftpChannel.get(fileName);
      char[] chBuffer = new char[0x10000];
      Reader objReader = new InputStreamReader(inputStream, "UTF-8");
      int intLine = 0;

      do {
        intLine = objReader.read(chBuffer, 0, chBuffer.length);
        if (intLine > 0) {
          stringBuilder.append(chBuffer, 0, intLine);
        }
      } while (intLine >= 0);

      objReader.close();

      inputStream.close();

      sftpChannel.exit();

      channel.getSession().disconnect();
      channel.disconnect();
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return stringBuilder.toString();
  }
}