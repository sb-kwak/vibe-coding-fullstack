# 프로젝트 명세서 (Project Specification)

본 문서는 최소 기능 스프링부트 애플리케이션인 `vibeapp`의 기술 사양을 정의합니다.

## 1. 프로젝트 기본 정보
- **프로젝트 명**: vibeapp
- **설명**: 최소 기능 스프링부트 애플리케이션을 생성하는 프로젝트
- **그룹**: com.example
- **메인 클래스**: com.example.vibeapp.VibeApp
- **설정 방식**: YAML (application.yml)

## 2. 개발 환경 및 기술 스택
- **언어**: Java
- **JDK**: JDK 25 이상
- **프레임워크**: Spring Boot 4.0.1 이상
- **빌드 도구**: Gradle 9.3.1 이상 (Groovy DSL)
- **데이터베이스**: H2 Database (File Mode: `./data/testdb`)
- **ORM/Persistence**: Spring Data JPA (JpaRepository 인터페이스 기반)

## 3. 설정 및 플러그인
- **플러그인**:
  - `io.spring.dependency-management`: Spring Boot 버전에 맞춘 의존성 관리
- **핵심 의존성 (Dependencies)**:
  - `org.springframework.boot:spring-boot-starter-data-jpa`: JPA 및 Hibernate 지원
  - `com.h2database:h2`: 데이터베이스 엔진
  - `org.springframework.boot:spring-boot-starter-validation`: 데이터 검증

## 4. 빌드 설정 (build.gradle 예시)
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '4.0.1'
    id 'io.spring.dependency-management' version '1.1.7'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '25'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

## 5. 현재 구현 상태 (Current Implementation Status)

### 주요 기능 (Core Features)
- **홈 페이지**: 프리미엄 테마가 적용된 메인 대시보드
- **게시판 CRUD**:
  - 게시글 목록 조회 (Pagination: 페이지당 5개)
  - 게시글 상세 조회 (조회수 및 생성/수정일 정보 포함)
  - 새 게시물 작성 및 기존 게시물 수정/삭제
- **태그 (Tags) 시스템**:
  - 쉼표(,) 구분 기반 태그 입력 및 상세 페이지 표시
  - `POST_TAGS` 테이블을 통한 데이터 관리 및 Cascade 삭제 지원 (FK 제약 조건 및 EntityManager 처리)
- **디자인 및 UI**:
  - Tailwind CSS 기반의 모던 UI
  - 다크 모드(Dark Mode) 지원 및 프리미엄 히어로 이미지 적용

### 아키텍처 및 관례 (Architecture & Conventions)
- **비즈니스 로직**:
  - `@Transactional`을 통한 데이터 원자성 보장 (서비스 계층)
  - DTO (Java Record) 패턴을 통한 데이터 전송 및 유효성 검증 분리
- **데이터 저장 및 영속성 (Spring Data JPA)**:
  - `JpaRepository` 인터페이스를 상속하여 CRUD 및 페이징 기능을 자동화
  - `Query Method`와 `@Query`를 활용하여 복잡한 데이터 조회 처리
  - H2 파일 데이터베이스 연동 및 `schema.sql` 기반 자동 테이블 생성
- **기능 기반 구조 (Feature-based Structure)**:
  - 패키지 및 템플릿을 기능별(`post`, `home` 등)로 분리
