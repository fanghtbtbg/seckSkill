package observerPattern;

import java.util.Vector;

/**
 *@author fqc
 *@date 2017-4-8
 *@project seckSkill Maven Webapp
 *@package observerPattern
 *@des 被观察这抽象类
 */
public abstract class Subject {
	//定义一个观察者数组
	private Vector<Observer> obsVector=new Vector<Observer>();
	//增加一个观察者
	public void addObserver(Observer o){
		this.obsVector.add(o);
	}
	//删除一个观察者
	public void delObserver(Observer o){
		this.obsVector.remove(0);
	}
	//通知所有观察者
	public void notifyObservers(){
		for(Observer o:this.obsVector){
			o.update();
		}
	}

}
