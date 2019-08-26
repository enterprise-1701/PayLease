package com.paylease.app.qa.framework.utility.demos;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.kafka.KafkaProducerClient;
import java.util.Scanner;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaClientDemo {

  /**
   * Demo Kafka.
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {
    String topicName = AppConstant.USERNAME + "-default";
    String groupId = AppConstant.USERNAME + "-consumer";

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient.start();

    //Create the Producer
    KafkaProducerClient kafkaProducerClient = new KafkaProducerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);
    Producer<String, String> producer = kafkaProducerClient.createProducer();

    //Produces message based on data read in from the command line (if it's not "exit")
    String line = "";
    Scanner in = new Scanner(System.in);
    while (!line.equals("exit")) {
      ProducerRecord<String, String> rec = new ProducerRecord<String, String>(topicName, line);
      producer.send(rec);
      line = in.nextLine();
    }
    in.close();
    kafkaConsumerClient.getKafkaConsumer().wakeup();
    Logger.info("Stopping consumer .....");
    kafkaConsumerClient.join();
    Logger.info("Stopping producer .....");
    producer.close();

  }
}
