package com.devsu.hackerearth.backend.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.devsu.hackerearth.backend.account.exception.InsufficientFundsException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public List<TransactionDto> getAll() {
        // Get all transactions
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        // Get transactions by id
        Optional<Transaction> optTransaction = transactionRepository.findById(id);
        return optTransaction.map(this::mapToDto).orElse(null);
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        // Create
        AccountDto accountDto = accountService.getById(transactionDto.getAccountId());
        Assert.isNull(accountDto, "Account not found");

        if (transactionDto.getAmount() <= 0 && Math.abs(transactionDto.getAmount()) > accountDto.getInitialAmount()) {
            throw new InsufficientFundsException("Insufficient funds, please enter other amount");
        }

        double newBalance = accountDto.getInitialAmount() + transactionDto.getAmount();

        Transaction transaction = new Transaction(
                transactionDto.getDate(),
                transactionDto.getType(),
                transactionDto.getAmount(),
                newBalance,
                transactionDto.getAccountId());

        Transaction saveTransaction = transactionRepository.save(transaction);
        return mapToDto(saveTransaction);
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        // Report
        List<Account> accounts = accountService.findByClientId(clientId);
        List<BankStatementDto> bankStatements = new ArrayList<>();
        for (Account account : accounts) {
            List<Transaction> transactions = transactionRepository.findByAccountIdAndDateBetween(account.getId(),
                    dateTransactionStart, dateTransactionEnd);
            if(!transactions.isEmpty()){
                for(Transaction transaction : transactions){
                    bankStatements.add(new BankStatementDto(
                        transaction.getDate(), clientId.toString(), account.getNumber(),
                        account.getType(), account.getInitialAmount(), account.isActive(),
                        transaction.getType(), transaction.getAmount(), transaction.getBalance()
                    ));
                }
            }

        }
        return bankStatements;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return transactionRepository.findLastByDate(accountId)
                .map(this::mapToDto)
                .orElse(null);
    }

    private TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getDate(),
                transaction.getType(), transaction.getAmount(),
                transaction.getBalance(), transaction.getAccountId());
    }
}
