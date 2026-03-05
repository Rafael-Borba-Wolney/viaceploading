# ViaCEP Loading

Projeto Java com Maven que realiza consulta de CEP utilizando a API ViaCEP.

## Funcionalidades

* Leitura de CEP informado pelo usuário no terminal
* Limpeza e validação do CEP
* Consulta da API ViaCEP
* Execução da busca em uma thread separada
* Exibição de animação de carregamento enquanto a busca é realizada
* Exibição do endereço retornado pela API

## Tecnologias utilizadas

* Java 17
* Maven
* API ViaCEP
* Jackson Databind

## Estrutura do projeto

* `Main.java` → classe principal da aplicação
* `CepUtils.java` → utilitário para limpar e validar CEP
* `Endereco.java` → modelo que representa a resposta da API
* `ViaCepService.java` → serviço responsável por consumir a API ViaCEP

## Como executar o projeto

Na raiz do projeto, execute:

`mvn compile`

Depois:

`mvn exec:java`

## Exemplo de uso

CEP informado:

`74735060`

Resposta esperada:

* Logradouro: Rua Capauam
* Bairro: Jardim Califórnia
* Cidade: Goiânia
* UF: GO

## API utilizada

ViaCEP:
https://viacep.com.br/

## Autor

Rafael
