# API PDV (Ponto de Venda) - Java + Spring Boot

API REST de um sistema de ponto de venda (PDV), desenvolvida com Java e Spring Boot.

---

## 🚀 Funcionalidades

- ✅ Cadastro de produtos  
- ✅ Controle de estoque  
- ✅ Criação de vendas  
- ✅ Adição de itens na venda  
- ✅ Finalização de venda  
- ✅ Pagamento com cálculo de troco  

---

## 🛠️ Tecnologias

- Java 17  
- Spring Boot  
- Spring Data JPA  
- Hibernate  
- Maven  
- Banco de dados: H2 / MySQL  

---
## 📌 Endpoints

### Produtos
POST /produtos → Cadastrar produto  
GET /produtos → Listar produtos  

### Vendas
POST /vendas → Criar venda  
POST /vendas/{id}/itens → Adicionar item  
PUT /vendas/{id}/finalizar → Finalizar venda  
POST /vendas/{id}/pagar → Realizar pagamento  
## 🧠 Regras de negócio

- Não é possível adicionar itens em uma venda finalizada
- Estoque é reduzido ao adicionar item
- Venda só pode ser paga após ser finalizada
- Troco é calculado automaticamente
## 📦 Como executar o projeto

```bash
# Clonar o repositório
git clone https://github.com/devpedrolc/pdv-api.git

# Entrar na pasta
cd pdv-api

# Rodar o projeto
./mvnw spring-boot:run
