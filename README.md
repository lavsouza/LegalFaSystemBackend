# LegalFA System - Backend

Backend REST para gerenciamento de empresas, usuarios, funcionarios e contratos juridicos. O projeto usa Spring Boot com autenticacao JWT, controle de perfis e persistencia em PostgreSQL via JPA.

## Funcionalidades

- Login e registro de usuarios.
- Autenticacao stateless com token JWT.
- Cadastro, consulta, atualizacao e remocao de empresas.
- Gerenciamento de funcionarios.
- Gerenciamento de usuarios por empresa.
- Criacao, edicao, consulta e exclusao de contratos.
- Atualizacao de status de contratos.
- Hierarquia de perfis: `ADMIN`, `GESTOR`, `ADVOGADO`, `ANALISTA` e `ESTAGIARIO`.

## Tecnologias

- Java 17
- Spring Boot 3.2.2
- Spring Web
- Spring Security
- Spring Data JPA
- Bean Validation
- PostgreSQL
- JWT com `java-jwt`
- Maven

## Estrutura

- `src/main/java/legalfasystem/controller`: endpoints REST.
- `src/main/java/legalfasystem/service`: regras de negocio.
- `src/main/java/legalfasystem/model`: entidades JPA.
- `src/main/java/legalfasystem/dto`: contratos de entrada e saida da API.
- `src/main/java/legalfasystem/repository`: repositorios Spring Data.
- `src/main/java/legalfasystem/infra/security`: JWT, filtro de seguranca, CORS e configuracao do Spring Security.
- `src/test/java/legalfasystem`: testes automatizados.

## Configuracao

Crie um banco PostgreSQL chamado `legalfasystem` e ajuste `src/main/resources/application.properties` conforme o seu ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/legalfasystem
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Tambem e recomendado definir o segredo JWT por variavel de ambiente:

```bash
set JWT_SECRET=sua-chave-secreta
```

No PowerShell:

```powershell
$env:JWT_SECRET="sua-chave-secreta"
```

Por padrao, o CORS permite:

```text
http://localhost:5173
```

Para liberar outra origem, configure `cors.allowed-origins`.

## Como executar

No diretorio do projeto:

```bash
mvn spring-boot:run
```

A API fica disponivel em:

```text
http://localhost:8080
```

## Testes

```bash
mvn test
```

## Rotas principais

### Autenticacao

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `POST` | `/auth/login` | Autentica usuario e retorna token JWT |
| `POST` | `/auth/register` | Registra usuario vinculado a uma empresa |

### Empresas

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `GET` | `/api/empresas` | Lista empresas |
| `GET` | `/api/empresas/{id}` | Busca empresa por ID |
| `POST` | `/api/empresas` | Cria empresa |
| `PUT` | `/api/empresas/{id}` | Atualiza empresa |
| `PUT` | `/api/empresas/{id}/simples` | Atualizacao simples de empresa |
| `DELETE` | `/api/empresas/{id}` | Remove empresa |

### Contratos

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `GET` | `/api/contratos/empresa/{empresaId}` | Lista contratos de uma empresa |
| `GET` | `/api/contratos/{id}` | Busca contrato por ID |
| `POST` | `/api/contratos` | Cria contrato |
| `PUT` | `/api/contratos/{id}` | Atualiza contrato |
| `PATCH` | `/api/contratos/{id}/status` | Atualiza status |
| `DELETE` | `/api/contratos/{id}` | Remove contrato |

### Usuarios e funcionarios

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `PUT` | `/api/usuarios/me` | Atualiza dados do usuario autenticado |
| `POST` | `/api/usuarios/empresa` | Cria usuario na empresa do gestor |
| `GET` | `/api/usuarios/empresa/meus-usuarios` | Lista usuarios da empresa |
| `DELETE` | `/api/usuarios/{usuarioId}` | Remove usuario |
| `GET` | `/funcionarios` | Lista funcionarios |
| `GET` | `/funcionarios/{id}` | Busca funcionario por ID |
| `PUT` | `/funcionarios/{id}` | Atualiza funcionario |
| `DELETE` | `/funcionarios/{id}` | Remove funcionario |

## Autorizacao

Para rotas protegidas, envie o token no header:

```http
Authorization: Bearer seu-token-jwt
```

Status de contrato aceitos:

```text
RASCUNHO, EM_REVISAO, APROVADO, ASSINADO, CANCELADO, EXPIRADO
```
