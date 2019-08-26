package com.paylease.app.qa.framework.utility.sshtool;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.io.IOUtils;

/**
 * Class that enables you to SSH into a Linux Server and execute commands.
 *
 * @author Jeffrey Walker
 */
public class SshDriver {

  private String commandOutput;
  private Session session;
  private Object syncObj = new Object();
  private boolean running = true;
  private LinkedBlockingQueue<String> queue;

  public static final String CHAR_ENCODING_UTF_8 = "utf-8";

  public static final String CHANNEL_TYPE_SHELL = "shell";
  public static final String CHANNEL_TYPE_SFTP = "sftp";

  /**
   * SSH to the your own host, or whatever values are provided to AppConfig.properties.
   *
   * @param type connection type
   * @return channel
   * @throws Exception JSchException
   */
  public Channel makeConnection(String type) throws Exception {
    String privateKeyPath = ResourceFactory.getInstance()
        .getProperty(ResourceFactory.SSH_PRIVATE_KEY_PATH_KEY);
    String username = ResourceFactory.getInstance().getProperty(ResourceFactory.SSH_USERNAME_KEY);
    String host = ResourceFactory.getInstance().getProperty(ResourceFactory.SSH_HOST_KEY);
    String passphrase = ResourceFactory.getInstance()
        .getProperty(ResourceFactory.SSH_PASSPHRASE_KEY);

    queue = new LinkedBlockingQueue<>();

    JSch jsch = new JSch();
    jsch.addIdentity(privateKeyPath, passphrase);

    session = jsch.getSession(username, host, 22);

    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");

    session.setConfig(config);
    session.connect(5000);

    return session.openChannel(type);
  }

  public Channel makeConnection(String type, String hostKey,String privateKey, String passPhrase) throws Exception {
    String privateKeyPath = ResourceFactory.getInstance()
        .getProperty(privateKey);
    String username = ResourceFactory.getInstance().getProperty(ResourceFactory.SSH_USERNAME_KEY);
    String host = ResourceFactory.getInstance().getProperty(hostKey);
    String passphrase = ResourceFactory.getInstance().getProperty(passPhrase);

    queue = new LinkedBlockingQueue<>();

    JSch jsch = new JSch();
    jsch.addIdentity(privateKeyPath, passphrase);

    session = jsch.getSession(username, host, 22);

    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");

    session.setConfig(config);
    session.connect(5000);

    return session.openChannel(type);
  }


  /**
   * Execute the given commands on the host.
   *
   * @param commands String array of commands to execute
   * @return channel
   */
  public Channel executeCommand(String[] commands, final Channel channel) {
    try {
      final PipedOutputStream pos = new PipedOutputStream();
      channel.setInputStream(new PipedInputStream(pos));

      new Thread(new Runnable() {
        final PrintWriter stdin = new PrintWriter(new OutputStreamWriter(pos, CHAR_ENCODING_UTF_8));

        @Override
        public void run() {
          for (String command : commands) {
            stdin.println(command);
          }

          stdin.println("exit");
          stdin.close();
        }
      }).start();
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return channel;
  }

  /** Process outputs of command execution. */
  public void processCommandOutput(final Channel channel) {
    try {
      final PipedInputStream pis = new PipedInputStream();
      channel.setOutputStream(new PipedOutputStream(pis));

      new Thread(new Runnable() {
        // Uses utf-8 character encoding
        private final BufferedReader br = new BufferedReader(
            new InputStreamReader(pis, CHAR_ENCODING_UTF_8));

        @Override
        public void run() {
          try {
            commandOutput = IOUtils.toString(br);
            queue.add(commandOutput);
          } catch (IOException e) {
            Logger.error(e.getMessage());
          } finally {
            session.disconnect();

            final int exitStatus = channel.getExitStatus();
            Logger.trace("Exit status : " + exitStatus);
          }
        }
      }).start();

      channel.connect(3 * 1000);

      synchronized (syncObj) {
        running = false;
        syncObj.notify();
      }
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
  }

  /**
   * Capture the command outputs.
   *
   * @return output of SSH command
   */
  public String getCommandOutput() throws InterruptedException {
    try {
      synchronized (syncObj) {
        while (running) {
          syncObj.wait();
        }
      }
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return queue.take();
  }
}