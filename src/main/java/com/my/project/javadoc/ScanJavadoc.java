package com.my.project.javadoc;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <pre>
 * Javadoc扫描器
 * </pre>
 * 
 * @version 1.0
 * @author yang.dongdong
 */
public class ScanJavadoc {

    /**
     * 日志记录
     */
    private static Logger logger = Logger.getLogger( ScanJavadoc.class.getName() );

    /**
     * 打描指定类的Javadoc
     * 
     * @throws ClassNotFoundException
     */
    public void scan( String className, String javaFilePath ) throws ClassNotFoundException {

        logger.info( "...开始提取Javadoc" );

        Map < String, String > classFieldComment = APIDoclet.getClassComment( javaFilePath );
        Class < ? > clazz = Class.forName( className );

        String classComment = classFieldComment.get( "Class[" + className + "]" );

        Field[] fields = clazz.getDeclaredFields();
        for ( Field field : fields ) {
            // 属性名
            String fieldName = field.getName();
            // 属性注释
            String fieldComment = classFieldComment.get( "Field[" + fieldName + "]" );
            // 将扫描结果写入文件
            FileUtil.writeResult( "javadoc.txt", className, classComment, fieldName, fieldComment, false );
        }

        Method[] methods = clazz.getDeclaredMethods();
        for ( Method method : methods ) {
            // 方法名
            String methodName = method.getName();
            // 方法注释
            String methodComment = classFieldComment.get( "Method[" + methodName + "]" );
            // 将扫描结果写入文件
            FileUtil.writeResult( "javadoc.txt", className, classComment, methodName + "()", methodComment, false );
        }
    }

    /**
     * Main
     */
    public static void main( String[] args ) throws ClassNotFoundException {
        File java = new File( "src/main/java/com/my/project/javadoc/FileUtil.java" );
        new ScanJavadoc().scan( "com.my.project.javadoc.FileUtil", java.getAbsolutePath() );
    }
}
