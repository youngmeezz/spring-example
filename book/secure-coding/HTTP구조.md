# HTTP 구조
; Hyper Text Transfer Protocol는 웹상에서 정보를 주고받기 위해 사용되는 애플리케이션  
계층의 프로토콜로 메시지 기반으로 동작  
(TCP Connection -> ASCII 텍스트로 만들어진 Request -> 서버에서 Response -> Close)  

> 요청(Request) 메시지 구조

<table>
  <tr>
    <th>메시지 구조</th> <th>내용</th>
  </tr>
  <tr>
    <td>Request Line</td> <td>요청방식, 요청페이지, 프로토콜/버전</td>
  </tr>
  <tr>
    <td>Header</td> <td>General Headers <br>Request Headers<br>Entity Headers</td>
  </tr>
  <tr>
    <td>Blank Line</td> <td>(헤더와 바디 구분 CRLF(\r\n))</td>
  </tr>
  <tr>
    <td>Body</td> <td>서버로 전달할 파라미터</td>
  </tr>
</table>

---

> 응답(Response) 메시지 구조

<table>
  <tr>
    <th>메시지 구조</th> <th>내용</th>
  </tr>
  <tr>
    <td>Status Line</td> <td>프로토콜/버전, 응답 상태 코드, 응답 상태 메시지</td>
  </tr>
  <tr>
    <td>Header</td> <td>General Headers <br>Request Headers<br>Entity Headers</td>
  </tr>
  <tr>
    <td>Blank Line</td> <td></td>
  </tr>
  <tr>
    <td>Body</td> <td>응답 메시지(HTML)</td>
  </tr>
</table>

---

> 일반적인(General) 헤더 정보

<table>
  <tr>
    <th>이름</th> <th>값</th> <th>HTTP 1.0</th> <th>HTTP 1.1</th>
  </tr>
  <tr>
    <td>Cache-Control</td> <td>캐시를 제어하기 위한 정보(예: no-cache)</td> <td>X</td> <td>O</td>    
  </tr>
  <tr>
    <td>Connection</td> <td>TCP 연결 제어 정보(예:close)</td> <td>X</td> <td>O</td>    
  </tr>

  <tr>
    <td>Date</td> <td>내용이 생성된 날짜와 시간 정보</td> <td>O</td> <td>O</td>    
  </tr>
  <tr>
    <td>Pragma</td> <td>브라우저에 no-cache와 같은 캐싱 지시를 보내기 위해 사용</td> <td>O</td> <td>X</td>    
  </tr>
  <tr>
    <td>Trailer</td> <td>Message Body의 끝을 가리킴</td> <td>X</td> <td>O</td>    
  </tr>
  <tr>
    <td>Trasfer-Encoding</td> <td>Message Body의 인코딩 방식 정보</td> <td>X</td> <td>O</td>    
  </tr>
  <tr>
    <td>Upgrade</td> <td>클라이언트가 추가적인 프로토콜을 지원하는지 지정</td> <td>X</td> <td>O</td>    
  </tr>
  <tr>
    <td>Via</td> <td>게이트웨이나 프록시 등을 통과할 경우 중간 장비 정보 포함</td> <td>X</td> <td>O</td>    
  </tr>
  <tr>
    <td>Warning</td> <td>메시지 상태에 대한 추가적인 정보 제공</td> <td>X</td> <td>O</td>
  </tr>
</table>

---

> HTTP Request 헤더 정보

<table>
  <tr>
    <th>이름</th> <th>값</th> <th>HTTP 1.0</th> <th>HTTP 1.1</th>
  </tr>
  <tr>
    <td>Accept</td> <td>브라우저에서 지원하는 미디어 방식</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Accept-Charset</td> <td>브라우저에서 지원하는 문자 셋</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Accept-Encoding</td> <td>브라우저에서 지원하는 인코딩 방식</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Accept-Language</td> <td>브라우저에서 지원하는 언어</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Authorization</td> <td>서버에 전송하는 인증정보</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>Expect</td> <td>사용자의 요청에 대한 특별한 응답</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>From</td> <td>웹 마스터의 전자우편 주소</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>Host</td> <td>웹 서버의 URL</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>If-Match</td> <td>요청한 자원의 태그와 동일하면 응답하도록 설정</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>If-Modified-Since</td> <td>지정된 시간 이후로 변경되었을 때만 응답하도록 설정</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>If-None-Match</td> <td>클라이언트가 요청한 자원과 요청 태그가 일치하지 않으면 응답하도록 설정</td>
    <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>If-Range</td> <td>조건부 요청에 사용</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>If-Unmodified-Since</td> <td>If-Modified-Since와 반대</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Max-Fowards</td> <td>TRACE나 OPTIONS와 함께 사용되는 IP 헤더의 TTL과 유사하게 동작</td>
    <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Proxy-Authorization</td> <td>프록시 인증정보</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Range</td> <td>바이트 범위를 지정해 일부만 받도록 설정</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Referer</td> <td>현재 요청 이전의 URL</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>TE</td> <td>서버가 보내는 데이터를 클라이언트가 어떻게 처리할지 설정</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>User-Agent</td> <td>브라우저 정보</td> <td>O</td> <td>O</td>
  </tr>
</table>

---

> HTTP Response 헤더 정보  
status 라인의 요약 결과에 따라 부가정보를 제공

<table>
  <tr>
    <th>이름</th> <th>값</th> <th>HTTP 1.0</th> <th>HTTP 1.1</th>
  </tr>
  <tr>
    <td>Accept-Ranges</td> <td>Range 요청에 대한 응답 정보</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Age</td> <td>자원의 수명</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>ETag</td> <td>Entity Tag</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Location</td> <td>리다이렉션 시킬 주소</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>Proxy-Authentication</td> <td>프록시 서버의 인증 방법 설정</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Retry-After</td> <td>재요청까지 시간 정보</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Server</td> <td>웹 서버 정보</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Vary</td> <td>캐시에서 페이지를 표시할지 결정할 때 사용자 에이전트를 고려해야 한다는 사실을 캐싱 서버에 알림</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>WWW-Authenticate</td> <td>서버가 클라이언트에 인증 방법 설정</td> <td>X</td> <td>O</td>
  </tr>
</table>

---

> HTTP Entity 헤더 정보  
데이터에 포함된 내용을 설명하는 정보

<table>
  <tr>
    <th>이름</th> <th>값</th> <th>HTTP 1.0</th> <th>HTTP 1.1</th>
  </tr>
  <tr>
    <td>Allow</td> <td>지원하는 메소드 목록</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>Content-Encoding</td> <td>메시지 인코딩 방법</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>Content-Language</td> <td>메시지에서 사용하는 언어</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>Content-Length</td> <td>메시지 크기</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>Content-Location</td> <td>메시지의 URL</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Content-MD5</td> <td>메시지의 MD5 값</td> <td>X</td> <td>O</td>
  </tr>
  <tr>
    <td>Content-Range</td> <td>Range 요청에 따른 응답</td> <td>X</td> <td>O</td>
  </tr>  
  <tr>
    <td>Content-Type</td> <td>메시지 타입</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>Expires</td> <td>HTTP 캐시에 보관하는 메시지의 유효기간</td> <td>O</td> <td>O</td>
  </tr>
  <tr>
    <td>Last-Modified</td> <td>자원이 마지막으로 변경 된 시간</td> <td>O</td> <td>O</td>
  </tr>
</table>

---

> HTTP 메소드  

<table>
  <tr>
    <th>HTTP Method</th> <th>설명</th>
  </tr>
  <tr>
    <td>GET</td>
    <td>
      자원 요청(default 값), 요청 파라미터를 URL 에 붙여 전송 <br>
      보안이 요구되지 않는 짧은 데이터를 전송하는 경우, 북마크가 필요한 경우에 사용
    </td>
  </tr>
  <tr>
    <td>POST</td>
    <td>
      자원요청, 요청 파라미터를 Body에 붙여서 전송 <br />
      전송할 데이터의 양이 많은 경우, 비밀 정보를 사용해야 하는 경우 사용      
    </td>
  </tr>
  <tr>
    <td>HEAD</td>
    <td>
      HTTP Header 정보만 요청
      메타(Meta) 정보 획득이나 Hyper Text의 링크 유효성을 검사하는 용도로 사용
    </td>
  </tr>
  <tr>
    <td>PUT</td>
    <td>
      URL 자원을 생성하도록 요청 <br />
      파일 업로드 가능
    </td>
  </tr>
  <tr>
    <td>DELETE</td>
    <td>
      URL 자원을 삭제하도록 요청 <br />
      파일 삭제 가능
    </td>
  </tr>
  <tr>
    <td>TRACE</td>
    <td>
      진단을 위한 목적으로 사용되는 메소드로, 서버는 응답 바디에 요청 받은 자료와 똑같은 내용을 보냄
    </td>
  </tr>
  <tr>
    <td>OPTIONS</td>
    <td>
      응답 가능한 HTTP 메소드를 요청
    </td>
  </tr>
</table>

---

> HTTP 응답 상태 코드

[wiki] : https://ko.wikipedia.org/wiki/HTTP_%EC%83%81%ED%83%9C_%EC%BD%94%EB%93%9C














<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
