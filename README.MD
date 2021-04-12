# API MercadoLivre
Olá! Este é o repositório onde está implementado a minha versão (básica) de uma API que fornece o MercadoLivre. O intuito desse projeto foi puramente de treino de desenvolvimento de software e com ele consegui treinar e me aprimorar mais na stack Java + Spring.

## Especificação do projeto
A especificação do projeto pode ser encontrada [aqui](https://trello.com/b/ntE2tuml/nosso-mercado-livre). Cada card virou uma branch.

### Como trabalhei nesse projeto
Cada card virou uma branch no meu repositório. Ao implementar a funcionalidade e os testes eu subia um Pull Request, revisava o Pull Request para anotar os principais gaps que senti e passava para o próximo card.


## Documentação do projeto
A documentação está disponível através do Swagger UI em localhost:8080/swagger-ui.html

## Algumas funcionalidades feitas

### Cadastro de usuário
Um usuário pode se registrar dado um login e senha.
````http request
POST http://localhost:8080/users
Content-Type: application/json

{
  "login": "henrique@gmail.com",
  "password": "123ABC"
}

````
Não há corpo de resposta 😬

### Autenticação
````http request
POST http://localhost:8080/auth
Content-Type: application/json

{
  "login": "henrique@gmail.com",
  "password": "123ABC"
}
````

### Cadastro de categoria
````http request
POST http://localhost:8080/categories
Content-Type: application/json

{
  "name": "Category Name",
  "parentCategoryId": ""
}

````
### Cadastro de produto
### Adicionar imagem ao produto
### Adicionar opinião sobre o produto
### Fazer uma pergunta ao vendedor
### Visualização de um produto específico
### Finalização da compra

