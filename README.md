# 🏫 API de Gerenciamento de Transporte Escolar

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

Esta é uma API REST robusta desenvolvida para o gerenciamento completo de transporte escolar, abrangendo o controle de alunos, responsáveis, escolas, pagamentos e endereços. O projeto foca em **Clean Code**, **SOLID** e padrões arquiteturais modernos.

## 🚀 Tecnologias Utilizadas

- **Java 21** & **Spring Boot 3**
- **Spring Data JPA**: Persistência de dados.
- **Spring Cloud OpenFeign**: Integração com a API ViaCEP para busca automática de endereços.
- **PostgreSQL**: Banco de dados relacional.
- **ModelMapper**: Mapeamento inteligente entre Entidades e DTOs.
- **Bean Validation (@Valid)**: Validações rigorosas de dados (CPF, Email, campos obrigatórios).
- **Docker & Docker Compose**: Containerização de toda a aplicação e banco de dados.
- **OpenAPI (Swagger)**: Documentação interativa da API.

## 🏗️ Arquitetura e Boas Práticas

O projeto foi construído seguindo padrões de mercado para garantir escalabilidade e fácil manutenção:

* **Padrão DTO (Data Transfer Object):** Isolamento total entre a camada de persistência e a camada de apresentação, garantindo segurança e flexibilidade.
* **Tratamento Global de Exceções:** Uso de `@ControllerAdvice` para fornecer respostas padronizadas e amigáveis ao cliente da API.
* **Injeção de Dependências:** Uso de `@RequiredArgsConstructor` do Lombok, seguindo a boa prática de injeção via construtor.
* **Auditoria JPA:** Registro automático de datas de criação com `@CreatedDate`.
* **Multi-stage Build (Docker):** Processo de build otimizado para gerar imagens leves e seguras.

## 🛠️ Como Executar o Projeto

Com o Docker instalado em sua máquina, basta clonar o repositório e rodar o comando abaixo na raiz do projeto:

bash
docker-compose up -d
A API estará disponível em http://localhost:8080.
Frontend: http://localhost
Documentação da API
Para visualizar e testar os endpoints, acesse o Swagger UI:
http://localhost:8080/swagger-ui/index.html

📡 Endpoints Principais
Responsáveis: Cadastro e busca por CPF (O CPF é protegido e ocultado na visualização padrão).

Endereços: Integração automática com ViaCEP através do envio do CEP.

Alunos: Gestão vinculada a responsáveis e escolas.

Pagamentos: Controle de mensalidades com status e auditoria de datas.

Desenvolvido por Carlos Roberto Ribeiro Santos Junior

<img width="1920" height="1080" alt="te1" src="https://github.com/user-attachments/assets/d66780aa-593d-4d83-a094-37882ad6de4d" />





