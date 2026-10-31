# Diario de Fotos

## Informacoes do aluno

- **Nome:** Marcio Evandro Pereira da Silva
- **Data de entrega:** 30/10/2026

## Tema do projeto

O tema escolhido foi um diario de fotos. A escolha foi motivada pela utilidade de aplicativos que registram atividades do dia a dia, permitindo guardar momentos com uma imagem, descricao, data e localizacao.

O aplicativo foi criado para facilitar esse registro, mantendo tudo organizado em uma lista simples e permitindo consultar ou excluir cada entrada posteriormente.

## Descricao do aplicativo

O Diario de Fotos possui telas para lista, camera, nova entrada e detalhes.

1. Na tela inicial, o usuario visualiza suas entradas em cards.
2. Ao selecionar **Nova foto**, a CameraX abre para capturar uma imagem.
3. Depois da captura, o usuario pode escrever uma descricao e obter sua coordenada GPS.
4. Ao salvar, a foto e seus dados ficam registrados localmente.
5. Na tela de detalhes, a foto pode ser visualizada, a coordenada pode ser aberta em mapas ou a entrada pode ser excluida.

## Tecnologias utilizadas

- Kotlin
- Jetpack Compose e Material 3
- Navigation Compose
- Android ViewModel, StateFlow, `remember` e `mutableStateOf`
- CameraX
- Localizacao GPS com Play Services
- Coil para exibicao das imagens

## Arquitetura

O projeto possui separacao inspirada em MVVM:

```text
UI Compose -> DiaryViewModel -> EntryRepository -> Armazenamento local
```

## Persistencia

Atualmente, os dados das entradas sao armazenados em um arquivo JSON em `filesDir`, enquanto as imagens ficam em uma pasta local separada.

Para atender integralmente ao requisito de persistencia com Room, seria necessario adicionar Entity, DAO, RoomDatabase e utilizar o Room no repositorio.

## Navegacao

```text
Lista -> Camera -> Nova entrada -> Lista
Lista -> Detalhes -> Lista
```

## Permissoes

O aplicativo utiliza:

- Camera
- Localizacao aproximada
- Localizacao precisa

## Prototipo

As imagens abaixo podem ser substituidas pelas capturas do prototipo desenvolvido em Figma ou pelo wireframe utilizado no projeto.

```text
imagens/
├── lista.png
├── camera.png
├── nova-entrada.png
└── detalhes.png
```

Para anexar as telas, crie a pasta `imagens` na raiz do projeto, adicione as imagens e referencie-as desta forma:

```markdown
![Tela de lista](imagens/lista.png)
![Tela da camera](imagens/camera.png)
![Tela de nova entrada](imagens/nova-entrada.png)
![Tela de detalhes](imagens/detalhes.png)
```

## Como executar

1. Abra o projeto no Android Studio.
2. Aguarde a sincronizacao do Gradle.
3. Conecte um dispositivo fisico ou execute um emulador.
4. Selecione o modulo `app` e clique em **Run**.

Windows:

```powershell
.\gradlew.bat assembleDebug
```

É recomendado usar um dispositivo fisico para testar a camera e o GPS.

## Observacoes

O projeto foi desenvolvido seguindo o fluxo de cadastro de momentos, com foco em uma interface simples, funcional e organizada.
