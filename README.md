# Star Wars App - Projeto Android com API SWAPI

## Descrição do Projeto
Este projeto tem como objetivo criar um aplicativo mobile que consome a API pública do Star Wars ([SWAPI](https://swapi.dev/)). O app permitirá que os usuários explorem informações detalhadas sobre personagens, veículos e planetas da saga Star Wars. Utilizando a arquitetura **MVC (Model-View-Controller)**, o projeto será modular, de fácil manutenção e escalável.

O app incluirá as seguintes funcionalidades:
- **Login**: Autenticação com nome completo e senha.
- **Visualização de dados**: Exibição de personagens, veículos e planetas organizados em abas.
- **Detalhamento**: Tela de detalhes com informações adicionais ao clicar em um item.
- **Favoritos**: O usuário poderá favoritar personagens e visualizá-los posteriormente.
- **Navegação**: Navegação fácil e intuitiva utilizando **TabLayout**.

## Integrante do Grupo
- **Pedro Henrique de Almeida Nascimento**

## Estrutura do Projeto

### Arquitetura MVC
Este projeto utiliza a arquitetura **MVC**, garantindo uma separação clara entre:
- **Model**: Responsável pelos dados e lógica de negócios, como a integração com a API SWAPI.
- **View**: Exibe os dados e interage com o usuário através de telas de login, listas e detalhes.
- **Controller**: Atua como intermediário entre o Model e a View, recebendo as entradas do usuário, atualizando o Model e informando a View para que seja atualizada.

### API
A API utilizada neste projeto é a [SWAPI](https://swapi.dev/), que fornece dados completos sobre os principais elementos do universo Star Wars, como personagens, naves e planetas.

## Requisitos Funcionais

1. **Autenticação**: O sistema deve permitir que o usuário faça login utilizando nome completo e senha.
2. **Exibição de Listas**: O aplicativo deve exibir listas de personagens, veículos e planetas, divididos em abas.
3. **Detalhamento**: Ao clicar em um item da lista, o sistema deve exibir uma tela com informações detalhadas.
4. **Favoritos**: O usuário deve poder favoritar e visualizar seus personagens favoritos.
5. **Navegação**: O aplicativo deve oferecer navegação por meio de um **TabLayout**, permitindo fácil alternância entre categorias.

## Protótipo
O protótipo do projeto pode ser visualizado no [Figma](https://www.figma.com/design/j7nAq9VF63f0OtkkcTaiUo/Star-Wars-App?node-id=0-1&t=e0FaLad9ubuRNME3-1), onde estão detalhados os fluxos de navegação e interfaces.

## Como Executar o Projeto

### Pré-requisitos
- Android Studio (versão mais recente)
- SDK Android
- Configuração de uma chave API (caso necessário)

### Passos para Execução
1. Clone este repositório:
   ```bash
   git clone https://github.com/seu_usuario/star-wars-app.git
