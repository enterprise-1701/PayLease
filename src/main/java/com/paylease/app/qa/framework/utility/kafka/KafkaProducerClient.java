package com.paylease.app.qa.framework.utility.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;


/**
 * Kafka Producer Client.
 *
 * @author Jeffrey Walker
 */
public class KafkaProducerClient {

  private String groupId;
  private String kafkaBrokerUrl;
  private String username;
  private String password;
  private String topicName;

  /**
   * Instantiate a KAFKA Producer client.
   * @param kafkaBrokerUrl
   * @param username
   * @param password
   * @param topicName
   * @param groupId
   */
  public KafkaProducerClient(String kafkaBrokerUrl, String username, String password,
      String topicName, String groupId) {
    this.kafkaBrokerUrl = kafkaBrokerUrl;
    this.username = username;
    this.password = password;
    this.topicName = topicName;
    this.groupId = groupId;

  }

  /**
   * Add config properties to the producer.
   *
   * @return producer
   */
  public Producer<String, String> createProducer() {
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

    Producer<String, String> producer = new KafkaProducer<String, String>(configProperties);

    return producer;
  }
}