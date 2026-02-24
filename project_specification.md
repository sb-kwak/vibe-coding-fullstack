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
- **빌드 도구**: Gradle 9.3.0 이상 (Groovy DSL)

## 3. 설정 및 플러그인
- **플러그인**:
  - `io.spring.dependency-management`: Spring Boot 버전에 맞춘 의존성 관리
- **의존성 (Dependencies)**:
  - 없음 (최소 기능 프로젝트)

## 4. 빌드 설정 (build.gradle 예시)
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '4.0.1'
    id 'io.spring.dependency-management' version '1.1.7' // Boot 버전에 호환되는 버전
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '25'

repositories {
    mavenCentral()
}

dependencies {
    // 최소 기능 프로젝트로 기본 의존성 없음
}
```

## 5. 현재 구현 상태 (Current Implementation Status)

### 주요 기능 (Core Features)
- **홈 페이지**: 프리미엄 테마가 적용된 메인 대시보드
- **게시판 CRUD**:
  - 게시글 목록 조회 (Pagination: 페이지당 5개)
  - 게시글 상세 조회 (조회수 및 생성/수정일 정보 포함)
  - 새 게시물 작성 및 기존 게시물 수정/삭제
- **디자인 및 UI**:
  - Tailwind CSS 기반의 모던 UI
  - 다크 모드(Dark Mode) 기본 지원 및 명암 대비 최적화
  - 프리미엄 히어로 이미지 적용

### 아키텍처 및 관례 (Architecture & Conventions)
- **기능 기반 구조 (Feature-based Structure)**:
  - Java 패키지 및 HTML 템플릿을 `home`, `post` 등 기능 단위로 분리하여 관리
- **표준 명명 규칙**:
  - 실무 관례를 따른 메서드 명명 (`findById`, `createPost`, `updatePost`, `deleteById` 등)
- **데이터 저장**:
  - 인메모리 저장소 (`CopyOnWriteArrayList`) 및 원자적 시퀀스 (`AtomicLong`) 사용
