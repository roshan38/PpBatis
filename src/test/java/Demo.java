import com.pp.bean.User;
import com.pp.dao.UserDao;
import com.pp.util.Resources;
import com.pp.util.SqlSession;
import com.pp.util.SqlSessionFactory;
import com.pp.util.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class Demo {
    @Test
    public void test() throws SQLException {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(Resources.getResourceAsStream("/sqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> users = userDao.selectAllUsers();
        System.out.println(users);
        int i = userDao.deleteUserById("3");
        System.out.println("删除了"+i+"行");
//        userDao.testException();
    }
}
