package com.example.reflect.filter;

import com.example.reflect.annotation.RequestMapping;
import com.example.reflect.controller.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;

public class Dispatcher implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Enter the Dispatcher");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

//        System.out.println("context path : " + httpServletRequest.getContextPath());
//        System.out.println("uri : " + httpServletRequest.getRequestURI());
//        System.out.println("url : " + httpServletRequest.getRequestURL());

        // Parsing uri : /reflect/user -> /user
        String endPoint =  httpServletRequest.getRequestURI().replaceAll(httpServletRequest.getContextPath(), "");
        System.out.println("End Point : " + endPoint);
        UserController userController = new UserController();

        // That's why we have to use reflection
        // Every time we add some methods in controller, we have to match them with these conditions
//        if(endPoint.equals("/join")) {
//            userController.join();
//        } else if(endPoint.equals("/login")) {
//            userController.login();
//        } else if(endPoint.equals("/user")) {
//            userController.user();
//        } else if(endPoint.equals("/hello")) {
//            userController.hello();
//        }

        // Mapping with name of methods
        Method[] methods = userController.getClass().getDeclaredMethods(); // not include methods of super class
//        for (Method method : methods) {
//            if(endPoint.equals("/" + method.getName())) {
//                try {
//                    method.invoke(userController);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        // Mapping with annotation of method
        for (Method method : methods) {
            Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class);
            RequestMapping requestMapping = (RequestMapping) annotation;

            if(requestMapping.value().equals(endPoint)) {
                System.out.println("Mapping Value : " + requestMapping.value());
                try {
                    // Get parameters from this method
                    Parameter[] params = method.getParameters();
                    String path;
                    if(params.length > 0) {
                        // 0번째 파라미터의 타입을 찾은 후, 그 타입으로 인스턴스 생성한다.
                        // 그 인스턴스를 Object 타입으로 담는다. (LoginDto 인지 JoinDto 인지 알 수 없기 때문)
                        Object dtoInstance = params[0].getType().newInstance();
                        setData(dtoInstance, httpServletRequest); // 인스턴스에 파라미터 값 추가
                        System.out.println(dtoInstance.toString());
                        path = (String) method.invoke(userController, dtoInstance);
                    } else {
                        path = (String) method.invoke(userController);
                    }

                    System.out.println("path = " + path);
                    RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher(path);
                    dispatcher.forward(httpServletRequest, httpServletResponse); // RequestDispatcher는 필터를 다시 안탄다.
                    break;
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private <T> void setData(T instance, HttpServletRequest request) {
        // 파라미터의 key 값을 받아온 후 이를 변형 해줘야 한다.
        System.out.println("Instance Type : " + instance.getClass());
        Enumeration<String> keys = request.getParameterNames();
        while(keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String methodKey = keyToMethodKey(key);
            System.out.println("Setter Method : "  + methodKey);

            // 리플렉션을 이용해 변형된 key 값과 메소드를 비교할 수 있다.
            Method[] methods = instance.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if(method.getName().equals(methodKey)) {
                    try {
                        method.invoke(instance, request.getParameter(key));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String keyToMethodKey(String key) {
        // username => setUsername, password => setPassword ...
        String firstKey = "set";
        String upperKey = key.substring(0, 1).toUpperCase();
        String remainKey = key.substring(1);

        return firstKey + upperKey + remainKey;
    }
}
