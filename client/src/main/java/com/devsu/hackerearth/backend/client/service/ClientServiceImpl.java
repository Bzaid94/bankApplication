package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		// Get all clients
		List<Client> clients = clientRepository.findAll();
		return clients.stream()
			.map(this::mapToDto)
			.collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		// Get clients by id
		Optional<Client> optClient = clientRepository.findById(id);
		return optClient.map(this::mapToDto).orElse(null);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		// Create client
		Client client = mapToEntity(clientDto);
		Client saveClient = clientRepository.save(client);
		return mapToDto(saveClient);
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		// Update client
		if(clientDto.getId() == null){
			throw new IllegalArgumentException("Client ID is required");
		}
		
		Optional<Client> optClient = clientRepository.findById(clientDto.getId());
		if(optClient.isPresent()){
			Client client = optClient.get();
			client.setDni(clientDto.getDni());
			client.setName(clientDto.getName());
			client.setPassword(clientDto.getPassword());
			client.setGender(clientDto.getGender());
			client.setAge(clientDto.getAge());
			client.setAddress(clientDto.getAddress());
			client.setPhone(clientDto.getPhone());
			client.setActive(clientDto.isActive());

			Client updateClient = clientRepository.save(client);
			return mapToDto(updateClient);
		}else{
			throw new EntityNotFoundException("Client not found");
		}
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		// Partial update account
		Optional<Client> optClient = clientRepository.findById(id);
		if(optClient.isPresent()){
			Client client = optClient.get();
			client.setActive(partialClientDto.isActive());
			Client updatePartial = clientRepository.save(client);

			return mapToDto(updatePartial);
		}else{
			throw new EntityNotFoundException("Client not found");
		}
	}

	@Override
	public void deleteById(Long id) {
		// Delete client
		Optional<Client> optClient = clientRepository.findById(id);
		if(optClient.isPresent()){
			clientRepository.deleteById(id);
		}else{
			throw new EntityNotFoundException("Client not found");
		}
	}

	private ClientDto mapToDto(Client client) {
		return new ClientDto(client.getId(), client.getDni(),
				client.getName(), client.getPassword(), client.getGender(),
				client.getAge(), client.getAddress(), client.getPhone(), client.isActive());
	}

	private Client mapToEntity(ClientDto clientDto) {
		Client client = new Client();
		client.setDni(clientDto.getDni());
		client.setName(clientDto.getName());
		client.setPassword(clientDto.getPassword());
		client.setGender(clientDto.getGender());
		client.setAge(clientDto.getAge());
		client.setAddress(clientDto.getAddress());
		client.setPhone(clientDto.getPhone());
		client.setActive(clientDto.isActive());
		return client;
	}
}
