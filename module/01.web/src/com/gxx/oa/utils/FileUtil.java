package com.gxx.oa.utils;

import com.gxx.oa.interfaces.SymbolInterface;

import java.io.*;
import java.text.DecimalFormat;

/**
 * �ļ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:33
 */
public class FileUtil implements SymbolInterface {
    /**
     * �����С
     */
    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * �����ļ�
     * @param src
     * @param dst
     */
    public static void copy(File src, File dst) {
        try {
            int byteRead;
            if (src.exists()) { //�ļ�����ʱ
                InputStream inStream = new FileInputStream(src); //����ԭ�ļ�
                FileOutputStream fs = new FileOutputStream(dst);
                byte[] buffer = new byte[BUFFER_SIZE];
                while ( (byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("���Ƶ����ļ���������");
            e.printStackTrace();
        }
    }

    /**
     * ȡ���ļ���С
     * @param f
     * @return
     * @throws Exception
     */
    public static  long getFileSizes(File f) throws Exception{
        long s=0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s= fis.available();
        } else {
            f.createNewFile();
            System.out.println("�ļ�������");
        }
        return s;
    }

    /**
     * �ݹ� ȡ���ļ��д�С
     * @param f
     * @return
     * @throws Exception
     */
    public static  long getFileSize(File f)throws Exception
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            } else
            {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * ת���ļ���С
     * @param fileS
     * @return
     */
    public static  String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * �ݹ���ȡĿ¼�ļ�����
     * @param f
     * @return
     */
    public static  long getList(File f){
        long size = 0;
        File flist[] = f.listFiles();
        size=flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getList(flist[i]);
                size--;
            }
        }
        return size;
    }
}
