package com.paylease.app.qa.framework.utility.demos;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.emailtool.EmailProcessor;
import com.paylease.app.qa.framework.utility.emailtool.MessagePart;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.Message;

/**
 * Demonstrates the functions of the Email Processor
 *
 * @author Jeffrey Walker
 */
public class EmailValidatorDemo {

  public static void main(String[] args) {
    EmailProcessor emailProcessor = new EmailProcessor();
    ArrayList<String> toAddressList = new ArrayList<>();
    Message[] messages;

    emailProcessor.connect();

    //--------------------------------DEMO EXAMPLES----------------------------------------------

    /**
     * Example 1: Fetches all messages in the folder.
     *
     */
    messages = emailProcessor.getAllMessages("INBOX");
    logResults(emailProcessor, messages, toAddressList);

    /**
     * Example 2: Fetch a specific message in the folder (with html).
     *
     */
    messages = emailProcessor.getMessage("INBOX", "welcome");
    logResults(emailProcessor, messages, toAddressList);

    /**
     * Example 3: Fetch a specific message in the folder (with inline images)
     *
     */
    messages = emailProcessor.getMessage("INBOX", "Olympics");
    logResults(emailProcessor, messages, toAddressList);

    /**
     * Example 4: Fetch a specific message in the folder (with attachments)
     *
     */
    messages = emailProcessor.getMessage("INBOX", "attachments");
    logResults(emailProcessor, messages, toAddressList);

    /**
     * Example 5: Get first matching message
     *
     */
    Message message = emailProcessor.getFirstMatchingMessage("INBOX", "changed");
    Logger.info("Subject: " + emailProcessor.getSubject(message));
    Logger.info("FROM: " + emailProcessor.getFromAddress(message));

    /**
     * Example 6: Get messages for a certain date range
     *
     */
    String startDateString = "01/01/2018";
    String endDateString = "25/01/2021";

    try {
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
      Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateString);

      Logger.info("Start date: " + startDate);
      Logger.info("End date " + endDate);

      messages = emailProcessor.getMessagesWithinRange("INBOX", "welcome", startDate, endDate);
      logResults(emailProcessor, messages, toAddressList);

    } catch (Exception e) {
      e.printStackTrace();
    }

    /**
     * Example 7: Get messages where keyword is found in message body.
     *
     */
    messages = emailProcessor.getMessagesWhereBodyContains("contains");
    logResults(emailProcessor, messages, toAddressList);

    /**
     * Example 8: Get messages where keyword is found in message body and has attached images (not inline).
     *
     */
    messages = emailProcessor.getMessage("INBOX", "attached image");
    logResults(emailProcessor, messages, toAddressList);

    //----------------------------------------------------------------------------------

    emailProcessor.close();
  }

  private static void logResults(EmailProcessor emailProcessor, Message messages[],
      ArrayList<String> toAddressList) {
    for (int i = 0; i < messages.length; i++) {
      Logger.info("################################################################");
      Logger.info("Processing email # " + i);
      //Subject
      Logger.info("Subject: " + emailProcessor.getSubject(messages[i]));

      //From Address List
      Logger.info("FROM: " + emailProcessor.getFromAddress(messages[i]));

      //To Address List
      toAddressList = emailProcessor.getToAddressList(messages[i]);
      Logger.info("TO List: " + toAddressList);

      //Body
      ArrayList<MessagePart> messageBodyPartsList = new ArrayList<>();
      messageBodyPartsList = emailProcessor.getBody(messages[i], messageBodyPartsList, true, true);
      for (int j = 0; j < messageBodyPartsList.size(); j++) {
        Logger.info("Message Body Part " + (j + 1));
        Logger.info("MIME Type: " + messageBodyPartsList.get(j).getMimeType());
        Logger.info(messageBodyPartsList.get(j).getContent());
      }
    }
  }
}

