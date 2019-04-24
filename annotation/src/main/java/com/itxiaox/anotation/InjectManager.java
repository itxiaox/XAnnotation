package com.itxiaox.anotation;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 */
public class InjectManager {


    public static void inject(Activity activity) {

        //布局的注入
        injectLatyout(activity);

        //控件的注入
        injectViews(activity);

        //事件的注入
        injectEvents(activity);
    }


    /**
     * 布局的注入
     *
     * @param activity 上下文对象,当前的Activity
     */
    private static void injectLatyout(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取这个类上面的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            //获取这个注解的值，即 R.layout.activity_main
            int layoutId = contentView.value();
            // 第一种方式
            //activity.setContentView(layoutId);
            //第二种方式，通过反射执行方法，setContentView是父类AppCompatActivity的方法，所有这里只能用getMethod
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                //执行方法, 在
                method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 控件的注入
     *
     * @param activity 上下文对象，当前的Activity
     */
    private static void injectViews(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取这个类里所有的属性
        Field[] fields = clazz.getDeclaredFields();
        //循环，获取每个属性
        for (Field field : fields) {
            //获取每个属性上面的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            //获取注解的值
            if (injectView != null) {
                int viewId = injectView.value();
                //第一种方式
                // Object view = activity.findViewById(viewId);
                // fiel
                //第二种
                Method method = null;
                try {
                    method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);
                    //将方法执行返回的值View 赋值给当前属性
                    field.setAccessible(true);//设置私有属性的访问权
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 事件的注入
     *
     * @param activity 当前的事件
     */
    private static void injectEvents(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类的所有方法
        Method[] methods = clazz.getDeclaredMethods();

        //循环，获取每个方法
        for (Method method : methods) {
            //获取每个方法上面的注解.注意每个方法上面可能有多个注解
            Annotation[] annotations = method.getAnnotations();
            //遍历每个方法的多个注解 ,如 @OnClick 注解
            for (Annotation annotation : annotations) {
                //获取注解上的注解类型， 获取注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    //获取注解上面的注解，如@OnClick上面的@EventBase
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    String listenerSettter = eventBase.listenerSettter(); // xx.SetOnClik
                    Class<?> listenerType = eventBase.listenerType(); // new View.OnClickLisetner
                    String callBackListener = eventBase.callBackListener(); //回调方法，onClick
                    //获取注解的值
                    // 通过anntationType 获取onClick注解的值
                    try {
                        Method valueMethod = annotationType.getDeclaredMethod("value"); // value()

                        //执行value方法获得注解的值
                        int[] viewIds = (int[]) valueMethod.invoke(annotation);

                        //动态代理拦截 事件
                        ListenerInvocationHandler listenerInvocationHandler = new ListenerInvocationHandler(activity);

                        listenerInvocationHandler.addMethodMap(callBackListener, method);//将自己的方法替换系统的事件方法
                        //打包之后，代理处理后续工作
                        Object listener = Proxy.newProxyInstance(
                                listenerType.getClassLoader(), //一个classloader对象，定义了由哪个classloader对象对生成的代理类进行加载
                                new Class[]{listenerType},//一个interface对象数组，表示我们将要给我们的代理对象提供一组什么样的接口，如果我们提供了这样一个接口对象数组，那么也就是声明了代理类实现了这些接口，代理类就可以调用接口中声明的所有方法。
                                listenerInvocationHandler);//一个InvocationHandler对象，表示的是当动态代理对象调用方法的时候会关联到哪一个InvocationHandler对象上，并最终由其调用。

                        //R.id.tv, R.id.btn,....
                        for (int viewId : viewIds) {
                            View view = activity.findViewById(viewId);

                            //获取方法 Method method = claszz.getMethod("findViewById",int.class)

                            Method setter = view.getClass().getMethod(listenerSettter, listenerType);

                            setter.invoke(view, listener);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
