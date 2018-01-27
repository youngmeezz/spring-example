package springbook.user.service;

import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.domain.UserLevelUpgradePolicy;

public class UserService {

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMENT_FOR_GOLD = 30;

    private UserDao userDao;
    private UserLevelUpgradePolicy upgradePolicy;
    private DataSource dataSource;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUpgradePolicy(UserLevelUpgradePolicy upgradePolicy) {
        this.upgradePolicy = upgradePolicy;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    public void upgradeLevels() throws Exception {
        // 트랜잭션 동기화 관리자를 이용해 동기화 작업
        // private static final ThreadLocal<Set<TransactionSynchronization>> synchronizations = new NamedThreadLocal("Transaction synchronizations"); 에 저장
        TransactionSynchronizationManager.initSynchronization();
        // DB 커넥션 생성 + 동기화
        Connection conn = DataSourceUtils.getConnection(dataSource);
        conn.setAutoCommit(false);

        try {
            List<User> users = userDao.getAll();
            for(User user : users) {
                if(canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            // 정상 작업 마치면 트랜잭션 커밋
            conn.commit();
        } catch(Exception e) {
            conn.rollback();
            throw e;
        } finally {
            // 스프링 유틸 메소드를 이용해 DB 커넥션을 안전하게 닫음
            DataSourceUtils.releaseConnection(conn,dataSource);
            // 동기화 작업 종료 및 정리
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER:
                return (user.getRecommend() >= MIN_RECCOMENT_FOR_GOLD);
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown Level : " + currentLevel);
        }
    }

    static class TestUserService extends UserService {

        private String id;

        public TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {

    }
}
