# README - Sistema Porto Seco

## Descrição do Projeto

O **Sistema Porto Seco** é um sistema de gestão para controle de cargas em um porto seco. Ele permite a administração de cargas, usuários e operações logísticas, garantindo um fluxo organizado e eficiente.

O projeto foi desenvolvido utilizando **Java** e **MySQL**, proporcionando um ambiente robusto e seguro para o gerenciamento de dados.

## Tecnologias Utilizadas

- **Java**: Linguagem principal do projeto
- **MySQL**: Banco de dados utilizado
- **JDBC (Java Database Connectivity)**: Conexão com o banco de dados
- **NetBeans**: IDE utilizada para o desenvolvimento
- **Bibliotecas externas**:
  - `mysql-connector-java-8.0.28.jar`
  - `RSMaterialComponents_3.0.jar`
  - `AbsoluteLayout.jar`
  - `RojeruSan.full.jar`
  
## Estrutura de Arquivos

```
├── src/                      # Código-fonte principal
│   ├── admin/                # Funcionalidades administrativas
│   ├── conecta/              # Configuração da conexão com o banco
│   ├── dashboards/           # Telas principais por tipo de usuário
│   ├── empresa/              # Gerenciamento de empresas
│   ├── login/                # Autenticação e cadastro de usuários
│   ├── mapeamento_poo/       # Classes de mapeamento de entidades
│   ├── oper/                 # Operações relacionadas a cargas e despacho
├── build/                    # Arquivos compilados
├── dist/                     # Versão distribuível do sistema
├── porto_seco.sql            # Script do banco de dados
├── mysql-connector-java-8.0.28.jar # Driver de conexão com MySQL
├── README.md                 # Documento com informações do projeto
```

## Funcionalidades

- Cadastro e edição de cargas
- Controle de usuários (admin, operador, empresa)
- Dashboard para cada tipo de usuário
- Autenticação segura com banco de dados
- Gerenciamento de despachos e cargas

## Configuração do Projeto

### Pré-requisitos

Para rodar o projeto localmente, você precisa ter:

- **Java JDK 8 ou superior**
- **MySQL Server** instalado e configurado
- **NetBeans** (recomendado) ou outra IDE compatível

### Como Executar

1. **Importar o Banco de Dados**:
   - No MySQL, crie um banco de dados chamado `porto_seco`
   - Execute o script `porto_seco.sql` para criar as tabelas

2. **Configurar a Conexão no Código**:
   - No arquivo `Conexao.java`, ajuste as credenciais do banco de dados, se necessário.

3. **Compilar e Executar**:
   - Abra o projeto no **NetBeans** e execute a classe principal.
   - Alternativamente, use o terminal:
     ```bash
     javac -cp .:mysql-connector-java-8.0.28.jar src/main.java
     java -cp .:mysql-connector-java-8.0.28.jar main
     ```

## Créditos

- Desenvolvido por [Seu Nome]

## Licença

Este projeto é de uso privado e não pode ser redistribuído sem autorização.

