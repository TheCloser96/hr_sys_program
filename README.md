인사관리 프로그램
------------------------------------------------------------------------------
### 개발 동기 </br>
이전 프로젝트는 JPA비중보다 오히려 javaScript 점유율이 높게 작성되었습니다 </br>
그리고 Querydsl도 적용이 되어있지 않았던 상태였습니다 </br>
이러한 점들을 개선하고 새롭게 작성하고 싶다는 생각이 들어서 REST API 설계방식, Querydsl 사용, test코드 작성 </br>
그리고 OSIV를 비활성화 한 상태에서 프로젝트를 만드는 것을 목표로 하였습니다

이전 프로젝트 참고: <https://github.com/channeyforwork/sample>
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
### 프로젝트 간단 설명</br>
사원의 정보와 부서의 정보를 입력 및 조회할 수 있는 REST API 방식 설계 </br>
그리고 이전에 사용하지 못해봤던 Querydsl을 사용하여 이전에 비해 효율적이고 유연하게 개발하였습니다
</br> </br>

#### 해당 프로젝트 PPT 링크: <https://docs.google.com/presentation/d/1YxOMuP9DbclMwt1zFtkSuSe1xWS-c9Ig/edit?usp=sharing&ouid=115740313083613061791&rtpof=true&sd=true> </br>
#### 해당 프로젝트 시연 링크: <https://drive.google.com/file/d/1guDAPKRqVHqyRtjHcs-UEoWHqLb9D3JQ/view?usp=sharing>
</br> </br>

> **사용 도구**
> * Java 11
> * InteliJ IDEA
> * Spring Boot
> * MySQL
> * ~~Thymleaf(사실상 안씀)~~
> * **Querydsl**
>
<br/>

> **전작과의 차이점 및 개선점**
> * 화면구현 X
> * fetch join 사용
> * Querydsl 사용
> * OSIV 비활성화
> * REST API 위주의 개발
> * Test Code 작성
> * application.properties를 application.yml 방식으로 변경
<br/>
