# Diario de Fotos

Aplicativo Android para registrar momentos por meio de fotos, descricoes, data/hora e coordenadas de GPS. A interface foi desenvolvida com Kotlin, Jetpack Compose e Material 3.

## Funcionalidades

- Visualiza todas as entradas em uma lista de cards.
- Captura fotos com CameraX.
- Armazena as imagens localmente.
- Permite adicionar uma descricao a cada foto.
- Obtém latitude e longitude com GPS.
- Exibe os detalhes de uma entrada.
- Abre as coordenadas em um aplicativo de mapas.
- Permite excluir a entrada e remover sua foto.
- Permite descartar uma foto ainda nao salva.

## Tecnologias e bibliotecas

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Android ViewModel
- StateFlow
- CameraX
- Fused Location Provider
- Coil
- Kotlin coroutines
- Persistencia local baseada em JSON

> Estado da persistencia: o projeto atualmente usa um arquivo `entries.json` em `filesDir` e nao Room. Para atender integralmente ao requisito do projeto final de persistencia com Room, sera necessario adicionar Entity, DAO, RoomDatabase e migrations/repository com Room.

## Arquitetura

O projeto utiliza uma separacao inspirada em MVVM:

```text
UI/Compose -> DiaryViewModel -> EntryRepository -> armazenamento local
```

- `ui/`: telas Compose, navegacao e ViewModel.
- `data/`: modelo de dados e repositorio local.
- `MainActivity.kt`: inicializacao do Compose e configuracao das rotas.

### Telas

| Tela | Responsabilidade |
|---|---|
| Lista | Exibe as entradas cadastradas e o botao para nova foto |
| Camera | Solicita permissao, mostra a CameraX e captura a foto |
| Nova entrada | Exibe a foto, descricao e coordenadas GPS |
| Detalhes | Exibe a entrada completa, mapa e opcao de excluir |

### Fluxo de navegação

```text
Lista -> Câmera -> Nova entrada -> Lista
Lista -> Detalhes -> Lista
```

## Requisitos do projeto

| Requisito | Status |
|---|---|
| Aplicativo em Kotlin | Atendido |
| Interface com Jetpack Compose | Atendido |
| Pelo menos duas telas | Atendido |
| Navegacao Compose | Atendido |
| Arquitetura MVVM | Atendido |
| Persistencia com Room | Pendente |

## Permissoes

O aplicativo solicita no uso:

- `CAMERA`
- `ACCESS_FINE_LOCATION`
- `ACCESS_COARSE_LOCATION`

É necessário conceder as permissoes para capturar fotos e obter coordenadas GPS.

## Requisitos do ambiente

- Android Studio
- JDK 17
- Android SDK 34
- Dispositivo ou emulador Android com API 24 ou superior
- Dispositivo fisico recomendado para testar câmera e GPS

## Como executar

1. Abra a pasta do projeto no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Conecte um dispositivo ou inicie um emulador.
4. Selecione o modulo `app`.
5. Execute o aplicativo com **Run** ou pressione `Shift + F10`.

No Windows, também é possível compilar pelo terminal:

```powershell
.\gradlew.bat assembleDebug
```

O APK será gerado em:

```text
app\build\outputs\apk\debug\app-debug.apk
```

## Estrutura principal

```text
app/src/main/java/com/example/diariofotos/
├── MainActivity.kt
├── data/
│   ├── DiaryEntry.kt
│   └── EntryRepository.kt
└── ui/
    ├── CameraScreen.kt
    ├── DetailScreen.kt
    ├── DiaryViewModel.kt
    ├── ListScreen.kt
    └── NewEntryScreen.kt
```
