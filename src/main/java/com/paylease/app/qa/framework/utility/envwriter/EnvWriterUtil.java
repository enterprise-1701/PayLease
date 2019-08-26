package com.paylease.app.qa.framework.utility.envwriter;

import static com.paylease.app.qa.framework.utility.sshtool.SshDriver.CHANNEL_TYPE_SFTP;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.utility.sshtool.SshDriver;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that enables you to write to an env file.
 *
 * @author Adrienne Aquino
 */
public class EnvWriterUtil {

  private static final String DOTENV_PATH = ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY) + "dotenv/";
  public static final String VANTIV_ENV_FILENAME = "vantiv.env";
  public static final String VANTIV_STUB_URL =
      ResourceFactory.getInstance().getProperty(ResourceFactory.STUB_VANTIV_URL_KEY);

  /**
   * Replaces the var name in the specified .env file
   *
   * @param envFileName file name without extension
   * @param varName env file variable whose value is to be replaced
   * @param value value to put in place of old value
   * @return old file contents
   */
  public String replaceEnvFileValue(String envFileName, String varName, String value) {

    SshUtil sshUtil = new SshUtil();

    String oldValue = "";
    // value is null, delete any file that was created.
    if (null == value) {
      if (sshUtil.fileExists(DOTENV_PATH + envFileName)) {
        sshUtil.deleteFile(DOTENV_PATH, envFileName);
        Logger.info("Deleted file: " + DOTENV_PATH + envFileName);
      }
    } else if (null == varName) {
      writeFile(envFileName, value);
      Logger.info("Wrote to : " + envFileName + "\nContents: " + readFile(envFileName));
      writeFile(envFileName, value);
      Logger
          .info("Wrote to : " + envFileName + "\nContents: " + readFile(envFileName));
    } else {
      String fileContents = "";
      if (sshUtil.fileExists(DOTENV_PATH + envFileName)) {
        fileContents = readFile(envFileName);
        Logger.info("file contents:" + fileContents);
        int varNameStartIndex = fileContents.indexOf(varName);
        if (fileContents.contains(varName) && ((varNameStartIndex > 2 && fileContents
            .substring(varNameStartIndex - 1, varNameStartIndex).equals("\n")) || (
            varNameStartIndex == 0)) && !(value.startsWith(varName))) {
          Pattern pattern = Pattern.compile("\\b" + varName + "\\b\\s*=");
          Matcher matcher = pattern.matcher(fileContents);

          StringBuilder builder = new StringBuilder(fileContents);

          while (matcher.find()) {
            int oldValueStartIndex = matcher.end();

            oldValue = fileContents.substring(oldValueStartIndex);
            if (oldValue.contains("\n")) {
              oldValue = oldValue.substring(0, oldValue.indexOf("\n"));
            }

            builder.replace(oldValueStartIndex, oldValueStartIndex + oldValue.length(), value);
            writeFile(envFileName, builder.toString());
          }
        } else {
          writeFile(envFileName, varName + "=" + value);
        }
        oldValue = fileContents;
      } else {
        if (varName != null) {
          writeFile(envFileName, varName + "=" + value);
        } else {
          writeFile(envFileName, value);
        }
        oldValue = null;
      }
      Logger.info("Wrote to : " + envFileName + "\nContents: " + readFile(envFileName));
    }
    return oldValue;

  }

  /**
   * Replaces the vantiv dotenv file with the vantiv stub values. Renames any current vantiv.env
   * files to vantiv.env.old
   */
  public void replaceVantivFileWithStub() {
    renameDotenvFile(VANTIV_ENV_FILENAME, VANTIV_ENV_FILENAME + ".old");

    String newContents =
        "VANTIV_LEGACY_URL=" + VANTIV_STUB_URL + "\n" + "VANTIV_URL=" + VANTIV_STUB_URL;
    replaceEnvFileValue(VANTIV_ENV_FILENAME, null, newContents);
  }

  /**
   * Rename the dotenv file. Before performing the rename, it checks if a file with the new file
   * name exists and if yes, deletes it. Also checks if the file to rename exists.
   *
   * @param fileName the file you want to rename
   * @param newFileName the new filename
   */
  public void renameDotenvFile(String fileName, String newFileName) {
    SshUtil sshUtil = new SshUtil();
    if (sshUtil.fileExists(DOTENV_PATH + newFileName)) {
      sshUtil.deleteFile(DOTENV_PATH, newFileName);
      Logger.info("Deleted file: " + DOTENV_PATH + newFileName);
    }

    if (sshUtil.fileExists(DOTENV_PATH + fileName)) {
      sshUtil
          .sshCommand(new String[]{"mv " + DOTENV_PATH + fileName + " " + DOTENV_PATH + newFileName});

      if (sshUtil.fileExists(DOTENV_PATH + newFileName)) {
        Logger.info(
            "Renamed file '" + DOTENV_PATH + fileName + "' to '" + DOTENV_PATH + newFileName + "'");
      }
    } else {
      Logger.info("Could not find file to rename: " + DOTENV_PATH + fileName);
    }
  }

  /**
   * Read file and return contents.
   *
   * @param fileName name of file
   * @return file contents as a String
   */
  public String readFile(String fileName) {
    SshDriver sshDriver = new SshDriver();
    StringBuilder stringBuilder = new StringBuilder();

    try {
      Channel channel = sshDriver.makeConnection(CHANNEL_TYPE_SFTP);
      channel.connect();

      ChannelSftp sftpChannel = (ChannelSftp) channel;
      sftpChannel.cd(DOTENV_PATH);

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

  /**
   * Write contents to file.
   *
   * @param fileName name of file
   * @param content new file content
   */
  private void writeFile(String fileName, String content) {
    SshDriver driver = new SshDriver();
    try {
      Channel channel = driver.makeConnection(CHANNEL_TYPE_SFTP);
      channel.connect();

      ChannelSftp sftpChannel = (ChannelSftp) channel;
      sftpChannel.cd(DOTENV_PATH);

      InputStream inputStream = new ByteArrayInputStream(content.getBytes());

      sftpChannel.put(inputStream, DOTENV_PATH + fileName);
      sftpChannel.exit();

      channel.getSession().disconnect();
      channel.disconnect();
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
  }
}