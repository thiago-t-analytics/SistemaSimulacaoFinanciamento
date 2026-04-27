# Sistema de Simulação de Financiamentos Imobiliários

Este projeto é uma aplicação Java desenvolvida para a disciplina de **Fundamentos da Programação Orientada a Objetos** do curso de **Big Data e Inteligência Analítica** da **PUCPR**.

O sistema simula um ecossistema financeiro para análise de portfólios imobiliários, aplicando rigorosos conceitos de engenharia de software e persistência de dados.

## Novas Funcionalidades e Resiliência

O sistema foi aprimorado para garantir robustez técnica e conformidade com regras de negócio:

- **Persistência Híbrida de Dados**: 
  - **Relatório Analítico (.txt)**: Gera um histórico acumulativo e formatado dentro da pasta `/outputs`, com labels gerenciais e formatação de moeda (BRL).
  - **Serialização de Objetos (.ser)**: Salva o estado real da memória em disco, permitindo a persistência completa do ecossistema de objetos.
- **Tratamento de Exceções Hierárquico**: Captura de erros de input (`NumberFormatException`) e validações de negócio (`IllegalArgumentException`).
- **Lógica de Compliance e Resiliência**: Implementação da exceção verificada `LimitedAdditionException` (Checked Exception). O sistema possui um "Gerente Virtual" que ajusta automaticamente acréscimos abusivos em financiamentos de casas.
- **Interface Híbrida**: Combinação de console para entrada de dados e `JOptionPane` (GUI) para feedbacks e notificações de sistema.
- **Motor Analítico em Lote**: Simulação automatizada de 5 modelos de financiamento com variações dinâmicas de mercado (±10%).

## Fundamentação Acadêmica e Lógica do Projeto

A arquitetura do projeto demonstra o domínio dos pilares da POO aplicados ao contexto de análise de dados:

1.  **Abstração**: Uso da classe abstrata `Financing` para definir o contrato financeiro global.
2.  **Herança**: Especialização das regras de cálculo e atributos para `HouseFinancing`, `ApartmentFinancing` e `LandFinancing`.
3.  **Encapsulamento**: Proteção de atributos e centralização de métodos utilitários, como a tradução polimórfica de tipos (`getFriendlyTypeName`).
4.  **Polimorfismo**: Processamento de coleções heterogêneas (`List<Financing>`), onde o comportamento de cálculo mensal e detalhamento é resolvido em tempo de execução.

## Estrutura do Projeto
- `main`: Orquestração do fluxo, persistência e controle de simulação.
- `modelo`: Classes de domínio e entidades financeiras.
- `modelo.excecao`: Exceções personalizadas para regras de negócio.
- `util`: Motores de persistência (`FileUtils`) e interface de usuário (`UserInterface`).
- `outputs`: Pasta gerada automaticamente para armazenamento dos arquivos de histórico.

### Pré-requisitos
- **Java JDK 19** ou superior instalado.
- Uma IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code) ou terminal.

### Passo a Passo
1.  **Clonar/Baixar**: Certifique-se de que os arquivos estão na estrutura de pastas correta (`src/main`, `src/modelo`, `src/util`).
2.  **Compilar**:
    ```bash
    javac src/main/Main.java src/modelo/*.java src/modelo/excecao/*.java src/util/*.java -d out
    ```
3.  **Executar**:
    ```bash
    java -cp out main.Main
    ```
4.  **No IntelliJ/Eclipse**: Basta abrir a pasta raiz como um projeto Java e executar o método `main` na classe `Main.java`.

---
**Projeto desenvolvido para fins acadêmicos - PUCPR - Big Data e Inteligência Analítica.**

***Desenvolvido por Thiago Ribeiro Rosa (T-Analytics).***
