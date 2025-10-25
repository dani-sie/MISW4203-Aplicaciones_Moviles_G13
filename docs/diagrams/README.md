# 🏗️ Diagramas Arquitectónicos - Vinilos Mobile

> **Proyecto**: Vinilos Mobile - Grupo 13  
> **Arquitectura**: MVVM + Repository Pattern  
> **Lenguaje**: Mermaid

---

## 📋 Índice de Diagramas

### 1. [Diagrama de Clases](./class-diagram.md)
Muestra las clases principales del sistema y sus relaciones:
- **ViewModels**: AlbumViewModel, ArtistViewModel, CollectorViewModel
- **Repositories**: AlbumRepository, ArtistRepository, CollectorRepository
- **Services**: AlbumService, ArtistService, CollectorService (Retrofit)
- **DAOs**: AlbumDao, ArtistDao, CollectorDao (Room)
- **Models**: Album, Artist, Collector y sus relaciones

**Patrones aplicados**: MVVM, Repository, Service Adapter

---

### 2. [Diagrama de Paquetes](./package-diagram.md)
Muestra la estructura de paquetes y módulos:
- **presentation/**: ViewModels, UI Screens, Navigation
- **data/**: Repositories, Services, DAOs, Models
- **di/**: Módulos de Dagger 2 (Network, Database, ViewModel, Coil)

**Organización**: Separación por capas (Presentation, Data, DI)

---

### 3. [Diagrama de Componentes](./component-diagram.md)
Muestra los componentes tecnológicos y su interacción:
- **Presentation Layer**: Jetpack Compose, Navigation, ViewModels
- **Domain Layer**: Repositories
- **Data Layer**: Retrofit, Room, In-Memory Cache
- **Frameworks**: Dagger 2, Coil, Coroutines, Flow

**Tecnologías**: Retrofit, Room, Dagger 2, Compose, Coil

---

## 🎯 Arquitectura General

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │ Compose UI   │  │ Navigation   │  │ ViewModels   │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                         │
│  ┌──────────────────────────────────────────────────┐   │
│  │              Repositories                        │   │
│  └──────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────┐
│                      DATA LAYER                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │ Retrofit     │  │ Room DB      │  │ Memory Cache │  │
│  │ (API REST)   │  │ (SQLite)     │  │ (Map)        │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────┐
│                   EXTERNAL SERVICES                      │
│  ┌──────────────┐                    ┌──────────────┐   │
│  │ Backend API  │                    │  Image CDN   │   │
│  └──────────────┘                    └──────────────┘   │
└─────────────────────────────────────────────────────────┘
```

---

## 🔄 Flujo de Datos

### Lectura de Datos
```
UI Screen → ViewModel → Repository → [API + Room] → Repository → ViewModel → UI Screen
```

### Escritura de Datos
```
UI Screen → ViewModel → Repository → API → Room → ViewModel → UI Screen
```

---

## 🛠️ Stack Tecnológico

| Capa | Tecnología | Propósito |
|------|------------|-----------|
| **UI** | Jetpack Compose | Framework declarativo de UI |
| **Navegación** | Compose Navigation | Navegación entre pantallas |
| **Arquitectura** | MVVM | Separación de responsabilidades |
| **DI** | Dagger 2 | Inyección de dependencias |
| **Networking** | Retrofit + OkHttp | Cliente HTTP REST |
| **Serialización** | Gson | JSON ↔ Kotlin Objects |
| **Database** | Room | ORM para SQLite |
| **Async** | Kotlin Coroutines | Programación asíncrona |
| **Reactive** | Kotlin Flow | Streams de datos reactivos |
| **Images** | Coil | Carga y caché de imágenes |

---

## 📊 Métricas del Proyecto

- **Paquetes**: 8 (presentation, data, di, ui, etc.)
- **ViewModels**: 3 (Album, Artist, Collector)
- **Repositories**: 3 (Album, Artist, Collector)
- **Services**: 3 (Album, Artist, Collector)
- **DAOs**: 3 (Album, Artist, Collector)
- **Modelos**: 3 principales + 9 auxiliares
- **Pantallas**: 7 (Home, 3 listas, 3 detalles)

---

## 📝 Notas

- Los diagramas están en formato **Mermaid** para compatibilidad con GitHub
- Se pueden visualizar directamente en GitHub, VS Code, o cualquier visor Mermaid
- Para editar, usar [Mermaid Live Editor](https://mermaid.live/)

---

**Última actualización**: 25 de Octubre de 2025  
**Equipo**: Grupo 13 - MISW4203 Aplicaciones Móviles

