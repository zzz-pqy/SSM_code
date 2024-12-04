package ioc_05;

import org.springframework.beans.factory.FactoryBean;

// 实现FactoryBean接口时需要指定泛型
// 泛型类型就是当前工厂要生产的对象的类型
public class HappyFactoryBean implements FactoryBean<HappyMachine> {

    private String machineName;

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    @Override
    public HappyMachine getObject() throws Exception {

        // 方法内部模拟创建、设置一个对象的复杂过程
        HappyMachine happyMachine = new HappyMachine();

        happyMachine.setMachineName(this.machineName);

        return happyMachine;
    }

    @Override
    public Class<?> getObjectType() {

        // 返回要生产的对象的类型
        return HappyMachine.class;
    }
}
