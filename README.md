# Forum Hub API 🚀

API desenvolvida como parte do desafio **Fórum Hub**, 
que simula um fórum acadêmico. 
A aplicação permite que alunos e professores interajam por meio 
de tópicos e respostas. O sistema oferece autenticação com JWT, 
controle de perfis, versionamento do banco com Flyway e 
documentação automática com Swagger.


## 📋 Funcionalidades

- **Usuários**: Cadastro, listagem, atualização e exclusão de usuários
- **Tópicos**: Criação, listagem paginada, detalhamento, atualização e exclusão
- **Respostas**: Responder tópicos e marcar respostas como solução
- **Autenticação**: Sistema de login com JWT
- **Documentação**: Interface Swagger/OpenAPI integrada
- **Segurança**: Proteção de endpoints com Spring Security
- **Tratamento de Erros**: Sistema robusto de tratamento global de exceções

## 🛠 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Security** (Autenticação JWT)
- **Spring Data JPA** (Persistência)
- **MySQL** (Banco de dados)
- **Flyway** (Migrações de banco)
- **Lombok** (Redução de boilerplate)
- **SpringDoc OpenAPI** (Documentação)
- **Maven** (Gerenciamento de dependências)

## 📋 Pré-requisitos

- Java 21+
- MySQL 8.0+
- Maven 3.6+

## 🚀 Como executar

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd forum-hub
```

### 2. Configure o banco de dados
```sql
CREATE DATABASE forum_hub;
```

### 3. Configure as variáveis de ambiente
```bash
export MYSQL_ROOT_PASSWORD=sua_senha_mysql
export JWT_SECRET=seu_jwt_secret_seguro
```

### 4. Execute a aplicação
```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

A documentação interativa está disponível via Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

## 🔐 Autenticação e Segurança

A API utiliza um sistema robusto de autenticação JWT com Spring Security.

### Como Autenticar

1. **Login**: Faça POST em `/login` com suas credenciais
2. **Token**: Receba o token JWT na resposta
3. **Uso**: Inclua o token no header de todas as requisições protegidas:
   ```
   Authorization: Bearer {seu-jwt-token}
   ```

### Configurações de Segurança

- **Algoritmo**: HMAC256
- **Expiração**: 2 horas
- **Issuer**: "API Forum Hub"
- **Timezone**: UTC-3 (Brasil)

### Endpoints Públicos

- `POST /login` - Autenticação
- `/swagger-ui/**` - Documentação Swagger
- `/v3/api-docs/**` - Especificação OpenAPI

### Sistema de Filtros

A API possui um filtro customizado (`SecurityFilter`) que:
- Intercepta todas as requisições
- Valida tokens JWT automaticamente
- Autentica usuários no contexto do Spring Security
- Fornece logs detalhados para debugging

### Exemplo de Resposta de Login

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

## 📋 Endpoints Principais

### Autenticação
- `POST /login` - Fazer login e obter token JWT

### Usuários
- `GET /usuario` - Listar todos os usuários *(requer auth)*
- `POST /usuario` - Cadastrar novo usuário *(requer auth)*
- `PUT /usuario/{id}` - Atualizar usuário *(requer auth)*
- `DELETE /usuario/{id}` - Deletar usuário *(requer auth)*

### Tópicos
- `GET /topicos` - Listar tópicos (com paginação e filtro por curso) *(requer auth)*
- `GET /topicos/{id}` - Detalhar tópico específico *(requer auth)*
- `POST /topicos` - Criar novo tópico *(requer auth)*
- `PUT /topicos/{id}` - Atualizar tópico *(requer auth)*
- `DELETE /topicos/{id}` - Deletar tópico *(requer auth)*

### Respostas
- `POST /respostas` - Responder a um tópico *(requer auth)*
- `PUT /respostas/{id}/solucao` - Marcar resposta como solução *(requer auth)*

## 📝 Estrutura do Projeto

```
src/main/java/com.forum.hub
├── controller
│   ├── AutenticacaoController
│   ├── RespostaController
│   ├── TopicoController
│   └── UsuarioController
│
├── exception
│   ├── AccessDeniedException
│   ├── BusinessException
│   ├── ErrorResponse
│   ├── GlobalExceptionHandler
│   ├── ResourceNotFoundException
│   ├── UserUnauthorizedException
│   └── ValidacaoException
│
├── model
│   ├── autenticacao
│   │   ├── AutenticacaoService
│   │   └── DadosAutenticacao
│   │
│   ├── resposta
│   │   ├── DadosCadastroResposta
│   │   ├── DadosDetalhamentoResposta
│   │   ├── Resposta
│   │   ├── RespostaRepository
│   │   └── RespostaService
│   │
│   ├── topico
│   │   ├── DadosDetalhamentoTopico
│   │   ├── DadosListagemTopico
│   │   ├── StatusTopico
│   │   ├── Topico
│   │   ├── TopicoCreateDTO
│   │   ├── TopicoRepository
│   │   ├── TopicoResponseDTO
│   │   └── TopicoService
│   │
│   └── usuario
│       ├── Perfis
│       ├── Usuario
│       ├── UsuarioCreateDTO
│       ├── UsuarioRepository
│       ├── UsuarioResponseDTO
│       └── UsuarioService
│
├── security
│   ├── DadosTokenJWT
│   ├── SecurityConfig
│   ├── SecurityFilter
│   └── TokenService
│
├── springdoc
│   └── SpringDocConfiguration
│
└── ForumHubApplication.java

resources
├── application.properties
└── db.migration
    ├── V1__create_topicos_table.sql
    ├── V2__alterar_tabela_topico.sql
    ├── V3__criar_tabela_usuario.sql
    ├── V4__create_resposta.sql
    ├── V5__alter_topico.sql
    └── V6__alter_table_usuario.sql

```

## 🗄 Modelo de Dados

### Usuario
- id (Long)
- nome (String)
- login (String)
- senha (String)
- perfis (Enum)

### Topico
- id (Long)
- titulo (String)
- mensagem (String)
- dataCriacao (LocalDateTime)
- status (StatusTopico)
- curso (String)
- autor (Usuario)
- respostas (List<Resposta>)

### Resposta
- id (Long)
- mensagem (String)
- dataCriacao (LocalDateTime)
- solucao (boolean)
- topico (Topico)
- autor (Usuario)

## 🔧 Configuração

### Banco de Dados
Configure as propriedades do MySQL em `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forum_hub
spring.datasource.username=root
spring.datasource.password=${MYSQL_ROOT_PASSWORD}
```

### JWT
Configure o secret do JWT (use um valor seguro em produção):
```properties
api.security.token.secret=${JWT_SECRET:seu-secret-super-seguro}
```

### Swagger
A documentação é configurada automaticamente com suporte a autenticação JWT:
```properties
# Acesse em: http://localhost:8080/swagger-ui.html
# Use o botão "Authorize" para inserir o token Bearer
```

## 📊 Recursos Adicionais

- **Paginação**: Os endpoints de listagem suportam paginação
- **Filtros**: Filtragem de tópicos por curso
- **Logs detalhados**: Configuração completa de logging
- **Migrações**: Controle de versão do banco com Flyway
- **Validação**: Validação de dados com Bean Validation
- **Tratamento de Erros**: Sistema global de tratamento de exceções com respostas padronizadas

## 🚨 Tratamento de Erros

A API possui um sistema robusto de tratamento de exceções que retorna respostas padronizadas:

### Tipos de Erro Tratados

- **400 Bad Request**: Dados inválidos, argumentos ilegais
- **403 Forbidden**: Acesso negado, usuário não autorizado
- **404 Not Found**: Recurso não encontrado
- **405 Method Not Allowed**: Método HTTP não suportado
- **409 Conflict**: Violação de integridade (dados duplicados, chaves estrangeiras)
- **422 Unprocessable Entity**: Erros de regras de negócio
- **500 Internal Server Error**: Erros não tratados

### Formato de Resposta de Erro

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Erro de Validação",
  "message": "Dados inválidos fornecidos",
  "path": "/topicos",
  "validationErrors": {
    "titulo": "não pode estar vazio",
    "curso": "é obrigatório"
  }
}
```

### Exceções Customizadas

- `AccessDeniedException`: Acesso negado a recursos
- `BusinessException`: Violação de regras de negócio
- `ResourceNotFoundException`: Recurso não encontrado
- `UserUnauthorizedException`: Usuário não autorizado
- `ValidacaoException`: Erros de validação customizados

## 🧪 Testes

Execute os testes com:
```bash
mvn test
```

## 📦 Build

Para gerar o JAR da aplicação:
```bash
mvn clean package
```

O arquivo JAR será gerado em `target/forum-hub-0.0.1-SNAPSHOT.jar`

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request


---

Desenvolvido com ❤️ e Java por Jessica Machado.