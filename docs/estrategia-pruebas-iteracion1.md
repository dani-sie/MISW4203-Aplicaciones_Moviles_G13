# 🧪 Estrategia de Pruebas - Iteración 1

> **Proyecto**: Vinilos Mobile - Grupo 13
> **Iteración**: Sprint 1
> **Fecha**: Octubre 2025

---

## 1. Objetivo

Validar las funcionalidades implementadas en la Iteración 1 mediante pruebas automatizadas End-to-End (E2E), garantizando el cumplimiento de los criterios de aceptación de cada Historia de Usuario.

---

## 2. Alcance

**Historias de Usuario cubiertas**:
- HU01: Consultar catálogo de álbumes
- HU02: Consultar detalle de un álbum
- HU03: Consultar listado de artistas
- HU04: Consultar detalle de un artista
- HU05: Consultar listado de coleccionistas
- HU06: Consultar detalle de un coleccionista

**Fuera de alcance**: Pruebas de rendimiento, seguridad, y HUs de iteraciones futuras.

---

## 3. Tipos de Pruebas

### Pruebas E2E Automatizadas
- **Herramienta**: Espresso + Compose Testing
- **Objetivo**: Validar flujos completos de usuario (navegación, carga de datos, interacciones)

---

## 4. Herramientas

| Herramienta | Versión | Propósito |
|-------------|---------|-----------|
| Espresso | 3.7.0 | Framework de pruebas UI |
| JUnit | 4.13.2 | Framework de pruebas |
| AndroidX Test | 1.3.0 | Librerías de testing |

---

## 5. Inventario de Pruebas

### Matriz de Trazabilidad

| HU | Descripción | Casos de Prueba |
|----|-------------|-----------------|
| HU01 | Consultar catálogo de álbumes | 3 |
| HU02 | Consultar detalle de álbum | 3 |
| HU03 | Consultar listado de artistas | 2 |
| HU04 | Consultar detalle de artista | 3 |
| HU05 | Consultar listado de coleccionistas | 2 |
| HU06 | Consultar detalle de coleccionista | 3 |
| **TOTAL** | | **16** |

### Casos de Prueba por HU

**HU01: Consultar catálogo de álbumes**
- E2E-HU01-01: Navegación a catálogo desde Home
- E2E-HU01-02: Carga de lista de álbumes desde API
- E2E-HU01-03: Expansión de álbum en lista

**HU02: Consultar detalle de álbum**
- E2E-HU02-01: Navegación a detalle de álbum
- E2E-HU02-02: Visualización de tracks del álbum
- E2E-HU02-03: Navegación de regreso a lista

**HU03: Consultar listado de artistas**
- E2E-HU03-01: Navegación a listado de artistas desde Home
- E2E-HU03-02: Carga de lista de artistas desde API

**HU04: Consultar detalle de artista**
- E2E-HU04-01: Navegación a detalle de artista
- E2E-HU04-02: Visualización de álbumes del artista
- E2E-HU04-03: Navegación de regreso a lista

**HU05: Consultar listado de coleccionistas**
- E2E-HU05-01: Navegación a listado de coleccionistas desde Home
- E2E-HU05-02: Carga de lista de coleccionistas desde API

**HU06: Consultar detalle de coleccionista**
- E2E-HU06-01: Navegación a detalle de coleccionista
- E2E-HU06-02: Visualización de álbumes favoritos
- E2E-HU06-03: Navegación de regreso a lista

---

## 6. Ejecución de Pruebas

Con Android Studio abierto en el proyecto, correr en el terminal:

**Comando**:
```bash
cd android-app
./gradlew connectedAndroidTest
```

**Requisitos**: Emulador Android API 21+ y conexión a internet.

---

## 7. Gestión de Defectos

Los defectos encontrados se reportarán como issues en GitHub con etiqueta `bug` e `iteration-1`, incluyendo:
- HU afectada
- Caso de prueba que falló
- Pasos para reproducir
- Resultado esperado vs. actual

---

## 8. Reporte de Resultados

Los resultados se documentarán en la Wiki con:
- Fecha de ejecución
- Total de pruebas ejecutadas/exitosas/fallidas
- Defectos encontrados (enlaces a issues)

---

## 9. Criterios de Éxito

- ✅ 16 casos de prueba E2E implementados con Espresso
- ✅ 100% de pruebas ejecutadas exitosamente
- ✅ Resultados documentados en la Wiki
- ✅ App funciona sin errores en Android API 21+

---

**Equipo Grupo 13 - Octubre 2025**

