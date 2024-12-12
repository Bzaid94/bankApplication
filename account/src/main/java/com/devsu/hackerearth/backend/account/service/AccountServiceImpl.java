package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> getAll() {
        // Get all accounts
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        // Get accounts by id
        Optional<Account> optAccount = accountRepository.findById(id);
        return optAccount.map(this::mapToDto).orElse(null);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        // Create account
        Account account = mapToEntity(accountDto);
        Account saveAccount = accountRepository.save(account);
        return mapToDto(saveAccount);
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        // Update account
        if (accountDto.getId() == null) {
            throw new IllegalArgumentException("Account ID is requerired");
        }
        Optional<Account> optAccount = accountRepository.findById(accountDto.getId());
        if (optAccount.isPresent()) {
            Account account = optAccount.get();
            account.setNumber(accountDto.getNumber());
            account.setType(accountDto.getType());
            account.setInitialAmount(accountDto.getInitialAmount());
            account.setActive(accountDto.isActive());
            account.setClientId(accountDto.getClientId());
            Account updateAccount = accountRepository.save(account);
            return mapToDto(updateAccount);
        } else {
            throw new EntityNotFoundException("Account not found");
        }
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Partial update account
        Optional<Account> optAccount = accountRepository.findById(id);
        if(optAccount.isPresent()){
            Account account = optAccount.get();
            account.setActive(partialAccountDto.isActive());
            Account updateAccount = accountRepository.save(account);

            return mapToDto(updateAccount);
        }else{
            throw new EntityNotFoundException("Account not found");
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Account> optAccount = accountRepository.findById(id);
        if(optAccount.isPresent()){
            accountRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Account not found");
        }
        // Delete account
    }

    private AccountDto mapToDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.isActive(),
                account.getClientId());
    }

    private Account mapToEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setId(accountDto.getId());
        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        return account;
    }

    @Override
    public List<Account> findByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }
}
