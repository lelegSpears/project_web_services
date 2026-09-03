# E-commerce API (Spring Boot)

API REST de um sistema de e-commerce desenvolvida com Spring Boot.

O projeto implementa operações de usuários, produtos, categorias e pedidos, utilizando JPA/Hibernate para os relacionamentos entre as entidades, além de autenticação e autorização com Spring Security e JWT.

Novas melhorias serão adicionadas ao longo do desenvolvimento.

## Tecnologias utilizadas

Java 21 • Spring Boot • Spring Web • Spring Data JPA • Hibernate  
• PostgreSQL • H2 • Spring Security • JWT • Flyway • MapStruct  
• Lombok • JUnit • Mockito • Swagger/OpenAPI • Postman • Maven • Git

## Funcionalidades

- CRUD de usuários
- CRUD de produtos
- CRUD de categorias
- Criação e gerenciamento de pedidos
- Pedido com múltiplos itens
- Cálculo automático do total do pedido
- Autenticação utilizando JWT
- Autorização baseada em Roles (`USER` e `ADMIN`)
- Promoção de usuários para `ADMIN`
- Rebaixamento de usuários para `USER`
- Tratamento global de exceções
- Documentação interativa com Swagger/OpenAPI

## Conceitos aplicados

- Arquitetura em camadas (Controller → Service → Repository)
- DTOs para entrada e saída de dados
- JPA/Hibernate
- Relacionamentos entre entidades
- Uso de `@EmbeddedId` em entidade associativa
- Controle de transações com `@Transactional`
- Spring Security
- Autenticação e autorização com JWT
- Controle de acesso com `@PreAuthorize`
- Validação de dados com Bean Validation
- Tratamento global de exceções
- Padronização de respostas de erro
- Uso de variáveis de ambiente para configuração
- Migração de banco de dados com Flyway

> ⚠️ O Dockerfile foi criado com foco em deploy na plataforma Render.



## Swagger / OpenAPI

A API possui documentação interativa através do Swagger UI.

Após iniciar a aplicação, acesse:

`http://localhost:8080/swagger-ui.html`

O Swagger permite visualizar os endpoints, modelos, parâmetros e exemplos de requisições, além de executar as requisições diretamente pela interface.

Os endpoints protegidos utilizam autenticação Bearer Token (JWT).

Para testar endpoints autenticados:

1. Faça login através de `/auth/login`.
2. Copie o JWT retornado.
3. Clique em `Authorize`.
4. Informe o token.
5. Utilize `Try it out` para testar os endpoints protegidos.


## Banco H2

O banco H2 é utilizado no perfil de testes.

Ao iniciar a aplicação com o perfil `test`, o `TestConfig` cria automaticamente dados para testes, incluindo um usuário com Role `USER` e um usuário com Role `ADMIN`.

Usuário ADMIN para testes:

username: Admin  
password: 123456

As credenciais acima são destinadas exclusivamente ao ambiente de testes.


# Autenticação

## POST `/auth/login`

Realiza o login do usuário e retorna um JWT.

### Request

json
{
  "username": "Leandro",
  "password": "123456"
}

## Endpoints de Users

POST:
http://localhost:8080/users

JSON
{
  "username": "Leandro Lazari",
  "email": "leandro@email.com",
  "password": "123456",
  "phone": "11999999999"
}

GET(By Id):
http://localhost:8080/users/{id}

GET(All):
http://localhost:8080/users

PATCH:
http://localhost:8080/users/{id}

JSON
{
  "username": "leleg",
  "email": "leleg@email.com",
  "password": "123456",
  "phone": "11999999999"
}

DELETE:
http://localhost:8080/users/{id}

PATCH(Promote to ADMIN - Necessária a Role ADMIN):
http://localhost:8080/users/promote/{id}

PATCH(Demote to USER - Necessária a Role ADMIN):
http://localhost:8080/users/demote/{id}

## Endpoints de Login

POST:
http://localhost:8080/auth/login

JSON
{
  "username": "Leandro",
  "password": "123456"
}

Devolverá um Token JWT com Role USER.

Para testes, o perfil `test` cria automaticamente um usuário ADMIN:

username: Admin
password: 123456

## Endpoints de Orders

POST:
http://localhost:8080/orders

JSON
{
  "clientId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 3
    },
    {
      "productId": 2,
      "quantity": 2
    }
  ]
}

GET(By Id):
http://localhost:8080/orders/{id}

GET(All):
http://localhost:8080/orders

Patch:
http://localhost:8080/orders/{id}

JSON
{
  "items": [
    {
      "productId": 1,
      "quantity": 6
    },
    {
      "productId": 2,
      "quantity": 2
    }
  ]
}

Delete:
http://localhost:8080/orders/{id}

## Endpoints de Products

POST(Necessária a Role ADMIN):
http://localhost:8080/products

JSON
{
  "name": "Notebook",
  "description": "Notebook para trabalho",
  "price": 4500.00,
  "categoryIds": [
    1,
    2
  ],
  "imgURL": "https://exemplo.com/notebook.jpg"
}

GET(By Id):
http://localhost:8080/products/{id}

GET(All):
http://localhost:8080/products

PATCH(Necessária a Role ADMIN):
http://localhost:8080/products/{id}

JSON
{
  "name": "Notebook Gamer",
  "description": "Notebook gamer atualizado",
  "price": 5200.00,
  "categoryIds": [
    1,
    3
  ],
  "imgURL": "https://exemplo.com/notebook-gamer.jpg"
}

DELETE(Necessária a Role ADMIN):
http://localhost:8080/products/{id}

## Endpoints de Categories

POST(Necessária a Role ADMIN):
http://localhost:8080/categories

JSON
{
  "name": "Eletrônicos"
}

GET(By Id):
http://localhost:8080/categories/{id}

GET(All):
http://localhost:8080/categories

PATCH(Necessária a Role ADMIN):
http://localhost:8080/categories/{id}

JSON
{
  "name": "Eletrônicos e Tecnologia"
}

DELETE(Necessária a Role ADMIN):
http://localhost:8080/categories/{id}

