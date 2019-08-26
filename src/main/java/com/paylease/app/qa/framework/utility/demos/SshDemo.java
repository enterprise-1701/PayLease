package com.paylease.app.qa.framework.utility.demos;

import static com.paylease.app.qa.framework.utility.sshtool.SshDriver.CHANNEL_TYPE_SHELL;

import com.jcraft.jsch.Channel;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.sshtool.SshDriver;

/**
 * Demonstration of how to use the SshDriver Class.
 *
 * @author Jeffrey Walker
 */
public class SshDemo {

  /**
   * Demo for ssh.
   *
   * @param arg arg
   */
  public static void main(String[] arg) {
    SshDriver sshDriver = new SshDriver();

    //Replace with the linux commands that you'd like to run
    String[] commands = {"cd /var/log", "ls -al"};
    try {
      Channel channel = sshDriver.makeConnection(CHANNEL_TYPE_SHELL);
      channel = sshDriver.executeCommand(commands, channel);
      sshDriver.processCommandOutput(channel);
      Logger.info(sshDriver.getCommandOutput());
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
  }
}
