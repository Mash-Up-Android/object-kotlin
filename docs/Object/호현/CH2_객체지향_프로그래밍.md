## 1. 영화 예매 시스템

### 요구사항

**영화**

- 영화에 대한 기본 정보 포함

**상영**

- 관객들이 영화를 관람하는 사건
- 영화는 한 개지만 상영은 하루에 한 번 이상일 수 있음

**할인 조건**

- 가격의 할인 여부 결정
- **순서 조건**
    - 상영 순번을 이용해 할인 여부를 결정
    - 순서 조건이 10인 경우 매일 10번째로 상영되는 영화를 예매한 사람들에게 할인
- **기간 조건**
    - 요일, 시작 시간, 종료 시간 세 부분으로 포함되며, 해당 기간안에 포함되면 할인됨

**할인 정책**

- 할인 요금을 결정
- **금액 할인 정책**
    - 예매 요금에서 일정 금액 할인
- **비율 할인 정책**
    - 정가에서 일정 비율의 요금을 할인
- 영화 별로 하나의 할인 정책만 할당 가능

## 2. 객체지향 프로그래밍을 향해

### 협력, 객체, 클래스

객체지향은 단순 클래스를 정의하는 것이 아니다. 객체지향은 말 그대로 객체를 지향하는 것이다. 객체지향은 클래스가 아닌 객체에 초점을 맞출 때에만 얻을 수 있으며, 다음 두 가지에 집중해야 한다.

- 어떤 클래스가 필요한지를 고민하기 전에 어떤 객체들이 필요한지 고민하라.
    - 객체가 어떤 상태와 행동을 가지는지 먼저 고민하고 클래스의 윤곽을 잡아야 한다.
- 객체를 독립적인 존재가 아니라 기능을 구현하기 위해 협력하는 공동체의 일원으로 봐야 한다.
    - 객체는 고립된 존재가 아닌 협력에 참여하는 협력자이다.

### 도메인의 구조를 따르는 프로그램 구조

객체지향 패러다임의 큰 장점중 하나는 요구사항과 프로그램을 객체라는 동일한 관점에서 바라볼 수 있는 것이다. 도메인의 개념과 관계를 반영하여 프로그램을 구조화 하면, 프로그램의 구조를 이해하고 예상하기 쉽게 만들 수 있다.

<img width="1246" height="272" alt="image" src="https://github.com/user-attachments/assets/e1bfe071-a338-43a3-a64f-8d9e4db009d3" />

- 위 구조에서도 도메인의 개념이 객체들과의 관계로 잘 표현되어 있다.

### 클래스 구현하기

클래스의 내부와 외부를 분리하는 것의 장점은 다음과 같다.

- 경계의 명확성이 객체의 자율성을 보장한다.
- 프로그래머에게 구현의 자유를 제공한다.

**자율적인 객체**

- 객체지향의 핵심은 스스로 상태를 관리하고, 판단하고, 행동하는 자율적인 객체들의 공동체를 구성하는 것이다. 외부에서 객체의 간섭을 최소화하여, 객체에게 원하는 것을 요청하고 객체가 스스로 최선의 방법을 결정할 수 있어야 한다.
- 캡슐화와 접근제어는 외부에서 접근 가능한 **퍼블릭 인터페이스(pulbic interface)**와 외부에서 접근 불가하고 내부에서만 접근 가능한 **구현(implementation)** 두 부분으로 나눌 수 있다. 이를 인터페이스와 구현의 분리 원칙이라고 한다.

**프로그래머의 자유**

- 프로그래머의 역할을 클래스 작성자와, 클라이언트 프로그래머로 구분하는 것이 유용하다.
- 클래스 작성자는 클라이언트 프로그래머에게 필요한 부분만 공개하고, 나머지는 숨겨야한다. 클라이언트 프로그래머가 숨겨 놓은 부분에 마음대로 접근할 수 없도록 방지함으로써, 클라이언트 프로그래머에 대한 영향을 걱정하지 않고도 내부 구현을 마음대로 바꿀 수 있다. 이를 **구현 은닉**이라고 부른다.
- 클라이언트 프로그래머는 내부 구현은 신경쓰지 않고도 클래스를 사용할 수 있기 때문에, 머릿속에 담아둬야 하는 지식의 양을 줄일 수 있다.

### 협력하는 객체들의 공동체

1. **도메인의 의미를 풍부하게 표현하라**

```kotlin
class Screening {
	fun calculateFee(audienceCount: Int): Money {
		return movie.caculateMovieFee(thos).times(audienceCount)
	}
}
```

- 금액을 구현하기 위해 Long 타입이 아닌 Money 타입을 사용하였다.
    - Long 타입은 변수의 크기, 연산자의 종류와 관련된 구현 관점의 제약은 표현할 수 있지만, 저장하는 값이 금액과 관련돼 있다는 의미를 전달할 수 없다.
    - 또한 금액 관련 로직이 서로 다른 곳에 중복되어 구현되는 것을 막을 수 없다.
    - Money 타입은 코드의 의미를 명시적이고 분명하게 표현하였다. 개념을 명시적으로 표현하는 것은 전체적인 설계의 명확성과 유연성을 높이는 첫걸음이다.
1. **협력의 관점에서 보아야 한다.**

영화를 예매하는 시스템을 구현하기 위해서는, Screening, Movie, Reservation 객체들은 서로의 메서드를 호출하며 상호작용한다. 이처럼 어떤 기능을 구현하기 위해 객체들 사이에 이뤄지는 상호작용을 **협력**이라고 부른다.

객체지향 프로그램을 작성할 때는 협력의 관점에서 어떤 객체가 필요한지 결정하고, 객체들의 공통 상태와 행위를 구현하기 위해 클래스를 작성한다. 따라서 협력에 대한 개념을 간략하게라도 살펴보는 것이, 다음의 설계 혹은 구현을 하는데 도움이 될 것이다.

### 협력에 관한 짧은 이야기

객체 내부 상태는 외부에서 접근하지 못하도록 감추지만, 대신 외부에 공개하는 퍼블릭 인터페이스를 통해 내부 상태에 접근할 수 있도록 허용한다.

객체가 다른 객체와 상호작용하는 방법은 **메시지 전송** 하는 것이고, 요청을 받은 객체는 **메시지를 수신**했다고 한다.

메시지를 수신한 객체는 자율적으로 메시지 처리할 방법을 결정하는데, 수신된 메시지를 처리하기 위한 방법을 **메서드**라고 한다.

## 3. 할인 요금 구하기

### 할인 요금 계산을 위한 협력 시작하기

```kotlin
class Movie(
	private val title: String,
	private val runningTile: Duration,
	val fee: Money,
	private val discountPolicy: DiscountPolicy,
) {
	fun calculateMovieFee(screening: Screening): Money {
		return fee.minus(discountPolicy.caculateDiscountAmount(screening))
	}
}
```

- 할인 요금 정책은 `금액 할인 정책`과 `비율 할인 정책`이 존재한다. 하지만 해당 코드에서는 어디에도 할인 정책을 판단하는 코드는 존재하지 않는다.
- 단지 `discountPolicy`에게 메시지를 전송할 뿐이다.
- 이 코드에는 **상속**, **다형성** 이라는 개념과 **추상화** 라는 원리가 숨겨져 있기 때문이다.

### 할인 정책과 할인 조건

**할인 정책**

```kotlin
abstract class DiscountPolicy(vararg conditions: DiscountCondition) {
	
	fun calculateDiscountAmount(screening: Screening): Money {
		conditions.forEach {
			if (it.isSatisfiedBy(screening)) {
				return getDiscountAmount(screening)
			}
			return Money.ZERO
		}
	}
	
	abstract protected fun getDiscountAmount(screening: SCreening): Money
}
```

- 할인 요금 정책은 `금액 할인 정책`과 `비율 할인 정책`이 존재한다. 두 가지 할인 정책을 `AmountDiscountPolicy`와 `PercentDiscountPolicy` 라는 클래스로 구현했다.
- 또한 두 클래스 사이의 중복 코드를 제거하기 위해 `DiscountPolicy` 를 추상 클래스를 구현했다.
- **Template Method 패턴**
    - 부모클래스에 기본적인 알고리즘의 흐름을 구현하고, 중간에 필요한 처리를 자식 클래스에게 위임하는 디자인 패턴
    - `DiscountPolicy`에 할인 여부와 요금 계산에 필요한 흐름은 정의하지만, 실제로 요금을 계산하는 부분은 추상 메서드인 `getDiscountAmount` 에게 위임한다.

```kotlin
class AmountDiscountPolicy(
	private val discountAmount: Money,
	vararg conditions: Conditions,
): DiscountPolicy(conditions) {

	override protected getDiscountAmount(screening: Screening): Money {
		return discountAmount
	}
}

class ParcentDiscountPolicy(
	private val percent: Double,
	vararg conditions: Condition,
): DiscountPolicy(conditions) {
	
	override protected getDiscountAmount(screening: Screening): Money {
		return screening.getMovieFee().times(percent)
	} 
}
```

**할인 조건**

```kotlin
interface DiscountCondition {
	fun isSatisfiedBy(screening: Screening): Boolean
}
```

- DiscountCondition은 인터페이스를 이용해 선언하였다.
- isSatisfiedBy 함수는 조건의 만족에 따라 Boolean값을 반환하는 함수이다.

```kotlin
class SequenceCondition(
	private val sequence: Int
) : DiscountCondition {

	override fun isSatisfiedBy(screening: Screening): Boolean {
		return screening.isSequence(sequence)
	}
}
```

- 순번을 이용하여 할인 여부를 판단하는 할인 조건

```kotlin
class PeriodCondition(
	private val dayOfWeek: DayOfWeek,
	private val startTime: LocalTime,
	private val endTime: LocalTime
): DiscountCondition {
	
	override fun isSatisfiedBy(screening: Screening): Boolean {
		return screening.getStartTime().getDayOfWeek().equals(dayOfWeek) &&
			startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 &&
			endTime.compareTo(screening.getStartTime().toLocalTime()) >= 0
	}
}
```

- 상영 시작 시간이 특정한 기간 안에 포함되는지 여부를 판단하는 할인 조건

다이어그램으로 나타내면 아래와 같다.

<img width="2048" height="437" alt="image" src="https://github.com/user-attachments/assets/68779c31-abac-4aef-9f29-64d14b4906ac" />

### 할인 정책 구성하기

요구 사항을 바탕으로 객체들의 관계를 살펴보자.

하나의 영화에 대해 단 하나의 할인 정책만 설정할 수 있지만, 할인 조건의 경우 여러 개를 적용할 수 있다. 이와 같은 제약을 Movie와 DiscountPolicy 클래스에서 강제하였다.

```kotlin
class Movie(
	...
	discountPolicy: DiscountPolicy // 하나의 할인 정책만 설정 가능
)

abstract class DiscountPolicy(vararg conditions: DiscountCondition) { // 여러 개의 조건 설정 가능
	...
}
```

이처럼 생성자의 파라미터 목록을 이용해 초기화에 필요한 정보를 전달하도록 강제하면, 올바른 상태를 가진 객체의 생성을 보장할 수 있다.

이제 위 내용들을 바탕으로 영화 ‘아바타’에 대한 할인 정책과 할인 조건을 설정한다면 다음과 같을 것이다.

```kotlin
val avatar = Movie(
	title = "아바타",
	duration = Duration.ofMinutes(120),
	fee = Money.wons(10000),
	discountPolicy = AmountDiscountPolicy(
		Money.wons(800),
		SequenceCondition(1),
		SequenceCondition(10),
		PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
	)
)
```

## 4. 상속과 다형성

### 컴파일 시간 의존성과 실행 시간 의존성

<img width="2048" height="727" alt="image" src="https://github.com/user-attachments/assets/65adab2b-81f9-4b05-a761-8996b0a4cc76" />

위 다이어그램에서 `Movie` 클래스는 정작 필요한 `AmountDiscountPolicy`나 `PercentDiscountPolicy`에는 의존하지 않는다. 그들의 추상 클래스인 `DiscountPolicy` 클래스를 의존하고 있다.

하지만 실행 시점에는 `Movie`의 인스턴스는 `AmountDiscountPolicy`나 `PercentDiscountPolicy` 를 의존하게 된다. 이는 **코드의 의존성과 실행 시점의 의존성이 서로 다를 수 있다**는 것을 보여준다.

코드의 의존성과 실행 시점의 의존성이 다를 수록 코드를 이해하기 어려워진다. 코드를 이해하기 위해서는 코드뿐만 아니라 객체를 생성하고 연결하는 부분을 찾아야 하기 때문이다.

반면 코드의 의존성과 실행 시점의 의존성이 다르면 다를수록 코드는 더 유연해지고 확장 가능해진다.

이와 같은 의존성의 양면성은 설계가 트레이드오프의 산물이라는 사실을 잘 보여준다.

### 차이에 의한 프로그래밍

상속은 기존 클래스를 기반으로 새로운 클래스를 쉽고 빠르게 추가할 수 있는 간편한 방법을 제공한다.

또한 상속을 이용하면 부모 클래스의 구현은 공유하면서도 행동이 다른 자식 클래스를 쉽게 추가할 수 있는데, 이를 차이에 의한 프로그래밍(programming by difference)라고 부른다.

### 상속과 인터페이스

상속은 단순히 부모의 메서드나 인스턴스 변수를 재사용하는 것이 아니다.

인터페이스는 객체가 이해할 수 있는 메시지의 목록을 정의한 것인데, 상속을 통해서 자식 클래스는 자신의 인터페이스에 부모 클래스의 인터페이스를 포함하게 된다. 

결과적으로 **자식 클래스는 부모 클래스와 동일한 모든 메시지를 수신**할 수 있으며, **외부에서는 자식 클래스를 부모 클래스와 동일한 타입으로 간주**할 수 있다.

<img width="1472" height="718" alt="image" src="https://github.com/user-attachments/assets/8e173bc8-e5f7-4208-81cc-a99abecbbfd0" />

예를 들어 `Movie`의 입장에서는 자신과 협력하는 객체가 어떤 클래스의 인스턴스인지는 중요하지 않다. 단지 `calculateDiscountAmount` 메시지를 수신할 수 있는지가 중요하다.

`AmountDiscountPolicy`와 `PercentDiscountPolicy` 모두 부모 클래스인 `DiscountPolicy`를 대체할 수 있다. 부모의 인터페이스를 포함하고 있기 때문이다. 이처럼 자식 클래스가 부모 클래스를 대신하는 것을 **업캐스팅**이라고 부른다.

### 다형성

다형성이란 동일한 메시지를 수신했을 때 객체의 타입에 따라 다르게 응답할 수 있는 능력을 의미한다. 따라서 다형적인 협력에 참여하는 객체들은 모두 같은 메시지를 이해할 수 있어야 한다.

다형성의 특징 중 하나는 메시지에 응답하기 위해 실행될 메서드를 컴파일 시점이 아닌 **실행 시점에 결정**한다는 것이다. 객체지향이 컴파일 시점의 의존성과 실행 시점의 의존성을 분리하고, 하나의 메시지를 선택적으로 서로 다른 메서드에 연결할 수 있는 이유가 바로 지연 바인딩이라는 메커니즘을 사용하기 때문이다.

- **지연 바인딩(lazy binding), 동적 바인딩(dynamic binding)**
    - 메지와 메서드를 실행 시점에 바인딩 하는 것
- **초기 바인딩(early binding), 정적 바인딩(static binding)**
    - 컴파일 시점에 실행될 함수나 프로시저를 결정하는 것

## 5. 추상화와 유연성

### 추상화의 힘

<img width="2048" height="388" alt="image" src="https://github.com/user-attachments/assets/9f92d8d0-51fe-4254-9dc4-228919ab58be" />

위 그림은 자식 클래스를 생략한 코드 구조를 그림으로 표현한 것이다. 이 그림은 추상화의 두 가지 장점을 보여준다.

1. **요구사항의 정책을 높은 수준에서 서술할 수 있다.**
    - 추상화를 사용하면 세부적인 내용을 무시한 채 상위 정책을 쉽고 간단하게 표현할 수 있다.
        - 위 그림을 하나의 무장으로 정리하면, “영화 예매 요금은 최대 하나의 할인 정책과 다수의 할인 조건을 이용해 계산할 수 있다”로 표현할 수 있다.
        - 때로는 ‘할인 조건’이 중요하지 않을 수 있는데, 추상화를 이용한 설계는 필요에 따라 표현의 수준을 조정할 수 있다.
    - 추상화를 이용해 상위 정책을 기술하는 것은 기본적인 애플리케이션의 협력 흐름을 기술하는 것을 의미한다.
        - 디자인 패턴이나 프레임워크 모두 추상화를 이용해 상위 정책을 정의하는 객체지향의 메커니즘을 활용하고 있다.
2. **설계가 유연해진다.**
    - 다음 내용에서 살펴보자

### 유연한 설계

추상화를 중심으로 코드의 구조를 설계하면 유연하고 확장 가능한 설계를 만들 수 있다. 그 이유는 설계가 구체적인 상황에 결합되는 것을 방지하기 때문이다.

예를들어, 할인 정책이 없는 경우에는 금액을 그대로 반환하는 기능을 추가한다고 해보자. 그러기 위해서는 단순히 아래와 같은 `NoneDiscountPolicy` 라는 할인 정책을 추가해주기만 하면 된다.

```kotlin
class NoneDiscountPolicy: DiscountPolicy {
	
	override fun getDiscountAmount(screening: Screening): Money {
		return Money.Zero
	}
}
```

유연성이 필요한 곳에 추상화를 사용하라.

### 추상 클래스와 인터페이스 트레이드오프

```kotlin
// 추상화 클래스 DiscountPolicy
abstract class DiscountPolicy(vararg conditions: DiscountCondition) {
	
	fun calculateDiscountAmount(screening: Screening): Money {
		conditions.forEach {
			if (it.isSatisfiedBy(screening)) {
				return getDiscountAmount(screening)
			}
			return Money.ZERO
		}
	}
	
	abstract protected fun getDiscountAmount(screening: SCreening): Money
}
```

앞서 `NonDiscountPolicy` 클래스와 `DiscountPolicy.caculateDiscountAmount`를 살펴보면, `NonDiscountPolicy.getDiscountAmount`의 구현과 상관 없이 할인 조건이 없을 경우 `DiscountPolicy.caculateDiscountAmount`는 0원을 반환한다.

이를 해결하기 위해서는 `DiscountPolicy`를 인터페이스로 바꾸고, `NoneDiscountPolicy`에서 `getDiscountAmount`가 아닌 `caclulateDiscountAmount`를 오버라이딩 하도록 변경하는 것이다.

```kotlin
// 인터페이스 DiscountPolicy
interface DiscountPolicy {
	fun caclutateDiscountAMount(screening: Screening): Moneny
}

abstract class DefaultDiscountPolicy: DiscountPolicy {
	...
}

class NoneDiscountPolicy: DiscountPolicy {
	override fun caclutateDiscountAMount(screening:Screening): Money {
		return Money.ZERO
	}
}
```

<img width="2048" height="969" alt="image" src="https://github.com/user-attachments/assets/ef826015-a8f8-4df0-8e27-cdd303ff2881" />

인터페이스로 변경하는 쪽의 설계가 더 좋지만, 다음과 같은 트레이드 오프는 존재한다.

- `NoneDiscountPolicy`만을 위해 인터페이스를 추가하는 것이 과할 수 있다.
    - 어쨌든 변경 전의 `NoneDiscountPolicy` 역시 할인 금액이 0원이라는 사실을 효과적으로 전달하기 때문이다.

구현과 관련된 모든 것들은 트레이드오프의 대상이 될 수 있다. 작성하는 모든 코드에는 합당한 이유가 있어야 하며, 사소한 결정이라도 트레이드오프를 통해 얻어진 결론과 그렇지 않은 결론 사이의 차이는 크다. 고민하고 트레이드오프하라.

### 코드 재사용

코드를 재사용하는 방법으로 상속보다는 합성이 더 좋은 방법이라는 말이 있다.합성이란 다른 객체의 인스턴스를 자신의 인스턴스 변수로 포함해서 재사용하는 방법이다.

Movie가 DiscountPolicy의 코드를 재사용하는 방법이 합성이다. 반면, Movie를 직접 상속 받는 방법도 있으며, 기존 방법과 기능적인 관점에서 완벽히 동일하다.

<img width="2044" height="890" alt="image" src="https://github.com/user-attachments/assets/6ae93c7c-a472-4368-8bb1-22b07d8c4ea8" />

그럼에도 상속보다 합성을 선호하는 이유는 무엇일까?

### 상속

상속은 두 가지 관점에서 설계에 악영향을 미친다.

1. **상속은 캡슐화를 위반한다.**
    - 상속을 이용하기 위해서는 부모클래스의 내부 구조를 잘 알고 있어야 한다.
        - `AmountDiscountMovie`를 구현하는 개발자는 부모클래스인 `Movie`의 `calculateFee` 메서드 안에서 `getDiscountAmount` 메서드를 호출한다는 사실을 알고 있어야 한다.
    - 부모 클래스의 구현이 자식 클래스에게 노출되기 때문에 캡슐화가 약화된다.
        - 캡슐화의 약화는 자식 클래스가 부모 클래스에 강하게 결합되도록 만든다.
        - 이는 부모 클래스 변경의 영향이 자식 클래스에게 전파된다는 것이다.
2. **설계를 유연하지 못하게 만든다.**
    - 부모-자식 관계는 컴파일 시점에 결정되기 때문에, 실행 시점에 객체의 종류를 변경하는 것이 불가능하다.
        - 예를 들어 실행 시간에 할인 정책을 변경한다고 해보자.
        - 상속을 사용한 경우 실행 시간에 `AmountDiscountMovie` 인스턴스를 `PercentDiscountMovie` 로 바꾸기 위해서는, `PercentDiscountMovie` 인스턴스를 만들고 상태를 복사해오는 방법 밖에 없다.
        - 반면 합성을 사용한 경우에는 DiscountPolicy를 동적으로 변경해주기만 하면 된다.

### 합성

합성은 상속이 가지는 두 가지 문제점을 해결한다.

- 인터페이스에 정의된 메시지를 통해서만 사용이 가능하기 때문에, 구현을 효과적으로 캡슐화할 수 있다.
- 의존하는 인스턴스를 교체하는 것이 비교적 쉽기 때문에 설계를 유연하게 만든다.

그렇다고 상속을 사용하지 말자는 뜻은 아니다. 코드를 재사용하는 경우에는 상속 보다 합성을 선호하는 것이 옳지만, 다형성을 위해 인터페이스를 재사용하는 경우에는 상속과 합성을 함께 사용할 수 밖에 없다.
