# Sistema de Simulação de Financiamentos Imobiliários

Este projeto é uma aplicação Java desenvolvida para a disciplina de **Fundamentos da Programação Orientada a Objetos** do curso de **Big Data e Inteligência Analítica** da **PUCPR**.

O objetivo é simular múltiplos financiamentos, gerenciando coleções de objetos na memória e aplicando regras de negócio realistas do mercado financeiro, utilizando técnicas avançadas de manipulação de dados e estruturação de software.

## Novas Funcionalidades e Resiliência

O sistema foi aprimorado para garantir robustez técnica e conformidade com regras de negócio:

- **Tratamento de Exceções Hierárquico**: Implementação de `try-catch` robusto com captura de `IllegalArgumentException` para entradas negativas e `NumberFormatException` para formatos inválidos.
- **Lógica de Compliance (Exceção Verificada)**: Criação da exceção personalizada `AumentoMaiorDoQueJurosException`. Caso o acréscimo de seguro da Casa exceda 50% dos juros, o sistema atua como um "Gerente Virtual" e ajusta o valor automaticamente para garantir a viabilidade do contrato.
- **Gerenciamento de Recursos**: Uso de métodos de fechamento de recursos (`scanner.close()`) para evitar vazamentos de memória.
- **Interface Polimórfica**: Centralização das traduções e nomes amigáveis dentro da superclasse `Financing`, eliminando redundâncias no código principal.
- **Motor Analítico em Lote**: Geração automatizada de portfólios variados com variações de mercado de ±10% nos valores dos imóveis.

## Fundamentação Acadêmica e Lógica do Projeto

A arquitetura do projeto demonstra o domínio dos pilares da POO aplicados ao contexto de análise de dados:

1.  **Abstração e Herança**: Uso de superclasse abstrata `Financing` para padronizar o comportamento de diversos ativos imobiliários.
2.  **Encapsulamento**: Atributos protegidos e centralização de lógica de tradução (`getFriendlyTypeName`) e validação.
3.  **Polimorfismo**: Chamada dinâmica de métodos de cálculo e descrição, onde cada objeto responde de acordo com sua especialização em tempo de execução.
4.  **Tratamento de Erros e Exceções**: Diferenciação entre exceções de runtime (erros de input) e exceções de negócio (violação de regras de financiamento).

## 📂 Estrutura do Projeto
- `main`: Orquestração do fluxo, geração de lotes e tratamento de erros globais.
- `modelo`: Entidades de negócio (`House`, `Apartment`, `Land`).
- `modelo.excecao`: Definição de exceções personalizadas de negócio.
- `util`: Gerenciamento de interface e validação de dados de entrada.

## Como Executar e Testar

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

***Desenvolvido por Thiago Ribeiro Rosa (T-Analitycs).***
