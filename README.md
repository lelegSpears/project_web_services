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
http://localhost:8080/users/{Id}

Get(All):
http://localhost:8080/users

Patching:
http://localhost:8080/users/{Id}
JSON
{
  "name": "Leandro Final",
  "email": "final@email.com",
  "phone": "11988887777"
}















> ⚠️ O Dockerfile foi criado com foco em deploy na plataforma Render.
