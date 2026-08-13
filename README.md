# E-commerce API (Spring Boot)

API REST de um sistema de e-commerce desenvolvida com Spring Boot.  
O projeto cobre operações básicas de usuários, produtos, categorias e pedidos, incluindo relacionamentos mais complexos com JPA.
Novas melhorias serão adicionadas ao longo do tempo.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Docker


## Funcionalidades

- CRUD de usuários
- CRUD de produtos e categorias
- Criação e gerenciamento de pedidos
- Pedido com múltiplos itens
- Cálculo automático do total do pedido
- Associação entre entidades:
  - Many-to-Many (Produto ↔ Categoria)
  - One-to-Many (Pedido ↔ Itens)
  - One-to-One (Pedido ↔ Pagamento)
- Tratamento global de exceções


## Conceitos aplicados

- Arquitetura em camadas (Controller → Service → Repository)
- JPA com relacionamentos complexos
- Uso de `@EmbeddedId` em entidade associativa
- Controle de transações com `@Transactional`
- Padronização de respostas de erro
- Uso de variáveis de ambiente para configuração


> ⚠️ O Dockerfile foi criado com foco em deploy na plataforma Render.


## Endpoints de Users
POST:
http://localhost:8080/users
JSON
{
  "name": "Leandro Lazari",
  "email": "leandro@email.com",
  "password": "123456",
  "phone": "11999999999"
}

GET(By Id):
http://localhost:8080/users/{id}

Get(All):
http://localhost:8080/users

Patch:
http://localhost:8080/users/{id}
JSON
{
  "name": "Leandro Final",
  "email": "final@email.com",
  "phone": "11988887777"
}

Delete:
http://localhost:8080/users/{id}

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

Get(All):
http://localhost:8080/orders

Patch:
http://localhost:8080/orders/{id}
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

POST:
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

Get(All):
http://localhost:8080/products

Patch:
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

Delete:
http://localhost:8080/products/{id}

## Endpoints de categories

POST:
http://localhost:8080/categories
JSON
{
  "name": "Eletrônicos"
}

GET(By Id):
http://localhost:8080/categories/{id}

Get(All):
http://localhost:8080/categories

Patch:
http://localhost:8080/categories/{id}
JSON
{
  "name": "Eletrônicos e Tecnologia"
}

Delete:
http://localhost:8080/categories/{id}

## Endpoints possuem paginação ?page=0&size=10&sort=name
