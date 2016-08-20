package com.cargocn.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

/**
 * apache common compress 辅助
 * 
 * @author
 */
public class ApacheCompressUtil {
	/**
	 * 把文件压缩成zip格式
	 * 
	 * @param files
	 *            需要压缩的文件
	 * @param zipFilePath
	 *            压缩后的zip文件路径 ,如"D:/test/aa.zip";
	 */
	public static void compressFiles2Zip(File[] files, String zipFilePath) {
		if (files != null && files.length > 0) {
			if (isEndsWithZip(zipFilePath)) {
				ZipArchiveOutputStream zaos = null;
				try {
					File zipFile = new File(zipFilePath);
					zaos = new ZipArchiveOutputStream(zipFile);
					// Use Zip64 extensions for all entries where they are
					// required
					zaos.setUseZip64(Zip64Mode.AsNeeded);

					// 将每个文件用ZipArchiveEntry封装
					// 再用ZipArchiveOutputStream写到压缩文件中
					for (File file : files) {
						if (file != null) {
							ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
							zaos.putArchiveEntry(zipArchiveEntry);
							InputStream is = null;
							try {
								is = new BufferedInputStream(new FileInputStream(file));
								byte[] buffer = new byte[1024 * 5];
								int len = -1;
								while ((len = is.read(buffer)) != -1) {
									// 把缓冲区的字节写入到ZipArchiveEntry
									zaos.write(buffer, 0, len);
								}
								// Writes all necessary data for this entry.
								zaos.closeArchiveEntry();
							} catch (Exception e) {
								throw new RuntimeException(e);
							} finally {
								if (is != null)
									is.close();
							}

						}
					}
					zaos.finish();
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (zaos != null) {
							zaos.close();
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}

			}

		}

	}

	/**
	 * 把zip文件解压到指定的文件夹
	 * 
	 * @param zipFilePath
	 *            zip文件路径, 如 "D:/test/aa.zip"
	 * @param saveFileDir
	 *            解压后的文件存放路径, 如"D:/test/"
	 * @throws IOException
	 */
	public static List<File> decompressZip(String zipFilePath, String saveFileDir) throws IOException {
		if (isEndsWithZip(zipFilePath)) {
			File file = new File(zipFilePath);
			if (file.exists()) {
				InputStream is = new FileInputStream(file);
				return decompressZip(is, saveFileDir);
			}
		}
		return new ArrayList<File>();
	}

	public static List<File> decompressZip(InputStream is, String saveFileDir) throws IOException {
		List<File> ret = new ArrayList<File>();
		// can read Zip archives
		ZipArchiveInputStream zais = null;
		try {
			zais = new ZipArchiveInputStream(is);
			ArchiveEntry archiveEntry = null;
			// 把zip包中的每个文件读取出来
			// 然后把文件写到指定的文件夹
			while ((archiveEntry = zais.getNextEntry()) != null) {
				// 获取文件名
				String entryFileName = archiveEntry.getName();
				// 构造解压出来的文件存放路径
				String entryFilePath = saveFileDir + entryFileName;
				byte[] content = new byte[(int) archiveEntry.getSize()];
				zais.read(content);
				OutputStream os = null;
				try {
					// 把解压出来的文件写到指定路径
					File entryFile = new File(entryFilePath);
					os = new BufferedOutputStream(new FileOutputStream(entryFile));
					os.write(content);
					ret.add(entryFile);
				} catch (IOException e) {
					throw new IOException(e);
				} finally {
					if (os != null) {
						os.flush();
						os.close();
					}
				}

			}
		} finally {
			if (zais != null) {
				zais.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return ret;
	}

	public static List<File> decompressZip(byte[] zipFileBytes, String saveFileDir) throws IOException {
		return decompressZip(new ByteArrayInputStream(zipFileBytes), saveFileDir);
	}

	/**
	 * 判断文件名是否以.zip为后缀
	 * 
	 * @param fileName
	 *            需要判断的文件名
	 * @return 是zip文件返回true,否则返回false
	 */
	public static boolean isEndsWithZip(String fileName) {
		boolean flag = false;
		if (fileName != null && !"".equals(fileName.trim())) {
			if (fileName.endsWith(".ZIP") || fileName.endsWith(".zip")) {
				flag = true;
			}
		}
		return flag;
	}
}
