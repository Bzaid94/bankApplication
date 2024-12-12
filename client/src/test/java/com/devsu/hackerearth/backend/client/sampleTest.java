package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@SpringBootTest
public class sampleTest {

	private ClientService clientService = mock(ClientService.class);
	private ClientController clientController = new ClientController(clientService);

    @Test
    void createClientTest() {
        // Arrange
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        ClientDto createdClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientService.create(newClient)).thenReturn(createdClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.create(newClient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
    }

    @Test
    void clientHaveValidData(){
        String name = "Brandon";
        String dni = "45659401013";
        String password = "MyPass!@";
        String gender = "Male";
        int age = 30;
        String address = "Guatemala";
        String phone = "502 32323323";
        boolean isActive = true;

        Client client = new Client();
        client.setDni(dni);
        client.setName(name);
        client.setPassword(password);
        client.setGender(gender);
        client.setAge(age);
        client.setAddress(address);
        client.setPhone(phone);
        client.setActive(isActive);

        assertNotNull(client);
        assertEquals(name, client.getName());
        assertEquals(password, client.getPassword());
        assertEquals(dni, client.getDni());
        assertEquals(gender, client.getGender());
        assertEquals(age, client.getAge());
        assertEquals(address, client.getAddress());
        assertEquals(phone, client.getPhone());
        assertEquals(isActive, client.isActive());
    }

    @Test
    void validateSaveDatabase(){
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientService.create(newClient)).thenReturn(newClient);
        ClientDto createClient = clientController.create(newClient).getBody();

        assertNotNull(createClient);
        assertEquals(newClient.getId(), createClient.getId());

        verify(clientService, times(1)).create(newClient);
    }
}
