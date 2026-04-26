# Sistema de Simulação de Financiamentos Imobiliários

Este projeto é uma aplicação Java desenvolvida para a disciplina de **Fundamentos da Programação Orientada a Objetos** do curso de **Big Data e Inteligência Analítica** da **PUCPR**.

O objetivo é simular múltiplos financiamentos, gerenciando coleções de objetos na memória e aplicando regras de negócio realistas do mercado financeiro, utilizando técnicas avançadas de manipulação de dados e estruturação de software.

## Funcionalidades e Refinamentos

O sistema foi projetado para oferecer uma experiência de simulação completa e dinâmica:

- **Seleção de Perfil**: Escolha entre Casa, Apartamento, Terreno ou um **Perfil Misto** (gera uma carteira diversificada automaticamente).
- **Entrada de Dados Inteligente**: O sistema sugere taxas anuais baseadas no tipo de imóvel (Casa: 9.5%, Apto: 10.5%, Terreno: 11.0%).
- **Lógica de Localização (Data Analytics)**: Para Terrenos, o sistema permite definir a zona ou gera uma distribuição estatística (3 Urbanos e 2 Rurais) caso o usuário opte pelo preenchimento automático.
- **Resumo Expandido**: Relatório consolidado exibindo o **Custo Efetivo Total** (Capital + Juros) e detalhes específicos de cada ativo.
- **Ciclo de Simulação Contínuo**: Implementação de um loop de execução que permite múltiplas rodadas de análise sem reiniciar o programa.

## Fundamentação Acadêmica e Lógica do Projeto

A arquitetura do projeto demonstra o domínio dos pilares da POO aplicados ao contexto de análise de dados:

1.  **Abstração e Herança**: A classe `Financing` é abstrata, definindo o contrato base para todos os modelos de negócio. As subclasses (`HouseFinancing`, `ApartmentFinancing`, `LandFinancing`) herdam e especializam esses comportamentos.
2.  **Encapsulamento**: Proteção da integridade dos dados financeiros através de modificadores de acesso e isolamento da lógica de IO na classe `UserInterface`.
3.  **Polimorfismo**: Processamento dinâmico de uma `List<Financing>`. O sistema invoca os métodos `calculateMonthlyPayment()` e `getSpecificDetails()` de forma polimórfica, executando a implementação correta para cada tipo de imóvel em tempo de execução.
4.  **Regras de Negócio Diferenciadas**:
    *   **Casas**: Taxa de seguro fixa inclusa na parcela.
    *   **Apartamentos**: Cálculo baseado em juros compostos para refletir financiamentos de longo prazo.
    *   **Terrenos**: Acréscimo de risco de 2% sobre o valor da parcela.

## Estrutura do Projeto
- `main`: Orquestração do fluxo e controle de loops.
- `modelo`: Entidades de negócio e algoritmos de cálculo financeiro.
- `util`: Utilitários para interação com o usuário e tratamento de erros.

## Como Executar e Testar

### Pré-requisitos
- **Java JDK 19** ou superior instalado.
- Uma IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code) ou terminal.

### Passo a Passo
1.  **Clonar/Baixar**: Certifique-se de que os arquivos estão na estrutura de pastas correta (`src/main`, `src/modelo`, `src/util`).
2.  **Compilar**:
    ```bash
    javac src/main/Main.java src/modelo/*.java src/util/*.java -d out
    ```
3.  **Executar**:
    ```bash
    java -cp out main.Main
    ```
4.  **No IntelliJ/Eclipse**: Basta abrir a pasta raiz como um projeto Java e executar o método `main` na classe `Main.java`.

---
**Projeto desenvolvido para fins acadêmicos - PUCPR - Big Data e Inteligência Analítica.**

***Desenvolvido por Thiago Ribeiro Rosa (T-Analitycs).***
