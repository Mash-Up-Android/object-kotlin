## 0. 개론

데이터 중심 설계는 행동보다 데이터를 먼저 결정하고, 협력이라는 문맥을 벗어나 객체의 고립된 상태에 초점을 맞춘다.

캡슐화를 위반하기 쉽고, 결합도가 높아지며, 변경이 어려워지는 문제가 있었다.

문제점을 해결하는 방법은 데이터가 아닌 책임에 초점을 맞추는 것

책임에 초점을 둘때 직면하는 어려운 점은 어떤 객체에 어떤 책임을 할당할지를 결정하는것이다.

하지만, 책임을 할당하는 다양한 책임 할당 방법이 있고, 어떤게 최선인지는 상황과 문맥에 따라 달라진다.

## 1. 책임 주도 설계를 향해

데이터 중심 설계에서 책임 중심 설계로 전환하기 위한 원칙 2가지

1. 데이터보다 행동을 먼저 결정하라
2. 협력이라는 문맥 안에서 책임을 결정하라

### 데이터보다 행동을 먼저 결정하라

객체에 중요한 것은 내부 데이터가 아닌 외부에 제공하는 행동임 (행동 == 책임으로 볼 수 있다)

객체는 협력에 참여하기 위해 존재하며, 협력 안에서 수행하는 책임이 객체의 존재가치를 증명한다.

여기서 데이터는 책임을 수행하는데 필요한 재료일 뿐

결국, 객체의 데이터에서 행동으로 무게중심을 옮기는 방법이 필요하다.

기본적인 방법은 객체 설계를 위한 질문 순서를 바꾸는 것.

**데이터 중심 설계에서는?**
자
1. 객체가 포함해야 하는 데이터가 무엇인가?
2. 데이터를 처리하는데 필요한 오퍼레이션은 무엇인가?

**책임 중심 설계에서는?**

1. 객체가 수행해야 하는 책임은 무엇인가?
2. 이 책임을 수행하는데 필요한 데이터는 무엇인가?

### 협력이라는 문맥 안에서 책임을 결정하라

책임의 품질은 그 책임이 협력에 얼마나 적합하냐로 결정된다.

객체에 할당된 책임이 협력에 어울리지 않는다면 잘못 할당한 것

책임은 객체의 입장이 아니라 객체가 참여하는 협력에 적합해야 한다.

협력에 적합한 책임을 얻기 위해서는 메세지를 결정한 후에 객체를 선택해야 한다.

메세지가 존재하기 때문에 메세지를 처리할 객체가 필요한 것

즉, 메세지는 클라이언트의 의도를 표현한다

또한, 객체를 결정하기 전에 객체가 수신할 메세지를 먼저 결정한다.

클라이언트는 어떤 객체가 내 메세지를 수신할지는 모른다. 단순히 자신의 의도를 포함한 메세지를 전송할 뿐

메세지를 수신하기로 결정된 객체는 그 의도를 처리할 “책임”을 할당받는 것이다.

## 2. 책임 할당을 위한 GRASP 패턴

GRASP (General Responsibility Assignment Software Pattern : 일반적 책임 할당을 위한 소프트웨어 패턴)

설계 과정은 아래 순서대로 따라간다.

### 도메인 개념에서 출발하기

설계 시작 전에 도메인에 대한 개략적인 모습을 그려보는 것이 유용

어떤 책임을 할당해야 할 때 가장 고민해야 하는 요소는 도메인 개념이다.

영화 예매 시스템에서 살펴보자

<img width="656" height="185" alt="image" src="https://github.com/user-attachments/assets/e5bdb3b6-c0f4-4281-ae84-5d30ae4c7a8f" />


설계를 시작하는 단계에서는 개념들의 의미나 관계가 완벽할 필요는 없음

위 그림은 단순히 설계를 위한 개념의 모음 정도로 이해하면 됨

**올바른 도메인 모델이란 존재하지 않는다?**

도메인 모델은 도메인을 개념적으로 표현한것, 그 안에 포함되는 개념과 관계는 구현의 기반이 되어야 함

도메인 모델이 구현을 염두에 두고 구조화 되는 것은 바람직하다는 것을 의미

<img width="656" height="119" alt="image" src="https://github.com/user-attachments/assets/32ae17ca-16d0-4882-a4a9-1a2540390acb" />

Q. 이게 무슨말일까??

### 정보 전문가에게 책임을 할당하라

책임 주도 설계 방식의 첫번째는 어플리케이션이 제공할 기능을 어플리케이션의 책임으로 생각하는 것.

이 책임을 어플리케이션에 대해 전송된 메시지로 간주하고, 해당 메시지를 처리할 첫번째 객체를 선택하는 것으로 시작

**영화 예매 시스템에서는?**

“영화를 예매하는 것”은 사용자에게 제공해야 하는 기능, 이걸 책임으로 본다면

어플리케이션은 “영화를 예매할 책임”이 있다

그 다음으로, 영화를 예매할 책임을 수행하는데 필요한 메시지를 결정

첫번째 질문) 메시지를 전송할 객체는 무엇을 원하는가?

- 협력을 시작할 객체는 미정이지만 객체가 원하는 것은 영화를 예매하는 것.
- “예매하라” 라는 메시지로 결정

두번째 질문) 메시지를 수신할 적합한 객체는 누구인가?

- 이 질문에 답을 하기 위해서는 객체가 상태와 행동을 통합한 캡슐화 단위라는 것에 집중해야 한다.
- 객체의 책임과 책임을 수행하는데 필요한 상태는 동일 객체에 존재해야 한다.

→ 객체에게 책임을 할당하는 첫번째 원칙은 책임을 수행할 정보를 알고있는 객체에 할당하는 것 (정보 전문가 패턴)

**INFORMATION EXPERT (정보 전문가) 패턴?**

책임을 수행하는데 필요한 정보를 가장 많이 알고있는 객체에게 할당하느 것

객체가 자신이 소유하고 있는 정보와 관련된 작업을 수행한다는 것을 표현한 것
→ 정보를 알고있는 객체만이 책임을 어떻게 수행할 지 스스로 결정할 수 있다

이렇게 하면 정보와 행동을 최대한 가깝게 위치시키기 때문에 캡슐화를 유지할 수 있다.

INFORMATION EXPERT 패턴 적용 예시

<img width="621" height="187" alt="image" src="https://github.com/user-attachments/assets/1ce68c11-5f41-405e-9037-b72d69671156" />


“예매하라” 메시지를 처리하기 위해서는 예매에 필요한 정보를 가장 많이 알고있는 객체에게 책임을 할당해야 함 → Screening 객체가 영화에 대한 정보, 상영시간 등 예매를 위한 정보를 가장 많이 알고있음

“예매하라”를 수신한 Screening 객체는 메시지를 처리하기 위해 수행할 책임들에 대해 생각해봐야 함

스스로 처리할 수 없다면 외부 객체에 위임해야 함 → 외부로 전송하는 메시지 생성

여기서, 위임하는 메시지는 새로운 객체의 책임으로 할당됨

이 과정이 반복되다 보면 연쇄적인 메시지 송수신 과정을 통해 협력 공동체로 구성됨

<img width="657" height="154" alt="image" src="https://github.com/user-attachments/assets/015d7842-920a-49b8-bdd3-662063546667" />


“예매하라” 메시지를 완료하기 위해서는 가격 계산 작업이 필요

Screening 객체는 가격과 관련된 정보를 모르기 때문에 외부 객체에 도움을 받아야 함

→ “가격을 계산하라” 메시지가 외부 객체로 송신됨

→ INFORMATION EXPERT 패턴에 의해 해당 메시지는 Movie객체의 책임이 됨

<img width="656" height="208" alt="image" src="https://github.com/user-attachments/assets/e38bfcb4-0949-4fbe-b82b-164750661095" />


Movie 객체는 “가격을 계산하라” 메시지의 완료를 위해 가격을 계산하겠지만, 할인 조건에 대한 적용 여부를 판단해야 함

하지만, 할인 조건은 Movie 객체의 책임이 아님

→ “할인 여부를 판단하라” 메시지가 송신되고, DiscountCondition 객체의 책임으로 할당됨

이런 과정들을 통해 각 객체에 책임이 할당되고, 이때 사용되는게 INFORMATION EXPERT 패턴

객체 지향의 가장 기본적인 원리인 상태와 행동을 책임 할당 관점으로 설계하게 됨

### 높은 응집도와 낮은 결합도

“할인 여부를 판단하라” 메시지를 Movie가 송신하는게 아니라 Screening이 송신하게 한다면?

<img width="656" height="285" alt="image" src="https://github.com/user-attachments/assets/c9acd617-8b55-48d9-8627-650aea1f7979" />

위 그림처럼 설계될 수 있지만 굳이?
→ Screening이 DiscountCondition 객체와 불필요한 협력 관계를 맺게 된다.

책임을 할당할 수 있는 여러 방안이 있다면 응집도와 결합도 측면에서 가장 나이스한 방안을 채택해야 한다.

LOW COUPLING (낮은 결합도) 패턴과 HIGH COHESION (높은 응집도)패턴을 지켜야 한다.

이 패턴들을 생각하면서 설계하다 보면 단순하면서도 재사용 가능하고 유연한 설계를 얻을 수 있다.

**LOW COUPLING 측면**

Movie 객체에 이미 DiscountCondition 목록을 속성으로 갖고있기 때문에 Movie가 협력하게 하는게 새로운 결합도를 추가하지 않는 더 나이스한 방안

**HIGH COHESION 측면**

Screening의 가장 중요한 책임은 예매

할인 여부를 받게 된다면 영화 요금 계산과 관련된 책임의 일부를 떠안게 되는 샘

또한, Movie가 할인 여부를 필요로 하다는 것도 알고있어야 한다.

→ 예매 요금 계산 방식이 변경된다면 Screening 객체도 영향을 받게 되기 때문에 Movie가 요금 계산과 관련된 모든 책임을 갖게 하는게 차라리 나이스한 방안

### 창조자에게 객체 생성 책임을 할당하라

영화 예매 시스템 협력 관계의 최종 결과물은 Reservation 인스턴스, 이 인스턴스를 어떤 객체에게 생성하도록 하는 책임이 필요

CREATOR (창조자) 패턴을 통해 객체를 생성할 책임을 할당하는데 사용할 수 있다.

객체 A를 생성해야 할 때 어떤 객체가 이 책임을 갖는게 좋을 지 판단하는 조건

1. 객체 B가 객체 A를 포함하거나 참조
2. 객체 B가 객체 A를 기록한다  (Q. 기록한다가 setter의 의미겠지?)
3. 객체 B가 객체 A를 긴밀하게 사용.
4. 객체 B가 객체 A를 초기화하는데 필요한 데이터를 들고있음. (B는 A의 정보전문가)

→ 이 조건들을 만족하여 CREATOR를 찾을 경우에 객체 B는 객체 A와 강하게 결합됨

<img width="659" height="227" alt="image" src="https://github.com/user-attachments/assets/c87a5429-e2da-4b7b-b30a-5cd985c4a414" />

Screening은 예매 정보를 생성하는데 필요한 다른 정보에 대한 정보전문가이며, Movie도 알고있음

→ Screening이 CREATOR이다!

## 3. 구현을 통한 검증

**Screening 객체 설계**

```kotlin
class Screening(
    private val movie: Movie,
    private val sequence: Int,
    private val whenScreened: LocalDateTime,
) {
    fun reserve(customer: Customer, audienceCount: Int): Reservation {
        return Reservation(customer, this, calculateFee(audienceCount), audienceCount)
    }

    fun calculateFee(audienceCount: Int): Money {
        return movie.calculateMovieFee(this).times(audienceCount)
    }
}
```

1. “예매하라” 메시지에 응답할 수 있도록 reserve 메서드 구현
2. calculateMovieFee 메시지는 Movie가 아닌 송신자인 Screening의 의도를 표현
    1. Screening이 Movie의 내부구현에 대한 지식 없이 전송할 메시지를 결정한다는 것이 중요

       → 단순히 요금 계산해~ㅋㅋ~의 의미
       → 이렇게 하면 Movie의 내부 구현을 깔쌈하게 캡슐화할 수 있음
       → 이렇게 하면 메시지가 변경되지 않는 한 Movie의 내부구현이 바뀌어도 영향 X
       → 이렇게 하면 Screening과 Movie를 느슨한 결합으로 유지할 수 있음



**Movie 객체 설계**


```kotlin
class Movie(
    private val title: String,
    private val runningTime: Duration,
    private val fee: Money,
    private val discountConditions: List<DiscountCondition>,
    private val movieType: MovieType,
    private val discountAmount: Money,
    private val discountPercent: Double,
) {
    fun calculateMovieFee(screening: Screening): Money {
        return if (isDiscountable(screening)) {
            fee.minus(calculateDiscountAmount())
        } else fee
    }

    private fun isDiscountable(screening: Screening): Boolean {
        return discountPercent.stream()
            .anyMatch(condition -> condition.isSatisfiedBy(screening))
    }

    private fun calculateDiscountAmount(): Money {
        when (movieType) {
            MovieType.AMOUNT_DISCOUNT -> calculateAmountDiscountAmount()
            MovieType.PERCENT_DISCOUNT -> calculatePercentDiscountAmount()
            MovieType.NONE_DISCOUNT -> calculateNoneDiscountAmount()
        }
    }

    private fun calculateAmountDiscountAmount(): Money = discountAmount
    private fun calculatePercentDiscountAmount(): Money = fee.times(discountPercent)
    private fun calculateNoneDiscountAmount(): Money = Money.ZERO
}

enum class MovieType {
    AMOUNT_DISCOUNT,
    PERCENT_DISCOUNT,
    NONE_DISCOUNT,
    ;
}
```

Movie는 Screening으로부터 받은 “요금을 계산하라” 메시지에 응답하기 위한 구현을 가짐

1. 할인 여부를 판단하기 위해서는 DiscountCondition 객체에게 “할인 여부를 판단하라” 메시지를 전송한다 (isSatisfiedBy)
2. 실제 할인 금액을 판단하기 위해 MovieType에 따라 적절한 구현 메서드를 가짐

**DiscountCondition 객체 설계**

```kotlin
class DiscountCondition(
    private val type: DiscountConditionType,
    private val sequence: Int,
    private val dayOfWeek: DayOfWeek,
    private val startTime: LocalDateTime,
    private val endTime: LocalDateTime,
) {
    fun isSatisfiedBy(screening: Screening): Boolean {
        return if (type == DiscountConditionType.PERIOD) {
            return isSatisfiedByPeriod(screening)
        } else isSatisfiedBySequence(screening)
    }

    private fun isSatisfiedByPeriod(screening: Screening): Boolean {
        return dayOfWeek.equals(screening.whenScreened.dayOfWeek)
                && startTime <= screening.whenScreened.toLocalTime() as ChronoLocalDateTime<*>?
                && endTime.isAfter(screening.whenScreened.toLocalTime() as ChronoLocalDateTime<*>?) >= 0
    }

    private fun isSatisfiedBySequence(screening: Screening): Boolean {
        return sequence == screening.sequence
    }
}

enum class DiscountConditionType {
    PERIOD,
    SEQUENCE,
    ;
}
```

1. “할인 여부 판단하라” 메시지 처리를 위해 DiscountConditionType에 따라 적절한 메서드를 호출

### DiscountCondition의 문제점

하지만 DiscountCondition은 아래와 같은 문제점들을 갖게 됨

1. 새로운 할인 조건이 추가될 경우 isSatisfiedBy if-else문 수정 필요
2. 순번 조건 (Sequence)을 판단하는 로직이 수정될 경우 isSatisfiedBySequence 내부 구현을 수정해야 함 + 순번을 판단하는데 필요한 데이터가 추가될 경우 sequence도 바뀔 수 있음
3. 기간 조건 (Period)을 판단하는 로직이 수정될 경우 isSatisfiedByPeriod 내부 구현을 수정해야 함 + 기간을 판단하는데 필요한 데이터가 변경된다면 dayOfWeek,.. 같은 데이터도 바뀔 수 있음

이 경우, 하나 이상의 변경의 이유를 갖기 때문에 DiscountCondition 객체는 SRP를 만족하지 못한다고 볼 수 있음 → 응집도가 낮다? → 변경의 이유에 따라 클래스를 분리해야함!

변경의 이유를 찾는 쉬운 방법이 있나? → 몇가지 패턴이 존재한다~

1. 인스턴스 변수 초기화 시점을 살펴보자!
    - 응집도가 높은 클래스일 경우 인스턴스 생성 시 모든 속성이 함께 초기화됨

      e.g. 순번 조건을 표현할때 sequence는 초기화되지만 dayOfWeek 같은건 초기화 되지 않음
      → 응집도가 낮구나!

    - 함께 초기화되는 속성을 기준을 코드를 분리해야 한다~
2. 메서드들이 인스턴스 변수를 사용하는 방식을 살펴보자!
    - 모든 메서드가 객체의 모든 속성을 사용하고 있다면 응집도가 높다고 볼 수 있다.

      e.g. isSatisfiedBySequence는 sequence는 사용하지만 dayOfWeek 같은건 사용하지 않음 → 응짐도가 낮구나!

    - 속성 그룹과 해당 그룹을 접근하는 메서드 그룹을 기준으로 코드를 분리해야 한다

      → sequence와 period를 결국 분리하는게 맞다~ → 자연스럽게 해결됨


### DiscountCondition 해결 방법 (타입 분리)

SequenceCondition과 PeriodCondition으로 분리하자!

```kotlin
class PeriodCondition(
    private val dayOfWeek: DayOfWeek,
    private val startTime: LocalDateTime,
    private val endTime: LocalDateTime,
) {
    private fun isSatisfiedByPeriod(screening: Screening): Boolean {
        return dayOfWeek.equals(screening.whenScreened.dayOfWeek)
                && startTime <= screening.whenScreened.toLocalTime() as ChronoLocalDateTime<*>?
                && endTime.isAfter(screening.whenScreened.toLocalTime() as ChronoLocalDateTime<*>?) >= 0
    }
}

class SequenceCondition(
    private val sequence: Int,
) {
    private fun isSatisfiedBySequence(screening: Screening): Boolean {
        return sequence == screening.getSequence()
    }
}
```

이렇게 분리하면 위에 문제점들을 모두 개선할 수 있게 된다.
→ 변경의 이유가 하나로 분리된 응집도 높은 객체

하지만, Movie와 협력하는 클래스는 기존에는 DiscountCondition이였기 때문에 협력이 불가능한 상태임

Movie안에 SequenceCondition과 PeriodCondition을 넣어버려? ㄴㄴ~

1. 이렇게 되면 두 객체와 강결합이 생겨버림
    - 오히려 결합도 1에서 2로 늘어버린 셈이라 전체적인 결합도가 높아지게 됨
2. 새로운 할인조건을 추가하기가 더 어려워짐
    - 할인조건 리스트를 Movie에 추가해야하고, 할인 조건을 만족하냐 안하냐에 대한 로직도 필요
    - 코드가 오히려 더 번잡해짐

### DiscountCondition 해결 방법 (다형성으로 분리)

Movie객체 입장에서 SequenceCondition이나 PeriodCondition이나 뭐가 뭔지는 관심이 없음

중요한건 이 둘다 결국 할인 조건에 대한 객체들이기 때문에 Movie는 할인 조건에 대한 것만 알면 됨

구체적으로 뭐가 뭔지는 몰라도 된다~

SequenceCondition, PeriodCondition에 각각의 책임은 할당되었고, 협력관계에서 역할이 들어갈 차례 두둥

<img width="659" height="158" alt="image" src="https://github.com/user-attachments/assets/a18800b6-b925-475c-9d04-8842e4846219" />

DiscountCondition을 역할로 추가해버리자!

이걸 통해서 구체적인 타입들(Period, Sequence)를 추상화할 수 있다

<img width="657" height="204" alt="image" src="https://github.com/user-attachments/assets/57a3328e-489e-435a-8b82-8ef6bac90c54" />

객체의 암시적 타입(SEQUENCE, PERIOD)에 따라 행동을 분기해야 한다면
암시 타입을 명시적인 클래스(SequenceCondition, PeriodCondition)로 정의하고 행동을 나눔으로서 응집도 문제를 해결할 수 있다.

**GRASP - POLYMORPHISM(다형성) 패턴**

객체의 타입에 따라 변하는 행동이 있다면 타입을 분리하고 변화하는 행동을 각 타입의 책임으로 할당하는 것

if-else나 when문같은 조건 논리로 설계한다면 새로운 변화가 일어날 때 조건 논리를 수정해야하는 상황이 발생한다. → 수정이 어렵고 변경에 취약하게 만드는 방식

*이게 창환이형이 말했던 거였던 것 같네 ㅋㅋ*

### 변경으로부터 보호하기

새로운 할인 조건이 추가된다면?

DiscountCondition의 역할은 Movie에서 구체 조건들을 감추는 것이다.

즉, DiscountCondition이라는 추상화가 구체 타입을 캡슐화 하는 것

즉, 하나 더 추가되더라도 Movie에는 영향이 가지 않는다~

이렇게 변경을 캡슐화하도록 책임을 할당하는 것을 GRASP - PROTECTED VARIATIONS(변경 보호) 패턴이라고 함

인터페이스를 통해 변경을 캡슐화 하는 것은 설계에서 결합도와 응집도를 높이는 아주 좋은 방법이다

### Movie 개선

기존 Movie 객체 코드

```kotlin
class Movie(
    private val title: String,
    private val runningTime: Duration,
    private val fee: Money,
    private val discountConditions: List<DiscountCondition>,
    private val movieType: MovieType,
    private val discountAmount: Money,
    private val discountPercent: Double,
) {
    fun calculateMovieFee(screening: Screening): Money {
        return if (isDiscountable(screening)) {
            fee.minus(calculateDiscountAmount())
        } else fee
    }

    private fun isDiscountable(screening: Screening): Boolean {
        return discountPercent.stream()
            .anyMatch(condition -> condition.isSatisfiedBy(screening))
    }

    private fun calculateDiscountAmount(): Money {
        when (movieType) {
            MovieType.AMOUNT_DISCOUNT -> calculateAmountDiscountAmount()
            MovieType.PERCENT_DISCOUNT -> calculatePercentDiscountAmount()
            MovieType.NONE_DISCOUNT -> calculateNoneDiscountAmount()
        }
    }

    private fun calculateAmountDiscountAmount(): Money = discountAmount
    private fun calculatePercentDiscountAmount(): Money = fee.times(discountPercent)
    private fun calculateNoneDiscountAmount(): Money = Money.ZERO
}

enum class MovieType {
    AMOUNT_DISCOUNT,
    PERCENT_DISCOUNT,
    NONE_DISCOUNT,
    ;
}
```

이친구의 문제점도 DiscountCondition과 마찬가지

금액 할인과 비율 할인 두가지를 하나의 클래스에서 구현하는 문제점이 있다 ~

이것도 POLYMORPHISM과 PROTECTED VARIATIONS로 해결해라~

```kotlin
abstract class Movie(
    private val title: String,
    private val runningTime: Duration,
    private val fee: Money,
    private val discountConditions: List<DiscountCondition>,
) {

    fun calculateMovieFee(screening: Screening): Money {
        return if (isDiscountable(screening)) {
            fee.minus(calculateDiscountAmount())
        } else fee
    }

    private fun isDiscountable(screening: Screening): Boolean {
        return discountConditions.stream()
            .anyMatch { condition -> condition.isSatisfiedBy(screening) }
    }

    abstract fun calculateDiscountAmount(): Money
}
```

DiscountCondition은 역할을 수행할 클래스들 사이에 구현을 공유할게 없기 때문에 인터페이스 사용

Movie는 금액 계산과 할인 가능 여부를 공유해야 하기 때문에 추상 클래스로 만듦

```kotlin
class AmountDiscountMovie(
    val title: String,
    val runningTime: Duration,
    val fee: Money,
    val discountConditions: List<DiscountCondition>,
    private val discountAmount: Money
) : Movie(title, runningTime, fee, discountConditions) {

    override fun calculateDiscountAmount(): Money {
        return discountAmount
    }
}

class AmountDiscountMovie(
    val title: String,
    val runningTime: Duration,
    val fee: Money,
    val discountConditions: List<DiscountCondition>,
    private val discountAmount: Money
) : Movie(title, runningTime, fee, discountConditions) {

    override fun calculateDiscountAmount(): Money {
        return discountAmount
    }
}

class NonDiscountMovie() : Movie() { .. }
```

이렇게 설계하면 Movie 또한 변경의 이유를 하나만 갖게 되면서 응집도를 높이게 됨

또한, 각 클래스들이 다른 클래스와 최대한 느슨하게 결합되어 있다

→ 책임을 중심으로 협력을 설계할 때 얻을 수 있는 혜택들임!

<img width="654" height="210" alt="image" src="https://github.com/user-attachments/assets/0860fd32-2a1a-4fa5-8cb6-d8761deb4565" />

**도메인의 구조가 코드의 구조를 이끈다!**

도메인 모델은 단순히 설계에 필요한 용어를 제공하는 것을 넘어 코드 구조에도 영향을 미친다

변경 또한 도메인 모델의 일부라는 점!

Movie와 DiscountConditiond에는 할인 조건과 할인 정책이 변경될 수 있다는 것이 반영된 것

### 변경과 유연성

변경에 대비하는 방법

1. 코드를 이해하고 수정하기 쉽도록 최대한 단순하게 설계하는 것
2. 코드를 수정하지 않고도 변경을 수용할 수 있도록 유연하게 만드는 것

새로운 할인 정책이 추가될때마다 인스턴스를 생성하고, 상태를 복사하고, 코드를 추가하는 것은 번거롭다

복잡성이 높아지더라도 할인 정책 변경을 쉽게 수용할 수 있도록 코드를 유연하게 만드는 것이 좋다

→ 이때 사용하는게 상속이 아니라 합성

<img width="663" height="222" alt="image" src="https://github.com/user-attachments/assets/034fbb77-c5d6-4fe0-b423-6a7f32850d65" />

Movie의 상속 구조를 DiscountPolicy로 분리시켜 런타임에 수정 가능하도록 하는 방식으로 합성을 사용할 수 있다

```kotlin
movie = Movie("타이타닉", ~, ~, ~, AmountDiscountPolicy)
movie.changeDiscountPolicy(PercentDiscountPolicy)
```

## 4. 책임 주도 설계의 대안

객체에 책임을 할당하는 과정에서 빠르게 할 수 있는 방법은 일단 코드부터 짜는것

아무것도 없는 상태에서 책임과 협력에 대해 고민하기 보다는 일단 실행되는 코드를 작성하고 올바른 위치로 옮기는 것이다

주의할 점은 코드를 수정한 후에 겉으로 드러나는 동작이 바뀌면 안되는 것!
→ 캡슐화를 향상시키고, 응집도를 높이고, 결합도를 낮추되 동작은 그대로되어야 함

리팩토링 잘하자~

### 메서드 응집도

ReservationAgency reserve 메서드 코드가 매우 길다~!

이런 메서드는 응집도가 낮기 때문에 이해하기도 어렵고 재사용하기도 어렵고, 변경하기도 어렵다 (몬스터 메서드)

주석을 추가하는 대신 작게 분해해서 각 메서드의 응집도를 높여라

```kotlin
class ReservationAgency {

    fun reserve(screening: Screening, customer: Customer, audienceCount: Int): Reservation {
        val discountable = checkDiscountable(screening)
    }

    private fun checkDiscountable(screening: Screening): Boolean {
        return screening.getMovie().getDiscountConditions().stream()
            .anyMatch { condition -> isDiscountable(condition, screening) }
    }

    private fun isDiscountable(condition: DiscountCondition, screening: Screening): Boolean {
        if (condition.type == DiscountConditionType.PERIOD) {
            return isSatisfiedByPeriod(condition, screening)
        }

        return isSatisfiedBySequecne(condition, screening)
    }
    ..
}
```

reserve 메서드에 다 때려박는 것 보다 변경의 이유를 하나만 갖게 하고, 명확하게 작성하는 게 중요하다

클래스 길이는 길어지더라도 명확성의 가치가 더 중요하다

작은 메서드들로 분해를 하게되면 전체적인 흐름을 이해하기도 쉬워진다

또한, 각 메서드는 하나의 이유에 의해서만 변경되도록 작성한다면 응집도를 높일 수 있다.

### 객체를 자율적으로 만들자

ReservationAgency가 갖는 isDiscountable이나 isSatisfiedBySequence 같은 메서드들은 DiscountCondition의 데이터를 접근해서 수행한다

DiscountCondition을 자율적인 존재로 만들기 위해 이런 코드들은 DiscountCondition의 행동으로 옮겨야 한다

책임주도 설계에 익숙하지 않다면 데이터 중심 설계로 먼저 시작한 다음 리팩토링을 하는 것도 꽤괜이다

일단 동작하는 코드를 먼저 작성하는게 맞다 (캡슐화, 결합도, 응집도를 고려하는건 그 이후에 )

캡슐화, 결합도, 응집도를 이해하고 훌륭한 객체지향 원칙을 적용하기 위해 노력한다면 책임 주도 설계 를 단계적으로 따르지 않더라도 나이스한 코드를 얻을 수있다~
