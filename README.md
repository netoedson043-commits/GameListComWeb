# 🎮 GameList | Game Library Web Application

Aplicação web para buscar jogos e organizar favoritos, desenvolvida com Java, Spring Boot e PostgreSQL. Evolução de um projeto originalmente utilizado pelo terminal.

Web application for searching games and organizing favorites, built with Java, Spring Boot, and PostgreSQL. An evolution of a project originally used through the terminal.

---

# 🇧🇷 Português

## 📋 Sobre o Projeto

O GameList permite pesquisar jogos utilizando a API da RAWG e manter uma lista pessoal de favoritos no PostgreSQL.

A interface web substitui os menus do terminal por abas de busca, favoritos e desenvolvedoras. O frontend se comunica com o backend por requisições HTTP, e os DTOs definem os dados enviados nas respostas.

## ✨ Funcionalidades

- ✅ Buscar jogos pelo nome na RAWG.
- ✅ Exibir nome, imagem e data de lançamento quando disponíveis.
- ✅ Adicionar jogos aos favoritos e evitar duplicação pelo nome.
- ✅ Listar e remover favoritos.
- ✅ Escolher e atualizar o status: Vou jogar, Jogando ou Concluído.
- ✅ Exibir o status Sem informações nos favoritos.
- ✅ Cadastrar ou reutilizar a desenvolvedora ao salvar um favorito.
- ✅ Listar desenvolvedoras e seus jogos favoritados.
- ✅ Remover desenvolvedoras sem favoritos vinculados.
- ✅ Persistir os dados no PostgreSQL.

A listagem por desenvolvedora mostra os favoritos cadastrados, não todo o catálogo da RAWG. A implementação atual associa a primeira desenvolvedora retornada para o jogo.

## 🗂️ Estrutura do Projeto

```text
GameListComWeb-main/
├── .mvn/wrapper/                   # Maven Wrapper
├── src/
│   ├── main/
│   │   ├── java/Jogo/Gamilist20/
│   │   │   ├── Api/                # Cliente HTTP da RAWG
│   │   │   ├── Controller/         # Rotas HTTP e tratamento de erros
│   │   │   ├── DTO/                # Dados enviados ao frontend
│   │   │   ├── Modelo/             # Entidades, enum e modelos da RAWG
│   │   │   ├── Principal/BuscarApi/ # Busca e detalhes de jogos
│   │   │   ├── Servicos/           # Regras de favoritos e desenvolvedoras
│   │   │   ├── repository/         # Acesso ao banco com Spring Data JPA
│   │   │   └── Application.java    # Inicialização do Spring Boot
│   │   └── resources/
│   │       ├── application.properties # Configuração local, ignorada pelo Git
│   │       └── static/
│   │           ├── index.html
│   │           ├── css/principal.css
│   │           └── js/principal.js
│   └── test/java/Jogo/Gamilist20/
│       └── ApplicationTests.java   # Teste de carregamento do contexto
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## 🚀 Como Executar

### Pré-requisitos

- JDK 25, conforme o `pom.xml`, ou uma versão compatível mais recente.
- PostgreSQL instalado e em execução.
- Chave de acesso à API RAWG.
- Git, navegador atualizado e acesso à internet para dependências e consultas à RAWG.
- Permissão de acesso ao repositório, caso ele esteja privado.

### 1. Clonar o repositório

```bash
git clone https://github.com/netoedson043-commits/GameListComWeb-main.git
cd GameListComWeb-main
```

Se a versão web estiver na branch `main` e ela não for a branch padrão:

```bash
git switch main
```

### 2. Criar o banco

No PostgreSQL, execute:

```sql
CREATE DATABASE "GameList";
```

### 3. Configurar a aplicação

Crie `src/main/resources/application.properties` com suas configurações locais:

```properties
spring.application.name=GameList
spring.datasource.url=jdbc:postgresql://localhost:5432/GameList
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
rawg.api.key=SUA_CHAVE_RAWG
```

Substitua os valores de exemplo. O Hibernate cria ou atualiza as tabelas no banco existente. O `.gitignore` exclui `application.properties`; mantenha senhas e chaves fora dos commits.

### 4. Iniciar o backend

Linux/macOS:

```bash
bash mvnw spring-boot:run
```

Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

Também é possível executar `Application.java` pelo IntelliJ IDEA. O Maven Wrapper dispensa a instalação separada do Maven.

### 5. Abrir o frontend

Acesse [http://localhost:8080/](http://localhost:8080/).

O Spring Boot disponibiliza o frontend e a API no mesmo servidor. Abra a aplicação por esse endereço para que as chamadas `/api/...` cheguem ao backend.

## 🛠️ Tecnologias

- Java 25 e Spring Boot 4.1.0.
- Spring MVC e API HTTP com respostas JSON.
- Spring Data JPA e Hibernate.
- PostgreSQL.
- Jackson para leitura dos dados da RAWG.
- HTML5, CSS3 e JavaScript com Fetch API.
- Maven Wrapper.
- JUnit e Spring Boot Test.

## 🔌 Endpoints da API

| Método | Rota | Função |
|---|---|---|
| GET | `/api/jogos/buscar?nome=...` | Buscar jogos na RAWG |
| POST | `/api/jogos/favoritos/{id}?status=VOU_JOGAR` | Adicionar um jogo da RAWG aos favoritos |
| GET | `/api/favoritos` | Listar favoritos |
| PUT | `/api/favoritos/{id}/status?status=CONCLUIDO` | Atualizar o status |
| DELETE | `/api/favoritos/{id}` | Remover um favorito |
| GET | `/api/desenvolvedoras` | Listar desenvolvedoras |
| GET | `/api/desenvolvedoras/{id}/jogos` | Listar favoritos da desenvolvedora |
| DELETE | `/api/desenvolvedoras/{id}` | Remover uma desenvolvedora sem favoritos |

Na inclusão, o ID é o identificador do jogo na RAWG. Nas rotas de favoritos, o ID é o identificador salvo no banco local. Os valores de status são `VOU_JOGAR`, `JOGANDO`, `CONCLUIDO` e `SEM_INFORMACOES`.

## 📱 Interface e Responsividade

A interface utiliza abas, Flexbox e largura máxima de conteúdo. Ajustes específicos para telas pequenas e validação completa em celulares e tablets ainda são pontos de evolução.

## 🧪 Testes

```bash
bash mvnw test
```

No Windows, utilize `.\mvnw.cmd test`. O teste atual verifica o carregamento do contexto Spring e exige as configurações locais, incluindo uma conexão válida com PostgreSQL. Ele não cobre todos os fluxos da aplicação.

## 🔗 Projeto

- [Repositório no GitHub](https://github.com/netoedson043-commits/GameListComWeb-main)
- [RAWG](https://rawg.io/apidocs)
- Execução local: [http://localhost:8080/](http://localhost:8080/).

Não há um endereço público de implantação confirmado. A aplicação requer um backend Java em execução e um banco PostgreSQL.

---

# 🇺🇸 English

## 📋 About the Project

GameList lets users search for games through the RAWG API and maintain a personal favorites collection in PostgreSQL.

The web interface replaces terminal menus with search, favorites, and developer tabs. The frontend communicates with the backend through HTTP requests, while DTOs define the data returned in responses.

## ✨ Features

- ✅ Search RAWG games by name.
- ✅ Display names, images, and release dates when available.
- ✅ Add favorites and prevent duplicates by name.
- ✅ List and remove favorites.
- ✅ Choose and update game status: Plan to play, Playing, or Completed.
- ✅ Display the No information status for favorites.
- ✅ Create or reuse a developer when saving a favorite.
- ✅ List developers and their favorited games.
- ✅ Remove developers without linked favorites.
- ✅ Persist data in PostgreSQL.

Developer game lists include locally saved favorites, rather than the entire RAWG catalog. The current implementation associates the first developer returned for each game.

## 🗂️ Project Structure

```text
GameListComWeb-main/
├── .mvn/wrapper/                   # Maven Wrapper
├── src/
│   ├── main/
│   │   ├── java/Jogo/Gamilist20/
│   │   │   ├── Api/                # RAWG HTTP client
│   │   │   ├── Controller/         # HTTP routes and error handling
│   │   │   ├── DTO/                # Frontend response data
│   │   │   ├── Modelo/             # Entities, enum, and RAWG models
│   │   │   ├── Principal/BuscarApi/ # Game search and details
│   │   │   ├── Servicos/           # Favorites and developer rules
│   │   │   ├── repository/         # Spring Data JPA database access
│   │   │   └── Application.java    # Spring Boot entry point
│   │   └── resources/
│   │       ├── application.properties # Local configuration, ignored by Git
│   │       └── static/
│   │           ├── index.html
│   │           ├── css/principal.css
│   │           └── js/principal.js
│   └── test/java/Jogo/Gamilist20/
│       └── ApplicationTests.java   # Context loading test
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## 🚀 How to Run

### Requirements

- JDK 25, as configured in `pom.xml`, or a compatible newer version.
- An installed and running PostgreSQL server.
- A RAWG API key.
- Git, a modern browser, and internet access for dependencies and RAWG requests.
- Repository access if the repository is private.

### 1. Clone the repository

```bash
git clone https://github.com/netoedson043-commits/GameListComWeb-main.git
cd GameListComWeb-main
```

If the web version is on `main` and it is not the default branch:

```bash
git switch main
```

### 2. Create the database

Run in PostgreSQL:

```sql
CREATE DATABASE "GameList";
```

### 3. Configure the application

Create `src/main/resources/application.properties`:

```properties
spring.application.name=GameList
spring.datasource.url=jdbc:postgresql://localhost:5432/GameList
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
rawg.api.key=YOUR_RAWG_KEY
```

Replace the placeholders with your local settings. Hibernate creates or updates tables in the existing database. The `.gitignore` excludes `application.properties`; keep passwords and API keys out of commits.

### 4. Start the backend

Linux/macOS:

```bash
bash mvnw spring-boot:run
```

Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

Alternatively, run `Application.java` in IntelliJ IDEA. The Maven Wrapper avoids a separate Maven installation.

### 5. Open the frontend

Visit [http://localhost:8080/](http://localhost:8080/).

Spring Boot serves the frontend and API from the same server. Open this address so `/api/...` requests reach the backend.

## 🛠️ Tech Stack

- Java 25 and Spring Boot 4.1.0.
- Spring MVC and an HTTP API with JSON responses.
- Spring Data JPA and Hibernate.
- PostgreSQL.
- Jackson for reading RAWG data.
- HTML5, CSS3, and JavaScript with Fetch API.
- Maven Wrapper.
- JUnit and Spring Boot Test.

## 🔌 API Endpoints

| Method | Route | Purpose |
|---|---|---|
| GET | `/api/jogos/buscar?nome=...` | Search RAWG games |
| POST | `/api/jogos/favoritos/{id}?status=VOU_JOGAR` | Add a RAWG game to favorites |
| GET | `/api/favoritos` | List favorites |
| PUT | `/api/favoritos/{id}/status?status=CONCLUIDO` | Update game status |
| DELETE | `/api/favoritos/{id}` | Remove a favorite |
| GET | `/api/desenvolvedoras` | List developers |
| GET | `/api/desenvolvedoras/{id}/jogos` | List a developer's favorites |
| DELETE | `/api/desenvolvedoras/{id}` | Remove a developer without favorites |

Adding a favorite uses the RAWG game ID. Favorites routes use the local database ID. Status values are `VOU_JOGAR` (Plan to play), `JOGANDO` (Playing), `CONCLUIDO` (Completed), and `SEM_INFORMACOES` (No information).

## 📱 Interface and Responsiveness

The interface uses tabs, Flexbox, and a maximum content width. Dedicated small-screen adjustments and complete phone/tablet validation remain areas for improvement.

## 🧪 Tests

```bash
bash mvnw test
```

On Windows, use `.\mvnw.cmd test`. The current test checks Spring context loading and requires local settings, including a valid PostgreSQL connection. It does not cover every application flow.

## 🔗 Project

- [GitHub repository](https://github.com/netoedson043-commits/GameListComWeb-main)
- [RAWG](https://rawg.io/apidocs)
- Local application: [http://localhost:8080/](http://localhost:8080/).

No public deployment URL has been confirmed. The application requires a running Java backend and a PostgreSQL database.

---

## 👤 Autor | Author

Edson Neto

GitHub: [netoedson043-commits](https://github.com/netoedson043-commits)

