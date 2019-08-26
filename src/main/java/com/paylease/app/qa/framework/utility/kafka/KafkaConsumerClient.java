package com.paylease.app.qa.framework.utility.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Kafka Consumer Client.
 *
 * @author Jeffrey Walker
 */
public class KafkaConsumerClient extends Thread {

  private String topicName;
  private String groupId;
  private String kafkaBrokerUrl;
  private KafkaConsumer<String, String> kafkaConsumer;
  private String username;
  private String password;
  private ArrayList<String> consumerMessageList;
  private boolean finish;

  public KafkaConsumerClient(String kafkaBrokerUrl, String username, String password,
      String topicName, String groupId) {
    this.kafkaBrokerUrl = kafkaBrokerUrl;
    this.username = username;
    this.password = password;
    this.topicName = topicName;
    this.groupId = groupId;
    this.consumerMessageList = new ArrayList<>();
  }

  public ArrayList<String> getConsumerMessageList() {
    return consumerMessageList;
  }

  public void run() {
    finish = false;
    String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
    String jaasCfg = String.format(jaasTemplate, username, password);

    String serializer = StringSerializer.class.getName();
    String deserializer = StringDeserializer.class.getName();
    Properties configProperties = new Properties();
    configProperties.put("bootstrap.servers", kafkaBrokerUrl);
    configProperties.put("group.id", groupId);
    configProperties.put("enable.auto.commit", "true");
    configProperties.put("auto.commit.interval.ms", "1000");
    configProperties.put("auto.offset.reset", "earliest");
    configProperties.put("session.timeout.ms", "20000");
    configProperties.put("key.deserializer", deserializer);
    configProperties.put("value.deserializer", deserializer);
    configProperties.put("key.serializer", serializer);
    configProperties.put("value.serializer", serializer);
    configProperties.put("security.protocol", "SASL_SSL");
    configProperties.put("sasl.mechanism", "SCRAM-SHA-256");
    configProperties.put("sasl.jaas.config", jaasCfg);

    // Figure out where to start processing messages from
    kafkaConsumer = new KafkaConsumer<String, String>(configProperties);
    kafkaConsumer.subscribe(Arrays.asList(topicName));

    // Start processing messages
    try {
      while (!finish) {
        Duration pollInterval = Duration.ofMillis(0);
        ConsumerRecords<String, String> records = kafkaConsumer.poll(pollInterval);
        for (ConsumerRecord<String, String> record : records) {
          consumerMessageList.add(record.value());
          System.out.println("[MyConsumer]: " + consumerMessageList);
        }
      }
    } catch (Exception ex) {
      //stops consumer here
    } finally {
      kafkaConsumer.close();
    }
  }

  public KafkaConsumer<String, String> getKafkaConsumer() {
    return this.kafkaConsumer;
  }

  public void finish() {
    finish = true;
  }
}
