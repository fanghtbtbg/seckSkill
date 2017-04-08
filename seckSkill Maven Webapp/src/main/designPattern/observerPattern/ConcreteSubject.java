package observerPattern;

/**
 *@author fqc
 *@date 2017-4-8
 *@project seckSkill Maven Webapp
 *@package observerPattern
 *@des 具体观察者
 */
public class ConcreteSubject extends Subject {
	//具体的业务
	public void doSomething(){
		super.notifyObservers();
	}

}
