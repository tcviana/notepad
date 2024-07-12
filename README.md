# Notepad API

Uma API simples de Notepad construída com Kotlin e Spring Boot.

## Sumário

- [Visão Geral](#visão-geral)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Execução](#execução)
- [Endpoints](#endpoints)
- [Testes](#testes)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Visão Geral

Esta API permite criar, listar e gerenciar notas.

## Requisitos

- JDK 11 ou superior
- Gradle
- Docker (opcional, para execução do banco de dados H2 em um contêiner)

## Instalação

Clone o repositório:

```sh
git clone https://github.com/tcviana/notepad.git
cd notepad-api
```

## Configuração

### Banco de Dados

Por padrão, este projeto utiliza o banco de dados H2 em memória para facilitar o desenvolvimento. Não é necessária
nenhuma configuração adicional.

### Configuração do Spring Boot

As configurações do Spring Boot estão no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

## Execução

Para executar a aplicação, use o seguinte comando:

```sh
./gradlew bootRun
```

A API estará disponível em `http://localhost:8080`.

## Endpoints

### Listar Todas as Notas

- **URL:** `/notes`
- **Método:** `GET`
- **Resposta de Sucesso:**
    - Código: `200 OK`
    - Corpo:
      ```json
      [
        {
          "id": 1,
          "title": "Título da Nota",
          "description": "Descrição da Nota"
        }
      ]
      ```

### Criar uma Nova Nota

- **URL:** `/notes`
- **Método:** `POST`
- **Corpo da Requisição:**
  ```json
  {
    "title": "Nova Nota",
    "description": "Descrição da Nova Nota"
  }
  ```
- **Resposta de Sucesso:**
    - Código: `200 OK`
    - Corpo:
      ```json
      {
        "id": 1,
        "title": "Nova Nota",
        "description": "Descrição da Nova Nota"
      }
      ```

### Excluir uma Nota

- **URL:** `/notes/{id}`
- **Método:** `DELETE`
- **Resposta de Sucesso:**
    - Código: `204 No Content`

## Testes

Para executar os testes, use o seguinte comando:

```sh
./gradlew test
```

## Contribuição

1. Faça um fork do repositório
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Envie para o branch (`git push origin feature/nova-feature`)
5. Crie um novo Pull Request

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.