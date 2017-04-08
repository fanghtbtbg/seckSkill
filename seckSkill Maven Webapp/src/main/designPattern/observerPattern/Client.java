package observerPattern;

public class Client {
   public static void main(String[] args){
	   //创建一个被观察者
	   ConcreteSubject csj=new ConcreteSubject();
	   //定义一个观察者
	   Observer os=new ConcreteObserver();
	   //观察者 观察 被观察者
	   csj.addObserver(os);
	   //观察者开始活动
	   csj.doSomething();
   }
}
