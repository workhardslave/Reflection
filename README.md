# Reflection
자바의 리플렉션을 이용하여 컨트롤러를 만들어보는 예제
- JDK 1.8 
- Java 8
- SpringBoot 2.4.1
- Gradle 6.7.1
- Postman

<br/>

## 리플렉션이란?
구체적인 클래스 타입을 알지 못해도, 컴파일된 바이트 코드를 통해 역으로 클래스의 정보를 알아낼 수 있도록 도와주는 Java API <br/>
컴파일이 아니라 런타임에 특정 클래스의 정보를 객체화함으로써 분석 및 추출해낼 수 있는 특징을 가진다. 

<br/>

## Why?
- 동적으로 클래스를 사용해야할 때 필요
- 작성 시점에는 어떤 클래스를 사용해야할 지 모르는 경우
- 런타임 시점에서 클래스를 가져와서 실행해야 하는 경우에 사용
- ex) IntelliJ의 자동완성, 어노테이션, 컨트롤러 매핑 등

<br/>

## How?
우리가 작성한 소스코드가 JVM 위에서 실행된다는 특성이 있기 때문이다. 우리가 자바 코드의 컴파일을 시도하면,
JVM상에 우리가 작성한 코드들(.java)는 .class라는 바이트 코드로 변환된 후 static 영역에 저장된다.
따라서 클래스의 이름만 알고 있다면 언제든 이 영역을 뒤져서 클래스에 대한 정보를 가져올 수 있는 것이다.
또한 클래스 로딩이 끝나면 해당 클래스 타입의 클래스 객체를 생성하여 힙 메모리 공간에 저장되고, 언제든지 인스턴스 사용이 가능하게 된다.

<br/>

## 리플렉션을 통해 알 수 있는 정보
- Class Name
- Class Modifiers(public, private, synchronized etc)
- Package Info
- Super Class
- Implemented Interfaces
- Constructors
- Methods
- Fields
- Annotations

<br/><br/>

> Reference 

https://www.youtube.com/playlist?list=PL93mKxaRDidFGJu8IWsAAe0O7y6Yw9f5x

https://brunch.co.kr/@kd4/8#comment

http://libqa.com/wiki/88
