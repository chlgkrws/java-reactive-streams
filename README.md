# java-reactive-streams
# Reactive Streams Playground

Java 9 Reactive Streams API를 기반으로, 데이터 발행(`Publisher`), 소비(`Subscriber`), 변환(`Processor`)의 흐름을 구현하고 테스트하는 Kotlin 프로젝트입니다.


---

## 주요 기능
- **Publisher-Subscriber 상호작용**: `SubmissionPublisher`를 사용하여 데이터를 발행하고 구독합니다.
- **데이터 변환**: `TransformProcessor`를 사용하여 스트림 데이터를 변환합니다.
- **구독 제어**: `Subscription.request(n)` 메서드를 활용하여 소비할 데이터 개수를 제어합니다.
- **테스트**: Reactive Streams의 다양한 시나리오를 검증하는 테스트 코드 작성.

---

## 사용 기술
- **Kotlin**
- **Java 9 Reactive Streams API**
- **JUnit 5**
- **AssertJ**
- **Awaitility**