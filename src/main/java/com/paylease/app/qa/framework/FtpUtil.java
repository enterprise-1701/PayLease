package com.paylease.app.qa.framework;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;

public class FtpUtil {

  private String username;
  private String password;
  private String host;
  private int port;

  /**
   * Constructor.
   *
   * @param username the username
   * @param password the password
   * @param host the host
   */
  public FtpUtil(String username, String password, String host) {
    this.username = username;
    this.password = password;
    this.host = host;
    this.port = 22;
  }

  /**
   * Upload file.
   *
   * @param file the file
   */
  public void uploadFile(File file) {
    Session session = null;
    Channel channel = null;
    ChannelSftp channelSftp = null;

    try {
      JSch jsch = new JSch();
      session = jsch.getSession(username, host, port);
      session.setPassword(password);

      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);

      session.connect();

      channel = session.openChannel("sftp");
      channel.connect();

      channelSftp = (ChannelSftp) channel;
      channelSftp.put(new FileInputStream(file), file.getName());

      Logger.info("File transferred successfully to host.");
    } catch (Exception e) {
      Logger.error(e.getMessage());
    } finally {
      assert channelSftp != null;
      channelSftp.exit();
      channel.disconnect();
      session.disconnect();
    }
  }

  /**
   * Download file.
   *
   * @param src the path with file on the ftp server
   * @param dest the path for the downloaded file
   * @return true if file downloaded
   */
  public boolean downloadFile(String src, String dest) {
    Session session = null;
    Channel channel = null;
    ChannelSftp channelSftp = null;
    boolean fileDownloaded = false;

    try {
      JSch jsch = new JSch();
      session = jsch.getSession(username, host, port);
      session.setPassword(password);

      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);

      session.connect();

      channel = session.openChannel("sftp");
      channel.connect();

      channelSftp = (ChannelSftp) channel;
      channelSftp.get(src, dest);

      Logger.info("File downloaded from host.");
      fileDownloaded = true;
    } catch (Exception e) {
      Logger.error(e.getMessage());
    } finally {
      assert channelSftp != null;
      channelSftp.exit();
      channel.disconnect();
      session.disconnect();
    }

    return fileDownloaded;
  }
}
