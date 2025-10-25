# Diagrama de Paquetes - Vinilos Mobile

## Estructura de Paquetes

```mermaid
graph TB
    subgraph "com.uniandes.vinylhub"
        APP[VinylHubApplication]
        
        subgraph "presentation"
            MAIN[MainActivity]
            
            subgraph "viewmodel"
                VM1[AlbumViewModel]
                VM2[ArtistViewModel]
                VM3[CollectorViewModel]
            end
            
            subgraph "ui.screens"
                UI1[HomeScreen]
                UI2[AlbumListScreen]
                UI3[AlbumDetailScreen]
                UI4[ArtistListScreen]
                UI5[ArtistDetailScreen]
                UI6[CollectorListScreen]
                UI7[CollectorDetailScreen]
            end
            
            subgraph "navigation"
                NAV[AppNavigation]
            end
        end
        
        subgraph "data"
            subgraph "repository"
                REPO1[AlbumRepository]
                REPO2[ArtistRepository]
                REPO3[CollectorRepository]
            end
            
            subgraph "remote"
                SVC1[AlbumService]
                SVC2[ArtistService]
                SVC3[CollectorService]
                API[ApiResponse Models]
            end
            
            subgraph "local"
                DAO1[AlbumDao]
                DAO2[ArtistDao]
                DAO3[CollectorDao]
                DB[VinylHubDatabase]
            end
            
            subgraph "model"
                MODEL1[Album]
                MODEL2[Artist]
                MODEL3[Collector]
                MODEL4[Track, Performer, Comment]
            end
        end
        
        subgraph "di"
            DI1[AppComponent]
            DI2[NetworkModule]
            DI3[DatabaseModule]
            DI4[ViewModelModule]
            DI5[CoilModule]
        end
    end
    
    %% Dependencias entre capas
    UI1 --> VM1
    UI2 --> VM1
    UI3 --> VM1
    UI4 --> VM2
    UI5 --> VM2
    UI6 --> VM3
    UI7 --> VM3
    
    VM1 --> REPO1
    VM2 --> REPO2
    VM3 --> REPO3
    
    REPO1 --> SVC1
    REPO1 --> DAO1
    REPO1 --> MODEL1
    
    REPO2 --> SVC2
    REPO2 --> DAO2
    REPO2 --> MODEL2
    
    REPO3 --> SVC3
    REPO3 --> DAO3
    REPO3 --> MODEL3
    
    DAO1 --> DB
    DAO2 --> DB
    DAO3 --> DB
    
    SVC1 --> API
    SVC2 --> API
    SVC3 --> API
    
    DI1 --> DI2
    DI1 --> DI3
    DI1 --> DI4
    DI1 --> DI5
    
    APP --> DI1
    MAIN --> NAV

    style presentation fill:#e1f5ff
    style data fill:#fff3e0
    style di fill:#f3e5f5
```

## Descripción de Paquetes

### **presentation** (Capa de Presentación)
- **viewmodel/**: ViewModels que gestionan el estado de la UI
- **ui.screens/**: Composables de Jetpack Compose para cada pantalla
- **navigation/**: Configuración de navegación con Compose Navigation
- **MainActivity**: Actividad principal que contiene el NavHost

### **data** (Capa de Datos)
- **repository/**: Implementación del patrón Repository
- **remote/**: Servicios Retrofit para consumo de API REST
- **local/**: DAOs de Room para persistencia local
- **model/**: Modelos de dominio (Entities de Room)

### **di** (Inyección de Dependencias)
- **AppComponent**: Componente principal de Dagger 2
- **NetworkModule**: Provee Retrofit, OkHttp, Gson
- **DatabaseModule**: Provee Room Database y DAOs
- **ViewModelModule**: Provee ViewModels
- **CoilModule**: Provee ImageLoader para Coil

### **VinylHubApplication**
- Clase Application que inicializa Dagger 2

## Flujo de Datos

```
UI (Screens) 
    ↓ observa Flow
ViewModel 
    ↓ solicita datos
Repository 
    ↓ consulta API / Cache
Service (Retrofit) + DAO (Room)
    ↓ retorna datos
Repository 
    ↓ emite Flow
ViewModel 
    ↓ actualiza UI
UI (Screens)
```

