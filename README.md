# security

유튜브 메타코딩 스프링 시큐리티 강의 <br/>
https://www.youtube.com/@metacoding

<h1>TIL</h1>

2023.02.15 Session<br/>
<br/>
클라이언트가 홈페이지에 로그인을 성공하면 서버에서 헤더에 쿠키를 추가하고, SessionID를 부여한다. <br/>
로그인을 한 상태인 경우에는 클라이언트는 SessionID를 가지고 있기 때문에, Session을 다시 생성하지 않는다.<br/>
서버는 SessionID를 생성하고, 클라이언트에게 전해준다. 그 후 Session 목록에 자신이 생성한 SessionID을 저장해놓는다.(위조방지)<br/>
클라이언트가 브라우저를 종료하거나, 서버에서 세션을 제거, 일정 시간이 지난 후 세션이 제거된다. <br/>
서버는 세션이라는 저장소에 세션ID를 생성하고 인증정보가 필요한 경우 세션을 확인해서 인증과정을 거친다.<br/>
<br/>
세션의 단점: 서버가 계속해서 클라이언트의 세션을 유지시켜야한다. 동시접속한 클라이언트가 1000명인 경우 세션을 1000개 유지한다. <br/>
만약 서버의 세션 한계가 100개인 경우? 서버 10대를 유지해야한다. <br/>

CPU > RAM > HDD. 일반적으로 CPU는 메모리에서 캐싱된 데이터를 가져온다. HDD로 접근할때 I/O가 발생하고 처리 속도가 느리다.<br/>
데이터베이스는 주로 하드디스크에 저장하고, 현재는 메모리(RAM) 서버를 사용 ex)Redis <br/>
<br/>

2023.02.16 Session<br/>
<h1>TCP</h1>
통신 OSI 7계층<br/>

응용계층 : 웹브라우저, 애플리케이션<br/>
프레젠테이션계층 : 암호화, 압축<br/>
세션계층 : 인증 체크<br/>
트랜스포트계층 : TCP-순서를 보장, 신뢰성을 보장 / UDP-순서를 보장하지 않는다. 신뢰성이 떨어진다.(속도는 빠르다)<br/>
네트워크계층 : IP확인<br/>
WAN<br/>
----------------------------------------<br/>
LAN<br/>
데이터링크계층 : 호스트 서버 검색<br/>
물리계층 : 인터넷 케이블<br/>

<br/>

<h1>CIA</h1>
기밀성, 무결성, 가용성<br/>
A국가와 B국가간의 통신 문서에서 C국가가 가로채면 기밀성이 깨진다.  <br/>
C국가가 가로챈 문서를 조작하면 무결성이 깨진다. <br/>
즉, 가용성이 파괴된다.<br/>
<br/>
가용성을 유지하는 방법 : 문서를 암호화 한다. <br/>
문제점 <br/>
1. 수신하는 B국가에서 암호를 해독할 수 있어야한다.<br/>
2. B국가에서 어디로 부터 왔는지 파악할 수 있어야한다. A에서 왔는지, C에서 왔는지<br/>
<br/>

