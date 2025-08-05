# 객체, 설계

### 티켓 판매 애플리케이션

**요구사항**

- 티켓을 판매하는 애플리케이션을 구현한다.
- 단, 이벤트에 당첨된 관람객은 초대장을 티켓으로 교환해주고, 당첨되지 않은 사람은 현금으로 티켓을 구매해야 한다.

**핵심 클래스 구현**

```kotlin
class Invitation {
	private val when: LocalDateTime? = null
}
```

- 초대장이라는 개념을 구현한 `Invitation`

```kotlin
class Ticket {
	private val fee: Long? = null
}
```

- 공연 관람을 위해 갖고 있어야 할 `Ticket` 클래스

```kotlin
class Bag(
	private val amount: Long,
	private val invitation: Invitation? = null,
) {
	private val ticket: Ticket? = null
	
	fun hasInvitation(): Boolean {
		return invitation != null
	}
	
	fun hasTicket(): Boolean {
		return ticket != null
	}
	
	fun setTicket(ticket: Ticket) {
		this.ticket = ticket
	}
	
	fun minusAmount(amount: Long) {
		this.amount -= amount
	}
	fun plusAmount(amount: Long) {
		this.amount += amount
	}
}
```

- 소지품을 보관할 `Bag` 클래스
    - `Bag` 클래스에는 초대장(invitation), 티켓(ticket), 현금(amount)를 필드와 관련 함수들을 갖고 있다.
    - `Bag` 인스턴스를 생성하는 시점에 현금만 보관하거나 초대장과 현금을 동시에 보관하는 제약을 두었다.

```kotlin
class Audience(val bag: Bag)
```

- 관람객이라는 개념을 구현하는 `Audience` 클래스

```kotlin
class TicketOffice(
	private val amount: Long,
	private val tickets: List<Ticket>,
) {

	fun getTicket(): Ticket {
		return tickets.remove(0)
	}
	
	fun minusAmount(amount: Long) {
		this.amount -= amount
	}
	
	fun plusAmount(amount: Long) {
		this.amount += amount
	}
}
```

- 티켓을 판매하는 `TicketOffice` 클래스
    - 판매할 티켓을 나타내는 `tickets` 변수가 있고, 티켓을 가져오고 현금을 줄이고 늘릴 수 있다.

```kotlin
class TicketSeller(val ticketOffice: TicketOffice)
```

- 매표소에서 초대장을 티켓으로 교환해주거나 판매하는 `TicketSeller` 클래스

```kotlin
class Theater(private val ticketSeller: TicketSeller) {
	fun enter(audience: Audience) {
		if (auidience.getBag().hasInvitation()) {
			ticket = ticketSeller.getTicketOffice().getTicket()
			audience.getBag().setTickeT(ticket)
		} else {
			ticket = ticketSeller.getTicketOffice().getTicket()
			audience.getBag().minusAmount(ticket.getFee())
			ticketSeller.getTicketOffice().plusAmount(ticket.getFee())
			audience.getBag().setTickeT(ticket)
		}
	}
}
```

- 소극장을 구현하는 Theater 클래스
    - 소극장은 관람객의 가방 안에서 초대장이 있는지 확인한다.
    - 초대장이 있으면 티켓을 주고, 없으면 현금을 받아 티켓을 준다.

지금까지의 **티켓 판매 애플리케이션**의 클래스 다이어그램을 나타내보면 아래와 같다.

<img width="2048" height="1276" alt="image" src="https://github.com/user-attachments/assets/f856a6c0-c95f-4ab7-9255-a22530a53c73" />

## 무엇이 잘못되었나?

### 예상을 빗나가는 코드

이해 가능한 코드란, 동작이 우리 예상에서 크게 벗어나지 않는 코드이다. 하지만 위에서 보여준 코드는 우리의 예상을 벗어난다. 관람객의 입장을 현실과 위 코드 상황에서 비교해보자.

- **현실**
    - 관람객이 직접 자신의 가방에서 초대장을 꺼내 판매원에게 건낸다.
- **코드**
    - 소극장이 관람객의 가방에서 초대장을 꺼내 판매원에게 건낸다.

이러한 코드는 상식과 너무나도 다르게 동작하기 때문에 이해하기 더 어려워진다.

### 많은 배경 지식 필요

Theater의 enter 메서드를 이해하기 위해서는 다음과 같은 배경 지식을 갖고 있어야 한다.

- Audience는 Bag을 가지고 있다.
- Bag 안에는 현금과 티켓이 있다.
- TicketSeller가 TicketOffice에서 티켓을 판매한다.
- TicketOffice에서는 돈과 티켓이 보관돼 있다.

이 코드는 하나의 클래스나 메서드에서 너무 많은 세부사항을 다루기 때문에 코드를 작성하는 사람뿐만 아니라 코드를 읽고 이해해야 하는 사람 모두에게 큰 부담을 준다.

### 변경에 취약한 코드

Theater는 너무 많은 의존성을 가지고 있다. 현재 구조에서 `Theater`는 `TicketSeller`, `TicketOffice`, `Ticket`, `Audience`, `Bag` 객체를 의존하고 있다.

문제는 `Theater`가 의존하고 있는 클래스의 변화가 발생했을 때 영향을 받는다는 것이다. 예를 들어 관람객이 가방이 아니라 탱크를 들고 다니는 것으로 변경된다면, `Theater` 클래스 역시 수정돼야 한다

객체 사이의 의존성이 과한 경우를 **결합도(coupling)**이 높다고 하는데, 두 객체 사이의 결합도가 높으면 함께 변경될 확률도 높아지기 때문에 변경하기 어려워진다. 따라서 설계의 목표는 객체 사이의 결합도를 낮춰 변경이 용이한 설계를 만드는 것이다.

## 설계 개선하기

Theater는 너무 많은 객체에 의존하고 있고, 결합도가 높다. 따라서 다른 클래스의 변경에 취약해지므로, 전체적으로 코드를 변경하기 어렵다.

이를 해결하기 위해서는 각 객체를 자율적인 존재로 만들고, **의존성을 최소화** 하는 것이다.

### Theater 의존성 최소화

Theater는 Audience와 TicketSeller 뿐만 아니라 다른 불필요한 객체까지 의존하고 있다. 그래서 Theater의 enter 메서드에 있는 로직을 TicketSeller로 옮겨 Theater의 의존성을 최소화 해보자.

```kotlin
// Before
class Theater(private val ticketSeller: TicketSeller) {
	fun enter(audience: Audience) {
		if (auidience.getBag().hasInvitation()) {
			ticket = ticketSeller.getTicketOffice().getTicket()
			audience.getBag().setTickeT(ticket)
		} else {
			ticket = ticketSeller.getTicketOffice().getTicket()
			audience.getBag().minusAmount(ticket.getFee())
			ticketSeller.getTicketOffice().plusAmount(ticket.getFee())
			audience.getBag().setTickeT(ticket)
		}
	}
}

// After
class Theater(private val ticketSeller: TicketSeller) {
	fun enter(audience: Audience) {
		ticketSeller.sellTo(audience)
	}
}
```

```kotlin
class TicketSeller(private val ticketOffice: TicketOffice) {
	
	fun sellTo(audience: Audience) {
		if (auidience.getBag().hasInvitation()) {
			ticket = ticketSeller.getTicketOffice().getTicket()
			audience.getBag().setTickeT(ticket)
		} else {
			ticket = ticketSeller.getTicketOffice().getTicket()
			audience.getBag().minusAmount(ticket.getFee())
			ticketSeller.getTicketOffice().plusAmount(ticket.getFee())
			audience.getBag().setTickeT(ticket)
		}
	}
}
```

수정된 `Theater` 클래스에서는 `TicketOffice`에 접근하지 않는다. 또한 `Theater`는 `ticketOffice`가 `TicketSeller` 내부에 존재한다는 사실을 알지 못한다. `Theater`는 단지 `ticketSeller`가 `sellTo` 메시지를 이해하고 응답할 수 있다는 사실만 알 뿐이다.

이렇듯 `Theater`는 오직 `TicketSeller`의 인터페이스에만 의존하고, `TicketSeller`의 내부 구현에 대해서는 알지 못한다. 객체를 인터페이스와 구현으로 나누고 인터페이스만을 공개하는 것은 객체 사이의 결합도를 낮추고 변경하기 쉬운 코드를 작성하기 위해 따라야 하는 가장 기본적인 설계 원칙이다.

### Audience 캡슐화

`TicketSeller`에게도 아직 문제가 남아있다. `TicketSeller`는 `Audience`의 `Bag`을 확인하고 초대장의 유무에 따라 현금을 지불할 것인지를 정하고 있다. 이는 두 가지 문제점이 있다.

- TicketSeller는 Audience를 자율적으로 행동하지 못하는 수동적인 객체로 만들어 버렸다.
- TicketSeller가 Audience가 Bag을 갖고 있는지를 알 필요가 없다. (다른 객체를 불필요하게 의존한다)

이제 `TicketSeller`가 `Audience`의 인터페이스에만 의존하도록 만들자.

```kotlin
// Before
class TicketSeller(private val ticketOffice: TicketOffice) {
	
	fun sellTo(audience: Audience) {
		if (auidience.getBag().hasInvitation()) {
			ticket = ticketSeller.getTicketOffice().getTicket()
			audience.getBag().setTickeT(ticket)
		} else {
			ticket = ticketSeller.getTicketOffice().getTicket()
			audience.getBag().minusAmount(ticket.getFee())
			ticketSeller.getTicketOffice().plusAmount(ticket.getFee())
			audience.getBag().setTickeT(ticket)
		}
	}
}

// After
class TicketSeller(private val ticketOffice: TicketOffice) {
	
	fun sellTo(audience: Audience) {
		ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()))
	}
}
```

```kotlin
class Audience(private val bag: Bag) {
	
	fun buy(ticket: Ticket): Long {
	bag.setTicket(ticket)
		if (bag.hasInvitation()) {
			return 0
		} else {
			bag.minusAmount(ticket.getFee())
			return ticket.getFee()
		}
	}
}
```

수정된 코드에서는 `TicketSeller`와 `Audience` 사이의 결합도가 낮아졌다. 오직 메세지 하나로 의존하고 있다. 더이상 `Audience`의 구현을 수정하더라도 TicketSeller에는 영향을 미치지 않는다.

 또한 `Audience`는 자신의 가방 안에 초대장이 들어있는지를 스스로 확인한다. 더이상 외부에서는 `Audience`가 `Bag`을 갖고 있는지 알 필요가 없으며, `Audience`는 비로소 자율적인 존재가 되었다.

개선된 구조의 클래스 다이어그램은 아래와 같다.

<img width="2048" height="1193" alt="image" src="https://github.com/user-attachments/assets/535802aa-01bb-4708-a7ce-026b9aee3527" />

### 캡슐화와 응집도

객체 내부의 상태를 캡슐화 하고, 객체 간에 오직 메시지를 통해서만 상호작용 하도록 만들었다. 또한 밀접하게 연관된 작업만을 수행하고, 연관성 없는 작업은 다른 객체에게 위임하는 객체를 가리켜 응집도가 높다고 말한다.

결합도를 낮추고 응집도를 높이려면 자율적인 객체를 만들어야 한다. 외부의 간섭을 최대한 배제하고 메시지를 통해서만 협력하는 자율적인 객체들의 공동체를 만드는 것이 훌륭한 객체지향 설계를 얻을 수 있는 지름길이다.

### 절차지향과 객체지향

`Theater`의 `enter`메서드는 **프로세스**이며, `Audience`, `TicketSeller`, `Bag`, `TicketOffice`는 **데이터**라고 해보자.

**절차적 프로그래밍(Procedural Programming)**

- 프로세스와 데이터를 별도의 모듈(Theater)에 위치시키는 방식을 절차적 프로그래밍이라고 한다.
- 절차적 프로그래밍의 문제점
    - 우리의 직관에 위배되는 코드를 작성하여, 이해하기 어려운 코드를 만든다.
    - 데이터 변경의 영향을 지역적으로 고립시키기 어렵다. 따라서 변경하기 어려운 코드를 만든다.

**객체지향 프로그래밍(Object-Oriented Programming)**

- 데이터와 프로세스가 동일한 모듈 내부에 위치하도록 프로그래밍하는 방식을 객체지향 프로그래밍이라고 부른다.
- 훌륭한 객체지향 설계의 핵심은 캡슐화를 이용해 의존성을 적절하 관리함으로써, 객체 사이의 결합도를 낮추는 것이다.

### 더 개선할 수 있다

수정된 구조에서도 여전히 TicketOffice는 TicketSeller에 의해 끌려다니는 수동적인 존재이다. TicketSellet는 TicketOffice의 티켓을 마음대로 꺼내서 자기 멋대로 팔고, 받은 돈을 다시 TicketSeller에게 추가한다.

TicketOffice를 자율적인 객체로 만들어주자.

```kotlin
// Before
class TicketOffice(
	private val amount: Long,
	private val tickets: List<Ticket>,
) {

	fun getTicket(): Ticket {
		return tickets.remove(0)
	}
	
	fun minusAmount(amount: Long) {
		this.amount -= amount
	}
	
	fun plusAmount(amount: Long) {
		this.amount += amount
	}
}

// After
class TicketOffice(
	private val tickets: List<Ticket>,
) {

	fun sellTicketTo(audience: Audience) {
		plusAmount(audience.buy(getTicket())
	}

	fun getTicket(): Ticket {
		return tickets.remove(0)
	}
	
	fun plusAmount(amount: Long) {
		this.amount += amount
	}
}
```

```kotlin
class TicketSeller(private val ticketOffice: TicketOffice) {
	
	fun sellTo(audience: Audience) {
		ticketOffice.sellTicketTo(audience)
	}
}
```

수정 후에 분명 TicketOffice의 자율권은 되찾아 주었지만 만족스러운 결과는 아니다. 그 이유는 TicketOffice와 Audience 사이에 의존성이 추가됐기 때문이다. 즉, 객체들간의 결합도가 높아진 것이고, 변경하기 어려운 설계가 된 것이다.

이 예제를 통해 우리는 두 가지를 알게 되었다.

- 어떤 기능을 설계하는 방법은 다양할 수 있다.
- 그렇기 때문에 설계는 트레이드오프의 산물이다.

전후 중에 어떤 것이 더 나은 설계일까? 두 가지 모두 트레이드오프가 있다. 훌륭한 설계는 적절한 트레이드오프의 결과물이다. 이러한 트레이드 오프 과정이 설계를 어려우면서도 흥미진진한 작업으로 만드는 것이다.

## 객체지향 설계

변경하기 전과 변경한 후의 코드 실행 결과는 같다. 하지만 소프트웨어의 요구사항은 시시각각 변하고, 그 변화를 수용할 수 있어야 한다. 그러므로 변경을 수용할 수 있는 설계가 중요하다.

객체지향 프로그래밍은 의존성을 효율적으로 통제할 수 있는 다양한 방법을 제공하고, 요구사항 변경에 좀 더 수월하게 대응할 수 있는 가능성을 높여준다.

또한 우리가 세상을 바라보는대로 코드를 작성할 수 있게 도와, 우리가 코드를 읽을 때 더 쉽게 이해할 수 있게 한다.
