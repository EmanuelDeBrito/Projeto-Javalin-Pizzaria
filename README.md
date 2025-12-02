# Pizza Note — Documentação Completa das Rotas (Main.java)
 
# Sumário
 
1. Funcionário
2. Produto
3. Carrinho
4. Pedido
5. Mapeamento no Código
 
---
 
# 1. Funcionário
 
## 1.1. Criar Funcionário (Signup)
 
* **URL:** `/signup`
* **Método:** `POST`
* **Headers:** nenhum
* **Body (JSON):** `cpf`, `name`, `password`
* **Descrição:** Cadastra um novo funcionário no banco.
 
## 1.2. Login
 
* **URL:** `/login`
* **Método:** `POST`
* **Headers:** nenhum
* **Body (JSON):** `cpf`, `password`
* **Descrição:** Valida o funcionário e retorna um token.
 
## 1.3. Buscar Funcionário
 
* **URL:** `/api/funcionario/{cpf}`
* **Método:** `GET`
* **Headers:** Authorization obrigatório
* **Descrição:** Retorna dados do funcionário.
 
## 1.4. Remover Funcionário
 
* **URL:** `/api/funcionario/{cpf}`
* **Método:** `DELETE`
* **Headers:** Authorization obrigatório
* **Descrição:** Deleta o funcionário do sistema.
 
---
 
# 2. Produto
 
## 2.1. Criar Produto
 
* **URL:** `/api/produto`
* **Método:** `POST`
* **Headers:** Authorization obrigatório
* **Body (JSON):** `image`, `name`, `description`, `price`
* **Descrição:** Cadastra um produto no banco.
 
## 2.2. Buscar Produto
 
* **URL:** `/api/produto/{id}`
* **Método:** `GET`
* **Headers:** Authorization obrigatório
* **Descrição:** Retorna dados de um produto pelo ID.
 
---
 
# 3. Carrinho
 
## 3.1. Adicionar Item ao Carrinho
 
* **URL:** `/api/carrinho`
* **Método:** `POST`
* **Headers:** Authorization obrigatório
* **Body (JSON):** `cdEmployee`, `cdProduct`, `value`
* **Descrição:** Adiciona um item ao carrinho.
 
## 3.2. Listar Itens do Carrinho
 
* **URL:** `/api/carrinho/{idFuncionario}`
* **Método:** `GET`
* **Headers:** Authorization obrigatório
* **Descrição:** Lista os itens do carrinho do funcionário.
 
## 3.3. Incrementar Quantidade de um Item
 
* **URL:** `/api/carrinho/incrementar/{idFuncionario}&{idProduto}`
* **Método:** `PATCH`
* **Headers:** Authorization obrigatório
* **Descrição:** Aumenta a quantidade do item.
 
## 3.4. Decrementar Quantidade de um Item
 
* **URL:** `/api/carrinho/decrementar/{idFuncionario}&{idProduto}`
* **Método:** `PATCH`
* **Headers:** Authorization obrigatório
* **Descrição:** Diminui a quantidade do item.
 
## 3.5. Remover Item Específico do Carrinho
 
* **URL:** `/api/carrinho/{idFuncionario}&{idProduto}`
* **Método:** `DELETE`
* **Headers:** Authorization obrigatório
* **Descrição:** Remove apenas o item selecionado.
 
## 3.6. Remover Todo o Carrinho do Funcionário
 
* **URL:** `/api/carrinho/{idFuncionario}`
* **Método:** `DELETE`
* **Headers:** Authorization obrigatório
* **Descrição:** Remove todos os itens do carrinho.
 
---
 
# 4. Pedido
 
## 4.1. Criar Pedido
 
* **URL:** `/api/pedido`
* **Método:** `POST`
* **Headers:** Authorization obrigatório
* **Body (JSON):** `cdEmployee`, `items`, `total`
* **Descrição:** Cria um novo pedido no sistema.
 
## 4.2. Listar Pedidos de um Funcionário
 
* **URL:** `/api/pedido/{idFuncionario}`
* **Método:** `GET`
* **Headers:** Authorization obrigatório
* **Descrição:** Retorna todos os pedidos associados ao funcionário.
 
## 4.3. Marcar Pedido como Entregue
 
* **URL:** `/api/pedido/entregue/{idPedido}`
* **Método:** `PATCH`
* **Headers:** Authorization obrigatório
* **Descrição:** Atualiza o status para entregue.
 
## 4.4. Deletar Pedido
 
* **URL:** `/api/pedido/{idPedido}`
* **Método:** `DELETE`
* **Headers:** Authorization obrigatório
* **Descrição:** Remove o pedido do sistema.
 
---
 
# 5. Mapeamento das Rotas no Código
 
### Estruturas Principais
 
* `SQLiteConnection.createDatabase();` — cria/verifica banco.
* `Javalin app = Javalin.create().start(7070);` — inicia o servidor.
* `app.get("/", ...)` — rota base.
* Middleware `/api/*` — valida token.
 
### Funcionário
 
```
app.post("/signup", ...);
app.post("/login", ...);
app.get("/api/funcionario/{cpf}", ...);
app.delete("/api/funcionario/{cpf}", ...);
```
 
### Produto
 
```
app.post("/api/produto", ...);
app.get("/api/produto/{id}", ...);
```
 
### Carrinho
 
```
app.post("/api/carrinho", ...);
app.get("/api/carrinho/{idFuncionario}", ...);
app.patch("/api/carrinho/incrementar/{idFuncionario}&{idProduto}", ...);
app.patch("/api/carrinho/decrementar/{idFuncionario}&{idProduto}", ...);
app.delete("/api/carrinho/{idFuncionario}&{idProduto}", ...);
app.delete("/api/carrinho/{idFuncionario}", ...);
```
 
### Pedido
 
```
app.post("/api/pedido", ...);
app.get("/api/pedido/{idFuncionario}", ...);
app.patch("/api/pedido/entregue/{idPedido}", ...);
app.delete("/api/pedido/{idPedido}", ...);
