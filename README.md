
# Car Rental Challenge

![License](https://img.shields.io/github/license/squad-13-solutis-dev-trail-2024/car-rental-challenge-solutis-school-dev-trail)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green)
![Build](https://img.shields.io/badge/Build-Passing-brightgreen)

## Overview

O **Car Rental Challenge** é uma aplicação backend desenvolvida como parte do desafio da Solutis School Dev Trail 2024. Este projeto visa simular um sistema de locadora de veículos, onde é possível gerenciar clientes, veículos e aluguéis de maneira eficiente e segura.

## Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Java 21**:
- **Spring Boot 3.3.3**  
- **Spring Data JPA**
- **Spring Test**
- **MySQL**
- **Swagger**
- **Intellij IDEA Ultimate 2024.2.1**
- **GitHub Copilot 1.5.20.6554**

Essa combinação de tecnologias proporciona uma base sólida para o desenvolvimento de uma aplicação robusta, escalável e bem documentada.

## Funcionalidades

O sistema de aluguel de carros oferece as seguintes funcionalidades, organizadas por controlador:


## ✨ Funcionalidades

O sistema de aluguel de carros oferece uma ampla gama de funcionalidades, cuidadosamente organizadas por controlador para facilitar a navegação e o entendimento:

### 1. 🧑‍💼 Gerenciamento de Clientes (`ClienteController`)

| Funcionalidade                 | Descrição                                                                        | Método HTTP | Endpoint                                |
|--------------------------------|----------------------------------------------------------------------------------|-------------|-----------------------------------------|
| **Cadastro de Clientes**       | Permite o cadastro de novos clientes (motoristas) com informações detalhadas.    | `POST`      | `/api/v1/cliente`                       |
| **Detalhamento de Clientes**   | Exibe os detalhes básicos de um cliente específico.                              | `GET`       | `/api/v1/cliente/{id}`                  |
| **Listagem de Clientes**       | Lista todos os clientes cadastrados, com paginação e ordenação por nome.         | `GET`       | `/api/v1/cliente`                       |
| **Detalhamento Completo**      | Mostra todos os detalhes de um cliente, incluindo histórico de aluguéis.         | `GET`       | `/api/v1/cliente/detalhar-completo/{id}`|
| **Atualização de Clientes**    | Permite atualizar informações de um cliente existente.                           | `PATCH`     | `/api/v1/cliente`                       |
| **Desativação de Clientes**    | Desativa um cliente, impedindo-o de realizar novos aluguéis.                     | `PATCH`     | `/api/v1/cliente/{id}`                  |
| **Exclusão de Clientes**       | Remove um cliente do sistema.                                                    | `DELETE`    | `/api/v1/cliente/{id}`                  |

### 2. ⚙️ Gerenciamento de Veículos (`CarroController`)

| Funcionalidade                        | Descrição                                                                        | Método HTTP | Endpoint                                                           |
|---------------------------------------|----------------------------------------------------------------------------------|-------------|--------------------------------------------------------------------|
| **Cadastro de Veículos**              | Permite o cadastro de novos veículos com informações completas.                  | `POST`      | `/api/v1/carro`                                                    |
| **Detalhamento de Veículos**          | Exibe os detalhes básicos de um veículo específico.                              | `GET`       | `/api/v1/carro/{id}`                                               |
| **Listagem de Veículos**              | Lista todos os veículos cadastrados, com paginação e ordenação por modelo.       | `GET`       | `/api/v1/carro`                                                    |
| **Detalhamento Completo de Veículos** | Mostra todos os detalhes de um veículo, incluindo histórico de aluguéis.         | `GET`       | `/api/v1/carro/detalhar-completo/{id}`                             |
| **Pesquisa de Veículos**              | Permite pesquisar veículos por diversos critérios (nome, placa, etc.).           | `GET`       | `/api/v1/carro/pesquisar`                                          |
| **Listagem de Veículos Disponíveis**  | Lista todos os veículos disponíveis para aluguel, com paginação.                 | `GET`       | `/api/v1/carro/disponiveis`                                        |
| **Listagem de Veículos Alugados**     | Lista todos os veículos que estão atualmente alugados, com paginação.            | `GET`       | `/api/v1/carro/alugados`                                           |
| **Bloquear/Disponibilizar Aluguel**   | Permite bloquear ou disponibilizar um veículo para aluguel.                      | `PATCH`     | `/api/v1/carro/{id}/bloquear`, `/api/v1/carro/{id}/disponibilizar` |
| **Atualização de Veículos**           | Permite atualizar informações de um veículo existente.                           | `PATCH`     | `/api/v1/carro`                                                    |
| **Desativação de Veículos**           | Desativa um veículo, tornando-o indisponível para aluguel (exclusão lógica).     | `DELETE`    | `/api/v1/carro/{id}`                                               |

### 3. 📅 Gerenciamento de Aluguéis (`AluguelController`)

| Funcionalidade                    | Descrição                                                                               | Método HTTP | Endpoint                                |
|-----------------------------------|-----------------------------------------------------------------------------------------|-------------|-----------------------------------------|
| **Criação de Aluguéis (Reserva)** | Permite criar um novo aluguel, associando um cliente a um veículo e definindo as datas. | `POST`      | `/api/v1/aluguel/alugar`                |
| **Visualização de Aluguéis**      | Exibe os detalhes básicos de um aluguel específico.                                     | `GET`       | `/api/v1/aluguel/{id}`                  |
| **Listagem de Aluguéis**          | Lista todos os aluguéis cadastrados, com paginação.                                     | `GET`       | `/api/v1/aluguel`                       |
| **Detalhamento Completo**         | Mostra todos os detalhes de um aluguel, incluindo informações do cliente e do veículo.  | `GET`       | `/api/v1/aluguel/detalhar-completo/{id}`|
| **Confirmação de Aluguéis**       | Permite confirmar um aluguel, definindo o tipo de pagamento.                            | `PATCH`     | `/api/v1/aluguel/confirmar/{id}`        |
| **Encerramento de Aluguéis**      | Permite finalizar um aluguel, registrando a data de devolução do veículo.               | `PATCH`     | `/api/v1/aluguel/finalizar/{id}`        |
| **Cancelamento de Aluguéis**      | Permite cancelar um aluguel.                                                            | `PATCH`     | `/api/v1/aluguel/cancelar/{id}`         |

  
## Estrutura do Projeto

```
car-rental-challenge
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── br
│   │   │   │   ├── com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail
│   │   │   │   │   ├── config       # Contém os configurações da API
│   │   │   │   │   ├── controller   # Controladores da API
│   │   │   │   │   ├── dto          # Objetos de Transferência de Dados (DTO)
│   │   │   │   │   ├── entity       # Entidades do projeto
│   │   │   │   │   ├── exception    # Classes de tratamento de exceções
|   |   |   |   |   ├── generator    # Classes para geração de Boleto e PixKey
│   │   │   │   │   ├── handler      # Classes de gerenciamento de exceções
│   │   │   │   │   ├── entity       # Classes de entidades
│   │   │   │   │   ├── repository   # Interfaces de repositório
│   │   │   │   │   ├── service      # Classes de serviço
│   │   │   │   │   ├── spec         # Tratamento de exceções
│   │   │   │   │   ├── validation   # Criação de 'Annotations' para validações persnalizadas
│   │   │   └── resources
│   │   │       ├── application.properties  # Configurações da aplicação
│   │   └── test
│   │       ├── java
│   │       │   ├── br
│   │       │   │   ├── com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail
│   │       │   │       ├── controller   # Testes dos controladores
│   │       │   │       ├── service      # Testes dos serviços
│   └── pom.xml  # Arquivo de configuração do Maven
│
└── README.md
```

## Como Executar o Projeto

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/squad-13-solutis-dev-trail-2024/car-rental-challenge-solutis-school-dev-trail.git
   ```
2. **Navegue até o diretório do projeto**:
   ```bash
   cd car-rental-challenge-solutis-school-dev-trail
   ```
3. **Configure o banco de dados**:
   - Certifique-se de ter um banco de dados MySQL rodando.
   - Atualize o arquivo `application.properties` com as configurações do seu banco de dados.

4. **Execute a aplicação**:
   ```bash
   mvn spring-boot:run
   ```

5. **Acesse a documentação da API**:
   - A documentação estará disponível em: `http://localhost:8080/swagger-ui.html`

## Contribuindo

Contribuições são bem-vindas! Por favor, siga as diretrizes abaixo para contribuir:

1. Faça um fork do repositório.
2. Crie uma nova branch (`git checkout -b feature/minha-feature`).
3. Commit suas mudanças (`git commit -m 'Adiciona minha feature'`).
4. Envie suas mudanças para a branch (`git push origin feature/minha-feature`).
5. Abra um Pull Request.

## ✍️ Autores:

Desenvolvedores que participaram da resolução dos exercícios:

- **Aldemir Carlos**   [GitHub](https://github.com/aldemaas)          [LinkedIn](https://www.linkedin.com/in/aldemir-carlos/)            [E-mail](mailto:aldemirc22@gmail.com)
- **Gabriel de Abreu** [GitHub](https://github.com/AzvedoGabriel)     [LinkedIn](https://www.linkedin.com/in/azevedo-gabriel-ssa/)       [E-mail](mailto:contato.gabrielazevedo0@gmail.com)
- **Kauê Alexandre**   [GitHub](https://github.com/bugkaue)           [LinkedIn](https://www.linkedin.com/in/bugkaue/)                   [E-mail](mailto:alexkauezinho@gmail.com)
- **Pedro Messias**    [GitHub](https://github.com/PedroMessiasxD)    [LinkedIn](https://www.linkedin.com/in/pedromessiasxd/)            [E-mail](mailto:pemlucena@gmail.com)
- **Pedro Rocha**      [GitHub](https://github.com/Pedro-E-S-R)       [LinkedIn](https://www.linkedin.com/in/pedro-e-s-r/)               [E-mail](mailto:pedroemanoel323@gmail.com)
- **Suerdo Flaubert**  [GitHub](https://github.com/Suerdo)            [LinkedIn](https://www.linkedin.com/in/suerdo-flaubert-78b3a4194/) [E-mail](mailto:suerdocampos@gmail.com)
- **Vinícius Andrade** [GitHub](https://github.com/viniciusdsandrade) [LinkedIn](https://www.linkedin.com/in/viniciusdsandrade/)         [E-mail](mailto:vinicius_andrade2010@hotmail.com)
---
