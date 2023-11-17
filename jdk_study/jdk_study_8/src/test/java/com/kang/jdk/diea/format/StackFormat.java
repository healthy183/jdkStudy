package com.kang.jdk.diea.format;

/**
 * User:
 * Description:
 * Date: 2023-06-28
 * Time: 23:29
 */
public class StackFormat {

    public static void main(String[] args) {
        String stack  ="batch:53, PreparedStatementHandler (org.apache.ibatis.executor.statement)\n" +
                "batch:70, RoutingStatementHandler (org.apache.ibatis.executor.statement)\n" +
                "doUpdate:78, BatchExecutor (org.apache.ibatis.executor)\n" +
                "update:116, BaseExecutor (org.apache.ibatis.executor)\n" +
                "update:74, CachingExecutor (org.apache.ibatis.executor)\n" +
                "update:200, DefaultSqlSession (org.apache.ibatis.session.defaults)\n" +
                "insert:187, DefaultSqlSession (org.apache.ibatis.session.defaults)\n" +
                "execute:64, MapperMethod (org.apache.ibatis.binding)\n" +
                "invoke:64, MapperProxy (org.apache.ibatis.binding)\n" +
                "insert:-1, $Proxy7 (com.sun.proxy)\n" +
                "lambda$insertBatch$0:108, MapperExtendTest (org.apache.ibatis.submitted.mapper_extend)\n" +
                "accept:-1, 292138977 (org.apache.ibatis.submitted.mapper_extend.MapperExtendTest$$Lambda$9)\n" +
                "forEach:1257, ArrayList (java.util)\n" +
                "insertBatch:107, MapperExtendTest (org.apache.ibatis.submitted.mapper_extend)\n" +
                "invoke0:-1, NativeMethodAccessorImpl (sun.reflect)\n" +
                "invoke:62, NativeMethodAccessorImpl (sun.reflect)\n" +
                "invoke:43, DelegatingMethodAccessorImpl (sun.reflect)\n" +
                "invoke:498, Method (java.lang.reflect)\n" +
                "runReflectiveCall:59, FrameworkMethod$1 (org.junit.runners.model)\n" +
                "run:12, ReflectiveCallable (org.junit.internal.runners.model)\n" +
                "invokeExplosively:56, FrameworkMethod (org.junit.runners.model)\n" +
                "evaluate:17, InvokeMethod (org.junit.internal.runners.statements)\n" +
                "evaluate:306, ParentRunner$3 (org.junit.runners)\n" +
                "evaluate:100, BlockJUnit4ClassRunner$1 (org.junit.runners)\n" +
                "runLeaf:366, ParentRunner (org.junit.runners)\n" +
                "runChild:103, BlockJUnit4ClassRunner (org.junit.runners)\n" +
                "runChild:63, BlockJUnit4ClassRunner (org.junit.runners)\n" +
                "run:331, ParentRunner$4 (org.junit.runners)\n" +
                "schedule:79, ParentRunner$1 (org.junit.runners)\n" +
                "runChildren:329, ParentRunner (org.junit.runners)\n" +
                "access$100:66, ParentRunner (org.junit.runners)\n" +
                "evaluate:293, ParentRunner$2 (org.junit.runners)\n" +
                "evaluate:26, RunBefores (org.junit.internal.runners.statements)\n" +
                "evaluate:306, ParentRunner$3 (org.junit.runners)\n" +
                "run:413, ParentRunner (org.junit.runners)\n" +
                "run:137, JUnitCore (org.junit.runner)\n" +
                "startRunnerWithArgs:69, JUnit4IdeaTestRunner (com.intellij.junit4)\n" +
                "execute:38, IdeaTestRunner$Repeater$1 (com.intellij.rt.junit)\n" +
                "repeat:11, TestsRepeater (com.intellij.rt.execution.junit)\n" +
                "startRunnerWithArgs:35, IdeaTestRunner$Repeater (com.intellij.rt.junit)\n" +
                "prepareStreamsAndStart:232, JUnitStarter (com.intellij.rt.junit)\n" +
                "main:55, JUnitStarter (com.intellij.rt.junit)";
        String[] stackSplit = stack.split("\n");
        for (int i = stackSplit.length - 1; i >= 0; i--) {
            String str = stackSplit[i];
            String[] split = str.split(",");
            String javaClazz = split[1];
            String[] javaClazzSplit = javaClazz.replace(")", "").split("\\(");
            String javaMethod = split[0];
            System.out.println(javaClazzSplit[1]+"."+javaClazzSplit[0].trim()+"#"+javaMethod);
        }
    }
}
