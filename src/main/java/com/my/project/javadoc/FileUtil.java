package com.my.project.javadoc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * <pre>
 * 文件操作工具类
 * </pre>
 * 
 * @version 1.0
 * @author yang.dongdong
 */
public class FileUtil {

    /**
     * 日志记录
     */
    private static Logger logger = Logger.getLogger( FileUtil.class.getName() );

    /**
     * 扫描指定目录及其子目录下的指定后缀名的文件
     * 
     * @param dir 要扫描的根目录
     * @param suffix 文件后缀名
     * @return 打描到的文件列表
     */
    public static List < File > getList( String dir, String suffix ) {
        logger.info( "...开始扫描以下目录：\n" + dir );
        List < File > sourceFiles = new ArrayList < File >();
        Vector < String > ver = new Vector < String >();
        ver.add( dir );
        while ( ver.size() > 0 ) {
            // 获取该文件夹下所有的文件(夹)名
            File[] files = new File( ver.get( 0 ).toString() ).listFiles();
            ver.remove( 0 );

            int len = files.length;
            for ( int i = 0; i < len; i++ ) {
                String tmp = files[i].getAbsolutePath();
                if ( files[i].isDirectory() ) {
                    // 如果是目录，则加入队列。以便进行后续处理
                    ver.add( tmp );
                } else {
                    // 如果是文件，则直接输出文件名到指定的文件。
                    if ( tmp.endsWith( suffix ) ) {
                        sourceFiles.add( files[i] );
                    }
                }
            }
        }
        logger.info( "...扫描完成，共找到" + sourceFiles.size() + "个*." + suffix + "文件" );
        return sourceFiles;
    }

    /**
     * 从文件路径中提取类名
     * 
     * @param sourceFiles 源文件列表
     * @param prefix 要除去的路径前缀
     * @param suffix 要除去的文件后缀名
     * @return 类名列表
     */
    public static List < String > getClassNames( List < File > sourceFiles, String prefix, String suffix ) {
        logger.info( "...开始提取完整类名" );
        List < String > classNames = new ArrayList < String >();
        for ( Iterator < File > it = sourceFiles.iterator(); it.hasNext(); ) {
            File file = (File) it.next();
            String className = file.getAbsolutePath();
            className = className.replace( prefix, "" );
            className = className.replace( suffix, "" );
            className = className.replace( File.separator, "." );
            classNames.add( className );
        }
        logger.info( "...提取完成，成功提取到" + classNames.size() + "个类名" );
        return classNames;

    }

    /**
     * 构建类名和文件路径的映射表
     * 
     * @param sourceFiles 源文件列表
     * @param prefix 要除去的路径前缀
     * @param suffix 要除去的文件后缀名
     * @return 类名和文件路径的映射表
     */
    public static Map < String, String >
            getClassNameToFilePath( List < File > sourceFiles, String prefix, String suffix ) {
        logger.info( "...开始构建类名和文件路径的映射表" );
        Map < String, String > classNameToFilePath = new LinkedHashMap < String, String >();
        for ( Iterator < File > it = sourceFiles.iterator(); it.hasNext(); ) {
            File file = (File) it.next();
            String className = file.getAbsolutePath();
            className = className.replace( prefix, "" );
            className = className.replace( suffix, "" );
            className = className.replace( File.separator, "." );
            classNameToFilePath.put( className, file.getAbsolutePath() );
        }
        logger.info( "...构建映射表完成，成功构建" + classNameToFilePath.size() + "个映射" );
        return classNameToFilePath;

    }

    /**
     * 将扫描结果写入文件
     * 
     * @param resultFilePath 文件路径
     * @param className 类名
     * @param classComment 类注释
     * @param fieldName 属性名
     * @param fieldComment 属性注释
     * @param log 是否输出详细log
     */
    public static void writeResult( String resultFilePath, String className, String classComment, String fieldName,
            String fieldComment, boolean log ) {
        File resultFile = new File( resultFilePath );
        StringBuilder record = new StringBuilder();
        record.append( className + "," );
        record.append( classComment + "," );
        record.append( fieldName + "," );
        record.append( fieldComment + "\n" );

        try {
            if ( !resultFile.exists() ) {
                resultFile.createNewFile();
                BufferedWriter title = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( resultFile,
                        true ) ) );
                String head = "类名,类注释,属性名,属性注释\n";
                title.write( head, 0, head.length() );
                title.flush();
                title.close();
                if ( log ) {
                    logger.info( head );
                }
            }
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( resultFile, true ) ) );
            out.write( record.toString(), 0, record.toString().length() );
            out.flush();
            out.close();
            if ( log ) {
                logger.info( record.toString() );
            }
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

}
