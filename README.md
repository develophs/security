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
1. 수신하는 B국가에서 암호를 해독할 수 있어야한다.(해독)<br/>
2. B국가에서 어디로 부터 왔는지 파악할 수 있어야한다. A에서 왔는지, C에서 왔는지 (인증문제)<br/>
<br/>
<h1>RSA(암호화)</h1>
Public key : 공개키  <br/>
Private key : 개인키 <br/>
키쌍 : 대칭키 <br/>

1. 암호화 해독의 문제 <br/>
A국가에서 암호화를 하는데 B국가의 Public key로 암호화를 해서 B국가로 보낸다. <br/>
C국가에서 탈취해도 B국가의 Private key가 없기때문에 문서를 읽지 못하고, B국가만 암호화를 해독할 수 있다.<br/>
보내는 측에서는 공개키로 암호화해서 보내고 해독할 때는 개인키로 해독한다.<br/>

<br/>

2. 어디에서 왔는가? <br/>
A의 개인키로 암호화를 추가적으로 진행하고, 수신하는 측에서 A의 공개키로 해독한다. <br/>
만약 A의 공개키로 해독이 불가능하면, A의 개인키로 암호화된게 아니다. 즉, 중간에 C가 내용을 변경했다. <br/>
개인키--> 전자 문서에서 서명으로 쓰인다. 인증정보 <br/>
<br/>
상대방의 공개키로 잠그다.(암호화) <br/>
자신의 개인키로 잠그다.(전자 서명,인증) <br/>

<br/>

B의 공개키로 암호화된 문서를 A의 개인키로 한번더 감싸준다. <br/>
B-> 1.A의 공개키로 열어본다. 열리면 A가 보낸것이고 안열리면 해커가 보낸거다.<br/>
2. A의 공개키로 열린 후 B의 개인키로 열어서 내용을 확인한다. <br/>
<br/>
<h1>RFC문서</h1>
네트워크상 약속된 규칙을 정의한 문서(=프로토콜이라 칭한다.) <br/>
WWW(World Wide Web) : RFC문서들로 이루어져있다.>> HTTP프로토콜 <br/>
RFC7519문서에 정의된 약속을 JWT라 부른다. 즉, RFC7519규정으로 JWT생성.
