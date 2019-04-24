package com.itxiaox.anotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ListenerInvocationHandler implements InvocationHandler {

    //需要拦截的对象
    private Object target;

    private HashMap<String, Method> methodMap = new HashMap<>();


    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * proxy:代理类代理的真实代理对象com.sun.proxy.$Proxy0
     * method:我们所要调用某个对象真实的方法的Method对象
     * args:指代代理对象方法传递的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //拦截系统的事件方法，将自己的自定义的方法替换掉系统的事件方法
        if (target != null) {
            //获取需要拦截的方法名
            String methodName = method.getName();//假如是 onClick()，
            //重新赋值，将拦截的方法换为自定义的方法。
            method = methodMap.get(methodName);//从集合中取出自己的方法。

            if (method != null) {//确实找到了需要拦截的反复，只执行自定义的方法。
                if (method.getGenericParameterTypes().length == 0) return method.invoke(target);
                return method.invoke(target, args);
            }
        }
        return null;
    }

    /**
     * 将需要拦截的方法加入集合
     *
     * @param methodName 需要拦截的方法，比如 onClick（）
     * @param method     执行自定义的方法，比如， submit()
     */
    public void addMethodMap(String methodName, Method method) {
        methodMap.put(methodName, method);
    }
}
