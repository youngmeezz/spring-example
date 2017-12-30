# Spring Security Demo

#### index

<a href="#database">Database</a>


---

#### Database

> CREATE SCHEMA & USER

```
CREATE SCHEMA `spring_demo` DEFAULT CHARACTER SET utf8;

CREATE USER 'spring_user'@'localhost' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON spring_demo.* to 'spring_user'@'localhost';
```

---
