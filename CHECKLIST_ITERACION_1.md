# ✅ CHECKLIST - Iteración 1: Cumplimiento de Criterios de Evaluación

> **Objetivo**: Alcanzar 100/100 puntos en la evaluación de la Iteración 1  
> **Última actualización**: 2025-10-25

---

## 📊 RESUMEN EJECUTIVO

| Categoría | Puntos Totales | Completados | Faltantes | % Progreso |
|-----------|----------------|-------------|-----------|------------|
| **Prácticas Ágiles** | 15 | 11 | 4 | 73% |
| **Documentación** | 5 | 5 | 0 | 100% |
| **Diseño y Construcción** | 40 | 40 | 0 | 100% |
| **Calidad** | 40 | 40 | 0 | 100% |
| **TOTAL** | **100** | **96** | **4** | **96%** |

---

## 🎯 PRÁCTICAS ÁGILES (15 puntos)

### ✅ Acceso al repositorio (2 puntos)
- [x] El equipo Uniandes puede acceder al repositorio
- [x] Repositorio público o con permisos adecuados

### ✅ APK disponible en el repositorio (2 puntos)
- [x] APK copiado a la carpeta `/releases`
- [x] APK renombrado a `vinilos-v1.0.0.apk`
- [x] .gitignore modificado para incluir APKs de releases/
- [x] README principal actualizado con link al APK
- [x] APK incluido en el commit
- **Completado**: APK en `releases/vinilos-v1.0.0.apk` (12 MB)

### ⏳ Release etiquetado (2 puntos)
- [ ] Tag de release creado (ej: `v1.0.0`)
- [ ] Tag pusheado al repositorio remoto
- [ ] Release publicado en GitHub con notas
- **Acción**: `git tag -a v1.0.0 -m "Release v1.0.0 - Iteración 1"`
- **Acción**: `git push origin v1.0.0`

### ⚠️ GitFlow implementado (2 puntos)
- [x] Rama `main` existe
- [x] Rama `develop` existe
- [ ] Evidencia de Pull Requests con "squash and merge"
- [ ] Código del release en rama `main`
- **Acción**: Verificar PRs en GitHub y documentar

### ✅ Evidencia de reuniones semana 3 y 4 (4 puntos)
- [x] Acta de reunión semana 2 (19/oct/2025)
- [x] Acta de reunión semana 3 (20/oct/2025)
- [x] Acta de reunión semana 4 (25/oct/2025)
- **Completado**: `Acta_Reunion_Semana_3.md` y `Acta_Reunion_Semana_4.md`

### ❌ Retrospectiva del Inception (3 puntos)
- [ ] Documento de retrospectiva creado
- [ ] Incluye: qué salió bien, qué mejorar, acciones de mejora
- [ ] Publicado en Wiki o `/docs/`
- **Acción**: Crear `docs/retrospectiva-inception.md`

---

## 📚 DOCUMENTACIÓN (5 puntos)

### ✅ Diseño arquitectónico en la Wiki (2 puntos)
- [x] Diagrama de clases (Album, Artist, Collector, ViewModels, Repositories, Services)
- [x] Diagrama de paquetes (estructura de carpetas: data, presentation, di, ui)
- [x] Diagrama de componentes (capas MVVM, Room, Retrofit, Dagger)
- [x] Diagramas creados en formato Mermaid
- [x] Diagramas disponibles en `docs/diagrams/`
- **Completado**: 3 diagramas arquitectónicos creados

### ✅ Estrategia de pruebas en la Wiki (3 puntos)
- [x] Documento de estrategia de pruebas creado
- [x] Incluye tipos de pruebas (E2E, API-based)
- [x] Incluye herramientas (Espresso, JUnit)
- [x] Incluye inventario de pruebas
- [x] Incluye casos de prueba por HU
- [x] Publicado en la Wiki
- **Completado**: https://github.com/dani-sie/MISW4203-Aplicaciones_Moviles_G13/wiki/Estrategia-de-pruebas

---

## 💻 DISEÑO Y CONSTRUCCIÓN (40 puntos)

### ✅ Diagramas arquitectónicos para HUs (5 puntos)
- [x] Diagrama de clases completo (Mermaid)
- [x] Diagrama de paquetes completo (Mermaid)
- [x] Diagrama de componentes completo (Mermaid)
- [x] Diagramas reflejan las 6 HUs implementadas
- **Completado**: Diagramas en `docs/diagrams/`

### ✅ Diseño refleja implementación (5 puntos)
- [x] Arquitectura MVVM implementada
- [x] Patrones aplicados correctamente
- [x] Código coherente con diseño

### ✅ Código coherente con diseño arquitectónico (5 puntos)
- [x] Separación de capas (data, domain, presentation)
- [x] ViewModels, Repositories, Services bien estructurados

### ✅ Patrones MVVM, Repository y Service Adapter (10 puntos)
- [x] **MVVM**: AlbumViewModel, ArtistViewModel, CollectorViewModel
- [x] **Repository**: AlbumRepository, ArtistRepository, CollectorRepository
- [x] **Service Adapter**: AlbumService, ArtistService, CollectorService (Retrofit)
- [x] Inyección de dependencias con Dagger 2

### ✅ Consumo de API REST (10 puntos)
- [x] Retrofit configurado correctamente
- [x] Endpoints implementados para álbumes, artistas, coleccionistas
- [x] Deserialización JSON funcionando
- [x] Manejo de errores implementado

### ✅ README con instrucciones de construcción (5 puntos)
- [x] README existe
- [x] Incluye requisitos previos (Android Studio, JDK, SDK)
- [x] Incluye pasos para clonar el repositorio
- [x] Incluye pasos para compilar la app
- [x] Incluye pasos para ejecutar la app
- [x] Incluye pasos para ejecutar las pruebas
- **Completado**: Sección "Construcción Local" agregada

---

## 🧪 CALIDAD (40 puntos)

### ✅ Estrategia de pruebas clara (5 puntos)
- [x] Estrategia documentada en Wiki
- [x] Objetivos de pruebas definidos
- [x] Alcance de pruebas definido
- **Completado**: `docs/estrategia-pruebas-iteracion1.md`

### ✅ Estrategia con componentes esperados (5 puntos)
- [x] Inventario de pruebas (16 casos de prueba)
- [x] Casos de prueba / Scripts (detallados por HU)
- [x] Reportes de defectos (template definido)
- [x] Matriz de trazabilidad HU-Pruebas
- **Completado**: Ver sección 4 del documento de estrategia

### ✅ Estrategia incluye pruebas E2E (5 puntos)
- [x] Escenarios E2E definidos (16 casos)
- [x] Pruebas E2E documentadas
- [x] Cobertura de flujos principales
- **Completado**: Ver sección 4.2 del documento de estrategia

### ✅ HUs implementadas según planeación (5 puntos)
- [x] HU01: Consultar catálogo de álbumes
- [x] HU02: Consultar detalle de álbum
- [x] HU03: Consultar listado de artistas
- [x] HU04: Consultar detalle de artista
- [x] HU05: Consultar listado de coleccionistas
- [x] HU06: Consultar detalle de coleccionista

### ✅ App sin errores en Android L+ (5 puntos)
- [x] minSdk = 21 (Android Lollipop)
- [x] Pruebas en emulador Android API 36 (superior a L)
- [x] Sin crashes reportados (17/17 pruebas exitosas)
- **Completado**: App funciona correctamente

### ✅ Casos de prueba automatizados por HU (5 puntos)
- [x] HU01: Pruebas automatizadas (AlbumListScreenTest.kt - 3 tests)
- [x] HU02: Pruebas automatizadas (AlbumDetailScreenTest.kt - 3 tests)
- [x] HU03: Pruebas automatizadas (ArtistListScreenTest.kt - 2 tests)
- [x] HU04: Pruebas automatizadas (ArtistDetailScreenTest.kt - 3 tests)
- [x] HU05: Pruebas automatizadas (CollectorListScreenTest.kt - 2 tests)
- [x] HU06: Pruebas automatizadas (CollectorDetailScreenTest.kt - 3 tests)
- **Completado**: 6 archivos con 16 pruebas E2E

### ✅ Escenarios E2E con Espresso ejecutados (5 puntos)
- [x] Pruebas E2E implementadas con Espresso
- [x] Pruebas ejecutadas exitosamente (17/17 pasaron)
- [x] Reportes de ejecución generados
- **Completado**: 100% de pruebas exitosas

### ✅ Resultados de pruebas reportados (5 puntos)
- [x] Resultados documentados (reporte HTML generado)
- [x] 17/17 pruebas ejecutadas exitosamente (100%)
- [x] Reporte disponible en `android-app/app/build/reports/androidTests/connected/debug/index.html`
- [ ] Resultados copiados a Wiki (pendiente)
- **Completado**: Pruebas ejecutadas con éxito

---

## 🎯 PLAN DE ACCIÓN PRIORIZADO

### ✅ COMPLETADO (51 puntos)
1. **Crear y ejecutar pruebas E2E con Espresso** → 15 puntos ✅
   - [x] Implementar pruebas para HU01-HU06 (16 pruebas)
   - [x] Ejecutar pruebas en emulador (17/17 exitosas)
   - [x] Documentar resultados (reporte HTML generado)

2. **Documentar estrategia de pruebas** → 18 puntos ✅
   - [x] Crear documento (`docs/estrategia-pruebas-iteracion1.md`)
   - [x] Incluir inventario y casos de prueba
   - [x] Publicar en Wiki (completado)

3. **App sin errores** → 5 puntos ✅
   - [x] Pruebas ejecutadas sin crashes
   - [x] Funciona en Android L+

4. **Actualizar README** → 5 puntos ✅
   - [x] Sección "Construcción Local" agregada
   - [x] Instrucciones de compilación y ejecución
   - [x] Instrucciones para ejecutar pruebas

5. **Crear diagramas arquitectónicos** → 7 puntos ✅
   - [x] Diagrama de clases (Mermaid)
   - [x] Diagrama de paquetes (Mermaid)
   - [x] Diagrama de componentes (Mermaid)
   - [x] README con índice de diagramas

6. **Gestión de APK** → 2 puntos ✅
   - [x] Copiar APK al repositorio
   - [x] Modificar .gitignore
   - [x] Actualizar README con link al APK

7. **Actas de reunión semana 3 y 4** → 4 puntos ✅
   - [x] Crear acta semana 3
   - [x] Crear acta semana 4

### 🟡 PRIORIDAD BAJA (4 puntos)
8. **Crear tag de release** → 2 puntos
   - [ ] Crear tag `v1.0.0`
   - [ ] Push del tag
   - [ ] Publicar release en GitHub

9. **Retrospectiva Inception** → 3 puntos
   - [ ] Crear documento de retrospectiva

---

## 📝 NOTAS Y OBSERVACIONES

### Fortalezas del proyecto:
- ✅ Arquitectura MVVM muy bien implementada
- ✅ Patrones Repository y Service Adapter correctos
- ✅ Integración con API funcionando
- ✅ 6 HUs implementadas (más de lo requerido para Sprint 1)
- ✅ Navegación completa entre pantallas
- ✅ UI/UX bien diseñada
- ✅ **16 pruebas E2E implementadas y ejecutadas (100% exitosas)**
- ✅ **Estrategia de pruebas documentada y en Wiki**
- ✅ **App sin crashes en Android L+**
- ✅ **Diagramas arquitectónicos completos (Mermaid)**
- ✅ **README completo con instrucciones**
- ✅ **APK disponible en repositorio**

### Áreas de mejora:
- ⏳ Falta crear tag de release (2 puntos)
- ⏳ Falta documentación de proceso (actas y retrospectiva) (7 puntos)

### Próximos pasos inmediatos:
1. ✅ ~~Crear pruebas E2E~~ **COMPLETADO**
2. ✅ ~~Documentar estrategia de pruebas~~ **COMPLETADO**
3. ✅ ~~Crear diagramas UML~~ **COMPLETADO**
4. ✅ ~~Completar README~~ **COMPLETADO**
5. ✅ ~~Subir APK~~ **COMPLETADO**
6. ⏳ Crear tag de release (2 puntos)
7. ⏳ Documentación de proceso (7 puntos)

---

**Última actualización**: 2025-10-25  
**Responsable**: Equipo Grupo 13  
**Sprint**: Iteración 1

