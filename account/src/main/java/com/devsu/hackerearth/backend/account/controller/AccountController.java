package com.devsu.hackerearth.backend.account.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/getAllAccounts")
	public ResponseEntity<List<AccountDto>> getAll(){
		// api/accounts
		// Get all accounts
		List<AccountDto> accountDtos = accountService.getAll();
		return ResponseEntity.ok(accountDtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> get(@PathVariable Long id){
		// api/accounts/{id}
		// Get accounts by id
		AccountDto accountDto = accountService.getById(id);
		return accountDto != null ? ResponseEntity.ok(accountDto) : ResponseEntity.notFound().build();
	}

	@PostMapping("/createAccount")
	public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto){
		// api/accounts
		// Create accounts
		AccountDto createAccount = accountService.create(accountDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createAccount);
	}

	@PutMapping("/updateAccount/{id}")
	public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto){
		// api/accounts/{id}
		// Update accounts
		accountDto.setId(id);
		AccountDto updateAccount = accountService.update(accountDto);
		return updateAccount != null ? ResponseEntity.ok(updateAccount) : ResponseEntity.notFound().build();
	}

	@PatchMapping("/partialUpdate/{id}")
	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id, @RequestBody PartialAccountDto partialAccountDto){
		// api/accounts/{id}
		// Partial update accounts
		AccountDto partialUpdate = accountService.partialUpdate(id, partialAccountDto);
		return partialUpdate != null ? ResponseEntity.ok(partialUpdate) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		// api/accounts/{id}
		// Delete accounts
		try {
			accountService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}

