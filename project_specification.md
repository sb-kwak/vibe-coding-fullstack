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
