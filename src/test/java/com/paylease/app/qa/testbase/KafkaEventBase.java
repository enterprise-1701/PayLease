package com.paylease.app.qa.testbase;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.typesafe.config.ConfigException.Null;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

public class KafkaEventBase {
  public static final String TRANSACTION_CREATED       = "TRANSACTION_CREATED";
  public static final String DEPOSIT_CREATED           = "DEPOSIT_CREATED";
  public static final String DEPOSIT_OPENED            = "DEPOSIT_OPENED";
  public static final String DEPOSIT_ITEM_CREATED      = "DEPOSIT_ITEM_CREATED";
  public static final String ALL_DEPOSIT_ITEMS_CREATED = "ALL_DEPOSIT_ITEMS_CREATED";
  public static final String DEPOSIT_CLOSED            = "DEPOSIT_CLOSED";
  public static final String DEPOSIT_ITEM_MOVED        = "DEPOSIT_ITEM_MOVED";
  public static final String DEPOSIT_ITEM_VOIDED       = "DEPOSIT_ITEM_VOIDED";
  public static final String DEPOSIT_ITEM_RETURNED     = "DEPOSIT_ITEM_RETURNED";
  public static final String TRANSACTION_INTEGRATED    = "TRANSACTION_INTEGRATED";
  public static final String DEPOSIT_ITEM_INTEGRATED   = "DEPOSIT_ITEM_INTEGRATED";
  public static final String TRANSACTION_VOIDED        = "TRANSACTION_VOIDED";
  public static final String ALL_DEPOSIT_ITEMS_VOIDED  = "ALL_DEPOSIT_ITEMS_VOIDED";
  public static final String DEPOSIT_DELETED           = "DEPOSIT_DELETED";
  public static final String EXTERNAL_BATCH_DELETED = "EXTERNAL_BATCH_DELETED";
  public static final String BATCH_ITEM_VOIDED         = "BATCH_ITEM_VOIDED";
  public static final String BATCH_ITEM_RETURNED       = "BATCH_ITEM_RETURNED";
  public static final String MESSAGE_PROCESSED         = "MESSAGE_PROCESSED";
  public static final String PROPERTY_LOCK_ENTERED     = "PROPERTY_LOCK_ENTERED";

  private KafkaConsumerClient consumerClient;

  public KafkaEventBase(KafkaConsumerClient kafkaConsumerClient) {
    this.consumerClient = kafkaConsumerClient;
  }

  /**
   * Wait for kafka event to be found.
   *
   * @param eventName name of the event.
   * @param expectedMessage expected message for the event.
   * @throws Exception in case if event is not found.
   * @return String JSON message that matched the expectation
   */
  public String waitForEvent(String eventName, String expectedMessage) throws Exception {
    new JSONObject();
    JSONObject obj;

    String eventTriggered;

    JSONParser parser = new JSONParser();

    boolean eventNameFound = false;
    for (int i = 0; i < 45; i++) {
      ArrayList<String> consumerMessages = consumerClient.getConsumerMessageList();
      for (String message : consumerMessages) {
        obj = ((JSONObject) parser.parse(message));
        eventTriggered = obj.get("eventName").toString();
        if (eventTriggered.equals(eventName)) {
          eventNameFound = true;
          if (message.contains(expectedMessage)) {
            Logger.info("Found Event: " + message);
            return message;
          }
        }
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    if (eventNameFound) {
      throw new Exception(eventName + " was published but not with: " + expectedMessage);
    }
    throw new Exception(eventName + " was never published");
  }

  /**
   * Get event name from message on topic.
   *
   * @param transId transaction ID
   * @param kafkaConsumerClient kafka consumer client.
   * @param newWorld true if new world transaction
   * @return event name
   */
  public String getEventNameFromMessageOnTopic(String transId,
      KafkaConsumerClient kafkaConsumerClient, boolean newWorld) {

    String eventName = "";
    String transactionId = "";

    new JSONObject();
    JSONObject obj;

    for (int i = 0; i < 20; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      ArrayList<String> consumerMessages = kafkaConsumerClient.getConsumerMessageList();

      if (newWorld) {
        for (String message : consumerMessages) {
          JSONParser parser = new JSONParser();
          try {
            obj = ((JSONObject) parser.parse(message));
            JSONObject context = (JSONObject) obj.get("context");
            transactionId = context.get("transaction_id").toString();
            if (transactionId.equals(transId)) {
              eventName = obj.get("eventName").toString();
              break;
            } else {
              eventName = null;
            }
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
        if (!eventName.isEmpty()) {
          break;
        }
      } else {
        if (consumerMessages.size() >= 0) {
          JSONParser parser = new JSONParser();
          if (consumerMessages.size() == 0) {
            eventName = null;
            break;
          } else {
            try {
              for (String message1 : consumerMessages) {
                obj = ((JSONObject) parser.parse(message1));
                JSONObject context = (JSONObject) obj.get("context");
                if (null != context.get("transaction_id")) {
                  transactionId = context.get("transaction_id").toString();
                  Assert.assertNotEquals(transId, transactionId, "The transaction exists on the topic");
                }
              }
              eventName = null;
              break;
            } catch (ParseException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
    return eventName;
  }

  /**
   * Wait for event to process.
   *
   * @param eventName name of the event.
   * @param expectedMessage expected message.
   * @throws Exception in case if event is not processed.
   * @return String JSON message that matched expectation
   */
  public String waitForEventToProcess(String eventName, String expectedMessage) throws Exception {

    new JSONObject();
    JSONObject obj;

    String eventTriggered;

    JSONParser parser = new JSONParser();

    boolean eventNameFound = false;
    for (int i = 0; i < 45; i++) {
      ArrayList<String> consumerMessages = consumerClient.getConsumerMessageList();
      for (String message : consumerMessages) {
        obj = ((JSONObject) parser.parse(message));
        eventTriggered = obj.get("eventName").toString();
        if (eventTriggered.equals(MESSAGE_PROCESSED)) {
          obj = (JSONObject) obj.get("context");
          String eventProcessed = obj.get("processed_event_name").toString();
          if (eventProcessed.equals(eventName)) {
            eventNameFound = true;
            String messageProcessed = obj.get("processed_event_context").toString();
            if (messageProcessed.contains(expectedMessage)) {
              Logger.info("Found Event: " + message);
              return message;
            }
          }
        }
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    if (eventNameFound) {
      throw new Exception(eventName + " was processed but not with: " + expectedMessage);
    }
    throw new Exception(eventName + " was never processed");
  }

  public String getContextFromPayload(String eventName, String expectedValue) throws Exception {

    new JSONObject();
    JSONObject obj;

    String eventTriggered;
    String payloadValue = "";

    JSONParser parser = new JSONParser();

    boolean eventNameFound = false;
    for (int i = 0; i < 45; i++) {
      ArrayList<String> consumerMessages = consumerClient.getConsumerMessageList();
      for (String message : consumerMessages) {
        obj = ((JSONObject) parser.parse(message));
        eventTriggered = obj.get("eventName").toString();
        if (eventTriggered.equals(eventName)) {
          eventNameFound = true;
          if (message.contains(expectedValue)) {
            Logger.info("Found Event: " + message);
            JSONObject context = (JSONObject) obj.get("context");
            if(context.get(expectedValue) == null){
              return null;
            }
            payloadValue= context.get(expectedValue).toString();
            Logger.info("found prop_id: " +payloadValue );
            return payloadValue;
          }
        }
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    if (eventNameFound) {
      throw new Exception(eventName + " was published but not with: " + expectedValue);
    }
    throw new Exception(eventName + " was never published");
  }
}