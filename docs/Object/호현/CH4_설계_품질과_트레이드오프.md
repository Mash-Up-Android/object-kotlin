# 설계 품질과 트레이드오프

> 객체지향 설계란 올바른 객체에게 올바른 책임을 할당하면서 낮은 결합도와 높은 응집도를 가진 구조를 창조하는 활동이다.
> 

## 데이터 중심의 영화 예매 시스템

**질문**

- 상태 중심의 설계는 좋지 않다. → 뷰모델은 어떻게 설계해야 할까?

### 데이터를 준비하자

데이터 중심 설계란 객체 내부에 저장되는 데이터를 기반으로 시스템을 분할하는 방법이다.

책임 중심의 설계가 ‘책임이 무엇인가’를 묻는 것으로 시작한다면, 데이터 중심의 설계는 객체가 내부에 저장해야 하는 ‘데이터가 무엇인가’를 묻는 것으로 시작한다.

```kotlin
class Movie(
	private val title: String,
	private val runningTime: Duration,
	private val fee: Money,
	private val discountConditions: List<DiscountCondition>,
	
	private val movieType: MovieType,
	private val discountAmount: Money,
	private val discountPercent: Double,
)
```

- 책임 중심 설계의 `Movie`와는 다르게, `Movie` 내부에 `discountConditions`가 포함되어 있고 `DiscountPolicy`로 분리했었던 `discountAmount`, `discountPercent`를 내부에 정의하고 있다.

할인 정책의 종류를 알 수 있는 방법을 만들기 위해 `MovieType`을 새로 만들었다.

```kotlin
enum MovieType {
	AMOUNT_DISCOUNT,
	PERCENT_DISCOUNT,
	NONE_DISCOUNT
}
```

- 어떤 데이터가 필요한다를 MovieType으로 결정하고, 이 값에 따라 어떤 데이터를 사용할지 결정하는 구조가 된다.

할인 조건을 구현하는데 필요한 데이터는 무엇인가? 먼저 할인 조건의 종류를 저장할 데이터가 필요하다.

```kotlin
enum DiscountConditionType {
	SEQUENCE,
	PERIOD,
}
```

이후 할인 조건을 저장할 데이터가 필요하다.

```kotlin
class DiscountCondition(
	private val type: DiscountConditionType,
	private val sequence: Int,
	private val dayOfWeek: DayOfWeek,
	private val startTime: LocalTime,
	private val endTime: LocalTime,
)
```

이어서 `Screening`도 위와 같이 데이터 중심의 설계를 한다면 다음과 같다.

```kotlin
class Screening(
	var movie: Movie,
	var whenScreened: LocalDateTime,
	var sequence: Int,
)
```

영화를 예매하는 `Reservation` 클래스와 고객을 나타내는 `Customer`도 추가하자.

```kotlin
class Reservation(
	var customer: Customer,
	var screening: Screening,
	var fee: Money,
	var audienceCount: Int,
)
```

```kotlin
class Customer(
	private val name: String,
	private val id: String,
)
```

위의 코드를 다이어그램으로 나타내면 다음과 같다.

<img width="1280" height="538" alt="image" src="https://github.com/user-attachments/assets/475a4ab1-33f2-4e44-8dfd-6ec7af20a127" />

### 영화를 예매하자

`ReservationAgency`는 데이터 클래스들으 조합해서 영화 예매 절차를 구하는 클래스이다.

```kotlin
class ReservationAgency {
	fun reserve(screening: Screening, customer: Customer, audienceCount: Int) {
		val movie = screening.movie
		val discountable = false

		// 할인 가능 여부 체크
		for(condition in movie.discountConditions) {
			if (condition.type == DiscountConditionType.PERIOD) {
				discountable = screening.whenScreened.dayOfWeek == condition.dayOfWeek &&
					condition.startTime <= screening.whenScreened.toLocalTime() &&
					condition.endTime >= screening.whenScreened.toLocalTime()
			} else {
				discountable = condition.sequence == screening.sequence
			}
			
			if (discountable) {
				break
			}
		}
		
		// 할인 금액 체크
		var fee: Money? = null
		if (discountable) {
			var discountAmount = Money.ZERO
			when(movie.movieType) {
				AMOUNT_DISCOUNT -> {
					discountAmount = movie.discountAmount
				}
				PERCENT_DISCOUNT -> {
					discountAmount = movie.fee * movie.discountPercent
				}
			}
			fee = movie.fee - discountAmount * audienceCount
		} else {
			fee = movie.fee
		}
		
		return Reservation(customer, screening, fee, audienceCount)
	}
}
```

## 설계 트레이드오프

앞선 데이터 중심 설계와 기존의 책임 중심 설계를 비교하기 위해 캡슐화, 응집도, 결합도 측면에서 비교를하겠다.

### 캡슐화

캡슐화는 외부에서 알 필요가 없는 부분을 감춤으로써 대상을 단순화 하는 추상화의 한 종류이다. 캡슐화가 중요한 이유는 **불안정한 부분과 안정적인 부분을 분리해서 변경의 영향을 통제**할 수 있기 때문이다. 따라서 변경의 관점에서 설계의 품질을 판단하기 위해 **캡슐화**를 기준으로 삼을 수 있다.

### 응집도와 결합도

**응집도**

- 응집도는 모듈에 포함된 내부 요소들이 연관돼 있는 정도를 나타낸다.
- 객체지향 관점에서 응집도는 객체 또는 클래스에 얼마나 관련 높은 책임들을 할당했는지를 나타낸다.
- 변경의 관점에서 응집도란 변경이 발생할 때 모듈 내부에서 발생하는 정도로 측정할 수 있다.
    - 하나의 변경을 수용하기 위해 모듈 전체가 함께 변경된다면 응집도가 높은 것이고, 일부만 변경된다면 응집도가 낮은 것이다.
    - 또한 하나의 변경에 대해 하나의 모듈만 변경된다면 응집도가 높지만, 다수의 모듈이 함께 변경돼야 한다면 응집도가 낮은 것이다.
    
    <img width="1486" height="516" alt="image" src="https://github.com/user-attachments/assets/45e87f68-ff4f-4055-9ddf-6443b9405968" />
    

**결합도**

- 결합도는 의존성의 정도를 나타내며 다른 모듈에 대해 얼마나 많은 지식을 갖고 있는지를 나타내는 정도다.
- 객체지향 관점에서 결합도는 객체 또는 클래스가 협력에 필요한 적절한 수준의 관계만을 유지하고 있는지를 나타낸다.
- 결합도는 한 모듈이 변경되기 위해서 다른 모듈의 변경을 요구하는 정도로 측정할 수 있다.
    - 하나의 모듈을 수정할 때 얼마나 많은 모듈이 수정돼야 하는지를 나타낸다.
    
    <img width="1602" height="556" alt="image" src="https://github.com/user-attachments/assets/7216232b-f8a6-428c-ab6c-2edd0328ebe9" />
    

**추가 개념**

- 변경의 원인에 따라 결합도의 개념을 설명할 수도 있다. 퍼블릭 인터페이스를 수정했을 때만 다른 모듈에 영향을 미치는 경우 결합도가 낮다고 할 수 있다. 따라서 클래스의 구현이 아닌 인터페이스에 의존하도록 코드를 작성해야 낮은 결합도를 얻을 수 있다.
- 결합도가 높아도 상관 없는 경우도 있다. 일반적으로 변경될 확률이 매우 적은 안정적인 모듈에 의존하는 것은 아무런 문제가 되지 않는다.
    - 예를 들어 자바의 String, ArrayList는 변경될 확률이 매우 낮기 때문에 결합도를 고민할 필요 없다.

캡슐화의 정도가 응집도와 결합도에 영향을 미친다. 캡슐화를 지키면 모듈 안의 응집도는 높아지고 모듈 사이의 결합도는 낮아진다. 따라서 응집도와 결합도를 고려하기 전에 먼저 캡슐화를 향상시키기 위해 노력하라.

## 데이터 중심의 영화 예매 시스템의 문제점

### 캡슐화 위반

```kotlin
class Movie {
	private var fee: Money
	
	fun getFee(): Money = fee
	fun setFee(fee: Money) {
		this.fee = fee
	}
}
```

- `getFee()`와 `setFee()`와 같은 접근자와 수정자 메서드는 객체 내부의 상태에 대해 캡슐화를 하지 못한다.
    - 오히려 fee를 가지고 있다고 홍보하는 꼴이다.
- 구현을 캡슐화할 수 있는 적절한 책임은 협력이라는 문맥을 고려할 때만 얻을 수 있다. 설계할 때 협력에 관해 고민하지 않으면 캡슐화를 위반하는 과도한 접근자와 수정자를 가지게 되는 경향이 있다.
    - 이는 캡슐화의 원칙을 위반하는 변경에 취약한 설계를 얻게 된다.

### 높은 결합도

```kotlin
class ReservationAgency {
	fun reserve(screening: Screening, customer: Customer, audienceCount: Int) {
		...
		var fee: Money? = null
		
		if (discountable) {
			...
			fee = movie.getFee() - disocuntedAmount * audienceCount
		} else {
			fee = movie.fee 
		}
	}
}
```

- 위 코드에서 `fee`의 타입을 변경한다고 해보자.
    - 이를 위해서 `getFee()` 메서드의 반환 타입도 함께 수정해야 하며, `getFee()`를 호출하는 `ReservationAgency`의 구현도 함께 수정돼야 한다.
    - 이처럼 데이터 중심 설계는 객체의 캡슐화를 약화시키기 때문에 클라이언트가 객체의 구현에 강하게 결합된다.
- 여러 데이터를 갖고 있는 제어 로직이 특정 객체 안에 집중되기 때문에, 하나의 제어 객체가 다수의 데이터 객체에 강하게 결합된다.
    - 제어 객체 내부에서 하나의 객체라도 수정된다면, 제어 객체도 함께 수정돼야 한다.

### 낮은 응집도

서로 다른 이유로 변경되는 코드가 하나의 모듈 안에 공존할 때 모듈의 응집도가 낮다고 말한다.

- 제어 로직을 가지고 있는 `ReservationAgency`는 너무 많은 변경될 이유를 가지고 있다.
    - 할인 정책이 추가될 경우
    - 할인 정책별로 할인 요금을 계산하는 방법이 변경되는 경우
    - 할인 조건이 추가되는 경우
    - 등등 ….

낮은 응집도는 설계 측면의 두 가지 문제가 있다.

- 변경의 이유가 서로 다른 코드들을 하나의 모듈 안에 뭉쳐놓았기 때문에, 변경과 상관 없는 코드들이 영향을 받게 된다.
- 하나의 요구사항 변경을 반영하기 위해 동시에 여러 모듈을 수정해야 한다. 응집도 가 낮을 경우 다른 모듈에 위치해야 할 책임의 일부가 엉뚱한 곳에 위치하기 때문이다.

<aside>
✅

**단일 책임 원칙**

단일 책임 원칙을 한 마디로 요약하면 클래스는 **단 한가지의 변경 이유**만 가져야 한다는 것이다. 
단일 책임 원칙은 클래스의 **응집도**를 높일 수 있는 설계 원칙이다.

</aside>

## 자율적인 객체를 향해

### 캡슐화를 지켜라

```kotlin
class Rectangle(
	private var left: Int,
	private var top: Int,
	private var right: Int,
	private var bottom: Int,
) {
	fun getLeft(): Int
	fun setLeft(left: Int)
	...
}
```

위 코드에는 두 가지 문제점이 있다.

**코드 중복이 발생할 확률 이 높다.**

- 여러 곳에서 사각형의 너비와 높이를 증가시키는 코드가 필요하다면, 각각의 위치에서 같은 코드가 쓰여질 것이다.

**변경에 취약하다**

- right과 bottom 대신 length와 height을 이용해 사각형을 표현하기로 바꾸자고 해보자. 접근자와 수정자 메서드는 변수의 존재 사실 자체를 외부에 노출 시킨다. 그러므로 right을 width로 변경한다면, 모든 곳에서 쓰이고 있는 getRight, setRight 메서드의 이름을 getWidth, setWidth로 바꿔야 한다.

해결 방법은 캡슐화를 강화시키는 것이다. Rectangle 내부에 너비와 높이를 조절하는 로직을 캡슐화 하면 두 가지 문제를 해결할 수 있다.

```kotlin
class Rectangle {
	fun enlarge(multiple: Int) {
		right *= multiple
		bottom *= multiple
	}
}
```

### 스스로 자신의 데이터를 책임지는 객체

객체는 단순한 데이터 제공자 아니다. 객체 내부에 저장되는 데이터보다 객체가 협력에 참여하면서 수행할 책임을 정의하는 오퍼레이션이 더 중요하다.

따라서 객체를 설계할 때 “이 객체가 어떤 데이터를 포함해야 하는가?”라는 질문은 다음과 같은 두 개의 개별적인 질문으로 분리해야 한다.

- 이 객체가 어떤 데이터를 포함해야 하는가?
- 이 객체가 데이터에 대해 수행해야 하는 오퍼레이션은 무엇인가?

두 질문을 조합하면 객체의 내부 상태를 저장하는 방식과, 저장된 상태에 대해 호출할 수 있는 오펄에ㅣ션의 집합을 얻을 수 있다.

영화 예매 시스템 예제에서 `ReservationAgency`로 새어나간 데이터에 대한 책임을 실제 데이터를 포함하고 있는 객체로 옮겨보자.

`DiscountCondition` 먼저 살펴보자.

- 어떤 데이터를 관리해야 하는가?
    
    ```kotlin
    class DiscountCondition(
    	private val type: DiscountConditionType,
    	private val sequence: Int,
    	private val dayOfWeek: DayOfWeek,
    	private val startTime: LocalTime,
    	private val endTime: LocalTime,
    )
    ```
    
- 이 데이터에 대해 수행할 수 있는 오퍼레이션은 무엇인가?
    - 할인 조건을 판단할 수 있는 `isDiscountable` 메서드가 필요할 것이다.

```kotlin
class DiscountCondition {
	...
	fun getType(): DiscountConditionType {
		return type
	}
	
	fun isDiscountable(dayOfWeek: DayOfWeek, time: LocalTime) {
		if (type != DiscountConditionType.PERIOD) {
			throw IllegalArgumentException()
		}
		
		return this.dayOfWeek == dayOfWeek &&
			this.startTime >= time &&
			this.endTime <= time
	}
	
	fun isDiscountable(sequence: Int): Boolean {
		if (type != DiscountConditionType.SEQUENCE) {
			throw IllegalArgumentException()
		}
		
		return this.sequence == sequence
	}
}
```

이제 Movie를 구현해보자.

- 어떤 데이터를 포함해야 하는가?
    
    ```kotlin
    class Movie(
    	private val title: String,
    	private val runningTime: Duration,
    	private val fee: Money,
    	private val discountConditions: List<DiscountCondition>,
    	
    	private val movieType: MovieType,
    	private val discountAmount: Money,
    	private val discountPercent: Double,
    )
    ```
    
- 데이터를 처리하기 위해 어떤 오퍼레이션이 필요한가?
    - 영화 요금을 계산하는 오퍼레이션
        
        ```kotlin
        class Movie {
        	...
        	fun getMovieType(): MovieType {
        		return movieType
        	}
        	
        	fun calculateAmountDiscountedFee(): Money {
        		if (movieType != MovieType.AMOUNT_DISCOUNT) {
        			throw IllegalArgumentException()
        		}
        		
        		return fee - discountAmount
        	}
        	
        	fun calculatePercentDiscountedFee(): Money {
        		if (movieType != MovieType.PERCENT_DISCOUNT) {
        			throw IllegalArgumentException()
        		}
        		
        		return fee - fee * discountPercent
        	}
        	
        	fun calculateNoneDiscountedFee(): Money {
        		if (movieType != MovieType.NONE_DISCOUNT) {
        			throw IllegalArgumentException()
        		}
        		
        		return fee
        	}
        }
        ```
        
    - 할인 여부를 판단하는 오퍼레이션
        
        ```kotlin
        class Movie {
        	...
        	fun isDiscountable(whenScreened: LocalDateTime, sequence: Int): Boolean {
        		for (condition in discountConditions) {
        			if (condition.getType() == DiscountConditionType.PERIOD) {
        				if (condition.isDiscountale(whenScreened.getDayOfWeek(), whenScreened.toLocalTime())) {
        					return true
        				}
        			}	else {
        				if (condition.isDiscountable(sequence)) {
        					return true
        				}
        			}
        		}
        		return false
        	}
        }
        ```
        

이제 `Screening`을 살펴보자.

```kotlin
class Screening(
	var movie: Movie,
	var whenScreened: LocalDateTime,
	var sequence: Int,
) {
	fun calclulateFee(audienceCount: Int): Money {
		when (movie.getMovieType()) {
			AMOUNT_DISCOUNT -> {
				if (movie.isDiscountable(whenScreened, sequence)) {
					return movie.calculateAmountDiscountedFee() & audienceCount
				}
			}
			PERCENT_DISCOUNT -> {
				if (movie.isDiscountable(whenScreened, sequence)) {
					return movie.calculatePercentDiscountedFee() * audienceCount
				}
			}
			NONE_DISCOUNT -> {
				return movie.calculateNoneDiscountedFee() * audienceCount
			}
		}
		
		return movie.calculateNoneDiscountedFee() * audienceCount
	}
}
```

이렇게 되면 원래 로직의 중심이었던 `ReservationAgency`가 훨씬 가벼워지게 된다.

```kotlin
class ReservationAgency {
	fun reserve(screening: Screening, customer: Customer, audienceCount: Int) {
		val fee = screening.calculateFee(audienceCount)
		return Reservation(customer, screening, fee, audienceCount)
	}
}
```

<img width="2048" height="1290" alt="image" src="https://github.com/user-attachments/assets/baadc5f0-6de3-4481-9dd4-c7a11f868143" />

## 하지만 여전히 부족하다

두 번째 설계가 첫 번째 설계보다 향상된 것은 사실이지만, 여전히 첫 번째 설계에서의 문제점이 남아있다.

### 캡슐화 위반

```kotlin
class DiscountCondition {
	...
	fun getType(): DiscountConditionType { ... }
	
	fun isDiscountable(dayOfWeek: DayOfWeek, time: LocalTime) { ... }
	
	fun isDiscountable(sequence: Int): Boolean { ... }
}
```

- DiscountCondition의 `isDiscountable()` 함수는 파라미터로 자신이 갖고 있는 상태를 받고 있다.
    - 이는 함수 내부의 구현 정보를 인터페이스에 노출하고 있다는 것이다.

Movie 역시 캐슐화가 부족하다.

```kotlin
class Movie {
	...
	fun getMovieType(): MovieType { ... }
	
	fun calculateAmountDiscountedFee(): Money { ... }
	
	fun calculatePercentDiscountedFee(): Money { ... }
	
	fun calculateNoneDiscountedFee(): Money { ... }
	
	fun isDiscountable(whenScreened: LocalDateTime, sequence: Int): Boolean { ... }
}
```

- calculateAmountDiscountedFee, calculatePercentDiscountedFee, calculateNoneDiscountedFee 메서드는 할인 정책에는 금액 할인 정책, 비율 할인 정책, 미적용 세 가지가 존재한다는 사실을 드러내고 있다.
    - 만약 새로운 할인 정책이 추가되거나 제거된다면, 해당 메서드들에 의존하는 모든 클라이언트가 영향받을 것이다.

캡슐화는 단순히 객체 내부의 데이터를 외부로부터 감추는 것이 아니다. 캡슐화란 변할 수 있는 어떤 것이라도 감추는 것이다. 그것이 무엇이든 구현과 관련된 것이라면 모두 감추어야 한다.

### 높은 결합도

캡슐화 위반으로 인해 `DiscountCondition`의 내부 구현이 외부로 노출됐기 때문에 Movie와 DiscountCondition 사이의 결합도는 높을 수 밖에 없다. 이는 변경 사항이 서로에게 영향을 준다는 말과 같다.

```kotlin
class Movie {
	...
	fun isDiscountable(whenScreened: LocalDateTime, sequence: Int): Boolean {
		for (condition in discountConditions) {
			if (condition.getType() == DiscountConditionType.PERIOD) {
				if (condition.isDiscountale(whenScreened.getDayOfWeek(), whenScreened.toLocalTime())) {
					return true
				}
			}	else {
				if (condition.isDiscountable(sequence)) {
					return true
				}
			}
		}
		return false
	}
}
```

DiscountCondition에 대한 변경이 Movie에 어떤 영향을 미치는지는 다음과 같다.

- `DiscountCondition`의 기간 할인 조건의 명칭이 `PERIOD`에서 다른 값으로 변경된다면 `Movie`를 수정해야 한다.
- `DiscountCondition`의 종류가 추가되거나 삭제되면 `Movie` 내부의 if문을 수정해야 한다.
- 각 `DiscountCondition`의 만족 여부를 판단하는 데 필요한 정보가 변경된다면, `Movie`의 `isDiscountable` 메서드로 전달된 파라미터를 변경해야 한다. 이로 인해 `Movie`의 `isDiscountable` 메서드 시그니처도 함께 변경될 것이고 결과적으로 이 메서드에 의존하는 `Screening`에 대한 변경을 초래할 것이다.

### 낮은 응집도

```kotlin
class Screening(
	var movie: Movie,
	var whenScreened: LocalDateTime,
	var sequence: Int,
) {
	fun calclulateFee(audienceCount: Int): Money {
		when (movie.getMovieType()) {
			AMOUNT_DISCOUNT -> {
				if (movie.isDiscountable(whenScreened, sequence)) {
					return movie.calculateAmountDiscountedFee() & audienceCount
				}
			}
			PERCENT_DISCOUNT -> {
				if (movie.isDiscountable(whenScreened, sequence)) {
					return movie.calculatePercentDiscountedFee() * audienceCount
				}
			}
			NONE_DISCOUNT -> {
				return movie.calculateNoneDiscountedFee() * audienceCount
			}
		}
		
		return movie.calculateNoneDiscountedFee() * audienceCount
	}
}
```

할인 조건의 종류를 변경하기 위해서는 `DiscountCondition`, `Movie` 그리고 `Movie`를 사용하는 `Screening`을 함께 수정해야 한다. 하나의 변경을 수용하기 위해 코드의 여러 곳을 동시에 변경해야 한다는 것은 설계의 응집도가 낮다는 증거다.

## 데이터 중심 설계의 문제점

데이터 중심 설계가 변경에 취약한 이유는 두 가지다.

- 데이터 중심의 설계는 본질적으로 너무 이른 시기에 데이터에 관해 결정하도록 강요한다.
- 데이터 중심의 설계에서는 협력이라는 문맥을 고려하지 않고 객체를 고립시킨 채 오퍼레이션을 결정한다.

### 데이터 중심 설계는 객체의 행동보다는 상태에 초점을 맞춘다

데이터 주도 설계는 설계를 시작하는 처음부터 데이터에 관해 결정하도록 강요하기 때문에 너무 이른 시기에 내부 구현에 초점을 맞추게 한다.

객체 내부 구현이 객체의 인터페이스를 어지럽히고 객체의 응집도와 결합도에 나쁜 영향을 미치기 때문에 변경에 취약한 코드를 낳게 된다.

### 데이터 중심 설계는 객체를 고립시킨 채 오퍼레이션을 정의하도록 만든다

데이터 중심 설계에서 초점은 객체의 외부가 아니라 내부로 향한다. 실행 문맥에 대한 깊이 있는 고민 없이 객체가 관리할 데이터의 세부 정보를 먼저 결정한다. 객체의 구현이 이미 결정된 상태에서 다른 객체와의 협력 방법을 고민하기 때문에, 이미 구현에 객체의 인터페이스를 억지로 끼워맞출 수 밖에 없다. 이는 변경에 대한 유연성을 떨어트린다.
