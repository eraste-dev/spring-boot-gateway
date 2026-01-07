# Mini Projet – Microservices avec Spring Boot & API Gateway

## 1. Présentation du projet

Ce projet est un **mini système de gestion de commandes (Order Management System)** conçu dans le but d'apprendre et de maîtriser les concepts fondamentaux des **architectures microservices** avec **Java Spring Boot**.

Il met en oeuvre :
- Plusieurs microservices indépendants basés sur l'**architecture hexagonale**
- Une API Gateway comme point d'entrée unique
- Communication inter-services via REST/Feign
- Découverte dynamique des services (Eureka)
- **Pattern Adapter** pour une flexibilité maximale sur les sources de données
- **Conteneurisation complète** avec Docker et Docker Compose

---

## 2. Objectifs pédagogiques

À travers ce projet, l'objectif est de comprendre et pratiquer :

- L'architecture microservices (avantages & contraintes)
- L'**architecture hexagonale** (Ports & Adapters)
- Le pattern **API Gateway**
- La séparation des responsabilités par domaine métier
- La communication synchrone entre services (OpenFeign)
- Les briques Spring Cloud : Gateway, Eureka, Feign
- La gestion des erreurs dans un contexte distribué
- Les bonnes pratiques de structuration et de tests
- La **conteneurisation** avec Docker et Docker Compose

---

## 3. Architecture globale

```
                    +------------------+
                    |   Client (ex:    |
                    |   Postman / UI)  |
                    +--------+---------+
                             |
                             v
                    +------------------+
                    |   API Gateway    |
                    |   (Docker)       |
                    +--------+---------+
                             |
      +---------------+------+-------+------------------+
      |               |              |                  |
      v               v              v                  v
+-------------+ +-------------+ +-------------+  +-------------+
| User Service| |Product Svc  | | Order Svc   |  |Eureka Server|
| (Docker)    | | (Docker)    | | (Docker)    |  | (Docker)    |
+-------------+ +-------------+ +-------------+  +-------------+
      |               |              |
      v               v              v
  PostgreSQL      PostgreSQL     PostgreSQL
  (ou autre)      (ou autre)     (ou autre)
```

---

## 4. Architecture Hexagonale (Ports & Adapters)

Chaque microservice suit l'**architecture hexagonale**, garantissant une séparation claire entre la logique métier et les détails techniques.

### Principes

```
                    +---------------------------+
                    |      INFRASTRUCTURE       |
                    |  (Adapters secondaires)   |
                    |                           |
                    |  +---------------------+  |
                    |  |      DOMAIN         |  |
                    |  |  (Logique métier)   |  |
                    |  |                     |  |
                    |  |  - Entities         |  |
                    |  |  - Value Objects    |  |
                    |  |  - Domain Services  |  |
                    |  +---------------------+  |
                    |           ^               |
                    |           |               |
                    |  +--------+--------+      |
                    |  |   APPLICATION   |      |
                    |  |    (Use Cases)  |      |
                    |  |                 |      |
                    |  | - Ports (in/out)|      |
                    |  | - Services      |      |
                    |  +-----------------+      |
                    +---------------------------+
                              ^
                              |
                    +------------------+
                    |   INFRASTRUCTURE |
                    | (Adapters primaires)
                    |  - REST Controllers
                    |  - Message Listeners
                    +------------------+
```

### Structure des dossiers (par microservice)

```
user-service/
├── src/main/java/com/example/userservice/
│   ├── domain/                          # Coeur métier (indépendant)
│   │   ├── model/
│   │   │   └── User.java                # Entité du domaine
│   │   ├── port/
│   │   │   ├── in/                      # Ports d'entrée (use cases)
│   │   │   │   └── UserUseCase.java
│   │   │   └── out/                     # Ports de sortie (SPI)
│   │   │       └── UserRepositoryPort.java
│   │   └── service/
│   │       └── UserDomainService.java   # Logique métier pure
│   │
│   ├── application/                     # Orchestration des use cases
│   │   └── service/
│   │       └── UserApplicationService.java
│   │
│   └── infrastructure/                  # Détails techniques (adapters)
│       ├── adapter/
│       │   ├── in/                      # Adapters primaires
│       │   │   └── web/
│       │   │       ├── UserController.java
│       │   │       └── dto/
│       │   │           ├── UserRequest.java
│       │   │           └── UserResponse.java
│       │   └── out/                     # Adapters secondaires
│       │       └── persistence/
│       │           ├── UserJpaAdapter.java
│       │           ├── UserJpaRepository.java
│       │           ├── UserJpaEntity.java
│       │           └── UserMapper.java
│       └── config/
│           └── BeanConfiguration.java
│
├── Dockerfile
├── pom.xml
└── src/main/resources/
    └── application.yml
```

---

## 5. Pattern Adapter – Flexibilité des sources de données

Le **pattern Adapter** permet de changer facilement de source de données sans impacter la logique métier.

### Port de sortie (Interface)

```java
// domain/port/out/UserRepositoryPort.java
public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
}
```

### Adapter JPA (PostgreSQL/MySQL/H2)

```java
// infrastructure/adapter/out/persistence/UserJpaAdapter.java
@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        UserJpaEntity entity = mapper.toJpaEntity(user);
        UserJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
```

### Adapter MongoDB (alternatif)

```java
// infrastructure/adapter/out/persistence/UserMongoAdapter.java
@Component
@RequiredArgsConstructor
@Profile("mongodb")  // Activé uniquement avec le profil mongodb
public class UserMongoAdapter implements UserRepositoryPort {

    private final UserMongoRepository mongoRepository;
    private final UserMongoMapper mapper;

    @Override
    public User save(User user) {
        UserDocument document = mapper.toDocument(user);
        UserDocument saved = mongoRepository.save(document);
        return mapper.toDomain(saved);
    }

    // ... autres méthodes
}
```

### Changement de base de données

Pour changer de base de données, il suffit de :

1. **Créer un nouvel adapter** implémentant `UserRepositoryPort`
2. **Configurer le profil Spring** approprié
3. **Modifier les dépendances** dans `pom.xml`

```yaml
# application.yml - Profil PostgreSQL (défaut)
spring:
  profiles:
    active: postgres

---
# application-postgres.yml
spring:
  datasource:
    url: jdbc:postgresql://db:5432/userdb
    driver-class-name: org.postgresql.Driver

---
# application-mongodb.yml
spring:
  data:
    mongodb:
      uri: mongodb://mongo:27017/userdb
```

---

## 6. Microservices du projet

### 6.1 User Service (`user-service`)

**Responsabilité** : Gestion des utilisateurs (CRUD)

**Ports d'entrée (Use Cases)**
- `CreateUserUseCase`
- `GetUserUseCase`
- `UpdateUserUseCase`
- `DeleteUserUseCase`

**Endpoints**
| Méthode | Route          | Description              |
|---------|----------------|--------------------------|
| POST    | `/users`       | Créer un utilisateur     |
| GET     | `/users/{id}`  | Récupérer un utilisateur |
| GET     | `/users`       | Lister les utilisateurs  |
| PUT     | `/users/{id}`  | Modifier un utilisateur  |
| DELETE  | `/users/{id}`  | Supprimer un utilisateur |

---

### 6.2 Product Service (`product-service`)

**Responsabilité** : Gestion du catalogue produits

**Ports d'entrée (Use Cases)**
- `CreateProductUseCase`
- `GetProductUseCase`
- `UpdateProductUseCase`
- `DeleteProductUseCase`

**Endpoints**
| Méthode | Route            | Description            |
|---------|------------------|------------------------|
| POST    | `/products`      | Créer un produit       |
| GET     | `/products/{id}` | Récupérer un produit   |
| GET     | `/products`      | Lister les produits    |
| PUT     | `/products/{id}` | Modifier un produit    |
| DELETE  | `/products/{id}` | Supprimer un produit   |

---

### 6.3 Order Service (`order-service`)

**Responsabilité** : Gestion des commandes avec validation croisée

**Ports d'entrée (Use Cases)**
- `CreateOrderUseCase`
- `GetOrderUseCase`
- `CancelOrderUseCase`

**Ports de sortie (externes)**
- `UserClientPort` : Communication avec user-service
- `ProductClientPort` : Communication avec product-service
- `OrderRepositoryPort` : Persistance des commandes

**Endpoints**
| Méthode | Route           | Description              |
|---------|-----------------|--------------------------|
| POST    | `/orders`       | Créer une commande       |
| GET     | `/orders/{id}`  | Récupérer une commande   |
| GET     | `/orders`       | Lister les commandes     |
| DELETE  | `/orders/{id}`  | Annuler une commande     |

---

## 7. API Gateway (`api-gateway`)

### Rôles
- Point d'entrée unique
- Routage vers les services
- Centralisation des logs (filtre global)
- Préparation à la sécurité (JWT header forwarding)

### Routes exposées

| Route              | Service cible      |
|--------------------|--------------------|
| `/api/users/**`    | `user-service`     |
| `/api/products/**` | `product-service`  |
| `/api/orders/**`   | `order-service`    |

---

## 8. Technologies utilisées

| Composant                 | Technologie                              |
|---------------------------|------------------------------------------|
| Langage                   | Java 17+                                 |
| Framework                 | Spring Boot 3.x                          |
| Architecture              | Hexagonale (Ports & Adapters)            |
| API Gateway               | Spring Cloud Gateway                     |
| Service Discovery         | Spring Cloud Netflix Eureka              |
| Client HTTP inter-service | OpenFeign + Spring Cloud LoadBalancer    |
| Persistance (défaut)      | PostgreSQL                               |
| Persistance (alternative) | MongoDB, MySQL, H2                       |
| Sérialisation             | JSON (Jackson)                           |
| Tests                     | JUnit 5, Mockito, Testcontainers         |
| Build                     | Maven                                    |
| Conteneurisation          | Docker & Docker Compose                  |

---

## 9. Configuration Docker

### Dockerfile (exemple pour user-service)

```dockerfile
# user-service/Dockerfile
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN apk add --no-cache maven && mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose

```yaml
# docker-compose.yml
version: '3.8'

services:
  # ==================== DATABASES ====================
  postgres-users:
    image: postgres:15-alpine
    container_name: postgres-users
    environment:
      POSTGRES_DB: userdb
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - postgres-users-data:/var/lib/postgresql/data
    networks:
      - microservices-network

  postgres-products:
    image: postgres:15-alpine
    container_name: postgres-products
    environment:
      POSTGRES_DB: productdb
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    ports:
      - "5433:5432"
    volumes:
      - postgres-products-data:/var/lib/postgresql/data
    networks:
      - microservices-network

  postgres-orders:
    image: postgres:15-alpine
    container_name: postgres-orders
    environment:
      POSTGRES_DB: orderdb
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    ports:
      - "5434:5432"
    volumes:
      - postgres-orders-data:/var/lib/postgresql/data
    networks:
      - microservices-network

  # ==================== EUREKA SERVER ====================
  eureka-server:
    build:
      context: ./eureka-server
      dockerfile: Dockerfile
    container_name: eureka-server
    ports:
      - "8761:8761"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
    networks:
      - microservices-network
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8761/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 5

  # ==================== USER SERVICE ====================
  user-service:
    build:
      context: ./user-service
      dockerfile: Dockerfile
    container_name: user-service
    ports:
      - "8081:8081"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-users:5432/userdb
      - SPRING_DATASOURCE_USERNAME=user
      - SPRING_DATASOURCE_PASSWORD=password
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      eureka-server:
        condition: service_healthy
      postgres-users:
        condition: service_started
    networks:
      - microservices-network

  # ==================== PRODUCT SERVICE ====================
  product-service:
    build:
      context: ./product-service
      dockerfile: Dockerfile
    container_name: product-service
    ports:
      - "8082:8082"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-products:5432/productdb
      - SPRING_DATASOURCE_USERNAME=user
      - SPRING_DATASOURCE_PASSWORD=password
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      eureka-server:
        condition: service_healthy
      postgres-products:
        condition: service_started
    networks:
      - microservices-network

  # ==================== ORDER SERVICE ====================
  order-service:
    build:
      context: ./order-service
      dockerfile: Dockerfile
    container_name: order-service
    ports:
      - "8083:8083"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-orders:5432/orderdb
      - SPRING_DATASOURCE_USERNAME=user
      - SPRING_DATASOURCE_PASSWORD=password
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      eureka-server:
        condition: service_healthy
      postgres-orders:
        condition: service_started
      user-service:
        condition: service_started
      product-service:
        condition: service_started
    networks:
      - microservices-network

  # ==================== API GATEWAY ====================
  api-gateway:
    build:
      context: ./api-gateway
      dockerfile: Dockerfile
    container_name: api-gateway
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      eureka-server:
        condition: service_healthy
      user-service:
        condition: service_started
      product-service:
        condition: service_started
      order-service:
        condition: service_started
    networks:
      - microservices-network

networks:
  microservices-network:
    driver: bridge

volumes:
  postgres-users-data:
  postgres-products-data:
  postgres-orders-data:
```

---

## 10. Organisation du projet

```
microservices-project/
├── docker-compose.yml           # Orchestration de tous les services
├── eureka-server/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── api-gateway/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── user-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/java/.../
│           ├── domain/          # Logique métier pure
│           ├── application/     # Use cases
│           └── infrastructure/  # Adapters (REST, DB)
├── product-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/java/.../
│           ├── domain/
│           ├── application/
│           └── infrastructure/
├── order-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/java/.../
│           ├── domain/
│           ├── application/
│           └── infrastructure/
└── README.md
```

Chaque service est **autonome** :
- Propre `Dockerfile`
- Propre `pom.xml`
- Propre configuration (`application.yml`)
- Propre base de données
- Architecture hexagonale complète
- Déployable indépendamment

---

## 11. Lancement du projet

### Avec Docker Compose (recommandé)

```bash
# Construire et démarrer tous les services
docker-compose up --build

# Démarrer en arrière-plan
docker-compose up -d --build

# Voir les logs
docker-compose logs -f

# Arrêter les services
docker-compose down

# Arrêter et supprimer les volumes
docker-compose down -v
```

### Sans Docker (développement local)

**Ordre de démarrage**
1. `eureka-server`
2. `user-service`
3. `product-service`
4. `order-service`
5. `api-gateway`

```bash
# Dans chaque dossier de service
mvn clean spring-boot:run
```

---

## 12. URLs utiles

| Service          | URL                                      |
|------------------|------------------------------------------|
| Eureka Dashboard | http://localhost:8761                    |
| API Gateway      | http://localhost:8080                    |
| User Service     | http://localhost:8081                    |
| Product Service  | http://localhost:8082                    |
| Order Service    | http://localhost:8083                    |

### Endpoints via API Gateway

```bash
# Users
curl http://localhost:8080/api/users
curl http://localhost:8080/api/users/1

# Products
curl http://localhost:8080/api/products
curl http://localhost:8080/api/products/1

# Orders
curl http://localhost:8080/api/orders
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "productId": 1, "quantity": 2}'
```

---

## 13. Tests

### Tests unitaires (domaine)

```bash
# Tester la logique métier sans dépendances externes
mvn test -Dtest=*DomainServiceTest
```

### Tests d'intégration (adapters)

```bash
# Tester les adapters avec Testcontainers
mvn test -Dtest=*AdapterTest
```

### Tests de bout en bout

```bash
# Lancer tous les services et exécuter les tests E2E
docker-compose up -d
mvn test -Dtest=*E2ETest
```

---

## 14. Avantages de cette architecture

| Aspect                    | Bénéfice                                                    |
|---------------------------|-------------------------------------------------------------|
| **Testabilité**           | Domaine testable sans infrastructure                        |
| **Flexibilité**           | Changement de BDD sans toucher au métier                    |
| **Maintenabilité**        | Séparation claire des responsabilités                       |
| **Évolutivité**           | Ajout de nouveaux adapters simple                           |
| **Indépendance**          | Chaque service déployable séparément                        |
| **Conteneurisation**      | Environnements reproductibles avec Docker                   |