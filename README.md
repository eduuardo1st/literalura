# Literalura

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

## Descrição do Projeto

O Literalura é um catálogo interativo de livros desenvolvido em Java com o framework Spring Boot. Este projeto permite aos usuários buscar livros e autores através da API Gutendex, persistindo os dados em um banco de dados PostgreSQL. É uma aplicação de console que oferece uma interface simples para interagir com o catálogo de livros.

## Funcionalidades

O sistema oferece as seguintes funcionalidades:

*   **Buscar Livro na Web:** Permite buscar livros na API Gutendex pelo título.
*   **Listar Livros Registrados:** Exibe todos os livros que foram salvos no banco de dados.
*   **Listar Autores Registrados:** Exibe todos os autores que foram salvos no banco de dados.
*   **Listar Autores Vivos em um Determinado Ano:** Filtra e exibe autores que estavam vivos em um ano específico.
*   **Listar Livros por Idioma:** Filtra e exibe livros por um idioma selecionado.
*   **Listar Top 10 Livros da API:** Exibe os 10 livros mais populares da API Gutendex.
*   **Buscar Autor por Nome:** Permite buscar autores na API Gutendex pelo nome.

## Tecnologias Utilizadas

As principais tecnologias e dependências utilizadas no desenvolvimento do Literalura incluem:

*   **Java 21:** Linguagem de programação.
*   **Spring Boot 3.2.3:** Framework para construção de aplicações Java robustas.
*   **Spring Data JPA:** Para persistência de dados e interação com o banco de dados.
*   **PostgreSQL:** Banco de dados relacional para armazenamento dos dados de livros e autores.
*   **Maven:** Ferramenta de automação de build e gerenciamento de dependências.
*   **Jackson:** Biblioteca para serialização e desserialização de JSON.
*   **Gutendex API:** API pública utilizada para buscar informações sobre livros e autores [1].

## Como Usar

Para configurar e executar o projeto Literalura localmente, siga os passos abaixo:

### Pré-requisitos

Certifique-se de ter o seguinte instalado em sua máquina:

*   Java Development Kit (JDK) 21 ou superior.
*   Maven.
*   PostgreSQL.

### Configuração do Banco de Dados

1.  Crie um banco de dados PostgreSQL com o nome `literalura`.
2.  Atualize as credenciais do banco de dados no arquivo `application.properties` (ou crie um a partir de `application-template.properties`) localizado em `src/main/resources`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
    spring.datasource.username=SEU_USUARIO_AQUI
    spring.datasource.password=SUA_SENHA_AQUI
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.hibernate.ddl-auto=update
    ```

    Substitua `SEU_USUARIO_AQUI` e `SUA_SENHA_AQUI` pelas suas credenciais do PostgreSQL.

### Executando a Aplicação

1.  Clone o repositório:

    ```bash
    git clone https://github.com/eduuardo1st/literalura.git
    cd literalura
    ```

2.  Compile o projeto usando Maven:

    ```bash
    mvn clean install
    ```

3.  Execute a aplicação:

    ```bash
    java -jar target/literalura-0.0.1-SNAPSHOT.jar
    ```

    Ou, se estiver usando um IDE como IntelliJ IDEA ou Eclipse, execute a classe `LiteraluraApplication.java`.

## API Gutendex

A API Gutendex é uma API pública e gratuita que fornece acesso a metadados de livros do Project Gutenberg. Ela não requer autenticação (API Key) e oferece endpoints para buscar livros por título, autor, idioma e outras informações relevantes [1].

*   **Documentação da API:** [https://gutendex.com/](https://gutendex.com/)

## Estrutura do Projeto

O projeto segue uma estrutura padrão de aplicações Spring Boot, organizada em pacotes para melhor modularidade:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── literalura/
│   │           ├── model/          # Classes de modelo (entidades JPA e DTOs)
│   │           ├── principal/      # Lógica principal da aplicação e menu de interação
│   │           ├── repository/     # Interfaces de repositório para acesso a dados (Spring Data JPA)
│   │           ├── service/        # Classes de serviço para lógica de negócios
│   │           └── util/           # Classes utilitárias (ex: conversão de dados JSON)
│   └── resources/
│       └── application.properties  # Configurações da aplicação e do banco de dados
└── test/
    └── java/
        └── com/
            └── literalura/
                └── LiteraluraApplicationTests.java # Testes da aplicação
```

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues para relatar bugs ou sugerir melhorias, e enviar pull requests com novas funcionalidades ou correções.

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.

## Autor

Eduardo Gonçalves

*   [GitHub](https://github.com/eduuardo1st)

## Referências

[1] Gutendex. (n.d.). *Gutendex*. Retrieved from [https://gutendex.com/](https://gutendex.com/)
