## 협력과 메시지

### 클라이언트-서버 모델

두 객체 사이의 협력 관계는 서버-클라이언트 모델을 따른다. 협력 안에서 메시지를 전송하는 객체를 클라이언트, 메시지를 수신하는 객체를 서버라고 부른다. 협력은 클라이언트가 서버의 서비스를 요청하는 단방향 상호작용이다.

일반적으로 객체는 협력하는 동안 클라이언트와 서버의 역할을 동시에 수행한다. 어떤 메시지를 수신 받는지도 중요하지만, 어떤 메시지를 전송하는지도 적절한 객체 설계에 중요한 요소이다.

### 메지와 메시지 전송

```kotlin
isSatisfiedBy(screening)
```

- 오퍼레이션명(operation name): isStaisfiedBy
- 인자(argument): screening
- 메시지: isSatisfiedBy(screening)
- 메시지 전송: 수신자.isSatisfiedBy(screening)

### 메시지와 메서드

**메서드**

- 메시지를 수신했을 때 실제로 실행되는 함수 또는 프로시저

함수 호출이나 프로시저 호출과 같이 컴파일 시점과 실행 시점에 동일한 동작을 하는 것과는 다르게, 객체는 메시지와 메서드라는 두 가지 서로 다른 개념을 **실행 시점에 연결**해야 하기 때문에 컴파일 시점과 실행 시점의 의미가 달라질 수 있다.

이는 시지와 메서드의 구분은 **메시지 전송자와 메시지 수신자가 느슨하게 결합**될 수 있게 한다.

- 이는 메시지 전송자는 어떤 메시지를 전송해야 하는지만 알면 되고, 메시지 수신자는 메시지를 처리하기 위해 필요한 메서드를 스스로 결정할 수 있는 자율권을 부여한다.

→ 실행 시점에 메시지와 메서드를 바인딩하는 메커니즘은 **두 객체 사이의 결합도를 낮춤**으로써 유연하고 확장 가능한 코드를 작성하게 만든다.

### 퍼블릭 인터페이스와 오퍼레이션

**퍼블릭 인터페이스**

- 객체가 의사소통을 위해 외부에 공개하는 메시지의 집합

**오퍼레이션**

- 퍼블릭 인터페이스에 포함된 메시지.
- 수행 가능한 어떤 행동에 대한 추상화.
- 실행하기 위해 객체가 호출될 수 있는 변환이나 정의에 관한 명세
- 예) `DiscountCondition` 인터페이스에 정의된 `isSatisfiedBy`

<img width="2048" height="549" alt="image" src="https://github.com/user-attachments/assets/9d769edf-a107-479e-b2cd-f6b3c2478629" />

### 시그니처

**시그니처**

- 오퍼레이션의 이름과 파라미터 목록을 합친 것

## 인터페이스와 설계 품질

### 디미터 법칙(Law of Demeter)

디미터 법칙을 간단하게 요약하면 객체의 내부 구조에 강하게 결합되지 않도록 협력 경로를 제한하라는 것이다.

디미터 법칙을 따르기 위해서는 클래스가 특정한 조건을 만족하는 대상에게만 메시지를 전송하도록 프로그래밍 해야 한다. 특정 조건은 다음과 같다.

- 자기 자신 (`this`)
- 자신의 필드 (멤버 변수로 직접 참조하는 객체)
- 메서드에서 생성한 객체 (로컬 변수, new로 만든 객체)
- 메서드의 매개변수 (파라미터로 전달된 객체)

**조건을 만족하지 않는 예시**

```kotlin
screening.getMovie().getDiscountconditions()
```

- 이렇게 연쇄적으로 메시지를 전송하는 코드를 **기차 충돌(train wreck)**이라고 부른다.
- 기차 충돌은 클래스의 내부 구현이 외부로 노출되었을 때 나타나는 형태로, 메시지 전송자가 메시지 수신자의 내부 정보를 자세히 알게된다.

### 묻지 말고 시켜라

**묻지 말고 시켜라 법칙(Tell, Don’t Ask)**

- 훌륭한 메시지는 객체의 상태에 관해 묻지 말고 원하는 것을 시켜야 한다.

메시지 전송자는 메시지 수신자의 상태를 기반으로 결정을 내린 후, 메시지 수신자의 상태를 바꿔서는 안된다. 이러한 로직은 메시지 수신자가 담당해야할 책임일 것이다. 또한 객체의 외부에서 해당 객체의 상태를 기반으로 결정을 내리는 것은 객체의 캡슐화를 위반한다.

묻지 말고 시켜라 원칙을 따르는 것의 장점

→ 객체의 정보를 이용하는 행동을 객체의 외부가 아닌 **내부에 위치**시키기 때문에 자연스럽게 정보와 행동을 동일한 클래스 안에 두게 된다 → 응집도가 높아진다

### 의도를 드러내는 인터페이스

켄트 백은 메서드를 명명하는 두 가지 방법을 설명했다.

**메서드가 작업을 어떻게 수행하는지를 나타내는 방법**

```kotlin
class PeriodCondition {
	fun isSAtisfiedByPeriod(screening: Screening): Boolean
}

class SequenceCondition {
	fun isSAtisfiedBySequence(screening: Screening): Boolean
}
```

이러한 스타일은 좋지 않은데, 두 가지 이유가 있다.

- 클라이언트 관점에서 동일한 작업을 수행하지만, 메서드 이름이 다르기 때문에 내부 구현을 정확히 이해하지 못한다면 두 메서드가 동일한 작업을 수행한다는 사실을 알아채기 어렵다.
- 메서드 수준에서 캡슐화를 위반한다.
    - PeriodCondition을 사용하는 코드를 SequenceCondition을 사용하도록 변경하려면 단순히 참조하는 객체를 변경하는 것뿐만 아니라 호출하는 메서드를 변경해야 한다.
    - 할인 여부를 판단하는 방법이 변경된다면 메서드의 이름을 변경해야 한다. 이는 메시지를 전송하는 클라이언트의 코드를 함께 변경해야 한다는 것을 의미하며, 변경에 취약하다.

**무엇을 하는지를 드러내는 방법**

```kotlin
class PeriodCondition {
	fun isSAtisfiedBy(screening: Screening): Boolean
}

class SequenceCondition {
	fun isSAtisfiedBy(screening: Screening): Boolean
}
```

- 두 메서드가 동일한 목적을 가진다는 것을 메서드의 이름을 통해 명확하게 표현한다.
- 인터페이스로 묶어 두 메서드를 동일한 방식으로 사용할 수 있게 된다.

### 함께 모으기

```kotlin
audience.getBag().minusAmount(ticket.getFee())
```

- 해당 코드는 `Audience` 뿐만 아니라 `Bag`에 대해서도 메시지를 전송한다. 이는 `Audience`의 내부 구조에 대해서도 결합된다는 뜻이다.

디미터 법칙을 위반한 코드를 수정하는 일반적인 방법은 `Audience`와 `TicketSeller`의 내부 구조를 묻는 대신 `Audience`와 `TicketSeller`가 **직접 자신의 책임을 수행하도록 시키는 것**이다.

**묻지 말고 시켜라**

```kotlin
class Audience {
	fun setTicket(ticket: Ticket): Long {
		if (bag.hasInvitation()) {
			bag.setTicket(ticket)
			return 0L
		} else {
			bag.setTicket(ticket)
			bag.minusAmount(ticket.getFee())
			return ticket.getFee()
		}
	}
}
```

- `Audience`에게 `setTicket` 메서드를 추가하고, 스스로 티켓을 가지도록 만들었다.
- 하지만 `Audience`가 `Bag`에게 원하는 일을 시키기 전에 `hasInvitation` 메서드를 이용해 초대권을 가지고 있는지 묻고 있다. 따라서 `Audience`는 여전히 디미터 법칙을 위반한다.

```kotlin
class Bag {
	fun setTicket(ticket: Ticket): Long {
		if (hasInvitaiton()) {
			this.ticket = ticket
			return 0L
		} else {
			this.ticket = ticket
			minusAmount(ticket.getFee())
			return ticket.getFee()
		}
	}
	
	fun hasInvitation(): Boolean { ... }
	fun minusAmount(amount: Amount) { ... }
}
```

- `Audience`의 `setTicket` 메서드 구현을 `Bag`의 `setTicket` 메서드로 이동시켰고, 디미터 법칙을 준수하는 `Audience`를 얻었다.

**인터페이스에 의도를 드러내자**

안타깝게도 `Audience`와 `Bag`의 `setText`는 클라이언트의 의도를 명확하게 드러내지 못한다. 미묘하게 다른 의미를 가진 세 메서드가 같은 이름을 가진다면, 클라이언트 개발자를 혼란스럽게 만들 확률이 높다. 따라서 클라이언트의 의도가 분명하게 드러나도록 객체의 퍼블릭 인터페이스를 개선해야 한다.

```kotlin
class Audience {
	fun buy(ticket: Ticket): Long
}

class Bag {
	fun holde(ticket: Ticket): Long
}
```

**디미터 법칙**

- 객체 간의 협력을 설계할 때 캡슐화를 위반하는 메시지가 인터페이스에 포함되지 않도록 제한한다.

**묻지 말고 시켜라 원칙**

- 디미터 법칙을 준수하는 협력을 만들기 위한 스타일을 제시한다.

**의도를 드러내는 인터페이스 원칙**

- 객체의 퍼블릭 인터페이스에 어떤 이름이 드러나야 하는지에 대한 지침을 제공함으로써 코드의 목적을 명확하게 커뮤니케이션할 수 있게 해준다.

## 원칙의 함정

원칙이 무조건 적으로 옳은 것은 아니다. 상황에 따라 트레이드오프 해야 한다.

### 디미터 법칙은 하나의 도트를 강제하는 규칙이 아니다.

```kotlin
IntStream.of(1, 15, 20, 3, 9).filter(x -> x > 10).distinct().count();
```

- 여러 개의 도트를 사용한 위 코드는 디미터 법칙을 위반한 것이 아니다.
- 디미터 법칙은 결합도와 관련된 것이며, 이 결합도가 문제가 되는 것은 객체의 내부 구조가 외부로 노출되는 경우로 한정된다.
- IntStream을 다른 IntStream으로 변환할 뿐, 객체를 둘러싸고 있는 캡슐은 그대로 유지된다.

### 결합도와 응집도의 충돌

묻지말고 시켜라와 디미터 법칙을 준수하는 것이 항상 긍정적인 결과로만 귀결되는 것은 아니다. 모든 상황에서 맹목적으로 위임 메서드를 추가하면 같은 퍼블릭 인터페이스 안에 어울리지 않는 오퍼레이션들이 공존하게 된다. 결과적으로는 객체는 상관 없는 책임들을 한꺼번에 떠안게 되기 때문에 결과적으로 응집도가 낮아진다.

아래 코드를 살펴보자.

```kotlin
class PeriodCondition(
	private val dayOfWeek: DayOfWeek,
	private val startTime: LocalTime,
	private val endTime: LocalTime,
) {
	fun isSatisfiedBy(screening: Screening): Boolean {
		return dayOfWeek == screening.whenScreened.dayOfWeek &&
			startTime >= screening.whenScreened.toLocalTime() &&
			endTime <= screening.whenScreened.toLocalTime ()
	}
}
```

`isSatisfiedBy` 메서드는 `screening`에게 질의한 상영 시작 시간을 이용해 할인 여부를 결정한다. 이 코드는 얼핏 보기에는 `Screening`의 내부 상태를 가져와서 사용하기 때문에 캡슐화를 위반한 것으로 보인다.

따라서 이 메서드를 묻지 말고 시켜라 스타일을 준수하는 퍼블릭 인터페이스로 바꿔보자.

```kotlin
class Screening {
	fun isSDiscountable(dayOfWeek: DayOfWeek, startTime: LocalTime, endTime: LocalTime): Boolean {
		return dayOfWeek == whenScreened.dayOfWeek &&
			startTime >= whenScreened.toLocalTime() &&
			endTime <= whenScreened.toLocalTime ()
	}
}

class PeriodCondition: Discountcondition {
	fun isSatisfiedBy(screening: Screening): Boolean {
		return screening.isDiscountable(dayOfWeek, startTime, endTime)
	}
}
```

이렇게 되면 `Screening`은 자신이 담당해야 하는 책임이 아닌 ‘할인 조건 판단’ 책임을 떠안게 된다. `Screening`이 **직접 할인 조건을 판단하게 되면 객체의 응집도가 낮아진다.** 반면 `PeriodCondition`의 본질적인 책임을 남이 수행하는 꼴이 된다.

게다가 `Screening`에서 `PeriodCondition`의 인스턴스 변수를 인자로 받기 때문에 두 객체간의 결합도가 높아진다. 따라서 `Screening`의 캡슐화를 향상시키는 것보다 `Screening`의 응집도를 높이고 `Screening`과 `PeriodCondition` 사이의 결합도를 낮추는 것이 전체적인 관점에서 더 좋은 방법이다.

**묻는 것 외에는 달리 방법이 없는 경우**

다음 코드에서 Movie에게 묻지 않고도 movies 컬렉션에 포함된 전체 영화의 가격을 계산할 수 있는 방법이 있을까?

```kotlin
movies.forEach { movie
	total += movie.getFee()
}
```

물으려는 객체가 정말로 데이터인 경우도 있다. 디미터 법칙의 위반 여부는 **묻는 대상이 객체인지, 자료 구조인지**에 달려있다. 객체는 내부 구조를 숨겨야 하므로 디미터 법칙을 따르는 것이 좋지만, 자료 구조라면 당연히 내부를 노출해야 하므로 디미터 법칙을 적용할 필요가 없다.

## 명령-쿼리 분리 원칙

**루틴**

- 어떤 절차를 묶어 호출 가능하도록 이름을 부여한 기능 모듈
- 루틴은 다시 프로시저와 함수로 구분할 수 있다.

**프로시저**

- 정해진 절차에 따라 내부의 상태를 변경하는 루틴의 한 종류
- 프로시저는 부수효과를 발생시킬 수 있지만 값을 반환할 수 없다.

**함수**

- 어떤 절차에 따라 필요한 값을 계산해서 반환하는 루틴의 한 종류
- 함수는 값을 반환할 수 있지만 부수효과를 발생시킬 수 없다.

**명령**

- 객체의 상태를 수정하는 오퍼레이션
- 명령은 프로시저와 대응되며 반환값을 가질 수 없다.

**쿼리**

- 객체와 관련된 정보를 반환하는 오퍼레이션
- 쿼리는 함수와 대응되며 상태를 변경할 수 없다.

### 반복 일정의 명령과 쿼리 분리하기

명령과 쿼리를 뒤섞으면 실행 결과를 예측하기가 어려워 질 수 있다. 겉으로 보기에는 쿼리처럼 보이지만 내부적으로 부수효과를 가지는 메서드는 이해하기 어렵고, 잘못 사용하기 쉬우며, 버그를 양산하는 경향이 있다.

가장 깔끔한 해결책은 명령과 쿼리를 명확하게 분리하는 것이다.

```kotlin
// ❌ 잘못된 예시
class Event {
	// 쿼리같이 생김
	fun isSatisfied(schedule: RecurringSchedule): Boolean {
		...
		reschedule(schedule) // 하지만 명령이 포함되어 있다.
		return false
	}
	
	private fun reschedule(shcedule: RecurringSchedule) {
		...
	}
}

// ✅ 좋은 예시
class Event {
	fun isSatisfied(schedule: RecurringSchedule): Boolean {
		...
		// reshcedule을 제거하면서 명령과 쿼리를 분리
		return false
	}
	
	// 퍼블릭 인터페이스로 변경
	fun reschedule(shcedule: RecurringSchedule) {
		...
	}
}

// 사용처
if (!event.isSatisfied(schedule)) {
	event.reschedule(shcedule)
}
```

### 명령-쿼리 분리와 참조 투명성

**참조 투명성**

- 어떤 표현식 e가 있을 때 모든 e를 e의 값으로 바꾸더라도 결과가 달라지지 않는 특성

함수는 내부에 부수효과를 퐇마할 경우 동일한 인자를 전달하더라도 부수효과에 의해 그 결괏값이 매번 달라질 수 있다. 하지만 쿼리는 부수효과가 없기 때문에 몇 번이고 반복적으로 호출하더라도 객체의 상태를 바꾸지 않는다.

이는 쿼리의 결과를 예측하기 쉬워지고, 쿼리들의 순서를 자유롭게 변경할 수도 있다.

객체지향 패러다임은 부수효과를 기반으로 하기 때문에, 참조 투명성은 예외에 가깝다. 하지만 명령-쿼리 분리 원칙을 사용하면 참조 투명성의 혜택을 일부 누릴 수 있게 된다.
