package spring_ioc_test;

import ioc_03.A;
import ioc_03.HappyComponent;
import ioc_04.BeanTwo;
import ioc_05.HappyMachine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ioc_test {
    //创建容器

    public void createIoc() {
        //选择适合的容器实现
       /*SpringIoc容器接口：
            `BeanFactory` 接口提供了一种高级配置机制，能够管理任何类型的对象，它是SpringIoC容器标准化超接口！
            `ApplicationContext` 是 `BeanFactory` 的子接口。*/
       /* ApplicationContext容器实现类：
            ClassPathXmlApplicationContext：通过读取类路径下的 XML 格式的配置文件创建 IOC 容器对象|
            FileSystemXmlApplicationContext：通过文件系统路径读取 XML 格式的配置文件创建 IOC 容器对象|
            AnnotationConfigApplicationContext：通过读取Java配置类创建 IOC 容器对象|
            WebApplicationContext：专门为 Web 应用准备，基于 Web 环境创建 IOC 容器对象，并将对象引入存入 ServletContext 域中。|
        */
        //方法1：直接创建容器并且制定配置文件(可以写一个或者多个)
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-03.xml");
        //方法2：分解配置
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
        classPathXmlApplicationContext.setConfigLocation("spring-03.xml");
        classPathXmlApplicationContext.refresh();
    }
    @Test
    public void getBeanFromeIoc() {
        //创建ioc容器对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-03.xml");
        //直接读取bean
        HappyComponent happyComponent = (HappyComponent) applicationContext.getBean("happyComponent");

        //方法2
        HappyComponent happyComponent1 = applicationContext.getBean("happyComponent", HappyComponent.class);
        //方法3:bean类型是唯一的，通过类型获取
        HappyComponent happyComponent2 = applicationContext.getBean(HappyComponent.class);

        happyComponent.doWork();
        happyComponent1.doWork();
        happyComponent2.doWork();
        //通过接口获取
        A happyComponent3 = applicationContext.getBean(A.class);
        happyComponent3.doWork();
        //ioc配置一定是实现类，但可以通过接口获取，当getBean(类型) instanceof ioc配置的类型时，返回true才可以
    }


    //测试试验4
    @Test
    public void test_04(){
        //实例化
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-04.xml");
        //正常结束ioc容器
        classPathXmlApplicationContext.close();

        //scope测试
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-04.xml");

        BeanTwo bean = applicationContext.getBean("beanTwo", BeanTwo.class);
        BeanTwo bean1 = applicationContext.getBean("beanTwo", BeanTwo.class);
        //单例对比 false
        System.out.println(bean == bean1);

        BeanTwo bean2 = applicationContext.getBean("beanTwo1", BeanTwo.class);
        BeanTwo bean3 = applicationContext.getBean("beanTwo1", BeanTwo.class);
        //单例对比 true
        System.out.println(bean2 == bean3);
    }

    @Test
    public void testExperiment07()  {

        ApplicationContext iocContainer = new ClassPathXmlApplicationContext("spring-05.xml");

        //注意: 直接根据声明FactoryBean的id,获取的是getObject方法返回的对象
        HappyMachine happyMachine = iocContainer.getBean("happyMachine", HappyMachine.class);
        System.out.println("happyMachine = " + happyMachine);

        //如果想要获取FactoryBean对象, 直接在id前添加&符号即可!  &happyMachine7 这是一种固定的约束
        Object bean = iocContainer.getBean("&happyMachine");
        System.out.println("bean = " + bean);
    }


}