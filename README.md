
# Car Rental Challenge

![License](https://img.shields.io/github/license/squad-13-solutis-dev-trail-2024/car-rental-challenge-solutis-school-dev-trail)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green)
![Build](https://img.shields.io/badge/Build-Passing-brightgreen)

## Overview

O **Car Rental Challenge** é uma aplicação backend desenvolvida como parte do desafio da Solutis School Dev Trail 2024. Este projeto visa simular um sistema de locadora de veículos, onde é possível gerenciar clientes, veículos e aluguéis de maneira eficiente e segura.

## Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Java 21**: A linguagem de programação principal utilizada para construir a aplicação, aproveitando os recursos e aprimoramentos da versão mais recente do Java.
- **Spring Boot 3.3.3**:  Framework robusto que simplifica o desenvolvimento de aplicações Spring, permitindo a criação rápida e eficiente da API RESTful, além de oferecer recursos para escalabilidade e gerenciamento de dependências.
- **Spring Data JPA**:  Módulo do Spring que facilita a interação com o banco de dados, abstraindo a complexidade do acesso a dados e permitindo o uso de repositórios para operações CRUD.
- **Spring Test**:  Framework para testes unitários e de integração, garantindo a qualidade e o funcionamento correto da aplicação.
- **MySQL**: Sistema de gerenciamento de banco de dados relacional (RDBMS) amplamente utilizado, responsável por armazenar as informações da aplicação de forma estruturada.
- **Lombok**: Biblioteca que reduz a quantidade de código boilerplate, como getters, setters e construtores, tornando o código mais limpo e legível.
- **Swagger**: Ferramenta para documentar a API RESTful de forma automatizada, gerando uma interface interativa para explorar e testar os endpoints.
- **Intellij IDEA Ultimate 2024.2.1**: Ambiente de Desenvolvimento Integrado (IDE)  da JetBrains, com recursos avançados para desenvolvimento Java, incluindo ferramentas de depuração, análise de código e integração com frameworks.
- **GitHub Copilot 1.5.20.6554**: Ferramenta de programação em par baseada em inteligência artificial que auxilia na escrita de código, fornecendo sugestões e autocompletamento.

Essa combinação de tecnologias proporciona uma base sólida para o desenvolvimento de uma aplicação robusta, escalável e bem documentada.

## Funcionalidades

O sistema de aluguel de carros oferece as seguintes funcionalidades, organizadas por controlador:


**Gerenciamento de Clientes (`ClienteController`)**

- **Cadastro de clientes:** Permite o cadastro de novos clientes (motoristas) com informações como nome, endereço, número da CNH, etc.
- **Detalhamento de clientes:** Permite visualizar os detalhes de um cliente específico, buscando pelo seu ID.
- **Listagem de clientes:** Oferece a funcionalidade de listar todos os clientes cadastrados, com paginação e ordenação por nome.
- **Detalhamento completo de clientes:** Permite visualizar todos os detalhes de um cliente, incluindo seus aluguéis passados e presentes.
- **Atualização de clientes:** Permite atualizar informações de um cliente existente, como endereço ou número de telefone.
- **Desativação de clientes:** Permite desativar um cliente, impedindo que ele realize novos aluguéis.
- **Exclusão de clientes:** Permite a exclusão de um cliente do sistema.


**Gerenciamento de Veículos (`CarroController`)**

- **Cadastro de veículos:** Permite o cadastro de novos veículos com informações como modelo, marca, ano, placa, valor da diária, etc.
- **Detalhamento de veículos:** Permite visualizar os detalhes de um veículo específico, buscando pelo seu ID.
- **Listagem de veículos:** Oferece a funcionalidade de listar todos os veículos cadastrados, com paginação e ordenação por modelo.
- **Detalhamento completo de veículos:** Permite visualizar todos os detalhes de um veículo, incluindo informações sobre seus aluguéis.
- **Atualização de veículos:** Permite atualizar informações de um veículo existente, como valor da diária ou disponibilidade.
- **Desativação de veículos:** Permite desativar um veículo, tornando-o indisponível para aluguel.
- **Exclusão de veículos:** Permite a exclusão de um veículo do sistema.


**Gerenciamento de Aluguéis (`AluguelController` - não fornecido)**

- **Criação de aluguéis:** Permite criar um novo aluguel, associando um cliente a um veículo e definindo as datas de início e fim do aluguel.
- **Visualização de aluguéis:** Permite visualizar os detalhes de um aluguel específico, buscando pelo seu ID.
- **Listagem de aluguéis:** Oferece a funcionalidade de listar todos os aluguéis cadastrados, com paginação e filtros por cliente, veículo ou status do aluguel.
- **Encerramento de aluguéis:** Permite finalizar um aluguel, registrando a data de devolução do veículo e calculando o valor total a ser pago.
- **Cancelamento de aluguéis:** Permite cancelar um aluguel, liberando o veículo para outros clientes e ajustando o status do aluguel.
  
## Estrutura do Projeto

```
car-rental-challenge
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── br
│   │   │   │   ├── com
│   │   │   │   │   ├── config       # Contém os configurações da API
│   │   │   │   │   ├── controller   # Controladores da API
│   │   │   │   │   ├── dto          # Objetos de Transferência de Dados (DTO)
│   │   │   │   │   ├── entity       # Entidades do projeto
│   │   │   │   │   ├── exception    # Tratamento de exceções
│   │   │   │   │   ├── handler      # Handler
│   │   │   │   │   ├── entity       # Classes de entidades
│   │   │   │   │   ├── repository   # Interfaces de repositório
│   │   │   │   │   ├── service      # Classes de serviço
│   │   │   │   │   ├── test         # Tratamento de exceções
│   │   │   │   │   ├── validation   # Tratamento de validacoes
│   │   │   └── resources
│   │   │       ├── application.properties  # Configurações da aplicação
│   │   └── test
│   │       ├── java
│   │       │   ├── com
│   │       │   │   ├── example
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

- **Aldemir Carlos**
    - [GitHub](https://github.com/aldemaas)
    - [LinkedIn](https://www.linkedin.com/in/aldemir-carlos/)
    - [E-mail](mailto:aldemirc22@gmail.com)

-  **Gabriel de Abreu**
  - [GitHub](https://github.com/AzvedoGabriel)
  - [LinkedIn](https://www.linkedin.com/in/azevedo-gabriel-ssa/)
  - [E-mail](mailto:contato.gabrielazevedo0@gmail.com)

- **Kauê Alexandre**
    - [GitHub](https://github.com/bugkaue)
    - [LinkedIn](https://www.linkedin.com/in/bugkaue/)
    - [E-mail](mailto:seu-melhor-email@hotmail.com)

- **Pedro Messias**
    - [GitHub](https://github.com/PedroMessiasxD)
    - [LinkedIn](https://www.linkedin.com/in/pedromessiasxd/)
    - [E-mail](mailto:seu-melhor-email@hotmail.com)

- **Pedro Rocha**
    - [GitHub](https://github.com/Pedro-E-S-R)
    - [LinkedIn](https://www.linkedin.com/in/pedro-e-s-r/)
    - [E-mail](mailto:pedroemanoel323@gmail.com)

- **Suerdo Flaubert**
    - [GitHub](https://github.com/Suerdo)
    - [LinkedIn](https://www.linkedin.com/in/suerdo-flaubert-78b3a4194/)
    - [E-mail](mailto:suerdocampos@gmail.com)

- **Vinícius Andrade**
    - [GitHub](https://github.com/viniciusdsandrade)
    - [LinkedIn](https://www.linkedin.com/in/viniciusdsandrade/)
    - [E-mail](mailto:vinicius_andrade2010@hotmail.com)
---
