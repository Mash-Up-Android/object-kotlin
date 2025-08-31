# 책임 할당하기

## 책임 주도 설계를 향해

데이터 중심 설계에서 책임 중심의 설계로 전환하기 위해서는 다음 두 가지의 원칙을 따라야 한다.

- 데이터보다 행동을 먼저 결정하라
- 협력이라는 문맥 안에서 책임을 결정하라

### 데이터보다 행동을 먼저 결정하라

객체에게 중요한 것은 데이터가 아닌 외부에 제공하는 행동이며, 클라이언트 관점에서 객체의 행동은 책임을 의미한다.

너무 이른 시기에 데이터에 초점을 맞추는 것은 객체가 낮은 응집도를 갖고, 높은 결합도를 갖게 만든다. 데이터는 객체가 책임을 수행하기 위한 재료일 뿐이다. 따라서 객체는 행동, 즉 책임을 먼저 결정한 후에 상태를 결정해야 한다.

### 협력이라는 문맥 안에서 책임을 결정하라

객체에게 할당된 책임의 품질은 협력에 적합한 정도로 결정된다.

- 객체에게 할당된 책임이 협력에 어울리지 않는다면 그 책임은 나쁜 것이다.
- 반대로 객체 입장에서 어색한 책임이라도, 협력에 적합하다면 그 책임은 좋은 것이다.

협력에 적합한 책임을 수확하기 위해서는 객체를 결정한 후에 메시지를 선택하는 것이 아니라 메시지를 결정한 후에 객체를 선택해야 한다.  메시지를 결정하기 때문에 메시지 송신자는 메시지 수신자에 대해 어떠한 가정도 할 수 없다. 메시지 전송자의 관점에서 수신자는 깔끔하게 캡슐화 되는 것이다.

### 책임 주도 설계

다음은 3장에서 설명한 책임 주도 설계의 흐름을 나열한 것이다.

- 시스템이 사용자에게 제공해야 하는 기능인 시스템 책임을 파악한다.
- 시스템 책임을 더 작은 책임으로 분할한다.
- 분할된 책임을 수행할 수 있는 적절한 객체 또는 역할을 찾아 할당한다.
- 객체가 책임을 수행하는 도중 다른 객체의 도움이 필요한 경우 이를 책임질 적절한 객체 또는 역할을 찾는다.
- 해당 객체 또는 역할에게 책임을 할당함으로써 두 객체가 협력하게 한다.

## 책임 할당을 위한 GRASP 패턴

GRASP는 “General Responsibility Assignment Software Pattern”의 약자로 객체에게 책임을 할당할 때 지침으로 삼을 수 있는 원칙들의 집합을 패턴 형식으로 정리한 것이다.

### 도메인 개념에서 출발하기

어떤 책임을 할당해야 할 때 가장 먼저 고민해야 하는 후보는 도메인 개념이다. 도메인 안에는 무수히 많은 개념들이 존재하며 이 도메인 개념들을 책임 할당의 대상으로 사용하면 코드에 도메인의 모습을 투영하기가 좀 더 수월해진다.

단, 설계를 시작하는 단계에서는 개념들의 의미와 관계가 정확하거나 완벽할 필요가 없다. 중요한 것은 설계를 시작하는 것이지 도메인 개념들을 완벽하게 정리하는 것이 아니다. 도메인 개념을 정리하는 데 너무 많은 시간을 들이지 말고 빠르게 설꼐와 구현을 진행하라.

### 정보 전문가에게 책임을 할당하라

책임 주도 설계 방식의 첫 단계는 애플리케이션이 제공해야 하는 기능을 애플리케이션의 책임으로 생각하는 것이다. 이 책임을 애플리케이션에 대해 전송된 메시지로 간주하고 이 메시지를 책임질 첫 번째 객체를 선택해보자.

사용자에게 제공해야 하는 기능은 영화를 예매하는 것이다. 즉, 애플리케이션은 영화를 예매할 책임이 있다.

이제 이 책임을 수행하는데 필요한 메시지를, 전송할 객체의 의도를 반영해서 결정해야 한다. 이 객체(애플리케이션)이 원하는 것은 영화를 예매하는 것이고, 따라서 메시지의 이름은 *예매하라*가 적절해 보인다.

<img width="622" height="254" alt="image" src="https://github.com/user-attachments/assets/f9a0c989-bc16-4cc9-8ef4-e7e36a43b55c" />

*메시지를 수신할 적합한 객체는 누구인가?*

객체는 자신의 상태를 스스로 처리하는 자율적인 존재여야 한다. 객체의 책임을 수행하는데 필요한 상태는 동일한 객체 안에 존재해야 한다. 따라서 객체에게 책임을 할당하는 첫 번째 원칙은 책임을 수행할 정보를 알고 있는 객체에게 책임을 할당하는 것이다. **GRASP에서는 이를 INFORMATION EXPERT(정보 전문가) 패턴이라고 부른다.** 

정보 전문가 패턴에 따르면 예매하는 데 필요한 정보를 가장 많이 알고 있는 것은 `Screening`이다. 따라서 Screening에게 예매를 위한 책임을 할당하자.

<img width="1216" height="342" alt="image" src="https://github.com/user-attachments/assets/bab707cf-e2f9-429e-b5c0-7dbb5ea2d786" />

예매하라 메시지를 완료하기 위해서는 예매 가격을 계산하는 작업이 필요하다. 그런데 Screening을 가격을 계산하는 데 필요한 정보를 모르기 때문에 외부의 객체로부터 가격을 얻어야 한다. 외부에 대한 요청이 새로운 메시지가 된다. 여기서 새로운 메시지의 이름으로는 *계산하라*가 적당해 보인다.

<img width="1514" height="338" alt="image" src="https://github.com/user-attachments/assets/ac0395a4-3286-44a8-b30f-25b19d4a12e6" />

가격을 계산하는 책임을 지닐 객체를 선택할 때에도 정보 전문가 패턴을 따른다. 그러므로 메시지를 수신할 적당한 객체는 `Movie`가 될 것이다.

요금을 계산하기 위해서는 또 할인 여부를 판단하는 일이 필요하다. 따라서 `Movie`에서도 *할인 여부를 판단하라* 메시지를 전송해서 외부의 도움을 요청해야 한다.

<img width="2016" height="368" alt="image" src="https://github.com/user-attachments/assets/0d1da19a-68fa-4f2e-aea4-fc37cd32bdf1" />

할인 여부를 판단하는데 필요한 정보를 가장 많이 알고 있는 객체는 `DiscountCondition`이다. 따라서 `DiscountCondition` 에게 해당 책임을 할당하자.

<img width="2048" height="676" alt="image" src="https://github.com/user-attachments/assets/b85e44bc-c549-4f03-9a8a-72f4fde68609" />

`DiscountCondition` 는 자체적으로 할인 여부를 판단하는데 필요한 모든 정보를 알고 있기 때문에, 외부에 메시지를 전송하지 않는다.

### 높은 응집도와 낮은 결합도

방금 전에 설계한 영화 예매 시스템에서는 할인 요금을 계싼하기 위해 `Movie`가 `DiscountCondition`에 할인 여부를 판단하라 메시지를 전송한다. 그렇다면 이 설계의 대안으로 `Movie` 대신 `Screening`이 직접 `DiscountCondition`과 협력하게 하는 것은 어떨까?

<img width="2048" height="1033" alt="image" src="https://github.com/user-attachments/assets/c3601beb-caa8-4c9d-ad52-f360e7e7e039" />

두 설계는 기능적인 측면에서 차이가 없어 보인다. 하지만 응집도와 결합도에 차이가 있다. 높은 응집도와 낮은 결합도는 객체에 책임을 할당할 때 항상 고려해야 하는 기본 원리다. 두 협력 패턴 중에서 높은 응집도와 낮은 결합도가 얻을 수 있는 설계가 있다면 그 설계를 선택해야 한다.

**GRASP**에서는 이를 **LOW COUPLING(낮은 결합도) 패턴**과 **HIGH COHESION(높은 응집도) 패턴**이라고 부른다.

**LOW COUPLING 패턴 관점**

`Movie`는 이미 `DiscountCondition`과 결합되어 있기 때문에, 새로운 결합도를 추가하지 않고도 협력을 완성할 수 있다. 하지만 `Screening`이 `DiscountCondition`과 협력하게 하면 설계 전체적으로 결합도를 추가하지 않고도 협력을 완성할 수 있다.

**HIGH COHESION 패턴 관점**

`Screening`이 `DiscountCondition`과 협력해야 한다면 `Screening`은 영화 요금 계산과 관련된 책임 일부를 떠안아야 할 것이다. 이 경우 예매 요금 계산 방식이 변경될 경우 `Screening`도 함께 변경해야 한다.

반면 `Movie`의 책임은 영화 요금을 계산하는 것이다. 따라서 영화 요금을 계산하는 데 필요한 할인 조건을 판단하기 위해 `Movie`가 `DiscountCondition`과 협력하는 것은 응집도에 아무런 해도 끼치지 않는다.

### 창조자에게 객체 생성 책임을 할당하라

영화 예매 협력의 최종 결과물은 `Reservation` 인스턴스를 생성하는 것이다. 이것은 협력에 참여하는 어떤 객체에게는 `Reservation` 인스턴스를 생성할 책임을 할당해야 한다는 것을 의미한다. GRASP의 **CREATOR(창조자) 패턴**은 이 같은 경우에 사용할 수 있는 책임 할당 패턴으로서 객체를 생성할 책임을 어떤 객체에게 할당할지에 대한 지침을 제공한다.

**CREATOR 패턴**

아래 조건을 최대한 많이 만족하는 B에게 객체 생성 책임을 할당하라.

- B가 A 객체를 포함하거나 참조한다.
- B가 A 객체를 기록한다.
- B가 A 객체를 긴밀하게 사용한다.
- B가 A 객체를 초기화하는 데 필요한 데이터를 가지고 있다.(이 경우 B는 A에 대한 정보전문가다)

**CREATOR 패턴**의 의도는 어떤 방식으로든 생성되는 객체와 연결되거나 관련될 필요가 있는 객체에 해당 객체를 생성할 책임을 맡기는 것이다. 생성될 객체에 대해 잘 알고 있어야 하거나 그 객체를 사용해야 하는 객체는 어떤 방식으로든 생성될 객체와 연결될 것이다.

이미 결합돼 있는 객체에게 생성 책임을 할당하는 것은 설계의 전체적인 결합도에 영향을 미치지 않는다. 결과적으로 CREATOR 패턴은 이미 존재하는 객체 사이의 관계를 이용하기 때문에 설계가 낮은 결합도를 유지할 수 있게 한다.

`Screening`은 예매 정보를 생성하는 데 필요한 영화, 상영 시간, 상영 순번 등의 정보에 대한 전문가이며, 예매 요금을 계산하는 데 필수적인 `Movie`도 알고 있다. 따라서 `Screening`을 `Reservation`의 **CREATOR**로 선택하는 것이 적절해 보인다.

## 구현을 통한 검증

`Screening`은 영화를 예매할 책임을 맡으며 그 결과로 `Reservation` 인스턴스를 생성할 책임을 수행해야 한다. 협력의 관점에서 ***Screening***은 예매하라 메시지에 응답할 수 있어야 한다.

```kotlin
class Screening {
	fun reserve(customer: Customer, audienceCount: Int): Reservation { ... }
}
```

책임이 결정됐으므로 책임을 수행하는데 필요한 인스턴스 변수를 결정해야 한다. `Screening`은 상영 시간과 상영 순번을 인스턴스로 포함한다. 또한 `Movie`에 *가격을 계산하라* 메시지를 전송해야 하기 때문에 영화에 대한 참조도 포함해야 한다.

```kotlin
class Screening(
	private val movie: Movie,
	private val sequence: Int,
	private val whenScreened: LocalDateTiem
) {
		fun reserve(customer: Customer, audienceCount: Int): Reservation { ... }
}
```

영화 예매를 위해서는 `movie`에게 메시지를 전송해서 계산된 영화 요금을 반환 받아야 한다.

```kotlin
class Screening(
	private val movie: Movie,
	private val sequence: Int,
	private val whenScreened: LocalDateTiem
) {
		fun reserve(customer: Customer, audienceCount: Int): Reservation {
			return Reservation(customer, this, calculateFee(audienceCount), audienceCount)
		}
		
		fun calculateFee(audienceCount: Int): Money {
			// 파라미터에 screening을 넣음으로써 Movie의 내부 구현에 대한 어떤 지식도 없이 전송할 메시지를 결정함 -> 캡슐화
			return movie.calculateMovieFee(this).times(audienceCount)
		}
}
```

`Screening`은 `Movie`와 협력하기 위해 `calculateMovieFee` 메시지를 전송한다. `Movie`는 이 메시지에 응답하기위해 `calculateMovieFee` 메서드를 구현해야 한다.

```kotlin
class Movie {
	fun calculateMovieFee(screening: Screening): Money
}
```

요금을 계산하기 위한 정보들과 Movie가 어떤 할인 정책이 적용된 영화인지를 나타내기 위한 영화 종류를 인스턴스 변수로 포함해야 한다.

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
	fun calculateMovieFee(screening: Screening): Money
}

enum class MovieType {
	AMOUNT_DISCOUNT,
	PERCENT_DISCOUNT,
	NONE_DISCOUNT,
}
```

`Movie`는 먼저 `discountConditions`의 원소를 순회하면서 `DiscountCondition` 인스턴스에게 `isSatisfiedBy` 메시지를 전송해서 할인 여부를 판단하도록 요청한다. 만약 할인 조건을 만족하는 `DiscountCondition` 인스턴스가 존재한다면 할인 요금을 계산하기 위해 `calculateDiscountAmount` 메서드를 호출한다. 만약 만족하는 조건이 존재하지 않을 경우에는 기본 금액인 `fee`를 반환한다.

```kotlin
class Movie {
	fun calculateMovieFee(screening: Screening) {
		if (isDiscountable(screening)) {
			return fee.minus(calculateDiscountAmount())
		}
		
		return fee
	}
	
	private fun isDiscountable(screening: Screening): Boolean {
		return discountConditions.any { condition -> condition.isSatisfiedBy(screening) }
	}
}
```

할인 요금을 계산하는 `calculateDiscountAmount` 메서드는 `movieType`에 따라 적절한 메서드를 호출한다.

```kotlin
class Movie {
	private fun calculateDiscountAmount() {
		return when (movieType) {
			AMOUNT_DISCOUNT -> calculateAmountDiscountAmount()
			PERCENT_DISCOUNT -> calcultatePercentDiscountAmount()
			NONE_DISCOUNT -> calculateNoneDiscountAmount()
		}
	}
	
	private fun calculateAmountDiscountAmount(): Money {
		return discountAmount
	}
	
	private fun calcultatePercentDiscountAmount(): Money {
		return fee * discountPercent
	}
	
	private fun calculateNoneDiscountAmount(): Money {
		return Money.ZERO
	}
}
```

`DiscountCondition`은 `Movie`에서 보내는 메시지를 처리하기 위해 `isSatisfiedBy` 메서드를 구현해야 한다.

```kotlin
class DiscountCondition {
	fun isSatisfiedBy(screening: Screening): Boolean
}
```

`DiscountCondition`은 기간 조건을 위한 정보와, 순번 조건을 위한 정보를 인스턴스 변수로 포함한다. 추가적으로 할인 조건의 종류를 포함하며, 할인 조건에 따라 `isSatisfiedBy` 메서드에서 적절한 메서드를 호출한다.

```kotlin
class DiscountCondition(
	private val type: DiscountConditionType,
	private val sequence: Int,
	private val dayOfWeek: DayOfWeek,
	private val startTime: LocalTime,
	private val endTime: LocalTime
) {
	
	fun isSatisfiedBy(screening: Screening): Boolean {
		if (type == DiscountConditionType.PERIOD) {
			return isSatisfiedByPeriod(screening)
		}
		
		return isSatisfiedBySequence(screening)
	}
	
	private fun isSatisfiedByPeriod(screening: Screening): Boolean {
		return dayOfWeek == screening.whenScreened.dayOfWeek &&
			startTime >= screening.whenScreened.toLocalTime() &&
			endTime <= screening.whenScreened.toLocalTime ()
	}
	
	private fun isSatisfiedBySequence(screening: Screening): Boolean {
		return sequence == screening.sequence
	}
}

enum class DiscountConditionType {
	SEQUENCE, PERIOD
}
```

### DiscountCondition 개선하기

현재 구조에서 `DiscountCondition`은 변경에 취약하다. `DiscountCondition`은 다음과 같이 서로 다른 세 가지 이유로 변경될 수 있다.

**새로운 할인 조건 추가**

- `isSatisfiedBy` 메서드 안의 if ~ else 구문을 수정해야 한다.
- 새로운 할인 조건이 새로운 데이터를 요구하면 `DiscountCondition`에 속성을 추가해야 한다.

**순번 조건을 판단하는 로직 변경**

- `isSatisfiedBySequence` 메서드의 내부 구현을 수정해야 한다.
- 순번 조건을 판단하는 데 필요한 데이터가 변경된다면 `DiscountCondition`의 `sequence` 속성도 변경해야 한다.

**기간 조건을 판단하는 로직이 변경되는 경우**

- `isSatisfiedByPeriod` 메서드의 내부 구현을 수정해야 한다.
- 순번 조건을 판단하는 데 필요한 데이터가 변경된다면 `DiscountCondition`의 `dayOfWeek`, startTime, `endTime` 속성을 변경해야 한다.

`DiscountCondition`은 하나 이상의 변경이유를 갖기 때문에 응집도가 낮다. 따라서 **변경의 이유에 따라 클래스를 분리**해야 한다.

`DiscountCondition` 안에 구현된 `isSatisfiedBySequence` 메서드와 `isSatisfiedByPeriod` 메서드는 서로 다른 이유로 변경된다. 서로 다른 이유로 변경되는 두 개의 메서드를 가지는 `DiscountCondition` 클래스의 응집도는 낮아질 수 밖에 없다.

변경의 이유를 찾는 것은 생각보다 어렵다. 하지만 변경의 이유가 하나 이상인 클래스는 몇 가지 패턴이 존재한다.

1. **인스턴스 변수가 초기화 되는 시점이 다르다**
    - 응집도가 높은 클래스는 인스턴스를 생성할 때 모든 속성을 함께 초기화한다.
    - 반면 응집도가 낮은 클래스는 객체의 속성 중 일부만 초기화하고 일부는 초기화되지 않은 상태로 남겨진다.
    - 따라서 **함께 초기화되는 속성을 기준으로 코드를 분리**해야 한다.
2. **메서드들이 인스턴스 변수를 사용하는 정도가 다르다**
    - 모든 메서드가 객체의 모든 속성을 사용한다면 클래스의 응집도는 높다
    - 반면 메서드들이 사용하는 속성에 따라 그룹이 나뉜다면 클래스의 응집도가 낮다고 볼 수 있다.

**클래스의 응집도 판단하기**

클래스가 다음과 같은 징후로 몸살을 앓고 있다면 클래스의 응집도는 낮은 것이다.

- 클래스가 하나 이상의 이유로 변경돼야 한다.
- 클래스의 인스턴스를 초기화하는 시점에, 경우에 따라 서로 다른 속성들을 초기화하고 있다면 응집도가 낮은 것이다.
- 메서드 그룹이 속성 그룹을 사용하는지 여부로 나뉜다면 응집도가 낮은 것이다.

`DiscountCondition` 클래스에는 낮은 응집도를 암시하는 세 가지 징후가 모두 들어있으므로, `DiscountCondition`을 변경의 이유에 따라 여러 개의 클래스로 분리해야 한다.

### 타입 분리하기

`DiscountCondition`의 가장 큰 문제는 순번 조건과 기간 조건이라는 두 개의 독립적인 타입이 하나의 클래스 안에 공존하고 있다는 점이다. 두 타입을 `SequenceCondition`과 `PeriodCondition`이라는 두 개의 클래스로 분리하자.

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

class SequenceCondition(
	private val sequence: Int
) {
		fun isSatisfiedBy(screening: Screening): Boolean {
			return sequence == screening.sequence
		}
	}
}
```

클래스를 분리하고 앞에서 언급했던 문제점들이 해결되었지만, 새로운 문제가 생겼다. 수정 전에는 `Movie`와 협력하는 클래스가 하나였지만, 수정 후에는 `Movie` 인스턴스는 `SequenceCondition`과 `PeriodCondition`이라는 두 개의 서로 다른 클래스의 인스턴스 모두와 협력해야 한다는 것이다.

이를 다형성을 통해 해결해보자.

### 다형성을 통해 분리하기

`Movie` 입장에서 보면 `SequenceCondition`과 `PeriodCondition`은 아무 차이도 없다. 둘은 동일한 책임을 수행하고 있을 뿐이며, 두 클래스의 내부 구현은 `Movie` 입장에서는 중요하지 않다.

`Movie` 입장에서 두 클래스가 동일한 책임을 수행한다는 것은 동일한 역할을 수행한다는 것이다. 따라서 역할을 사용하여 두 객체의 구체적인 타입을 추상화할 수 있다.

```kotlin
interface DiscountCondition {
	fun isSatisfiedBy(screening: Screening): Boolean
}

class PeriodCondition: DiscountCondition { ... }
class SequenceCondition: DiscountCondition { ... }
```

이제 `Movie`는 구체 타입을 몰라도 상관 없다. 협력하는 객체가 `DiscountCondition` 역할을 수행할 수 있고 `isSatisfiedBy` 메시지를 이해할 수 있다는 사실만 알고 있어도 충분하다.

```kotlin
class Movie(
	private val discountConditions: List<DiscountCondition>
) {
	fun calculateMovieFee(screening: Screening): Money {
		if (isDiscountable(screening)) {
			return fee - calculateDiscountAmount()
		}
		
		return fee
	}
}
```

객체의 암시적인 타입에 따라 행동을 분기해야 한다면 암시적인 타입을 명시적인 클래스로 정의하고 행동을 나눔으로써 응집도 문제를 해결할 수 있다. 다시 말해 객체의 타입에 따라 변하는 행동이 있다면 타입을 분리하고 변화하는 행동을 각 타입의 책임으로 할당하라는 것이다.

**GRASP**에서는 이를 **POLYMORPHISM(다형성) 패턴**이라고 부른다.

### 변경으로부터 보호하기

`DiscountCondition`의 두 서브 클래스는 서로 다른 이유로 변경되며, 각 조건이 변경될 경우에만 수정된다. 두 개의 서로 다른 변경이 두 개의 서로 다른 클래스 안으로 캡슐화된 것 이다.

또한 `DiscountCondition`이라는 역할이 `Movie`로 부터 `PeriodCondition`과 `SequenceCondition`의 존재를 감춘다. `DiscountCondition`이라는 추상화가 구체적인 타입을 캡슐화하여, `Movie`의 관점에서 `DiscountCondition`의 새로운 타입을 추가하더라도 `Movie`가 영향을 받지 않는다.

이처럼 변경을 캡슐화하도록 책임을 할당하는 것을 **GRASP**에서는 **PROTECTED VARIATIONS(변경 보호)** 패턴이라고 부른다.

**정리**

- 하나의 클래스가 여러 타입의 행동을 구현하고 있는 것처럼 보인다 → 클래스를 분리하고 **POLYMORPHISM 패턴**에 따라 책임을 분산시켜라
- 예측 가능한 변경으로 인해 여러 클래스들이 불안정해진다 → **PROTECTED VARIATIONS 패턴**에 따라 안정적인 인터페이스 뒤로 변경을 캡슐화하라

### Movie 클래스 개선하기

Movie 역시 금액 할인 정책 영화와 비율 할인 정책 영화라는 두 가지 타입을 하나의 클래스 안에 구현하고 있기 때문에 하나 이상의 이유로 변경될 수 있다. 즉, 응집도가 낮다.

Movie 역시 **POLYMORPHISM** 패턴을 사용해 서로 다른 행동을 타입별로 분리하면 다형성의 혜택을 누릴 수 있다. Movie의 경우 Movie관련 구현을 공유하며 할인 정책만 다르므로, 추상 클래스를 이용해 역할을 구현할 수 있다.

```kotlin
abstract class Movie(
	private val title: String,
	private val runningTime: Duration,
	private val fee: Money,
	private val discountConditions: List<DiscountCondition>
) {
	fun calculateMovieFee(screening: Screening): Money {
		if (isDiscountable(screening)) {
			return fee - calculateDiscountAmount()
		}
		
		return fee
	}
	
	private fun isDiscountable(screening: Screening): Boolean {
		return discountConditions.any { condition -> condition.isSatisfiedBy(screening) }
	}
	
	abstract protected caculateDiscountAmount(): Money
	
	// 영화의 기본 금액
	protected fun getFee(): Money {
		return fee
	}
}
```

```kotlin
class AmountDiscountMovie(
	private val discountAmount: Money
): Movie {
	override fun calculateDiscountAmount(): Money {
		return discountAmount
	}
}
```

```kotlin
class PercentDiscountMovie(
	private val percent: Double
): Movie {
	override fun calculateDiscountAmount: Money {
		return getFee() * percent
	}
}
```

```kotlin
class NoneDiscountMovie: Movie {
	override fun calculateDiscountAmount: Money {
		return Money.ZERO
	}
}
```

<img width="2048" height="638" alt="image" src="https://github.com/user-attachments/assets/cd796ec5-3a27-4964-a065-029e8ba2cb81" />

이제 모든 클래스의 내부 구현은 캡슐화돼 있고, 모든 클래스는 변경의 이유를 오직 한 가지씩만 가진다. 각 클래스는 응집도가 높고 다른 클래스와 최대한 느슨하게 결합돼 있다. 클래스는 작고 오직 한 가지 일만 수행한다. 책임은 적절하게 분배돼 있다.

결론은 데이터가 아닌 책임을 중심으로 설계하라는 것이다. 객체에게 중요한 것은 상태가 아니라 행동이다. 객체지향 설계의 기본은 책임과 협력에 초점을 맞추는 것이다.

### 변경과 유연성

변경에 대비할 수 있는 두 가지 방법은 다음과 같다.

- 코드를 이해하고 수정하기 쉽도록 단순하게 설계한다
- 코드를 수정하지 않고 변경을 수용할 수 있도록 코드를 유연하게 만든다

대부분의 경우 전자가 좋은 방법이지만, 유사한 변경이 반복적으로 발생한다면 복잡성이 상승하더라도 유연성을 추가하는 두 번째 방법이 더 좋다.

예를들어, 런타임 때 영화에 설정된 할인 정책을 변경할 수 있어야 하는 요구사항이 있다고 해보자. 현재의 설계에서는 할인 정책을 구현하기 위해 **상속**을 이용하고 있기 때문에, 영화의 할인 정책을 변경하기 위해서는 새로운 인스턴스를 생성한 후 필요한 정보를 복사해야 한다.

이는 상속 대신 **합성**으로 해결 가능하다.

<img width="2048" height="640" alt="image" src="https://github.com/user-attachments/assets/070fbbe2-3c12-49fd-83c1-ea3d2e887776" />

위 그림과 같이 `Movie` 상속 계층 안에 구현된 할인 정책을 분리한 후, `Movie`에 합성 시키면 유연한 설계가 완성된다. 이제 금액 할인 정책이 적용된 영화를 비율 할인 정책으로 바꾸는 일은 `Movie`에 연결된 `DiscountPolicy`의 인스턴스를 교체하는 단순한 작업으로 바뀐다.

## 책임 주도 설계의 대안

적절한 책임과 객체를 선택하는 일은 어려울 수 있다. 이때 최선의 방법은 최대한 빠르게 목적한 기능을 수행하는 코드를 작성하는 것이다. 일단 실행되는 코드를 얻고 난 후에 리팩터링을 수행하는 것이 더 빠른 방법일 수 있다.

### 메서드 응집도

데이터 설계 중심에서 `ReservationAgency`의 `reserve`메서드는 너무 길고 이해하기 어렵다. 이렇게 긴 메서드는 다양한 측명에서 코드의 유지보수에 부정적인 영향을 미친다.

- 코드를 이해하는데 오랜 시간이 걸린다.
- 하나의 메서드 안에서 너무 많은 작업을 처리하기 때문에, 변경이 필요한 곳을 찾기 어렵다.
- 메서드 내부의 일부 로직만 수정하더라도, 메서드의 나머지 부분에 사이드 이팩트가 발생할 수 있다.
- 로직의 일부만 재사용하는 것은 불가능하다.
- 코드의 재사용하는 유일한 방법은 원하는 코드를 복사해서 붙여넣는 것뿐이므로 코드 중복을 초래하기 쉽다.

반면 짧고 이해하기 쉬운 메서드는 다음과 같은 장점을 지닌다.

- 전체적인 흐름을 읽기 쉬워진다.
- 변경하기 쉽다.
- 메서드가 잘게 나뉘어져 있으면 재사용될 확률이 높아진다.
- 고수준의 메서드를 볼 때 일련의 주석을 읽는 것 같은 느낌이 들게 할 수 있다.
- 메서드가 잘게 나뉘어져 있을 때 오버라이딩 하는 것도 쉽다.
