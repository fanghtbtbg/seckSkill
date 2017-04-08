package observerPattern;

/**
 *@author fqc
 *@date 2017-4-8
 *@project seckSkill Maven Webapp
 *@package observerPattern
 *@des 具体观察者
 */
public class ConcreteObserver implements Observer{
    //实现更新方法
	@Override
	public void update() {		
		System.out.println("接收到消息并进行处理！");
	}
}
