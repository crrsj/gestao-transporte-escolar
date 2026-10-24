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
<img width="1920" height="1080" alt="te2" src="https://github.com/user-attachments/assets/fb9c6ce4-348e-4d61-90d2-0c7f35921038" />
<img width="1920" height="1080" alt="te3" src="https://github.com/user-attachments/assets/f721c0c9-4c68-4984-bd8c-cfad5bb4a9d7" />
<img width="1920" height="1080" alt="te14" src="https://github.com/user-attachments/assets/b0993a3b-ff95-4720-a5dd-1f02f1bdc78f" />
<img width="1920" height="1080" alt="te13" src="https://github.com/user-attachments/assets/c7252b6e-e56b-44a4-a84f-dd64015e0673" />
<img width="1920" height="1080" alt="te12" src="https://github.com/user-attachments/assets/bb7f1b8f-ecc2-4079-b860-5914240961b2" />
<img width="1920" height="1080" alt="te11" src="https://github.com/user-attachments/assets/0244f2df-17cc-422b-8f6e-5da54b1642eb" />
<img width="1920" height="1080" alt="te10" src="https://github.com/user-attachments/assets/bcec7450-45ca-4646-a120-b86151260c48" />
<img width="1920" height="1080" alt="te9" src="https://github.com/user-attachments/assets/44b8c295-2447-4330-ab44-625bf3fc1ee6" />
<img width="1920" height="1080" alt="te8" src="https://github.com/user-attachments/assets/cb704045-bbe2-475b-80af-71b755197b3f" />
<img width="1920" height="1080" alt="te7" src="https://github.com/user-attachments/assets/031d7b28-a069-48d3-846b-50a7b05bf6cd" />
<img width="1920" height="1080" alt="te6" src="https://github.com/user-attachments/assets/b8b4c855-f9cf-486e-a5f0-ac2c27c47e76" />
<img width="1920" height="1080" alt="te5" src="https://github.com/user-attachments/assets/99f47d97-b5d1-4e2a-95f3-10cadace2676" />
<img width="1920" height="1080" alt="te4" src="https://github.com/user-attachments/assets/ebb8a8a6-c9b4-4ddc-9d2b-dfec3f2e9041" />








