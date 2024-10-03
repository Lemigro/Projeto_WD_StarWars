
# Documento de Requisitos - Star Wars App

## 1. Visão Geral
O **Star Wars App** tem como objetivo permitir que os usuários explorem informações detalhadas sobre personagens, veículos, naves, espécies e filmes da saga Star Wars, consumindo dados da API pública **SWAPI**. O aplicativo será dividido em diferentes abas e permitirá favoritar itens. Ele seguirá a arquitetura **MVC**, separando as responsabilidades da lógica de negócio, interface de usuário e controle.

## 2. Requisitos Funcionais

### 2.1. Autenticação
- O usuário deve ser capaz de se autenticar no sistema usando nome completo e senha.
- O sistema deve validar os dados de login e impedir acessos não autorizados.
- O usuário deve permanecer logado durante a sessão, mas deve ter a opção de logout.
- **MVC**: A autenticação será gerenciada pelo Model, que armazenará as credenciais e realizará a validação. A View exibirá os campos de login, e o Controller coordenará a interação entre ambos.

### 2.2. Exibição de Dados
O sistema deve exibir informações detalhadas e organizadas em diferentes categorias. Cada item será exibido com nome, descrição e, quando possível, uma imagem. As categorias são:

- **Personagens (Characters)**:
  - Campos exibidos: nome, altura, peso, cor do cabelo, cor dos olhos, ano de nascimento, gênero e planeta natal.
  - Os dados devem ser apresentados de forma paginada.
  - O usuário poderá clicar em um personagem para ver mais detalhes e favoritar.

- **Veículos (Vehicles)**:
  - Campos exibidos: nome, modelo, fabricante, custo, comprimento, número de passageiros, capacidade de carga, classe do veículo.
  - Cada veículo será mostrado com uma breve descrição e detalhes adicionais ao clicar.

- **Naves Estelares (Starships)**:
  - Campos exibidos: nome, modelo, fabricante, custo, comprimento, tripulação, número de passageiros, velocidade máxima e classe de nave.
  - Assim como em veículos, o usuário poderá visualizar e favoritar naves estelares.

- **Espécies (Species)**:
  - Campos exibidos: nome, classificação, designação, altura média, cor da pele, cor do cabelo, cor dos olhos, tempo médio de vida e idioma.
  - O usuário poderá explorar as espécies e obter mais informações sobre as principais características de cada uma.

- **Filmes (Films)**:
  - Campos exibidos: título, diretor, produtor, data de lançamento, abertura (crawl).
  - O usuário poderá acessar a lista de filmes e visualizar detalhes ao clicar, como elenco e navegação entre filmes.

### 2.3. Detalhamento de Itens
- O sistema deve permitir ao usuário clicar em um item da lista (personagem, veículo, nave, espécie ou filme) para ver uma tela de detalhes.
- A tela de detalhes deve exibir todas as informações detalhadas do item, incluindo imagens, quando disponíveis.
- **MVC**: O Controller gerenciará a navegação entre as listas e a exibição dos detalhes, solicitando ao Model os dados para exibição.

### 2.4. Favoritos
- O usuário deve poder marcar personagens, veículos, naves, espécies ou filmes como favoritos.
- O sistema deve salvar os favoritos no banco de dados para que estejam disponíveis em sessões futuras.
- O usuário deve poder visualizar e remover favoritos de uma lista dedicada.
- **MVC**: O Model armazenará os favoritos e o Controller controlará a lógica de adicionar e remover favoritos.

### 2.5. Navegação
- O aplicativo deve permitir a navegação entre as categorias (Personagens, Planetas, Veículos, Espécies, Naves Estelares, Filmes, Favoritos) utilizando um **TabLayout**.
- O sistema deve ser fácil de navegar e seguir as diretrizes de design do Android (Material Design).
- **MVC**: O Controller gerenciará as mudanças de navegação, coordenando o conteúdo a ser exibido pela View.

## 3. Requisitos Não Funcionais

### 3.1. Performance
- O sistema deve carregar as listas com tempo de resposta inferior a 2 segundos em uma conexão estável de internet.

### 3.2. Usabilidade
- O sistema deve ser intuitivo e seguir o padrão de usabilidade do Android, permitindo fácil navegação e manipulação dos dados.

### 3.3. Segurança
- Os dados de login do usuário devem ser criptografados e protegidos contra acessos não autorizados.
- Favoritos e dados do usuário devem ser armazenados em um banco de dados seguro (Firebase Realtime Database, por exemplo).
- **MVC**: A separação clara entre Model, View e Controller no MVC ajudará a modularizar a lógica de segurança e proteger dados de forma mais eficiente.

### 3.4. Compatibilidade
- O sistema deve ser compatível com versões do Android API 21 (Lollipop) ou superior.

## 4. Casos de Uso

### Caso de Uso 1: Autenticação
- **Ator**: Usuário
- **Descrição**: O usuário insere seu nome completo e senha. O sistema valida as credenciais e, se estiverem corretas, permite o acesso à aplicação.
  - **Fluxo principal**:
    1. O usuário insere o nome completo e a senha.
    2. O sistema valida as credenciais.
    3. O sistema autentica o usuário e o redireciona para a tela principal.
  - **Fluxo alternativo**: Se as credenciais forem inválidas, o sistema exibe uma mensagem de erro.

### Caso de Uso 2: Favoritar Personagem, Veículo, Nave, Espécie ou Filme
- **Ator**: Usuário
- **Descrição**: O usuário pode favoritar personagens, veículos, naves, espécies ou filmes ao visualizar seus detalhes.
  - **Fluxo principal**:
    1. O usuário navega até a tela de detalhes de um item.
    2. O usuário clica no ícone de "favoritar".
    3. O sistema salva o item como favorito e exibe uma confirmação.

### Caso de Uso 3: Exibir Favoritos
- **Ator**: Usuário
- **Descrição**: O usuário pode visualizar todos os seus itens favoritados.
  - **Fluxo principal**:
    1. O usuário navega até a aba de "Favoritos".
    2. O sistema exibe todos os itens favoritados pelo usuário.
