## Definições técnicas

Utilizar java ou kotlin como linguagem

Projeto deve conter:

1. banco de dados postgres

	1.1. Tabela **movie**
	
		- id (serial)
		- title (varchar)
		- release date (timestamp)
		- synopsis (text)
		- user_rating (int)


2. **API Rest** com URLS:
	- `GET /movies` Retorna lista de objetos "movie"
	- `GET /movies/{id}` Retorna um objeto "movie" de {id}
	- `POST /movies` Enviando como body um objeto "movie" (json). Deve salvar o movie no banco
	- `PUT /movies/{id}` Enviando como body um objeto "movie" com informação de rating. Deve editar o rating do movie de {id} no banco de dados
	- `GET /movies/letter_metrics_top10` Retorna uma lista das dez letras que mais se repetem nos títulos de todos os "movies" sem contar vogais e a quantidade absoluta de repetições de cada uma delas. 
	Exemplo: 
	
```
    [
      {
        "letra": "b",
        "quantidade": 3
      },
      {
        "letra": "t",
        "quantidade": 6
      },
      {
        "letra": "p",
        "quantidade": 2
      }
    ]
```


3. Utilizar **maven** para criação de projeto spring boot
4. Utilizar [spring boot com spring mvc](https://spring.io/guides/gs/serving-web-content/) para facilitar a criação do projeto
5. Implementar testes que julgar necessário
6. [Opcional] Utilizar [**migration**](https://flywaydb.org/getstarted/why) de Banco de dados. Utilizamos a ferramenta [flyWay](https://flywaydb.org/) para controle de versão de banco

## Definições de escopo:

- Movie title no máximo 30 caracteres e não pode aceitar caracteres especiais
- User rating deve aceitar apenas valores de 0 a 10 como válidos


