import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTemplateTest {


    /**
     * 使用jdbcTemplate进行DML动作
     */
    @Test
    public void testDML(){

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("practice.xml");

        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);

        //TODO 执行插入一条学员数据
        String sql = "insert into students (id,name,gender,age,class) values (?,?,?,?,?);";
    /*
        参数1: sql语句
        参数2: 可变参数,占位符的值
     */
        int rows = jdbcTemplate.update(sql, 9,"十一", "男", 18, "二年三班");

        System.out.println("rows = " + rows);

    }


    /**
     * 查询单条实体对象
     *   public class Student {
     *     private Integer id;
     *     private String name;
     *     private String gender;
     *     private Integer age;
     *     private String classes;
     */
    @Test
    public void testDQLForPojo(){

        String sql = "select id , name , age , gender , class as classes from students where id = ? ;";

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring-ioc.xml");

        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);

        //根据id查询
        Student student = jdbcTemplate.queryForObject(sql,  (rs, rowNum) -> {
            //自己处理结果映射
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setName(rs.getString("name"));
            stu.setAge(rs.getInt("age"));
            stu.setGender(rs.getString("gender"));
            stu.setClasses(rs.getString("classes"));
            return stu;
        }, 2);

        System.out.println("student = " + student);
    }



    /**
     * 查询实体类集合
     */
    @Test
    public void testDQLForListPojo(){

        String sql = "select id , name , age , gender , class as classes from students  ;";

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring-ioc.xml");

        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
    /*
        query可以返回集合!
        BeanPropertyRowMapper就是封装好RowMapper的实现,要求属性名和列名相同即可
     */
        List<Student> studentList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class));

        System.out.println("studentList = " + studentList);
    }

}
