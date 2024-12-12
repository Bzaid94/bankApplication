package com.devsu.hackerearth.backend.client.controller;

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

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping("/getAllClients")
	public ResponseEntity<List<ClientDto>> getAll(){
		// api/clients
		// Get all clients
		List<ClientDto> clients = clientService.getAll();
		return ResponseEntity.ok(clients);
	}

	@GetMapping("/getClient/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id){
		// api/clients/{id}
		// Get clients by id
		ClientDto clientDto = clientService.getById(id);
		return clientDto != null ? ResponseEntity.ok(clientDto) : ResponseEntity.notFound().build();
	}

	@PostMapping("/createClient")
	public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto){
		// api/clients
		// Create client
		ClientDto createClient = clientService.create(clientDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createClient);
	}

	@PutMapping("/updateClient/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto){
		// api/clients/{id}
		// Update client
		clientDto.setId(id);
		ClientDto udpateClient = clientService.update(clientDto);
		return udpateClient != null ? ResponseEntity.ok(udpateClient) : ResponseEntity.notFound().build();
	}

	@PatchMapping("/partialUpdate/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id, @RequestBody PartialClientDto partialClientDto){
		// api/accounts/{id}
		// Partial update accounts
		ClientDto partialUpdate = clientService.partialUpdate(id, partialClientDto);
		return partialUpdate != null ? ResponseEntity.ok(partialUpdate) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		// api/clients/{id}
		// Delete client
		try {
			clientService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
