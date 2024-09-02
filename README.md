# Car Rental Challenge

## 🚗 Overview

O **Car Rental Challenge** é uma aplicação backend robusta e eficiente, desenvolvida para simular um sistema completo de locadora de veículos, abrangendo desde o cadastro do cliente até a efetivação do aluguel. Criada como parte do desafio da Solutis School Dev Trail 2024, esta aplicação oferece uma experiência completa para o usuário, permitindo:

**Cadastro de Clientes:**

- Clientes em potencial podem se cadastrar facilmente, fornecendo informações básicas como nome, data de nascimento, CPF e número da CNH.
- O sistema garante a validade do endereço de e-mail para evitar registros duplicados.
- Após o cadastro, o cliente é redirecionado para a página inicial, onde pode acessar os serviços da locadora.

**Escolha de Veículo para Aluguel:**

- Clientes cadastrados podem navegar por uma lista completa de veículos disponíveis, com informações detalhadas como fabricante, modelo, categoria, acessórios e preço por dia.
- Filtros intuitivos permitem que o cliente encontre o veículo ideal com base em suas necessidades, como categoria e acessórios específicos.
- Uma página de detalhes do veículo fornece informações completas, incluindo especificações técnicas e descrição.
- O cliente pode selecionar o período de aluguel e adicionar o veículo ao seu carrinho.
- O carrinho de aluguel exibe um resumo dos veículos selecionados, datas de aluguel e custo total estimado, permitindo revisões e ajustes antes da confirmação da reserva.

**Efetivação do Aluguel:**

- Após revisar o carrinho, o cliente pode confirmar a reserva e efetivar o aluguel.
- Uma página de resumo da reserva exibe todos os detalhes do aluguel, incluindo informações do veículo, datas, custo total e termos de aluguel.
- O cliente deve revisar e concordar com os termos e condições antes de prosseguir.
- O sistema permite a escolha do método de pagamento e a inserção das informações de pagamento (simulado).
- Após a confirmação do pagamento, o cliente recebe uma confirmação na tela com todos os detalhes do aluguel, informações de contato e a fatura.
- O sistema marca o veículo como "reservado" e bloqueia as datas de aluguel no calendário.
- O cliente pode acessar seus aluguéis confirmados e detalhes futuros através de sua conta.


Em resumo, o **Car Rental Challenge** oferece uma solução completa para o gerenciamento de uma locadora de veículos, proporcionando uma experiência intuitiva e eficiente para os clientes, desde o cadastro até a efetivação do aluguel, com funcionalidades essenciais como pesquisa de veículos, gestão de reservas e acompanhamento de aluguéis.

## Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Java 21**
- **Spring Boot 3.3.3**
- **Spring Data JPA & MySQL**
- **Spring Test com JUnit5 e Mockito**
- **Documentação com Swagger e Javadoc**
- **Intellij IDEA Ultimate 2024.2.1**
- **GitHub Copilot 1.5.20.6554**

Essa combinação de tecnologias proporciona uma base sólida para o desenvolvimento de uma aplicação robusta, escalável e
bem documentada.

## Funcionalidades

O sistema de aluguel de carros oferece as seguintes funcionalidades, organizadas por controlador:

## ✨ Funcionalidades

O sistema de aluguel de carros oferece uma ampla gama de funcionalidades, cuidadosamente organizadas por controlador
para facilitar a navegação e o entendimento:

### 1. 🧑‍💼 Gerenciamento de Clientes (`ClienteController`)

| Funcionalidade               | Descrição                                                                     | Método HTTP | Endpoint                                 |
|------------------------------|-------------------------------------------------------------------------------|-------------|------------------------------------------|
| **Cadastro de Clientes**     | Permite o cadastro de novos clientes (motoristas) com informações detalhadas. | `POST`      | `/api/v1/cliente`                        |
| **Detalhamento de Clientes** | Exibe os detalhes básicos de um cliente específico.                           | `GET`       | `/api/v1/cliente/{id}`                   |
| **Listagem de Clientes**     | Lista todos os clientes cadastrados, com paginação e ordenação por nome.      | `GET`       | `/api/v1/cliente`                        |
| **Detalhamento Completo**    | Mostra todos os detalhes de um cliente, incluindo histórico de aluguéis.      | `GET`       | `/api/v1/cliente/detalhar-completo/{id}` |
| **Atualização de Clientes**  | Permite atualizar informações de um cliente existente.                        | `PATCH`     | `/api/v1/cliente`                        |
| **Desativação de Clientes**  | Desativa um cliente, impedindo-o de realizar novos aluguéis.                  | `PATCH`     | `/api/v1/cliente/{id}`                   |
| **Exclusão de Clientes**     | Remove um cliente do sistema.                                                 | `DELETE`    | `/api/v1/cliente/{id}`                   |

### 2. ⚙️ Gerenciamento de Veículos (`CarroController`)

| Funcionalidade                        | Descrição                                                                    | Método HTTP | Endpoint                                                           |
|---------------------------------------|------------------------------------------------------------------------------|-------------|--------------------------------------------------------------------|
| **Cadastro de Veículos**              | Permite o cadastro de novos veículos com informações completas.              | `POST`      | `/api/v1/carro`                                                    |
| **Detalhamento de Veículos**          | Exibe os detalhes básicos de um veículo específico.                          | `GET`       | `/api/v1/carro/{id}`                                               |
| **Listagem de Veículos**              | Lista todos os veículos cadastrados, com paginação e ordenação por modelo.   | `GET`       | `/api/v1/carro`                                                    |
| **Detalhamento Completo de Veículos** | Mostra todos os detalhes de um veículo, incluindo histórico de aluguéis.     | `GET`       | `/api/v1/carro/detalhar-completo/{id}`                             |
| **Pesquisa de Veículos**              | Permite pesquisar veículos por diversos critérios (nome, placa, etc.).       | `GET`       | `/api/v1/carro/pesquisar`                                          |
| **Listagem de Veículos Disponíveis**  | Lista todos os veículos disponíveis para aluguel, com paginação.             | `GET`       | `/api/v1/carro/disponiveis`                                        |
| **Listagem de Veículos Alugados**     | Lista todos os veículos que estão atualmente alugados, com paginação.        | `GET`       | `/api/v1/carro/alugados`                                           |
| **Bloquear/Disponibilizar Aluguel**   | Permite bloquear ou disponibilizar um veículo para aluguel.                  | `PATCH`     | `/api/v1/carro/{id}/bloquear`, `/api/v1/carro/{id}/disponibilizar` |
| **Atualização de Veículos**           | Permite atualizar informações de um veículo existente.                       | `PATCH`     | `/api/v1/carro`                                                    |
| **Desativação de Veículos**           | Desativa um veículo, tornando-o indisponível para aluguel (exclusão lógica). | `DELETE`    | `/api/v1/carro/{id}`                                               |

### 3. 📅 Gerenciamento de Aluguéis (`AluguelController`)

| Funcionalidade                    | Descrição                                                                               | Método HTTP | Endpoint                                 |
|-----------------------------------|-----------------------------------------------------------------------------------------|-------------|------------------------------------------|
| **Criação de Aluguéis (Reserva)** | Permite criar um novo aluguel, associando um cliente a um veículo e definindo as datas. | `POST`      | `/api/v1/aluguel/alugar`                 |
| **Visualização de Aluguéis**      | Exibe os detalhes básicos de um aluguel específico.                                     | `GET`       | `/api/v1/aluguel/{id}`                   |
| **Listagem de Aluguéis**          | Lista todos os aluguéis cadastrados, com paginação.                                     | `GET`       | `/api/v1/aluguel`                        |
| **Detalhamento Completo**         | Mostra todos os detalhes de um aluguel, incluindo informações do cliente e do veículo.  | `GET`       | `/api/v1/aluguel/detalhar-completo/{id}` |
| **Confirmação de Aluguéis**       | Permite confirmar um aluguel, definindo o tipo de pagamento.                            | `PATCH`     | `/api/v1/aluguel/confirmar/{id}`         |
| **Encerramento de Aluguéis**      | Permite finalizar um aluguel, registrando a data de devolução do veículo.               | `PATCH`     | `/api/v1/aluguel/finalizar/{id}`         |
| **Cancelamento de Aluguéis**      | Permite cancelar um aluguel.                                                            | `PATCH`     | `/api/v1/aluguel/cancelar/{id}`          |

## Estrutura do Projeto

```
car-rental-challenge
│
├── src
│ ├── main
│ │ ├── java
│ │ │ ├── br
│ │ │ │ ├── com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail
│ │ │ │ │ ├── config # Configurações da API, como segurança e banco de dados
│ │ │ │ │ ├── controller # Controladores REST que recebem requisições e orquestram a lógica de negócio
│ │ │ │ │ ├── dto # DTOs (Data Transfer Objects) para transferência de dados entre camadas
│ │ │ │ │ ├── entity # Entidades JPA que representam as tabelas do banco de dados
│ │ │ │ │ ├── exception # Exceções personalizadas para tratamento de erros específicos da aplicação
| | | | | ├── generator # Classes que geram códigos de boleto e chave Pix para pagamentos
│ │ │ │ │ ├── handler # Manipuladores de exceções globais para tratamento centralizado de erros
│ │ │ │ │ ├── repository # Interfaces de repositórios JPA para acesso aos dados
│ │ │ │ │ ├── service # Classes de serviço que implementam a lógica de negócio da aplicação
│ │ │ │ │ ├── spec # Classe utilitária para construir especificações JPA para consultas complexas
│ │ │ │ │ ├── validation # Anotações personalizadas para validação de dados de entrada
│ │ │ └── resources
│ │ │ ├── application.properties # Configurações da aplicação, como porta do servidor e propriedades do banco de dados
│ │ └── test
│ │ ├── java
│ │ │ ├── br
│ │ │ │ ├── com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail
│ │ │ │ ├── controller # Testes unitários para validar o comportamento dos controladores
│ │ │ │ ├── service # Testes unitários para validar a lógica de negócio dos serviços
│ └── pom.xml # Arquivo de configuração do Maven para gerenciar dependências e construir o projeto
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

## 🤝 Contribuindo

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
