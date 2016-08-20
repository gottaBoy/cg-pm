package com.cargocn.zip;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * ZIP压缩工具--无法包含中文名称
 * 
 * @author
 * @since 1.0
 */
public class ZipUtils {
	private static final int BUFFER = 1024;

	/**
	 * 文件 解压缩
	 * 
	 * @param srcPath
	 *            源文件路径
	 * 
	 * @throws Exception
	 */
	public static List<File> decompress(String srcPath) throws IOException {
		File srcFile = new File(srcPath);

		return decompress(srcFile);
	}

	/**
	 * 解压缩
	 * 
	 * @param srcFile
	 * @throws Exception
	 */
	public static List<File> decompress(File srcFile) throws IOException {
		String basePath = srcFile.getParent();
		return decompress(srcFile, basePath);
	}

	/**
	 * 解压缩
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws Exception
	 */
	public static List<File> decompress(File srcFile, File destFile) throws IOException {

		CheckedInputStream cis = new CheckedInputStream(new FileInputStream(srcFile), new CRC32());

		ZipInputStream zis = new ZipInputStream(cis);

		List<File> ret = decompress(destFile, zis);

		zis.close();

		return ret;
	}

	public static List<File> decompress(byte[] srcFileBytes, String destPath) throws IOException {
		return decompress(srcFileBytes, new File(destPath));

	}

	public static List<File> decompress(byte[] srcFileBytes, File destFile) throws IOException {

		CheckedInputStream cis = new CheckedInputStream(new ByteArrayInputStream(srcFileBytes), new CRC32());

		ZipInputStream zis = new ZipInputStream(cis);

		List<File> ret = decompress(destFile, zis);

		zis.close();

		return ret;
	}

	/**
	 * 解压缩
	 * 
	 * @param srcFile
	 * @param destPath
	 * @throws Exception
	 */
	public static List<File> decompress(File srcFile, String destPath) throws IOException {
		return decompress(srcFile, new File(destPath));

	}

	/**
	 * 文件 解压缩
	 * 
	 * @param srcPath
	 *            源文件路径
	 * @param destPath
	 *            目标文件路径
	 * @throws Exception
	 */
	public static List<File> decompress(String srcPath, String destPath) throws IOException {

		File srcFile = new File(srcPath);
		return decompress(srcFile, destPath);
	}

	/**
	 * 文件 解压缩
	 * 
	 * @param destFile
	 *            目标文件
	 * @param zis
	 *            ZipInputStream
	 * @throws IOException
	 * @throws Exception
	 */
	private static List<File> decompress(File destFile, ZipInputStream zis) throws IOException {
		System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding")); // 防止文件名中有中文时出错
		List<File> ret = new ArrayList<File>();
		ZipEntry entry = null;
		while ((entry = zis.getNextEntry()) != null) {

			// 文件
			String dir = destFile.getPath() + File.separator + entry.getName();

			File dirFile = new File(dir);

			// 文件检查
			fileProber(dirFile);

			if (entry.isDirectory()) {
				dirFile.mkdirs();
			} else {
				ret.add(decompressFile(dirFile, zis));
			}

			zis.closeEntry();
		}
		return ret;
	}

	/**
	 * 文件探针
	 * 
	 * 
	 * 当父目录不存在时，创建目录！
	 * 
	 * 
	 * @param dirFile
	 */
	private static void fileProber(File dirFile) {

		File parentFile = dirFile.getParentFile();
		if (!parentFile.exists()) {

			// 递归寻找上级目录
			fileProber(parentFile);

			parentFile.mkdir();
		}

	}

	/**
	 * 文件解压缩
	 * 
	 * @param destFile
	 *            目标文件
	 * @param zis
	 *            ZipInputStream
	 * @throws IOException
	 * @throws Exception
	 */
	private static File decompressFile(File destFile, ZipInputStream zis) throws IOException {

		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = zis.read(data, 0, BUFFER)) != -1) {
			bos.write(data, 0, count);
		}

		bos.close();
		return destFile;
	}

}
