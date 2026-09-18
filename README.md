# Bowling TDD — Taller 02 DOSW

## 1. Identificación

**Nombre completo**: Juan Manuel Garzon Viracacha

**Código estudiantil**: 1000103731

**Correo institucional** juan.gviracacha@mail.escuelaing.edu.co

---

## 2. Descripción

**BowlTech S.A.S.** desea digitalizar el sistema de puntuación de sus pistas de bolos. Este proyecto implementa el motor de puntuación de un juego de bowling para un jugador, aplicando **Test-Driven Development (TDD)** desde cero.

### Reglas del dominio implementadas

| Situación | Condición | Puntuación |
|---|---|---|
| Tiro normal | Derriba algunos pinos sin completar 10 | Solo los pinos derribados |
| Spare `/` | Derriba los 10 pinos en 2 intentos del mismo frame | 10 + primer tiro del siguiente frame |
| Strike `X` | Derriba los 10 pinos en el primer intento | 10 + suma de los dos tiros siguientes |
| Frame 10 | Si hay strike o spare en el frame 10 | Hasta 3 tiros |
| Juego perfecto | 12 strikes consecutivos | 300 puntos (máximo) |

### Responsabilidades de cada clase

| Clase | Responsabilidad |
|---|---|
| `BowlingGame` | Motor principal. Registra tiros con `roll()`, valida entradas, gestiona el avance de frames, expone `score()` e `isComplete()`. |
| `Frame` | Representa un frame individual. Almacena sus tiros, sabe si es strike/spare y cuándo está completo. |
| `FrameType` | Enum con los tipos de frame: `NORMAL`, `SPARE`, `STRIKE`, `TENTH`. |
| `BowlingScorer` | Calcula el puntaje total aplicando los bonos de spare y strike. Sin estado. |

### Estructura del proyecto

```
bowling-tdd/
├── src/
│   ├── main/java/edu/eci/dosw/bowling/
│   │   ├── BowlingGame.java
│   │   ├── Frame.java
│   │   ├── FrameType.java
│   │   └── BowlingScorer.java
│   └── test/java/edu/eci/dosw/bowling/
│       ├── BowlingGameTest.java
│       └── BowlingScorerTest.java
├── docs/evidence/
│   ├── jacoco-antes.png
│   ├── jacoco-final.png
│   └── sonarqube-dashboard.png
├── pom.xml
└── README.md
```

---

## 3. Evidencia TDD

El proyecto se construyó aplicando el ciclo **RED → GREEN → REFACTOR** de forma iterativa. A continuación se documenta el flujo con ejemplos reales.

### Ejemplo de ciclo — Caso A2 (`roll(-1)` lanza excepción)

#### 🔴 RED — Escribir la prueba primero

![Consola en rojo: prueba fallando](/bowling-tdd/docs/evidence/tdd-red.png)

```java
@Test
@DisplayName("A2: roll(-1) lanza IllegalArgumentException")
void rollNegativePins_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> game.roll(-1));
}
```

**Commit:** `test: RED - roll lanza excepción negativo`

#### 🟢 GREEN — Código mínimo para pasar

![Consola en verde: prueba pasando](/bowling-tdd/docs/evidence/tdd-green.png)

```java
public void roll(int pins) {
    if (pins < 0) {
        throw new IllegalArgumentException("Pines no puede ser negativo");
    }
}
```

**Commit:** `feat: GREEN - valida pines negativos`

#### 🔵 REFACTOR — Mejorar sin romper

```java
private void validatePins(int pins) {
    if (pins < MIN_PINS || pins > MAX_PINS) {
        throw new IllegalArgumentException("Pines fuera de rango: " + pins);
    }
}
```

**Commit:** `refactor: extrae validatePins y usa constantes`


### Resumen de ciclos completados

| Módulo | Casos | Descripción |
|---|---|---|
| **A** | A1–A8 | Validaciones de `roll()` y detección de strike/spare |
| **B** | B1–B8 | Cálculo de puntaje con bonos (incluye juego perfecto = 300) |
| **C** | C1–C6 | Estado `isComplete()` con casos borde del frame 10 |

---

## 4. JaCoCo — Cobertura de código

### Configuración

Umbral configurado en `pom.xml`: **LINE coverage ≥ 85%** (no se puede bajar). El build falla con `BUILD FAILURE` si no se alcanza.

### Comando

```bash
mvn clean verify
```

### Reporte ANTES de pruebas adicionales

![Reporte JaCoCo con cobertura inicial](/bowling-tdd/docs/evidence/jacoco-antes.png)

**Estado inicial:** 58% branches, 65% lines.

### Reporte FINAL

![Reporte JaCoCo con cobertura ≥ 85%](/bowling-tdd/docs/evidence/jacoco-final.png)

**Estado final:** 89% branches, 98% lines.

### ¿Qué pruebas subieron la cobertura?

Al revisar el reporte inicial, se identificaron las siguientes ramas no ejercitadas:

1. **Frame 10 y `isStrike()`** — no había test que verificara que el frame 10 con primer tiro 10 **no** se considera strike (es `TENTH`). Se agregó `tenthFrameWithTen_isNotStrike()`.
2. **Frame 10 y `isSpare()`** — faltaba cubrir la rama `rolls.get(0) != 10` cuando el frame es el 10. Se agregó `tenthFrameWithTenAndZero_isNotSpare()`.
3. **`validateFrameSum` en frame 10** — faltaba verificar que dos tiros de 10 seguidos en el frame 10 **no** lanzan excepción. Se agregó `tenthFrameTwoStrikes_noException()`.
4. **`getType()` = NORMAL** — faltaba un test que verificara el tipo `NORMAL` para un frame sin strike ni spare. Se agregó `normalFrame_hasNormalType()`.

Además, se eliminó **código muerto** detectado por JaCoCo:
- Un `break` redundante en `strikeBonus()` (nunca ejecutado).
- Una guarda inalcanzable en `firstRollOf()` (nunca tomada).

---

## 5. SonarQube — Análisis estático

### Configuración

- **Imagen Docker:** `sonarqube:lts-community`
- **Puerto:** 9000
- **Project Key:** `bowling-tdd`
- **Project Name:** Bowling TDD Taller 02
- **Host URL:** `http://localhost:9000`
- **Token:** gestionado por variable de entorno `SONAR_TOKEN` (nunca en el repositorio)

### Comandos ejecutados

```powershell
# Levantar SonarQube
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community

# Ejecutar análisis
$env:SONAR_TOKEN="sqp_***"
mvn clean verify sonar:sonar "-Dsonar.token=$env:SONAR_TOKEN"
```

### Dashboard

![Dashboard SonarQube](/bowling-tdd/docs/evidence/sonarqube-dashboard.png)

### Resultado del Quality Gate

| Métrica | Valor | Estado |
|---|---|---|
| **Quality Gate** | Passed | ✅ |
| **Bugs** | 0 | ✅ |
| **Vulnerabilities** | 0 | ✅ |
| **Security Hotspots** | 0 | ✅ |
| **Code Smells** | 0 | ✅ |
| **Coverage** | 93.8% | ✅ |
| **Duplications** | 0.0% | ✅ |
| **Unit Tests** | 22 | ✅ |
| **Reliability** | A | ✅ |
| **Security** | A | ✅ |
| **Maintainability** | A | ✅ |

### Hallazgos y correcciones aplicadas

Durante el análisis inicial, SonarQube detectó 1 Code Smell (5 min de deuda técnica) que fue corregido:

| Issue | Severidad | Corrección aplicada |
|---|---|---|
| `break` redundante en `strikeBonus()` | Minor | Reemplazado por `return`, eliminando código muerto |
| Campo `currentFrame` sin uso | Major | Eliminado de `BowlingGame` |
| `scorer` no marcado como `final` | Info | Marcado como `private final` |
| Guarda inalcanzable en `firstRollOf()` | Minor | Simplificado el método |

Tras aplicar las correcciones y reanalizar, el **Quality Gate pasó a Passed** con **0 Code Smells**.

---

## 6. Pull Requests

| # | Enlace al PR | Fecha de merge | Módulo que cubre |
|---|---|---|---|
| 1 | https://github.com/JuanMaGV1/DOSW-Taller2-Bowling-Garzon-Juan/pull/1 | 17/09/2026 | Módulos A, B, C + Cobertura JaCoCo|
**Rama de trabajo:** `feature/Garzon-Juan_bowling`
**Rama destino:** `develop`

---

## 7. Reflexión técnica

### 01. ¿Qué caso edge del Bowling fue el más difícil de implementar con TDD y por qué?

El caso edge más complejo fue el **frame 10 con strike o spare**, porque rompe la uniformidad de los primeros 9 frames. En los frames 1–9, el flujo es predecible: dos tiros, o uno si es strike. Pero el frame 10 puede tener **2 o 3 tiros** dependiendo de si hubo strike o spare, y sus tiros extra **no generan bonos externos** (a diferencia de los frames normales). Esto obligó a:

1. Separar la lógica del frame 10 en `tenthFrameComplete()` dentro de `Frame`.
2. Tratar el frame 10 como **suma directa de sus tiros** en `BowlingScorer.calculate()`.
3. Asegurar que `isComplete()` no marcara completo el juego antes del tercer tiro.

Escribir la prueba del **juego perfecto (12 strikes = 300)** fue lo que reveló los huecos en la lógica: fallaba con 290 o 300 incorrectos según cómo se manejaran los bonos del frame 10.

---

### 02. ¿Qué parte del código cambió durante REFACTOR sin modificar el comportamiento observable?

Varios cambios:

- **Extracción de `validatePins()`** desde `roll()`: la validación se centralizó y se añadieron constantes `MIN_PINS` y `MAX_PINS`.
- **Extracción de `currentFrame()`**: eliminó la repetición de `frames.get(frames.size() - 1)`.
- **Extracción de `strikeBonus()` y `spareBonus()`** desde `calculate()`: mejoró la legibilidad y preparó el terreno para el frame 10.
- **Extracción de `isTenthFrame()`**: eliminó la comparación mágica `getIndex() == 9` repetida.
- **Simplificación de `tenthFrameComplete()`**: se reordenaron las condiciones para claridad, sin cambiar el resultado.
- **Eliminación de código muerto** (break redundante, guarda inalcanzable) detectado por JaCoCo y SonarQube.

En todos los casos, `mvn test` siguió en verde tras cada refactor.

---

### 03. ¿Qué casos de prueba descubriste al revisar el reporte de cobertura de JaCoCo que no habías considerado antes?

Al revisar el reporte con 89% de branches, noté que varias ramas del **frame 10** no estaban cubiertas porque todas mis pruebas lo abordaban desde `isComplete()` y `score()`, pero **no desde el propio `Frame`**. Agregué:

1. **`tenthFrameWithTen_isNotStrike()`** — verifica que el frame 10 con primer tiro 10 **no** se marca como strike (es `TENTH`).
2. **`tenthFrameWithTenAndZero_isNotSpare()`** — verifica que `[10, 0]` en el frame 10 no se marca como spare.
3. **`tenthFrameTwoStrikes_noException()`** — confirma que `validateFrameSum` no aplica al frame 10.
4. **`tenthFrameWithSpare_requiresBonus()`** — cubre `tenthFrameComplete()` con spare.
5. **`normalFrame_hasNormalType()`** — cubre la rama `NORMAL` de `getType()`.

Además, JaCoCo reveló **código muerto** (un `break` redundante y una guarda inalcanzable) que eliminé en refactor.

---

### 04. ¿Qué hallazgo de SonarQube produjo un cambio real en el código?

SonarQube marcó el **campo `currentFrame` como "unused"** en `BowlingGame`. Efectivamente, tras refactorizar el manejo de frames, ese campo dejó de usarse (el estado vive en `frames.size()`). Lo eliminé, junto con su inicialización en el constructor.

También marcó el **`break` redundante** en `strikeBonus()` y la **guarda inalcanzable** en `firstRollOf()`, que ya había detectado JaCoCo. Ambos se simplificaron.

Esto demuestra que **JaCoCo y SonarQube son complementarios**: JaCoCo ve qué no se ejecuta, SonarQube ve qué no se usa y qué se puede mejorar.

---

## 8. Cómo ejecutar el proyecto

### Requisitos

- Java 24 (o la versión configurada en `pom.xml`)
- Maven 3.9+
- Docker Desktop (para SonarQube)

### Comandos

```bash
# Compilar y ejecutar pruebas
mvn clean test

# Verificar cobertura (falla si < 85%)
mvn clean verify

# Reporte de cobertura (HTML)
# Abrir target/site/jacoco/index.html

# Análisis estático con SonarQube
mvn clean verify sonar:sonar -Dsonar.token=$SONAR_TOKEN
```
