package com.paylease.app.qa.framework.utility.emailtool;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.Logger;
import com.sun.mail.gimap.GmailRawSearchTerm;
import com.sun.mail.gimap.GmailStore;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

/**
 * Processes Email Messages.
 *
 * @author Jeffrey Walker
 */
public class EmailProcessor {

  public enum PlNotifyLevel {
    LOW("100"),
    CRITICAL("500");

    private final String notificationLevel;

    PlNotifyLevel(String notificationLevel) {
      this.notificationLevel = notificationLevel;
    }

    String getNotificationLevel() {
      return notificationLevel;
    }
  }

  public static final String INBOX_FOLDER = "INBOX";

  public static final int EMAIL_RETRY_TIMEOUT = 1000;
  public static final int EMAIL_RETRY_COUNT = 4;

  private Store store;
  private Folder emailFolder;

  private String imageOutputDir = "test-output/inline-images/";
  private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

  private boolean connected;

  /** Simple constructor to show there is no connection. */
  public EmailProcessor() {
    connected = false;
  }

  /**
   * Returns a boolean value based on if the processor is connected to the service or not.
   *
   * @return boolean if the processor is connected or not.
   */
  public boolean isConnected() {
    return connected;
  }

  /** Creates a connection to the email server with the defined login credentials. */
  public void connect() {
    Properties props = System.getProperties();
    props.setProperty("mail.store.protocol", "imaps");
    Session session = Session.getDefaultInstance(props, null);

    try {
      store = (GmailStore) session.getStore("gimaps");
      String username = AppConstant.QA_MAILBOX;
      String password = AppConstant.QA_MAILBOX_PASSWORD;
      store.connect("imap.gmail.com", username, password);
      connected = true;
    } catch (Exception e) {
      Logger.error(e.toString());
      connected = false;
    }
  }

  /** Gets all the messages in the given folder. */
  public Message[] getAllMessages(String folderName) {
    Message[] messages = null;

    try {
      //Open the folder
      emailFolder = store.getFolder(folderName);
      emailFolder.open(Folder.READ_ONLY);

      //Retrieve the messages from the folder
      messages = emailFolder.getMessages();
    } catch (Exception e) {
      Logger.error(e.toString());
    }

    return messages;
  }

  public Message[] getMessage(String folderName, final String keyword) {
    return getMessage(folderName, keyword, 0);
  }
  /** Get all messages whose subject contains the given keyword. Will search the whole folder. */
  public Message[] getMessage(String folderName, final String keyword, int startingMessageCount) {
    Message[] messages = null;

    try {
      //Open the folder
      emailFolder = store.getFolder(folderName);
      emailFolder.open(Folder.READ_WRITE);
      Logger.info("Opened folder...");
      //Retrieve the messages from the folder

      //Creates a search criterion
      SearchTerm searchCondition = new SearchTerm() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean match(Message message) {
          try {
            Logger.info("Checking match condition...");
            if (message.getSubject().contains(keyword)) {
              return true;
            }
          } catch (MessagingException ex) {
            Logger.error(ex.toString());
          }
          return false;
        }
      };

      //Performs search through the folder
      messages = emailFolder.search(searchCondition,
          emailFolder.getMessages(startingMessageCount, emailFolder.getMessageCount()));

    } catch (Exception e) {
      Logger.error(e.toString());
    }

    return messages;
  }

  /** Close the store and folder objects. */
  public void close() {
    try {
      emailFolder.close(false);
      store.close();
    } catch (MessagingException e) {
      Logger.error(e.toString());
    }
  }

  /** Get the message body. Returns of the message body in parts, if multipart. */
  public ArrayList<MessagePart> getBody(Part p, ArrayList<MessagePart> messagePartsList,
      boolean saveImages, boolean saveAttachments) {

    MessagePart messagePart = new MessagePart();

    try {
      // check if the content is plain text that is part of the email body (not an attachment)
      if (p.isMimeType("text/plain") && !Part.ATTACHMENT.equalsIgnoreCase(p.getDisposition())) {
        Logger.info("Processing text/plain Message Part...");
        messagePart.setMimeType("text/plain");
        messagePart.setContent((String) p.getContent());
        messagePartsList.add(messagePart);
      } else if (p.isMimeType("multipart/*")) { // check if the content is Multipart
        Multipart mp = (Multipart) p.getContent();
        int count = mp.getCount();

        for (int i = 0; i < count; i++) {
          getBody(mp.getBodyPart(i), messagePartsList, saveImages, saveAttachments);
        }

      } else if (p.isMimeType("message/rfc822")) { // check if the content is a nested message
        getBody((Part) p.getContent(), messagePartsList, saveImages, saveAttachments);
      } else if (saveImages && Part.INLINE.equalsIgnoreCase(p.getDisposition())) {
        // check if the content is an inline image

        MimeBodyPart mimeBodyPart = (MimeBodyPart) p;

        String fileName = mimeBodyPart.getFileName();

        String imageOutputPath =
            imageOutputDir + formatter.format(new Date().getTime()) + "_" + fileName
                .replaceAll("\\.[a-zA-Z0-9\\\\._]{3,}", ".PNG");
        try {
          boolean result = saveImageAsPng((InputStream) mimeBodyPart.getContent(), imageOutputPath);

          if (result) {
            Logger.info("Image converted successfully.");
          } else {
            Logger.info("Could not convert image.");
          }
        } catch (IOException ex) {
          Logger.error("Error during image conversion.");
          Logger.error(ex.toString());
        }

        messagePart.setMimeType("image/");
        messagePart.setContent("Embedded image part is stored here: " + imageOutputPath);
        messagePartsList.add(messagePart);
      } else if (saveAttachments && Part.ATTACHMENT.equalsIgnoreCase(p.getDisposition())) {
        //check if content has an attachment
        Logger.info("Processing image/ Message Part...");

        String attachmentOutputPath = saveAttachmentPart(p);

        messagePart.setMimeType("attachments/");
        messagePart.setContent("Attachment is stored here: " + attachmentOutputPath);
        messagePartsList.add(messagePart);
      } else {
        Object o = p.getContent();
        if (o instanceof String) {
          Logger.info("Processing Simple String Message Part...");
          messagePart.setMimeType("String");
          messagePart.setContent((String) o);
          messagePartsList.add(messagePart);
        } else if (saveImages && o instanceof InputStream) {
          Logger.info("Processing image/ (input stream) Message Part...");
          InputStream is = (InputStream) o;
          String imageFilePath = "inline-images/image-" + new Date().getTime() + ".jpg";
          OutputStream os = new FileOutputStream(imageFilePath);

          byte[] buffer = new byte[1024];
          int bytesRead;

          while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
          }

          is.close();
          os.flush();
          os.close();

          messagePart.setMimeType("image/");
          messagePart.setContent("Embedded image part is stored here: " + imageFilePath);
          messagePartsList.add(messagePart);

        } else {
          Logger.info("Processing Unknown Message Part...");
          messagePart.setMimeType("Unknown Type");
          messagePart.setContent("");
          messagePartsList.add(messagePart);
        }
      }
    } catch (Exception e) {
      Logger.error(e.toString());
    }

    return messagePartsList;
  }

  /** Get the list of addresses that the email was sent to. */
  public ArrayList<String> getToAddressList(Message m) {
    ArrayList<String> toList = new ArrayList<String>();
    try {
      Address[] a;

      if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
        for (int j = 0; j < a.length; j++) {
          toList.add(a[j].toString());
        }
      }
    } catch (Exception e) {
      Logger.error(e.toString());
    }

    return toList;
  }

  /** Get the email address of the recipient. */
  public String getFromAddress(Message m) {
    ArrayList<String> fromList = new ArrayList<String>();
    try {
      Address[] a;

      if ((a = m.getFrom()) != null) {
        for (int j = 0; j < a.length; j++) {
          fromList.add(a[j].toString());
        }
      }
    } catch (Exception e) {
      Logger.error(e.toString());
    }

    return fromList.get(0);
  }

  /** Get the message subject. */
  public String getSubject(Message m) {
    String subject = null;

    try {
      if (m.getSubject() != null) {
        subject = m.getSubject();
      }
    } catch (Exception e) {
      Logger.error(e.toString());
    }

    return subject;
  }

  /** Get the first message whose subject contains the given keyword. */
  public Message getFirstMatchingMessage(String folderName, final String keyword) {
    Message[] messages = getMessage(folderName, keyword);
    Message message = null;

    try {
      //Open the folder
      emailFolder = store.getFolder(folderName);
      emailFolder.open(Folder.READ_ONLY);

      //Retrieve the messages from the folder
      messages = emailFolder.getMessages();

      //get first message
      message = messages[0];
      Logger.info("Rec date" + message.getReceivedDate());

    } catch (Exception e) {
      Logger.info("No matching messages found");
      Logger.error(e.toString());
    }

    return message;
  }

  /** Get messages whose subject contains the given keyword and date range. */
  public Message[] getMessagesWithinRange(String folderName, final String keyword, Date startDate,
      Date endDate) {
    Logger.info("Start date: " + startDate);
    Logger.info("End date " + endDate);
    Message[] messages = null;

    try {
      //Open the folder
      emailFolder = store.getFolder(folderName);
      emailFolder.open(Folder.READ_ONLY);
      Logger.info("Opened folder...");

      //Creates a search criterion
      SearchTerm matchesKeyword = new SearchTerm() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean match(Message message) {
          try {
            Logger.info("Checking keyword matching condition...");
            if (message.getSubject().contains(keyword)) {
              return true;
            }
          } catch (MessagingException ex) {
            Logger.error(ex.toString());
          }
          return false;
        }
      };

      SearchTerm olderThan = new ReceivedDateTerm(ComparisonTerm.LT, endDate);
      SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT, startDate);
      SearchTerm andTerm = new AndTerm(new AndTerm(olderThan, newerThan), matchesKeyword);

      messages = emailFolder.search(andTerm);

    } catch (Exception e) {
      Logger.error(e.toString());
    }

    return messages;
  }

  /**
   * This looks for an e-mail that has both a subject restriction and body content
   * restriction. It does not need to be private, but it is currently only being used
   * internally.
   *
   * @param subject Subject line of the E-mail.
   * @param expression Contents of the body to be search.
   * @return Message the message found.
   */
  private Message getMessageWhereSubjectIsAndBodyContains(String subject, String expression)
      throws MessagingException {

    Message[] messages = getMessagesWhereBodyContains(expression);

    if (messages != null) {
      for (Message message : messages) {
        if (message.getSubject().contains(subject)) {
          return message;
        }
      }
    }
    return null;
  }

  /**
   * Waits for a message to appear in the inbox giving it the max amount of attempts.
   * It will return null if no message is found after the the allotted time.
   *
   * @param subject The message subject
   * @param bodyContents The expression to look for in the body of the e-mail.
   * @return Message of the e-mail if found, null otherwise.
   */
  public Message waitForSpecifiedEmailToBePresent(String subject, String bodyContents) {
    if (!isConnected()) {
      connect();
    }

    try {
      for (int i = 1; i <= EMAIL_RETRY_COUNT; i++) {
        Logger.trace("Attempt #" + i + ": Looking for E-mail.");
        Message message = getMessageWhereSubjectIsAndBodyContains(subject, bodyContents);

        if (message != null) {
          return message;
        } else {
          try {
            Thread.sleep(EMAIL_RETRY_TIMEOUT * (i + 1));
          } catch (InterruptedException e) {
            Logger.debug("Sleep problem: " + e.getMessage());
          }
        }
      }
    } catch (Exception e) {
      Logger.debug(e.toString());
    }

    return null;
  }

  /**
   * Wait for email message containing filename to be present in INBOX.
   *
   * @param search string to find in message body
   * @param level the level of the plnotify email
   * @param key the event key of the plnotify email
   * @return Message that was found
   */
  public Message waitForPlNotifyEmailToBePresent(String search, PlNotifyLevel level, String key) {
    EmailProcessor emailProcessor = new EmailProcessor();
    emailProcessor.connect();

    String subject = "PLNotify level: " + level.getNotificationLevel() + " key: " + key;

    for (int i = 0; i < EMAIL_RETRY_COUNT; i++) {
      Logger.trace("Attempt #" + (i + 1) + ": Looking for message.");

      Message[] messages = emailProcessor.getMessagesWhereBodyContains(search);

      for (Message message : messages) {
        if (emailProcessor.getSubject(message).equals(subject)) {

          return message;
        }
      }

      try {
        Thread.sleep(EMAIL_RETRY_TIMEOUT);
      } catch (InterruptedException e) {
        Logger.debug("Sleep problem: " + e.getMessage());
      }
    }

    return null;
  }

  /** Save a given image InputStream as a PNG file. */
  private boolean saveImageAsPng(InputStream inputImageStream, String outputPath)
      throws IOException {
    FileOutputStream outStream = new FileOutputStream(outputPath);

    if (!Files.exists(Paths.get(imageOutputDir), new LinkOption[0])) {
      Files.createDirectory(Paths.get(imageOutputDir));
    }

    BufferedImage inputImage = ImageIO.read(inputImageStream);

    boolean result = ImageIO.write(inputImage, "PNG", outStream);

    outStream.close();
    inputImageStream.close();

    return result;
  }

  /**
   *  Extracts and saves an attachment to the file system.
   *
   * @param p attachment part
   * @return the path to the saved attachment
   * @throws Exception IOException
   */
  private String saveAttachmentPart(Part p) throws Exception {

    String attachmentsOutputDir = "test-output/attachments/";
    String attachmentOutputPath =
        attachmentsOutputDir + formatter.format(new Date().getTime()) + "_";

    if (!Files.exists(Paths.get(attachmentsOutputDir), new LinkOption[0])) {
      Files.createDirectory(Paths.get(attachmentsOutputDir));
    }

    if (p.getContentType().contains("IMAGE")) { //check if attachment is an image
      String fileName = p.getFileName();
      attachmentOutputPath = attachmentOutputPath + fileName.replaceAll("\\.[a-zA-Z0-9\\\\._]{3,}", ".PNG");

      try {
        boolean result = saveImageAsPng((InputStream) p.getContent(), attachmentOutputPath);

        if (result) {
          Logger.info("Image converted successfully.");
        } else {
          Logger.info("Could not convert image.");
        }
      } catch (IOException ex) {
        Logger.error("Error during image conversion.");
        Logger.error(ex.toString());
      }
    } else {
      MimeBodyPart part = (MimeBodyPart) p;

      attachmentOutputPath = attachmentOutputPath + part.getFileName();
      InputStream inputStream = part.getInputStream();
      FileOutputStream outputStream = new FileOutputStream(attachmentOutputPath);

      byte[] buffer = new byte[1024];
      int bytesRead;

      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      outputStream.close();
    }

    return attachmentOutputPath;
  }

  /**
   * Delete specified email from inbox.
   *
   * @param message Specific email that needs to be deleted
   */
  public void deleteMessage(Message message) throws MessagingException {
    connect();
    Folder emailFolder = store.getFolder("INBOX");
    Logger.info("Deleting message");
    emailFolder.open(Folder.READ_WRITE);
    message.setFlag(Flags.Flag.DELETED, true);
    emailFolder.close(true);
  }

  private void deleteMessages() throws MessagingException {
    Folder emailFolder = store.getFolder("INBOX");
    emailFolder.open(Folder.READ_WRITE);
    // retrieve the messages from the folder in an array and print it
    Message[] messages = emailFolder.getMessages();
    for (int i = 0; i < messages.length; i++) {
      Message message = messages[i];
      // set the DELETE flag to true
      message.setFlag(Flags.Flag.DELETED, true);
    }
// expunges the folder to remove messages which are marked deleted
    emailFolder.close(true);
  }

  /**
   * Deletes all emails in inbox.
   */
  public void cleanUp() throws MessagingException {
    connect();
    deleteMessages();
  }

  public int numOfMessagesInInbox() {
    connect();

    try {
      emailFolder = store.getFolder(INBOX_FOLDER);
      emailFolder.open(Folder.READ_ONLY);
      return emailFolder.getMessageCount();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public Message[] getMessagesWhereBodyContains(String bodyContents) {
    Message[] messages;
    if (!connected) {
      connect();
    }

    try {
      //Open the folder
      emailFolder = store.getFolder(INBOX_FOLDER);
      emailFolder.open(Folder.READ_WRITE);

      GmailRawSearchTerm gmailRawSearchTerm = new GmailRawSearchTerm(bodyContents);
      messages = emailFolder.search(gmailRawSearchTerm);
      if (messages.length > 0) {
        return messages;
      }
    } catch (Exception e) {
      Logger.debug(e.toString());
    }
    return null;
  }
}
