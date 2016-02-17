package com.my.project.javadoc;

import java.util.LinkedHashMap;
import java.util.Map;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

/**
 * <pre>
 * 获取Java文件中类、方法和字段的注释
 * </pre>
 * 
 * @version 1.0
 * @author yang.dongdong
 */
public class APIDoclet {

    private static Map < String, String > classFieldComment;

    /**
     * 实现一个自定义的Doclet必须包含此方法
     * 
     * @param root com.sun.tools.javadoc生成的文档结构
     * @return 成功返回true，否则返回false
     */
    public static boolean start( RootDoc root ) {
        classFieldComment = new LinkedHashMap < String, String >();

        ClassDoc[] classes = root.classes();
        for ( int i = 0; i < classes.length; ++i ) {
            ClassDoc cd = classes[i];
            classFieldComment.put( "Class[" + cd.qualifiedName() + "]", singleLine( cd.commentText() ) );
            FieldDoc[] fields = cd.fields();
            for ( FieldDoc fd : fields ) {
                classFieldComment.put( "Field[" + fd.name() + "]", singleLine( fd.commentText() ) );
            }
            MethodDoc[] methods = cd.methods();
            for ( MethodDoc md : methods ) {
                classFieldComment.put( "Method[" + md.name() + "]", singleLine( md.commentText() ) );
            }
        }

        return true;
    }

    /**
     * 获取指定java文件的类、方法和字段的注释
     * 
     * @param javaFilePath java源文件
     * @return 类、方法和字段的注释
     */
    public static Map < String, String > getClassComment( String javaFilePath ) {
        String[] docArgs = new String[] { "-encoding", "UTF-8", "-doclet", APIDoclet.class.getName(), "-private",
                "-verbose", javaFilePath };
        com.sun.tools.javadoc.Main.execute( docArgs );
        return classFieldComment;
    }

    private static String singleLine( String s ) {
        if ( s == null )
            return null;
        return s.replaceAll( "[\\t\\n\\r]", "" ).replaceAll( "<pre>", "" ).replaceAll( "</pre>", "" ).trim();
    }
}
