package com.paylease.app.qa.framework.utility.demos;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchFileDao;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchFile;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DatabaseClientDemo {

  public static void main(String[] args) {

    //Create Connection
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();

    Logger.info("\n*******************************************");
    Logger.info("Transaction Table CRUD Ops...");
    Logger.info("*******************************************");

    //DB Query: Update the transaction's transaction date (to the current date/time)
    Logger.info("\n-----------------------------------------");
    Logger.info("DB Query: Update the transaction's transaction date");
    Logger.info("(to the current date/time)");
    Logger.info("\n-----------------------------------------");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date currentDateTime = new Date();

    Transaction transaction = new Transaction();
    long transactionId = 111538513;

    transaction.setTransactionId(transactionId);
    transaction.setTransactionDate(dateFormat.format(currentDateTime)); //updated date

    TransactionDao transactionDao = new TransactionDao();

    Connection connection = dataBaseConnector.getConnection();

    int numOfRowsAffected = transactionDao.update(connection, transaction);
    Logger.info("Rows Affected: " + numOfRowsAffected);

    //DB Query: Find transaction by trans_id
    Logger.info("\n-----------------------------------------");
    Logger.info("DB Query: Find transaction by trans_id");
    Logger.info("\n-----------------------------------------");
    transactionId = 111538513;
    int limit = 50;
    ArrayList<Transaction> transactionsList = transactionDao
        .findById(connection, transactionId, limit);
    for (Transaction trans : transactionsList) {
      Logger.info("Transaction Id: " + trans.getTransactionId());
      Logger.info("Transaction Date: " + trans.getTransactionDate());
    }

    Logger.info("\n*******************************************");
    Logger.info("Batch Items Table CRUD Ops...");
    Logger.info("*******************************************");

    //DB Query: Find batch files by trans_id
    Logger.info("\n-----------------------------------------");
    Logger.info("DB Query: Find batch item by trans_id");
    Logger.info("\n-----------------------------------------");

    transactionId = 91718528;
    limit = 50;

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findByTransId(connection, transactionId, limit);
    for (BatchItem batchItem : batchItemsList) {
      Logger.info("Transaction Id: " + batchItem.getTransId());
      Logger.info("File Id: " + batchItem.getFileId());
    }

    Logger.info("\n*******************************************");
    Logger.info("Batch Files Table CRUD Ops...");
    Logger.info("*******************************************");

    //DB Query: Update the batch item
    Logger.info("\n-----------------------------------------");
    Logger.info("DB Query: Update the batch item");
    Logger.info("\n-----------------------------------------");
    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    currentDateTime = new Date();

    BatchFile batchFile = new BatchFile();
    long fileId = 48643;
    batchFile.setFileId(fileId);
    batchFile.setHasBeenSent(1);
    batchFile.setSentDate(dateFormat.format(currentDateTime));
    batchFile.setHasBeenProcessed(1);

    BatchFileDao batchFileDao = new BatchFileDao();

    numOfRowsAffected = batchFileDao.update(connection, batchFile);
    Logger.info("Rows Affected: " + numOfRowsAffected);

    //DB Query: Find batch files by trans_id
    Logger.info("\n-----------------------------------------");
    Logger.info("DB Query: Find batch files by file_id");
    Logger.info("\n-----------------------------------------");

    limit = 50;
    ArrayList<BatchFile> batchFilesList = batchFileDao.findById(connection, fileId, limit);
    for (BatchFile bf : batchFilesList) {
      Logger.info("File Id: " + bf.getFileId());
      Logger.info("Has Been Processed: " + bf.getHasBeenProcessed());
      Logger.info("Has Been Sent: " + bf.getHasBeenSent());
      Logger.info("Sent Date: " + bf.getSentDate());
    }
  }
}
