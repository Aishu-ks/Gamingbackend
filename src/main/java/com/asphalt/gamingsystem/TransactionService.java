package com.asphalt.gamingsystem;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionsRepository repo;

    // ✅ Create Transaction
    public Transactions create(Transactions transaction) {
        log.info("Creating transaction for memberId: {} and gameId: {}",
                transaction.getMemberId(), transaction.getGameId());

        transaction.setId(null); // Let MongoDB assign ObjectId

        if (transaction.getDateTime() == null) {
            transaction.setDateTime(new Date()); // default now()
        }

        validate(transaction);

        return repo.save(transaction);
    }

    // ✅ Find all transactions
    public List<Transactions> findAll() {
        log.info("Finding all transactions");
        return repo.findAll();
    }

    // ✅ Find by Id
    public Transactions findById(String id) {
        log.info("Finding transaction by id {}", id);
        Optional<Transactions> optionalTransaction = repo.findById(id);

        if (optionalTransaction.isEmpty()) {
            log.error("Attempted to find non-existing transaction id: {}", id);
            throw new ResourceNotFoundException("Transaction not found: " + id);
        }

        return optionalTransaction.get();
    }

    // ✅ Update
    public Transactions update(String id, Transactions transaction) {
        Optional<Transactions> optionalTransaction = repo.findById(id);

        if (optionalTransaction.isEmpty()) {
            log.error("Attempted to update non-existing transaction id: {}", id);
            throw new ResourceNotFoundException("Transaction not found: " + id);
        }

        log.info("Updating transaction by id {}", id);

        Transactions oldTransaction = optionalTransaction.get();
        oldTransaction.setMemberId(transaction.getMemberId());
        oldTransaction.setGameId(transaction.getGameId());
        oldTransaction.setAmount(transaction.getAmount());
        oldTransaction.setDateTime(transaction.getDateTime() != null
                ? transaction.getDateTime()
                : oldTransaction.getDateTime());

        return repo.save(oldTransaction);
    }

    // ✅ Delete
    public boolean delete(String id) {
        Optional<Transactions> optionalTransaction = repo.findById(id);

        if (optionalTransaction.isEmpty()) {
            log.error("Attempted to delete non-existing transaction id: {}", id);
            throw new ResourceNotFoundException("Transaction not found: " + id);
        }

        log.info("Deleting transaction by id {}", id);
        repo.deleteById(id);
        return true;
    }

    // ✅ Validation
    private void validate(Transactions transaction) {
    	if (transaction.getAmount() == null || transaction.getAmount() <= 0) {
    	    log.error("Amount must be greater than zero");
    	    throw new BusinessException("Amount must be greater than zero");
    	}


        if (transaction.getMemberId() == null) {
            log.error("MemberId cannot be null");
            throw new BusinessException("MemberId cannot be null");
        }

        if (transaction.getGameId() == null) {
            log.error("GameId cannot be null");
            throw new BusinessException("GameId cannot be null");
        }
    }
}
