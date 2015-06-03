package com.dangdang.digital.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * zip压缩解压工具类 Description: All Rights Reserved.
 * 
 * @version 1.0 2014年11月29日 上午11:10:41 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ZipUtil {

	/**
	 * 
	 * Description: 压缩zip
	 * 
	 * @Version1.0 2014年11月29日 上午11:07:53 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param srcfile
	 *            需要压缩的文件列表
	 * @param zipfile
	 *            压缩后的文件
	 * @throws IOException
	 */
	public static void ZipFiles(List<File> srcfiles, java.io.File zipfile) throws IOException {
		byte[] buf = new byte[1024];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
		for (File file : srcfiles) {
			FileInputStream in = new FileInputStream(file);
			out.putNextEntry(new ZipEntry(file.getName()));
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
		}
		out.close();
	}

	/**
	 * 
	 * Description: 解压缩zip
	 * 
	 * @Version1.0 2014年11月29日 上午11:08:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param zipfile
	 *            需要解压缩的文件
	 * @param descDir
	 *            解压后的目标目录
	 * @throws IOException
	 * @throws ZipException
	 */
	@SuppressWarnings("rawtypes")
	public static void UnZipFiles(java.io.File zipfile, String descDir) throws ZipException, IOException {
		ZipFile zf = new ZipFile(zipfile);
		for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
			ZipEntry entry = ((ZipEntry) entries.nextElement());
			String zipEntryName = entry.getName();
			InputStream in = zf.getInputStream(entry);
			OutputStream out = new FileOutputStream(descDir + zipEntryName);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
	}
	
	public static void unZip(java.io.File file, String descDir) throws ZipException, IOException{
		 ZipFile zipFile = new ZipFile(file);
         Enumeration emu = zipFile.entries();
         int i=0;
         while(emu.hasMoreElements()){
             ZipEntry entry = (ZipEntry)emu.nextElement();
             //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
             if (entry.isDirectory())
             {
                 new File(descDir +File.separator+ entry.getName()).mkdirs();
                 continue;
             }
             BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
             File temp = new File(descDir +File.separator+ entry.getName());
             //加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
             //而这个文件所在的目录还没有出现过，所以要建出目录来。
             File parent = temp.getParentFile();
             if(parent != null && (!parent.exists())){
                 parent.mkdirs();
             }
             FileOutputStream fos = new FileOutputStream(temp);
             BufferedOutputStream bos = new BufferedOutputStream(fos,1024);           
             
             int count;
             byte data[] = new byte[1024];
             while ((count = bis.read(data, 0, 1024)) != -1)
             {
                 bos.write(data, 0, count);
             }
             bos.flush();
             bos.close();
             bis.close();
         }
         zipFile.close();
	}
}
