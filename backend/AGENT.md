# 이거챙겨 — AI Coding Agent Guide

## 1. 프로젝트 개요

프로젝트 이름: 이거챙겨

이거챙겨는 날씨 정보를 보여주는 앱이 아니다.

사용자의 출발 시간에 맞춰
전날 대비 기상 변화를 분석하고
"오늘 챙겨야 할 행동"을 추천하는
행동 중심 개인화 기상 알림 앱이다.

핵심 개념:
- 정보 제공이 아니라 행동 추천
- 사용자 피드백 기반 점진적 보정
- 로그인 없이 디바이스 기반 사용자 모델
- Rule 기반 추천 엔진

---

## 2. 설계 철학

1. 기본 판단은 Rule 기반으로 구현한다.
2. 추천은 점수 기반으로 계산한다.
3. 사용자 피드백을 통해 민감도를 조정한다.
4. MVP는 단일 서버 + MySQL 기반 단순 구조로 구현한다.
5. 레이어 의존성 규칙을 반드시 지킨다.

---

## 3. 아키텍처 원칙

패키지 구조는 다음과 같다:

api     → 외부 요청 진입점 (Controller + Request/Response DTO)
domain  → 핵심 비즈니스 로직 (Entity, Repository, Service)
infra   → 외부 시스템 연동 (Weather API, Push, FCM 등)
support → 공통 설정, 예외 처리, 스케줄러

### 3.1 의존성 규칙 (매우 중요)

- domain은 infra를 직접 의존하지 않는다.
- 외부 API 호출은 반드시 infra 패키지에 위치한다.
- controller(api)는 domain service만 호출한다.
- 추천 핵심 로직은 반드시 domain.recommendation 패키지에 위치한다.
- AI 로직은 domain 내부 보조 모듈로만 존재한다.
- domain 레이어는 외부 API에 직접 의존하지 않는다.

의존성 방향:

api → domain  
infra → domain (인터페이스 구현 가능)  
support → domain 호출 가능  
domain → infra 의존 금지

---

## 4. 사용자 식별 방식

로그인 기능은 없다.

앱 최초 실행 시:
- UUID(device_id)를 생성한다.
- 서버에 device_id를 등록한다.
- 이후 모든 요청은 device_id 기반으로 사용자 식별한다.

User는 계정이 아니라 디바이스 사용자이다.

---

## 5. 핵심 동작 흐름

### 5.1 알림 생성 흐름

1. support.scheduler에서 매 1분마다 실행 (@Scheduled)
2. 출발 10분 전 사용자 조회
3. infra.weather에서 현재 날씨 조회
4. infra.weather에서 전날 동일 시간 날씨 조회
5. domain.recommendation에서 변화량 계산
6. domain.recommendation에서 행동 점수 계산
7. domain에서 민감도 반영
8. 추천 메시지 생성
9. infra.push를 통해 알림 발송

---

## 6. 행동 기반 추천 로직 (domain.recommendation)

### 6.1 외투 추천 점수 예시

temp_diff = today_temp - yesterday_temp

if temp_diff <= -5 → coat_score += 30
if today_temp <= 5 → coat_score += 20
if wind_speed >= 8 → coat_score += 10

coat_score = coat_score * cold_sensitivity

---

### 6.2 우산 추천 점수 예시

if rain_probability >= 60 → umbrella_score += 40
if expected_rainfall >= 5 → umbrella_score += 30

umbrella_score = umbrella_score * rain_sensitivity

---

### 6.3 추천 조건

if coat_score >= threshold → "외투 이거챙겨"
if umbrella_score >= threshold → "우산 이거챙겨"

threshold는 고정값이 아니라
피드백을 통해 조정 가능하도록 설계한다.

---

## 7. 피드백 기반 동작 (핵심 설계)

이 앱은 행동 기반 + 피드백 기반으로 동작한다.

사용자는 알림 후:

- 👍 도움이 됐어요
- 👎 별로였어요

를 선택할 수 있다.

### 7.1 피드백 저장

Feedback 엔티티:

- device_id
- recommendation_type
- accepted (boolean)
- weather_snapshot
- timestamp

---

### 7.2 민감도 보정 로직 (domain 내부)

if accepted == true:
sensitivity += 0.05
else:
sensitivity -= 0.03

민감도는 0.0 ~ 1.0 범위로 제한한다.

이 로직은 반드시 domain 레이어에서 구현한다.

---

## 8. AI 사용 원칙

AI는 보조적 역할만 수행한다.

허용:
- 추천 문구 자연어 생성
- 추천 이유 설명 생성

금지:
- 점수 계산
- 날씨 판단
- 민감도 계산
- 스케줄링 제어

AI 관련 모듈은 domain 내부 보조 컴포넌트로만 존재한다.

---

## 9. 기술 스택

Backend: Spring Boot
Database: MySQL
Scheduling: Spring @Scheduled
Weather API: 기상청 Open API
Push: FCM

Kafka, Redis, 분산 큐 등은 MVP 범위 밖이다.

---

## 10. API 목록 (api 패키지)

POST   /api/device/register
POST   /api/schedules
GET    /api/recommendation?deviceId={id}
POST   /api/feedback

Controller는 domain service만 호출해야 한다.

---

## 11. 절대 하지 말 것

- 로그인/OAuth 구현
- JWT 인증 구현
- domain에서 외부 API 직접 호출
- infra 로직을 domain에 배치
- 뉴스 크롤링
- 복잡한 분산 아키텍처

MVP는 단순하고 명확해야 한다.
