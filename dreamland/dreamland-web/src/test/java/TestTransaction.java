import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.service.UserService;

/**
 * @Author: 徐杰
 * @Date: 2019/5/4 0004 23:34
 */
/*@ContextConfiguration 注解表示加载配置文件，如果有多个配置文件用逗号隔开*/
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml","classpath:spring-mvc.xml"})

/*测试类必须要继承 AbstractJUnit4SpringContextTests 测试基类；*/
public class TestTransaction extends AbstractJUnit4SpringContextTests {

    /*通过 @Autowired 注解注入 UserService 对象；*/
    @Autowired
    private UserService userService;

    /*@Test 注解代表这是一个测试方法；
    * 在测试方法 testSave 方法中 new 一个对象 User，设置两个属性，然后调用 userService 的 regist 插入方法*/
    @Test
    public void testSave(){
        User user = new User();
        user.setNickName("阿杰来啦");
        user.setEmail("123456@qq.com");
        userService.regist(user);
    }
}
