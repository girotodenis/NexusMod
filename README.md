# NexusMod

NexusMod é uma base modular desenvolvida com Apache Felix OSGi, projetada para servir como ponto de partida para a construção de aplicações desktop modulares. Este projeto tem como objetivo ser um estudo de viabilidade, explorando a flexibilidade e escalabilidade proporcionadas pela arquitetura OSGi.

## Objetivo

O principal objetivo do NexusMod é fornecer uma base sólida para aplicações desktop modulares, facilitando o desenvolvimento de projetos que compartilhem componentes e promovendo a reutilização de código. Além disso, este projeto busca explorar o potencial do Apache Felix OSGi, avaliando sua aplicabilidade em projetos reais.

## Características

- Arquitetura Modular: Construído com base no Apache Felix OSGi, permitindo a criação de módulos independentes e reutilizáveis.
- Extensibilidade: Suporte para adicionar ou substituir módulos sem necessidade de alterar a base do projeto.
- Foco em Projetos Desktop: Otimizado para o desenvolvimento de aplicações desktop.
- Propósito Educacional: Voltado para o aprendizado e experimentação com tecnologias de modularidade.

## Requisitos

Para executar ou desenvolver com o FelixCore, você precisará dos seguintes requisitos:

- **Java 17+** (JDK instalado)
- **Apache Maven** para gerenciamento de dependências e build
- **Apache Felix Framework**

## Estrutura do Projeto

A estrutura do projeto é modular e segue um modelo baseado em OSGi:
NexusMod/
├── core/ # Módulo principal do sistema
├── modules/ # Módulos adicionais (plugins ou funcionalidades)
├── config/ # Arquivos de configuração
├── docs/ # Documentação do projeto
└── README.md # Este arquivo

## Instalação

Siga as etapas abaixo para configurar e executar o projeto localmente:

1. Clone este repositório:
  
   git clone https://github.com/girotodenis/NexusMod.git
   cd NexusMod

2. Compile o projeto usando o Maven:
   mvn clean install
   
