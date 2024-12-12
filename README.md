# Bank Application
## NOTAS: 

No pude generar internamente la colleccion debido a un error con la version free de Thunder Client
![image](https://github.com/user-attachments/assets/2e20be88-fe1e-4259-a3b3-fe751b80c6e9)

## Postman Requests

**1. Accounts**
**1.1 Obtener todas las cuentas:**

- **Método:** GET
- **Endpoint:** `/api/accounts/getAllAccounts`

**1.2 Obtener una cuenta por ID:**

- **Método:** GET
- **Endpoint:** `/api/accounts/{id}`

*1.3 Crear una cuenta:**

- **Método:** POST
- **Endpoint:** `/api/accounts/createAccount`
- **Body:**  `AccountDto` 

**1.4 Actualizar una cuenta:**

- **Método:** PUT
- **Endpoint:** `/api/accounts/updateAccount/{id}`

**1.5 Actualizar parcialmente una cuenta:**

- **Método:** PATCH
- **Endpoint:** `/api/accounts/partialUpdate/{id}`

**1.6 Eliminar una cuenta:**

- **Método:** DELETE
- **Endpoint:** `/api/accounts/{id}`

**2.1 Obtener todas las transacciones:**

- **Método:** GET
- **Endpoint:** `/api/transactions/getAllTransactions`

**2.2 Obtener una transacción por ID:**

- **Método:** GET
- **Endpoint:** `/api/transactions/{id}`

**2.3 Crear una transacción:**

- **Método:** POST
- **Endpoint:** `/api/transactions/createTransaction`
- **Body:**  `TransactionDto` 

**2.4 Obtener reporte de movimientos:**

- **Método:** GET
- **Endpoint:** `/api/transactions/clients/{clientId}/report`
- **Parámetros de consulta:**
    - `dateTransactionStart`: Fecha de inicio del rango de fechas (formato "yyyy-MM-dd").
    - `dateTransactionEnd`: Fecha de fin del rango de fechas (formato "yyyy-MM-dd").

**3. Clientes (Clients)**

**3.1 Obtener todos los clientes:**

- **Método:** GET
- **Endpoint:** `/api/clients/getAllClients`

**3.2 Obtener un cliente por ID:**

- **Método:** GET
- **Endpoint:** `/api/clients/getClient/{id}` 

**3.3 Crear un cliente:**

- **Método:** POST
- **Endpoint:** `/api/clients/createClient`
- **Body:**  `ClientDto` 

**3.4 Actualizar un cliente:**

- **Método:** PUT
- **Endpoint:** `/api/clients/updateClient/{id}` 
- **Body:** `ClientDto` 

**3.5 Actualizar parcialmente un cliente:**

- **Método:** PATCH
- **Endpoint:** `/api/clients/partialUpdate/{id}`
- **Body:** `PartialClientDto`.

**3.6 Eliminar un cliente:**

- **Método:** DELETE
- **Endpoint:** `/api/clients/{id}` 