# 💡 TáLigado - Aplicativo de Monitoramento de Energia e Emissões de Carbono

## 🚀 Descrição do Projeto

O "TáLigado" é um aplicativo móvel desenvolvido para o gerenciamento do consumo de energia e a redução das emissões de carbono. O objetivo principal do aplicativo é fornecer aos usuários uma plataforma onde possam monitorar o consumo de energia de seus dispositivos, acompanhar suas emissões de carbono e receber recomendações para otimizar seu consumo energético.

## Funcionalidades

O aplicativo possui as seguintes funcionalidades:

1. **Tela de Login**: Permite aos usuários autenticar-se com e-mail e senha. Também oferece links para recuperação de senha e cadastro.
2. **Tela de Dashboard**: Exibe informações sobre o consumo de energia, as emissões de carbono, alertas, recomendações e dispositivos vinculados. O usuário pode gerar relatórios de consumo e emissões.
3. **Tela de Detalhes do Perfil**: Exibe as informações do usuário com opções para editar, excluir conta e realizar logout.
4. **Tela de Dispositivos Vinculados**: Mostra a lista de dispositivos conectados, seu consumo de energia e emissões de carbono. Permite adicionar novos dispositivos.
5. **Tela de Detalhes do Dispositivo**: Exibe um histórico detalhado de consumo e emissões de energia de dispositivos individuais e permite configurar esses dispositivos.
6. **Tela de Notificações de Alerta**: Apresenta alertas relacionados ao consumo excessivo de energia e às emissões de carbono, com sugestões de ações corretivas.
7. **Tela de Gerar Relatório**: Permite aos usuários gerar relatórios personalizados sobre o consumo e as emissões, em formato PDF ou Excel.
8. **Tela de Recomendações**: Oferece sugestões para ajudar os usuários a reduzir o consumo de energia e minimizar suas emissões de carbono.

## Estrutura do Projeto

## 📂 Estrutura de Pastas
```tree
  app
├── manifests
│   └── AndroidManifest.xml
├── kotlin+java
│   └── com.example.taligado
│       ├── activity
│       │   └── [Atividades/Telas do Aplicativo]
│       ├── adapter
│       │   └── [Adaptadores para RecyclerView]
│       ├── model
│       │   └── [Modelos de Dados]
│       ├── fragments
│       │   └── [Classes de Fragmentos]
│       ├── viewModel
│           └── [ViewModels para lógica de negócio]
└── res
    ├── drawable
    ├── layout
    |     └── [Layout (estilização) das Páginas]
    ├── menu
    ├── mipmap
    ├── values
    └── xml
```


### Diretórios e Arquivos

- **`com.example.taligado`**: Pacote principal contendo as telas e funcionalidades do aplicativo.
  - **`fragments`**: Contém os fragments para as diversas telas do aplicativo (ex: Dashboard, Dispositivos, Perfil).
  - **`model`**: Contém as classes de modelo (ex: `Usuario`, `Dispositivo`, `Filial`).
  - **`viewModel`**: Contém as classes de ViewModel responsáveis pela lógica de negócios e interações com o backend.
  - **`activity`**: Contém as atividades que são responsáveis pela navegação entre os diferentes fragments.

### Tecnologias Usadas

- **Kotlin**: Linguagem de programação principal.
- **Firebase**: Para o gerenciamento de usuários e dados em tempo real.
- **Google Maps API**: Para exibição de mapas e localização.
- **OkHttp**: Biblioteca para realizar requisições HTTP ao backend.
- **Gson**: Biblioteca para serialização e desserialização de objetos JSON.

### Requisitos

- Android 5.0 (Lollipop) ou superior.
- Conexão com a internet para autenticação de usuários e carregamento de dados.

## Requisições e CRUD

### 1. **Operações CRUD para Usuário**
As operações CRUD para o usuário são realizadas utilizando o Firebase Authentication.

- **Create**: Cadastro de um novo usuário com e-mail e senha.
    - Requisição: `POST /auth/signup`
    - Parâmetros: `email`, `senha`
    - Resposta: Status da criação, com informações do usuário.

- **Read**: Login de usuário já cadastrado.
    - Requisição: `POST /auth/login`
    - Parâmetros: `email`, `senha`
    - Resposta: Token de autenticação, detalhes do usuário.

- **Update**: Edição das informações do usuário (por exemplo, nome, e-mail).
    - Requisição: `PUT /user/update`
    - Parâmetros: `nome`, `email`, `senha`
    - Resposta: Status da atualização.

- **Delete**: Exclusão da conta do usuário.
    - Requisição: `DELETE /user/delete`
    - Parâmetros: `userId`
    - Resposta: Status da exclusão.

### 2. **Operações CRUD para Dispositivos**
As operações CRUD para os dispositivos conectados ao aplicativo são realizadas via Firebase Database e APIs do backend.

- **Create**: Adicionar novo dispositivo para monitoramento.
    - Requisição: `POST /device/add`
    - Parâmetros: `nome`, `tipo`, `consumo`, `emissao`
    - Resposta: Detalhes do dispositivo adicionado.

- **Read**: Obter informações sobre todos os dispositivos conectados.
    - Requisição: `GET /device/list`
    - Parâmetros: `userId`
    - Resposta: Lista de dispositivos com informações de consumo e emissão.

- **Update**: Atualizar as configurações de um dispositivo (ex: nome, tipo, etc).
    - Requisição: `PUT /device/update`
    - Parâmetros: `deviceId`, `nome`, `tipo`, `consumo`, `emissao`
    - Resposta: Status da atualização.

- **Delete**: Remover um dispositivo da lista.
    - Requisição: `DELETE /device/delete`
    - Parâmetros: `deviceId`
    - Resposta: Status da exclusão.

### 3. **Operações CRUD para Filiais**
O aplicativo também gerencia informações sobre as filiais associadas ao usuário, incluindo dados sobre o consumo e as emissões.

- **Create**: Adicionar uma nova filial.
    - Requisição: `POST /branch/add`
    - Parâmetros: `nome`, `endereco`, `consumoTotal`, `emissaoTotal`
    - Resposta: Detalhes da filial adicionada.

- **Read**: Obter informações sobre todas as filiais de um usuário.
    - Requisição: `GET /branch/list`
    - Parâmetros: `userId`
    - Resposta: Lista de filiais com detalhes de consumo e emissões.

- **Update**: Atualizar informações de uma filial existente (ex: nome, endereço, consumo, etc).
    - Requisição: `PUT /branch/update`
    - Parâmetros: `branchId`, `nome`, `endereco`, `consumoTotal`, `emissaoTotal`
    - Resposta: Status da atualização.

- **Delete**: Remover uma filial da lista.
    - Requisição: `DELETE /branch/delete`
    - Parâmetros: `branchId`
    - Resposta: Status da exclusão.


## Como Rodar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/biancaroman/TaLigado-MobileKotlin.git

2. Abra o projeto no Android Studio.

3. Conecte seu dispositivo ou inicie um emulador.

4. Execute o projeto:

  - Clique em Run > Run 'app' ou utilize o atalho Shift + F10.

## Link para o prototipo feito no figma
<https://www.figma.com/design/Fe7uJ657IAOeluGL6P4v0N/Untitled?node-id=0-1&t=BQliNA6f5jo3Upfl-1>

## Link para o video testando o app
<https://youtu.be/lsjXs8t7oc0)>

## 🫂 Integrantes

Aqui estão os membros do grupo que participaram durante desenvolvimento desta SPRINT.

* **RM 552267 - Bianca Leticia Román Caldeira**
  - Turma: 2TDSPH
    
* **RM 552252 – Charlene Aparecida Estevam Mendes Fialho**
  - Turma: 2TDSPH

* **RM 552258 - Laís Alves da Silva Cruz**
  - Turma: 2TDSPH

* **RM 97916 – Fabricio Torres Antonio**
  - Turma: 2TDSPH

* **RM 99675 – Lucca Raphael Pereira dos Santos**
  - Turma: 2TDSPZ

<table>
  <tr>
        <td align="center">
      <a href="https://github.com/biancaroman">
        <img src="https://avatars.githubusercontent.com/u/128830935?v=4" width="100px;" border-radius='50%' alt="Bianca Román's photo on GitHub"/><br>
        <sub>
          <b>Bianca Román</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/charlenefialho">
        <img src="https://avatars.githubusercontent.com/u/94643076?v=4" width="100px;" border-radius='50%' alt="Charlene Aparecida's photo on GitHub"/><br>
        <sub>
          <b>Charlene Aparecida</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/laiscrz">
        <img src="https://avatars.githubusercontent.com/u/133046134?v=4" width="100px;" alt="Lais Alves's photo on GitHub"/><br>
        <sub>
          <b>Lais Alves</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/LuccaRaphael">
        <img src="https://avatars.githubusercontent.com/u/127765063?v=4" width="100px;" border-radius='50%' alt="Lucca Raphael's photo on GitHub"/><br>
        <sub>
          <b>Lucca Raphael</b>
        </sub>
      </a>
    </td>
     <td align="center">
      <a href="https://github.com/Fabs0602">
        <img src="https://avatars.githubusercontent.com/u/111320639?v=4" width="100px;" border-radius='50%' alt="Fabricio Torres's photo on GitHub"/><br>
        <sub>
          <b>Fabricio Torres</b>
        </sub>
      </a>
    </td>
  </tr>
</table>


