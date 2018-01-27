package springbook.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static springbook.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserService.MIN_RECCOMENT_FOR_GOLD;

import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserService.TestUserService;
import springbook.user.service.UserService.TestUserServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DataSource dataSource;
    private List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
            new User("bumjin", "name1", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0),
            new User("joytouch", "name2", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
            new User("erwins", "name3", "p3", Level.SILVER, 60, MIN_RECCOMENT_FOR_GOLD - 1),
            new User("madnite1", "name4", "p4", Level.SILVER, 60, MIN_RECCOMENT_FOR_GOLD),
            new User("green", "name5", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
    }

    @Test
    public void upgradeLevels() throws Exception {
        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    @Test
    public void upgradeAllOrNothing() throws Exception {
        // 예외를 발생 시킬 ID
        UserService testUserService = new TestUserService(users.get(3).getId());
        // UserDao setting
        testUserService.setUserDao(this.userDao);
        // DataSource setting
        testUserService.setDataSource(dataSource);

        // given
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            testUserService.upgradeLevels();
            // 아래 코드가 실행되면 fail
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) {

        }
        checkLevelUpgraded(users.get(1), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }

    }
}















