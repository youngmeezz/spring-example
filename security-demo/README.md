# Spring Security Demo

#### index

<a href="#database">Database</a>  
<a href="#security-settings">Security Settings</a>  
<a href="#limit-login-attempts">Limit Login Attempts</a>
<a href="">Logging for member`s action</a>  

---

#### Database

> CREATE SCHEMA & USER

```
CREATE SCHEMA `spring_demo` DEFAULT CHARACTER SET utf8;

CREATE USER 'spring_user'@'localhost' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON spring_demo.* to 'spring_user'@'localhost';
```

> Create table

```
# member
CREATE TABLE tbl_members (
  login_id varchar(255) not null,
  name varchar(255) not null,
  password varchar(255) not null,
  primary key(login_id)
);

# member roles
CREATE TABLE tbl_member_roles (
  id bigint not null auto_increment,
  role_name varchar(255),
  login_id varchar(255),
  primary key(id),
  foreign key(login_id) references tbl_members(login_id) ON DELETE CASCADE ON UPDATE CASCADE
);

# board
CREATE TABLE tbl_boards (
  id bigint not null auto_increment,
  title varchar(255),
  content varchar(2000),
  writer varchar(255),
  reg_date timestamp default now(),
  mod_date timestamp,
  primary key(id),
  foreign key(writer) references tbl_members(login_id) ON DELETE CASCADE ON UPDATE CASCADE
);

# login attempts
create table login_attempts (
    id bigint not null auto_increment,
    ip varchar(128),
    attempts int default 1,
    last_modified timestamp,
    primary key(id)
);
```

<div id="security-settings"> </div>

---

<div id="limit-login-attempts"> </div>

#### limit login attempts

- Classify failures as exceptions
  - not exist login id or not matched password => throw LoginNotMatchedException
  - exceed login attempts => throw ExceedLoginAttemptsException



> First : MemberAuthProvider.java

```
public class MemberAuthProvider implements AuthenticationProvider {
  ...

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String id = authentication.getName();
    String password = (String) authentication.getCredentials();

    // extract user ip & check try cnt
    WebAuthenticationDetails wad = (WebAuthenticationDetails) authentication.getDetails();
    String ip = wad.getRemoteAddress();
    checkLoginAttempts(ip);

    UserDetails securityMember = memberDetailService.loadUserByUsername(id);

    // check id & password
    if (securityMember == null || !passwordEncoder.matches(password, securityMember.getPassword())) {
      logger.debug("## [failed to sign in] id : {}", id);
      throw new LoginNotMatchedException();
    }
    ...
  }

  private void checkLoginAttempts(String ip) {
    LoginAttempts loginAttempts = loginAttemptsMapper.findByIp(ip);
    final int lockedMinutes = 5;
    final int limitOfAttempts = 3;

    // exist failure
    if (loginAttempts != null) {
      LocalDateTime lastModified = loginAttempts.getLastModified();
      if (lastModified != null) {
          long diff = System.currentTimeMillis() - lastModified.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
          // check locked time
          if(TimeUnit.MILLISECONDS.toMinutes(diff) >= lockedMinutes) {
            loginAttemptsMapper.resetFailAttempts(ip);
          }
          // exceed login attempts
          else if(loginAttempts.getAttempts() >= limitOfAttempts) {
            throw new ExceedLoginAttemptsException();
          }
        }
      }
    }
}

```

> Second : AuthFailureHandler.java

```
public class AuthFailureHandler implements AuthenticationFailureHandler {
  ...

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
      // extract login id
      String loginId = request.getParameter("loginid");
      boolean isExceedAttempts = false;
      request.setAttribute("loginid", loginId);

      String message = null;
      // Not exist id or Not matched password
      if(exception instanceof LoginNotMatchedException) {
          logger.debug("## [tried to login but failed] id : {}", loginId);
          message = "Please confirm ur id and password";
      }
      // exceed login attempts
      else if(exception instanceof ExceedLoginAttemptsException) {
          logger.debug("## [exceed login attempts] id : {}", loginId);
          isExceedAttempts = true;
          message = "Exceed login attempts. Please try again 5 minutes later";
      }
      else {
          if(exception != null) {
              logger.debug("## [not handle exception in auth failure handler] exception : {} , message : {}", new Object[] {
                      exception.getClass().getName(), exception.getMessage()
              });
          }
          else {
              logger.debug("## [cant handle login failure]");
          }
        }        
        request.setAttribute("failMessage", message);

        // update login failed attempts
        if (!isExceedAttempts) {
            String ip = request.getRemoteHost();
            LoginAttempts attempts = loginAttemptsMapper.findByIp(ip);

            if(attempts == null) {
                loginAttemptsMapper.save(ip);
            }
            else {
                loginAttemptsMapper.updateFailAttempts(ip);
            }
        }                
        request.getRequestDispatcher("/WEB-INF/views/loginPage.jsp").forward(request,response);
    }
}

```
