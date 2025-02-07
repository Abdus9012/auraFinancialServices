package com.example.aura.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.aura.model.Transaction;

@Service
public class TransactionService {
    private List<Transaction> flaggedTransactions = new ArrayList<>();
    private Map<String, List<Transaction>> userTransactions = new HashMap<>(); // for ord velocity
    private List<String> userTypicalLocations = List.of("Hyderabad", "Mumbai", "Chennai", "New Delhi", "Bengaluru");
    private List<String> trustedDevices = List.of("Redmi note 8", "Realme pro 12", "ios18.2");
    private static final int VELOCITY_LIMIT = 3;

    public void processTransaction(Transaction transaction){
        if(isFraudulent(transaction)){
            flaggedTransactions.add(transaction);
            System.out.println("[ALERT] : Transaction flagged as Fraudulent: " +transaction);
        } else {
            System.out.println("[INFO] : Transaction processed successfully");
        }
        recordTransaction(transaction);
    }

    private boolean isFraudulent(Transaction transaction){
        if(transaction.getAmount() > 250000){
            System.out.println("[ALERT] : High value transaction flagged for investigation");
            return true;
        }

        if(isUnusualLocation(transaction)){
            System.out.println("[ALERT] : Unusual Location flagged for investigation");
            return true;
        }
        
        if(isVelocityExceeded(transaction)){
            System.out.println("[ALERT] : Payment Velocity flagged for investigation");
            return true;
        }

        if(isDeviceMismatch(transaction)){
            System.out.println("[ALERT] : Device mismatch flagged for investigation");
        }
        return false;
    }

    private boolean isUnusualLocation(Transaction transaction){
        String transactionLocation = transaction.getLocation();
        if(!userTypicalLocations.contains(transactionLocation)){
            System.out.println(" [DEBUG] : Location "+transactionLocation+" is unusual for this customer");
            return true;
        }
        return false;
    }

    private boolean isDeviceMismatch(Transaction transaction){
        String deviceId = transaction.getDeviceId();
        if(!trustedDevices.contains(deviceId)){
            System.out.println(" [ALERT] : Device"+deviceId+" is unusual for this customer");
            return true;
        }
        return false;
    }

   private boolean isVelocityExceeded(Transaction transaction){
        String userId = transaction.getUserId();
        List<Transaction> userHistory = userTransactions.getOrDefault(userId, new ArrayList<>());

        //check if vel>3
        int transactionsInLastHour = 0;
        Long currentTime = System.currentTimeMillis();

        for(Transaction pastTransaction : userHistory){
            Long transactionTime = Long.parseLong(pastTransaction.getTimeStamp());
            if((currentTime - transactionTime) > 3600000){
                transactionsInLastHour++;
            }
        }
        return (transactionsInLastHour > VELOCITY_LIMIT);
   } 

    public List<Transaction> getFlaggedTransactions(){
        return flaggedTransactions;
    }

    private void recordTransaction(Transaction transaction){
        String userId = transaction.getUserId();
        List<Transaction> userHistory = userTransactions.getOrDefault(userId, new ArrayList<>());
        userHistory.add(transaction);

        //update this new transacn in the map
        userTransactions.put(userId, userHistory);
    }

    //after improvs in controller
    public boolean checkIfFraudulent(Transaction transaction){
        return isFraudulent(transaction);
    }
    
    public List<Transaction> transactions = new ArrayList<>();
    
    public List<Transaction> getUserTransactions(String userId) {
    // Assuming transactions is a List<Transaction> that stores all transactions
    return transactions.stream()
            .filter(transaction -> transaction.getUserId().equals(userId))
            .collect(Collectors.toList());
}

}
